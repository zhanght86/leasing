package com.pqsoft.invoice.action;

import com.pqsoft.invoice.service.InvoiceHandleService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;

public class InvoiceHandleAction extends Action {
	InvoiceHandleService service = new InvoiceHandleService();
	
	/**
	 * 
	 * <p>Title: execute</p> 
	 * <p>Description: TODO()</p> 
	 * @return 
	 * @see com.pqsoft.skyeye.api.Action#execute() 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	@Override
	public Reply execute() {
		try {
			service.mergeReceipt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
