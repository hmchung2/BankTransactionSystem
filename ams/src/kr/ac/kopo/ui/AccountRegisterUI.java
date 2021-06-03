package kr.ac.kopo.ui;

import java.util.Arrays;
import java.util.Map;

import kr.ac.kopo.config.Config;

public class AccountRegisterUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		while (true) {
			System.out.println("-----------------");
			System.out.println("\t���� ���");
			System.out.println("-----------------");	
			System.out.println("���� ������ ������ ������ �����ϴ�.");
			for (int i = 0; i < Config.bankList.length; i++) {
				System.out.print(Config.bankList[i] + "   ");
			}
			String bankName = scanStr("���� �̸��� �Է��ϼ��� : ");
			String nick = scanStr("���� ��Ī�� �ۼ� �ϼ��� : ");
			int money = scanInt("�ʱ� �Ա� �ݾ��� �ۼ� �ϼ��� (�Ҽ��� ���� ������ �ۼ�)");
			Map<String, String> result = service.accountRegister(bankName, money, nick);

			
			if (result.get("success").equals("true")) {
				System.out.println("���� ��� �Ϸ�");
				String accountId = result.get("accountNumber");
				System.out.println("������ ���� ��ȣ : " + accountId);
				break;
			} else if (result.get("success").equals("false")) {
				
				if (result.get("bankExist") == "false" ) {
					System.out.println("�Է� �Ͻ� ������ ���� ���� �ʽ��ϴ�.");
					System.out.println("���� �����߿��� ���� ���ּ���.");
					for (int i = 0; i < Config.bankList.length; i++) {
						System.out.print(Config.bankList[i] + "   ");
					}
				} else if (result.get("enoughMoney") == "false") {
					System.out.println(Config.minimumBalance + " �̻��� �ݾ��� �Ա��ؾ� ���¸� ���� �� �� �ֽ��ϴ�.");
				} else if(result.get("monthCheck") == "false") {
					System.out.println("�Ѵ� �̳��� ���� ���࿡�� 2�� �̻��� ���µ��� ���� �� �����ϴ�.");
					System.out.println(result.get("createDate") + " �� " + bankName + "  ���� ���� " +result.get("existingAccount") +"��ȣ�� ���¸� �̹� ����̽��ϴ�.");					
				} else if (result.get("insert") == "false") {
					System.out.println("������ Ʈ����� ���� �߻�");
				} else {
					System.out.println("�˼� ���� UI ���μ��� ���� �߻�");
				}
				System.out.println("���� ���� ���� �ٽ� �Ͻðڽ��ϱ�?");
				String cont = scanStr("�ٽýõ� : [r]  ȸ������ ���� [q]");
				while ((!cont.toUpperCase().equals("Q")) && (!cont.toUpperCase().equals("R"))) {
					System.out.println("����� �Է� �ٶ��ϴ�.");
					System.out.println(cont);
					cont = scanStr("�ٽýõ� : [r] ���� ���� ���� [q]");
				}
				if (cont.toUpperCase().equals("Q")) {
					break;
				}
			}
		}
	}
}
