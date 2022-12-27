package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.AlbumDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.AlbumAllInfo;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.Song;

public class AlbumDaoImpl implements AlbumDao {

	/**
	* Lưu đối tượng album vào cơ sở dữ liệu
	* @param album đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(Album album) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO album(singerId, albumTitle, albumPic, albumPubDate, albumPubCom) VALUES(?,?,?,?,?)";
		JdbcUtil jdbc = null;
		
		int singerId = album.getSingerId();
		String albumTitle = album.getAlbumTitle();
		String albumPic = album.getAlbumPic();
		Date albumPubDate = album.getAlbumPubDate();
		String albumPubCom = album.getAlbumPubCom();
		
		params.add(singerId);
		if (albumTitle != null && !"".equals(albumTitle)){
			params.add(albumTitle);
		} else {
			return result;
		}
		if (albumPic != null && !"".equals(albumPic)){
			params.add(albumPic);
		} else {
			return result;
		}
		params.add(albumPubDate);
		if (albumPubCom != null && !"".equals(albumPubCom)){
			params.add(albumPubCom);
		} else {
			return result;
		}
		System.out.println("Lưu album và bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Lưu vòng lặp album kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Lưu album ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Đã lưu album thành công!");
		return result;
	}

	/**
	* Xóa đối tượng album
	* Xóa album @param theo id
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	@Override
	public boolean delete(Album album) {
		boolean result = false;
		
		String sql = "DELETE FROM album WHERE albumId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(album.getAlbumId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Xóa album ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}

	/**
	* Sửa đổi đối tượng album thành cơ sở dữ liệu
	* @param album Đối tượng được sửa đổi, được xác định bởi id
	* @return trả về true nếu sửa đổi thành công, ngược lại trả về false
	*/
	@Override
	public boolean update(Album album) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE album SET singerId=?, albumTitle=?, albumPic=?, albumPubDate=?, albumPubCom=? WHERE albumId=?";
		JdbcUtil jdbc = null;
		
		int singerId = album.getSingerId();
		String albumTitle = album.getAlbumTitle();
		String albumPic = album.getAlbumPic();
		Date albumPubDate = album.getAlbumPubDate();
		String albumPubCom = album.getAlbumPubCom();
		int albumId = album.getAlbumId();
		
		params.add(singerId);
		if (albumTitle != null && !"".equals(albumTitle)){
			params.add(albumTitle);
		} else {
			return result;
		}
		if (albumPic != null && !"".equals(albumPic)){
			params.add(albumPic);
		} else {
			return result;
		}
		params.add(albumPubDate);
		if (albumPubCom != null && !"".equals(albumPubCom)){
			params.add(albumPubCom);
		} else {
			return result;
		}
		params.add(albumId);
		
