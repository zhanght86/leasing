package com.pqsoft.payment.service;

import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessInstance;

import com.allinpay.xmltrans.service.TranxServiceImpl;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.entity.Excel;
import com.pqsoft.fi.service.FundAutoService;
import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateSiteUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FIinterface;
import com.pqsoft.util.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PaymentApplyService {

    public Page getPage(Map<String, Object> param) {
        return PageUtil.getPage(param, "paymentApply.getPageList", "paymentApply.getPageCount");
    }

    public Map<String, Object> getpayment(Map<String, Object> param) {
        return Dao.selectOne("paymentApply.getpayment", param);
    }

    // 客户
    public Map<String, Object> getProject(Map<String, Object> param) {
        return Dao.selectOne("paymentApply.getProject", param);
    }

    public List<Map<String, Object>> getPeople(Map<String, Object> param) {
        return Dao.selectList("paymentApply.getPeople", param);
    }

    // 设备
    public List<Map<String, Object>> getEq(Map<String, Object> param) {
        return Dao.selectList("paymentApply.getEq", param);
    }

    // 收款银行
    public List<Map<String, Object>> PaymentBank() {
        return Dao.selectList("paymentApply.PaymentBank");
    }

    // 付款银行
    public Map<String, Object> ReceivablesBank(Map<String, Object> param) {
        List<Map<String, Object>> ywlx = new SysDictionaryMemcached().get("业务类型");
        Map<String, Object> map = new HashMap<String, Object>();
        map = Dao.selectOne("paymentApply.ReceivablesBank", param);
        Map<String, Object> m = new HashMap<String, Object>();
        for (int i = 0; i < ywlx.size(); i++) {
            m = ywlx.get(i);
            if (m.get("CODE").toString().equals(param.get("PLATFORM_TYPE").toString()) && m.get("FLAG").toString().contains("标准型售后回租")) {
                map = Dao.selectOne("paymentApply.ReceivablesBank1", param);
            }
        }

        return map;
    }

    public int UpdPayment(Map<String, Object> param) {
        return Dao.update("paymentApply.UpdPayment", param);
    }

    public Map<String, Object> payMent_C_Submit(Map<String, Object> mapDate) {
        String PAYMENT_HEAD_ID = Dao.getSequence("SEQ_FI_FUND_PAYMENT_HEAD");
        String ID = "";
        mapDate.put("payMent_ID", mapDate.get("PAYMENT_ID"));
        String Time = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        mapDate.put("BEGGIN_DATE", Time);
        mapDate.put("PROJECT_NUM", 1);
        mapDate.put("TYPE", 1);
        Map<String, Object> m = mapDate;
        System.out.println(m + "===========");
        // 将收款单的ID保存到细表中
        if (m != null && m.get("payMent_ID") != null && !m.get("payMent_ID").equals("") && !m.get("payMent_ID").equals("0")) {
            m.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
            ID = m.get("payMent_ID").toString();
            Dao.update("payment.updatePaymentDetailPayID", m);
            Dao.update("payment.updateBuyContractType", m);// 更新买卖合同修改状态5-14
        }
        // 查询第一个勾选数据的基本信息
        Map<String, Object> map = new HashMap<String, Object>();
        if (ID != null && !ID.equals("")) {
            map.put("pay_ID", ID);
            map = Dao.selectOne("payment.queryPayMentDeatelByPayID", map);
        }
        mapDate.putAll(map);
        mapDate.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
        // 应收单编号
        Map<String, Object> paycodeMap = Dao.selectOne("payment.PayCodeCreate");
        mapDate.put("PAYMENT_CODE", paycodeMap.get("PAYMENT_CODE"));

        // 当前登录人ID
        mapDate.put("USER_ID", Security.getUser().getId());
        // 组织机构应该取缓存 后面在改
        Map<String, Object> ORG_MAP = Dao.selectOne("payment.orgListByUserId", mapDate);
        if (ORG_MAP != null) {
            mapDate.put("ORG_ID", ORG_MAP.get("ORG_ID"));
        }
        Map<String, Object> mapbank = Dao.selectOne("paymentApply.getPaymentBank", mapDate);
        if (mapbank != null) {
            mapDate.putAll(mapbank);
        }
        Dao.insert("payment.createPayMentHead", mapDate);
        Map<String, Object> ffph = Dao.selectOne("paymentApply.getffph", PAYMENT_HEAD_ID);
        return ffph;
    }

    public Map<String, Object> PayHead_Status_Submit(Map<String, Object> mapDate) {

        // 将多个核销单发起一个流程
        // String JBPM_ID=Dao.getSequence("SEQ_PAYMENT_HEAD_JBPM");
        String SUPPER_ID = "";
        String ID = null;
        Map<String, Object> m = mapDate;
        // 将收款单的ID保存到细表中

        if (m != null && m.get("SUPPER_ID") != null && !m.get("SUPPER_ID").equals("") && !m.get("SUPPER_ID").equals("")) {
            SUPPER_ID = m.get("SUPPER_ID").toString();
        }

        Dao.update("payment.updatePayDetailStatusForSubmit", m);// 修改细表已核销状态

        if (mapDate != null && mapDate.get("FLAG") != null) {
            String FLAG = mapDate.get("FLAG").toString();
            // 货款支付申请
            if (FLAG.equals("1")) // 设备款发起流程
            {
                // List<String> jbpmList=JBPM.getDeploymentListByModelName("purchasePricePaid");
                List<String> jbpmList =
                    JBPM.getDeploymentListByModelName("付款审批流程", mapDate.get("PLATFORM_TYPE").toString(), Security.getUser().getOrg().getPlatformId());
                if (!jbpmList.isEmpty() && jbpmList.size() > 0) {
                    Map<String, Object> jm = new HashMap<String, Object>();
                    jm.put("PAYMENT_JBPM_ID", mapDate.get("ID") + "");
                    jm.put("SUPPLIER_ID", SUPPER_ID);
                    jm.put("PROJECT_ID", mapDate.get("PROJECT_ID") + "");
                    jm.put("CLIENT_ID", mapDate.get("CLIENT_ID") + "");
                    jm.put("PROVINCE_NAME", mapDate.get("PROVINCE_NAME"));
                    List<Map<String, Object>> projectscheme = Dao.selectList("project.querySechmeByProjectID", mapDate);
                    Map<String, Object> mapscheme = projectscheme.get(0);
                    if (mapscheme != null && mapscheme.containsKey("SFBZHT")) {
                        jm.put("SFBZHT", mapscheme.get("SFBZHT"));
                    } else {
                        jm.put("SFBZHT", "");
                    }
                    ProcessInstance instance = JBPM.startProcessInstanceById(jbpmList.get(0), mapDate.get("PROJECT_ID").toString(),
                        mapDate.get("CLIENT_ID").toString(), mapDate.get("PAY_ID").toString(), jm);
                    mapDate.put("JBPM_ID", instance.getId());
                }
            }
        }
        if (m != null && m.get("ID") != null && !m.get("ID").equals("") && !m.get("ID").equals("0")) {
            m.put("STATUS", mapDate.get("STATUS"));
            m.put("JBPM_ID", mapDate.get("JBPM_ID"));
            Dao.update("payment.PayHead_Status", m);
            ID = m.get("ID") + "";
        }
        return mapDate;
    }

    public int savePaymentBank(Map<String, Object> param) {
        return Dao.update("paymentApply.updBank", param);
    }

    public int updBank(Map<String, Object> param) {
        int result = 0;
        if ("1".equals(param.get("ISDEKOU").toString())) {
            param.put("IS_DIFFERENCE", 5);
        } else if ("0".equals(param.get("ISDEKOU").toString())) {
            param.put("IS_DIFFERENCE", 1);
        }
        Dao.update("paymentApply.updbeginDifference", param);
        Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE", param);
        JSONArray json = JSONArray.fromObject(param.get("ARR"));
        Map<String, Object> map = new HashMap<String, Object>();
        Dao.delete("paymentApply.delLoanBank", param);

        for (int i = 0; i < json.size(); i++) {
            map = (Map<String, Object>) json.get(i);
            map.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
            map.put("SUP_ID", param.get("SUP_ID"));
            // map.put("MC", param.get("MC"));

            map.put("COUSTNAME", map.get("MC"));
            map.put("PROJECT_ID", param.get("PROJECT_ID"));

            // 如果map对象的值是从表中取的值，那么直接更新，如果不是，则新增
            if (null != map.get("addPay") && "1".equals(map.get("addPay").toString())) {

                // 查询出表中已存在的费用明细
                Map<String, Object> cpMap = Dao.selectOne("paymentApply.findXmmxlistByCP", map);

                if (null != cpMap) {
                    map.put("TYPE_ID", cpMap.get("TYPE_ID"));
                    Dao.update("paymentApply.updateProjectDetail", map);
                }

            } else {

                // 根据码表属性名称查询出码表TYPEID
                Map<String, Object> cpMap = Dao.selectOne("paymentApply.findXmmxBaseByCN", map);

                if (null != cpMap) {
                    map.put("TYPE_ID", cpMap.get("TYPEID"));
                    map.put("IS_PAY", "1");
                    Dao.insert("paymentApply.addProjectDetail", map);
                }

            }

            if (i == 0) {
                map.put("PAYMENT_ID", param.get("PAYMENT_ID"));
                map.put("REALITY_BANK_NAME", param.get("FA_BINK") + "(" + param.get("FA_ACCOUNT") + ")");
                param.put("LAST_MONEY", map.get("MONEY"));
                map.put("APPLY_MONEY_SUM", param.get("APPLY_MONEY_SUM"));
                result = Dao.update("paymentApply.updBank1", map);
            }
            if (!"0".equals(map.get("MONEY").toString())) {
                Dao.insert("paymentApply.insLoanBank", map);
            }
        }
        Dao.update("paymentApply.updBank", param);
        return result;
    }

    public Excel exportData() {
        Map<String, Object> dataLi = Dao.selectOne("paymentApply.exportExcelcount");
        List<Map<String, Object>> dataList = Dao.selectList("paymentApply.exportExcel");
        dataList.add(dataLi);
        // 表头
        // String aaa="审核方式,1";
        String col = "审核方式,总金额,总笔数";
        String column = "SHENHE,ZBS,ZJE";
        // String[] abc=aaa.split(",");
        String[] coll = col.split(",");
        String[] columnn = column.split(",");
        String columnString = "制单类型,企业自制凭证号,客户号,预约标志,付款账号,交易金额,收款账号,收款人姓名,收款账户类型,子客户号,子付款账号,子付款账户名,子付款账户开户行名,用途,汇路,是否通知收款人,手机号码,邮箱,支付行号&支付行名称";
        String columnName =
            "ZDLX,NAME,YLBZ,REALITY_BANK_NAME,PAY_MONEY,PAY_BANK_ACCOUNT,SUP_NAME,ZHLX,ZKHH,ZFKZH,ZFKZHM,ZFKKHH,YT,HL,ISTZ,PHONE,EMAIL,ZFHHZFHM";
        String[] columns = columnString.split(",");
        String[] columnNames = columnName.split(",");
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        if (col != null) {
            for (int i = 0; i < columnn.length; i++) {
                // title.put(abc[0], abc[1]);
                title.put(columnn[i], coll[i]);
                for (int j = 0; j < columnNames.length; j++) {
                    title.put(columnNames[j], columns[j]);
                }
            }

        }

        // 封装excel
        Excel excel = new Excel();
        excel.addTitle(title);
        excel.setName("workFlow" + DateUtil.getSysDate("yyyymmddss") + ".xls");
        excel.addSheetName("sheet01");
        excel.addData(dataList);
        return excel;
    }

    public String getFund(Map<String, Object> m) {
        // Map<String,Object> headfund=Dao.selectOne("paymentApply.getheadfundId",m);
        // if(headfund !=null && headfund.containsKey("FUNDID") && !"".equals(headfund.get("FUNDID").toString()) &&
        // !"0".equals(headfund.get("FUNDID").toString()))
        // {
        // return "核销首期款还未完成";
        // }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> fund = Dao.selectOne("paymentApply.getFund", m);
        if (fund != null) {
            fund.put("KEY_NAME_EN", "YSZJQC");
            Map<String, Object> money = Dao.selectOne("paymentApply.getSqk", fund);
            Map<String, Object> yszj = Dao.selectOne("paymentApply.getFkbzj", fund);
            Map<String, Object> ysmoney = null;
            if (yszj != null && !"0".equals(yszj.get("VALUE_STR").toString())) {
                if (Integer.parseInt(fund.get("LEASE_TERM").toString()) > Integer.parseInt(yszj.get("VALUE_STR").toString())) {
                    fund.put("YSZJQC", yszj.get("VALUE_STR").toString());
                } else {
                    fund.put("YSZJQC", fund.get("LEASE_TERM").toString());
                }
                ysmoney = Dao.selectOne("paymentApply.getYs", fund);
            }
            if ("0".equals(money.get("MONEY").toString())) {
                // 预收租金
                if (ysmoney != null) {
                    m.put("WDS", ysmoney.get("MONEY"));
                    Map<String, Object> ysfund = Dao.selectOne("paymentApply.selparagraph", m);
                    if (ysfund != null) {
                        fund.put("FUND_ACCEPT_DATE", df.format(new Date()));
                        Dao.update("paymentApply.updSqkDate", fund);
                        Dao.update("paymentApply.updSqkDate1", fund);
                        Dao.update("paymentApply.updSqkDate2", fund);
                        Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE", fund);
                        new rentWriteNewService().tqHzByPeriodBefore(ysfund.get("FUND_ID").toString(), fund.get("PAYLIST_CODE").toString(),
                            Integer.parseInt(fund.get("YSZJQC").toString()));
                    }
                }
                return "首期款已核销";
            }
            if ("0".equals(m.get("WDS").toString())) {
                String FUND_ID = Dao.selectOne("fi.fund.getFundId");
                fund.put("COMPANY", "代收");
                fund.put("FUND_ID", FUND_ID);
                fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
                fund.put("FUND_ACCEPT_DATE", df.format(new Date()));
                fund.put("FUND_IMPORT_PERSON", "系统");
                fund.put("FUND_IMPORT_PERSON_ID", "1");
                fund.put("FUND_DOCKET", "首期款代收部分,系统自动生成一笔来款");
                m.put("FUND_ID", FUND_ID);
                Dao.update("paymentApply.updFundIdPayment", m);
                fund.put("FUND_SY_MONEY", fund.get("FUND_RECEIVE_MONEY"));
                Dao.insert("paymentApply.insFund", fund);
                Dao.update("paymentApply.updSqkDate", fund);
                Dao.update("paymentApply.updSqkDate1", fund);
                Dao.update("paymentApply.updSqkDate2", fund);
                Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE", fund);
                new FundAutoService().autoSQK(fund);
                return "首期款核销完成";
            } else {
                // 查询承租人来款大于未代收的资金
                Map<String, Object> paragraph = Dao.selectOne("paymentApply.selparagraph", m);
                if (paragraph != null) {
                    String FUND_ID = Dao.selectOne("fi.fund.getFundId");
                    fund.put("COMPANY", "代收");
                    fund.put("FUND_ID", FUND_ID);
                    fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
                    fund.put("FUND_ACCEPT_DATE", df.format(new Date()));
                    fund.put("FUND_IMPORT_PERSON", "系统");
                    if (ysmoney != null) {
                        fund.put("FUND_RECEIVE_MONEY", Float.parseFloat(money.get("MONEY").toString())
                            + Float.parseFloat(ysmoney.get("MONEY").toString()) - Float.parseFloat(m.get("WDS").toString()));
                    } else {
                        fund.put("FUND_RECEIVE_MONEY", Float.parseFloat(money.get("MONEY").toString()) - Float.parseFloat(m.get("WDS").toString()));
                    }
                    fund.put("FUND_IMPORT_PERSON_ID", "1");
                    m.put("FUND_ID", FUND_ID);
                    Dao.update("paymentApply.updFundIdPayment", m);
                    if (ysmoney != null) {
                        fund.put("FUND_SY_MONEY", Float.parseFloat(money.get("MONEY").toString()) + Float.parseFloat(ysmoney.get("MONEY").toString())
                            - Float.parseFloat(m.get("WDS").toString()));
                    } else {
                        fund.put("FUND_SY_MONEY", Float.parseFloat(money.get("MONEY").toString()) - Float.parseFloat(m.get("WDS").toString()));
                    }
                    fund.put("FUND_DOCKET", "首期款代收部分,系统自动生成一笔来款");
                    Dao.update("paymentApply.updSqkDate", fund);
                    Dao.update("paymentApply.updSqkDate1", fund);
                    Dao.update("paymentApply.updSqkDate2", fund);
                    Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE", fund);
                    System.out.println(fund + "=================");
                    if (Float.parseFloat(fund.get("FUND_SY_MONEY").toString()) > 0) {
                        Dao.insert("paymentApply.insFund", fund);
                        fund.put("SQK", "SQK");
                        new FundAutoService().autoSQK(fund);
                    } else {
                        paragraph.put("SQK", "SQK");
                        new FundAutoService().autoSQK(paragraph);
                    }
                    if (yszj != null && !"0".equals(yszj.get("VALUE_STR").toString())) {
                        paragraph.put("YSZJQC", yszj.get("VALUE_STR").toString());
                        paragraph.remove("SQK");
                        new FundAutoService().autoSQK(paragraph);
                    }
                    return "首期款核销完成";
                } else {
                    return "请录入来款";
                }
            }
        }
        return "没有首期款";
        // m.put("FUND_ID", "0");
        // Dao.update("paymentApply.updFundIdPayment",m);
        // if(fund !=null){
        // Map<String,Object> map=new HashMap<String,Object>();
        // List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        // Map<String,Object> money=Dao.selectOne("paymentApply.getSqk", fund);
        // if(money!=null)
        // {
        // Map<String,Object> map1=Dao.selectOne("paymentApply.tranxgetPaymentId",m);
        // m.put("ORGANIZATION_ID", map1.get("FHFA_ID"));
        // Map<String,Object> fund =Dao.selectOne("paymentApply.getFund",m);
        // String FUND_ID=map.get("SN").toString();
        // String FUND_ID=Dao.selectOne("fi.fund.getFundId");
        // fund.put("COMPANY",map1.get("FA_NAME"));
        // fund.put("FUND_ID", FUND_ID);
        // fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // fund.put("FUND_ACCEPT_DATE",df.format(new Date()));
        // fund.put("FUND_IMPORT_PERSON", "系统");
        // fund.put("FUND_IMPORT_PERSON_ID", "1");
        // System.out.println(fund+"+======================");
        // Dao.insert("paymentApply.insFund",fund);
        // Dao.update("paymentApply.updSqkDate",fund);
        // Dao.update("paymentApply.updSqkDate1",fund);
        // Dao.update("paymentApply.updSqkDate2",fund);
        // Map<String,Object> mapDate=new HashMap<String,Object>();
        // m.put("PAYLIST_CODE", fund.get("PAYLIST_CODE"));
        // Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE",fund);
        // new FundAutoService().autoSQK(fund);
        // m.put("FUND_ID", fund.get("FUND_ID"));
        // Dao.update("paymentApply.updFundIdPayment",m);
        // return "首期款已核销";
        // if("0".equals(money.get("MONEY").toString()))
        // {
        // return "没有首期款";
        // }else if("0".equals(m.get("WDS").toString())){
        // String FUND_ID=Dao.selectOne("fi.fund.getFundId");
        // fund.put("COMPANY","代收");
        // fund.put("FUND_ID", FUND_ID);
        // fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // fund.put("FUND_ACCEPT_DATE",df.format(new Date()));
        // fund.put("FUND_IMPORT_PERSON", "系统");
        // fund.put("FUND_IMPORT_PERSON_ID", "1");
        // System.out.println(fund+"=======================");
        // Dao.insert("paymentApply.insFund",fund);
        // Dao.update("paymentApply.updSqkDate",fund);
        // Dao.update("paymentApply.updSqkDate1",fund);
        // Dao.update("paymentApply.updSqkDate2",fund);
        // Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE",fund);
        // new FundAutoService().autoSQK(fund);
        // return "首期款已核销";
        // }else
        // {
        // String FUND_ID=Dao.selectOne("fi.fund.getFundId");
        // map.put("SN", FUND_ID);
        // map.put("CUST_USERID", fund.get("FUND_CLIENT_ID"));
        // map.put("ACCOUNT_TYPE", fund.get("ACCOUNT_TYPE"));
        // map.put("ACCOUNT_NO", fund.get("FUND_COMECODE"));
        // map.put("ACCOUNT_PROP", fund.get("ACCOUNT_PROP"));
        // map.put("ACCOUNT_NAME", fund.get("BANK_CUSTNAME"));
        // map.put("AMOUNT", m.get("WDS"));
        // map.put("CURRENCY", fund.get("CURRENCY"));
        // map.put("REMARK", fund.get("PAYLIST_CODE"));
        // map.put("BANK_NAME", fund.get("BANK_NAME"));
        // map.put("FFPDID", fund.get("FFPDID"));
        // list.add(map);
        // map=new HashMap<String,Object>();
        // map.put("TOTAL_ITEM", "1");
        // map.put("TOTAL_SUM", m.get("WDS"));
        // m.put("FUND_ID", FUND_ID);
        // Dao.update("paymentApply.updFundIdPayment",m);
        // new FIinterface().tranx(list, map,"100003");
        // return FUND_ID;
        // }
        // }
        // }
        // return "没有首期款";
    }

    public void firstReceipt() {
        List<Map<String, Object>> list = Dao.selectList("paymentApply.getfirstReceipt");
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            Dao.update("rentWriteNew.updateTranxStatus", map);
            if ("0000".equals(map.get("RETURN_STATUS").toString()) || "4000".equals(map.get("RETURN_STATUS").toString())) {
                Map<String, Object> m = Dao.selectOne("paymentApply.tranxgetPaymentId", map);
                m.put("ORGANIZATION_ID", m.get("FHFA_ID"));
                Map<String, Object> fund = Dao.selectOne("paymentApply.getFund", m);
                String FUND_ID = map.get("SN").toString();
                fund.put("COMPANY", m.get("FA_NAME"));
                fund.put("FUND_ID", FUND_ID);
                fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                fund.put("FUND_ACCEPT_DATE", df.format(new Date()));
                fund.put("FUND_IMPORT_PERSON", "系统");
                fund.put("FUND_IMPORT_PERSON_ID", "1");
                fund.put("FUND_DOCKET", "接口代扣首期款");
                Dao.insert("paymentApply.insFund", fund);
                Dao.update("paymentApply.updSqkDate", fund);
                Dao.update("paymentApply.updSqkDate1", fund);
                Dao.update("paymentApply.updSqkDate2", fund);
                // Map<String,Object> mapDate=new HashMap<String,Object>();
                Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE", m);
                new FundAutoService().autoSQK(fund);
            } else {
                Dao.update("paymentApply.updffphfindId", map);
            }
        }
    }

    /**
     * 捷翊租赁：中金接口
     * 
     * @author wanghl
     * @datetime 2015年8月14日,上午9:31:10
     */
    public void firstReceipt_JYZL() {
        List<Map<String, Object>> list = Dao.selectList("paymentApply.getfirstReceipt_JYZL");
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            Dao.update("rentWriteNew.updateZhongjinStatus", map);
            // 30表示代扣或者代付成功
            if ("30".equals(map.get("RETURN_STATUS").toString())) {
                Map<String, Object> m = Dao.selectOne("paymentApply.tranxgetPaymentId_JYZL", map);
                m.put("ORGANIZATION_ID", m.get("FHFA_ID"));
                Map<String, Object> fund = Dao.selectOne("paymentApply.getFund", m);
                String FUND_ID = map.get("ITEM_NO").toString();
                fund.put("COMPANY", m.get("FA_NAME"));
                fund.put("FUND_ID", FUND_ID);
                fund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                fund.put("FUND_ACCEPT_DATE", df.format(new Date()));
                fund.put("FUND_IMPORT_PERSON", "系统");
                fund.put("FUND_IMPORT_PERSON_ID", "1");
                fund.put("FUND_DOCKET", "接口代扣首期款");
                Dao.insert("paymentApply.insFund", fund);
                Dao.update("paymentApply.updSqkDate", fund);
                Dao.update("paymentApply.updSqkDate1", fund);
                Dao.update("paymentApply.updSqkDate2", fund);
                Map<String, Object> mapDate = new HashMap<String, Object>();
                Dao.update("rentWriteNew.doPRC_BE_QJL_PAY_CODE", m);
                new FundAutoService().autoSQK(fund);
            } else {
                Dao.update("paymentApply.updffphfindId_JYZL", map);
            }
        }
    }

    /**
     * 捷翊租赁：中金接口
     * 
     * @throws Exception
     * @author wanghl
     * @datetime 2015年8月14日,上午11:15:53
     */
    public void paymentReceipt_JYZL() throws Exception {
        List<Map<String, Object>> list = Dao.selectList("paymentApply.getfirstReceipt1_JYZL");
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            Dao.update("rentWriteNew.updateZhongjinStatus", map);
            if ("30".equals(map.get("RETURN_STATUS").toString())) {
                Dao.update("paymentApply.updBanklendState", map);
                final Map projectMap = Dao.selectOne("paymentApply.selClob", map);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Class<?> cla = Class.forName("com.pqsoft.bpm.status.ProjectStatus");
                            Method method = cla.getMethod("SendNotification", String.class, String.class, String.class);
                            Object o = cla.newInstance();
                            method.invoke(o, projectMap.get("ID").toString(), "还款通知书", "放款审批流程");
                        } catch (Exception e) {
                            Log.error(e, e);
                        } finally {
                            Dao.commit();
                            Dao.close();
                        }
                    }
                }).start();
                List mapPayList = Dao.selectList("payment.queryPaymentStartDayByID", projectMap);
                for (int ii = 0; ii < mapPayList.size(); ii++) {
                    Map mapPay = (Map) mapPayList.get(ii);
                    if (mapPay != null && (mapPay.get("START_DATE") == null || mapPay.get("START_DATE").equals(""))) {
                        paymentService paymentSer = new paymentService();
                        try {
                            // 先查询该项目中金融产品配置的是否差额利息（SFCELX）,如果没有配置或者停用状态或者配置的为Y则计算否则不计算
                            Map ISCELXMap = Dao.selectOne("PayTask.querySchemeSFCELXByPay", mapPay);
                            if (ISCELXMap != null && ISCELXMap.get("VALUE_STR") != null && ISCELXMap.get("VALUE_STR").equals("3")) {

                                DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null, true);// 参数PAY_ID,3固定为放款日期类型标示

                            } else {

                                // 根据关键日期管理查询出还款日和起租日
                                DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null, false);// 参数PAY_ID,3固定为放款日期类型标示

                                Map<String, Object> mapbase = Dao.selectOne("PayTask.getSchemeBaseInfoByProjectIdINT", mapPay);

                                // 查询起租日和还款日
                                Map dataMap = Dao.selectOne("PayTask.queryPayDataByPayId", mapPay);

                                if (mapbase != null) {
                                    mapbase.put("SCHEME_ID", mapbase.get("ID"));
                                    List<Map<String, Object>> clobList = Dao.selectList("leaseApplication.queryfil_scheme_clobForCs", mapbase);
                                    for (Map<String, Object> map2 : clobList) {
                                        mapbase.put(map2.get("KEY_NAME_EN") + "", map2.get("VALUE_STR"));
                                    }

                                    try {
                                        mapbase.put("AMOUNT", Dao.selectInt("PayTask.queryAmountCount", mapPay));
                                    } catch (Exception e) {
                                    }

                                    mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
                                    mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
                                    mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
                                    mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
                                    mapbase.put("pay_way", mapbase.get("PAY_WAY"));

                                    mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
                                    mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
                                    mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
                                    mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));

                                    mapbase.put("START_DATE", dataMap.get("START_DATE"));
                                    mapbase.put("REPAYMENT_DATE", dataMap.get("REPAYMENT_DATE"));

                                    JSONObject page = null;
                                    Class<?> cla = Class.forName("com.pqsoft.pay.service.PayTaskService");
                                    page = (JSONObject) cla.getMethod("quoteCalculateTest", Map.class).invoke(cla.newInstance(), mapbase);
                                    List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");

                                    double ZJHJ = 0.00;// 租金合计
                                    for (int hh = 0; hh < irrList.size(); hh++) {
                                        // "zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
                                        Map mapIrr = irrList.get(hh);
                                        if (mapIrr != null) {
                                            mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));

                                            ZJHJ = ZJHJ + Double
                                                .parseDouble((mapIrr.get("zj") != null && mapIrr.get("zj") != "") ? mapIrr.get("zj") + "" : "0");
                                            // 更新租金
                                            mapIrr.put("ITEM_NAME", "租金");
                                            mapIrr.put("ITEM_MONEY", (mapIrr.get("zj") != null && mapIrr.get("zj") != "") ? mapIrr.get("zj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新PMT租金
                                            mapIrr.put("ITEM_NAME", "PMT租金");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("PMTzj") != null && mapIrr.get("PMTzj") != "") ? mapIrr.get("PMTzj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新本金
                                            mapIrr.put("ITEM_NAME", "本金");
                                            mapIrr.put("ITEM_MONEY", (mapIrr.get("bj") != null && mapIrr.get("bj") != "") ? mapIrr.get("bj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新利息
                                            mapIrr.put("ITEM_NAME", "利息");
                                            mapIrr.put("ITEM_MONEY", (mapIrr.get("lx") != null && mapIrr.get("lx") != "") ? mapIrr.get("lx") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新剩余本金
                                            mapIrr.put("ITEM_NAME", "剩余本金");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("sybj") != null && mapIrr.get("sybj") != "") ? mapIrr.get("sybj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新管理费
                                            mapIrr.put("ITEM_NAME", "管理费");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("glf") != null && mapIrr.get("glf") != "") ? mapIrr.get("glf") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新手续费
                                            mapIrr.put("ITEM_NAME", "手续费");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("sxf") != null && mapIrr.get("sxf") != "") ? mapIrr.get("sxf") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新利息增值税
                                            mapIrr.put("ITEM_NAME", "利息增值税");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("lxzzs") != null && mapIrr.get("lxzzs") != "") ? mapIrr.get("lxzzs") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                        }

                                    }

                                    // 更新还款计划主表租金合计
                                    Map pmap = new HashMap();
                                    pmap.put("ID", mapPay.get("PAY_ID"));
                                    pmap.put("ZJHJ", ZJHJ);
                                    Dao.update("PayTask.updatePayHeadMoneyAll", pmap);

                                    System.out.println("------------------------dataMap=" + dataMap);
                                    // 同步应收期初表数据
                                    // 同步财务数据
                                    Map<String, String> temp = new HashMap<String, String>();
                                    temp.put("PAY_ID", mapPay.get("PAY_ID") + "");
                                    temp.put("PAYLIST_CODE", dataMap.get("PAYLIST_CODE") + "");
                                    temp.put("PMT", "PMT租金");
                                    temp.put("ZJ", "租金");
                                    temp.put("SYBJ", "剩余本金");
                                    temp.put("D", "第");
                                    temp.put("QI", "期");
                                    // 删除财务表数据
                                    Dao.delete("PayTask.deleteBeginning", temp);
                                    Dao.insert("PayTask.synchronizationBeginning", temp);

                                    // 同步中间表
                                    // 刷单条逾期数据
                                    Dao.update("PayTask.doDunDateByPaylist_code", temp);
                                    Dao.update("PayTask.doPRC_BE_QJL_PAY_CODE", temp);

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                Map<String, Object> fkje = new HashMap<String, Object>();
                Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
                String str = null;
                try {
                    str = clob.getSubString(1, (int) clob.length());
                    JSONArray SCHEME_CLOB = JSONArray.fromObject(str);
                    for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
                        Map mClob = (Map) iterator.next();
                        if ((mClob.get("KEY_NAME_EN").toString()).contains("MONEY") || (mClob.get("KEY_NAME_EN").toString()).contains("PRICE")) {
                            if (!"".equals(mClob.get("VALUE_STR").toString())) {
                                fkje = new HashMap<String, Object>();
                                fkje.put("FFPDID", map.get("FFPDID"));
                                fkje.put("KEY_NAME_ZN", mClob.get("KEY_NAME_ZN").toString());
                                fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
                                fkje.put("DSFS", mClob.get("DSFS"));
                                fkje.put("FYGS", mClob.get("FYGS"));
                                Dao.insert("paymentApply.insCostDetailed", fkje);
                            }
                        }
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void paymentReceipt() {
        List<Map<String, Object>> list = Dao.selectList("paymentApply.getfirstReceipt1");
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            Dao.update("rentWriteNew.updateTranxStatus", map);
            if ("0000".equals(map.get("RETURN_STATUS").toString()) || "4000".equals(map.get("RETURN_STATUS").toString())) {
                Dao.update("paymentApply.updBanklendState", map);
                Map<String, Object> projectMap = Dao.selectOne("paymentApply.selClob", map);
                List<Map<String, Object>> mapPayList = Dao.selectList("payment.queryPaymentStartDayByID", projectMap);
                for (int ii = 0; ii < mapPayList.size(); ii++) {
                    Map<String, Object> mapPay = mapPayList.get(ii);
                    if (mapPay != null && (mapPay.get("START_DATE") == null || mapPay.get("START_DATE").equals(""))) {
                        paymentService paymentSer = new paymentService();
                        try {
                            // 先查询该项目中金融产品配置的是否差额利息（SFCELX）,如果没有配置或者停用状态或者配置的为Y则计算否则不计算
                            Map ISCELXMap = Dao.selectOne("PayTask.querySchemeSFCELXByPay", mapPay);
                            if (ISCELXMap != null && ISCELXMap.get("VALUE_STR") != null && ISCELXMap.get("VALUE_STR").equals("3")) {

                                DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null, true);// 参数PAY_ID,3固定为放款日期类型标示

                            } else {

                                // 根据关键日期管理查询出还款日和起租日
                                DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null, false);// 参数PAY_ID,3固定为放款日期类型标示

                                Map<String, Object> mapbase = Dao.selectOne("PayTask.getSchemeBaseInfoByProjectIdINT", mapPay);

                                // 查询起租日和还款日
                                Map dataMap = Dao.selectOne("PayTask.queryPayDataByPayId", mapPay);

                                if (mapbase != null) {
                                    mapbase.put("SCHEME_ID", mapbase.get("ID"));
                                    List<Map<String, Object>> clobList = Dao.selectList("leaseApplication.queryfil_scheme_clobForCs", mapbase);
                                    for (Map<String, Object> map2 : clobList) {
                                        mapbase.put(map2.get("KEY_NAME_EN") + "", map2.get("VALUE_STR"));
                                    }

                                    try {
                                        mapbase.put("AMOUNT", Dao.selectInt("PayTask.queryAmountCount", mapPay));
                                    } catch (Exception e) {
                                    }

                                    mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
                                    mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
                                    mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
                                    mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
                                    mapbase.put("pay_way", mapbase.get("PAY_WAY"));

                                    mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
                                    mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
                                    mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
                                    mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));

                                    mapbase.put("START_DATE", dataMap.get("START_DATE"));
                                    mapbase.put("REPAYMENT_DATE", dataMap.get("REPAYMENT_DATE"));

                                    JSONObject page = null;
                                    Class<?> cla = Class.forName("com.pqsoft.pay.service.PayTaskService");
                                    page = (JSONObject) cla.getMethod("quoteCalculateTest", Map.class).invoke(cla.newInstance(), mapbase);
                                    List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");

                                    double ZJHJ = 0.00;// 租金合计
                                    for (int hh = 0; hh < irrList.size(); hh++) {
                                        // "zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
                                        Map mapIrr = irrList.get(hh);
                                        if (mapIrr != null) {
                                            mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));

                                            ZJHJ = ZJHJ + Double
                                                .parseDouble((mapIrr.get("zj") != null && mapIrr.get("zj") != "") ? mapIrr.get("zj") + "" : "0");
                                            // 更新租金
                                            mapIrr.put("ITEM_NAME", "租金");
                                            mapIrr.put("ITEM_MONEY", (mapIrr.get("zj") != null && mapIrr.get("zj") != "") ? mapIrr.get("zj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新PMT租金
                                            mapIrr.put("ITEM_NAME", "PMT租金");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("PMTzj") != null && mapIrr.get("PMTzj") != "") ? mapIrr.get("PMTzj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新本金
                                            mapIrr.put("ITEM_NAME", "本金");
                                            mapIrr.put("ITEM_MONEY", (mapIrr.get("bj") != null && mapIrr.get("bj") != "") ? mapIrr.get("bj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新利息
                                            mapIrr.put("ITEM_NAME", "利息");
                                            mapIrr.put("ITEM_MONEY", (mapIrr.get("lx") != null && mapIrr.get("lx") != "") ? mapIrr.get("lx") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新剩余本金
                                            mapIrr.put("ITEM_NAME", "剩余本金");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("sybj") != null && mapIrr.get("sybj") != "") ? mapIrr.get("sybj") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新管理费
                                            mapIrr.put("ITEM_NAME", "管理费");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("glf") != null && mapIrr.get("glf") != "") ? mapIrr.get("glf") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新手续费
                                            mapIrr.put("ITEM_NAME", "手续费");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("sxf") != null && mapIrr.get("sxf") != "") ? mapIrr.get("sxf") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                            // 更新利息增值税
                                            mapIrr.put("ITEM_NAME", "利息增值税");
                                            mapIrr.put("ITEM_MONEY",
                                                (mapIrr.get("lxzzs") != null && mapIrr.get("lxzzs") != "") ? mapIrr.get("lxzzs") : "0");
                                            Dao.update("PayTask.updatePayDetailItemInfo", mapIrr);
                                        }

                                    }

                                    // 更新还款计划主表租金合计
                                    Map pmap = new HashMap();
                                    pmap.put("ID", mapPay.get("PAY_ID"));
                                    pmap.put("ZJHJ", ZJHJ);
                                    Dao.update("PayTask.updatePayHeadMoneyAll", pmap);

                                    System.out.println("------------------------dataMap=" + dataMap);
                                    // 同步应收期初表数据
                                    // 同步财务数据
                                    Map<String, String> temp = new HashMap<String, String>();
                                    temp.put("PAY_ID", mapPay.get("PAY_ID") + "");
                                    temp.put("PAYLIST_CODE", dataMap.get("PAYLIST_CODE") + "");
                                    temp.put("PMT", "PMT租金");
                                    temp.put("ZJ", "租金");
                                    temp.put("SYBJ", "剩余本金");
                                    temp.put("D", "第");
                                    temp.put("QI", "期");
                                    // 删除财务表数据
                                    Dao.delete("PayTask.deleteBeginning", temp);
                                    Dao.insert("PayTask.synchronizationBeginning", temp);

                                    // 同步中间表
                                    // 刷单条逾期数据
                                    Dao.update("PayTask.doDunDateByPaylist_code", temp);
                                    Dao.update("PayTask.doPRC_BE_QJL_PAY_CODE", temp);

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                Map<String, Object> fkje = new HashMap<String, Object>();
                Clob clob = (Clob) projectMap.get("SCHEME_CLOB");
                String str = null;
                try {
                    str = clob.getSubString(1, (int) clob.length());
                    JSONArray SCHEME_CLOB = JSONArray.fromObject(str);
                    for (Iterator iterator = SCHEME_CLOB.iterator(); iterator.hasNext();) {
                        Map mClob = (Map) iterator.next();
                        if ((mClob.get("KEY_NAME_EN").toString()).contains("MONEY") || (mClob.get("KEY_NAME_EN").toString()).contains("PRICE")) {
                            if (!"".equals(mClob.get("VALUE_STR").toString())) {
                                fkje = new HashMap<String, Object>();
                                fkje.put("FFPDID", map.get("FFPDID"));
                                fkje.put("KEY_NAME_ZN", mClob.get("KEY_NAME_ZN").toString());
                                fkje.put("VALUE_STR", mClob.get("VALUE_STR"));
                                fkje.put("DSFS", mClob.get("DSFS"));
                                fkje.put("FYGS", mClob.get("FYGS"));
                                Dao.insert("paymentApply.insCostDetailed", fkje);
                            }
                        }
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public Map<String, Object> queryProjectById(Map<String, Object> map) {
        map.put("tags1", "业务类型");
        map.put("tags3", "客户类型");
        map.put("tags4", "业务类型");
        Map<String, Object> m = Dao.selectOne("project.queryProjectById", map);
        if (m != null) {
            Clob clob = (Clob) m.get("IDCARD_PHOTO");
            if (clob != null) {
                try {
                    m.put("IDCARD_PHOTO", clob.getSubString(1, (int) clob.length()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return m;
    }

    public List<Map<String, Object>> queryEqByProjectIDByScheme(Map<String, Object> param) {
        param.put("tags", "设备单位");
        return Dao.selectList("project.queryEqByProjectIDByScheme", param);
    }

    public int insVinContrast(Map<String, Object> map) {
        return Dao.insert("paymentApply.insVinContrast", map);
    }

    public List<Map<String, Object>> getVinContrast(Map<String, Object> map) {
        return Dao.selectList("paymentApply.getVinContrast", map);
    }

    public List<Map<String, Object>> getVinList(Map<String, Object> map) {
        return Dao.selectList("paymentApply.getVinList", map);
    }

    public void tranxTradeNew() {
        List<Map<String, Object>> cwjk = new SysDictionaryMemcached().get("通联财务接口");
        String URLhttp11 = "";
        Map<String, Object> m = new HashMap<String, Object>();
        m = cwjk.get(0);
        URLhttp11 = m.get("CODE").toString();
        TranxServiceImpl tranxService = new TranxServiceImpl();
        boolean isfront = false;// 是否发送至前置机（由前置机进行签名）如不特别说明，商户技术不要设置为true
        List<Map<String, Object>> list = Dao.selectList("paymentApply.getAllTranxMiddle");
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            try {
                tranxService.queryTradeNew(URLhttp11, map.get("REQ_SN_MAIN").toString(), isfront, "", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 捷翊租赁:中金网银接口
    public void tranxTradeNew_JYZL() {
        // 查询批次号，银行未反馈的
        List<Map<String, Object>> list = Dao.selectList("paymentApply.getAllZhongjinMiddle");
        Map<String, Object> map = new HashMap<String, Object>();
        FIinterface fi = new FIinterface();
        for (int i = 0; i < list.size(); i++) {
            map = list.get(i);
            try {
                // 1代付 2代扣 5服务费
                if ("1".equals(map.get("TYPE").toString()) || "5".equals(map.get("TYPE").toString())) {
                    fi.queryDaifu(map.get("BATCH_NO").toString());
                } else if ("2".equals(map.get("TYPE").toString())) {
                    fi.queryDaikou(map.get("BATCH_NO").toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Object> getProByPayment_Id(Map<String, Object> map) {
        return Dao.selectOne("paymentApply.getProByPayment_Id", map);
    }

    public Map<String, Object> getCollectionMoney(Map<String, Object> map) {
        return Dao.selectOne("paymentApply.getCollectionMoney", map);
    }

    public List<Map<String, Object>> getCollectionDetailed(Map<String, Object> map) {
        return Dao.selectList("paymentApply.getCollectionDetailed", map);
    }

    public int getPaymentNum(Map<String, Object> map) {
        return Dao.selectInt("paymentApply.getPaymentNum", map);
    }

    /**
     * 根据ProjectId得到ProjectDetailBase类型和价格
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> findXmmxlist(Map<String, Object> param) {
        return Dao.selectList("paymentApply.findXmmxlist", param);
    }

    /**
     * 查询费用明细码表所有类型
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getProjectDetailBase(Map<String, Object> param) {
        return Dao.selectList("paymentApply.getProjectDetailBase");
    }

    /**
     * add by gengchangbao 2016年3月9日14:27:46
     * 查询重签合同前版【废弃】项目编号
     * 
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryParentProjectById(Map<String, Object> param) {
        return Dao.selectList("paymentApply.queryParentProjectById", param);
    }

    /**
     * 根据ProjectId获取前版已经支付的设备款
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> findReSignXmmxlist(Map<String, Object> param) {
        return Dao.selectList("paymentApply.findReSignXmmxlist", param);
    }

    /**
     * 查询放款清单列表
     * 
     * @param param
     * @date 2016年1月12日10:56:27
     * @author 邢素敏
     * @return
     */
    public Page getPaymentApplyListPage(Map<String, Object> param) {
        BaseUtil.getDataAllAuth(param);
        Page page = new Page(param);
        List<Map<String, Object>> selectList = Dao.selectList("paymentApply.getPaymentApplyListPageList", param);
        if (null != selectList && selectList.size() > 0) {
            for (int i = 0; i < selectList.size(); i++) {
                // 获取部门信息、门店信息
                Map<String, Object> m = selectList.get(i);
                if (null != m.get("ORG_ID") && m.get("ORG_ID").toString() != "") {
                    param.put("ORG_ID", m.get("ORG_ID"));
                    List<Map<String, Object>> orgList = Dao.selectList("paymentApply.getPaymentApplyOrgList", param);
                    if (null != orgList && orgList.size() > 0) {
                        for (int a = 0; a < orgList.size(); a++) {
                            if (orgList.get(a).get("NAME").toString().contains("分公司")) {
                                // 添加分公司信息
                                m.put("SUB_COMPANY_NAME", orgList.get(a).get("NAME"));
                            } else if (orgList.get(a).get("NAME").toString().contains("门店")) {
                                // 添加门店信息
                                m.put("STORE_NAME", orgList.get(a).get("NAME"));
                            }
                        }
                    }
                }
            }
        }

        page.addDate(JSONArray.fromObject(selectList), Dao.selectInt("paymentApply.getPaymentApplyListPageCount", param));
        return page;
    }

    /**
     * 放款明细导出
     * exportPaymentApplyList
     * 
     * @date 2016年1月11日18:32:54
     * @author 邢素敏
     * @return 返回值类型 Object
     * @parameter
     */
    public Excel exportPaymentApplyList(Map<String, Object> map) {
        String columnString = "确认日期,持卡人,合同编号,分公司,门店,客户,项目编号,金额,放款银行,账户类型,银行账号,放款日期,是否放款";
        String columnName =
            "APPLY_DATE,UNIT,LEASE_CODE,SUB_COMPANY_NAME,STORE_NAME,CUST_NAME,PROJECT_CODE,APPLY_MONEY_SUM,BANK,ACCOUNT_TYPE,ACCOUNT,BEGGIN_DATE,STATUS";
        String[] columns = columnString.split(",");
        String[] columnNames = columnName.split(",");
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        for (int i = 0; i < columnNames.length; i++) {
            title.put(columnNames[i], columns[i]);
        }
        List<Map<String, Object>> list = Dao.selectList("paymentApply.exportPaymentApplyList", map);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map_item = list.get(i);
            if (null != map_item.get("ORG_ID") && map_item.get("ORG_ID").toString() != "") {
                map.put("ORG_ID", map_item.get("ORG_ID"));
                List<Map<String, Object>> orgList = Dao.selectList("paymentApply.getPaymentApplyOrgList", map);
                if (null != orgList && orgList.size() > 0) {
                    for (int a = 0; a < orgList.size(); a++) {
                        if (orgList.get(a).get("NAME").toString().contains("分公司")) {
                            // 添加分公司信息
                            map_item.put("SUB_COMPANY_NAME", orgList.get(a).get("NAME"));
                        } else if (orgList.get(a).get("NAME").toString().contains("门店")) {
                            // 添加门店信息
                            map_item.put("STORE_NAME", orgList.get(a).get("NAME"));
                        }
                    }
                }
            }
        }

        // 封装excel
        Excel excel = new Excel();
        excel.setAutoColumn(15);
        excel.addTitle(title);
        excel.setName("放款清单" + DateUtil.getSysDate("yyyyMMddHHmmss") + ".xls");
        excel.addSheetName("sheet01");
        excel.addData(list);
        excel.setHeadDate(true);
        // excel.setHeadTitles("客户名："+map.get("CLIENT_NAME"));
        return excel;
    }

}
