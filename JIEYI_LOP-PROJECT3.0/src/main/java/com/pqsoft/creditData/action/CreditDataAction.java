package com.pqsoft.creditData.action;

import com.pqsoft.creditData.service.CreditAPIService;
import com.pqsoft.creditData.service.CreditDataService;
import com.pqsoft.creditData.service.CreditFileService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

import java.io.File;
import java.util.Map;

public class CreditDataAction extends Action {

    private CreditFileService creditFileService = CreditFileService.getInstance();
    private CreditDataService creditDataService = CreditDataService.getInstance();
    private CreditAPIService creditAPIService = CreditAPIService.getInstance();

    @Override
    @aAuth(type = aAuth.aAuthType.ALL)
    public Reply execute() {
        return null;
    }

    @aAuth(type = aAuth.aAuthType.ALL)
    @aDev(email = "linjunzhong@jiezhongchina.com", name = "钟林俊", code = "D050")
    public void generate(){
        Map<String, Object> params = _getParameters();
        try{
            Integer id = Integer.parseInt(String.valueOf(params.get("id")));
            creditFileService.generateExcelFile(creditDataService.extractCreditDataById(id));
        }catch (NumberFormatException e){
            creditFileService.generateExcelFiles(String.valueOf(params.get("pageNum")), String.valueOf(params.get("pageSize")));
        }
    }

    @aAuth(type = aAuth.aAuthType.ALL)
    @aDev(email = "linjunzhong@jiezhongchina.com", name = "钟林俊", code = "D050")
    public ReplyFile download(){
        return new ReplyFile(creditFileService.zipFiles(), "征信数据.zip");
    }

    @aAuth(type = aAuth.aAuthType.ALL)
    @aDev(email = "linjunzhong@jiezhongchina.com", name = "钟林俊", code = "D050")
    public void queryStatus(){
        creditAPIService.queryStatus();
    }

    @aAuth(type = aAuth.aAuthType.ALL)
    @aDev(email = "linjunzhong@jiezhongchina.com", name = "钟林俊", code = "D050")
    public void upload(){
        creditAPIService.upload();
    }

    @aAuth(type = aAuth.aAuthType.ALL)
    @aDev(email = "linjunzhong@jiezhongchina.com", name = "钟林俊", code = "D050")
    public void merge(){
        creditFileService.zipFile(creditFileService.mergeExcelFiles());
    }

    @aAuth(type = aAuth.aAuthType.ALL)
    @aDev(email = "linjunzhong@jiezhongchina.com", name = "钟林俊", code = "D050")
    public void resolveError(){
        creditFileService.resolveFeedBackFile(new File("D:\\creditData\\Q10151000Hy7002016050151X.xls"));
    }


}
