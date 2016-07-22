package com.pqsoft.newRentWrite.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.newRentWrite.service.financeExportService;
import com.pqsoft.newRentWrite.service.rentAccountMonitoringService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.FiEbankUtil;
import com.pqsoft.util.ReplyExcel;

public class rentAccountMonitoringAction  extends Action {

    public VelocityContext context = new VelocityContext();

    private FiEbankUtil util = new FiEbankUtil();

    rentAccountMonitoringService service = new rentAccountMonitoringService();

    @Override
    public Reply execute() {
        // TODO Auto-generated method stub
        return null;
    }

    // 租金台账监控入口
    @aPermission(name = {"报表管理", "租金台账监控", "列表"})
    @aDev(code = "170051", email = "liliu@jiezhongchina.com.cn", name = "刘丽")
    @aAuth(type = aAuthType.USER)
    public Reply rentAccountMonitorPage() {

        Map map = this._getParameters();

        map.put("USERCODE", Security.getUser().getCode());// 获取登陆人编号
        map.put("USERNAME", Security.getUser().getName());// 获取登陆人

        // // 开户行所属总行
        // List<Object> BANK_NAME_LIST = (List) new DataDictionaryMemcached().get("开户行所属总行");
        // context.put("BANK_NAME_LIST", BANK_NAME_LIST);
        //
        // // ITEM_FLAG_LIST
        // List<Object> ITEM_FLAG_LIST = (List) new DataDictionaryMemcached().get("租金类别");
        // context.put("ITEM_FLAG_LIST", ITEM_FLAG_LIST);
        // 业务类型
        List<Object> YW_TYPE_LIST = (List) new SysDictionaryMemcached().get("业务类型");
        context.put("YW_TYPE_LIST", YW_TYPE_LIST);
        return new ReplyHtml(VM.html("newRentWrite/rentAccountMonitoring.vm", context));
        
       
      
    }

    // 查询按钮
    @aPermission(name = {"报表管理", "租金台账监控", "查询"})
    @aDev(code = "170051", email = "liliu@jiezhongchina.com.cn", name = "刘丽")
    @aAuth(type = aAuthType.ALL)
    public Reply query() {
        Map<String, Object> param = _getParameters();
        System.out.println(param+"--------");
        Page page = service.query(param);
        return new ReplyAjaxPage(page);
    }

    // 导出下载
    @aPermission(name = {"报表管理", "租金台账监控", "下载"})
    @aDev(code = "170051", email = "liliu@jiezhongchina.com.cn", name = "刘丽")
    @aAuth(type = aAuthType.ALL)
    public Reply excelUpload() {
        Map map = _getParameters();
        if (map != null && map.get("exportType") != null && !map.get("exportType").equals("")) {
            String uploadType = map.get("exportType").toString();
            List listReturn = new ArrayList();
            if (uploadType.equals("all")) {
                listReturn = service.excelAll(map);
            }
            map.put("dataList", listReturn);
            String SHH = "00000001";// 商户号
            String BS = "S";// 标示，S为收款
            String FGF = "_";// 分割符
            String BBH = "02";// 版本号
            String DA = DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "");
            // excel导出
            String no = new FiEbankUtil().getExportNo("租金监控");
            return new ReplyExcel(util.exportRentAccountMonitoring(map), "rent" + DateUtil.getSysDate("yyyy-MM-dd").replaceAll("-", "") + no + ".xls");// 山重导出
        }
        return this.rentAccountMonitorPage();
    }

}
