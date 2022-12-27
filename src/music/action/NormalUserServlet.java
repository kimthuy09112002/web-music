package music.action;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;

import music.Constant;
import music.dao.NormalUserDao;
import music.dao.impl.NormalUserDaoImpl;
import music.vo.NormalUser;

/**
 * Servlet implementation class NormalUserServlet
 */
@WebServlet("/NormalUserServlet")
public class NormalUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ServletConfig servletConfig;
	private String info = "";
	private NormalUserDao normalUserDao = new NormalUserDaoImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NormalUserServlet() {
        super();
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.servletConfig = config;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		info = request.getParameter("info");
		
		// Người dùng thông thường đăng nhập
		if (info.equals("login")){
			this.user_login(request, response);
		}
		// Đăng ký người dùng thông thường
		if (info.equals("signup")){
			this.user_signup(request, response);
		}
		// Đăng xuất người dùng bình thường
		if (info.equals("logout")){
			this.user_logout(request, response);
		}
		// Quản lý người dùng chung
		if (info.equals("manager")){
			this.user_manager(request, response);
		}
		// Cài đặt người dùng cơ bản
		if (info.equals("set")){
			this.user_set(request, response);
		}
		// Sửa ảnh đại diện
		if (info.equals("avatar")){
			this.user_avatar(request, response);
		}
		// Đổi mật khẩu
		if (info.equals("psw")){
			this.user_psw(request, response);
		}
		if (info.equals("ban")){
			this.user_ban(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// Người dùng thông thường đăng nhập
	protected void user_login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		
		NormalUser userData = new NormalUser();
		userData.setUserName(userName);
		userData.setUserPassword(userPassword);
		
		NormalUser user = normalUserDao.login(userData);
		if (user != null){
			if (user.getUserStatus() == 0){
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("login_flag", Constant.LOGIN_SUCCESS);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "Thành viên này đã bị cấm!");
				request.setAttribute("flag", true);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
		} else {
			request.setAttribute("message", "Tên người dùng hoặc mật khẩu nhập sai!");
			request.setAttribute("flag", true);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	// Đăng ký người dùng thông thường
	protected void user_signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		String userEmail = request.getParameter("userEmail");
		
		NormalUser user = new NormalUser();
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setUserNickname(userName);
		user.setUserSex(Constant.SEX_DEFAULT);
		user.setUserEmail(userEmail);
		user.setUserAvatar(Constant.DEFAULT_AVATAR);
		user.setUserRegisterDate(Constant.DEFAULT_DATE);
		user.setUserLastDate(Constant.DEFAULT_DATE);
		user.setUserStatus(Constant.USER_STATUS_NORMAL);
		
		request.setAttribute("message", normalUserDao.singup(user) ? "Đăng ký thành công!" : "Đăng ký thất bại!");
		request.setAttribute("flag", true);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
	// đăng xuất người dùng bình thường
	protected void user_logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.setAttribute("login_flag", Constant.LOGIN_FAILURE);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	// Quản lý người dùng chung
	protected void user_manager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}
	// Cài đặt người dùng cơ bản
	protected void user_set(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNickname = request.getParameter("userNickname");
		int userSex = Integer.parseInt(request.getParameter("sex"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		NormalUser userData = new NormalUser();
		userData.setUserId(userId);
		userData.setUserNickname(userNickname);
		userData.setUserSex(userSex);
		
		if (normalUserDao.setting(userData)){
			request.setAttribute("message","Sửa đổi thành công!");
		} else {
			request.setAttribute("message", "Không thể chỉnh sửa!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("user/setting.jsp").forward(request, response);
	}
	protected void user_avatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Tạo đối tượng SmartUpload mới
			SmartUpload su = new SmartUpload();
			
			// khởi tạo tải lên
			su.initialize(servletConfig, request, response);
			
			// Giới hạn độ dài tối đa của mỗi tệp được tải lên
			su.setMaxFileSize(1000000);
			
			// Giới hạn độ dài của tổng dữ liệu đã tải lên
//			su.setTotalMaxFileSize(200000);
			
			// Đặt các tệp được phép tải lên (bị giới hạn bởi tiện ích mở rộng), chỉ cho phép các tệp jpg, png
//			su.setAllowedFilesList("jpg,png");
			
			// Cài đặt cấm upload file (hạn chế bởi extension), cấm upload file có đuôi exe, bat, jsp, htm, html và file không có đuôi
			su.setDeniedFilesList("exe,bat,jsp,htm,html");
			
			// tải tập tin lên
			su.upload();
			
			// Nhận hoạt động tập tin tải lên
			Files files = su.getFiles();
			
			// lấy một tập tin duy nhất
			File singleFile = files.getFile(0);
			
			// Nhận phần mở rộng của tệp đã tải lên
			String fileType = singleFile.getFileExt();
			
			// Đặt phần mở rộng của tệp đã tải lên
			String[] type = {"JPG","jpg"};
			
			//Xác định xem loại tệp đã tải lên có đúng không
			int place = java.util.Arrays.binarySearch(type, fileType);
			
			//Xác định xem phần mở rộng tệp có đúng không
			if (place != -1){
				//Xác định xem tệp có được chọn không
				if (!singleFile.isMissing()){
					
					
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.DEFAULT_AVATAR_SECPATH + fileName + "." + singleFile.getFileExt();
					
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//Thực hiện thao tác tải lên
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("Tải lên: " + filedir);
					
					
					int userId = Integer.parseInt(su.getRequest().getParameter("userId"));
					
					NormalUser userData = new NormalUser();
					userData.setUserId(userId);
					userData.setUserAvatar(saveFileName);
					
					if (normalUserDao.save_avatar(userData)){
						request.setAttribute("message", "Sửa ảnh đại diện thành công!");
					} else {
						request.setAttribute("message", "Không thể sửa đổi hình đại diện!");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Sửa đổi ngoại lệ Servlet hình đại diện!", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("user/avatar.jsp").forward(request, response);
	}
	protected void user_psw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newPassword = request.getParameter("newPassword");
		String password = request.getParameter("password");
		int userId = Integer.parseInt(request.getParameter("userId"));
		String userName = request.getParameter("userName");
		
		NormalUser userLoginData = new NormalUser();
		userLoginData.setUserPassword(password);
		userLoginData.setUserName(userName);
		
		NormalUser user = normalUserDao.login(userLoginData);
		if (user != null){
			// Mật khẩu ban đầu là chính xác
			NormalUser userData = new NormalUser();
			userData.setUserId(userId);
			userData.setUserPassword(newPassword);
			if (normalUserDao.save_psw(userData)){
				request.setAttribute("message", "Mật khẩu đã được cập nhật!");
			} else {
				request.setAttribute("message", "Không đổi được mật khẩu!");
			}
		} else {
			request.setAttribute("message", "Mật khẩu ban đầu là sai!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("user/psw.jsp").forward(request, response);
	}
	protected void user_ban(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		NormalUser userData = new NormalUser();
		userData.setUserId(userId);
		if (normalUserDao.ban(userData)){
			request.setAttribute("message", "Cấm thành công!");
		} else {
			request.setAttribute("message", "Lệnh cấm không thành công!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/ban.jsp").forward(request, response);
	}
}
