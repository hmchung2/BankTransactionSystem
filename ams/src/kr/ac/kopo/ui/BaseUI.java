package kr.ac.kopo.ui;

import java.util.Scanner;

import kr.ac.kopo.service.AMSService;
import kr.ac.kopo.service.AmsServiceFactory;

public abstract class BaseUI implements IAMSUI {
	private Scanner sc;
	protected AMSService service;
	
	
	public BaseUI() {
		this.sc = new Scanner(System.in);
		this.service = AmsServiceFactory.getInstance();
	}

	protected String scanStr(String msg) {
		System.out.print(msg);
		String str = sc.nextLine();
		return str;
	}
	
	protected int scanInt(String msg) {
		int num = Integer.parseInt(scanStr(msg));
		return num;
	}

}
