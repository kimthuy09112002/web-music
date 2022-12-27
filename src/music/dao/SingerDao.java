package music.dao;

import java.util.List;

import music.vo.Pager;
import music.vo.Singer;
import music.vo.SingerAllInfo;

public interface SingerDao {

	/**
	* Lưu đối tượng ca sĩ vào cơ sở dữ liệu
	* @param ca sĩ đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	public boolean save(Singer singer);
	
	/**
	* Xóa đối tượng ca sĩ
	* @param singer xóa theo id
	* @return trả về true nếu xóa thành công, ngược lại trả về false
	*/
	public boolean delete(Singer singer);
	
	/**
	* Sửa đổi đối tượng ca sĩ vào cơ sở dữ liệu
	* @param Singer Đối tượng được sửa đổi, được xác định bởi id
	* @return trả về true nếu sửa đổi thành công, ngược lại trả về false
	*/
	public boolean update(Singer singer);
	/**
	* Theo điều kiện truy vấn, truy vấn thông tin trang ca sĩ
	* @param searchModel Đóng gói các điều kiện truy vấn
	* @param pageNum dữ liệu trang truy vấn
	* @param pageSize Có bao nhiêu bản ghi được hiển thị trên mỗi trang
	* @return
	*/
	public Pager<Singer> findSingers(Singer searchModel, int pageNum, int pageSize);
	/**
	* Theo điều kiện truy vấn, truy vấn tất cả thông tin ca sĩ
	* @param searchModel Đóng gói các điều kiện truy vấn
	* @return danh sách các đối tượng Ca sĩ
	*/
	public List<Singer> findAllSingers();
	/**
	* Truy vấn tất cả các thông tin liên quan của ca sĩ
	* ID ca sĩ @param được gói gọn trong đối tượng Ca sĩ
	* @return
	*/
	public SingerAllInfo findSingerInfo(Singer singer);
}
