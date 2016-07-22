package com.pqsoft.creditData.service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.pqsoft.creditData.model.BaseModel;
import com.pqsoft.creditData.model.CreditData;
import com.pqsoft.creditData.model.CreditUpload;
import com.pqsoft.insure.util.DateUtil;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.ExcelUtil;
import com.pqsoft.util.ReflectionUtil;
import com.pqsoft.util.ZipUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;

import java.io.*;
import java.util.*;

/**
 *
 * 征信文件相关的业务处理
 *
 * @className CreditFileService
 * @author 钟林俊
 * @version V1.0 2016年5月13日 16:46:13
 */
public class CreditFileService {

    private static final Logger logger = Logger.getLogger(CreditFileService.class);
    private CreditDataService creditDataService = CreditDataService.getInstance();
    private ExcelConfigService excelConfigService = ExcelConfigService.getInstance();
    private UploadDataConfigService uploadDataConfigService = UploadDataConfigService.getInstance();

    /**
     * 36进制元素
     */
    private static final char[] ELEMENTS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static CreditFileService creditFileService;

    public static CreditFileService getInstance(){
        if(creditFileService == null){
            synchronized (CreditFileService.class){
                if(creditFileService == null){
                    creditFileService = new CreditFileService();
                }
            }
        }
        return creditFileService;
    }

    private CreditFileService(){}

    /**
     * 根据征信数据集合生成多个文件
     */
    public void generateExcelFiles(String pageNumStr, String pageSizeStr) {
        // 分页参数
        Integer pageNum;
        Integer pageSize;
        try {
            pageNum = Integer.parseInt(pageNumStr);
        } catch (NumberFormatException e) {
            pageNum = 1;
        }
        try{
            pageSize = Integer.parseInt(pageSizeStr);
            if(pageSize > 300){
                pageSize = 300;
            }
        } catch (NumberFormatException e){
            pageSize = 100;
        }
        Map<String, Object> params = new HashMap<>();
        Integer start = (pageNum - 1) * pageSize;
        params.put("pageStart", start);
        params.put("pageEnd", start + pageSize);
        // 以客户id为基础分页
        List<Integer> idList = Dao.selectList("IdentityMapper.selectCreditIds", params);
        Preconditions.checkArgument(!CollectionUtils.isEmpty(idList), "没有可用的征信数据！");
        for (Integer id : idList) {
            CreditData creditData = creditDataService.extractCreditDataById(id);
            generateExcelFile(creditData);
        }
    }

