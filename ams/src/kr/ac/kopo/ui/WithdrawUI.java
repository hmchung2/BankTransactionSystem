package kr.ac.kopo.ui;

import java.util.Map;

import kr.ac.kopo.config.Config;

public class WithdrawUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("-----------------");
			System.out.println("���� ���");
			System.out.println("-----------------");
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			String accountId = scanStr("����� ���� ��ȣ�� �Է� �ϼ��� : ");
			int money = scanInt("��ݾ��� �Է��ϼ��� : ");
			Map<String, String> result = service.withdraw(accountId, money);
			if (result.get("success").equals("true")) {
				System.out.println("��� �Ϸ� �߽��ϴ�. ��� �ý��� ���� �մϴ�.");
				break;
			} else {
				if (result.get("accountExist").equals("false")) {
					System.out.println("�����ϰ� ��� ���µ��� �Է��Ͻ� ��ȣ�� �����ϴ�.");
				} else if (result.get("minimumTransfer").equals("false")) {
					System.out.println("���� ��ü �ּ� �ݾ��� " + Config.minimumTransfer + "���� Ŀ�� �մϴ�.");
				} else if (result.get("enoughMoney").equals("false")) {
					System.out.println("���¿� �����ϰ� ��� �ݾ׺��� ��ݾ��� �� Ů�ϴ�.");
				} else if (result.get("bank_update").equals("false")) {
					System.out.println("���� ����� ���� �߻�");
				} else if (result.get("log_insert").equals("false")) {
					System.out.println("�α� ������ ���� �� ���� �߻�");
				} else {
					System.out.println("�˼� ���� ���� �߻�");
				}
				System.out.println("����� �ٽ� �Ͻðڽ��ϱ�?");
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