		System.out.println("Sửa đổi album để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Sửa đổi kết thúc lặp lại album!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu sửa thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Sửa đổi album ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Sửa đổi album thành công!");
		return result;
	}

	/**
	* Theo các điều kiện truy vấn, truy vấn thông tin phân trang album
	* @param searchModel Đóng gói các điều kiện truy vấn
	* @param pageNum dữ liệu trang truy vấn
	* @param pageSize Có bao nhiêu bản ghi được hiển thị trên mỗi trang
	 * @return
	 */
	@Override
	public Pager<Album> findAlbums(Album searchModel, int pageNum, int pageSize) {
		Pager<Album> result = null;
		// lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("select * from album where 1=1");
		StringBuilder countSql = new StringBuilder("select count(albumId) as totalRecord from album where 1=1 ");

		int fromIndex	= pageSize * (pageNum -1);
		
		// Sử dụng từ khóa giới hạn để triển khai phân trang
		sql.append(" limit " + fromIndex + ", " + pageSize );
		
		// Lưu trữ tất cả các đối tượng sinh viên được truy vấn
		List<Album> albumList = new ArrayList<Album>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); //Nhận liên kết cơ sở dữ liệu
			
			// Lấy tổng số bản ghi
			List<Map<String, Object>> countResult = jdbcUtil.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			
			//Nhận bản ghi album được truy vấn
			List<Map<String, Object>> albumResult = jdbcUtil.findResult(sql.toString(), paramList);
			if (albumResult != null) {
				for (Map<String, Object> map : albumResult) {
					Album s = new Album(map);
					albumList.add(s);
				}
			}
			
			//Lấy tổng số trang
			int totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			
			// Lắp ráp đối tượng máy nhắn tin
			result = new Pager<Album>(pageSize, pageNum, totalRecord, totalPage, albumList);
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các dữ liệu ngoại lệ", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		return result;
	}

	@Override
	public List<Album> findAllAlbums() {

		// lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT * FROM album WHERE 1=1";
		
		// Lưu trữ tất cả các đối tượng ca sĩ được truy vấn
		List<Album> albumList = new ArrayList<Album>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // Nhận liên kết cơ sở dữ liệu
			
			// Lấy tổng số bản ghi
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			if (queryResultList != null){
				for (Map<String, Object> map : queryResultList) {
					Album a = new Album(map);
					albumList.add(a);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các dữ liệu ngoại lệ!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		return albumList;
	}

	
	/**
	* Truy vấn tất cả thông tin liên quan của album theo ID album
	* ID album bài hát @param được gói gọn trong đối tượng Album
	* @return Trả về tất cả các đối tượng thông tin của album
	*/
	@Override
	public AlbumAllInfo findAlbumInfo(Album album) {
		
		int albumId = album.getAlbumId();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(albumId);
		
		String sql = "SELECT album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerId, singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM album, singer "
				+ "WHERE album.albumId=? AND album.singerId=singer.singerId";
		String sqlSongs = "SELECT song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes, song.songFile "
				+ "FROM album, song "
				+ "WHERE song.albumId=album.albumId AND album.albumId=?";
		
		
		AlbumAllInfo albumAllInfo = new AlbumAllInfo();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); //Nhận liên kết cơ sở dữ liệu
			
			//Lấy tổng số bản ghi
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			List<Map<String, Object>> querySongsResult = jdbcUtil.findResult(sqlSongs, paramList);
			if (queryResultList != null && !queryResultList.isEmpty()){
				Map<String, Object> map = queryResultList.get(0);
				String albumTitle = (String) map.get("albumTitle");
				String albumPic = (String) map.get("albumPic");
				Date albumPubDate = (Date) map.get("albumPubDate");
				String albumPubCom = (String) map.get("albumPubCom");
				int singerId = (int) map.get("singerId");
				String singerName = (String) map.get("singerName");
				int singerSex = (int) map.get("singerSex");
				String singerThumbnail = (String) map.get("singerThumbnail");
				String singerIntroduction = (String) map.get("singerIntroduction");
				
				
				
				
				Album resultAlbum = new Album(albumId, singerId, albumTitle, albumPic, albumPubDate, albumPubCom);
				Singer resultSinger = new Singer(singerId, singerName, singerSex, singerThumbnail, singerIntroduction);
				List<Song> resultSongs = new ArrayList<Song>();
				if (querySongsResult != null){
					for (Map<String, Object> mapSong : querySongsResult) {
						int songId = (int) mapSong.get("songId");
						String songTitle = (String) mapSong.get("songTitle");
						int songPlaytimes = (int) mapSong.get("songPlaytimes");
						int songDldtimes = (int) mapSong.get("songDldtimes");
						String songFile = (String) mapSong.get("songFile");
						
						Song s = new Song(songId, singerId, albumId, songTitle, songPlaytimes, songDldtimes, songFile);
						resultSongs.add(s);
					}
				}
				albumAllInfo.setSongs(resultSongs);;
				albumAllInfo.setAlbum(resultAlbum);
				albumAllInfo.setSinger(resultSinger);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các dữ liệu ngoại lệ!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		
		return albumAllInfo;
		
	}

	
}
