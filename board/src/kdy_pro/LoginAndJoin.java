package kdy_pro;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;




public class LoginAndJoin {
	
	SHA256 sha256 = new SHA256();
	Scanner sc = new Scanner(System.in);
	
	//회원가입
	public Member join() { 
			
		System.out.println("id : "); //id입력
		String id = sc.nextLine();
		System.out.println();
		
		System.out.println("pw : "); //비밀번호 입력
		String pw = sc.nextLine();
		System.out.println();
		
		System.out.println("이름 : "); //이름 입력
		String name = sc.nextLine();
		System.out.println();
		
		System.out.println("전화번호 : "); //전화번호 입력
		int phone = Integer.parseInt(sc.nextLine());
		System.out.println();
		
		System.out.println("이메일 : "); //이메일 입력
		String email = sc.nextLine();
		System.out.println();
		
		System.out.println("주소 : "); //이메일 입력
		String address = sc.nextLine();
		System.out.println();
			
		
		Member member = new Member();
		try {
			
			String encryptedPw = sha256.encrypt(pw); // 해시값으로 암호화
			
			member.setId(id);
			member.setPw(encryptedPw);
			member.setName(name);
			member.setPhone(phone);
			member.setEmail(email);
			member.setAddress(address);
			
			
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		
		} 
		return member;
	}
		
	//로그인
	public Member login() { 
			
		System.out.println("id : "); //id 입력
		String id = sc.nextLine();
		
		System.out.println("pw : "); //비밀번호 입력
		String pw = sc.nextLine();
		
		Member member = new Member();
		
		try {
		
			String encryptedPw = sha256.encrypt(pw); //로그인전 비밀번호를 확인하기 위해 해시값으로 바꾼다 
			member.setId(id);
			member.setPw(encryptedPw);
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		
		}
		
		return member;
		
		}
	
}
