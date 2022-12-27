package music.dao;

import java.util.List;

import music.vo.NormalUserSinger;

public interface NormalUserSingerDao {

	/**
	 * Lưu đối tượng NormalUserSinger vào cơ sở dữ liệu
	 * @param normalUserSinger
	 * @return trả về true nếu lưu thành công, ngược lại trả về false
	 */
	public boolean save(NormalUserSinger normalUserSinger);
	
	/**
	 * Xác định xem bản ghi này đã tồn tại chưa
	 * @param normalUserSinger
	 * @return trả về true nếu tồn tại, ngược lại trả về false
	 */
	public boolean isfollow(NormalUserSinger normalUserSinger);
	/**
	* Xóa đối tượng NormalUserSinger vào cơ sở dữ liệu
	* @param normalUserSinger
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	public boolean delete(NormalUserSinger normalUserSinger);
	/**
	 * Theo ID người dùng, trả về tất cả thông tin bài hát của người dùng hiện tại
     * @param normalUserSinger đóng gói ID người dùng trong đối tượng này
	 * @return
	 */
	public List<NormalUserSinger> findAllSinger(NormalUserSinger normalUserSinger);
}