    /**
     * 从指定的文件（夹）中获取所有后缀为.xls的文件放入集合（支持递归)
     *
     * @param excelFiles excel文件集合
     * @param file 给定的目标文件（夹）
     */
    public void setMergeableExcelFiles(List<File> excelFiles, List<String> mergedFileNames, File file){
        Preconditions.checkNotNull(file, "源文件对象为空！");
        Preconditions.checkArgument(file.exists(), "源文件（夹）" + file.getName() + "不存在！");
        if(file.isDirectory()){
            //只接受.xls后缀并且不以机构号码开头的文件
            for(File subFile : file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String name = file.getName();
                    return !file.isDirectory() && (name.startsWith(DateUtil.format(new Date(), "yyyyMMdd")) && name.endsWith(".xls"));
                }
            })){
                excelFiles.add(subFile);
                mergedFileNames.add(StringUtils.substringBeforeLast(subFile.getName(), "."));
            }
        }
    }

    /**
     * 合并所有默认位置的excel文件，并按照征信接口的接受规则生成可上传的excel文件
     *
     * @return 合并后的可上传的文件对象
     */
    public File mergeExcelFiles() {
        // 获取默认目录
        File srcFile = new File(excelConfigService.getFilePath());
        Preconditions.checkNotNull(srcFile, "默认目录读取失败！");

        // 初始化excel文件集合
        File[] files = srcFile.listFiles();
        int len = files == null ? 1 : files.length;
        List<File> excelFiles = new ArrayList<>(len);
        List<String> mergedFileNames = new ArrayList<>(len);
        // 获取被合并的文件集合
        setMergeableExcelFiles(excelFiles, mergedFileNames, srcFile);
        Preconditions.checkArgument(!CollectionUtils.isEmpty(excelFiles), "没有用于上传的excel文件！");

        // 建一个新的excel文件用于汇总
        HSSFWorkbook workbook = new HSSFWorkbook();
        String key;

        // 设置好工作表和每个工作表的列标题
        for (Map.Entry<String, String> entry : excelConfigService.getTitle().entrySet()) {
            key = entry.getKey();
            HSSFSheet sheet = workbook.createSheet(entry.getValue());
            addSheetHead(sheet, excelConfigService.getHeadMap().get(key), excelConfigService.getWidthMap().get(key));
        }

        String serialNo = getSerialNo();
        File destDir = new File(excelConfigService.getFilePath() + File.separator + DateUtil.format(new Date(), "yyyyMMdd") + "_" + serialNo);

        // 合并
        merge(workbook, excelFiles, destDir);

        // 生成.xls的文件写入硬盘
        File outputFile = new File(checkPath(excelConfigService.getFilePath()), getFileName(uploadDataConfigService.get("orgCode"), serialNo, "1.xls"));
        BufferedOutputStream bos = null;
        boolean delete = false;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(outputFile));
            workbook.write(bos);
            // 将文件信息写入上传信息表
            CreditUpload creditUpload = creditDataService.insertCreditUpload(StringUtils.substringBeforeLast(outputFile.getName(), "."));
            // 更新关联数据
            creditDataService.updateIdentityBatch(creditUpload.getId(), mergedFileNames);
            return outputFile;
        } catch (Exception e) {
            logger.error("", e);
            delete = true;
            throw new RuntimeException("合并文件失败！");
        } finally {
            IOUtils.closeQuietly(bos);
            if(delete){
                outputFile.delete();
            }
        }
    }

    /**
     * 将集合中的excel文件合并到指定的workbook当中，默认不复制每个工作表的第一行（即列标题），主workbook已设置了工作表名和列标题
     *
     * @param workbook 主workbook(即所有文件的数据都往其中复制)
     * @param excelFiles 文件集合
     * @param destDir 移动被成功合并的文件到该文件夹
     */
    private void merge(HSSFWorkbook workbook, List<File> excelFiles, File destDir) {
        HSSFWorkbook aWorkbook;
        NPOIFSFileSystem system = null;
        boolean move;
        for(File file : excelFiles){
            move = true;
            try {
                system = new NPOIFSFileSystem(file);
                aWorkbook = new HSSFWorkbook(system.getRoot(), true);
                Map<String, Map<Integer, Integer>> sheetRowNoMap = ExcelUtil.merge(workbook, aWorkbook, 1, 0);
                // 更新合并后变更的行号
                creditDataService.updateRowNo(file, sheetRowNoMap);

            } catch (IOException e) {
                logger.error("", e);
                logger.error("文件" + file.getName() + "合并失败！");
                move = false;
            } finally {
                IOUtils.closeQuietly(system);
                if(move){
                    try{
                        FileUtils.moveFileToDirectory(file, destDir, true);
                    }catch (IOException e){
                        logger.error("", e);
                    }
                }
            }
        }
    }

    /**
     * 声称用于征信接口的zip文件，使用默认的目录和密码
     *
     * @param file 源文件
     * @return 生成的压缩文件
     */
    public File zipFile(File file){
        return ZipUtil.zipWithPassword(file, new File(file.getParent(), StringUtils.substringBeforeLast(file.getName(), ".") + ".zip"), uploadDataConfigService.get("zipPassword"), false);
    }

