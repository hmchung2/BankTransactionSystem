package kr.ac.kopo.ui;

import java.util.Map;

import kr.ac.kopo.config.Config;

public class WithdrawUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("-----------------");
			System.out.println("계좌 출금");
			System.out.println("-----------------");
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			String accountId = scanStr("출금할 계좌 번호를 입력 하세요 : ");
			int money = scanInt("출금액을 입력하세요 : ");
			Map<String, String> result = service.withdraw(accountId, money);
			if (result.get("success").equals("true")) {
				System.out.println("출금 완료 했습니다. 출금 시스템 종료 합니다.");
				break;
			} else {
				if (result.get("accountExist").equals("false")) {
					System.out.println("보유하고 계신 계좌들중 입력하신 번호는 없습니다.");
				} else if (result.get("minimumTransfer").equals("false")) {
					System.out.println("계좌 이체 최소 금액은 " + Config.minimumTransfer + "보다 커야 합니다.");
				} else if (result.get("enoughMoney").equals("false")) {
					System.out.println("계좌에 보유하고 계신 금액보다 출금액이 더 큽니다.");
				} else if (result.get("bank_update").equals("false")) {
					System.out.println("계좌 출금중 에러 발생");
				} else if (result.get("log_insert").equals("false")) {
					System.out.println("로그 데이터 생성 중 에러 발생");
				} else {
					System.out.println("알수 없는 문제 발생");
				}
				System.out.println("출금을 다시 하시겠습니까?");
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
