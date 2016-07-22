package com.pqsoft.reportexcel.action;

import com.pqsoft.entity.Excel;
import com.pqsoft.insure.util.DateUtil;
import com.pqsoft.reportexcel.service.OperationLedgerService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.*;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.ReplyExcel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理运营台账相关请求
 *
 * @author 钟林俊
 * @version V1.0 2016-07-13 13:30
 */
public class OperationLedgerAction extends Action {

    private OperationLedgerService operationLedgerService = new OperationLedgerService();

    /**
     * 跳转运营台账的列表显示页面
     *
     * @return 运营台账页面
     */
    @Override
    @aPermission(name = {"报表管理","运营台账","列表"})
    @aAuth(type = aAuth.aAuthType.USER)
    @aDev(code = "D050", email = "linjunzhong@huashenghaoche.com", name = "钟林俊")
    public Reply execute() {
        return new ReplyHtml(VM.html("reportexcel/operation_ledger.vm", null));
    }

    /**
     * 显示台账列表
     *
     * @return 台账数据
     */
    @aPermission(name = {"报表管理","运营台账","列表"})
    @aAuth(type = aAuth.aAuthType.USER)
    @aDev(code = "D050", email = "linjunzhong@huashenghaoche.com", name = "钟林俊")
    public Reply getLedgerPage(){
        Map<String, Object> params = _getParameters();
        return new ReplyAjaxPage(operationLedgerService.queryLedgerPage(params));
    }

    @aPermission(name = {"报表管理","运营台账","导出"})
    @aAuth(type = aAuth.aAuthType.USER)
    @aDev(code = "D050", email = "linjunzhong@huashenghaoche.com", name = "钟林俊")
    public Reply downloadLedger(){
        Map<String, Object> params = _getParameters();
        Excel excel = operationLedgerService.transformExcel(removeEmpty(params));
        if(excel != null){
            return new ReplyExcel(excel, "XS" + DateUtil.format(new Date(), "yyyyMMdd")  +"运营台账"+ ".xls");
        }
        throw new ActionException("没有可用的数据！");
    }

    private Map<String,Object> removeEmpty(Map<String, Object> params) {
        Map<String, Object> notEmpty = new HashMap<>();
        Object value;
        for(Map.Entry<String, Object> entry : params.entrySet()){
            value = entry.getValue();
            if(value == null || "".equals(value.toString())){
                continue;
            }
            notEmpty.put(entry.getKey(), value);
        }
        return notEmpty;
    }
}
