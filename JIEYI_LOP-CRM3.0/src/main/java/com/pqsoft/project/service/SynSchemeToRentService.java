package com.pqsoft.project.service;

import com.google.common.base.Strings;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 同步方案改动到租金计划及beginning
 *
 * @author 钟林俊
 * @version V1.0 2016-07-06 14:17
 */
public class SynSchemeToRentService {

    private static final Logger logger = LoggerFactory.getLogger(SynSchemeToRentService.class);
    private static final String MAPPER = "SynSchemeToRentMapper.";

    /**
     * 同步跟指定项目id相关的方案及方案子数据到租金计划表，租金计划子表和beginning表中
     *
     * @param projectId 项目id
     */
    public void synSchemeToRent(String projectId){
        if(Strings.isNullOrEmpty(projectId)){
            throw new ActionException("合同重签-数据同步时项目id为空！");
        }
        // 锁方案表和设备表中符合projectId的记录, 用于判断项目修改后的设备数量
        List<Long> equipmentIds = Dao.selectList(MAPPER + "lockEquipmentByProjectId", projectId);
        // 锁租金计划表和租金计划子表中符合projectId的记录
        List<Long> payIds = Dao.selectList(MAPPER + "lockRentPlanByProjectId", projectId);
        // 锁beginning表中符合projectId的记录
        List<String> payListCodes = Dao.selectList(MAPPER + "lockBeginningByProjectId", projectId);

        // 检查同步的可行性
        if(checkRent(new HashSet<>(equipmentIds).size(), new HashSet<>(payIds).size(), new HashSet<>(payListCodes).size())){
            // 备份同步前数据
            backupRent(projectId);
            // 同步租金计划表
            synSchemeToRentHead(projectId);
            // 同步租金计划子表和beginning
            synSchemeToRentDetail(projectId, payIds.get(0));
        }
    }

    /**
     * 备份fil_rent_plan_head, fil_rent_plan_detail, fi_r_beginning三表中跟指定项目id相关的数据
     *
     * @param projectId 项目id
     */
    private void backupRent(String projectId) {
        Map<String, Object> backupIdAndProjectId = new HashMap<>(2);
        backupIdAndProjectId.put("backupId", Dao.getSequence("seq_rent_plan_head_backup"));
        backupIdAndProjectId.put("projectId", projectId);
        // 备份同步前的租金计划表
        Dao.insert(MAPPER + "backupRentHead", backupIdAndProjectId);
        // 备份同步前的租金计划子表
        Dao.insert(MAPPER + "backupRentDetail", backupIdAndProjectId);
        // 备份同步前的beginning表
        Dao.insert(MAPPER + "backupBeginning", backupIdAndProjectId);
    }

    /**
     * 同步跟指定项目id相关的支付详细数据到租金计划子表和beginning表
     *
     * @param projectId 项目id
     * @param payId 租金计划id
     */
    private void synSchemeToRentDetail(String projectId, Long payId) {
        // 同步首付数据
        synDownPaymentToRentDetail(projectId, payId);
        // 同步还款期次数据
        synPeriodPaymentToRentDetail(projectId, payId);
    }

    /**
     * 同步跟指定项目id相关的方案子表数据到租金计划子表和beginning表
     *
     * @param projectId 项目id
     */
    private void synPeriodPaymentToRentDetail(String projectId, Long payId) {
        // 租金计划子表期数
        int rentPeriodsCount = Dao.selectInt(MAPPER + "countRentDetailPeriodsByProjectId", projectId);
        // 方案子表详情数据
        List<Map<String,Object>> schemeDetailList = Dao.selectList(MAPPER + "selectSchemeDetailByProjectId", projectId);
        Object period;
        int periodNum = 0;
        Object item;
        String itemName;
        // 遍历方案子表数据并同步
        for(Map<String, Object> schemeDetail : schemeDetailList){
            // 期次
            period = schemeDetail.get("PERIOD_NUM");
            periodNum = period == null ? 0 : Integer.parseInt(period.toString());
            // 费用项
            item = schemeDetail.get("ITEM_NAME");
            itemName = item == null ? "" : item.toString();
            if(periodNum > rentPeriodsCount){
                Dao.insert(MAPPER + "insertRentDetail", schemeDetail);
                switch (itemName){
                    case "本金":
                    case "管理费":
                    case "利息":
                    case "利息增值税":
                    case "手续费":
                        Dao.insert(MAPPER + "insertBeginning", schemeDetail);
                        break;
                    default: break;
                }
            }else{
                // 方案子表和租金计划子表中期次相同的进行更新
                Dao.update(MAPPER + "updateRentDetailByDetailScheme", schemeDetail);
                // 更新条件中加入了未核销（beginning_flag = 0）
                switch (itemName){
                    case "本金":
                    case "管理费":
                    case "利息":
                    case "利息增值税":
                    case "手续费":
                        Dao.update(MAPPER + "updateBeginningByDetailScheme", schemeDetail);
                        break;
                    default: break;
                }
            }
        }

        Map<String, Object>schemeDetail = new HashMap<>();
        schemeDetail.put("PROJECT_ID", projectId);
        schemeDetail.put("PAY_ID", payId);
        while(periodNum < rentPeriodsCount){
            schemeDetail.put("PERIOD_NUM", ++periodNum);
            Dao.delete(MAPPER + "deleteRentDetailByPayIdAndPeriodNum", schemeDetail);
            Dao.delete(MAPPER + "deleteBeginningByProjectIdAndPeriodNum", schemeDetail);
        }
    }

