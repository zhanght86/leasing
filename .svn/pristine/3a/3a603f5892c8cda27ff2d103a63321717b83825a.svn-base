package com.pqsoft.creditData.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * 读取excel配置文件的业务处理
 *
 * @className ExcelConfigService
 * @author 钟林俊
 * @version V1.0 2016年4月21日 下午4:04:32
 */
public class ExcelConfigService extends ConfigService{
    //工作表名映射
    private Map<String, String> title;
    //工作表中的列名映射
    private Map<String, Map<String, String>> headMap;
    //工作表中的列宽度映射
    private Map<String, Integer[]> widthMap;
    //excel文件的生成路径
    private String filePath;

    private static ExcelConfigService excelConfigService;

    public static ExcelConfigService getInstance(){
        if(excelConfigService == null){
            synchronized (ExcelConfigService.class){
                if(excelConfigService == null){
                    excelConfigService = new ExcelConfigService();
                }
            }
        }
        return excelConfigService;
    }

    private ExcelConfigService(){}

    @Override
    public void reload(){
        loadExcelProperties("content/creditData/excelData.properties");
    }

    public void loadExcelProperties(String fileName){
        headMap = new LinkedHashMap<>();
        widthMap = new LinkedHashMap<>();
        try{
            //从配置文件中读取配置
            Configuration configuration = new PropertiesConfiguration(fileName);
            //获取工作表名映射
            title = load(configuration, "title");
            for(String key : title.keySet()){
                //获取对应工作表下的列名映射
                Map<String, String> map = load(configuration, key);
                headMap.put(key, map);
                //初始化对应工作表下的列宽度映射容器
                widthMap.put(key, new Integer[map.size()]);
            }
            //获取生成文件的路径
            filePath = configuration.getString("filePath");
//            if(StringUtils.endsWith(filePath, File.separator)){
//                filePath = StringUtils.substringBeforeLast(filePath, File.separator);
//            }

            Preconditions.checkArgument(!MapUtils.isEmpty(title), "工作表名称映射为空！");
            Preconditions.checkArgument(!MapUtils.isEmpty(headMap), "工作表列标题映射为空！");
            Preconditions.checkArgument(!MapUtils.isEmpty(widthMap), "工作表列宽度映射为空！");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(filePath), "文件生成目录为空！");
        }
        catch (ConfigurationException e) {
            throw new RuntimeException("征信数据文件加载失败！");
        }
    }

    /**
     * 获取工作表名的中英文映射
     *
     * @return 工作表名映射
     */
    public Map<String, String> getTitle() {
        return title;
    }

    /**
     * 获取工作表名的列标题映射
     *
     * @return 工作表列标题映射
     */
    public Map<String, Map<String, String>> getHeadMap() {
        return headMap;
    }

    /**
     * 获取工作表名的列宽度数组映射
     *
     * @return 工作表列宽度数组映射
     */
    public Map<String, Integer[]> getWidthMap() {
        return widthMap;
    }

    /**
     * 生成的文件路径
     *
     * @return 文件路径
     */
    public String getFilePath() {
        return filePath;
    }
}
