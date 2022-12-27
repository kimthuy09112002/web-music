package music.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


public class Admin implements Serializable{

	private static final long serialVersionUID = 8714071127799262867L;
	
	
	int adminId;			//ID quản trị viên
	String adminUsername;	//tên người dùng quản trị
	String adminPassword;	//mật khẩu quản trị viên
	Date adminRegisterDate;	//Thời gian đăng ký của quản trị viên
	Date adminLastDate;		//Lần đăng nhập cuối cùng của quản trị viên
	
	// Phương pháp xây dựng
	public Admin() {
		
	}
	public Admin(Map<String, Object> map){
		this.adminId = (int) map.get("adminId");
		this.adminUsername = (String) map.get("adminUsername");
		this.adminPassword = (String) map.get("adminPassword");
		this.adminRegisterDate = (Date) map.get("adminRegisterDate");
		this.adminLastDate = (Date) map.get("adminLastDate");
	}
	
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public Date getAdminRegisterDate() {
		return adminRegisterDate;
	}
	public void setAdminRegisterDate(Date adminRegisterDate) {
		this.adminRegisterDate = adminRegisterDate;
	}
	public Date getAdminLastDate() {
		return adminLastDate;
	}
	public void setAdminLastDate(Date adminLastDate) {
		this.adminLastDate = adminLastDate;
	}
	
	
}
