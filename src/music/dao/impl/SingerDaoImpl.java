package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.SingerDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.SingerAllInfo;
import music.vo.Song;

public class SingerDaoImpl implements SingerDao {

	/**
	* Lưu đối tượng ca sĩ vào cơ sở dữ liệu
	* @param ca sĩ đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(Singer singer) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO singer(singerName, singerSex, singerThumbnail, singerIntroduction) VALUES(?,?,?,?)";
		JdbcUtil jdbc = null;
		
		String singerName = singer.getSingerName();
		int singerSex = singer.getSingerSex();
		String singerThumbnail = singer.getSingerThumbnail();
		String singerIntroduction = singer.getSingerIntroduction();
		
		if (singerName != null && !"".equals(singerName)){
			params.add(singerName);
		} else {
			return result;
		}
		params.add(singerSex);
		if (singerThumbnail != null && !"".equals(singerThumbnail)){
			params.add(singerThumbnail);
		} else {
			return result;
		}
		if (singerIntroduction != null && !"".equals(singerIntroduction)){
			params.add(singerIntroduction);
		} else {
			return result;
		}
		System.out.println("Lưu ca sĩ để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Lưu ca sĩ lặp lại kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				//Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Lưu ca sĩ ngoại lệ !", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Lưu thành công ca sĩ!");
		return result;
	}

	/**
	* Xóa đối tượng ca sĩ
	* Ca sĩ @param xóa theo id
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	@Override
	public boolean delete(Singer singer) {
		boolean result = false;
		
		String sql = "DELETE FROM singer WHERE singerId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(singer.getSingerId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Xóa ca sĩ ngoại lệ !", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}

	/**
	* Sửa đổi đối tượng ca sĩ vào cơ sở dữ liệu
	* @param Singer Đối tượng được sửa đổi, được xác định bởi id
	* @return trả về true nếu sửa đổi thành công, ngược lại trả về false
	*/
	@Override
	public boolean update(Singer singer) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		
		String sql = "UPDATE singer SET singerName=?, singerSex=?, singerThumbnail=?, singerIntroduction=? WHERE singerId=?";
		JdbcUtil jdbc = null;
		
		int singerId = singer.getSingerId();
		String singerName = singer.getSingerName();
		int singerSex = singer.getSingerSex();
		String singerThumbnail = singer.getSingerThumbnail();
		String singerIntroduction = singer.getSingerIntroduction();
		
		if (singerName != null && !"".equals(singerName)){
			params.add(singerName);
		} else {
			return result;
		}
		params.add(singerSex);
		if (singerThumbnail != null && !"".equals(singerThumbnail)){
			params.add(singerThumbnail);
		} else {
			return result;
		}
		if (singerIntroduction != null && !"".equals(singerIntroduction)){
			params.add(singerIntroduction);
		} else {
			return result;
		}
		params.add(singerId);
		
		System.out.println("Sửa đổi ca sĩ để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Sửa đổi ca sĩ để kết thúc lặp lại!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu sửa thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Sửa đổi ca sĩ ngoại lệ!" , e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Sửa ca sĩ thành công!");
		return result;
	}

	/**
	 * Theo điều kiện truy vấn, truy vấn thông tin trang ca sĩ
	* @param searchModel Đóng gói các điều kiện truy vấn
	* @param pageNum dữ liệu trang truy vấn
	* @param pageSize Có bao nhiêu bản ghi được hiển thị trên mỗi trang
	 * @return
	 */
	@Override
	public Pager<Singer> findSingers(Singer searchModel, int pageNum, int pageSize) {
		Pager<Singer> result = null;
		// lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		StringBuilder sql = new StringBuilder("select * from singer where 1=1");
		StringBuilder countSql = new StringBuilder("select count(singerId) as totalRecord from singer where 1=1 ");

		// chỉ mục bắt đầu
		int fromIndex	= pageSize * (pageNum -1);
		
		// Sử dụng từ khóa giới hạn để triển khai phân trang
		sql.append(" limit " + fromIndex + ", " + pageSize );
		
		// Lưu trữ tất cả các đối tượng sinh viên được truy vấn
		List<Singer> singerList = new ArrayList<Singer>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // Nhận liên kết cơ sở dữ liệu
			
			// Lấy tổng số bản ghi
			List<Map<String, Object>> countResult = jdbcUtil.findResult(countSql.toString(), paramList);
			Map<String, Object> countMap = countResult.get(0);
			int totalRecord = ((Number)countMap.get("totalRecord")).intValue();
			
			//Lấy bản ghi ca sĩ truy vấn
			List<Map<String, Object>> studentResult = jdbcUtil.findResult(sql.toString(), paramList);
			if (studentResult != null) {
				for (Map<String, Object> map : studentResult) {
					Singer s = new Singer(map);
					singerList.add(s);
				}
			}
			
			//Lấy tổng số trang
			int totalPage = totalRecord / pageSize;
			if(totalRecord % pageSize !=0){
				totalPage++;
			}
			
			// Lắp ráp đối tượng máy nhắn tin
			result = new Pager<Singer>(pageSize, pageNum, 
							totalRecord, totalPage, singerList);
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các ngoại lệ dữ liệu!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		return result;
	}

