package kr.ac.kopo.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.util.JDBCClose;
import kr.ac.kopo.vo.AMSVO;




public class AMSDAODB {
	
	
	public boolean checkDuplicate(Connection conn , PreparedStatement pstmt , String tableName , String column , String value) throws Exception {		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) AS ROWCOUNT  FROM (");
		sql.append("SELECT * FROM "+tableName+ " WHERE "+column+" = ? )");
		pstmt = conn.prepareStatement(sql.toString());		
		pstmt.setString(1, value );
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int no = rs.getInt("ROWCOUNT");
				
		if(no != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Map<String ,String> accountRegister(Map<String , String> result, String bankName, int money , String nick , AMSVO vo){
		result.put("success" , "false");
		result.put("monthCheck" , "true");
		result.put("insert","true");
		Connection conn = null;
		PreparedStatement pstmt = null;		
		try {
			conn = new ConnectionFactory().getConnection();			
			StringBuilder sql = new StringBuilder();
			int userId = vo.getId();
			sql.append("SELECT ACCOUNT_NUMBER , CREATE_DATE FROM AMS_BANKS WHERE USER_ID = ?");
			sql.append("AND BANK_NAME = ? AND (SYSDATE - CREATE_DATE) < 30");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , userId);
			pstmt.setString(2 ,  bankName);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next() == true) {
				String existingAccount = rs.getString("ACCOUNT_NUMBER");
				String createDate = rs.getString("CREATE_DATE");
				result.put("monthCheck" , "false");
				result.put("existingAccount",existingAccount);
				result.put("createDate" , createDate);
				return result;
			}
			sql.setLength(0);					
			String accountID;
			while(true) {
				accountID = generateDigits();
				if( checkDuplicate(conn , pstmt , "ams_banks" , "ACCOUNT_NUMBER" , accountID ) ==false ) {
					break;					
				}
			}
			sql.append("INSERT INTO AMS_BANKS(USER_ID, BANK_NAME, ACCOUNT_NUMBER, BALANCE, NICKNAME )");
			sql.append("values(?,?,?,?,?)");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1 , vo.getId());
			pstmt.setString(2, bankName);
			pstmt.setString(3 , accountID  );
			pstmt.setInt(4,money);
			pstmt.setString(5, nick);
			int insertRow = pstmt.executeUpdate();
			if(insertRow != 1) {
				result.put("insert" , "false");
				return result;
			} else {					
				result.put("success" , "true");
				result.put("accountNumber" , accountID);				
				return result;
			}			
		} catch (Exception e) {
			System.out.println("DAO error");
			result.put("unknown" , "unknown");
			return result;			
		}finally {
			JDBCClose.close(conn, pstmt);
		}
	}
	

	public String generateDigits() { 
	    Random rnd = new Random();
	    char [] digits = new char[11];
	    digits[0] = (char) (rnd.nextInt(9) + '1');
	    for(int i=1; i<digits.length; i++) {
	        digits[i] = (char) (rnd.nextInt(10) + '0');
	    }
	    return String.valueOf(Long.parseLong(new String(digits)) ) ;  
	}
	
	
	
	public List<Map<String,String>> getBankInfo(int user_id){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> ID_to_Account = new HashMap<>();
		Map<String, String> Account_to_ID = new HashMap<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT BANK_ID , ACCOUNT_NUMBER FROM AMS_BANKS WHERE USER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String accountId = rs.getString("ACCOUNT_NUMBER");
				int bankId = rs.getInt("BANK_ID");
				ID_to_Account.put(String.valueOf(bankId) , accountId);
				Account_to_ID.put(accountId , String.valueOf(bankId));
			}
			list.add(ID_to_Account);
			list.add(Account_to_ID);			
			return list;
		} catch (Exception e) {
			System.out.println("DAO getBANKINFO error");
			return null;
		}finally {
			JDBCClose.close(conn, pstmt);
		}
	}
	public Map<String, String> modifyAccount(Map<String , String> result, AMSVO vo , String accountID , String nickname) throws Exception{
		result.put("success","false");
		result.put("update" , "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Map<String, String> Account_to_ID = vo.getAccount_to_ID();
			int bankId = Integer.parseInt(Account_to_ID.get(accountID));
			conn = new ConnectionFactory().getConnection();
			conn.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE AMS_BANKS SET NICKNAME = ? WHERE BANK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setString(1 , nickname);
			pstmt.setInt(2 , bankId);
			int updateRow = pstmt.executeUpdate();
			if(updateRow != 1) {
				result.put("update" , "false");
				conn.rollback();
				return result;
			}
			conn.commit();
			result.put("success","true");			
			return result;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			result.put("unknown" , "unknown");
			return result;
			// TODO: handle exception
		}finally {
			conn.setAutoCommit(true);
			JDBCClose.close(conn, pstmt);
		}
	}
	
	
	public Map<String , String> signIn( String username , String password) {
		Map<String, String> result = new HashMap<>();
		result.put("success", "false");		
		result.put("usernameExist", "true");
		result.put("samePassword", "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM AMS_USER WHERE USERNAME = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, username );
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String checkPass = rs.getString("password");
				if( checkPass.equals(password)) {
					result.put("success", "true");
					result.put("ssn" , rs.getString("ssn"));
					result.put("name" , rs.getString("name"));
					result.put("user_id" , rs.getString("user_id"));
					
					
					return result;					
				}else {
					result.put("samePassword","false");
					return result;
				}
			}else {	
				result.put("usernameExist" , "false");
				return result;				
			}
		} catch (Exception e) {
			result.put("unknown", "unknown");
		}finally {
			JDBCClose.close(conn, pstmt);
		}
		return null;
	}
	@SuppressWarnings("resource")
	public Map<String ,String> withdraw(Map<String , String> result, int bankId , String accountId, int money) throws Exception{
		result.put("success", "false");
		result.put("enoughMoney" , "true");
		result.put("bank_update" , "true");
		result.put("log_insert" , "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConnection();
			conn.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT BALANCE FROM AMS_BANKS WHERE BANK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , bankId);
			ResultSet rs = pstmt.executeQuery();

			rs.next();
			int balance = rs.getInt("BALANCE");
			if(balance < money) {
				result.put("enoughMoney" , "false");
				return result;
			}
			sql.setLength(0);
			sql.append("UPDATE AMS_BANKS SET ");
			sql.append("BALANCE = BALANCE - ? ");
			sql.append(" WHERE BANK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , money);
			pstmt.setInt(2 , bankId);
			int updateRow = pstmt.executeUpdate();
			if(updateRow != 1) {
				result.put("bank_update" , "false");
				conn.rollback();
				return result;
			}
			sql.setLength(0);			
			sql.append("INSERT INTO AMS_LOGS(BANK_ID , PRICE , PROCESS_STATUS) VALUES (?, ? , 2)");			
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1, bankId);
			pstmt.setInt(2, money);
			int insertRow = pstmt.executeUpdate();
			if(insertRow  != 1) {
				result.put("log_insert" , "false");
				conn.rollback();
				return result;
			}
			conn.commit();
			result.put("success", "true");
			return result;			
			
		} catch (Exception e) {
			conn.rollback();
			result.put("unknown" , "unknown");
			return result;
		}finally {
			conn.setAutoCommit(true);
			JDBCClose.close(conn, pstmt);
		}
		
	}

	
	public Map<String , String> transfer(Map<String , String> result,   AMSVO vo , String accountId, int money , String otherAccount) throws Exception{
		result.put("success", "false");
		result.put("otherAccountExist" , "true");
		result.put("bank_update1" , "true");
		result.put("bank_update2" , "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		int bankId = Integer.parseInt(Account_to_ID.get(accountId));
		try {
			conn = new ConnectionFactory().getConnection();
			conn.setAutoCommit(false);

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT BANK_ID FROM AMS_BANKS WHERE ACCOUNT_NUMBER = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setString(1, otherAccount);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next() == false ) {
				result.put("otherAccountExist" , "false");
				conn.rollback();
				return result;
			}
			int otherId = rs.getInt("BANK_ID");
			Map<String, String> withdrawResult = withdraw(result, bankId , accountId, money);			
			if(withdrawResult.get("success").equals("false") ) {
				result.put("bank_update1" , "false");
				result.put("enoughMoney" , "true");
				if(withdrawResult.get("enoughMoney") =="false"  ) {
					result.put("enoughMoney" , "false");
				}
				conn.rollback();
				return result;
			}
			if(deposit(result, otherId , accountId, money).get("success").equals("false") ) {
				result.put("bank_update2" , "false");
				conn.rollback();
				return result;
			}
			conn.commit();			
			result.put("success", "true");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			conn.rollback();

			result.put("unknown", "unknown");
			return result;
		}finally {
			conn.setAutoCommit(true);
			JDBCClose.close(conn, pstmt);
		}

	}
	@SuppressWarnings("resource")
	public Map<String , String > deleteAccount(Map<String , String> result, AMSVO vo , String accountID) throws Exception{
		
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		int bankId = Integer.parseInt(Account_to_ID.get(accountID)  );
		result.put("success", "false");
		result.put("deleteLog","false");
		result.put("deleteAccount","true");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConnection();
			conn.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			
			sql.append("DELETE FROM AMS_LOGS WHERE BANK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , bankId);
			pstmt.executeUpdate();
			result.put("deleteLog","true");
			
			sql.setLength(0);
			sql.append("DELETE FROM AMS_BANKS WHERE BANK_ID = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , bankId);
			int deleteRow =   pstmt.executeUpdate();
			if(deleteRow != 1) {
				result.put("deleteAccount","false");
				conn.rollback();
				return result;
			
			}
			conn.commit();
			result.put("success", "true");
			return result;			
		} catch (Exception e) {
			System.out.println("DAO delete error");
			conn.rollback();
			result.put("unknown","unknown");
			return result;
		}finally {
			conn.setAutoCommit(true);
			JDBCClose.close(conn, pstmt);
		}	
	}
	

	@SuppressWarnings("resource")
	public Map<String , String> deposit(Map<String , String> result , int bankId , String accountId , int money) throws Exception{
		result.put("success", "false");
		result.put("bank_update" , "true");
		result.put("log_insert" , "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConnection();
			conn.setAutoCommit(false);
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE AMS_BANKS SET ");
			sql.append("BALANCE = BALANCE + ? ");
			sql.append(" WHERE BANK_ID = ?");

			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , money);
			pstmt.setInt(2 , bankId);		
			int updateRow = pstmt.executeUpdate();
			if(updateRow != 1) {
				result.put("bank_update" , "false");
				return result;
			}
			sql.setLength(0);			
			sql.append("INSERT INTO AMS_LOGS(BANK_ID , PRICE , PROCESS_STATUS) VALUES (?, ? , 1)");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1, bankId);
			pstmt.setInt(2, money);
			int insertRow = pstmt.executeUpdate();
			if(insertRow  != 1) {
				result.put("log_insert" , "false");
				return result;
			}
			conn.commit();
			result.put("success", "true");
			return result;			
		} catch (Exception e) {
			conn.rollback();
			result.put("unknown", "unknown");
			System.out.println("DAO ERROR");
			return result;
		}finally {
			conn.setAutoCommit(true);
			JDBCClose.close(conn, pstmt);
		}
	}
	
	public Map<String , String> signUp( Map<String, String> result,String username , String password , String ssn , String name   ) throws Exception {
		
		result.put("success", "false");
		result.put("duplicate_username", "true");
		result.put("duplicate_ssn", "true");
		result.put("insert", "true");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = new ConnectionFactory().getConnection();
			if( checkDuplicate(conn , pstmt , "AMS_USER" , "USERNAME"  , username ) ) {				
				result.put("duplicate_username", "false");
				return result;
			} 
			else if( checkDuplicate(conn , pstmt , "AMS_USER" , "SSN"  , ssn ) ) {				
				result.put("duplicate_ssn", "false");
				return result;				
			}
			else {
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO AMS_USER(NAME , SSN , USERNAME , PASSWORD)");
				sql.append("values( ? , ?, ? , ?  ) ");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, name );
				pstmt.setString(2 , ssn  );
				pstmt.setString(3, username);
				pstmt.setString(4, password);
				
				int insertRow = pstmt.executeUpdate();
				if(insertRow != 1) {
					result.put("insert" , "false");
					return result;
				} else {					
					result.put("success" , "true");
					return result;
				}
			}			
		} catch (Exception e) {
			result.put("unknown", "unknown");
			return result;
			
		} finally {
			JDBCClose.close(conn, pstmt);
		}
		
	}
	public Map<String , String> lookUp_oneBank(Map<String , String> result, AMSVO vo , String selectedBank){
		result.put("success", "false");
		result.put("select", "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		int user_id = vo.getId();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT BANK_NAME , ACCOUNT_NUMBER, CREATE_DATE , BALANCE , NICKNAME FROM AMS_BANKS ");
			sql.append("WHERE USER_ID = ? AND BANK_NAME = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , user_id);
			pstmt.setString(2, selectedBank);
			ResultSet rs = pstmt.executeQuery();

			int cnt = 0;
			String bankName;
			String account_number;
			String create_date ;
			int balance ;
			String nickname ;
			while(rs.next()) {
				bankName = rs.getString("BANK_NAME");
				account_number = rs.getString("ACCOUNT_NUMBER");
				create_date = rs.getString("CREATE_DATE");
				balance = rs.getInt("BALANCE");
				nickname = rs.getString("NICKNAME");
				String eachRow = bankName +"," + account_number + "," +create_date +"," + balance +"," + nickname;
				result.put( String.valueOf(++cnt) ,   eachRow );
			}

			result.put("rowCount" , String.valueOf(cnt));
			result.put("success", "true");
			return result;
			
		} catch (Exception e) {
			System.out.println("DAO ERROR");
			result.put("select", "false");
			return result;
		}finally {
			JDBCClose.close(conn, pstmt);
		}
	}
	
	
	public Map<String , String> lookUp_banks(Map<String , String> result, AMSVO vo){
		result.put("success", "false");
		result.put("select", "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		int user_id = vo.getId();
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT BANK_NAME , ACCOUNT_NUMBER, CREATE_DATE , BALANCE , NICKNAME FROM AMS_BANKS ");
			sql.append("WHERE USER_ID = ?");
			pstmt = conn.prepareStatement(sql.toString()  ,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
			pstmt.setInt(1 , user_id);
			ResultSet rs = pstmt.executeQuery();

			int cnt = 0;
			String bankName;
			String account_number;
			String create_date ;
			int balance ;
			String nickname ;
			while(rs.next()) {
				bankName = rs.getString("BANK_NAME");
				account_number = rs.getString("ACCOUNT_NUMBER");
				create_date = rs.getString("CREATE_DATE");
				balance = rs.getInt("BALANCE");
				nickname = rs.getString("NICKNAME");
				String eachRow = bankName +"," + account_number + "," +create_date +"," + balance +"," + nickname;
				result.put( String.valueOf(++cnt) ,   eachRow );
			}

			result.put("rowCount" , String.valueOf(cnt));
			result.put("success", "true");
			return result;
			
		} catch (Exception e) {
			System.out.println("DAO ERROR");
			result.put("select", "false");
			return result;
		}finally {
			JDBCClose.close(conn, pstmt);
		}
	}
	
	public Map<String , String> lookUp_logs(Map<String , String> result, AMSVO vo , String account_id , int days){
		result.put("success", "false");
		result.put("select", "true");
		Connection conn = null;
		PreparedStatement pstmt = null;
		Map<String, String> Account_to_ID = vo.getAccount_to_ID();
		
		int bankId = Integer.parseInt(  Account_to_ID.get(  account_id )  ); 
		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select  DECODE(PROCESS_STATUS , 1, '입금' , '출금'  ) AS PTYPE , ");
			sql.append("PRICE , TO_CHAR(PROCESS_DATE , 'YYYY/MM/DD HH24:MI:SS') AS PTIME from ams_logs ");
			sql.append("where process_date > (sysdate - ?) and bank_id =  ? ");
			sql.append("order by PROCESS_DATE desc");

			pstmt = conn.prepareStatement(sql.toString() );
			pstmt.setInt(1 , days);
			pstmt.setInt(2 , bankId);
			
			ResultSet rs = pstmt.executeQuery();
			int cnt = 0;
			while(rs.next()) {
				result.put(String.valueOf(++cnt) ,  rs.getString("PTYPE") + "," + rs.getString("PTIME") + "," + rs.getString("PRICE")   ) ;    
			}
			result.put("rowCount" , String.valueOf(cnt));		
			result.put("success", "true");
			return result;
		} catch (Exception e) {
			result.put("select", "false");
			System.out.println("DAO ERROR");
			return result;
		}finally {
			JDBCClose.close(conn, pstmt);
		}	
	}
