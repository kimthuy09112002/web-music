package music.dao;


import java.util.List;

import music.vo.Pager;
import music.vo.Song;
import music.vo.SongAllInfo;

public interface SongDao {

	/**
	* Lưu đối tượng bài hát vào cơ sở dữ liệu
	* @param song đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	public boolean save(Song song);
	
	/**
	* Xóa đối tượng bài hát
	* @param xóa bài hát theo id
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	public boolean delete(Song song);
	
	/**
	* Sửa đổi đối tượng bài hát vào cơ sở dữ liệu
	* @param song Đối tượng được sửa đổi, được xác định bởi id
	* @return trả về true nếu sửa đổi thành công, ngược lại trả về false
	*/
	public boolean update(Song song);
	/**
	* Theo điều kiện truy vấn, truy vấn thông tin phân trang bài hát
	* @param searchModel Đóng gói các điều kiện truy vấn
	* @param pageNum dữ liệu trang truy vấn
	* @param pageSize Có bao nhiêu bản ghi được hiển thị trên mỗi trang
	 * @return
	 */
	public Pager<Song> findSongs(Song searchModel, int pageNum, int pageSize);
	/**
	* Truy vấn tất cả thông tin liên quan của bài hát theo ID bài hát
	* @param song ID bài hát được gói gọn trong đối tượng Bài hát
	* @return Trả về tất cả các đối tượng thông tin của bài hát
	*/
	public SongAllInfo findSongInfo(Song song);
	/**
	 * Ghi lại số lượng bài hát đã tải xuống theo ID bài hát
	 * @param song
	 */
	public void downloadSong(Song song);
	/**
	 * Quay lại top 10 lượt tải bài hát
	 * @return
	 */
	public List<Song> downloadRank();
	/**
	 * Truy vấn mờ tất cả các đối tượng Bài hát theo songTitle
	 * @param song
	 * @return
	 */
	public List<Song> findSongsByTitle(Song song);
}
