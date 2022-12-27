package music.dao;

import java.util.List;

import music.vo.Album;
import music.vo.AlbumAllInfo;
import music.vo.Pager;

public interface AlbumDao {

	
	/**
	* Lưu đối tượng album vào cơ sở dữ liệu
	* @param album đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	public boolean save(Album album);
	
	/**
	* Xóa đối tượng album
	* Xóa album @param theo id
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	public boolean delete(Album album);
	

	/**
	* Sửa đổi đối tượng album thành cơ sở dữ liệu
	* @param album Đối tượng được sửa đổi, được xác định bởi id
	* @return trả về true nếu sửa đổi thành công, ngược lại trả về false
	*/
	public boolean update(Album album);
	/**
	* Theo các điều kiện truy vấn, truy vấn thông tin phân trang album
	* @param searchModel Đóng gói các điều kiện truy vấn
	* @param pageNum dữ liệu trang truy vấn
	* @param pageSize Có bao nhiêu bản ghi được hiển thị trên mỗi trang
	* @trở về
	*/
	public Pager<Album> findAlbums(Album searchModel, int pageNum, int pageSize);

	/**
	* Theo điều kiện truy vấn, truy vấn tất cả thông tin album
	* @param searchModel Đóng gói các điều kiện truy vấn
	* Danh sách đối tượng @return Album
	*/
	public List<Album> findAllAlbums();
	/**
	* Truy vấn tất cả thông tin liên quan của bài hát theo ID bài hát
	* @param song ID bài hát được gói gọn trong đối tượng Bài hát
	* @return Trả về tất cả các đối tượng thông tin của bài hát
	*/
	public AlbumAllInfo findAlbumInfo(Album album);
}
