package kr.ac.kopo.ui;

import java.util.Map;

public class ModifyUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		
		while(true) {
			System.out.println("-----------------");
			System.out.println("\t계좌 수정");
			System.out.println("-----------------");	
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			String accountId = scanStr("수정 할 계좌 번호를 입력 하세요 : ");
			String nickName  = scanStr("별칭을 새로 작성 해주세요 : ");
			Map<String , String>  result = service.modifyAccount(accountId , nickName);
			if(result.get("success").equals("true")) {
				System.out.println("계좌 수정 성공");
				System.out.println("계좌 수정 시스템을 종료 합니다.");
				break;				
			} else {
				if(result.get("accountExist").equals("false")) {
					System.out.println("입력하신 계좌번호를 보유하고 있지 않습니다.");
				} else if(result.get("update").equals("false")) {
					System.out.println("계좌 수정 중 문제 발생");
					
				} else {
					System.out.println("알수 없는 시스템 에러 발생");					
				}
				System.out.println("계좌 수정을 다시 하시겠습니까?");
				String cont = scanStr("다시시도 : [r]  종료 [q]");
				while ((!cont.toUpperCase().equals("Q")) && (!cont.toUpperCase().equals("R"))) {
					System.out.println("제대로 입력 바랍니다.");
					System.out.println(cont);
					cont = scanStr("다시시도 : [r]  회원가입 종료 [q]");
				}
				if (cont.toUpperCase().equals("Q")) {
					break;
				}				
			}			
		}
	}
}
