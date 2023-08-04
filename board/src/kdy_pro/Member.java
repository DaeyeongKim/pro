package kdy_pro;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

//회원 등록 정보
@Data
public class Member { 
	
	private String id;
	private String pw;
	private String name;
	private int phone;
	private String email;
	private String address;
	private Date signUpDay; //가입일
	
}
