package kr.ac.kopo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ac.kopo.config.Config;
import kr.ac.kopo.dao.AMSDAODB;
import kr.ac.kopo.vo.AMSVO;

public class AMSService {
	
	private Map<String, String> result;
	private AMSDAODB dao;
	private Boolean session;
	private AMSVO vo;
	private Boolean isManager;

	
	public AMSService() {	
		result= new HashMap<>();
		dao = new AMSDAODB();
		session = false;
		isManager = false;
	}
	
	public void signOut() throws Exception{
		this.session = false;
		this.vo = null;		
	}
	
	public Map<String, String> sendSQL(String sendingSql){
		result.clear();
		result.put("success", "false");		
		try {
			result = dao.sendSQL(result, sendingSql);
		} catch (Exception e) {
			result.put("unknown", "unknown");
		}	
		return result;
	}
	
	public Map<String, String> signUp(String username, String password, String repassword, String ssn, String name)
			throws Exception {
		result.clear();
		result.put("success", "false");
		result.put("samePassword", "true");
		if (!password.equals(repassword) ) {
			result.clear();
			result.put("samePassword", "false");
			return result;
		} else {
			result = dao.signUp(result, username, password, ssn, name);
			return result;
		}
	}
	//	public AMSVO(int id, String username, String password, String ssn) {
	
	public void updateVo() throws Exception{
		List<Map<String,String>> infoList = dao.getBankInfo( vo.getId() );
		vo.setID_to_Account( infoList.get(0));
		vo.setAccount_to_ID( infoList.get(1));		
	}
	
	public Map<String, String> signIn(String username , String password) throws Exception{		
		result = dao.signIn(username , password);
		if(result.get("success").equals("true")  ) {
			session = true;
			if(Arrays.asList(Config.managers).contains(username) ) {
				isManager = true;
			}			
			vo = new AMSVO(
					Integer.parseInt(result.get("user_id") ),
					result.get("name"),					
					username,
					password,
					result.get("ssn")					
					);
			
			System.out.println("vo created");
			List<Map<String,String>> infoList = dao.getBankInfo(Integer.parseInt(result.get("user_id")));
			vo.setID_to_Account( infoList.get(0));
			vo.setAccount_to_ID( infoList.get(1));		
		} else {
			session  = false;
			vo = null;
		}
		return result;
	}
	public Map<String,String> transfer(String accountId, int money , String otherAccount ) throws Exception{
		result.clear();
		updateVo();
		result.put("success", "false");
		result.put("accountExist", "true");
		result.put("minimumTransfer", "true");
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		if (Account_to_ID.get(accountId) == null) {
			result.put("accountExist", "false");
			return result;
		} else if (money < Config.minimumTransfer) {
			result.put("minimumTransfer", "false");
			return result;
		} else {
			result = dao.transfer(result, vo, accountId, money, otherAccount);
			return result;
		}
	}
	public Map<String , String> withdraw(String accountId, int money) throws Exception{
		result.clear();
		updateVo();
		result.put("success", "false");
		result.put("accountExist", "true");
		result.put("minimumTransfer","true");
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		if (Account_to_ID.get(accountId) == null) {
			result.put("accountExist", "false");
			return result;
		} else if(money < Config.minimumTransfer) {
			result.put("minimumTransfer","false");
			return result;
		}else {
			int bankId = Integer.parseInt(Account_to_ID.get(accountId));
			result = dao.withdraw(result , bankId, accountId, money);
			return result;
		}
	}
	public Map<String ,String> deposit(String accountId , int money) throws Exception{
		result.clear();
		updateVo();
		result.put("success" , "false");
		result.put("accountExist" , "true");
		result.put("minimumTransfer","true");
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		if(Account_to_ID.get(accountId) == null ) {
			result.put("accountExist" , "false");
			return result;			
		} else if(money < Config.minimumTransfer) {
			result.put("minimumTransfer","false");
			return result;			
		}else {
			int bankId = Integer.parseInt(Account_to_ID.get(accountId));
			result = dao.deposit(result, bankId , accountId , money  );
			return result;
		}
	}
	public Map<String, String> accountRegister( String bankName , int money , String nick) throws Exception{		
		updateVo();
		result.clear();		
		result.put("success","false");
		result.put("bankExist" , "true");
		result.put("enoughMoney" ,"true");
		if(Arrays.asList(Config.bankList).contains(bankName) ==false ) {
			result.put("bankExist" , "false");
			return result;
		} else if(money < Config.minimumBalance) {
			result.put("enoughMoney" ,"false");
			return result;			
		} else {
			result = dao.accountRegister(result, bankName , money , nick , vo);
			updateVo();
		}
		return result;		
	}
	
	public Map<String, String> lookUp_banks() throws Exception{
		updateVo();
		result.clear();
		result = dao.lookUp_banks(result , vo);
		return result;
	}
	
	public Map<String, String> lookUp_oneBank(String selectedBank) throws Exception{
		updateVo();
		result = dao.lookUp_oneBank(result , vo , selectedBank);
		return result;
	}
		
	public Map<String, String> lookUp_logs(String accountID , int days) throws Exception{
		result.clear();
		result.put("success" , "false");
		result.put("accountExist" , "true");
		updateVo();
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		if(Account_to_ID.get(  accountID )  == null ) {
			result.put("accountExist" , "false");
			return result;
		} else {
			result = dao.lookUp_logs(result, vo , accountID , days);
			return result;	
		}
	}
	public Map<String , String> deleteAccount(String accountID) throws Exception{
		result.clear();
		result.put("success","false");
		result.put("accountExist"  , "true");
		updateVo();
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		if(Account_to_ID.get(  accountID )  == null ) {
			result.put("accountExist" , "false");
			return result;
		} else {			
			result = dao.deleteAccount(result , vo , accountID);
			return result;	
		}		
	}
	public Map<String , String> modifyAccount(String accountID , String nickname) throws Exception {
		result.clear();
		result.put("success","false");
		result.put("accountExist"  , "true");
		updateVo();
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		if(Account_to_ID.get(  accountID )  == null ) {
			result.put("accountExist" , "false");
			return result;
		} else {			

			result = dao.modifyAccount(result , vo , accountID,  nickname);			
			return result;	
		}		
	}
	public AMSVO getAMSVO() {
		return this.vo;
	}

	public Boolean getSesssion() {
		return this.session;	
	}
	public Boolean getIsManager() {
		return isManager;
	}
}
