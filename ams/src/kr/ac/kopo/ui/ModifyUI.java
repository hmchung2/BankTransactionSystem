package kr.ac.kopo.ui;

import java.util.Map;

public class ModifyUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		
		while(true) {
			System.out.println("-----------------");
			System.out.println("\t���� ����");
			System.out.println("-----------------");	
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			String accountId = scanStr("���� �� ���� ��ȣ�� �Է� �ϼ��� : ");
			String nickName  = scanStr("��Ī�� ���� �ۼ� ���ּ��� : ");
			Map<String , String>  result = service.modifyAccount(accountId , nickName);
			if(result.get("success").equals("true")) {
				System.out.println("���� ���� ����");
				System.out.println("���� ���� �ý����� ���� �մϴ�.");
				break;				
			} else {
				if(result.get("accountExist").equals("false")) {
					System.out.println("�Է��Ͻ� ���¹�ȣ�� �����ϰ� ���� �ʽ��ϴ�.");
				} else if(result.get("update").equals("false")) {
					System.out.println("���� ���� �� ���� �߻�");
					
				} else {
					System.out.println("�˼� ���� �ý��� ���� �߻�");					
				}
				System.out.println("���� ������ �ٽ� �Ͻðڽ��ϱ�?");
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
