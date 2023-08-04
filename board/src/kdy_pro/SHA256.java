package kdy_pro;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//비밀번호를 해쉬값, 16진수로 변환하여 보안화
public class SHA256 { 

	 public String encrypt(String text) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256"); //해시알고리즘 인스턴스 가져옴
	        md.update(text.getBytes()); //입력값을 바이트배열로 반환,해시알고리즘에 입력

	        return bytesToHex(md.digest()); //해쉬값가져와서 bytesToHex()로 보냄
	    }

	    private String bytesToHex(byte[] bytes) { //해시값을 16진수 문자열로 변환 
	        StringBuilder builder = new StringBuilder();
	        for (byte b : bytes) {
	            builder.append(String.format("%02x", b));
	        }
	        return builder.toString();
	    }
	
}
