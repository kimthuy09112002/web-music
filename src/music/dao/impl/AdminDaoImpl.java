package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.Constant;
import music.dao.AdminDao;
import music.util.JdbcUtil;
import music.vo.Admin;

/**
 * Lớp triển khai Administrator DAO
 * @author 
 *
 */
public class AdminDaoImpl implements AdminDao {
	/**
	* Lưu đối tượng quản trị vào cơ sở dữ liệu
	* @param quản trị đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(Admin admin) {
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO admin(adminUsername, adminPassword, adminRegisterDate, adminLastDate) VALUES(?,?,?,?)";
		JdbcUtil jdbc = null;
		System.out.println("Kiểm tra sự tồn tại của...");
		// Xác định xem người dùng có tồn tại không
		if (!isExist(admin)){
			
			String adminUsername = admin.getAdminUsername();
			String adminPassword = admin.getAdminPassword();
			Date adminRegisterDate = admin.getAdminRegisterDate();
			Date adminLastDate = admin.getAdminLastDate();
			
			System.out.println("Người dùng không tồn tại : " + adminUsername);
			
			
			if (adminUsername != null && !"".equals(adminUsername)){
				params.add(adminUsername);
			} else {
				return false;
			}
			if (adminPassword != null && !"".equals(adminPassword)){
				params.add(adminPassword);
			} else {
				return false;
			}
			if (adminRegisterDate != null){
				params.add(adminRegisterDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
			if (adminLastDate != null){
				params.add(adminLastDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
			System.out.println("Bắt đầu lặp đi lặp lại!");
			for (Object object : params) {
				System.out.println(object);
			}
			System.out.println("Kết thúc lặp lại");
			try {
				jdbc = new JdbcUtil();
				jdbc.getConnection();
				jdbc.updateByPreparedStatement(sql, params);
			} catch (SQLException e) {
				throw new RuntimeException("Lưu ngoại lệ quản trị viên!", e);
			} finally {
				if (jdbc != null) {
					jdbc.releaseConn();
				}
			}
		} else {
			return false;
		}
		System.out.println("Thành công!");
		return true;
	}

	/**
	* Sửa đổi đối tượng quản trị cơ sở dữ liệu
	* @param quản trị đối tượng cần sửa đổi
	*/
	@Override
	public boolean update(Admin admin) {
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE admin SET adminPassword=? WHERE adminId=?";
		JdbcUtil jdbc = null;
		
		String adminPassword = admin.getAdminPassword();
		int adminId = admin.getAdminId();
		
		if (adminPassword != null && !"".equals(adminPassword)){
			params.add(adminPassword);
		} else {
			return false;
		}
		params.add(adminId);
		
		System.out.println("Thay đổi mật khẩu để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Thay đổi mật khẩu lặp đi lặp lại kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Sửa đổi ngoại lệ thông tin quản trị viên!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	* Đăng nhập quản trị viên
	* quản trị viên @param
	* @return Nếu đăng nhập thành công trả về đối tượng quản trị viên, ngược lại trả về null
	*/
	@Override
	public Admin login(Admin admin) {
		Admin result = null;
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = admin.getAdminUsername();
		String password = admin.getAdminPassword();
		
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM admin WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and adminUsername=?");
		} else {
			return null;
		}
		if (password != null && !"".equals(password)){
			params.add(password);
			sql.append(" and adminPassword=?");
		} else {
			return null;
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
			if (mapList != null && !mapList.isEmpty()){
				System.out.println("mapList : " + mapList.toString());
				result = new Admin(mapList.get(0));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ngoại lệ đăng nhập của quản trị viên!", e);
		} finally {
			if (jdbc != null){
				jdbc.releaseConn();
			}
		}
		
		return result;
	}
	
	/**
	* Truy vấn người dùng có tồn tại hay không, truy vấn theo tên người dùng
	* Người dùng truy vấn @param searchModel
	* @return trả về true nếu tồn tại, ngược lại trả về false
	*/
	public static boolean isExist(Admin searchModel){

		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = searchModel.getAdminUsername();
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM admin WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and adminUsername=?");
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn xem quản trị viên có bất thường không!", e);
		} finally {
			if (jdbc != null){
				jdbc.releaseConn();
			}
		}
		for (Map<String, Object> map : mapList) {
			System.out.println(map);
		}
		return mapList.isEmpty() ? false : true;
	}

	
}
