package com.pqsoft.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: InvoiceEntry 
 * @Description: (发票) 
 * @author 程龙达
 * @date 2013-11-2 下午05:11:53 
 *
 */
public class InvoiceEntry {
	
	String invoice_code;
	Date invoice_date;
	List<InvoiceItem> items = new ArrayList<InvoiceItem>();
	
	public InvoiceEntry(){
		
	}
	
	public InvoiceEntry (String invoice_code,Date invoice_date,List<InvoiceItem> items){
		this.invoice_code = invoice_code;
		this.invoice_date = invoice_date;
		this.items = items;
	}
	
	public String getInvoice_code() {
		return invoice_code;
	}
	public void setInvoice_code(String invoiceCode) {
		invoice_code = invoiceCode;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoiceDate) {
		invoice_date = invoiceDate;
	}
	public List<InvoiceItem> getItems() {
		return items;
	}
	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}
	
}