//    public File zipFile(Integer id){
//        String filePath = excelConfigService.getFilePath();
//        String fileName = uploadDataConfigService.get("orgCode") + DateUtil.format(new Date() , "yyyyMM");
//        File file = new File(excelConfigService.getFilePath(), fileName + "_" + id + ".xls");
//        String serialNo = getSerialNo();
//        File srcFile = new File(filePath, fileName + serialNo + "1.xls");
//        try {
//            FileUtils.copyFile(file, srcFile);
//        } catch (IOException e) {
//            logger.error("", e);
//        }
//        return ZipUtil.zipWithPassword(srcFile, new File(filePath, fileName + serialNo + "1.zip"), uploadDataConfigService.get("zipPassword"), true);
//    }

    /**
     * 蒋征信数据文件压缩成一个压缩文件
     */
    public File zipFiles(){
        return ZipUtil.zip(excelConfigService.getFilePath(), getOutputPath(excelConfigService.getFilePath()), getOutputFileName(excelConfigService.getFilePath()), true);
    }

    /**
     * 通过源文件目录获取生成的压缩文件的目录
     *
     * @param filePath 源文件目录
     * @return 压缩文件所在的目录
     */
    private String getOutputFileName(String filePath) {
        String outputFileName= StringUtils.substringAfterLast(filePath, File.separator);
        if(Strings.isNullOrEmpty(outputFileName)){
            outputFileName = "creditData";
        }
        return outputFileName;
    }

    /**
     * 通过源文件目录获取生成的压缩文件的名字
     *
     * @param filePath 源文件目录
     * @return 压缩文件的名字
     */
    private String getOutputPath(String filePath) {
        String outputPath = StringUtils.substringBeforeLast(filePath, File.separator);
        // linux根目录或者windows根目录
        if(Strings.isNullOrEmpty(outputPath) || outputPath.equals(filePath)){
            outputPath = "/";
        }
        return outputPath;
    }

    /**
     * 根据征信数据生成对应的excel文件
     *
     * @param creditData 征信数据
     * @return 生成的文件的绝对路径
     */
    public File generateExcelFile(CreditData creditData) {
        Preconditions.checkNotNull(creditData, "征信数据对象为空！");
        File file = null;
        BufferedOutputStream bos = null;
        boolean delete = false;
        try {
            file = getFile(creditData);
//            if(file.exists()){
//                return file;
//            }
            HSSFWorkbook workbook = generateWorkbook(creditData);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            workbook.write(bos);
            creditDataService.persistCreditData(creditData, StringUtils.substringBeforeLast(file.getName(), "."));
            Dao.commit();
        } catch (Exception e) {
            logger.error("", e);
            Dao.rollback();
            delete = true;
        } finally {
            IOUtils.closeQuietly(bos);
            if(delete && file != null){
                file.delete();
            }
        }
        return file;
    }

    /**
     * 获取征信数据对应的文件
     *
     * @param creditData 征信数据
     * @return 生成的文件对象
     */
    private File getFile(CreditData creditData) {
        String fileName = getFileName(creditData);
        return new File(checkPath(excelConfigService.getFilePath()), fileName);
    }

    /**
     * 文件流水号为三位且只能是0-9或A-Z组成，当其为一个36进制的数字，对应的10进制最大值为36*36*36，在数据库中建立了一个序列做了相应转换得到36进制值，
     * 并且以高位补0的方式补齐三位
     *
     * @return 文件流水号
     */
    private String getSerialNo(){
        // 一个最大值为46655的循环序列
        int sequenceNo = Integer.parseInt(Dao.getSequence("SEQ_CREDIT_FILE"));
        StringBuilder serialNo = new StringBuilder();
        // 利用自定义的元素数组转换成36位进制数
        do{
            serialNo.append(ELEMENTS[sequenceNo % 36]);
            sequenceNo /= 36;
        }while(sequenceNo != 0);
        // 不够三位高位补0
        return Strings.padStart(serialNo.reverse().toString(), 3, '0');
    }

    /**
     * 通过数据信息获取其对应的文件名
     *
     * @param creditData 征信数据
     * @return 文件名
     */
    private String getFileName(CreditData creditData) {
        return getFileName("", DateUtil.format(new Date(), "dd") + "_" + creditData.getClientId(), ".xls");
    }

    /**
     * 获取需要生成的文件的文件名
     *
     * @param P2POrganCode p2p机构代码
     * @param serialNo 序列号
     * @param suffix 文件名后缀
     * @return 完整的文件名
     */
    private String getFileName(String P2POrganCode, String serialNo, String suffix){
        return P2POrganCode + DateUtil.format(new Date(), "yyyyMM") + serialNo + suffix;
    }

    /**
     * 检查给定的文件路径
     *
     * @param filePath 文件路径
     * @return 文件路径
     */
    private String checkPath(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isDirectory()){
            if(!file.mkdirs()){
                throw new RuntimeException("目录创建失败！");
            }
        }
        return filePath;
    }
    /**
     * 根据征信数据生成单个文件
     *
     * @param creditData 征信数据
     */
    public HSSFWorkbook generateWorkbook(CreditData creditData){
        Preconditions.checkNotNull(creditData, "征信数据记录为空！");
        HSSFWorkbook workbook = new HSSFWorkbook();

        List<? extends BaseModel> dataList;
        HSSFSheet dataSheet;

        //遍历工作表标题集合
        for(Map.Entry<String, String> entry : excelConfigService.getTitle().entrySet()){
            //noinspection unchecked
            dataList = (List<? extends BaseModel>) ReflectionUtil.getGetterResult(creditData, entry.getKey() + "List");
//            if(CollectionUtils.isEmpty(dataList)){
//                continue;
//            }
            //建立对应标题的工作表
            dataSheet = workbook.createSheet(entry.getValue());
            //工作表列标题映射容器
            Map<String, String> headMap = excelConfigService.getHeadMap().get(entry.getKey());
            //工作表列宽度存储容器
            Integer[] widthArray = excelConfigService.getWidthMap().get(entry.getKey());
            //将数据写入工作表
            manageSheet(dataSheet, dataList, headMap, widthArray);
            //设置工作表单元格宽度为本列最宽的那一行的宽度
            for(int i = 0; i < headMap.size(); i++){
                dataSheet.setColumnWidth(i, widthArray[i]);
            }
        }
        return workbook;
    }

    /**
     * 获取单元格格式（默认宋体，11号字）
     *
     * @param workbook 工作簿
     * @return 格式对象
     */
    private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 11);
        cellStyle.setFont(font);
        return cellStyle;
    }


    /**
     * 将数据列表写入工作表中
     *
     * @param sheet 指定的工作表
     * @param dataList 数据集合
     * @param headMap 工作表列名映射
     */
    private void manageSheet(HSSFSheet sheet, List<? extends BaseModel> dataList, Map<String, String> headMap, Integer[] widthArray) {
        //工作表对象为空抛出异常
        Preconditions.checkNotNull(sheet, "工作表对象为空!");
        //写入工作表第一行的列名
        addSheetHead(sheet, headMap, widthArray);

        //数据集合为空不进行操作
        if(CollectionUtils.isEmpty(dataList)){
            return;
        }

        //将数据集合拆分为单行数据写入工作表
        for (BaseModel data : dataList) {
            addSheetData(sheet, sheet.getLastRowNum() + 1, data, headMap, widthArray);
        }
    }

    /**
     * 将数据写入工作表的一行中
     *
     * @param sheet 指定的工作表
     * @param rowNum 指定行的行号
     * @param model 征信数据对象
     * @param headMap 工作表列名映射
     */
    private void addSheetData(HSSFSheet sheet, int rowNum, BaseModel model, Map<String, String> headMap, Integer[] widthArray) {
        if(MapUtils.isEmpty(headMap)){
            return;
        }

        HSSFRow row = sheet.createRow(rowNum);
        Object value;
        String val;
        int i = 0;
        for(Map.Entry<String, String> entry : headMap.entrySet()){
            //获取对应单元格的值
            value = ReflectionUtil.getGetterResult(model, entry.getKey());
            //设置单元格的值
            val = value == null? "": String.valueOf(value);
            setCellStringValue(row, i, val);
            //获取当前列的宽度
            int width = String.valueOf(value).getBytes().length * 2 * 192;
            //与列宽度容器存储的对应列的宽度比较取较大值覆盖存储
            widthArray[i] = Math.max(widthArray[i], width);
            i++;
        }
        // 记录行号
        model.setFormerRowNo(String.valueOf(rowNum + 1));
    }

    /**
     * 按照列名映射将列名写入指定工作表的第一行
     *
     * @param sheet 指定的工作表
     * @param headMap 工作表的列名映射
     */
    private void addSheetHead(HSSFSheet sheet, Map<String, String> headMap, Integer[] width) {
        if(MapUtils.isEmpty(headMap)){
            return;
        }

        int i = 0;
        String value;
        HSSFRow row = sheet.createRow(i);
        for(Map.Entry<String, String> entry : headMap.entrySet()){
            value = entry.getValue() == null? "": entry.getValue();
            //设置单元格的值
            setCellStringValue(row, i, value);
            //将列宽度设置到列宽度容器中存储便于后后续新增行做比较
            int thisWidth = value.getBytes().length * 2 * 192;
            sheet.setColumnWidth(i, thisWidth);
            width[i++] = thisWidth;
        }
    }

    /**
     * 将字符串设置到指定行的指定单元格中
     *
     * @param row 行对象
     * @param cellNum 单元格的格号
     * @param value 字符串值
     */
    private void setCellStringValue(HSSFRow row, int cellNum, String value){
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellStyle(getStyle(cell.getSheet().getWorkbook()));
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(value.trim());
    }

    /**
     * 解析错误反馈文件
     *
     * @param file 错误反馈文件对象
     */
    public void resolveFeedBackFile(File file) {
        NPOIFSFileSystem system = null;
        HSSFWorkbook workbook;
        try {
            system = new NPOIFSFileSystem(file);
            workbook = new HSSFWorkbook(system.getRoot(), true);
            HSSFSheet sheet = workbook.getSheetAt(0);
            // 将获得的错误反馈信息插入数据库
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                creditDataService.insertCreditError(StringUtils.substringBeforeLast(file.getName(), "."), sheet.getRow(i));
            }
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException("解析反馈文件" + file.getName() + "失败！");
        } finally {
            IOUtils.closeQuietly(system);
        }
    }

    /**
     * 解析反馈文件集合
     *
     * @param files 错误反馈的文件集合
     */
    public void resolveFeedBackFiles(List<File> files) {
        if(CollectionUtils.isEmpty(files)){
            return;
        }

        for(File file : files){
            try{
                resolveFeedBackFile(file);
                Dao.commit();
            }
            catch (Exception e){
                logger.error("", e);
                Dao.rollback();
            }
        }
    }
}
