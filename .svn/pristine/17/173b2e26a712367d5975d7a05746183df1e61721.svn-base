package com.pqsoft.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.exception.ActionException;
// import javax.imageio.ImageIO;
// import oracle.sql.DATE;
// import org.springframework.util.FileCopyUtils;
// import java.awt.Graphics;
// import java.awt.Image;
// import java.awt.image.BufferedImage;
// import java.io.ByteArrayInputStream;
import com.pqsoft.skyeye.util.IMG;
import com.pqsoft.skyeye.util.UTIL;

/**
 * 图像采集工具类
 * 
 * @author 付玉龙
 *         下午04:52:37
 */
public class FileCatalogUtil {

	private final String xmlPath = "fileCatalogUtil.";
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);

	/**
	 * 获取文件全路径
	 * 
	 * @param String
	 *            renterName 用户名
	 * @param String
	 *            renterCode 用户编号
	 * @param String
	 *            path 文件目录
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-3-7 上午10:34:19
	 */
	public String rootCatalog(String renterName, String renterCode, String path) {
		if (!isNull(renterName) && !isNull(renterCode)) {
			return renterName + "_" + renterCode + "/" + path;
		} else if (isNull(renterName) && isNull(renterCode)) {
			return path;
		} else if (isNull(renterName)) {
			return renterCode + "/" + path;
		} else {
			return renterName + "/" + path;
		}
	}

	/**
	 * 根据文件ID查询文件信息
	 * 
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-28 上午11:39:32
	 */
	public Map<String, Object> getPictureFileData(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "selectPictureFileData", param);
	}

	/**
	 * 根据path获取文件目录id 如果没有该目录则创建后返回该目录id
	 * 
	 * @param path
	 *            图片存放文件目录，多级目录用“/”隔开，格式如：核心要件/身份证
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-3-6 上午09:41:44
	 */
	@SuppressWarnings("unchecked")
	public String getCatalogIdByPath(String renterName, String renterCode, String path) {
		path = rootCatalog(renterName, renterCode, path);// 获取文件全路径
		String catalogId = "";
		String parent_id = "";
		String[] catalogName = (path).split("/");
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < catalogName.length; i++) {
			param.put("FILENAME", catalogName[i]);
			param.put("PARENT_ID", parent_id);
			Map<String, Object> map = ((Map<String, Object>) Dao.selectOne(xmlPath + "selectCatalogId", param));
			if (map != null) {
				catalogId = map.get("ID").toString();
				parent_id = catalogId;
			} else {
				String newCatalogId = Dao.getSequence("SEQ_PICTURECATALOG");
				param.put("ID", newCatalogId);
				if (i == catalogName.length - 1) {
					param.put("READONLY", "1");
				} else {
					param.put("READONLY", "0");
				}
				param.put("PARENT_ID", parent_id);
				Dao.insert(xmlPath + "autoInsertCatalog", param);
				catalogId = newCatalogId;
				parent_id = newCatalogId;
			}
		}
		return catalogId;
	}

	/**
	 * FIXME BUG : 有可能会查询多条？报错！（ Dao.selectOne(xmlPath + "selectCatalogId", param)) ）
	 * 根据path获取文件目录id 如果没有该目录则创建后返回该目录id
	 * 
	 * @param path
	 *            图片存放文件目录，多级目录用“/”隔开，格式如：核心要件/身份证
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-3-6 上午09:41:44
	 */
	@SuppressWarnings("unchecked")
	public String getCatalogIdByPath(String renterName, String renterCode, String path, boolean type) {
		boolean flag = false;
		path = rootCatalog(renterName, renterCode, path);// 获取文件全路径
		System.out.println("renterName:"+renterName);
		String catalogId = "";
		String parent_id = "";
		String[] catalogName = (path).split("/");
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < catalogName.length; i++) {
			param.put("FILENAME", catalogName[i]);
			param.put("PARENT_ID", parent_id);
			Map<String, Object> map = ((Map<String, Object>) Dao.selectOne(xmlPath + "selectCatalogId", param));
			if (map != null) {
				catalogId = map.get("ID").toString();
				parent_id = catalogId;
			} else {
				String newCatalogId = Dao.getSequence("SEQ_PICTURECATALOG");
				param.put("ID", newCatalogId);
				if (i == catalogName.length - 1) {
					param.put("READONLY", "1");
				} else {
					param.put("READONLY", "0");
				}
				param.put("PARENT_ID", parent_id);
				Dao.insert(xmlPath + "autoInsertCatalog", param);
				catalogId = newCatalogId;
				parent_id = newCatalogId;
				flag = true;
			}
		}
		if (flag) { // 记录添加目录的操作
			if (type) {
				Map<String, Object> recordMap = new HashMap<String, Object>();
				recordMap.put("INSTRUCTION", "ADDPATH");
				recordMap.put("RECORD_REMARK", "添加新目录");
				recordMap.put("ORIGINAL_PATH", path);
				addPictrueRecord(recordMap);
			}
		}
		return catalogId;
	}

	/**
	 * 根据path获取文件目录id 如果没有该目录则返回null
	 * 
	 * @param path
	 *            图片存放文件目录，多级目录用“/”隔开，格式如：核心要件/身份证
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-3-6 上午09:41:44
	 */
	@SuppressWarnings("unchecked")
	public String getCatalogIdByPathNoCreat(String renterName, String renterCode, String path) {
		path = rootCatalog(renterName, renterCode, path);// 获取文件全路径
		String catalogId = "";
		String parent_id = "";
		String[] catalogName = (path).split("/");
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < catalogName.length; i++) {
			param.put("FILENAME", catalogName[i]);
			param.put("PARENT_ID", parent_id);
			Map<String, Object> map = ((Map<String, Object>) Dao.selectOne(xmlPath + "selectCatalogId", param));
			if (map != null) {
				catalogId = map.get("ID").toString();
				parent_id = catalogId;
			} else {
				catalogId = null;
				break;
			}

		}
		return catalogId;
	}

	// /**
	// * 上传图片
	// * @author 付玉龙 fuyulong47@foxmail.com
	// * @date 2013-9-16 下午02:49:17
	// */
	// public boolean uploadPicture(Map<String, Object> param,List<FileItem>
	// fileList){
	// if(param !=null && !param.isEmpty() && param.containsKey("RENTER_NAME")
	// && param.containsKey("RENTER_CODE") && param.containsKey("FILE_PATH")){
	// String catalogId =
	// getCatalogIdByPath(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString());
	// param.put("CATALOG_ID", catalogId);
	// if(doUploadPicture(param,fileList,
	// rootCatalog(param.get("RENTER_NAME").toString(),param.get("RENTER_CODE").toString(),param.get("FILE_PATH").toString()))){//上传到磁盘
	// insertFileMessage(param);//在数据库中新添加数据
	// return true;
	// }
	// }
	// return false;
	// }

	/**
	 * 创建磁盘目录
	 */
	public void CreatDiskDirectory(String URL) {
		String path = SkyEye.getConfig("file.path.picture");
		File file = new File(path + File.separator + URL);
		if (!file.exists()) {// 检查磁盘上是否存在path路径文件
			file.mkdirs();// 在磁盘上创建路径
		}
	}

	/**
	 * 上传图片
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-16 下午02:49:17
	 */
	public boolean doUploadPicture(Map<String, Object> param, List<FileItem> fileList, String URL) {
		logger.info("开始上传文件");
		// 设置文件上传的大小 目前设置为50M
		final long MAX_SIZE = 50 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path.picture");
		ServletFileUpload suf = new ServletFileUpload(df);
		suf.setHeaderEncoding("UTF-8");
		suf.setSizeMax(MAX_SIZE);
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			if (!fileitem.isFormField()) {// 如果是文件
				if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
					String fileName = fileitem.getName();
					Pattern pattern = Pattern.compile("[^\\\\]+\\.{1}\\w+$");
					Matcher mat = pattern.matcher(fileName);
					while (mat.find()) {
						fileName = mat.group();
					}
					try {
						file = new File(path + File.separator + URL);// 原图文件目录
						if (!file.exists()) {// 检查磁盘上是否存在path路径文件
							file.mkdirs();// 在磁盘上创建路径
						}
						if (param.containsKey("VALUE2")) {
							file = new File(path + File.separator + URL + File.separator + UUID.randomUUID() + fileName + "." + param.get("VALUE2"));// 原图
						} else {
							file = new File(path + File.separator + URL + File.separator + UUID.randomUUID() + fileName);// 原图
						}
						file.createNewFile();
					} catch (Exception e) {
						logger.error(e, e);
						return false;
					}
					String filename = file.getName();
					String file_type = filename.toString().substring(filename.toString().lastIndexOf(".") + 1, filename.toString().length());
					InputStream is = null;
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					try {
						is = fileitem.getInputStream();
						file_type = file_type.toUpperCase();
						byte[] byte_ = null;
						if (file_type.equals("PNG")) {// 将图片处理一个压缩图片出来
							byte_ = UTIL.IMG.shrink(is, IMG.TYPE_PNG, 1024, 768);
							FileCopyUtils.copy(byte_, file);
						} else if (file_type.equals("JPG")) {
							byte_ = UTIL.IMG.shrink(is, IMG.TYPE_JPG, 1024, 768);
							FileCopyUtils.copy(byte_, file);
						} else {
							fileitem.write(file);// 非图片文件
						}
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					} finally {
						if (is != null) {
							try {
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (bos != null) {
							try {
								bos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					param.put("FILE_TYPE", file_type);// 文件扩展名
					String[] fileNames = fileName.split("[.]");
					param.put("FILE_NAME", fileNames[0]);// 文件名称
					param.put("ORIGINAL_PATH", file.getPath());// 原图片上传路径
					{// 防止同一文件夹下的文件名称重复
						Map<String, Object> fileMap = selectRepeatFileMax(param);
						if (fileMap != null && fileMap.containsKey("FILE_NAME_REPEAT")) {
							int FILE_NAME_REPEAT = Integer.parseInt(fileMap.get("FILE_NAME_REPEAT").toString()) + 1;
							param.put("FILE_NAME", param.get("FILE_NAME") + "(" + FILE_NAME_REPEAT + ")");
							param.put("FILE_NAME_REPEAT", FILE_NAME_REPEAT);
						} else {
							param.put("FILE_NAME_REPEAT", "0");
						}
					}
					param.put("SIZE", fileitem.getSize() / 1024 + "KB");// 文件大小
					logger.info("保存文件完毕：" + file.getAbsolutePath());
				}
			} else {
				try {
					param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 上传图片
	 * 
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-9-16 下午02:49:17
	 */
	public List<Map<String, Object>> doUploadPictureBat(Map<String, Object> m, List<FileItem> fileList, String URL) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		logger.info("开始上传文件");
		// 设置文件上传的大小 目前设置为50M
		final long MAX_SIZE = 50 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path.picture");
		ServletFileUpload suf = new ServletFileUpload(df);
		suf.setHeaderEncoding("UTF-8");
		suf.setSizeMax(MAX_SIZE);
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			Map<String, Object> param = new HashMap<String, Object>(m);
			FileItem fileitem = (FileItem) i.next();
			if (!fileitem.isFormField()) {// 如果是文件
				if (fileitem.getName() == null || fileitem.getName().isEmpty() || "".equals(fileitem.getName())) continue;
				if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
					String fileName = fileitem.getName();
					Pattern pattern = Pattern.compile("[^\\\\]+\\.{1}\\w+$");
					Matcher mat = pattern.matcher(fileName);
					while (mat.find()) {
						fileName = mat.group();
					}
					try {
						file = new File(path + File.separator + URL);// 原图文件目录
						if (!file.exists()) {// 检查磁盘上是否存在path路径文件
							file.mkdirs();// 在磁盘上创建路径
						}
						if (param.containsKey("VALUE2")) {
							file = new File(path + File.separator + URL + File.separator + UUID.randomUUID() + fileName + "." + param.get("VALUE2"));// 原图
						} else {
							file = new File(path + File.separator + URL + File.separator + UUID.randomUUID() + fileName);// 原图
						}
						file.createNewFile();
					} catch (Exception e) {
						throw new ActionException("上传失败", e);
					}
					String filename = file.getName();
					String file_type = filename.toString().substring(filename.toString().lastIndexOf(".") + 1, filename.toString().length());
					InputStream is = null;
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					try {
						is = fileitem.getInputStream();
						file_type = file_type.toUpperCase();
						byte[] byte_ = null;
						if (file_type.equals("PNG")) {// 将图片处理一个压缩图片出来
							byte_ = UTIL.IMG.shrink(is, IMG.TYPE_PNG, 1024, 768);
							FileCopyUtils.copy(byte_, file);
						} else if (file_type.equals("JPG")) {
							byte_ = UTIL.IMG.shrink(is, IMG.TYPE_JPG, 1024, 768);
							FileCopyUtils.copy(byte_, file);
						} else {
							fileitem.write(file);// 非图片文件
						}
					} catch (Exception e) {
						throw new ActionException("上传失败", e);
					} finally {
						if (is != null) {
							try {
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (bos != null) {
							try {
								bos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					param.put("FILE_TYPE", file_type);// 文件扩展名
					String[] fileNames = fileName.split("[.]");
					param.put("FILE_NAME", fileNames[0]);// 文件名称
					param.put("ORIGINAL_PATH", file.getPath());// 原图片上传路径
					{// 防止同一文件夹下的文件名称重复
						Map<String, Object> fileMap = selectRepeatFileMax(param);
						if (fileMap != null && fileMap.containsKey("FILE_NAME_REPEAT")) {
							int FILE_NAME_REPEAT = Integer.parseInt(fileMap.get("FILE_NAME_REPEAT").toString()) + 1;
							param.put("FILE_NAME", param.get("FILE_NAME") + "(" + FILE_NAME_REPEAT + ")");
							param.put("FILE_NAME_REPEAT", FILE_NAME_REPEAT);
						} else {
							param.put("FILE_NAME_REPEAT", "0");
						}
					}
					param.put("SIZE", fileitem.getSize() / 1024 + "KB");// 文件大小
					logger.info("保存文件完毕：" + file.getAbsolutePath());
				}
				list.add(param);
			} else {
				try {
					m.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					throw new ActionException("上传失败", e);
				}
			}
		}
		return list;
	}

	/**
	 * 添加图像采集操作记录
	 */
	public void addPictrueRecord(Map<String, Object> param) {
		String ID = Dao.selectOne(xmlPath + "getSequence");
		int sq = 1;
		if (ID != null && !ID.isEmpty()) {
			sq = Integer.parseInt(ID) + sq;
		}
		Map<String, Object> recordMap = new HashMap<String, Object>();
		recordMap.putAll(param);
		recordMap.put("ID", sq);
		if (param.containsKey("INSTRUCTION")) {
			recordMap.put("INSTRUCTION", param.get("INSTRUCTION"));
		}
		if (param.containsKey("RECORD_REMARK")) {
			recordMap.put("REMARK", param.get("RECORD_REMARK"));
		}
		if (param.containsKey("REMARK")) {
			recordMap.put("VALUE4", param.get("REMARK"));
		}
		if (param.containsKey("ORIGINAL_PATH")) {
			recordMap.put("VALUE", param.get("ORIGINAL_PATH"));
		}
		if (param.containsKey("FILE_NAME")) {
			recordMap.put("VALUE1", param.get("FILE_NAME"));
		}
		if (param.containsKey("FILE_TYPE")) {
			recordMap.put("VALUE2", param.get("FILE_TYPE"));
		}
		if (param.containsKey("CREATE_DATE")) {
			recordMap.put("VALUE3", param.get("CREATE_DATE"));
		}
		boolean flag = Dao.insert(xmlPath + "addPictureRecord", recordMap) > 0 ? true : false;
		if (flag) {
			if (ID != null && !ID.isEmpty()) {
				Dao.update(xmlPath + "setSequence", sq);
			} else {
				Dao.insert(xmlPath + "initializeSequence", sq);
			}
		}
	}
	
	/**
	 * 添加图像采集操作记录
	 */
	public void addPictrueRecord2(Map<String, Object> param) {
		String ID = Dao.selectOne(xmlPath + "getSequence");
		int sq = 1;
		if (ID != null && !ID.isEmpty()) {
			sq = Integer.parseInt(ID) + sq;
		}
		Map<String, Object> recordMap = new HashMap<String, Object>();
		recordMap.putAll(param);
		recordMap.put("ID", sq);
		if (param.containsKey("INSTRUCTION")) {
			recordMap.put("INSTRUCTION", param.get("INSTRUCTION"));
		}
		if (param.containsKey("RECORD_REMARK")) {
			recordMap.put("REMARK", param.get("RECORD_REMARK"));
		}
		if (param.containsKey("REMARK")) {
			recordMap.put("VALUE4", param.get("REMARK"));
		}
		if (param.containsKey("ORIGINAL_PATH")) {
			recordMap.put("VALUE", param.get("ORIGINAL_PATH"));
		}
		if (param.containsKey("FILE_NAME")) {
			recordMap.put("VALUE1", param.get("FILE_NAME"));
		}
		if (param.containsKey("FILE_TYPE")) {
			recordMap.put("VALUE2", param.get("FILE_TYPE"));
		}
		if (param.containsKey("CREATE_DATE")) {
			recordMap.put("VALUE3", param.get("CREATE_DATE"));
		}
		boolean flag = Dao.insert(xmlPath + "addPictureRecord", recordMap) > 0 ? true : false;
		if (flag) {
			if (ID != null && !ID.isEmpty()) {
				Dao.update(xmlPath + "setSequence", sq);
			} else {
				Dao.insert(xmlPath + "initializeSequence", sq);
			}
		}
	}

	/**
	 * 根据条件向文件表中插入一条文件数据
	 * 
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-13 下午03:20:20
	 */
	public boolean insertFileMessage(Map<String, Object> param) {
		String ID = Dao.getSequence("SEQ_PICTUREFILE");
		param.put("ID", ID);
		return Dao.insert(xmlPath + "insertPictureFile", param) > 0;
	}

	/**
	 * 根据条件向文件表中修改一条文件数据
	 * 
	 * @param
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-13 下午03:20:20
	 */
	public boolean updateFileMessage(Map<String, Object> param) {
		return Dao.insert(xmlPath + "updateFileMessage", param) > 0;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            文件路径
	 * @author 付玉龙 fuyulong47@foxmail.com
	 * @date 2013-10-16 上午11:04:06
	 */
	public void deleteFile(String sPath, boolean type) {
		sPath = sPath.replaceAll("\\\\", "/");
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			if (type) {
				Map<String, Object> param = selectOperationFile(sPath);
				if (param != null) {
					param.put("INSTRUCTION", "DELETEPICTURE");
					param.put("RECORD_REMARK", "删除图片");
				}
				addPictrueRecord(param);
			}
		}
	}

	/**
	 * 根据文件全路径查看图片信息
	 */
	public Map<String, Object> selectOperationFile(String sPath) {
		sPath = sPath.replaceAll("\\\\", "/");
		return Dao.selectOne(xmlPath + "selectOperationFile", sPath);
	}

	/**
	 * 根据文件目录ID：CATALOG_ID查询该目录下所有图片
	 */
	public List<Map<String, Object>> selectFileList(String CATALOG_ID) {
		return Dao.selectList(xmlPath + "selectFileList", CATALOG_ID);
	}

	/**
	 * 查询该目录下文件名称重复的最大序列的图片信息
	 */
	public Map<String, Object> selectRepeatFileMax(Map<String, Object> param) {
		return Dao.selectOne(xmlPath + "selectRepeatFileMax", param);
	}

	// /**
	// * 根据id获取文件的缩略图信息
	// * @param file_id 文件id
	// * @author DELONG
	// * @date 2013-3-6 上午09:41:44
	// * @throws IOException
	// * @return 文件id
	// * @throws SQLException
	// * @throws IOException
	// */
	// @SuppressWarnings("unchecked")
	// public byte[] getFileThumbById(String file_id) throws SQLException,
	// IOException{
	// if(!isNull(file_id)){
	// Map<String,Object> m = new HashMap<String, Object>();
	// m.put("ID", file_id);
	// Map<String,Object> map = (Map<String, Object>)
	// Dao.selectOne(xmlPath+"getFileThumbById",m);
	//
	// String fileName = map.get("FILE_NAME").toString().replaceAll(" ", "");
	// String[] FILE_NAME = fileName.split("[.]");//文件没有后缀名的将后缀名扑充---付玉龙
	// if(FILE_NAME.length <= 1){
	// map.put("FILE_NAME", FILE_NAME[0]+"."+map.get("FILE_TYPE"));
	// }
	//
	// Blob blob = (Blob)map.get("FILE_BYTE");
	// InputStream inStream = blob.getBinaryStream();
	// byte[] file_byte = FileCopyUtils.copyToByteArray(inStream);
	// return file_byte;
	// }
	// return null;
	// }
	//
	// /**
	// * 根据id获取文件的原图信息
	// * @param file_id 文件id
	// * @author DELONG
	// * @date 2013-3-6 上午09:41:44
	// * @throws IOException
	// * @return 文件id
	// * @throws SQLException
	// * @throws IOException
	// */
	// @SuppressWarnings("unchecked")
	// public byte[] getFileByteById(String file_id) throws SQLException,
	// IOException{
	// if(!isNull(file_id)){
	// Map<String,Object> m = new HashMap<String, Object>();
	// m.put("ID", file_id);
	// Map<String,Object> map = (Map<String, Object>)
	// Dao.selectOne(xmlPath+"getFileByteById",m);
	//
	// String fileName = map.get("FILE_NAME").toString().replaceAll(" ", "");
	// String[] FILE_NAME = fileName.split("[.]");//文件没有后缀名的将后缀名扑充---付玉龙
	// if(FILE_NAME.length <= 1){
	// map.put("FILE_NAME", FILE_NAME[0]+"."+map.get("FILE_TYPE"));
	// }
	//
	// Blob blob = (Blob)map.get("FILE_BYTE");
	// InputStream inStream = blob.getBinaryStream();
	// byte[] file_byte = FileCopyUtils.copyToByteArray(inStream);
	// return file_byte;
	// }
	// return null;
	// }
	// /**
	// * 根据id获取文件的缩略图信息
	// * @param file_id 文件id
	// * @author DELONG
	// * @date 2013-3-6 上午09:41:44
	// * @throws IOException
	// * @return 文件id
	// * @throws SQLException
	// * @throws IOException
	// */
	// @SuppressWarnings("unchecked")
	// public Map<String,Object> getFileInfoById(String file_id) throws
	// SQLException, IOException{
	// if(!isNull(file_id)){
	// Map<String,Object> m = new HashMap<String, Object>();
	// m.put("ID", file_id);
	// Map<String,Object> map = (Map<String, Object>)
	// Dao.selectOne(xmlPath+"getFileInfoById",m);
	//
	// String fileName = map.get("FILE_NAME").toString().replaceAll(" ", "");
	// String[] FILE_NAME = fileName.split("[.]");//文件没有后缀名的将后缀名扑充---付玉龙
	// if(FILE_NAME.length <= 1){
	// map.put("FILE_NAME", FILE_NAME[0]+"."+map.get("FILE_TYPE"));
	// }
	//
	// Blob blob = (Blob)map.get("FILE_BYTE");
	// InputStream inStream = blob.getBinaryStream();
	// byte[] file_byte = FileCopyUtils.copyToByteArray(inStream);
	// map.put("FILE_BYTE", file_byte);
	// blob = (Blob)map.get("FILE_THUMB");
	// inStream = blob.getBinaryStream();
	// byte[] file_thumb = FileCopyUtils.copyToByteArray(inStream);
	// map.put("FILE_THUMB", file_thumb);
	// return map;
	// }
	// return null;
	// }
	/**
	 * 根据id删除文件
	 * 
	 * @param file_id
	 *            文件id
	 * @author DELONG
	 * @date 2013-3-6 上午09:41:44
	 * @return int
	 */
	public boolean deleteFileById(String file_id) {
		if (!isNull(file_id)) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("ID", file_id);
			return Dao.delete(xmlPath + "deleteFileById", m) > 0;
		}
		return false;
	}

	// /**
	// * 图片文件下载
	// * @param file_id 文件id
	// * @author DELONG
	// * @date 2013-3-6 上午09:41:44
	// * @return
	// */
	// public HttpServletResponse download(String file_name,byte[] file_byte,
	// HttpServletResponse response) {
	// try {
	// // 清空response
	// response.reset();
	// // 设置response的Header
	// response.setCharacterEncoding("UTF-8");
	// response.addHeader("Content-Disposition", "attachment;filename=" + new
	// String(file_name.getBytes("GB2312"), "ISO-8859-1"));
	// response.addHeader("Content-Length", "" + file_byte.length);
	// OutputStream toClient = new
	// BufferedOutputStream(response.getOutputStream());
	// response.setContentType("application/octet-stream");
	// toClient.write(file_byte);
	// // 关闭流
	// toClient.close();
	// } catch (IOException ex) {
	// logger.error(ex);
	// }
	// return response;
	// }
	// /**
	// * 根据文件夹id创建子文件夹。*根据父id，在PICTURECATALOG表中创建子目录
	// * @param id – 父文件夹的id
	// * @param folderName –文件夹名称
	// * @param readonly –只读标识
	// * @param auth_code –加密狗序列号
	// * @author 付玉龙 438473135@qq.com
	// * @date 2013-3-6 下午04:22:27
	// */
	// public int createChildCatalog (int id,String folderName,int
	// readonly,String auth_code){
	// if(id == 0 || folderName == null || readonly ==0 || auth_code ==null){
	// return -1;
	// }else{
	// Map<String,Object> param = new HashMap<String,Object>();
	// param.put("PARENT_ID", id);
	// param.put("FILENAME", folderName);
	// param.put("READONLY", readonly);
	// param.put("AUTH_CODE", auth_code);
	// int i = Dao.insert(xmlPath+"insertCatalog",param);
	// return i;
	// }
	// }
	//
	// public List<?> getFileDetailList (String CATALOG_ID){
	// return Dao.selectList(xmlPath+"getFileDetailList",CATALOG_ID);
	// }

	// /**
	// * 获取图片内容列表,图片均为查取缩略图字段。
	// * renterName – 客户名称
	// * renterCode – 客户编号
	// * path – 图片存放文件目录，多级目录用“/”隔开，格式如：核心要件/身份证
	// * 付玉龙 438473135@qq.com
	// * 2013-3-7 上午10:08:02
	// * @throws SQLException
	// * @throws IOException
	// */
	// @SuppressWarnings("unchecked")
	// public List<Map<String,Object>> getFileList (String renterName ,String
	// renterCode,String path) throws SQLException, IOException {
	// String catalogId = getCatalogIdByPath(renterName, renterCode, path);
	// Map<String,Object> param = new HashMap<String,Object>();
	// param.put("CATALOG_ID", catalogId);
	// if(isNull(catalogId)){
	// return null;
	// }else{
	// List<Map<String,Object>> listFile = (List)
	// Dao.selectList(xmlPath+"selectFileList", param);
	// Map<String,Object> fileMap = new HashMap<String,Object>();
	// for(int i = 0; i < listFile.size(); i++){
	// fileMap = listFile.get(i);
	// Blob blob = (Blob)listFile.get(i).get("FILE_THUMB");
	// InputStream inStream = blob.getBinaryStream();
	// ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
	// byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
	// int rc = 0;
	// while ((rc = inStream.read(buff, 0, 100)) > 0) {
	// swapStream.write(buff, 0, rc);
	// }
	// byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果
	// listFile.get(i).put("FILE_THUMB", in_b);
	//
	// if(fileMap != null){
	// String fileName = fileMap.get("FILE_NAME").toString().replaceAll(" ",
	// "");
	// String[] FILE_NAME = fileName.split("[.]");//文件没有后缀名的将后缀名扑充
	// if(FILE_NAME.length <= 1){
	// fileMap.put("FILE_NAME", FILE_NAME[0]+"."+fileMap.get("FILE_TYPE"));
	// }
	// }
	// }
	// return listFile;
	// }
	// }

	/**
	 * 获取图片内容id最大的，即最新存入的。,图片均为查取缩略图字段。
	 * 
	 * @param renterName
	 *            – 客户名称
	 * @param renterCode
	 *            – 客户编号
	 * @param path
	 *            – 图片存放文件目录，多级目录用“/”隔开，格式如：核心要件/身份证
	 * @author 付玉龙 438473135@qq.com
	 * @throws SQLException
	 * @throws IOException
	 * @date 2013-3-8 下午01:57:41
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFileOne(String renterName, String renterCode, String path) throws SQLException, IOException {
		String catalogId = getCatalogIdByPath(renterName, renterCode, path);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CATALOG_ID", catalogId);
		if (isNull(catalogId)) {
			return null;
		} else {
			Map<String, Object> fileMap = (Map<String, Object>) Dao.selectOne(xmlPath + "selectFileMap", param);
			if (fileMap != null) {
				String fileName = fileMap.get("FILE_NAME").toString().replaceAll(" ", "");
				String[] FILE_NAME = fileName.split("[.]");// 文件没有后缀名的将后缀名扑充---付玉龙
				if (FILE_NAME.length <= 1) {
					fileMap.put("FILE_NAME", FILE_NAME[0] + "." + fileMap.get("FILE_TYPE"));
				}
			}
			return fileMap;
		}
	}

	/**
	 * 获取图片内容最大的id的图片信息Map
	 * 
	 * @param catalogId
	 *            – 最后一层目录id
	 * @param path
	 *            – 图片存放文件目录，多级目录用“/”隔开，格式如：核心要件/身份证
	 * @throws SQLException
	 * @throws IOException
	 * @date 2013-3-8 下午01:57:41
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getFileMaxIdMap(Map<String, Object> param) {
		return (Map<String, Object>) Dao.selectOne(xmlPath + "selectFileMap", param);
	}

	/**
	 * 该方法在数据库中添加一个以folderName为文件夹名 称的根目录，只读标识默认为0。*在PICTURECATALOG表中创建文件夹目录
	 * 
	 * @param folderName
	 *            – 文件夹名称
	 * @param auth_code
	 *            – 加密狗序列号
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-3-8 下午02:38:52
	 */
	public int createRootCatalog(String folderName, String auth_code) {
		if (folderName != null && folderName != "" && auth_code != null && auth_code != "") {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("FILENAME", folderName);
			param.put("AUTH_CODE", auth_code);
			int i = Dao.insert(xmlPath + "insertRootCatalog", param);
			return i;
		} else {
			return -1;
		}
	}

	/**
	 * 创建客户后调用此方法生成该客户下的预置文件夹，
	 * 根目录的文件夹名称为“客户名称_客户编号”*该方法是从PRESETCATALOG表中读取需要创建的文件夹目录，
	 * 根据客户名称和客户编号在PICTURECATALOG表中创建出该客户对应的一套文件夹目录
	 * 
	 * @param renterName
	 *            – 客户名称
	 * @param renterCode
	 *            – 客户编号
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-3-7 下午02:07:58
	 */
	@SuppressWarnings("unchecked")
	public boolean createCustCatalog(String renterName, String renterCode) {
		boolean result = false;
		Map<String, Object> parentMap = new HashMap<String, Object>();
		parentMap = (Map<String, Object>) Dao.selectOne(xmlPath + "selectRootCatalogId", parentMap);// 查询根目录数据
		if (parentMap != null) {
			List<Map<String, Object>> subsetCatalogListOne = (List) Dao.selectList("selectCatalogList", parentMap);// 查询子菜单数据
			String newCatalogId = Dao.getSequence("SEQ_PICTURECATALOG");
			parentMap.put("ID", newCatalogId);
			parentMap.put("FILENAME", renterName + "_" + renterCode);
			int i = Dao.insert(xmlPath + "autoInsertCatalog", parentMap);// 添加根目录
			String parentCatalogId = parentMap.get("ID").toString();// 下级目录的父ID
			if (i > 0) {
				result = true;
				if (subsetCatalogListOne != null && subsetCatalogListOne.size() > 0) {
					insertSubstCatalogList(result, i, subsetCatalogListOne, parentCatalogId);
				}
			}
		}
		return result;
	}

	// 添加子目录
	@SuppressWarnings("unchecked")
	public boolean insertSubstCatalogList(boolean result, int i, List<Map<String, Object>> subsetCatalogList, String parentCatalogId) {
		for (int j = 0; j < subsetCatalogList.size(); j++) {
			Map<String, Object> subsetCatalogMap = subsetCatalogList.get(j);
			if (subsetCatalogMap != null) {
				List<Map<String, Object>> subsetCatalogListNext = (List) Dao.selectList("selectCatalogList", subsetCatalogMap);// SQL条件为subsetCatalogMap里的ID
																																// 查询子菜单数据
				String newCatalogId = Dao.getSequence("SEQ_PICTURECATALOG");
				subsetCatalogMap.put("ID", newCatalogId);
				subsetCatalogMap.put("PARENT_ID", parentCatalogId);
				i = Dao.insert(xmlPath + "autoInsertCatalog", subsetCatalogMap);// 添加子目录
				String parentCatalogIdNext = subsetCatalogMap.get("ID").toString();// 下级目录的父ID
				if (i > 0) {
					if (subsetCatalogListNext != null && subsetCatalogList.size() > 0) {
						insertSubstCatalogList(result, i, subsetCatalogListNext, parentCatalogIdNext);
					} else {
						break;
					}
				} else {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 修改文件夹名称
	 * 此方法不能用于修改目录结构
	 * 
	 * @param renterName
	 *            – 客户名称
	 * @param renterCode
	 *            – 客户编号
	 * @param oldPath
	 *            修改前的目录
	 * @param newPath
	 *            新的目录
	 *            目录 = 第二层文件夹到修改目录名称的文件夹
	 * @author 付玉龙 438473135@qq.com
	 * @throws Exception
	 * @date 2013-4-19 下午03:11:37
	 */
	public void changeCatalog(String renterName, String renterCode, String oldPath, String newPath) throws Exception {
		String pdOldCatalogId = getCatalogIdByPathNoCreat(renterName, renterCode, oldPath);// 根据文件路径查询文件ID
		oldPath = rootCatalog(renterName, renterCode, oldPath);// 获取文件全路径----------oldPath
		newPath = rootCatalog(renterName, renterCode, newPath);// 获取文件全路径----------newPath
		String[] oldCatalogName = (oldPath).split("/");// 根据路径截取出所有文件夹名称
		String[] newCatalogName = (newPath).split("/");// 根据路径截取出所有文件夹名称
		String pdNewPath = "";// ---所修改文件的路径------查询系统是否有该目录时用到
		String pdOldPath = "";// ---所修改文件的old路径------查询系统是否有该目录时用到
		Map<String, Object> newNameMap = new HashMap<String, Object>();// 修改文件夹名称时放参数的容器
		if (pdOldCatalogId != null) {// 判断系统中是否有要修改的文件夹
			if (oldCatalogName.length >= 2 && newCatalogName.length >= 2 && oldCatalogName.length == newCatalogName.length) {// 确保有需要修改的文件夹
				for (int i = newCatalogName.length - 1; i > 0; i--) {// 对新旧目录的文件夹名称做比对----根文件夹不用对比所以
																		// i = 1
					if (!newCatalogName[i].equals(oldCatalogName[i])) {// true为文件夹名称不一致
						for (int j = 1; j <= i; j++) {// 重组文件目录
							pdNewPath += newCatalogName[j] + "/";
							pdOldPath += oldCatalogName[j] + "/";
						}
						String pdCatalogId = getCatalogIdByPathNoCreat(renterName, renterCode, pdNewPath);// 查询系统是否已经存在新的文件目录
						String oldCatalogId = getCatalogIdByPathNoCreat(renterName, renterCode, pdOldPath);// 修改的文件目录ID
						if (pdCatalogId != null) {// 系统中已存在新路径----需要将原有路径下的文件与新路径下的文件合并
							deleteCatalog(oldCatalogId);// 根据目录ID删除掉修改的目录
							mergerDirectory(pdCatalogId, oldCatalogId);// 执行合并***************************
						} else {// 系统中不存在新路径
							newNameMap.put("FILENAME", newCatalogName[i]);// 新文件夹名称
							newNameMap.put("ID", oldCatalogId);// 要修改文件的目录id
							Dao.update(xmlPath + "updateFileName", newNameMap);
						}
						// 为下一次重组目录做准备
						oldCatalogName[i] = newCatalogName[i];// 新的oldCatalogName
						pdNewPath = "";
						pdOldPath = "";
					}
				}
			} else {
				throw new Exception("修改的新旧目录结构不一致！不能用于修改目录结构！");
			}
		} else {
			throw new Exception("要修改的文件在系统中不存在！请先创建文件目录再修改！");
		}
	}

	/**
	 * @param newCatalogId
	 *            系统已经存在的文件目录ID-----new
	 * @param oldCatalogId
	 *            修改的文件目录ID-----old
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-4-22 下午04:00:41
	 */
	@SuppressWarnings("unchecked")
	public void mergerDirectory(String newCatalogId, String oldCatalogId) {
		Map<String, Object> newNameMap = new HashMap<String, Object>();// 合并文件时放参数的容器----同时也是下一层文件合并所需的参数
		boolean existence = false;// ---false---代表修改的文件夹下的目录与系统中已存在的没有相同的
		newNameMap.put("SELECT_PARENT_ID", newCatalogId);
		List<Object> listNewCatalog = (List<Object>) Dao.selectList(xmlPath + "selectListNewCatalog", newNameMap);// 查询系统已存在文件夹的下层目录信息
		newNameMap.put("SELECT_PARENT_ID", oldCatalogId);
		List<Object> listOldCatalog = (List<Object>) Dao.selectList(xmlPath + "selectListNewCatalog", newNameMap);// 查询修改的文件夹的下层目录信息
		if (listNewCatalog != null && listOldCatalog != null && listNewCatalog.size() >= 1 && listOldCatalog.size() >= 1) {
			for (int k = 0; k < listOldCatalog.size(); k++) {// 遍历对比两个文件夹中的文件name
				for (int l = 0; l < listNewCatalog.size(); l++) {
					if (((Map<String, Object>) listOldCatalog.get(k)).get("FILENAME").toString()
							.equals(((Map<String, Object>) listNewCatalog.get(l)).get("FILENAME").toString())) {
						existence = true;
						newNameMap.put("newCatalogId", ((Map<String, Object>) listNewCatalog.get(l)).get("ID"));// 下一层文件合并所需的newCatalogId
					}
				}
				if (existence) {// 修改的文件夹下的目录与系统中已存在的有相同的
					deleteCatalog(((Map<String, Object>) listOldCatalog.get(k)).get("ID").toString());// 根据目录ID删除掉修改的目录
					newNameMap.put("oldCatalogId", ((Map<String, Object>) listOldCatalog.get(k)).get("ID"));// 下一层文件合并所需的oldCatalogId
					mergerDirectory(newNameMap.get("newCatalogId").toString(), newNameMap.get("oldCatalogId").toString());// 继续合并下一层
				} else {// 修改的文件夹下的目录与系统中已存在的没有相同的
					newNameMap.put("ID", ((Map<String, Object>) listOldCatalog.get(k)).get("ID"));// 需要合并的文件目录ID
					newNameMap.put("PARENT_ID", newCatalogId);// 合并后的父ID
					Dao.update(xmlPath + "updateFileName", newNameMap);
				}
			}
		} else {// 根据附件的初始目录ID去修改附件的父ID = 附件修改后的目录ID
			String CATALOG_ID = oldCatalogId;// 修改条件----附件的初始目录ID
			List<Object> listAttachmentInformation = (List<Object>) Dao.selectList(xmlPath + "selectCreditAttachment", CATALOG_ID);// 查询需要修改目录ID的附件信息
			if (listAttachmentInformation != null && listAttachmentInformation.size() >= 1) {
				for (int p = 0; p < listAttachmentInformation.size(); p++) {
					newNameMap.put("ID", ((Map<String, Object>) listAttachmentInformation.get(p)).get("ID"));
					newNameMap.put("CATALOG_ID", newCatalogId);// newCatalogId-----值----附件修改后的目录ID
					Dao.update(xmlPath + "updateAttachmentInformation", newNameMap);// 修改附件目录ID
				}
			}
		}
	}

	/**
	 * 根据目录ID删除目录
	 * 
	 * @param CatalogId
	 *            要删除的目录ID
	 * @author 付玉龙 438473135@qq.com
	 * @date 2013-4-23 上午10:09:22
	 */
	public int deleteCatalog(String ID) {
		return Dao.delete(xmlPath + "deleteCatalog", ID);
	}

	/**
	 * 判断文件是否为图片<br>
	 * <br>
	 * 
	 * @param pInput
	 *            文件名<br>
	 * @param pImgeFlag
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public boolean isPicture(String pInput, String pImgeFlag) {
		// 文件名称为空的场合
		if (isNull(pInput)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		// String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1,
		// pInput.length());
		String tmpName = pInput;
		// 声明图片后缀名数组
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" }, { "gif", "2" }, { "jfif", "3" }, { "jpe", "4" }, { "jpeg", "5" }, { "jpg", "6" },
				{ "png", "7" }, { "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (!isNull(pImgeFlag) && imgeArray[i][0].equals(tmpName.toLowerCase()) && imgeArray[i][1].equals(pImgeFlag)) { return true; }
			// 判断符合全部类型的场合
			if (isNull(pImgeFlag) && imgeArray[i][0].equals(tmpName.toLowerCase())) { return true; }
		}
		return false;
	}

	/**
	 * 判断字符串是否为空 <br>
	 * 
	 * @param str
	 * @throws Exception
	 */
	public boolean isNull(String str) {
		if (str == null || "".equals(str.trim())) { return true; }
		return false;
	}

	public void qwe() {
		String s = "c:\\qwe\\qwe\\qwe\\123.png";
		Pattern pattern = Pattern.compile("[^\\\\]+\\.{1}\\w+$");
		Matcher mat = pattern.matcher(s);
		while (mat.find()) {
			System.out.println(mat.group());
		}
	}

	public static void main(String[] args) {
		new FileCatalogUtil().qwe();
	}
	
	/**
	 * 根据条件向文件表中插入一条文件数据
	 * @param catalogId 文件目录id 
	 * @param file_name 文件名称
	 * @param file_type 文件扩展名
	 * @param is 文件流
	 * @author DELONG
	 * @date 2013-3-6  上午09:41:44
	 * @throws IOException 
	 * @return 文件id
	 */
	public String insertFile (String catalogId,String file_name,String file_type,InputStream is,String remark) throws IOException {
		if(file_name != null && file_type != null && !"".equals(file_type) && !"".equals(file_name)){
			Map<String,Object> param = new HashMap<String, Object>();
			String ID = Dao.getSequence("PICTURE.SEQ_PICTUREFILE");
			param.put("ID", ID);
		    param.put("FILE_NAME", file_name);
		    param.put("FILE_TYPE", file_type);
		    param.put("CATALOG_ID", catalogId);
		    param.put("REMARK", remark);
		    byte[] file_byte = FileCopyUtils.copyToByteArray(is);
		    if(file_byte.length > 0 && isPicture(file_type, null)){
		    	logger.debug("byte[]大于零");
		    	try {
//		    		file_byte=IMG.shrink2is(is, file_type, 1024, 768);
//		    		file_byte = IMG.shrink(file_byte, IMG.TYPE_JPG, 1024, 768);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    	param.put("FILE_BYTE", file_byte);
		    	//将图片处理出一个缩略图出来
			    try {
				    param.put("FILE_THUMB", file_byte);
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        } 
		    }
		    int a = Dao.insert(xmlPath+"insertPictureFile", param);
		    if(a>0){
		    	return ID;
		    }
		}
		return null;
	}
	
	
	/**
	 * 根据id获取文件的原图信息
	 * @param file_id 文件id 
	 * @author DELONG
	 * @date 2013-3-6  上午09:41:44
	 * @throws IOException 
	 * @return 文件id
	 * @throws SQLException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public byte[] getFileByteById(String file_id) throws SQLException, IOException{
		if(!isNull(file_id)){
			Map<String,Object> m = new HashMap<String, Object>();
			m.put("ID", file_id);
			Map<String,Object> map = (Map<String, Object>) Dao.selectOne(xmlPath+"getFileByteById",m);
			
			String fileName = map.get("FILE_NAME").toString().replaceAll(" ", "");
			String[] FILE_NAME = fileName.split("[.]");//文件没有后缀名的将后缀名扑充---付玉龙
			if(FILE_NAME.length <= 1){
				map.put("FILE_NAME", FILE_NAME[0]+"."+map.get("FILE_TYPE"));
			}
			
			Blob blob = (Blob)map.get("FILE_BYTE");
			InputStream  inStream = blob.getBinaryStream();
			byte[] file_byte = FileCopyUtils.copyToByteArray(inStream);
			return file_byte;
		}
		return null;
	}
	
	/**
	 * 图片文件下载
	 * @param file_id 文件id 
	 * @author DELONG
	 * @date 2013-3-6  上午09:41:44
	 * @return
	 */
	public HttpServletResponse download(String file_name,byte[] file_byte, HttpServletResponse response) {
		try {
			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(file_name.getBytes("GB2312"), "ISO-8859-1"));
			response.addHeader("Content-Length", "" + file_byte.length);
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(file_byte);
			// 关闭流
			toClient.close();
		} catch (IOException ex) {
			logger.error(ex);
		}
		return response;
	}
	
	
	/**
	 * 获取图片内容列表,图片均为查取缩略图字段。
	 * renterName – 客户名称
	 * renterCode – 客户编号
	 * path – 图片存放文件目录，多级目录用“/”隔开，格式如：核心要件/身份证
	 * 付玉龙  438473135@qq.com
	 * 2013-3-7  上午10:08:02
	 * @throws SQLException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getFileList (String renterName ,String renterCode,String path) throws SQLException, IOException {
		String catalogId = getCatalogIdByPath(renterName, renterCode, path);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("CATALOG_ID", catalogId);
		if(isNull(catalogId)){
			return null;
		}else{
			List<Map<String,Object>> listFile =Dao.selectList(xmlPath+"selectFileList", param);
			Map<String,Object> fileMap = new HashMap<String,Object>();
			for(int i = 0; i < listFile.size(); i++){
				fileMap = listFile.get(i);
				Blob blob = (Blob)listFile.get(i).get("FILE_THUMB");
				InputStream  inStream = blob.getBinaryStream();
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
				byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据 
				int rc = 0; 
				while ((rc = inStream.read(buff, 0, 100)) > 0) { 
					swapStream.write(buff, 0, rc); 
				} 
				byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果 
				listFile.get(i).put("FILE_THUMB", in_b);
				
				if(fileMap != null){
					String fileName = fileMap.get("FILE_NAME").toString().replaceAll(" ", "");
					String[] FILE_NAME = fileName.split("[.]");//文件没有后缀名的将后缀名扑充
					if(FILE_NAME.length <= 1){
						fileMap.put("FILE_NAME", FILE_NAME[0]+"."+fileMap.get("FILE_TYPE"));
					}
				}
			}
			return listFile;
		}
	}
}