	/**
	* Theo điều kiện truy vấn, truy vấn tất cả thông tin ca sĩ
	* @return danh sách các đối tượng Ca sĩ
	*/
	@Override
	public List<Singer> findAllSingers() {
		
		// lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT * FROM singer WHERE 1=1";
		
		// Lưu trữ tất cả các đối tượng ca sĩ được truy vấn
		List<Singer> singerList = new ArrayList<Singer>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); //Nhận liên kết cơ sở dữ liệu
			
			//Lấy tổng số bản ghi
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			if (queryResultList != null){
				for (Map<String, Object> map : queryResultList) {
					Singer s = new Singer(map);
					singerList.add(s);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các ngoại lệ dữ liệu!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); //Đảm bảo giải phóng tài nguyên
			}
		}
		return singerList;
	}

	/**
	* Truy vấn tất cả các thông tin liên quan của ca sĩ
	* ID ca sĩ @param được gói gọn trong đối tượng Ca sĩ
	 * @return
	 */
	@Override
	public SingerAllInfo findSingerInfo(Singer singer) {
		
		int singerId = singer.getSingerId();
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(singerId);
		
		String sqlSongs = "SELECT singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction, "
				+ "song.songId, song.songTitle, song.songPlaytimes, song.songDldtimes,song.songFile "
				+ "FROM singer, song "
				+ "WHERE singer.singerId=song.singerId AND singer.singerId=?";
		String sqlAlbums = "SELECT album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom "
				+ "FROM singer, album "
				+ "WHERE singer.singerId=album.singerId AND singer.singerId=?";
		
		
		SingerAllInfo singerAllInfo = new SingerAllInfo();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // Nhận liên kết cơ sở dữ liệu
			
			// Lấy tổng số bản ghi
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sqlSongs, paramList);
			List<Map<String, Object>> queryAlbumsResult = jdbcUtil.findResult(sqlAlbums, paramList);
			
			List<Song> resultSongs = new ArrayList<Song>();
			List<Album> resultAlbums = new ArrayList<Album>();
			if (queryResultList != null && !queryResultList.isEmpty()){
				Map<String, Object> map = queryResultList.get(0);
				
				String singerName = (String) map.get("singerName");
				int singerSex = (int) map.get("singerSex");
				String singerThumbnail = (String) map.get("singerThumbnail");
				String singerIntroduction = (String) map.get("singerIntroduction");
				Singer sin = new Singer(singerId, singerName, singerSex, singerThumbnail, singerIntroduction);
				singerAllInfo.setSinger(sin);
				
				for (Map<String, Object> mapSong : queryResultList) {
					int songId = (int) mapSong.get("songId");
					String songTitle = (String) mapSong.get("songTitle");
					int songPlaytimes = (int) mapSong.get("songPlaytimes");
					int songDldtimes = (int) mapSong.get("songDldtimes");
					String songFile = (String) mapSong.get("songFile");
					
					Song s = new Song();
					s.setSongId(songId);
					s.setSongTitle(songTitle);
					s.setSongPlaytimes(songPlaytimes);
					s.setSongDldtimes(songDldtimes);
					s.setSongFile(songFile);
					resultSongs.add(s);
				}
			}
			if (queryAlbumsResult != null && !queryAlbumsResult.isEmpty()){
				for (Map<String, Object> mapAlbum : queryAlbumsResult) {
					
					int albumId = (int) mapAlbum.get("albumId");
					String albumTitle = (String) mapAlbum.get("albumTitle");
					String albumPic = (String) mapAlbum.get("albumPic");
					Date albumPubDate = (Date) mapAlbum.get("albumPubDate");
					String albumPubCom = (String) mapAlbum.get("albumPubCom");
					
					Album a = new Album(albumId, singerId, albumTitle, albumPic, albumPubDate, albumPubCom);
					resultAlbums.add(a);
				}
				
			}
			singerAllInfo.setSongs(resultSongs);
			singerAllInfo.setAlbums(resultAlbums);
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các ngoại lệ dữ liệu!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		
		return singerAllInfo;
	}

}
