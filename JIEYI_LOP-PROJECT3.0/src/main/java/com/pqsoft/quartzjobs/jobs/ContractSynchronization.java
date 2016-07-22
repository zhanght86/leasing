package com.pqsoft.quartzjobs.jobs;

import com.alibaba.fastjson.JSON;
import com.pqsoft.bpm.service.ConfigService;
import com.pqsoft.quartzjobs.AbstractJob;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.util.HttpUtil;
import org.apache.commons.configuration.ConfigurationException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 债权系统同步合同数据
 *
 * @author 钟林俊
 * @version V1.0 2016-07-18 09:50
 */
public class ContractSynchronization extends AbstractJob {

    @Override
    protected void exec(JobExecutionContext context) throws JobExecutionException {
        String url = getUrl();
        Map<String, String> requestParams = getParams();
        manageResponse(HttpUtil.formPost(url, requestParams));
    }

    private void manageResponse(String responseEntity) {
        @SuppressWarnings("unchecked")
        Map<String, Object> response = (Map<String, Object>) JSON.parse(responseEntity);
        Integer result = (Integer) response.get("result");
        switch (result){
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    private Map<String,String> getParams() {
        Map<String, String> requestParams = new HashMap<>(1);
        List<Map<String, Object>> contracts = queryContracts();
        String contractsStr = JSON.toJSONStringWithDateFormat(contracts, "yyyy-MM-dd");
        requestParams.put("contracts", contractsStr);
        return requestParams;
    }

    private List<Map<String,Object>> queryContracts() {
        List<Map<String, Object>> contracts = Dao.selectList("jobs.selectContracts");
        for(Map<String, Object> contract : contracts){

        }
        return contracts;
    }

    private String getUrl(){
        try {
            ConfigService configService = new ConfigService();
            return configService.get("host") + configService.get("contractSynUrl");
        } catch (ConfigurationException e) {
            throw new ActionException(e.getMessage());
        }
    }
}
