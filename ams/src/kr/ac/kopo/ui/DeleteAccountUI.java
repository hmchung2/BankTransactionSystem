package kr.ac.kopo.ui;

import java.util.Map;

public class DeleteAccountUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-----------------");
		System.out.println("\t계좌 삭제");
		System.out.println("-----------------");	
		while(true) {
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			String accountId = scanStr("삭제할 계좌 번호를 입력 하세요 : ");
			Map<String, String> result = service.deleteAccount(accountId);
			if(result.get("success").equals("true")) {
				System.out.println("계좌 삭제를 완료 했습니다.");
				System.out.println("삭제 시스템을 종료 합니다.");
				break;
			}else {
				if(result.get("accountExist").equals("false")){
					System.out.println("보유하고 계좌중에 입력하신 계좌번호는 존재하지 않습니다.");
				} else if(result.get("deleteLog").equals("false")) {
					System.out.println("로그 데이터 삭제중 문제 발생");
				} else if(result.get("deleteAccount").equals("false")) {
					System.out.println("계좌 삭제중 문제 발생");
				} else {
					System.out.println("알 수 없는 문제 발생");
				}
				System.out.println("계좌 삭제를 다시 시도 하시겠습니까?");
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
