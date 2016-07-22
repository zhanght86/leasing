package com.pqsoft.util;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import de.idyl.winzipaes.AesZipFileEncrypter;
import de.idyl.winzipaes.impl.AESEncrypter;
import de.idyl.winzipaes.impl.AESEncrypterBC;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.pqsoft.skyeye.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/********** Zip打包工具类*@auth: king 2014年9月26日 *************************/
public class ZipUtil {

	private static Logger logger = LoggerFactory.getLogger(ZipUtil.class);
	/**
	 * 生成ZIP文件
	 * 
	 * @param Zip_File
	 *            zip文件创建的路径包含文件名及后缀 XX/XX/CC.zip
	 * @param ZipFilePathList
	 *            zip文件需要包含的具体文件列表
	 * @param isExists
	 *            生成zip文件后是否保留zip外的具体文件 true 保留 false 不保留 
	 * @return zip文件
	 * @author King 2014年9月26日
	 */
	public static File createZipFile(String Zip_File,
			List<File> ZipFilePathList, boolean isExists) {
		String strZipPath=Zip_File.substring(0,Zip_File.lastIndexOf(File.separator));
		new File(strZipPath).mkdirs();
		byte[] buffer = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(Zip_File));
			for (int i = 0; i < ZipFilePathList.size(); i++) {
				FileInputStream fis = new FileInputStream(
						ZipFilePathList.get(i));
				out.putNextEntry(new ZipEntry(ZipFilePathList.get(i).getName()));
				// 设置压缩文件内的字符编码，不然会变成乱码
				out.setEncoding("GBK");
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException("生成zip包失败");
		}
		if (!isExists)
			for (int i = 0; i < ZipFilePathList.size(); i++) {
				ZipFilePathList.get(i).delete();
			}

