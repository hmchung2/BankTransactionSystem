package kr.ac.kopo.ui;

import java.util.Arrays;
import java.util.Map;

import kr.ac.kopo.config.Config;

public class AccountRegisterUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		while (true) {
			System.out.println("-----------------");
			System.out.println("\t계좌 등록");
			System.out.println("-----------------");	
			System.out.println("선택 가능한 은행은 다음과 같습니다.");
			for (int i = 0; i < Config.bankList.length; i++) {
				System.out.print(Config.bankList[i] + "   ");
			}
			String bankName = scanStr("은행 이름을 입력하세요 : ");
			String nick = scanStr("은행 별칭을 작성 하세요 : ");
			int money = scanInt("초기 입금 금액을 작성 하세요 (소수점 없는 원단위 작성)");
			Map<String, String> result = service.accountRegister(bankName, money, nick);

			
			if (result.get("success").equals("true")) {
				System.out.println("계좌 등록 완료");
				String accountId = result.get("accountNumber");
				System.out.println("생성된 계좌 번호 : " + accountId);
				break;
			} else if (result.get("success").equals("false")) {
				
				if (result.get("bankExist") == "false" ) {
					System.out.println("입렵 하신 은행은 존재 하지 않습니다.");
					System.out.println("다음 은행중에서 선택 해주세요.");
					for (int i = 0; i < Config.bankList.length; i++) {
						System.out.print(Config.bankList[i] + "   ");
					}
				} else if (result.get("enoughMoney") == "false") {
					System.out.println(Config.minimumBalance + " 이상의 금액을 입금해야 계좌를 생성 할 수 있습니다.");
				} else if(result.get("monthCheck") == "false") {
					System.out.println("한달 이내에 같은 은행에서 2개 이상의 계좌들을 만들 수 없습니다.");
					System.out.println(result.get("createDate") + " 날 " + bankName + "  은행 에서 " +result.get("existingAccount") +"번호로 계좌를 이미 만드셨습니다.");					
				} else if (result.get("insert") == "false") {
					System.out.println("데이터 트랜잭션 에러 발생");
				} else {
					System.out.println("알수 없는 UI 프로세스 에러 발생");
				}
				System.out.println("은행 계좌 생성 다시 하시겠습니까?");
				String cont = scanStr("다시시도 : [r]  회원가입 종료 [q]");
				while ((!cont.toUpperCase().equals("Q")) && (!cont.toUpperCase().equals("R"))) {
					System.out.println("제대로 입력 바랍니다.");
					System.out.println(cont);
					cont = scanStr("다시시도 : [r] 계좌 생성 종료 [q]");
				}
				if (cont.toUpperCase().equals("Q")) {
					break;
				}
			}
		}
	}
}
