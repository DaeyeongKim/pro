package kdy_pro;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.Scanner;

import org.jsoup.select.Elements;

public class WebSelect {
	
	Scanner sc = new Scanner(System.in);
   
	
	private String[] lyricsArr;//가사담을 배열
    private String url; 
    private Connection conn; 
    private String singer;
    
    public String getSinger() { //가수이름 가져오기
    	
    	return singer;
    	
    }
    
    
	public String[] selectSongsLyrics(String choiceSong) {
			
			url ="https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="+choiceSong+" 가사";
			conn = Jsoup.connect(url);
			
			Document document;
			try {
				document = conn.get();
				Elements showMe = document.getElementsByClass("text no_ellipsis type_center _content_text");//해당클래스의 것을 가져온다,가사 가져오기
				Elements showMe2 = document.getElementsByClass("info_group");//가수이름 가져오기 
				
				String bsinger = showMe2.get(0).text(); //가수이름 첫번째 정보 가져오기
				String[] singerArr = bsinger.split(" "); // " "를 기준으로 나누기
				this.singer = singerArr[1];//2번쨰로 들어있는 가수이름 가져오기
				
				String elementHTML = showMe.html(); //html형식으로 가져온다.
				
				//<br개수 세기>
				int count = 0; //<br> 개수세는 카운터
				int index = 0;
				
				while((index = elementHTML.indexOf("<br>",index)) != -1) {
					
					count++;
					index +=4;
					
				}
				
				//배열에 가사를 "<br>"을 제외하고 담기
				for(int i = 1; i <= count+1; i++) {
					
					lyricsArr = elementHTML.split("<br>");
					//System.out.println(i+"번째 "+lyricsArr[i]);
					
				}
				
				//System.out.println(lyricsArr.length);
				
				if(elementHTML==null||showMe.size()==0) {
					System.out.println("결과가 없습니다.");
					}else {
						
							
						
//						for(int i =0;i <=lyricsArr.length-1;i++) {
//							
//							//System.out.println(i+lyricsArr[i]);
//							
//						}
						
					}
				
				} catch (IOException e) {
					
					System.out.println("없습니다.");
					e.printStackTrace();
				
				}
		
			return lyricsArr;
			
		}
	
}
