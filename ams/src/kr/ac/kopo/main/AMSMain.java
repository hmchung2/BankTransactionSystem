package kr.ac.kopo.main;

import kr.ac.kopo.ui.AMSUI;

public class AMSMain {
	
	public static void main(String[] args) {
		AMSUI ui = new AMSUI();
		try {
			ui.execute();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("main catch");
			e.printStackTrace();
		}
	}

}

