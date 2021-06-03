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
						System.out.println("�߸������ϼ̽��ϴ�.");
					}
				} else if (session == true && isManager ==false) {
					IAMSUI ui = null;
					AMSVO vo = service.getAMSVO();
					System.out.println( "�� ���� : " + vo.toString());
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
						System.out.println("�߸������ϼ̽��ϴ�.");
					}
				} else if(session == true && isManager ==true) {
					IAMSUI ui = null;
					AMSVO vo = service.getAMSVO();
					System.out.println("----------������ �������� �α��� -------------");
					System.out.println( "������ ���� : " + vo.toString());
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
						System.out.println("�߸������ϼ̽��ϴ�.");
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
		System.out.println("\t���� ���� ���� ���α׷� ");
		System.out.println("----------------------------------");
		System.out.println("\t1. �α׾ƿ�");
		System.out.println("\t2. ���µ��");
		System.out.println("\t3. ���� ����");
		System.out.println("\t4. ���� ��ü ��ȸ �� ���� ���� Ȯ��");
		System.out.println("\t5. ���� ��ü");
		System.out.println("\t6. ���� �Ա�");
		System.out.println("\t7. ���� ���");
		System.out.println("\t8. ���� ����");
		System.out.println("\t9. ���ະ ���� ��ȸ �� ���� ���� Ȯ��");
		System.out.println("\t0. ����");
		System.out.println("----------------------------------");
		int type = scanInt("�޴� �� ���ϴ� �׸��� �����ϼ��� : ");	
		return type;
	}
	
	private int loggedInMenuManager() {
		System.out.println("----------------------------------");
		System.out.println("\t���� ���� ���� ���α׷� ");
		System.out.println("----------------------------------");
		System.out.println("\t1. �α׾ƿ�");
		System.out.println("\t2. ���µ��");
		System.out.println("\t3. ���� ����");
		System.out.println("\t4. ���� ��ü ��ȸ �� ���� ���� Ȯ��");
		System.out.println("\t5. ���� ��ü");
		System.out.println("\t6. ���� �Ա�");
		System.out.println("\t7. ���� ���");
		System.out.println("\t8. ���� ����");
		System.out.println("\t9. ���ະ ���� ��ȸ �� ���� ���� Ȯ��");
		System.out.println("\t10. ������ �����ͺ��̽� ��ȸ");
		System.out.println("\t0. ����");
		System.out.println("----------------------------------");
		int type = scanInt("�޴� �� ���ϴ� �׸��� �����ϼ��� : ");	
		return type;
	}
	
	
	
	private int loggedOutMenu() {
		
		System.out.println("----------------------------------");
		System.out.println("\t���� ���� ���� ���α׷� ");
		System.out.println("----------------------------------");
		System.out.println("\t1. �α���");
		System.out.println("\t2. ȸ������");
		System.out.println("\t0. ����");
		System.out.println("----------------------------------");
		int type = scanInt("�޴� �� ���ϴ� �׸��� �����ϼ��� : ");	
		return type;
	}



	
	
}
