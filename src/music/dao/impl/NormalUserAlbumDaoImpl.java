package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.NormalUserAlbumDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.NormalUserAlbum;
import music.vo.Singer;

public class NormalUserAlbumDaoImpl implements NormalUserAlbumDao {
	/**
	* Lưu đối tượng NormalUserAlbum vào cơ sở dữ liệu
	* @param normalUserAlbum
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(NormalUserAlbum normalUserAlbum) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normaluseralbum(userId, albumId) VALUES(?,?)";
		JdbcUtil jdbc = null;
		
		int userId = normalUserAlbum.getUserId();
		int albumId = normalUserAlbum.getAlbumId();
		
		params.add(userId);
		params.add(albumId);
		
		System.out.println("Bắt đầu lặp lại bài hát yêu thích !");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Lặp lại bài hát yêu thích đã kết thúc!");
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
		System.out.println("Bài hát yêu thích thành công!");
		return result;
	}
	/**
	* Xác định xem bản ghi này đã tồn tại chưa
	* @param normalUserAlbum
	* @return trả về true nếu tồn tại, ngược lại trả về false
	*/
	@Override
	public boolean isfollow(NormalUserAlbum normalUserAlbum) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM normaluseralbum WHERE userId=? AND albumId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserAlbum.getUserId();
		int albumId = normalUserAlbum.getAlbumId();
		
		params.add(userId);
		params.add(albumId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// hồ sơ tồn tại
				System.out.println("Yêu thích tồn tại");
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
	* Xóa đối tượng NormalUserAlbum vào cơ sở dữ liệu
	* @param normalUserAlbum
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	@Override
	public boolean delete(NormalUserAlbum normalUserAlbum) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "DELETE FROM normaluseralbum WHERE userId=? AND albumId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserAlbum.getUserId();
		int albumId = normalUserAlbum.getAlbumId();
		
		params.add(userId);
		params.add(albumId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Bài hát không yêu thích ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Hủy yêu thích bài hát thành công!");
		return result;
	}

	/**
	 * Theo ID người dùng, trả về thông tin của tất cả các album mà người dùng hiện tại theo dõi
	* @param nus Đóng gói ID người dùng trong đối tượng này
	 * @return
	 */
	@Override
	public List<NormalUserAlbum> findAllAlbum(NormalUserAlbum nus) {
		List<NormalUserAlbum> result = new ArrayList<NormalUserAlbum>();
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM normaluseralbum, album, singer "
				+ "WHERE normaluseralbum.userId=? AND normaluseralbum.albumId=album.albumId AND album.singerId=singer.singerId";
		JdbcUtil jdbc = null;
		
		int userId = nus.getUserId();
		
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				//hồ sơ tồn tại
				for (Map<String, Object> map : queryResultList) {
					
					
					
					int albumId = (int) map.get("albumId");
					String albumTitle = (String) map.get("albumTitle");
					String albumPic = (String) map.get("albumPic");
					Date albumPubDate = (Date) map.get("albumPubDate");
					String albumPubCom = (String) map.get("albumPubCom");
					
					int singerId = (int) map.get("singerId");
					String singerName = (String) map.get("singerName");
					int singerSex = (int) map.get("singerSex");
					String singerThumbnail = (String) map.get("singerThumbnail");
					String singerIntroduction = (String) map.get("singerIntroduction");
					
					Album album = new Album();
					album.setAlbumId(albumId);
					album.setAlbumTitle(albumTitle);
					album.setAlbumPic(albumPic);
					album.setAlbumPubDate(albumPubDate);
					album.setAlbumPubCom(albumPubCom);
					album.setSingerId(singerId);
					
					Singer singer = new Singer();
					singer.setSingerName(singerName);
					singer.setSingerId(singerId);
					singer.setSingerSex(singerSex);
					singer.setSingerThumbnail(singerThumbnail);
					singer.setSingerIntroduction(singerIntroduction);
					
					album.setSinger(singer);
					
					NormalUserAlbum n = new NormalUserAlbum();
					n.setAlbum(album);
					n.setAlbumId(albumId);
					n.setUserId(userId);
					
					result.add(n);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả ngoại lệ album yêu thích!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		return result;
	}

}
