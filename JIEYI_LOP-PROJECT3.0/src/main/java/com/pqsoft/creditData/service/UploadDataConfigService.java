package com.pqsoft.creditData.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 读取上传数据的配置文件的业务处理
 *
 * @className ExcelConfigService
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class UploadDataConfigService extends ConfigService {

    private Map<String, String> config;
//    private String host;
//    private String batchCreditURL;
//    private String msgSubmitURL;
//    private String queryCreditURL;
//    private String loginURL;
//    private String indexURL;
//    private String orgCode;
//    private String username;
//    private String password;
//    private String secret;
//    private String zipPassword;

    private static UploadDataConfigService uploadDataConfigService;

    public static UploadDataConfigService getInstance(){
        if(uploadDataConfigService == null){
            synchronized (UploadDataConfigService.class){
                if(uploadDataConfigService == null){
                    uploadDataConfigService = new UploadDataConfigService();
                }
            }
        }
        return uploadDataConfigService;
    }

    private UploadDataConfigService(){}

    @Override
    public void reload() {
        loadUploadDataProperties("/content/creditData/uploadData.properties");
    }

    private void loadUploadDataProperties(String fileName) {
        try{
            config = new ConcurrentHashMap<>();
            //从配置文件中读取配置
            Configuration configuration = new PropertiesConfiguration(fileName);
            Iterator<String> keys = configuration.getKeys();
            String key;
            while (keys.hasNext()){
                key = keys.next();
                if(!Strings.isNullOrEmpty(key)){
                    String val = configuration.getString(key);
                    Preconditions.checkArgument(!Strings.isNullOrEmpty(val), "上传数据的配置文件中存在空配置！(" + key + ": " + val + ")");
                    config.put(key, val);
                }
            }
//            host = configuration.getString("host");
//            batchCreditURL = configuration.getString("batchCreditURL");
//            msgSubmitURL = configuration.getString("msgSubmitURL");
//            queryCreditURL = configuration.getString("queryCreditURL");
//            loginURL = configuration.getString("loginURL");
//            indexURL = configuration.getString("indexURL");
//            username = configuration.getString("username");
//            password = configuration.getString("password");
//            orgCode = configuration.getString("orgCode");
//            secret = configuration.getString("secret");
//            zipPassword = configuration.getString("zipPassword");
        }
        catch (ConfigurationException e) {
            throw new RuntimeException("征信数据文件加载失败！");
        }
    }

    public String get(String key){
        return config.get(key);
    }

//    /**
//     * 征信直连主机地址
//     *
//     * @return
//     */
//    public String getHost() {
//        return host;
//    }
//
//    /**
//     * 征信登录地址
//     *
//     * @return 登录地址
//     */
//    public String getLoginURL() {
//        return loginURL;
//    }
//
//    /**
//     * 征信访问前置页地址
//     *
//     * @return 征信访问前置页地址
//     */
//    public String getIndexURL() {
//        return indexURL;
//    }
//
//    /**
//     * NFCS分配的账户
//     *
//     * @return NFCS分配的账户
//     */
//    public String getUsername() {
//        return username;
//    }
//
//    /**
//     * NFCS分配的账号密码
//     *
//     * @return NFCS分配的账号密码
//     */
//    public String getPassword() {
//        return password;
//    }
//
//    /**
//     * NFCS分配的机构号
//     *
//     * @return NFCS分配的机构号
//     */
//    public String getOrgCode() {
//        return orgCode;
//    }
//
//    /**
//     * NFCS分配的直连密码
//     *
//     * @return NFCS分配的直连密码
//     */
//    public String getSecret() {
//        return secret;
//    }
//
//    /**
//     * NFCS分配的zip密码;
//     *
//     * @return NFCS分配的zip密码
//     */
//    public String getZipPassword() {
//        return zipPassword;
//    }
//
//    public String getBatchCreditURL() {
//        return batchCreditURL;
//    }
//
//    public String getQueryCreditURL() {
//        return queryCreditURL;
//    }
//
//    public String getMsgSubmitURL() {
//        return msgSubmitURL;
//    }
}
