package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.SongDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.Comments;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.Song;
import music.vo.SongAllInfo;

public class SongDaoImpl implements SongDao {

	/**
	* Lưu đối tượng bài hát vào cơ sở dữ liệu
	* @param song đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(Song song) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO song(singerId, albumId, songTitle, songPlaytimes, songDldtimes, songFile) VALUES(?,?,?,?,?,?)";
		JdbcUtil jdbc = null;
		
		int singerId = song.getSingerId();
		int albumId = song.getAlbumId();
		String songTitle = song.getSongTitle();
		int songPlaytimes = song.getSongPlaytimes();
		int songDldtimes = song.getSongDldtimes();
		String songFile = song.getSongFile();
		
		params.add(singerId);
		params.add(albumId);
		if (songTitle != null && !"".equals(songTitle)){
			params.add(songTitle);
		} else {
			return result;
		}
		params.add(songPlaytimes);
		params.add(songDldtimes);
		if (songFile != null && !"".equals(songFile)){
			params.add(songFile);
		} else {
			return result;
		}
		
		System.out.println("Lưu bài hát và bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Lưu lặp lại bài hát kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Lưu bài hát ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Đã lưu bài hát thành công!");
		return result;
	}
	/**
	* Xóa đối tượng bài hát
	* @param xóa bài hát theo id
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	@Override
	public boolean delete(Song song) {
		boolean result = false;
		
		String sql = "DELETE FROM song WHERE songId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(song.getSongId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Xóa bài hát ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}
	/**
	* Sửa đổi đối tượng bài hát vào cơ sở dữ liệu
	* @param song Đối tượng được sửa đổi, được xác định bởi id
	* @return trả về true nếu sửa đổi thành công, ngược lại trả về false
	*/
	@Override
	public boolean update(Song song) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE song SET singerId=?, albumId=?, songTitle=?, songFile=? WHERE songId=?";
		JdbcUtil jdbc = null;
		
		int singerId = song.getSingerId();
		int albumId = song.getAlbumId();
		String songTitle = song.getSongTitle();
		int songId = song.getSongId();
		String songFile = song.getSongFile();
		
		params.add(singerId);
		params.add(albumId);
		if (songTitle != null && !"".equals(songTitle)){
			params.add(songTitle);
		} else {
			return result;
		}
		if (songFile != null && !"".equals(songFile)){
			params.add(songFile);
		} else {
			return result;
		}
		params.add(songId);
		
