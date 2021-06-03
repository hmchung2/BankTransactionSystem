package kr.ac.kopo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.ac.kopo.config.Config;

public class BankLookUpUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("----------------------");
			System.out.println("\t 은행 계좌조회");
			System.out.println("----------------------");
			System.out.println("조회 가능한 은행은 다음과 같습니다.");
			for (int i = 0; i < Config.bankList.length; i++) {
				System.out.print(Config.bankList[i] + "   ");
			}
			String bankName = scanStr("은행 이름을 입력하세요 : ");
			Map<String, String> bank_result = service.lookUp_oneBank(bankName);
			List<String> accounts =new ArrayList<String>();
			if (bank_result.get("success").equals("false")   ) {
				System.out.println("은행을 조회하는데 문제가 발생 했습니다. 조회 시스템을 종료 합니다.");
				break;
			} else {
				int rowCounts = Integer.parseInt(bank_result.get("rowCount"));
				if (rowCounts == 0) {
					System.out.println("조회할 은행 계좌가 없습니다.");
					System.out.println("조회 시스템을 종료 합니다.");
					break;
				} else {
					accounts.clear();
					System.out.println("은행 이름 \t 계좌번호 \t\t 계좌 생성 시간 \t\t\t 잔금 \t 계좌별명");
					for (int i = 1; i <= rowCounts; i++) {
						String eachRow = bank_result.get(String.valueOf(i));
						String[] values = eachRow.split(",");
						for (int j = 0; j < values.length; j++) {
							System.out.print(values[j] + "\t");
							if( j == 1) {
								accounts.add(values[j]);
							}
						}
						System.out.println("");
					}
				}
			}
			System.out.println("계좌 내역을 조회 하시겠습니끼?");
			String cont = scanStr("조회 : [y]  종료 [q]");
			while ((!cont.toUpperCase().equals("Y")) && (!cont.toUpperCase().equals("Q"))) {
				System.out.println("제대로 입력 바랍니다.");
				System.out.println(cont);
				cont = scanStr("조회 : [y]  종료 [q]");
			}
			if (cont.toUpperCase().equals("Q")) {
				break;
			}
			String accountId = scanStr("입출금 내역 조회할 계좌번호를 작성 해주세요 : ");			
			int days = scanInt("과거 몇일 전 정보까지 조회를 하시 겠습니까?  (숫자 단위로 입력)");
			if(accounts.contains(accountId) == false) {
				System.out.println(bankName + "은행에 작성하신 계좌번호는 존재 하지 않습니다.");
			}else {
				
				Map<String, String> logResult = service.lookUp_logs(accountId, days);
				if (logResult.get("success").equals("false")   ) {
					if (logResult.get("accountExist").equals("false" )   ) {
						System.out.println("고객님이 보유한 계좌 번호가 아닙니다.");
					} else {
						System.out.println("데이터 조회 에러 발생");
					}
				}else {
					System.out.println( "거래 개수 :  " + logResult.get("rowCount"));
					int logCounts = Integer.parseInt(logResult.get("rowCount"));
					if (logCounts == 0) {
						System.out.println("이 계좌에는 조회할 내역이 없습니다.");
					} else {
						System.out.println("종류 \t 거래 날짜 \t\t 거래량");
						for (int k = 1; k <= logCounts; k++) {
							String eachRow = logResult.get(String.valueOf(k));
							String[] logValues = eachRow.split(",");
							for (int n = 0; n < logValues.length; n++) {
								System.out.print(logValues[n] + "\t");
							}
							System.out.println();
						}
					}	
				}
				
			}
		
			System.out.println("다시 조회 하시겠습니끼?");
			cont = scanStr("조회 : [y]  종료 [q]");
			while ((!cont.toUpperCase().equals("Y")) && (!cont.toUpperCase().equals("Q"))) {
				System.out.println("제대로 입력 바랍니다.");
				System.out.println(cont);
				cont = scanStr("조회 : [y]  종료 [q]");
			}
			if (cont.toUpperCase().equals("Q")) {
				break;
			} else {
				continue;
			}		
		}
	}
}
