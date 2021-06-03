package kr.ac.kopo.ui;

import kr.ac.kopo.vo.AMSVO;

public class AMSUI extends BaseUI {


	@Override
	public void execute() throws Exception {
		while (true) {
			try {
				Boolean session = service.getSesssion();
				Boolean isManager = service.getIsManager();
				
				if (session == false) {
					IAMSUI ui = null;
					int type = loggedOutMenu();
					switch (type) {
					case 1:
						ui = new SignInUI();
						break;
					case 2:
						ui = new SignUpUI();
						break;
					case 0:
						ui = new ExitUI();
						break;
					}
					if (ui != null)
						ui.execute();
					else {
						System.out.println("잘못선택하셨습니다.");
					}
				} else if (session == true && isManager ==false) {
					IAMSUI ui = null;
					AMSVO vo = service.getAMSVO();
					System.out.println( "고객 정보 : " + vo.toString());
					System.out.println("welcome : " + vo.getName());
					int type = loggedInMenu();
					switch (type) {
					case 1:
						ui = new SignOutUI();
						break;
					case 2:
						ui = new AccountRegisterUI();
						break;
					case 3:
						ui = new ModifyUI();
						break;
					case 4:
						ui = new LookUpUI();
						break;
					case 5:
						ui = new TransferUI();
						break;
					case 6:
						ui = new DepositUI();
						break;
					case 7:
						ui = new WithdrawUI();
						break;
					case 8:
						ui = new DeleteAccountUI();
						break;
					case 9:
						ui = new BankLookUpUI();
						break;
					case 0:
						ui = new ExitUI();
						break;
					}
					if(ui != null) {
						ui.execute();
					}else {
						System.out.println("잘못선택하셨습니다.");
					}
				} else if(session == true && isManager ==true) {
					IAMSUI ui = null;
					AMSVO vo = service.getAMSVO();
					System.out.println("----------관리가 계정으로 로그인 -------------");
					System.out.println( "관리자 정보 : " + vo.toString());
					System.out.println("welcome : " + vo.getName());
					int type = loggedInMenuManager();
					switch (type) {
					case 1:
						ui = new SignOutUI();
						break;
					case 2:
						ui = new AccountRegisterUI();
						break;
					case 3:
						ui = new ModifyUI();
						break;
					case 4:
						ui = new LookUpUI();
						break;
					case 5:
						ui = new TransferUI();
						break;
					case 6:
						ui = new DepositUI();
						break;
					case 7:
						ui = new WithdrawUI();
						break;
					case 8:
						ui = new DeleteAccountUI();
						break;
					case 9:
						ui = new BankLookUpUI();
						break;
					case 0:
						ui = new ExitUI();
						break;
					case 10:
						ui = new ManagerUI();
						break;
					}					
					if(ui != null) {
						ui.execute();
					}else {
						System.out.println("잘못선택하셨습니다.");
					}
					
				}
			} catch (Exception e) {
				System.out.println("AMSUI catch");
				e.printStackTrace();
			}
		}
	}
	
	private int loggedInMenu() {
		System.out.println("----------------------------------");
		System.out.println("\t통합 계좌 관리 프로그램 ");
		System.out.println("----------------------------------");
		System.out.println("\t1. 로그아웃");
		System.out.println("\t2. 계좌등록");
		System.out.println("\t3. 계좌 수정");
		System.out.println("\t4. 계좌 전체 조회 및 계좌 내역 확인");
		System.out.println("\t5. 계좌 이체");
		System.out.println("\t6. 계좌 입금");
		System.out.println("\t7. 계좌 출금");
		System.out.println("\t8. 계좌 삭제");
		System.out.println("\t9. 은행별 계좌 조회 및 계좌 내역 확인");
		System.out.println("\t0. 종료");
		System.out.println("----------------------------------");
		int type = scanInt("메뉴 중 원하는 항목을 선택하세요 : ");	
		return type;
	}
	
	private int loggedInMenuManager() {
		System.out.println("----------------------------------");
		System.out.println("\t통합 계좌 관리 프로그램 ");
		System.out.println("----------------------------------");
		System.out.println("\t1. 로그아웃");
		System.out.println("\t2. 계좌등록");
		System.out.println("\t3. 계좌 수정");
		System.out.println("\t4. 계좌 전체 조회 및 계좌 내역 확인");
		System.out.println("\t5. 계좌 이체");
		System.out.println("\t6. 계좌 입금");
		System.out.println("\t7. 계좌 출금");
		System.out.println("\t8. 계좌 삭제");
		System.out.println("\t9. 은행별 계좌 조회 및 계좌 내역 확인");
		System.out.println("\t10. 관리자 데이터베이스 조회");
		System.out.println("\t0. 종료");
		System.out.println("----------------------------------");
		int type = scanInt("메뉴 중 원하는 항목을 선택하세요 : ");	
		return type;
	}
	
	
	
	private int loggedOutMenu() {
		
		System.out.println("----------------------------------");
		System.out.println("\t통합 계좌 관리 프로그램 ");
		System.out.println("----------------------------------");
		System.out.println("\t1. 로그인");
		System.out.println("\t2. 회원가입");
		System.out.println("\t0. 종료");
		System.out.println("----------------------------------");
		int type = scanInt("메뉴 중 원하는 항목을 선택하세요 : ");	
		return type;
	}



	
	
}
