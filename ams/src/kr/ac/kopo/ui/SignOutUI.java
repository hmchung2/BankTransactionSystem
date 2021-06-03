package kr.ac.kopo.ui;

public class SignOutUI extends BaseUI {

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("로그아웃 완료 햇습니다.");
		service.signOut();
	}
	
	
}
