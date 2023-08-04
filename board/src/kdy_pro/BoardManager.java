package kdy_pro;

import java.util.List;
import java.util.Scanner;






public class BoardManager {

	Scanner sc = new Scanner(System.in);
	
	//게시판목록
	public void boardList(List<Board> list) { 
			
			System.out.println("게시물 목록");
			System.out.println("-----------------------------------------------------");
			System.out.printf("%-6s%-12s%-20s%-40s\n","번호","작성자","제목","작성일");
			System.out.println("-----------------------------------------------------");
			
			
			//int num = 1;
			for(Board l:list) {
				
				System.out.printf("%-6s%-12s%-16s%-40s",l.getNo(),l.getId(),l.getTitle(),l.getLoadDay());
				//System.out.printf("%-6s%-12s%-16s%-40s",num,l.getId(),l.getTitle(),l.getLoadDay());
				
				//num++;
				System.out.println();
				}
				System.out.println();
			
		}
	
	public Board insertBoard(String id) { //게시물 입력
		
		System.out.println("-개시물입력-");
		
		System.out.println("제목 : ");
		String title = sc.nextLine();
		
		System.out.println("내용 : ");
		String content = sc.nextLine();
		
		Board board = new Board();
		
		board.setTitle(title);
		board.setContent(content);
		board.setId(id);
		
		return board;
	
	}

	public void selectContent(Board board) {
		
		System.out.println();
		System.out.println("-------------------------------------------------------------");
		System.out.println("작성자 : "+ board.getId());
		System.out.println("작성일 : "+ board.getLoadDay());
		System.out.println("제목  : "+board.getTitle()+"\n");
		System.out.println("내용  :"+board.getContent());
		System.out.println();
		
	}
	
	public void selectContentTranslate(Board board) {
			
			System.out.println();
			System.out.println("-------------------------------------------------------------");
			System.out.println("作成者 : "+ board.getId());
			System.out.println("作成日 : "+ board.getLoadDay());
			System.out.println("タイトル  : "+board.getTitle()+"\n");
			System.out.println("内容  :"+board.getContent());
			System.out.println();
			
		}
		
	public Board changeBoard() {
		
		System.out.println("-개시물수정-");
		
		System.out.println("수정할 제목 : ");
		String title = sc.nextLine();
		
		System.out.println("수정할 내용 : ");
		String content = sc.nextLine();
		
		Board board = new Board();
		
		board.setTitle(title);
		board.setContent(content);
		
		return board;
	}
	
}
