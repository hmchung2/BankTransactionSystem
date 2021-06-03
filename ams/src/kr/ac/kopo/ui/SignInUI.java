package kr.ac.kopo.ui;

import java.util.Map;

public class SignInUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("--------------");
			System.out.println("\t 로그인");
			System.out.println("--------------");
			String username = scanStr("아이디를 입력하세요 :");
			String password = scanStr("비밀번호를 입력하세요 :");
			Map<String, String> result = service.signIn(username, password);
			if (result.get("success").equals("true")) {
				System.out.println("로그인 완료");
				break;
			} else if (result.get("success").equals("false")) {
				if (result.get("usernameExist").equals("false")) {
					System.out.println("회원가입 되지 않은 아이디를 입력 하셨습니다.");
				} else if (result.get("samePassword").equals("false")) {
					System.out.println("비밀 번호를 잘못 입력 하셨습니다.");
				} else {
					System.out.println("알수 없는 UI 프로세스 에러 발생");
				}
				System.out.println("다시 로그인 시도 하시겠습니까?");
				String cont = scanStr("다시시도 : [r]  회원가입 종료 [q]");
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
}
