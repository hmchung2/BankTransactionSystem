package kr.ac.kopo.ui;

import java.util.Map;

import kr.ac.kopo.config.Config;

public class TransferUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
		while(true) {
			System.out.println("--------------");
			System.out.println("계좌 이체");
			System.out.println("--------------");
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			
			String accountId = scanStr("출금할 본인 계좌 번호를 입력 하세요 : ");
			String otherAccount  = scanStr("입금할 다른 계좌 번호를 입력 하세요 : ");
			int money = scanInt("출금액을 입력하세요 : ");
			Map<String, String> result = service.transfer(accountId , money , otherAccount );
			if(result.get("success").equals("true")) {
				System.out.println("계좌이체 성공. 프로그램 종료!");
				break;
			} else {
				if(result.get("accountExist").equals("false")){
					System.out.println("입력하신 계좌번호를 보유하고 있지 않습니다.");
				}else if(result.get("minimumTransfer").equals("false")) {
					System.out.println("계좌 이체 최소 금액은 " + Config.minimumTransfer +"보다 커야 합니다.");
				}else if(result.get("otherAccountExist").equals("false")) {
					System.out.println("입력하신 이체할 계좌 번호는 존재 하지 않습니다.");
				}else if(result.get("bank_update1").equals("false")) {
					System.out.println("출금 계좌에 문제가 발생 했습니다.");
					if(result.get("enoughMoney").equals("false")) {
						System.out.println("이채량이 계좌 잔액보다 더 큽니다.");
					}					
				}else if(result.get("bank_update2").equals("false")) {
					System.out.println("입금 계좌에 문제가 발생 했습니다.");
				}
				System.out.println("계좌 이체를 다시 하시겠습니까?");
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
