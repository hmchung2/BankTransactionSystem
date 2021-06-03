package kr.ac.kopo.ui;

import java.util.Map;


public class LookAllBanks {
	
	public static boolean lookAllBanks(Map<String, String> bank_result ) throws Exception {		
		if (bank_result.get("success").equals("false")) {
			System.out.println("은행을 조회하는데 문제가 발생 했습니다. 조회 시스템을 종료 합니다.");
			return false;
		} else {
			int rowCounts = Integer.parseInt(bank_result.get("rowCount"));
			if (rowCounts == 0) {
				System.out.println("조회할 은행 계좌가 없습니다.");
				System.out.println("조회 시스템을 종료 합니다.");
				return false;
			} else {
				System.out.println("은행 이름 \t 계좌번호 \t\t 계좌 생성 시간 \t\t 잔금 \t 계좌별명");
				for (int i = 1; i <= rowCounts; i++) {
					String eachRow = bank_result.get(String.valueOf(i));
					String[] values = eachRow.split(",");
					for (int j = 0; j < values.length; j++) {
						System.out.print(values[j] + "\t");
					}
					System.out.println("");
				}
			}
		}
		return true;
	}
}
