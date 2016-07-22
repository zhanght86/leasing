package com.pqsoft.creditData.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.IOUtil;
import com.pqsoft.util.JsonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 征信接口相关的业务处理
 *
 * @className CreditAPIService
 * @author 钟林俊
 * @version V1.0 2016年5月13日 15:10:29
 */
public class CreditAPIService {

    private static final Logger logger = Logger.getLogger(CreditAPIService.class);
    private ExcelConfigService excelConfigService = ExcelConfigService.getInstance();
    private UploadDataConfigService uploadDataConfigService = UploadDataConfigService.getInstance();
    private HttpClient httpClient;
    private static CreditAPIService creditAPIService;
    private CreditDataService creditDataService = CreditDataService.getInstance();
//    private CreditFileService creditFileService = CreditFileService.getInstance();

    public static CreditAPIService getInstance(){
        if(creditAPIService == null){
            synchronized (CreditAPIService.class){
                if(creditAPIService == null){
                    creditAPIService = new CreditAPIService();
                }
            }
        }
        return creditAPIService;
    }

    private CreditAPIService(){
        HttpClientParams httpClientParams = new HttpClientParams();
        httpClientParams.setSoTimeout(30000);
        httpClient = new HttpClient(httpClientParams);
    }

    /**
     * 征信接口的登录方法
     */
    private void login(){
        try {
            PostMethod login = new PostMethod(uploadDataConfigService.get("loginURL"));
            login.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            if(httpClient.executeMethod(login) == HttpStatus.SC_OK){
                login = new PostMethod(uploadDataConfigService.get("indexURL"));
                login.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                login.addRequestHeader("Refer", uploadDataConfigService.get("loginURL"));
                login.addRequestHeader("Accept", "image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");

                NameValuePair tgroup = new NameValuePair("tgroup", "");
                NameValuePair next = new NameValuePair("next", "");
                NameValuePair tgcookieset = new NameValuePair("tgcookieset", "");
                NameValuePair Login = new NameValuePair("Login", "登录");
                NameValuePair Email = new NameValuePair("username", uploadDataConfigService.get("username"));
                NameValuePair password = new NameValuePair("password", uploadDataConfigService.get("password"));
                // NameValuePair code = new NameValuePair( "code", "????");// 验证码TODO

                NameValuePair[] data = { tgroup , next , tgcookieset, Login, Email, password };
                login.setRequestBody(data);
                if(httpClient.executeMethod(login) == HttpStatus.SC_OK){
                    return;
                }
            }
            throw new IOException();
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException("征信接口登录失败！");
        }
    }

    /**
     * 征信接口查询上传的文件结果
     */
    public List<File> queryStatus(){
//        HttpPost httpPost = new HttpPost(uploadDataConfigService.getBatchCreditURL());
//        httpPost.setParams(new BasicHttpParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8"));
//        httpPost.addHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
//        httpPost.setEntity(new StringEntity(request), "utf-8");
        // 查找上传后尚未获取处理结果的文件名集合
        List<String> uploadedFileNames = Dao.selectList("UploadMapper.selectUploadedFileNames");

        // 反馈文件集合
        List<File> feedbackFiles = new ArrayList<>();

        if(CollectionUtils.isEmpty(uploadedFileNames)){
            return feedbackFiles;
        }

        login();
        PostMethod query = new PostMethod(uploadDataConfigService.get("host") + uploadDataConfigService.get("msgSubmitURL"));
        query.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        query.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        for(String uploadedFileName : uploadedFileNames){
            query.setRequestEntity(getUploadResultQueryRequestEntity(uploadedFileName));
            try{
                if(httpClient.executeMethod(query) == HttpStatus.SC_OK) {
                    // 获取响应结果转换成一个map
                    Map<String, Object> response = parseResponse(query.getResponseBodyAsStream());
                    // 结果反馈为失败时，错误信息计入日志
                    if(!"true".equals(String.valueOf(response.get("success")))){
                        logger.error("文件" + uploadedFileName + "结果查询失败， 错误信息: " + String.valueOf(response.get("errors")));
                        continue;
                    }
                    // 结果反馈成功时
                    String state = String.valueOf(response.get("state"));
                    String feedbackFileUrl = null;
                    String feedbackFileName = null;
                    String total = null;
                    String failed = null;
                    // 处理完成
                    if (state.equals("3")) {
                        // 错误记录数目
                        total = String.valueOf(response.get("totalcount"));
                        failed = String.valueOf(response.get("failcount"));
                        if (!failed.equals("0")) {
                            // 获取错误反馈文件
                            feedbackFileUrl = String.valueOf(response.get("feedbackfilepath"));
                            File feedBackFile = getFeedBackFile(uploadedFileName, feedbackFileUrl);
                            feedbackFileName = StringUtils.substringBeforeLast(feedBackFile.getName(), ".");
                            feedbackFiles.add(feedBackFile);
                        }
                    }
                    // 更新上传信息
                    creditDataService.updateUpload(uploadedFileName, state, feedbackFileUrl, feedbackFileName, total, failed);
                    Dao.commit();
                }else{
                    throw new IOException("网络连接失败！");
                }
            }catch (Exception e){
                logger.error("", e);
                Dao.rollback();
            }
        }

        return feedbackFiles;
    }

