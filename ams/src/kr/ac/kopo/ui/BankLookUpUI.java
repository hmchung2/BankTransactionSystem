package kr.ac.kopo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.ac.kopo.config.Config;

public class BankLookUpUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("----------------------");
			System.out.println("\t ���� ������ȸ");
			System.out.println("----------------------");
			System.out.println("��ȸ ������ ������ ������ �����ϴ�.");
			for (int i = 0; i < Config.bankList.length; i++) {
				System.out.print(Config.bankList[i] + "   ");
			}
			String bankName = scanStr("���� �̸��� �Է��ϼ��� : ");
			Map<String, String> bank_result = service.lookUp_oneBank(bankName);
			List<String> accounts =new ArrayList<String>();
			if (bank_result.get("success").equals("false")   ) {
				System.out.println("������ ��ȸ�ϴµ� ������ �߻� �߽��ϴ�. ��ȸ �ý����� ���� �մϴ�.");
				break;
			} else {
				int rowCounts = Integer.parseInt(bank_result.get("rowCount"));
				if (rowCounts == 0) {
					System.out.println("��ȸ�� ���� ���°� �����ϴ�.");
					System.out.println("��ȸ �ý����� ���� �մϴ�.");
					break;
				} else {
					accounts.clear();
					System.out.println("���� �̸� \t ���¹�ȣ \t\t ���� ���� �ð� \t\t\t �ܱ� \t ���º���");
					for (int i = 1; i <= rowCounts; i++) {
						String eachRow = bank_result.get(String.valueOf(i));
						String[] values = eachRow.split(",");
						for (int j = 0; j < values.length; j++) {
							System.out.print(values[j] + "\t");
							if( j == 1) {
								accounts.add(values[j]);
							}
						}
						System.out.println("");
					}
				}
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
			if(accounts.contains(accountId) == false) {
				System.out.println(bankName + "���࿡ �ۼ��Ͻ� ���¹�ȣ�� ���� ���� �ʽ��ϴ�.");
			}else {
				
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
