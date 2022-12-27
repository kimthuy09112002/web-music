package music.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class NormalUser implements Serializable{
	
	private static final long serialVersionUID = -2688544176336105712L;
	
	int userId;				// ID người dùng
	String userName;		// tên tài khoản
	String userPassword;	// mật khẩu người dùng
	String userNickname;	// Biệt hiệu của người dùng
	int userSex;			// Giới tính người dùng (0. Khác, 1. Nam, 2. Nữ)
	String userEmail;		// Email người dùng
	String userAvatar;		// Hình đại diện
	Date userRegisterDate;	// Thời gian đăng ký người dùng
	Date userLastDate;		// Lần đăng nhập cuối cùng của người dùng
	int userStatus;			// Tâm trạng người dùng
	
	
	public NormalUser() {
		
	}
	public NormalUser(Map<String, Object> map){
		this.userId = (int) map.get("userId");
		this.userName = (String) map.get("userName");
		this.userPassword = (String) map.get("userPassword");
		this.userNickname = (String) map.get("userNickname");
		this.userSex = (int) map.get("userSex");
		this.userEmail = (String) map.get("userEmail");
		this.userAvatar = (String) map.get("userAvatar");
		this.userRegisterDate = (Date) map.get("userRegisterDate");
		this.userLastDate = (Date) map.get("userLastDate");
		this.userStatus = (int) map.get("userStatus");
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public int getUserSex() {
		return userSex;
	}
	public void setUserSex(int userSex) {
		this.userSex = userSex;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	public Date getUserRegisterDate() {
		return userRegisterDate;
	}
	public void setUserRegisterDate(Date userRegisterDate) {
		this.userRegisterDate = userRegisterDate;
	}
	public Date getUserLastDate() {
		return userLastDate;
	}
	public void setUserLastDate(Date userLastDate) {
		this.userLastDate = userLastDate;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	
	
}
