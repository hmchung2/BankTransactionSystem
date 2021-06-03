package kr.ac.kopo.ui;

import java.util.Map;

public class LookUpUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("----------------------");
			System.out.println("\t ��ü ������ȸ");
			System.out.println("----------------------");
			Map<String, String> bank_result = service.lookUp_banks();
			if (LookAllBanks.lookAllBanks(bank_result) == false) {
				break;
			}
			System.out.println("���� ������ ��ȸ �Ͻðڽ��ϳ�?");
			String cont = scanStr("��ȸ : [y]  ���� [q]");
			while ((!cont.toUpperCase().equals("Y")) && (!cont.toUpperCase().equals("Q"))) {
				System.out.println("����� �Է� �ٶ��ϴ�.");
				System.out.println(cont);
				cont = scanStr("��ȸ : [y]  ���� [q]");
			}
			if (cont.toUpperCase().equals("Q")) {
				break;
			}
			String accountId = scanStr("����� ���� ��ȸ�� ���¹�ȣ�� �ۼ� ���ּ��� : ");
			int days = scanInt("���� ���� �� �������� ��ȸ�� �Ͻ� �ڽ��ϱ�?  (���� ������ �Է�)");
			Map<String, String> logResult = service.lookUp_logs(accountId, days);
			if (logResult.get("success").equals("false")   ) {
				if (logResult.get("accountExist").equals("false" )   ) {
					System.out.println("������ ������ ���� ��ȣ�� �ƴմϴ�.");
				} else {
					System.out.println("������ ��ȸ ���� �߻�");
				}
			}else {
				System.out.println( "�ŷ� ���� :  " + logResult.get("rowCount"));
				int logCounts = Integer.parseInt(logResult.get("rowCount"));
				if (logCounts == 0) {
					System.out.println("�� ���¿��� ��ȸ�� ������ �����ϴ�.");
				} else {
					System.out.println("���� \t �ŷ� ��¥ \t\t �ŷ���");
					for (int k = 1; k <= logCounts; k++) {
						String eachRow = logResult.get(String.valueOf(k));
						String[] logValues = eachRow.split(",");
						for (int n = 0; n < logValues.length; n++) {
							System.out.print(logValues[n] + "\t");
						}
						System.out.println();
					}
				}	
			}
			System.out.println("�ٽ� ��ȸ �Ͻðڽ��ϳ�?");
			cont = scanStr("��ȸ : [y]  ���� [q]");
			while ((!cont.toUpperCase().equals("Y")) && (!cont.toUpperCase().equals("Q"))) {
				System.out.println("����� �Է� �ٶ��ϴ�.");
				System.out.println(cont);
				cont = scanStr("��ȸ : [y]  ���� [q]");
			}
			if (cont.toUpperCase().equals("Q")) {
				break;
			} else {
				continue;
			}

		}
	}

}
//System.out.println("�ٽ� ��ȸ �Ͻðڽ��ϳ�?");
//								cont = scanStr("��ȸ : [y]  ���� [q]");
//								while( (!cont.toUpperCase().equals("Y"))   && (!cont.toUpperCase().equals("Q" ) ) ) {
//									System.out.println("����� �Է� �ٶ��ϴ�.");					
//									System.out.println(cont);
//									cont =  scanStr("��ȸ : [y]  ���� [q]");
//								}
//								if(cont.toUpperCase().equals("Q") ) {
//									break;
//								}else {
//									continue;
//								}