//	ResultSet rs = statement.executeQuery();
//	   ResultSetMetaData rsmd = rs.getMetaData();
//	   System.out.println("querying SELECT * FROM XXX");
//	   int columnsNumber = rsmd.getColumnCount();
//	   while (rs.next()) {
//	       for (int i = 1; i <= columnsNumber; i++) {
//	           if (i > 1) System.out.print(",  ");
//	           String columnValue = rs.getString(i);
//	           System.out.print(columnValue + " " + rsmd.getColumnName(i));
//	       }
//	       System.out.println("");
//	   }
	
	public Map<String, String> sendSQL(Map<String, String> result, String sendingSql) throws Exception {
		result.put("success", "false");
		result.put("colCounts", "0");
		result.put("rowCounts","0");
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = new ConnectionFactory().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append(sendingSql);
			pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			result.put("colCounts", String.valueOf(columnsNumber));
			StringBuilder resultSQL = new StringBuilder();
			int cnt = 0;
			while (rs.next()) {
				resultSQL.setLength(0);
				for (int i = 1; i <= columnsNumber; i++) {
					resultSQL.append(rs.getString(i) + ",");
				}
				result.put(String.valueOf(cnt++), resultSQL.toString());
			}
			result.put("rowCounts",String.valueOf(cnt) );
			result.put("success", "true");
			return result;
		} catch (Exception e) {
			result.put("success", "false");
			return result;
		} finally {
			JDBCClose.close(conn, pstmt);
		}
	}
}