		System.out.println("Sửa đổi bài hát và bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Sửa đổi kết thúc lặp bài hát!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				//Nếu sửa thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Sửa đổi bài hát ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Sửa đổi bài hát thành công!");
		return result;
	}

	/**
	* Theo điều kiện truy vấn, truy vấn thông tin phân trang bài hát
	* @param searchModel Đóng gói các điều kiện truy vấn
	* @param pageNum dữ liệu trang truy vấn
	* @param pageSize Có bao nhiêu bản ghi được hiển thị trên mỗi trang
	 * @return
	 */
	@Override
	public Pager<Song> findSongs(Song searchModel, int pageNum, int pageSize) {
		
		Pager<Song> result = null;
		// lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("select * from song where 1=1");
		StringBuilder countSql = new StringBuilder("select count(songId) as totalRecord from song where 1=1 ");

		//chỉ mục bắt đầu
		int fromIndex	= pageSize * (pageNum -1);
		
		// Sử dụng từ khóa giới hạn để triển khai phân trang
		sql.append(" limit " + fromIndex + ", " + pageSize );
		
		//Lưu trữ tất cả các đối tượng bài hát được truy vấn
		List<Song> songList = new ArrayList<Song>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // Nhận liên kết cơ sở dữ liệu
			
			// Lấy tổng số bản ghi
			List<Map<String, Object>> countResult = jdbcUtil.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			
			// Nhận bản ghi album được truy vấn
			List<Map<String, Object>> songResult = jdbcUtil.findResult(sql.toString(), paramList);
			if (songResult != null) {
				for (Map<String, Object> map : songResult) {
					Song s = new Song(map);
					songList.add(s);
				}
			}
			
			//Lấy tổng số trang
			int totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			
			// Lắp ráp đối tượng máy nhắn tin
			result = new Pager<Song>(pageSize, pageNum, totalRecord, totalPage, songList);
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các dữ liệu ngoại lệ !", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		return result;
	}

	/**
	* Truy vấn tất cả thông tin liên quan của bài hát theo ID bài hát
	* @param song ID bài hát được gói gọn trong đối tượng Bài hát
	* @return Trả về tất cả các đối tượng thông tin của bài hát
	*/
	@Override
	public SongAllInfo findSongInfo(Song song) {
		int songId = song.getSongId();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(songId);
		
		String sql = "SELECT song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile, "
				+ "album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM song, album, singer "
				+ "WHERE song.songId=? AND song.albumId=album.albumId AND song.singerId=singer.singerId";
		String sqlComments = "SELECT comments.commentId, comments.commentText, comments.commentDate, "
				+ "normaluser.userId, normaluser.userAvatar, normaluser.userNickname, "
				+ "songId "
				+ "FROM comments, normaluser "
				+ "WHERE comments.songId=? AND normaluser.userId=comments.userId";
		// Lưu trữ đối tượng SongAllInfo được truy vấn
		SongAllInfo songAllInfo = new SongAllInfo();
		
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); //Nhận liên kết cơ sở dữ liệu
			
			// Lấy tổng số bản ghi
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			List<Map<String, Object>> queryCommentsResult = jdbcUtil.findResult(sqlComments, paramList);
			if (queryResultList != null){
				Map<String, Object> map = queryResultList.get(0);
				String songTitle = (String) map.get("songTitle");
				int songPlaytimes = (int) map.get("songPlaytimes");
				int songDldtimes = (int) map.get("songDldtimes");
				String songFile = (String) map.get("songFile");
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
				
				Song resultSong = new Song(songId, singerId, albumId, songTitle, songPlaytimes, songDldtimes, songFile);
				Album resultAlbum = new Album(albumId, singerId, albumTitle, albumPic, albumPubDate, albumPubCom);
				Singer resultSinger = new Singer(singerId, singerName, singerSex, singerThumbnail, singerIntroduction);
				List<Comments> resultComments = new ArrayList<Comments>();
				if (queryCommentsResult != null){
					for (Map<String, Object> mapCmt : queryCommentsResult) {
						Comments s = new Comments(mapCmt);
						resultComments.add(s);
					}
				}
				songAllInfo.setSong(resultSong);
				songAllInfo.setAlbum(resultAlbum);
				songAllInfo.setSinger(resultSinger);
				songAllInfo.setComments(resultComments);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các ngoại lệ dữ liệu!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		
		return songAllInfo;
	}

	/**
	 * Ghi lại số lượng bài hát đã tải xuống theo ID bài hát
	 * @param song
	 */
	@Override
	public void downloadSong(Song song) {
		int songId = song.getSongId();
		
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE song SET songDldtimes=songDldtimes+1 WHERE songId=?";
		JdbcUtil jdbc = null;
		
		params.add(songId);
		
		System.out.println("Tải xuống các bài hát để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Kết thúc lặp lại bài hát tải xuống!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			jdbc.updateByPreparedStatement(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException("Tải bài hát bất thường!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Đã tải xuống bài hát thành công!");
	}

	
	/**
	 * Quay lại top 10 lượt tải bài hát
	 * @return
	 */
	@Override
	public List<Song> downloadRank() {
		
		// lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT songId, songTitle, songDldtimes FROM song ORDER BY songDldtimes DESC LIMIT 0,10";

		List<Song> songList = new ArrayList<Song>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // Nhận liên kết cơ sở dữ liệu
			// Nhận bản ghi album được truy vấn
			List<Map<String, Object>> songResult = jdbcUtil.findResult(sql, paramList);
			if (songResult != null) {
				for (Map<String, Object> map : songResult) {
					Song s = new Song();
					s.setSongId((int) map.get("songId"));
					s.setSongTitle((String) map.get("songTitle"));
					s.setSongDldtimes((int) map.get("songDldtimes"));
					
					songList.add(s);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Bảng xếp hạng tải xuống truy vấn là bất thường!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		return songList;
	}

	/**
	 * Truy vấn mờ tất cả các đối tượng Bài hát theo songTitle
	 * @param song
	 * @return
	 */
	@Override
	public List<Song> findSongsByTitle(Song song) {
		
		String songLikeTitle = "%" + song.getSongTitle() + "%";
		
		// lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM song, singer "
				+ "WHERE song.songTitle LIKE ? AND song.singerId=singer.singerId";

		paramList.add(songLikeTitle);
		List<Song> songList = new ArrayList<Song>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // Nhận liên kết cơ sở dữ liệu
			// Nhận bản ghi album được truy vấn
			List<Map<String, Object>> songResult = jdbcUtil.findResult(sql, paramList);
			if (songResult != null) {
				for (Map<String, Object> map : songResult) {
					Song s = new Song();
					s.setSongId((int) map.get("songId"));
					s.setSongTitle((String) map.get("songTitle"));
					s.setSongPlaytimes((int) map.get("songPlaytimes"));
					s.setSongDldtimes((int) map.get("songDldtimes"));
					s.setSongFile((String) map.get("songFile"));
					
					Singer singer = new Singer();
					singer.setSingerId((int) map.get("singerId"));
					singer.setSingerName((String) map.get("singerName"));
					singer.setSingerSex((int) map.get("singerSex"));
					singer.setSingerThumbnail((String) map.get("singerThumbnail"));
					singer.setSingerIntroduction((String) map.get("singerIntroduction"));
					
					s.setSinger(singer);
					songList.add(s);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Bảng xếp hạng tải xuống truy vấn là bất thường!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		return songList;
	}

	
}
