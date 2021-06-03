package kr.ac.kopo.ui;

import java.util.Map;

import kr.ac.kopo.config.Config;

public class TransferUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		
		while(true) {
			System.out.println("--------------");
			System.out.println("���� ��ü");
			System.out.println("--------------");
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			
			String accountId = scanStr("����� ���� ���� ��ȣ�� �Է� �ϼ��� : ");
			String otherAccount  = scanStr("�Ա��� �ٸ� ���� ��ȣ�� �Է� �ϼ��� : ");
			int money = scanInt("��ݾ��� �Է��ϼ��� : ");
			Map<String, String> result = service.transfer(accountId , money , otherAccount );
			if(result.get("success").equals("true")) {
				System.out.println("������ü ����. ���α׷� ����!");
				break;
			} else {
				if(result.get("accountExist").equals("false")){
					System.out.println("�Է��Ͻ� ���¹�ȣ�� �����ϰ� ���� �ʽ��ϴ�.");
				}else if(result.get("minimumTransfer").equals("false")) {
					System.out.println("���� ��ü �ּ� �ݾ��� " + Config.minimumTransfer +"���� Ŀ�� �մϴ�.");
				}else if(result.get("otherAccountExist").equals("false")) {
					System.out.println("�Է��Ͻ� ��ü�� ���� ��ȣ�� ���� ���� �ʽ��ϴ�.");
				}else if(result.get("bank_update1").equals("false")) {
					System.out.println("��� ���¿� ������ �߻� �߽��ϴ�.");
					if(result.get("enoughMoney").equals("false")) {
						System.out.println("��ä���� ���� �ܾ׺��� �� Ů�ϴ�.");
					}					
				}else if(result.get("bank_update2").equals("false")) {
					System.out.println("�Ա� ���¿� ������ �߻� �߽��ϴ�.");
				}
				System.out.println("���� ��ü�� �ٽ� �Ͻðڽ��ϱ�?");
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
