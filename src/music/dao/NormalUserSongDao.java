package music.dao;

import java.util.List;

import music.vo.NormalUserSong;

public interface NormalUserSongDao {

	/**
	* Lưu đối tượng NormalUserSong vào cơ sở dữ liệu
	* @param normalUserSong
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	public boolean save(NormalUserSong normalUserSong);
	
	/**
	* Xác định xem bản ghi này đã tồn tại chưa
	* @param normalUserSong
	* @return trả về true nếu tồn tại, ngược lại trả về false
	*/
	public boolean isfollow(NormalUserSong normalUserSong);
	/**
	* Xóa đối tượng NormalUserSong vào cơ sở dữ liệu
	* @param normalUserSong
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	public boolean delete(NormalUserSong normalUserSong);
	/**
	* Theo ID người dùng, trả về tất cả thông tin bài hát của người dùng hiện tại
	* @param normalUserSong đóng gói ID người dùng trong đối tượng này
	 * @return
	 */
	public List<NormalUserSong> findAllSong(NormalUserSong normalUserSong);
	
}
