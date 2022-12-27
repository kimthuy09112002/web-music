package music.dao;


import music.vo.Admin;

/**
 * Quản trị DAO
 * @tác giả 
 *
 */
public interface AdminDao {

	/**
	* Lưu đối tượng quản trị vào cơ sở dữ liệu
	* @param quản trị đối tượng cần lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	public boolean save(Admin admin);
	
	
	/**
	* Sửa đổi đối tượng quản trị cơ sở dữ liệu
	* @param quản trị đối tượng cần sửa đổi
	*/
	public boolean update(Admin admin);
	
	/**
	* Đăng nhập quản trị viên
	* quản trị viên @param
	* @return Nếu đăng nhập thành công thì trả về đối tượng quản trị viên, ngược lại trả về null
	*/
	public Admin login(Admin admin);
}