		return new File(Zip_File);
	}

	/**
	 *
	 * @description：压缩文件操作
	 * @param list
	 *            要压缩的文件路径
	 * @return
	 */
	public static ByteArrayOutputStream zipFiles(List<Map<String,Object>> list)
	{
		ZipOutputStream zos = null;
		ByteArrayOutputStream baos =new ByteArrayOutputStream();
		try
		{
			// 创建一个Zip输出流
			zos = new ZipOutputStream(baos);
			// 启动压缩
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				File file = new File(map.get("PDF_PATH").toString());
				zipFile(zos, map.get("TPM_TYPE").toString() + File.separator, file);
			}
			return baos;
//				System.out.println("******************压缩完毕********************");
		}
		finally
		{
			try
			{
				if (zos != null)
				{
					zos.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}


	/**
	 *
	 * @description：压缩单个文件到目录中
	 * @param zos
	 *            zip输出流
	 * @param oppositePath
	 *            在zip文件中的相对路径
	 * @param file
	 *            要压缩的的文件
	 */
	public static void zipFile(ZipOutputStream zos, String oppositePath,
			File file)
	{
		// 创建一个Zip条目，每个Zip条目都是必须相对于根路径
		InputStream is = null;
		try
		{
			ZipEntry entry = new ZipEntry(oppositePath + file.getName());
			// 将条目保存到Zip压缩文件当中
			zos.putNextEntry(entry);
			// 从文件输入流当中读取数据，并将数据写到输出流当中.
			is = new FileInputStream(file);

			int length = 0;

			int bufferSize = 1024;

			byte[] buffer = new byte[bufferSize];

			while ((length = is.read(buffer, 0, bufferSize)) >= 0)
			{
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if (is != null)
				{
					is.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/*********************************** 华丽的分割线 ***********************************/

	/**
	 * 解压源文件到指定的目录
	 *
	 * @author 钟林俊
	 * @param srcFile 源文件
	 * @param destPath 目的目录
	 * @param overwrite 是否覆盖已存在的文件
     */
	public static void unzip(File srcFile, String destPath, boolean overwrite) {
		Preconditions.checkArgument(srcFile.exists(), "源文件" + srcFile.getName() + "不存在！");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(destPath), "目标目录为空！");
		BufferedOutputStream bos = null;
		ZipInputStream zis = null;
		try {
			zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(srcFile)));
			java.util.zip.ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				File outputFile = new File(destPath + File.separator + entry.getName());
				if ((!outputFile.exists() || !outputFile.isDirectory()) && entry.isDirectory()) {
					if (!outputFile.mkdirs()) {
						throw new IOException("文件夹" + outputFile.getName() + "创建失败");
					}
				} else {
					Preconditions.checkArgument(!(!overwrite && outputFile.exists()), "目的文件" + outputFile.getName() + "已存在！");
					bos = new BufferedOutputStream(new FileOutputStream(outputFile));
					int i;
					byte[] bytes = new byte[1024];
					while ((i = zis.read(bytes)) != -1) {
						bos.write(bytes, 0, i);
					}
					IOUtils.closeQuietly(bos);
				}
			}
		} catch (IOException e) {
			logger.error("", e);
			throw new RuntimeException("解压文件" + srcFile.getName() + "失败！");
		} finally {
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(zis);
		}
	}

	/**
	 * 通过源路径所在的文件在指定的的目录生成指定文件名的压缩文件（支持文件夹递归）
	 *
	 * @author 钟林俊
	 * @param srcPath 压缩源路径
	 * @param destPath 目标文件路径
	 * @param zipFileName 目标文件名（后缀不是.zip的话会添加.zip）
	 * @param overwrite 如果目标已存在，是否进行覆盖
     * @return 压缩文件
     */
	public static File zip(String srcPath, String destPath, String zipFileName, boolean overwrite) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(srcPath), "源路径为空！");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(destPath), "目的路径为空！");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(zipFileName), "压缩文件名为空！");
		File srcFile = new File(srcPath);
		String fileName = zipFileName;
		if(!zipFileName.endsWith(".zip")){
			fileName += ".zip";
		}
		File destFile = new File(destPath, fileName);
		return zip(srcFile, destFile, overwrite);
	}

	/**
	 * 通过源路径所在的文件在指定的的目录生成指定文件名的带密码的压缩文件（支持文件夹递归）
	 *
	 * @author 钟林俊
	 * @param srcPath 压缩源路径
	 * @param destPath 目标文件路径
	 * @param zipFileName 目标文件名（后缀不是.zip的话会添加.zip）
	 * @param password 解压所需的密码
	 * @param overwrite 如果目标已存在，是否进行覆盖
     * @return 压缩文件
     */
	public static File zipWithPassword(String srcPath, String destPath, String zipFileName, String password, boolean overwrite){
		Preconditions.checkArgument(!Strings.isNullOrEmpty(srcPath), "源路径为空！");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(destPath), "目的路径为空！");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(zipFileName), "压缩文件名为空！");
		File srcFile = new File(srcPath);
		String fileName = zipFileName;
		if(!zipFileName.endsWith(".zip")){
			fileName += ".zip";
		}
		File destFile = new File(destPath, fileName);
		return zipWithPassword(srcFile, destFile, password, overwrite);
	}

	/**
	 * 通过源文件生成压缩文件（支持文件夹递归）
	 *
	 * @author 钟林俊
	 * @param srcFile 压缩源文件（夹）
	 * @param destFile 要生成的压缩文件
	 * @param overwrite 如果目标已存在，是否进行覆盖
     * @return 生成的压缩文件
     */
	public static File zip(File srcFile, File destFile, boolean overwrite){
		Preconditions.checkArgument(srcFile.exists(), "源文件" + srcFile.getName() + "不存在！");
		Preconditions.checkArgument(!(!overwrite && destFile.exists()), "目标文件" + destFile.getName() + "已存在！");
		java.util.zip.ZipOutputStream zos = null;
		try {
			zos = new java.util.zip.ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)));
			//压缩
			doZip(zos, srcFile, srcFile.getName());
			return destFile;
		} catch (IOException e) {
			logger.error("", e);
			throw new RuntimeException(srcFile.getName() + "生成压缩文件失败！");
		} finally {
			IOUtils.closeQuietly(zos);
		}
	}

	/**
	 * 通过源文件生成带密码的目标压缩文件（支持文件夹递归）
	 *
	 * @author 钟林俊
	 * @param srcFile 压缩源文件（夹）
	 * @param destFile 要生成的压缩文件
	 * @param password 解密所需的密码
	 * @param overwrite 如果目标已存在，是否进行覆盖
	 * @return 生成的压缩文件
	 */
	public static File zipWithPassword(File srcFile, File destFile, String password, boolean overwrite){
		Preconditions.checkArgument(srcFile.exists(), "源文件" + srcFile.getName() + "不存在！");
		Preconditions.checkArgument(!(!overwrite && destFile.exists()), "目标文件" + destFile.getName() + "已存在！");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "解压密码为空！");
		AESEncrypter aesEncrypter = new AESEncrypterBC();
		AesZipFileEncrypter aesZipFileEncrypter = null;
		try {
			//初始化带密码压缩的工具对象
			aesZipFileEncrypter = new AesZipFileEncrypter(destFile, aesEncrypter);
			//压缩
			doZip(aesZipFileEncrypter, srcFile, srcFile.getName(), password);
		} catch (IOException e) {
			logger.error("", e);
			throw new RuntimeException(srcFile.getName() + "生成压缩文件失败！");
		} finally {
			try {
				if (aesZipFileEncrypter != null) {
					aesZipFileEncrypter.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return destFile;
	}
	/**
	 * 带密码压缩指定的文件（夹）
	 *
	 * @author 钟林俊
	 * @param aesZipFileEncrypter 压缩文件工具类
	 * @param file 压缩文件源
	 * @param superiorPath 上级目录路径
	 * @param password 密码
	 * @throws IOException
	 */
	private static void doZip(AesZipFileEncrypter aesZipFileEncrypter, File file, String superiorPath, String password) throws IOException {
		if(file != null){
			if(file.isDirectory()){
				//noinspection ConstantConditions
				for(File subFile : file.listFiles()){
					String superior = superiorPath + File.separator + subFile.getName();
					doZip(aesZipFileEncrypter, subFile, superior, password);
				}
			}else{
				aesZipFileEncrypter.add(file, superiorPath, password);
			}
		}
	}

	/**
	 * 压缩指定的文件（夹）
	 *
	 * @author 钟林俊
	 * @param zos 压缩输出流
	 * @param file 压缩文件源
	 * @param superiorPath 上级目录路径
	 * @throws IOException
	 */
	private static void doZip(java.util.zip.ZipOutputStream zos, File file, String superiorPath) throws IOException {
		if(file != null){
			if(file.isDirectory()){
				//noinspection ConstantConditions
				for(File subFile : file.listFiles()){
					String superior = superiorPath + File.separator + subFile.getName();
					doZip(zos, subFile, superior);
				}
			}else{
				addFile(zos, file, superiorPath);
			}
		}
	}

	/**
	 * 将单个文件加入压缩输出流
	 *
	 * @author 钟林俊
	 * @param zos 压缩输出流
	 * @param file 单个文件
	 * @throws IOException
	 */
	private static void addFile(java.util.zip.ZipOutputStream zos, File file, String superiorPath) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			zos.putNextEntry(new java.util.zip.ZipEntry(superiorPath));
			byte[] bytes = new byte[1024];
			int i;
			while ((i = fis.read(bytes)) != -1) {
				zos.write(bytes, 0, i);
			}
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

}
