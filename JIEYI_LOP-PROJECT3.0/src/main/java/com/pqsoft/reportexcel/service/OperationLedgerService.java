package com.pqsoft.reportexcel.service;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理运营台账相关的业务逻辑
 *
 * @author 钟林俊
 * @version V1.0 2016-07-13 13:36
 */
public class OperationLedgerService {

    private static final String MAPPER = "";

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return 分页列表
     */
    public Page queryLedgerPage(Map<String, Object> params) {
        managePageParam(params);
        return PageUtil.getPage(params, MAPPER + "selectPage", MAPPER + "countOperationLedger");
    }

    /**
     * 数据转化为Excel对象
     *
     * @param params 查询参数
     * @return excel对象
     */
    public Excel transformExcel(Map<String, Object> params){
        List<Map<String, Object>> operationLedgers = Dao.selectList(MAPPER + "selectExport", params);
        if(!CollectionUtils.isEmpty(operationLedgers)){
            Excel excel = new Excel();
            excel.setAutoColumn(25);
            excel.addSheetName("运营台账");
            excel.addData(operationLedgers);
            LinkedHashMap<String, String> title = getTitle(operationLedgers.get(0));
            excel.addTitle(title);
            excel.setHasTitle(true);
            return excel;
        }
        return null;
    }

    /**
     * 获取excel的标题容器
     *
     * @param titleSource 标题源
     * @return 标题容器
     */
    private LinkedHashMap<String,String> getTitle(Map<String, Object> titleSource) {
        LinkedHashMap<String, String> title = new LinkedHashMap<>();
        for(String key : titleSource.keySet()){
            title.put(key, key);
        }
//        title.put("进件时间", "进件时间");
//        title.put("项目号", "项目号");
//        title.put("门店", "门店");
//        title.put("分部", "分部");
//        title.put("签约主体", "签约主体");
//        title.put("客户姓名", "客户姓名");
//        title.put("身份证号", "身份证号");
//        title.put("客户手机号", "客户手机号");
//        title.put("客户来源", "客户来源");
//        title.put("厂商", "厂商");
//        title.put("品牌系列", "品牌系列");
//        title.put("型号", "型号");
//        title.put("款式", "款式");
//        title.put("规格", "规格");
//        title.put("座位数", "座位数");
//        title.put("排量", "排量");
//        title.put("变速箱", "变速箱");
//        title.put("颜色", "颜色");
//        title.put("打包价", "打包价");
//        title.put("融资额", "融资额");
//        title.put("购置税", "购置税");
//        title.put("上牌费", "上牌费");
//        title.put("GPS费", "GPS费");
//        title.put("环保标费", "环保标费");
//        title.put("临牌费", "临牌费");
//        title.put("路桥费", "路桥费");
//        title.put("交强险", "交强险");
//        title.put("商业险", "商业险");
//        title.put("车船税", "车船税");
//        title.put("市场利率", "市场利率");
//        title.put("月租", "月租");
//        title.put("租赁期数", "租赁期数");
//        title.put("首付比例", "首付比例");
//        title.put("首付", "首付");
//        title.put("保证金", "保证金");
//        title.put("预付租金", "预付租金");
//        title.put("首付合计", "首付合计");
//        title.put("客户自付差价", "客户自付差价");
//        title.put("合同号", "合同号");
//        title.put("审核日期", "审核日期");
//        title.put("签约日期", "签约日期");
//        title.put("方案名称", "方案名称");
//        title.put("开户行", "开户行");
//        title.put("", "");
//        title.put("", "");
//        title.put("", "");
//        title.put("", "");
        return title;
    }

    /**
     * 转换分页参数
     *
     * @param params 参数源
     */
    private void managePageParam(Map<String, Object> params) {
        Object page = params.get("page");
        page = page == null ? "1" : page;
        Object rows = params.get("rows");
        rows = rows == null ? "20" : rows;
        Integer pageNum;
        Integer pageSize;
        try{
            pageNum = Integer.parseInt(page.toString());
        }catch (NumberFormatException e){
            pageNum = 1;
        }

        try{
            pageSize = Integer.parseInt(rows.toString());
        }catch (NumberFormatException e){
            pageSize = 20;
        }

        Integer pageStart = (pageNum - 1) * pageSize + 1;

        params.put("pageStart", (pageNum - 1) * pageSize + 1);
        params.put("pageEnd", pageStart + pageSize);
    }

}
