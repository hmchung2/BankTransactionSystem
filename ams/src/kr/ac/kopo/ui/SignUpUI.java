package kr.ac.kopo.ui;

import java.util.Map;

public class SignUpUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub

		while (true) {

			System.out.println("----------------");
			System.out.println("\t 회원 가입");
			String name = scanStr("이름을 입력하세요 :");
			String username = scanStr("새로운 아이디를 입력하세요 :");
			String password = scanStr("비밀번호를 입력하세요 :");
			String repassword = scanStr("비밀번호를 다시 입력하세요 :");
			String ssn  = scanStr("주민등록본을 입력하세요 :");
			
		
			Map<String, String> result = service.signUp(username, password, repassword, ssn, name);

			if (result.get("success").equals("true" )  ) {
				System.out.println("회원가입 완료");
				break;
			} else if (result.get("success").equals("false") ) {
				if (result.get("samePassword").equals("false"  )   ) {
					System.out.println("입력하신 비밀번호가 다릅니다.");
				} else if (result.get("duplicate_username").equals("false") ) {
					System.out.println("이미 사용중인 아이디 입니다.");
				} else if (result.get("duplicate_ssn").equals("false") ) {
					System.out.println("이미 사용중인 주민등록본 입니다.");
				} else if (result.get("insert").equals("false")  ) {
					System.out.println("데이터를 생성하는데 에러가 발생 했습니다.");
				} else if (result.get("unknown").equals("unknown") ) {
					System.out.println("알수 없는 서비스 프로세스 에러 발생");
				} else {
					System.out.println("알수 없는 UI 프로세스 에러 발생");
				}
				
				System.out.println("회원 가입을 다시 하시겠습니까?");
				String cont = scanStr("다시시도 : [r]  회원가입 종료 [q]");
				while( (!cont.toUpperCase().equals("Q"))   && (!cont.toUpperCase().equals("R" ) ) ) {
					System.out.println("제대로 입력 바랍니다.");					
					System.out.println(cont);
					cont = scanStr("다시시도 : [r]  회원가입 종료 [q]");
				}
				if(cont.toUpperCase().equals("Q") ) {
					break;
				}
			}			
		}
	}
}
