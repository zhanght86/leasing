package com.pqsoft.secu.service;

import java.io.Serializable;

import org.bouncycastle.util.encoders.Base64;


public class menuEntity  implements Serializable{
	 private String ID ; 
	 private String NAME ; 
	 private String PARENT_ID ; 
	 private String URL ; 
	 private String ORDERNO ; 
	 private String MEMO ; 
	 private String PER_CODE ; 
	 private byte[] ICON ;
	 private String iconBase64;
	 
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENTID) {
		PARENT_ID = pARENTID;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getORDERNO() {
		return ORDERNO;
	}
	public void setORDERNO(String oRDERNO) {
		ORDERNO = oRDERNO;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	public String getPER_CODE() {
		return PER_CODE;
	}
	public void setPER_CODE(String pERCODE) {
		PER_CODE = pERCODE;
	}
	public byte[] getICON() {
		return ICON;
	}
	public void setICON(byte[] iCON) {
		ICON = iCON;
		if (iCON != null) iconBase64 = new String(Base64.encode(iCON));
	} 
	
	public String getIconBase64() {
		return iconBase64;
	}
	
}
