package kr.ac.kopo.ui;

import java.util.Map;

public class DeleteAccountUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("-----------------");
		System.out.println("\t���� ����");
		System.out.println("-----------------");	
		while(true) {
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			String accountId = scanStr("������ ���� ��ȣ�� �Է� �ϼ��� : ");
			Map<String, String> result = service.deleteAccount(accountId);
			if(result.get("success").equals("true")) {
				System.out.println("���� ������ �Ϸ� �߽��ϴ�.");
				System.out.println("���� �ý����� ���� �մϴ�.");
				break;
			}else {
				if(result.get("accountExist").equals("false")){
					System.out.println("�����ϰ� �����߿� �Է��Ͻ� ���¹�ȣ�� �������� �ʽ��ϴ�.");
				} else if(result.get("deleteLog").equals("false")) {
					System.out.println("�α� ������ ������ ���� �߻�");
				} else if(result.get("deleteAccount").equals("false")) {
					System.out.println("���� ������ ���� �߻�");
				} else {
					System.out.println("�� �� ���� ���� �߻�");
				}
				System.out.println("���� ������ �ٽ� �õ� �Ͻðڽ��ϱ�?");
				String cont = scanStr("�ٽýõ� : [r]  ���� [q]");
				while ((!cont.toUpperCase().equals("Q")) && (!cont.toUpperCase().equals("R"))) {
					System.out.println("����� �Է� �ٶ��ϴ�.");
					System.out.println(cont);
					cont = scanStr("�ٽýõ� : [r]  ȸ������ ���� [q]");
				}
				if (cont.toUpperCase().equals("Q")) {
					break;
				}
			}
			
		}
		
	}
}
