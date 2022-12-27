package music;

import java.util.Date;

public class Constant {
	
	/**
	 * Giới tính: Khác
	 */

	public static final int SEX_DEFAULT = 0;
	/**
	 * giới tính: Nam
	 */
	public static final int SEX_MALE = 1;
	/**
	 * Giới tính : Nữ
	 */
	
	public static final int SEX_FEMALE = 2;
	/**
	 * Trạng thái tài khoản: Bình thường
	 */
	

	public static final int USER_STATUS_NORMAL = 0;

	/**
	* Trạng thái tài khoản: Bị cấm
	*/
	
	public static final int USER_STATUS_BAN = 1;
	/**
	* ngày mặc định
	*/
	
	public static final Date DEFAULT_DATE = new Date();
	/**
	* Người dùng chưa đăng nhập
	*/
	
	public static final int LOGIN_FAILURE = 0;

	/**
	* Người dùng đã đăng nhập thành công
	*/
	
	public static final int LOGIN_SUCCESS = 1;

	/**
	* Tên avatar mặc định
	*/
	
	public static final String DEFAULT_AVATAR = "default.jpg";

	/**
	* Đường dẫn đầy đủ hình đại diện mặc định
	*/
	
	public static final String DEFAULT_AVATAR_PATH = "img/avatar/" + DEFAULT_AVATAR;
	/**
	* Đường dẫn hình đại diện
	*/
	
	public static final String DEFAULT_AVATAR_SECPATH = "img/avatar/";

	/**
	* Album và bài hát mặc định
	*/

	public static final String DEFAULT_ALBUM = "default.jpg";

	/**
	* Hình ảnh ca sĩ mặc định
	*/
	
	public static final String DEFAULT_SINGER = "default.jpg";

	/**
	* Con đường hình ảnh ca sĩ
	*/
	
	
	public static final String SINGER_PATH = "img/singer/";

	/**
	* Đường dẫn ảnh album
	*/
	
	public static final String ALBUM_PATH = "img/";

	/**
	* Con đường âm nhạc
	*/

	public static final String MUSIC_PATH = "audio/";
}
/**
 Sử dụng Chuỗi tĩnh cuối cùng công khai khi bạn muốn tạo một Chuỗi: thuộc về lớp 
 ( static : không cần phiên bản nào để sử dụng nó), đó. sẽ không thay đổi ( final ), 
 chẳng hạn như khi bạn muốn định nghĩa một hằng Chuỗi sẽ có sẵn cho tất cả các phiên bản 
 của lớp và cho các đối tượng khác sử dụng lớp và ..*/
