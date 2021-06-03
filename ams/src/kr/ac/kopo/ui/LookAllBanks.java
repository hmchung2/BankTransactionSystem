package kr.ac.kopo.ui;

import java.util.Map;


public class LookAllBanks {
	
	public static boolean lookAllBanks(Map<String, String> bank_result ) throws Exception {		
		if (bank_result.get("success").equals("false")) {
			System.out.println("������ ��ȸ�ϴµ� ������ �߻� �߽��ϴ�. ��ȸ �ý����� ���� �մϴ�.");
			return false;
		} else {
			int rowCounts = Integer.parseInt(bank_result.get("rowCount"));
			if (rowCounts == 0) {
				System.out.println("��ȸ�� ���� ���°� �����ϴ�.");
				System.out.println("��ȸ �ý����� ���� �մϴ�.");
				return false;
			} else {
				System.out.println("���� �̸� \t ���¹�ȣ \t\t ���� ���� �ð� \t\t �ܱ� \t ���º���");
				for (int i = 1; i <= rowCounts; i++) {
					String eachRow = bank_result.get(String.valueOf(i));
					String[] values = eachRow.split(",");
					for (int j = 0; j < values.length; j++) {
						System.out.print(values[j] + "\t");
					}
					System.out.println("");
				}
			}
		}
		return true;
	}
}
