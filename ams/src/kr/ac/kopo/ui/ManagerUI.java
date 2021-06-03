package kr.ac.kopo.ui;

import java.util.Map;

public class ManagerUI extends BaseUI{

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while(true) {
			System.out.println("-----------------");
			System.out.println("-----------------");
			System.out.println("데이터베이스 조회 ");
			System.out.println("-----------------");
			System.out.println("-----------------");
			System.out.println("SELCT 문 작성");
			System.out.println("-----------------");
			String sendingSql= scanStr("작성 SQL : SELECT");
			Map<String , String > result = service.sendSQL(sendingSql);
			if(result.get("success") == "false") {
				System.out.println("sql 처리에서 문제가 발생 했습니다. sql문을 다시 한번 확인 해주세요.");
				
			}else {
				if(result.get("colCounts").equals("0")  ) {
					System.out.println("SELECT 한 sql에서 조회하는 컬럼이 없습니다.");					
				}else if(result.get("rowCounts").equals("0")) {
					System.out.println("SELECT 한 sql에서 조회하는 행이 없습니다.");
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
			System.out.println("다시 데이터베이스를 조회 하시겠습니까?");
			String cont = scanStr("다시시도 : [r]  종료 [q]");
			while ((!cont.toUpperCase().equals("Q")) && (!cont.toUpperCase().equals("R"))) {
				System.out.println("제대로 입력 바랍니다.");
				System.out.println(cont);
				cont = scanStr("다시시도 : [r]  회원가입 종료 [q]");
			}
			if (cont.toUpperCase().equals("Q")) {
				break;
			}							
		}		
	}
}
