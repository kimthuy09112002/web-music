package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.RecommendDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.Recommend;
import music.vo.Singer;
import music.vo.Song;

public class RecommendDaoImpl implements RecommendDao {

	@Override
	public boolean add(Song song) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO recommend(songId) VALUES(?)";
		JdbcUtil jdbc = null;
		
		
		int songId = song.getSongId();
		
		params.add(songId);
		
		System.out.println("Các bài hát được đề xuất để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Lặp lại bài hát được đề xuất kết thúc");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Các bài hát được đề xuất là bất thường!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Bài hát được đề xuất thành công!");
		return result;
	}

	@Override
	public boolean remove(Recommend recmd) {
		
		boolean result = false;
		
		String sql = "DELETE FROM recommend WHERE recmdId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(recmd.getRecmdId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Xóa ngoại lệ bài hát được đề xuất!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}

	@Override
	public List<Recommend> findAll() {
		
		// Lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT recmdId, "
				+ "song.songId, song.singerId, songTitle, songPlaytimes, songDldtimes, "
				+ "album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM recommend, song, album, singer "
				+ "WHERE song.songId=recommend.songId AND song.albumId=album.albumId AND song.singerId=singer.singerId";
		
		// Lưu trữ tất cả các đối tượng ca sĩ được truy vấn
		List<Recommend> recommendList = new ArrayList<Recommend>();
		JdbcUtil jdbcUtil = null;
		try {
			jdbcUtil = new JdbcUtil();
			jdbcUtil.getConnection(); // Nhận liên kết cơ sở dữ liệu
			
			// Lấy tổng số bản ghi
			List<Map<String, Object>> queryResultList = jdbcUtil.findResult(sql, paramList);
			if (queryResultList != null){
				for (Map<String, Object> map : queryResultList) {
					
					int songId = (int) map.get("songId");
					int singerId = (int) map.get("singerId");
					int albumId = (int) map.get("albumId");
					String songTitle = (String) map.get("songTitle");
					int songPlaytimes = (int) map.get("songPlaytimes");
					int songDldtimes = (int) map.get("songDldtimes");
					
					Song song = new Song(songId, singerId, albumId, songTitle, songPlaytimes, songDldtimes);
					
					String albumTitle = (String) map.get("albumTitle");
					String albumPic = (String) map.get("albumPic");
					Date albumPubDate = (Date) map.get("albumPubDate");
					String albumPubCom = (String) map.get("albumPubCom");
					
					Album album = new Album(albumId, singerId, albumTitle, albumPic, albumPubDate, albumPubCom);
					
					String singerName = (String) map.get("singerName");
					int singerSex = (int) map.get("singerSex");
					String singerThumbnail = (String) map.get("singerThumbnail");
					String singerIntroduction = (String) map.get("singerIntroduction");
					
					Singer singer = new Singer(singerId, singerName, singerSex, singerThumbnail, singerIntroduction);
					
					int recmdId = (int) map.get("recmdId");
					
					Recommend r = new Recommend();
					r.setRecmdId(recmdId);
					r.setSongId(songId);
					r.setSong(song);
					r.setAlbum(album);
					r.setSinger(singer);
					recommendList.add(r);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các ngoại lệ dữ liệu!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); //Đảm bảo giải phóng tài nguyên
			}
		}
		return recommendList;
	}

}
