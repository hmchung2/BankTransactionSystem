package kr.ac.kopo.ui;

import java.util.Map;

public class SignUpUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub

		while (true) {

			System.out.println("----------------");
			System.out.println("\t ȸ�� ����");
			String name = scanStr("�̸��� �Է��ϼ��� :");
			String username = scanStr("���ο� ���̵� �Է��ϼ��� :");
			String password = scanStr("��й�ȣ�� �Է��ϼ��� :");
			String repassword = scanStr("��й�ȣ�� �ٽ� �Է��ϼ��� :");
			String ssn  = scanStr("�ֹε�Ϻ��� �Է��ϼ��� :");
			
		
			Map<String, String> result = service.signUp(username, password, repassword, ssn, name);

			if (result.get("success").equals("true" )  ) {
				System.out.println("ȸ������ �Ϸ�");
				break;
			} else if (result.get("success").equals("false") ) {
				if (result.get("samePassword").equals("false"  )   ) {
					System.out.println("�Է��Ͻ� ��й�ȣ�� �ٸ��ϴ�.");
				} else if (result.get("duplicate_username").equals("false") ) {
					System.out.println("�̹� ������� ���̵� �Դϴ�.");
				} else if (result.get("duplicate_ssn").equals("false") ) {
					System.out.println("�̹� ������� �ֹε�Ϻ� �Դϴ�.");
				} else if (result.get("insert").equals("false")  ) {
					System.out.println("�����͸� �����ϴµ� ������ �߻� �߽��ϴ�.");
				} else if (result.get("unknown").equals("unknown") ) {
					System.out.println("�˼� ���� ���� ���μ��� ���� �߻�");
				} else {
					System.out.println("�˼� ���� UI ���μ��� ���� �߻�");
				}
				
				System.out.println("ȸ�� ������ �ٽ� �Ͻðڽ��ϱ�?");
				String cont = scanStr("�ٽýõ� : [r]  ȸ������ ���� [q]");
				while( (!cont.toUpperCase().equals("Q"))   && (!cont.toUpperCase().equals("R" ) ) ) {
					System.out.println("����� �Է� �ٶ��ϴ�.");					
					System.out.println(cont);
					cont = scanStr("�ٽýõ� : [r]  ȸ������ ���� [q]");
				}
				if(cont.toUpperCase().equals("Q") ) {
					break;
				}
			}			
		}
	}
}
