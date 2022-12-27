package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import music.dao.NormalUserSingerDao;
import music.util.JdbcUtil;
import music.vo.NormalUserSinger;
import music.vo.Singer;

public class NormalUserSingerDaoImpl implements NormalUserSingerDao {

	/**
	* Lưu đối tượng NormalUserSinger vào cơ sở dữ liệu
	* @param normalUserSinger
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(NormalUserSinger normalUserSinger) {

		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normalusersinger(userId, singerId) VALUES(?,?)";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		int singerId = normalUserSinger.getSingerId();
		
		params.add(userId);
		params.add(singerId);
		
		System.out.println("Bài hát yêu thích để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Lặp lại bài hát yêu thích kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Bộ sưu tập các bài hát bất thường!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Bài hát yêu thích thành công");
		return result;
	}

	/**
	* Xác định xem bản ghi này đã tồn tại chưa
	* @param normalUserSinger
	* @return trả về true nếu tồn tại, ngược lại trả về false
	*/
	@Override
	public boolean isfollow(NormalUserSinger normalUserSinger) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM normalusersinger WHERE userId=? AND singerId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		int singerId = normalUserSinger.getSingerId();
		
		params.add(userId);
		params.add(singerId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// hồ sơ tồn tại
				System.out.println("Yêu thích tồn tại!!");
				System.out.println(queryResultList);
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn bài hát yêu thích ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		return result;
	}

	/**
	* Xóa đối tượng NormalUserSinger vào cơ sở dữ liệu
	* @param normalUserSinger
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	@Override
	public boolean delete(NormalUserSinger normalUserSinger) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "DELETE FROM normalusersinger WHERE userId=? AND singerId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		int singerId = normalUserSinger.getSingerId();
		
		params.add(userId);
		params.add(singerId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Bài hát ngoại lệ không yêu thích!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Hủy bài hát yêu thích thành công!");
		return result;
	}

	/**
	* Theo ID người dùng, trả về tất cả thông tin bài hát của người dùng hiện tại
	* @param normalUserSinger đóng gói ID người dùng trong đối tượng này
	 * @return
	 */
	@Override
	public List<NormalUserSinger> findAllSinger(NormalUserSinger normalUserSinger) {
		List<NormalUserSinger> result = new ArrayList<NormalUserSinger>();
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM singer, normalusersinger "
				+ "WHERE normalusersinger.singerId=singer.singerId AND normalusersinger.userId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSinger.getUserId();
		
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// hồ sơ tồn tại
				for (Map<String, Object> map : queryResultList) {
					int singerId = (int) map.get("singerId");
					String singerName = (String) map.get("singerName");
					int singerSex = (int) map.get("singerSex");
					String singerThumbnail = (String) map.get("singerThumbnail");
					String singerIntroduction = (String) map.get("singerIntroduction");
					
					
					Singer singer = new Singer();
					singer.setSingerId(singerId);
					singer.setSingerName(singerName);
					singer.setSingerSex(singerSex);
					singer.setSingerThumbnail(singerThumbnail);
					singer.setSingerIntroduction(singerIntroduction);
					
					NormalUserSinger nus = new NormalUserSinger();
					nus.setSinger(singer);
					nus.setSingerId(singerId);
					nus.setUserId(userId);
					result.add(nus);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả bài hát ngoại lệ yêu thích!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		return result;
	}


}
