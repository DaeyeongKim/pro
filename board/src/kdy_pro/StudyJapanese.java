package kdy_pro;

import java.util.List;
import java.util.Scanner;

public class StudyJapanese {
	
	Translate translate = new Translate();
	Scanner sc = new Scanner(System.in);
	int finishNum; //학습완료 체크번호 0:학습미완료 , 1:학습완료
	private String loginedId; //현재 로그인되어있는 아이디
	
	public int studyTranslation(String loginedId) {
		
		this.loginedId=loginedId; //현재 로그인된 아이디 저장
		
		System.out.println("1.새로시작하기 2.불러오기 3.뒤로가기");
		int num = Integer.parseInt(sc.nextLine());
		
		if(num==1) { //새로 시작
			
			Song song = new Song();
			song.setSvaeNum(0);
			newTranslation(song);
			
			if(finishNum==1) {//학습완료
				
				System.out.println("수고하셨습니다. 뒤로 돌아가겠습니다. ");//다 끝나고 실행
				return finishNum;
			
			}
		
		}if(num==2) { //불러오기
			
			Song song = Db.selectSave(loginedId);//db에서 저장된 노래이름, 순서번호 가져오기
			if(song!=null) {
				
				newTranslation(song);
				
			}else {
				
				System.out.println("불러올 정보가 없습니다.");
				System.out.println("뒤로갑니다\n");
			}
			
			if(finishNum==1) {//학습완료
				
				System.out.println("수고하셨습니다. 뒤로 돌아가겠습니다. ");//다 끝나고 실행
				return finishNum;
			
			}
			
		}
		
		return 0;
	
	}

	public void noteTranslation(String loginedId2) {//노트 메뉴
		
		Song song = new Song();
		System.out.println();
		System.out.println("저장된 노트의 단어로 다시 번역 학습이 가능합니다.");
		System.out.println("1.노트 학습하기  3.뒤로가기.");
		
		int num = Integer.parseInt(sc.nextLine());
		
		if(num==1) {
			
			List<Song> list = Db.selectNote(loginedId2); 
			
			for(Song l:list) {
				
				System.out.println(l.getLyrics());
				System.out.println("1.다음부분 2.번역보기 3.노트에서 삭제하기 4.뒤로가기");
				int num1 = Integer.parseInt(sc.nextLine()); //메뉴 선택
				
				if(num1==1) { //다음부분으로 넘어가기
					
				}else if(num1==2){//번역
					
					String translatedLyrics =  translate.translateSelect(l.getLyrics());
					System.out.println("\n"+translatedLyrics+"\n");
					System.out.println("1.다음부분 2.노트에 삭제하기 3.뒤로가기");
					int num2 = Integer.parseInt(sc.nextLine()); //메뉴 선택
					
					if(num2==1) {//다음부분으로 넘어가기
						
					}else if(num2==2) {//삭제
						
						song.setLyrics(l.getLyrics());
						Db.deleteNote(song);
						
					}else if(num2==3) {//뒤로가기
						
						break;
					}
					
					
				}else if(num1==3) {//삭제
					
					song.setLyrics(l.getLyrics());
					Db.deleteNote(song);
				
				}else if(num1==4) {//뒤로가기
					
					break;
					
				}
			
			}
			
			System.out.println("학습을 완료하였습니다.");
			
		}
	
	}
	
