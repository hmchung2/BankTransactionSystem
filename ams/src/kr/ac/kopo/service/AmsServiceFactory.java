package kr.ac.kopo.service;

public class AmsServiceFactory {
	
	private static final AMSService service = new AMSService();
	
	public static AMSService getInstance() {
		return service;
	}
}
