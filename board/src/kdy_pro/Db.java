package kdy_pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Db {
	
	private static Connection conn; //커넥션
	
	Db(){
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "lee";
		String pw = "1234";
		
		try {
		
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
			//System.out.println("연결성공");
		
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	//Db에 회원가입정보 입력
	public int insertMember(Member member){
		
		String sql = "insert into member (id, pw , name, phone, email, address, signUpDay) values(?,?,?,?,?,?,sysdate)";	
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, member.getId());
			pst.setString(2, member.getPw());
			pst.setString(3, member.getName());
			pst.setInt(4, member.getPhone());
			pst.setString(5, member.getEmail());
			pst.setString(6, member.getAddress());
			
			
			
			int result = pst.executeUpdate();
		
			if(result>0) {
				
				System.out.println("가입되었습니다.");
				System.out.println("로그인 부탁드립니다\n");
				return 1;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
		return 0;
	}
	
	//Db에서 id 가져와 확인후 로그인
	public String selectLogin(Member member) {
		
		String sql = "select id, pw from member where id = ?";
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, member.getId());
			ResultSet rs = pst.executeQuery();
			
			
			if(rs.next()) {
				
				if(member.getPw().equals(rs.getString("pw"))) {
					
					String result = member.getId();
					rs.close();
					return result; //아이디와 비밀번호 일치할때
					
				}else {
					rs.close();
					return "불일치"; // 아이디는 일치하나 비밀번호가 불일치할때
					
				}
				
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return "비존재"; //존재하지 않는 아이디인 경우
	}
	//게시물 모두 가져오기
	public List selectBoard(){
		
		List<Board> list = new ArrayList<>();
		String sql = "select * from board ";
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Board board = new Board();
				board.setNo(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setContent(rs.getString(3));
				board.setId(rs.getString(4));
				board.setLoadDay(rs.getDate(5));
			
				list.add(board);
			
				}
			
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
	return list;	
	}

	
	//게시글 작성
	public void insertBoard(Board board) { 

		String sql = "insert into board (no, title, content, id, loadday) values(board_sequence.nextval,?,?,?,sysdate)";
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
		pst.setString(1, board.getTitle());	
		pst.setString(2, board.getContent());	
		pst.setString(3, board.getId());	
		int result =  pst.executeUpdate();
		if(result>0) {
			
			System.out.println("입력하였습니다");
			System.out.println();
		}else {
			
			System.out.println("입력 실패하였습니다");
			System.out.println();
		}
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
		
		
	}
	
	//게시물읽기
	public Board selectContent(int num) { 
		
		String sql = "select * from board where no = ?";
		Board board = new Board();
		try(PreparedStatement pst = conn.prepareStatement(sql)){
		
			pst.setInt(1, num);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				
				
				board.setNo(rs.getInt("no"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setId(rs.getString("id"));
				board.setLoadDay(rs.getDate("loadday"));
				
			}else { //해당하는 번호가 없을때
				
				System.out.println("번호가 일치하지않습니다");
				return null;
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		}
		return board;
	}
	
	//게시물 수정
	public int changeBoard(Board board) { 
		
		String sql = "update board set title = ?, content = ? where no = ?";	
	
		int result = 0;
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, board.getTitle());
			pst.setString(2, board.getContent());
			pst.setInt(3, board.getNo());
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	//게시물삭제
	public int delete(int contentNum) { 
		
		String sql = "delete from board where no = ?";
		int result = 0;
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setInt(1, contentNum);
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
		return result;
	}
	
	//노트 insert
	static void insertNote(Song song) {
		
		String sql = "insert into note_trans (id, songtitle, singer, lyrics) values(?,?,?,?)";
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, song.getId());	
			pst.setString(2, song.getSongTitle());	
			pst.setString(3, song.getSinger());
			pst.setString(4, song.getLyrics());
			
			int result =  pst.executeUpdate();
			if(result>0) {
				
				System.out.println("입력하였습니다");
				System.out.println();
			
			}else {
				
				System.out.println("입력 실패하였습니다");
				System.out.println();
			
			}
			
			} catch (SQLException e) {
				
				e.printStackTrace();
			
			}
		
	}
	
	static List selectNote(String id) { //노트 가져오기
		
		String sql = "select * from note_trans where id = ?";
		List<Song> list = new ArrayList<>();
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				Song song = new Song(id,rs.getString("singer"),rs.getString("songtitle"),rs.getString("lyrics"));
				
				list.add(song);
			
				}
			
			rs.close();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
	
		return list;	
		
	}
	
	static void deleteNote(Song song) { //노트의 가사 지우기
		
		String sql = "delete from note_trans where lyrics = ?";
		int result = 0;
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, song.getLyrics());
			result = pst.executeUpdate();
			System.out.println("삭제완료\n");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	static int saveStudy(Song song) { //번역학습 중도 저장하기
		
		String sql = "insert into save_study (id, savenum, songtitle) values(?,?,?)";
		int result = 0;
		
		try(PreparedStatement pst = conn.prepareStatement(sql)){
			
			pst.setString(1, song.getId());	
			pst.setInt(2, song.getSvaeNum());	
			pst.setString(3, song.getSongTitle());
			
			
			result =  pst.executeUpdate();
			
			} catch (SQLException e) {
				
				e.printStackTrace();
			
			}
		
		return result;
		
	}
	
	static Song selectSave(String loginedId) { //번역학습도중 저장한거 불러오기
		
		String sql = "select savenum, songtitle from save_study where id=?";
		Song song = new Song();
		try(PreparedStatement pst = conn.prepareStatement(sql)){
		
			pst.setString(1, loginedId);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				
				song.setId(loginedId);
				song.setSvaeNum(rs.getInt("savenum"));
				song.setSongTitle(rs.getString("songtitle"));
				
			}else { //해당하는 번호가 없을때
				
				System.out.println("번호가 일치하지않습니다");
				return null;
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		}
		return song;
		
	}


	//커넥션 연결 끊기
	public void closeConn() { 		
	
		if(conn!=null) {
			try {
				conn.close();
				System.out.println("연결을 종료합니다");
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	
}