	private void newTranslation(Song check) { // 노래가사 번역학습
		
		int saveCheck = 0; //저장여부 체크번호
		String choiceSong = null; //저장된경우 가져올 노래제목
		
		if(check.getSvaeNum()==0) { //새로시작
			
			saveCheck = 0;
		
		}else if(check.getSvaeNum()>0||check.getSongTitle()!=null) { //저장된 부분부터 시작
			System.out.println("불러오기 성공");
			saveCheck=check.getSvaeNum(); //저장내용이 있는 경우 저장넘버와 노래 제목을 넣는다 
			choiceSong = check.getSongTitle();
			
		}
		finishNum = 0;//학습완료 체크번호 미학습으로 초기화
		int count = 1; 
		
		WebSelect webSelect = new WebSelect();

		if(choiceSong == null) { //새 시작일때 노래 제목입력
		
		System.out.println("번역하고 싶은 노래를 입력주세요.");
		System.out.println();
		choiceSong = sc.nextLine();
		
		}
		String[] lyricsArr = webSelect.selectSongsLyrics(choiceSong);
		
		
		if(lyricsArr.length>3) {
		
			for(;saveCheck <=lyricsArr.length-1;saveCheck++) { //가사 번역 하기 실행
				
				//if(i==0) {
				
				System.out.println(lyricsArr[saveCheck]+"\n");//맨처음가사 보여주기　//한줄 띄우는거 해결하기
				count++;
				//}
				
				System.out.println("1.번역입력하기 2.번역보기 3.다음부분 4.끝내기(저장할건지 묻기)");	
				
				int num = Integer.parseInt(sc.nextLine()); //메뉴 선택
				
				//if(i>0) {
				//	System.out.println("\n"+i+1+" "+lyricsArr[i]+"\n");//두번째 가사 부터보여주기 
				//}
				
				if(num==1) {
					
					System.out.println("\n 직접 번역한것을 입력해보세요");
					String selfTranslate = sc.nextLine();
					System.out.println("나의 번역 : "+"\n"+selfTranslate+"\n");
					System.out.println("1.다음부분 ");
					int num1 = Integer.parseInt(sc.nextLine()); //메뉴 선택
					if(num1==1) { //다음 가사로 넘어감
						
					}
					
				}else if(num==2) { // 번역보기 선택
					
					String lyrics = lyricsArr[saveCheck];
					String translatedLyrics =  translate.translateSelect(lyrics);
					System.out.println("\n"+translatedLyrics+"\n");
					System.out.println("1.다음부분 2.노트에 저장하기 3.끝내기(저장할건지 묻기)");
					int num2 = Integer.parseInt(sc.nextLine()); //메뉴 선택
					
					if(num2==1) { //다음 가사로 넘어감
				
					}if(num2==2) {//복습노트에 저장하기
						
						//가사,제목,가수명 저장
						
						String singer = webSelect.getSinger();
						Song song = new Song(loginedId,singer,choiceSong,lyricsArr[saveCheck]);
						Db.insertNote(song);
					
					}if(num2==3) {//뒤로가기
						
						System.out.println("저장하시겠습니까?");
						System.out.println("1.저장하기 2.저장하지 않고 나가기)");
						int choice = Integer.parseInt(sc.nextLine()); //저장여부 선택
						
						if(choice==1) {//저장하기 선택 
							
							saveSong(loginedId, saveCheck, choiceSong);
							break;
							
						}else{
							
							break;
								
						}
						
					}
					
				}else if(num==3) { //다음부분으로 넘어가기
				
				}else if(num==4) {
					
					System.out.println("저장하시겠습니까?");
					System.out.println("1.저장하기 2.저장하지 않고 나가기)");
					int choice = Integer.parseInt(sc.nextLine()); //저장여부 선택
					
					if(choice==1) {//저장하기 선택 
						
						saveSong(loginedId, saveCheck, choiceSong);
						break;
					
					}else{
						
						break;
							
					}
					
				}
				
			}
		
		}else if(lyricsArr.length<3) {
			
			System.out.println("찾지 못했습니다.\n");
			Song song = new Song();
			song.setSvaeNum(0);
			newTranslation(song);
			
		}
		
		if(count==lyricsArr.length) { //학습완료
		
			System.out.println("학습 완료!");
			finishNum = 1;
		
		}
		
	}

	private void saveSong(String loginedId,int i, String choiceSong) { //중도저장
		
		Song song = new Song();
		song.setId(loginedId);
		song.setSvaeNum(i);
		song.setSongTitle(choiceSong);
		int result = Db.saveStudy(song);
		
		if(result>0) {//저장성공
			
			System.out.println("저장하였습니다");
			System.out.println();
			
			
		}else {//저장실패
			
			System.out.println("저장 실패하였습니다");
			System.out.println();
			
			
		}
	
	}
	
	
}
