package kr.ac.kopo.vo;

import java.util.Map;

public class AMSVO {
	private int id;
	private String name;
	private String username;
	private String password;
	private String ssn;
	private Map<String , String> Account_to_ID;
	private Map<String , String> ID_to_Account;
	
	public AMSVO(int id, String name,  String username, String password, String ssn) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.ssn = ssn;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public Map<String, String> getAccount_to_ID() {
		return Account_to_ID;
	}
	public void setAccount_to_ID(Map<String, String> account_to_ID) {
		Account_to_ID = account_to_ID;
	}
	public Map<String, String> getID_to_Account() {
		return ID_to_Account;
	}
	public void setID_to_Account(Map<String, String> iD_to_Account) {
		ID_to_Account = iD_to_Account;
	}
	
	
	@Override
	public String toString() {
		return "AMSVO [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", ssn="
				+ ssn + "]";
	}
	
	
	
	
	

}
