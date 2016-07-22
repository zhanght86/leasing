package com.pqsoft.console.action;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;

public class ConsoleAction extends Action {

	@Override
	@aAuth(type = aAuthType.ALL)
	@aDev(code = "leedsjung", email = "leedsjung@126.com", name = "张路")
	public Reply execute() {
		PipedInputStream pipedIS = new PipedInputStream();
		PipedOutputStream pipedOS = new PipedOutputStream();
		try {
		   pipedOS.connect(pipedIS);
		}
		catch(IOException e) {
		   System.err.println("连接失败");
		   System.exit(1);
		}
		PrintStream ps = new PrintStream(pipedOS);
		System.setOut(ps);
		System.setErr(ps);	
		ps.println();
		return null;
	}

}
