package com.pqsoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.zip.ZipOutputStream;

public class CopyFile {
	public boolean copy(String file1, String file2) {

		File in = new File(file1);
		File out = new File(file2);
		if (!in.exists()) {
			System.out.println(in.getAbsolutePath() + "源文件路径错误！！！");
			return false;
		} else {
			System.out.println("源文件路径" + in.getAbsolutePath());
			System.out.println("目标路径" + out.getAbsolutePath());

		}
		if (!out.exists())
			out.mkdirs();
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream(in);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String fileName = in.getName();
		try {
			fout = new FileOutputStream(new File(file2 + File.separator
					+ fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int c;
		byte[] b = new byte[1024 * 5];
		try {
			while ((c = fin.read(b)) != -1) {
				fout.write(b, 0, c);
				System.out.println("复制文件中！");
			}
			fin.close();
			fout.flush();
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void zip(String inputFileName,String zipFileName) throws Exception {
		System.out.println(zipFileName);
		zip(zipFileName, new File(inputFileName));
	}

	private void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		zip(out, inputFile, "");
		System.out.println("zip done");
		out.close();
	}

	private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				if(base + fl[i].getName()!=""){
					zip(out, fl[i], base + fl[i].getName());
				}
			}
		} else {
			System.out.println(base+"=================");
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}
}
