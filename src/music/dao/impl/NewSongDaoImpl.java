package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import music.dao.NewSongDao;
import music.util.JdbcUtil;
import music.vo.Album;
import music.vo.NewSong;
import music.vo.Singer;
import music.vo.Song;

public class NewSongDaoImpl implements NewSongDao {

	@Override
	public boolean add(Song song) {
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO newsong (songId) VALUES (?)";
		JdbcUtil jdbc = null;
		
		
		int songId = song.getSongId();
		
		params.add(songId);
		
		System.out.println("Các bài hát mới đã lên kệ và bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("Vòng lặp phát hành bài hát mới kết thúc!");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Phát hành bài hát mới bất thường!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Bài hát mới được phát hành thành công!");
		return result;
	}

	@Override
	public boolean remove(NewSong newSong) {
		boolean result = false;
		
		String sql = "DELETE FROM newsong WHERE newSongId=?";
		List<Object> params = new ArrayList<Object>();
		JdbcUtil jdbc = null;
		
		params.add(newSong.getNewSongId());
		
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Xóa bài hát ngoại lệ được đề xuất!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		
		return result;
	}

	@Override
	public List<NewSong> findAll() {
		//lưu trữ tham số truy vấn
		List<Object> paramList = new ArrayList<Object>();
		
		String sql = "SELECT newSongId, "
				+ "song.songId, song.singerId, songTitle, songPlaytimes, songDldtimes, "
				+ "album.albumId, album.albumTitle, album.albumPic, album.albumPubDate, album.albumPubCom, "
				+ "singer.singerName, singer.singerSex, singer.singerThumbnail, singer.singerIntroduction "
				+ "FROM newsong, song, album, singer "
				+ "WHERE song.songId=newsong.songId AND song.albumId=album.albumId AND song.singerId=singer.singerId";
		
		// Lưu trữ tất cả các đối tượng ca sĩ được truy vấn
		List<NewSong> newSongList = new ArrayList<NewSong>();
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
					String songTitle = (String) map.get("songTitle");
					int songPlaytimes = (int) map.get("songPlaytimes");
					int songDldtimes = (int) map.get("songDldtimes");
					int albumId = (int) map.get("albumId");
					
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
					
					int newSongId = (int) map.get("newSongId");
					
					NewSong r = new NewSong();
					r.setNewSongId(newSongId);
					r.setSong(song);
					r.setAlbum(album);
					r.setSinger(singer);
					r.setSongId(songId);
					newSongList.add(r);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Truy vấn tất cả các ngoại lệ dữ liệu!", e);
		} finally {
			if (jdbcUtil != null) {
				jdbcUtil.releaseConn(); // Đảm bảo giải phóng tài nguyên
			}
		}
		return newSongList;
	}

}
