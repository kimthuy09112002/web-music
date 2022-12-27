package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import music.util.JdbcUtil;
import music.vo.NormalUserSong;
import music.vo.Singer;
import music.vo.Song;

public class NormalUserSongDaoImpl implements music.dao.NormalUserSongDao {

	/**
	* Lưu đối tượng NormalUserSong vào cơ sở dữ liệu
	* @param normalUserSong
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(NormalUserSong normalUserSong) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO normalusersong(userId, songId) VALUES(?,?)";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		int songId = normalUserSong.getSongId();
		
		params.add(userId);
		params.add(songId);
		
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
	* @param normalUserSong
	* @return trả về true nếu tồn tại, ngược lại trả về false
	*/
	@Override
	public boolean isfollow(NormalUserSong normalUserSong) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT * FROM normalusersong WHERE userId=? AND songId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		int songId = normalUserSong.getSongId();
		
		params.add(userId);
		params.add(songId);
		
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
	* Xóa đối tượng NormalUserSong vào cơ sở dữ liệu
	* @param normalUserSong đóng gói userId và songId vào đối tượng này
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	@Override
	public boolean delete(NormalUserSong normalUserSong) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "DELETE FROM normalusersong WHERE userId=? AND songId=?";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		int songId = normalUserSong.getSongId();
		
		params.add(userId);
		params.add(songId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Bài hát ngoại lệ không yêu thích", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Hủy bài hát yêu thích thành công");
		return result;
	}

	/**
	* Theo ID người dùng, trả về tất cả thông tin bài hát của người dùng hiện tại
	* @param normalUserSong đóng gói ID người dùng trong đối tượng này
	 * @return
	 */
	@Override
	public List<NormalUserSong> findAllSong(NormalUserSong normalUserSong) {
		
		List<NormalUserSong> result = new ArrayList<NormalUserSong>();
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT song.songId, song.singerId, song.albumId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile, "
				+ "singer.singerName "
				+ "FROM normalusersong, song, singer "
				+ "WHERE normalusersong.userId=? AND song.songId=normalusersong.songId AND song.singerId=singer.singerId";
		JdbcUtil jdbc = null;
		
		int userId = normalUserSong.getUserId();
		
		params.add(userId);
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			List<Map<String, Object>> queryResultList = jdbc.findResult(sql, params);
			if (queryResultList != null && !queryResultList.isEmpty()){
				// hồ sơ tồn tại
				for (Map<String, Object> map : queryResultList) {
					int songId = (int) map.get("songId");
					int singerId = (int) map.get("singerId");
					int albumId = (int) map.get("albumId");
					String songTitle = (String) map.get("songTitle");
					int songPlaytimes = (int) map.get("songPlaytimes");
					int songDldtimes = (int) map.get("songDldtimes");
					String songFile = (String) map.get("songFile");
					String singerName = (String) map.get("singerName");
					
					Song song = new Song();
					song.setSongId(songId);
					song.setSingerId(singerId);
					song.setAlbumId(albumId);
					song.setSongTitle(songTitle);
					song.setSongDldtimes(songDldtimes);
					song.setSongPlaytimes(songPlaytimes);
					song.setSongFile(songFile);
					
					Singer singer = new Singer();
					singer.setSingerName(singerName);
					song.setSinger(singer);
					
					NormalUserSong nus = new NormalUserSong();
					nus.setSong(song);
					result.add(nus);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả ngoại lệ bài hát yêu thích!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		return result;
	}
}
