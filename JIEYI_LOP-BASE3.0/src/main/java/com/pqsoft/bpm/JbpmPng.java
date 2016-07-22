package com.pqsoft.bpm;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.StaticFile;

public class JbpmPng extends StaticFile {

	@Override
	public byte[] update(String id) {
		Map<String, Object> m = Dao.selectOne("bpm.deployment.getPng", id);
		try {
			return FileCopyUtils.copyToByteArray(((Blob) m.get("BLOB")).getBinaryStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
