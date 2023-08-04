package kdy_pro;

import java.util.Date;

public class Song { //노래가사 객체
	
	private String id;
	private String singer;
	private String songTitle;
	private String lyrics;
	private int svaeNum;
	
	public Song() {

	}
	
	public Song(String id,String singer,String songTitle ,String lyrics) {
		
		this.singer = singer;
		this.id = id;
		this.songTitle = songTitle;
		this.lyrics = lyrics;
		
	}

	
	
	public int getSvaeNum() {
		return svaeNum;
	}

	public void setSvaeNum(int svaeNum) {
		this.svaeNum = svaeNum;
	}

	public String getId() {
		return id;
	}

	public String getSinger() {
		return singer;
	}

	public String getSongTitle() {
		return songTitle;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	
	
	
}
