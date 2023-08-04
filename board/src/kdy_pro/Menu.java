package kdy_pro;

import java.util.List;
import java.util.Scanner;

public class Menu {
	
	private int loginNum; //로그인 확인용 필드 
	private int contentNum; //게시물 번호(수정용도) 
	private String loginedId; //로그인 된 아이디 저장 할 객체(필드)
	
	Db db;
	BoardManager boardManager = new BoardManager();
	
	Menu(int loginNum) {
	
		db = new Db();
		this.loginNum = 0; //로그인전 로그인 번호 0(비로그인상태)로 초기화
	}
	
	public void setLoginNum(int loginNum) { //로그인 상태에따라 변화 로그인이면1,비로그인이면 0으로 지정
		
		this.loginNum = loginNum;
		
	}
	
	public void setLogindId(String id) { // 로그인 한 아이디 셋
		
		this.loginedId = id;
	
		
	}
	
	public String getLogindId() { //로그인 한 아이디 겟
		
		return loginedId; 
		
	}
	
	public int getContentNum() {
		
		return contentNum;
		
	}
	
	public void setContentNum(int num) {
		
		this.contentNum = num;
		
	}
	
	Scanner sc = new Scanner(System.in);
	
	
	//메인화면
	public void mainMenu() { 
		
		readBoard(); //게시판목록불러오기
		
		if(loginNum==0) { //로그인전 화면 
		
			System.out.println();
			System.out.println("게시물을 보시려면 로그인 하셔야 합니다.");
			
			beforeLogin();
			
		}else if(loginNum == 1 ) {
			
			System.out.println("1:읽기 2.작성하기 3.일어공부 7.로그아웃 8.종료");
			int num = Integer.parseInt(sc.nextLine());
			
			switch(num){
				
			case 1->readContent();
			case 2->rightBoard();
			case 3->goStudyJapanese();
			case 7->logOut();
			case 8->exit();
			}
			
		}
	
	}
	
	// 로그인 전 선택 매뉴
	private void beforeLogin() { 
		
		System.out.println("1.로그인하기 2.회원가입 3.종료");
		int choice = Integer.parseInt(sc.nextLine());
		LoginAndJoin loginAndJoin = new LoginAndJoin(); 
		
		if(choice==1) { //로그인 
			
		 	Member member = loginAndJoin.login();
			String result = db.selectLogin(member);
		 	
			if(!result.equals("불일치")&&!result.equals("비존재")) {
				
				System.out.println("로그인 되었습니다.");
				setLoginNum(1);//로그인넘버 로그인상태로 세팅
				setLogindId(result);//로그인된 아이디 저장
				System.out.println(getLogindId()+"님 환영합니다.");
				System.out.println();
				mainMenu();
				
			}else if(result.equals("불일치")){
				
				System.out.println("비밀번호가 일치하지 않습니다.");
				beforeLogin();
			
			}else {
				
				System.out.println("존재하지않는 아이디 입니다");
				beforeLogin();
			
			}
			
		}else if(choice==2) { //회원가입 
			
			Member member = loginAndJoin.join();
			
			int num = db.insertMember(member); 
			if(num==1) { //가입성공
				
				beforeLogin();
				
			}else {
				
				System.out.println("가입 실패 하셨습니다");
				beforeLogin();
			}
			
		}else if(choice==3) { //종료
			
			exit();
			
		}else {
			System.out.println();
			System.out.println("다른 번호를 입력하셨습니다.");
			System.out.println("다시 입력해주세요\n");
			beforeLogin();
		
		}
		
	}
	
	private void readBoard() { //게시물 읽기
		
		List<Board> list = db.selectBoard();
		boardManager.boardList(list);
		
	}
	
	private void rightBoard() {//게시물 쓰기
		
		Board board = boardManager.insertBoard(loginedId);
		db.insertBoard(board);
		mainMenu();
		
	}
	