    /**
     * 同步跟指定项目id相关的首付信息到租金计划子表和beginning表
     *
     * @param projectId 项目id
     * @param payId 租金计划id
     */
    private void synDownPaymentToRentDetail(String projectId, Long payId) {

        int settledDownPaymentsCount = Dao.selectInt(MAPPER + "countSettledDownPaymentsByProjectId", projectId);
        // 首付有核销不做同步
        if(settledDownPaymentsCount != 0){
            logger.debug("Beginning表中存在已核销的首付数据，跳过首付数据的同步工作！");
            return;
        }
        // 删除原有的首付数据
        Map<String, Object> payIdAndProjectIdAndPeriodNum = new HashMap<>(3);
        payIdAndProjectIdAndPeriodNum.put("PROJECT_ID", projectId);
        payIdAndProjectIdAndPeriodNum.put("PAY_ID", payId);
        payIdAndProjectIdAndPeriodNum.put("PERIOD_NUM", null);
        Dao.delete(MAPPER + "deleteRentDetailByPayIdAndPeriodNum", payIdAndProjectIdAndPeriodNum);
        Dao.delete(MAPPER + "deleteBeginningByProjectIdAndPeriodNum", payIdAndProjectIdAndPeriodNum);

        // 插入新的首付数据
        List<Map<String, Object>> downPayments = Dao.selectList(MAPPER + "selectDownPaymentsByProjectId", projectId);
        for(Map<String, Object> downPayment : downPayments){
            Dao.insert(MAPPER + "insertRentDetail", downPayment);
            Dao.insert(MAPPER + "insertBeginning", downPayment);
        }
    }

    /**
     * 同步跟指定项目id相关的方案表数据到租金计划表
     *
     * @param projectId 项目id
     */
    private void synSchemeToRentHead(String projectId) {
        // 从方案表中查询用于同步的数据
        try {
            Map<String, Object> rentHeadMap = Dao.selectOne(MAPPER + "selectRentHeadByProjectId", projectId);
            rentHeadMap.put("projectId", projectId);
            Clob schemeClob = (Clob) rentHeadMap.get("SCHEME_CLOB");
            rentHeadMap.put("SCHEME_CLOB", schemeClob.getSubString(1, (int) schemeClob.length()));
            // fil_project_scheme to fil_rent_plan_head
            Dao.update(MAPPER + "updateRentHeadByProjectId", rentHeadMap);
        } catch (SQLException ignored) {

        }
    }

    /**
     * 同步前的校验
     * 检查跟指定项目id相关的设备表，租金计划表，beginning表的数据
     *
     * @param equipmentIdsCount 设备表中不同设备id的数量
     * @param payIdsCount 租金计划表中不同的PayId的数量
     * @param unsettledPayListCodesCount beginning表中不同的payListCode的数量
     * @return 是否可进行同步的标记
     */
    private boolean checkRent(int equipmentIdsCount, int payIdsCount, int unsettledPayListCodesCount) {

        // 修改后的项目包含了多辆车暂不做处理
        if(equipmentIdsCount != 1){
            logger.debug("设备表中无记录或者记录了多辆车信息，退出同步操作！");
            return false;
        }

        // 租金计划表中多个payId也是暂不做处理
        if(payIdsCount != equipmentIdsCount){
            logger.debug("租金计划表无记录或者payId数与方案子表不符，退出同步操作！");
            return false;
        }

        // beginning表中多个支付表号也暂不做处理
        if(unsettledPayListCodesCount != equipmentIdsCount){
            logger.debug("BEGINNING表中无记录或者payListCode数与方案子表不符，退出同步操作！");
            return false;
        }

        return true;
    }

}
