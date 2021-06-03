package kr.ac.kopo.ui;

import java.util.Map;

public class SignInUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("--------------");
			System.out.println("\t �α���");
			System.out.println("--------------");
			String username = scanStr("���̵� �Է��ϼ��� :");
			String password = scanStr("��й�ȣ�� �Է��ϼ��� :");
			Map<String, String> result = service.signIn(username, password);
			if (result.get("success").equals("true")) {
				System.out.println("�α��� �Ϸ�");
				break;
			} else if (result.get("success").equals("false")) {
				if (result.get("usernameExist").equals("false")) {
					System.out.println("ȸ������ ���� ���� ���̵� �Է� �ϼ̽��ϴ�.");
				} else if (result.get("samePassword").equals("false")) {
					System.out.println("��� ��ȣ�� �߸� �Է� �ϼ̽��ϴ�.");
				} else {
					System.out.println("�˼� ���� UI ���μ��� ���� �߻�");
				}
				System.out.println("�ٽ� �α��� �õ� �Ͻðڽ��ϱ�?");
				String cont = scanStr("�ٽýõ� : [r]  ȸ������ ���� [q]");
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