	private void goStudyJapanese() { //일어공부 코너
		StudyJapanese studyJapanese = new StudyJapanese();
		System.out.println("1.노래가사 번역학습하기 2.노트 불러오기 3.뒤로가기");
		int num = Integer.parseInt(sc.nextLine());
		
		if(num==1) {
			
			int result = studyJapanese.studyTranslation(loginedId);
			
			if(result==1) { //학습완료후 복귀
				
				goStudyJapanese();
				
			}else { //학습완료가 아닌경우
				
				mainMenu();
			
			}
			
		}else if(num==2){ //노트 
			
			studyJapanese.noteTranslation(loginedId);
			
			goStudyJapanese();//학습완료
			
		}else if(num==3) {
			
			mainMenu();
			
		}
		
	}
	
	
	private void readContent() { //게시물 읽기
		
		System.out.println("읽고싶으신 번호를 입력하세요");
		int num = Integer.parseInt(sc.nextLine());
		Board board = db.selectContent(num);
		
		
		if(board!=null) {
			setContentNum(board.getNo());//게시물번호 수정용게시물번호 필드에 셋
			boardManager.selectContent(board); 
			
			if(loginedId.equals(board.getId())) {//로그인한 사람이 작성한 게시물인경우
				
				System.out.println("1.수정하기 2.삭제하기 3.일본어로 번역 4.뒤로가기");
				
				num = Integer.parseInt(sc.nextLine());
				
				if(num==1) { //수정
					
					reWriteBoard();
					
				}
				else if(num==2) {     
					
					deleteContent();//삭제
					
				}else if(num==3) { //게시물 일어로 번역
					
					readContentTranslate(board);
					
				}
				else{  //시간부족으로 다른 번호입력시 문제 처리 못함(시험끝나고 코딩필요) 
					
					mainMenu();
					
				}
				
				
			}else { //자신이 작성한것이 아닌경우
				
				System.out.println("1.일본어로 번역 2.뒤로가기"); 
				num = Integer.parseInt(sc.nextLine());
				
				if(num==1) {// 게시물 일어로 번역
					
					readContentTranslate(board);
					
				}
				
				if(num==2) {     //시간부족으로 다른 번호입력시 문제 처리 못함(시험끝나고 코딩필요) 
					mainMenu();
					
				}
				
			}
			
		}else {
			
			mainMenu();
			
		}
		
	}
	
	private void readContentTranslate(Board board) { 

		String title = board.getTitle();
		String content =  board.getContent();
		
		Translate translate = new Translate();
		title = translate.translateSelect(title);
		content = translate.translateSelect(content);
		
		Board boardJ = new Board();
		boardJ.setId(board.getId());
		boardJ.setTitle(title);
		boardJ.setLoadDay(board.getLoadDay());
		boardJ.setContent(content);
		
		boardManager.selectContentTranslate(boardJ);
		
		System.out.println("2.뒤로가기");
		int num = Integer.parseInt(sc.nextLine());
		if(num==2) {
			
			mainMenu();
			
		}
		
	}
	
	private void reWriteBoard() { //게시물 수정
		
		Board board = boardManager.changeBoard();
		
		board.setNo(contentNum);//로그인된 아이디 셋
		int result = db.changeBoard(board);
		
		if(result>0) {
			
			System.out.println("수정 성공했습니다");
			mainMenu();
			
		}else {
			
			System.out.println("수정 실패했습니다");
			mainMenu();
			
		}
		
	}
	
	private void deleteContent() { //게시물 삭제
	
		System.out.println("정말 삭제하시겠습니까?");
		System.out.println("1.네 2.아니요");
		int num = Integer.parseInt(sc.nextLine());
		
		if(num==1) {
			
			int result = db.delete(contentNum);
			
			if(result>0) {
				
				System.out.println("삭제되었습니다");
				mainMenu();
			}else {
				System.out.println("삭제 실패하였습니다");
				mainMenu();
			}
			
		}else {
			
			System.out.println();
			System.out.println("삭제를 취소하고 메인으로 돌아갑니다.");
			System.out.println();
			mainMenu();
		}
	}
	
	private void logOut() { //로그아웃
	
		setLoginNum(0);
		System.out.println("로그아웃 되었습니다");
		System.out.println();
		mainMenu();
		
	}

	private void exit() { // 종료
		
		db.closeConn();
		System.out.println("프로그램을 종료합니다");
	
	}
	
}
