package kr.ac.kopo.ui;

import java.util.Map;

public class ManagerUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while(true) {
			System.out.println("-----------------");
			System.out.println("-----------------");
			System.out.println("�����ͺ��̽� ��ȸ ");
			System.out.println("-----------------");
			System.out.println("-----------------");
			System.out.println("SELCT �� �ۼ�");
			System.out.println("-----------------");
			String sendingSql= scanStr("�ۼ� SQL : SELECT");
			Map<String , String > result = service.sendSQL(sendingSql);
			if(result.get("success") == "false") {
				System.out.println("sql ó������ ������ �߻� �߽��ϴ�. sql���� �ٽ� �ѹ� Ȯ�� ���ּ���.");
				
			}else {
				if(result.get("colCounts").equals("0")  ) {
					System.out.println("SELECT �� sql���� ��ȸ�ϴ� �÷��� �����ϴ�.");					
				}else if(result.get("rowCounts").equals("0")) {
					System.out.println("SELECT �� sql���� ��ȸ�ϴ� ���� �����ϴ�.");
				}else {
					int rowCounts = Integer.parseInt(result.get("rowCounts"));
					
					for(int i = 0 ; i < rowCounts ;  i ++) {
						String eachrow = result.get(String.valueOf(i));
						String[] rowValues = eachrow.split(",");
						for(int j = 0 ; j < rowValues.length ;  j ++) {
							System.out.print( rowValues[j] + "\t");
						}
						System.out.println("");
					}					
				}								
			}
			System.out.println("�ٽ� �����ͺ��̽��� ��ȸ �Ͻðڽ��ϱ�?");
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