    /**
     * 解析征信接口的反馈数据，封装进一个map
     *
     * @param responseStream 应答流
     * @return 应答数据map
     */
    private Map<String, Object> parseResponse(InputStream responseStream) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(responseStream);
            NodeList allNodes = document.getFirstChild().getChildNodes();
            Node node;
            Map<String, String> map = new HashMap<>();
            int i=0;
            while (i < allNodes.getLength()) {
                node = allNodes.item(i);
                if(node instanceof Element){
                    map.put(node.getNodeName(),node.getTextContent());
                }
                i++;
            }
            String result = map.get("soap:Body");
            if(result.indexOf("[") == 0){
                result = StringUtils.substringAfter(result, "[");
                result = StringUtils.substringBeforeLast(result, "]");
            }
            return JsonUtil.toMap(JsonUtil.toJson(result));
        } catch (ParserConfigurationException |SAXException |IOException e) {
            logger.error("", e);
            throw new RuntimeException("征信接口反馈信息解析失败！");
        }
    }

    /**
     * 上传征信数据文件
     */
    public void upload(){
        List<String> fileNames = Dao.selectList("UploadMapper.selectNotUploaded");

        if(CollectionUtils.isEmpty(fileNames)){
            return;
        }

        login();
        for(String fileName : fileNames){
            File file = new File(excelConfigService.getFilePath(), fileName + ".zip");
            if(!file.exists()){
                continue;
            }
            // 登录
            String host = uploadDataConfigService.get("host");
            PostMethod upload = new PostMethod(host + uploadDataConfigService.get("msgSubmitURL"));
            upload.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            try {
                // 设置上传数据
                upload.setRequestEntity(getUploadRequestEntity(file));
                if(httpClient.executeMethod(upload) == HttpStatus.SC_OK){
                    // 获取响应结果
                    Map<String, Object> result = parseResponse(upload.getResponseBodyAsStream());
                    // 上传失败抛异常
                    Preconditions.checkArgument("true".equals(String.valueOf(result.get("success"))), String.valueOf(result.get("errors")));
                    // 更新上传信息
                    creditDataService.updateUploadTime(fileName);
                    Dao.commit();
                }
            } catch (Exception e) {
                logger.error("", e);
                Dao.rollback();
            }
        }
    }

    public File getFeedBackFile(String fileName, String feedBackFileUrl) {
        // 验证数据有效性
        Preconditions.checkArgument(!Strings.isNullOrEmpty(fileName), "文件名为空!");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(feedBackFileUrl), "错误反馈Url为空！");
        PostMethod postMethod = new PostMethod(uploadDataConfigService.get("host") + feedBackFileUrl);
        try {
//            Header[] headers = postMethod.getResponseHeaders();
            // 响应成功
            if(httpClient.executeMethod(postMethod) == HttpStatus.SC_OK){
                // 通过响应的流生成反馈文件
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                File file = new File(excelConfigService.getFilePath(), fileName + "X.xls");
                IOUtil.saveFile(inputStream, file, true);
                return file;
            }
            throw new IOException();
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException("获取反馈文件失败！");
        }
    }

    /**
     * 上传文件状态查询的请求体字符串
     *
     * @param fileName 文件名
     * @return http请求体
     */
    private StringRequestEntity getUploadResultQueryRequestEntity(String fileName) {
        return new StringRequestEntity("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.datasubmit.p2p.sino.com/\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + "<web:queryMessageFile>"
                + "<orgcode>"+ uploadDataConfigService.get("orgCode") +"</orgcode>"
                + "<secret>"+ uploadDataConfigService.get("secret") +"</secret>"
                + "<filename>" + fileName + ".zip</filename>"
                + "</web:queryMessageFile>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>");
    }

    /**
     * 上传文件的请求体字符串
     *
     * @param file 待上传的文件
     * @return http请求体
     */
    private StringRequestEntity getUploadRequestEntity(File file) throws IOException {
        return new StringRequestEntity("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.datasubmit.p2p.sino.com/\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + "<web:uploadMessageFile>"
                + "<orgcode>"+ uploadDataConfigService.get("orgCode") +"</orgcode>"
                + "<secret>"+ uploadDataConfigService.get("secret") +"</secret>"
                + "<filename>" + file.getName() + "</filename>"
                + "<filebuff>"+  Base64.encodeBase64String(FileUtils.readFileToByteArray(file)) +"</filebuff>"
                + "</web:uploadMessageFile>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>");
    }
}
