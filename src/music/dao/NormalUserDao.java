package music.dao;

import music.vo.NormalUser;

public interface NormalUserDao {


	/**
    * người dùng đã đăng ký
	* @param normalUser Thông tin người dùng cần đăng ký được gói gọn trong đối tượng NormalUser
	* @return true nếu đăng ký thành công, ngược lại là false
	*/
	public boolean singup(NormalUser normalUser);
	
	/**
	* Người dùng đăng nhập
	* @param normalUser đóng gói thông tin đăng nhập, bao gồm tên người dùng và mật khẩu
	* @return Đối tượng NormalUser được trả về nếu đăng nhập thành công và trả về null nếu đăng nhập không thành công
	*/
	public NormalUser login(NormalUser normalUser);
	/**
	 *  Sửa đổi cài đặt cơ bản của người dùng theo normalUser
	 * @param normalUser
	 * @return
	 */
	public boolean setting(NormalUser normalUser);
	/**
	 * Lưu hình đại diện người dùng
	 * @param normalUser
	 * @return
	 */
	public boolean save_avatar(NormalUser normalUser);
	/**
	 * Sửa đổi mật khẩu người dùng theo userId
	 * @param normalUser
	 * @return
	 */
	public boolean save_psw(NormalUser normalUser);
	/**
	* Cấm người dùng dựa trên ID người dùng
	* @param normalUser đóng gói ID người dùng vào đối tượng
	 * @return
	 */
	public boolean ban(NormalUser normalUser);
}
