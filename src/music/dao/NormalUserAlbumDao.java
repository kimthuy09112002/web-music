package music.dao;

import java.util.List;

import music.vo.NormalUserAlbum;

public interface NormalUserAlbumDao {


	/**
	* Lưu đối tượng NormalUserAlbum vào cơ sở dữ liệu
	* @param normalUserAlbum
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	public boolean save(NormalUserAlbum normalUserAlbum);
	
	
	/**
	* Xác định xem bản ghi này đã tồn tại chưa
	* @param normalUserAlbum
	* @return trả về true nếu tồn tại, ngược lại trả về false
	*/
	public boolean isfollow(NormalUserAlbum normalUserAlbum);

	/**
	* Xóa đối tượng NormalUserAlbum vào cơ sở dữ liệu
	* @param normalUserAlbum
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	public boolean delete(NormalUserAlbum normalUserAlbum);


	/**
	* Theo ID người dùng, trả về thông tin của tất cả các album mà người dùng hiện tại theo dõi
	* @param nus Đóng gói ID người dùng trong đối tượng này
	* @trở về
	*/
	public List<NormalUserAlbum> findAllAlbum(NormalUserAlbum nus);
}
