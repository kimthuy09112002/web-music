package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.Constant;
import music.dao.NormalUserDao;
import music.util.JdbcUtil;
import music.vo.NormalUser;

public class NormalUserDaoImpl implements NormalUserDao {

	@Override
	public boolean singup(NormalUser normalUser) {
		
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normaluser(userName, userPassword, userNickname, userSex, userEmail, userAvatar, userRegisterDate, userLastDate, userStatus) VALUES(?,?,?,?,?,?,?,?,?)";
		JdbcUtil jdbc = null;
		System.out.println("Kiểm tra sự tồn tại của...");
		// Xác định xem người dùng có tồn tại không
		if (!isExist(normalUser)){
			
			String userName = normalUser.getUserName();
			String userPassword = normalUser.getUserPassword();
			String userNickname = normalUser.getUserNickname();
			int userSex = normalUser.getUserSex();
			String userEmail = normalUser.getUserEmail();
			String userAvatar = normalUser.getUserAvatar();
			Date userRegisterDate = normalUser.getUserRegisterDate();
			Date userLastDate = normalUser.getUserLastDate();
			int userStatus = normalUser.getUserStatus();
			
			if (userName != null && !"".equals(userName)){
				params.add(userName);
			} else {
				return false;
			}
			if (userPassword != null && !"".equals(userPassword)){
				params.add(userPassword);
			} else {
				return false;
			}
			if (userNickname != null && !"".equals(userNickname)){
				params.add(userNickname);
			} else {
				return false;
			}
			params.add(userSex);
			if (userEmail != null && !"".equals(userEmail)){
				params.add(userEmail);
			} else {
				return false;
			}
			if (userAvatar != null && !"".equals(userAvatar)){
				params.add(userAvatar);
			} else {
				return false;
			}
			if (userRegisterDate != null){
				params.add(userRegisterDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
			if (userLastDate != null){
				params.add(userLastDate);
			} else {
				params.add(Constant.DEFAULT_DATE);
			}
			params.add(userStatus);
			
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
				throw new RuntimeException("Lưu người dùng ngoại lệ!", e);
			} finally {
				if (jdbc != null) {
					jdbc.releaseConn();
				}
			}
		} else {
			return false;
		}
		System.out.println("Đã lưu người dùng thành công!");
		return true;
	}
	/**
	* Người dùng đăng nhập
	* @param normalUser đóng gói thông tin đăng nhập, bao gồm tên người dùng và mật khẩu
	* @return Đối tượng NormalUser được trả về nếu đăng nhập thành công và trả về null nếu đăng nhập không thành công
	*/
	@Override
	public NormalUser login(NormalUser normalUser) {
		NormalUser result = null;
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = normalUser.getUserName();
		String password = normalUser.getUserPassword();
		
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM normaluser WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and userName=?");
		} else {
			return null;
		}
		if (password != null && !"".equals(password)){
			params.add(password);
			sql.append(" and userPassword=?");
		} else {
			return null;
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
			if (mapList != null && !mapList.isEmpty()){
				System.out.println("mapList : " + mapList.toString());
				result = new NormalUser(mapList.get(0));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Người dùng ngoại lệ đăng nhập!", e);
		} finally {
			if (jdbc != null){
				jdbc.releaseConn();
			}
		}
		
		return result;
	}
	
	/**
	* Xác minh rằng người dùng tồn tại dựa trên tên người dùng
	* Tên người dùng @param normalUser được gói gọn trong đối tượng NormalUser
	* @return true nếu tồn tại, false nếu không
	*/
	public boolean isExist(NormalUser normalUser){
		
		List<Object> params = new ArrayList<Object>();
		List<Map<String, Object>> mapList = null;
		String username = normalUser.getUserName();
		JdbcUtil jdbc = null;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM normaluser WHERE 1=1");
		if (username != null && !"".equals(username)){
			params.add(username);
			sql.append(" and userName=?");
		}
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			mapList = jdbc.findResult(sql.toString(), params);
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn xem người dùng có bất thường không!", e);
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

	@Override
	public boolean setting(NormalUser normalUser) {
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userNickname=?, userSex=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		String userNickname = normalUser.getUserNickname();
		int userSex = normalUser.getUserSex();
		int userId = normalUser.getUserId();
		
		if (userNickname != null && !"".equals(userNickname)){
			params.add(userNickname);
		} else {
			return false;
		}
		params.add(userSex);
		params.add(userId);
		
		System.out.println("Sửa đổi thông tin cơ bản để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Sửa đổi thông tin cơ bản kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Sửa đổi thông tin cơ bản ngoại lệ", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	 * Lưu hình đại diện người dùng
	 * @param normalUser
	 * @return
	 */
	@Override
	public boolean save_avatar(NormalUser normalUser) {
		
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userAvatar=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		String userAvatar = normalUser.getUserAvatar();
		int userId = normalUser.getUserId();
		
		if (userAvatar != null && !"".equals(userAvatar)){
			params.add(userAvatar);
		} else {
			return false;
		}
		params.add(userId);
		
		System.out.println("Sửa đổi hình đại diện để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Sửa đổi hình đại diện lặp đi lặp lại kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Sửa đổi avatar là bất thường!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	 * Sửa đổi mật khẩu người dùng theo userId
	 * @param normalUser
	 * @return
	 */
	@Override
	public boolean save_psw(NormalUser normalUser) {
		
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userPassword=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		String userPassword = normalUser.getUserPassword();
		int userId = normalUser.getUserId();
		
		if (userPassword != null && !"".equals(userPassword)){
			params.add(userPassword);
		} else {
			return false;
		}
		params.add(userId);
		
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
			throw new RuntimeException("Thay đổi mật khẩu ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	/**
	 * Cấm người dùng theo ID người dùng
	 * @param normalUser Đóng gói id người dùng vào một đối tượng
	 * @return
	 */
	@Override
	public boolean ban(NormalUser normalUser) {
		
		int result = -1;
		List<Object> params = new ArrayList<Object>();
		String sql = "UPDATE normaluser SET userStatus=? WHERE userId=?";
		JdbcUtil jdbc = null;
		
		int userStatus = Constant.USER_STATUS_BAN;
		int userId = normalUser.getUserId();
		
		params.add(userStatus);
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				result = 1;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Người dùng ngoại lệ bị cấm!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
	
		return result > 0 ? true : false;
	}

	
}
