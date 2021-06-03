package kr.ac.kopo.main;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import kr.ac.kopo.dao.AMSDAODB;
import kr.ac.kopo.util.ConnectionFactory;

public class TestMain {
	
	public static void main(String[] args) throws Exception {
		
		 Map<String, String> result = new HashMap<>();
		 
		 result.put("one" ,"one");
		 System.out.println(result.get("one"));
		 System.out.println(result.get("two"));
	}
}
