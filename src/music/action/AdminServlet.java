package music.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import music.Constant;
import music.dao.AdminDao;
import music.dao.impl.AdminDaoImpl;
import music.vo.Admin;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String info = "";
       
  
    public AdminServlet() {
        super(); //Sử dụng super() để gọi trực tiếp constructor của lớp cha
    }

/**   SerialVersionUID là một thuộc tính định danh dùng trong serialize/deserialize một object 
 * của một class implement Serializable interface.Admin servlet sử dụng thuộc tính serialVersionUID
 * để định danh và kiểm tra sự tương thích với các object được tuần tự hoá.
*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Thực hiện các hoạt động khác nhau theo các giá trị khác nhau của tham số thông tin
		info = request.getParameter("info");
		// Đăng nhập
		if (info.equals("login")){
			this.admin_login(request, response);
		}
		// đăng xuất
		if (info.equals("logout")){
			this.admin_logout(request, response);
		}
		// thêm quản trị viên
		if (info.equals("add")){
			this.admin_add(request, response);
		}
		//đổi mật khẩu
		if (info.equals("psw")){
			this.admin_modifyPsw(request, response);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
//Với một file servlet khi tạo ra,vì thường chúng ta sử dụng hai phương thức chủ yếu của http là doGet()
//và doPost() nên eclipse mặc định tạo ra hai phương thức Post và Get.
//Một số framework phổ hiện nay như Spring hay Struts,thì phương thức Get dùng để hiển thị form,nghĩa là hiển thị view.(View theo mô hình MVC) 
//Phương thức Post dùng để xử lý form,tất là khi người dùng nhấn nút submit của form thì sẽ xử lý tại phương thức Post.
	/**
	 * Thao tác đăng nhập quản trị viên
	 * @param request      yêu cầu
	 * @param response     phản ứng
	 * @throws ServletException  Xây dựng một ngoại lệ servlet mới.
	 * @throws IOException Trong java thì có 2 loại exception: check và uncheck exception.
	 *  Và IOException là loại check exception. Bắt buộc phải handle.
	 *  Khi khai báo như thế có nghĩa là trong hàm đó sử dụng các method thao tác với file. như open/close …
	 */
	public void admin_login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Lớp String đại diện cho các chuỗi ký tự. 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//Tạo  mới 1 đối tượng Admin() set cái username với password của vào Class đó
		Admin adminData = new Admin();
		adminData.setAdminUsername(username);
		adminData.setAdminPassword(password);
		//nó gọi tới thằng adminDao, sau đó nó gọi hàm login bên trong cái adminDao đó với giá trị đưa vào là adminData, 
		//sau đó gán kết quả trả về vào admin
		AdminDao adminDao = new AdminDaoImpl();
		Admin admin = adminDao.login(adminData);
		if (admin != null){
//		    request.setAttribute("admin", admin);  
//			request.setAttribute("login_flag", Constant.LOGIN_SUCCESS);
			//setAttribute(name,value): hàm đặt giá trị
			HttpSession session = request.getSession();
			session.setAttribute("admin", admin);
			session.setAttribute("admin_login_flag", Constant.LOGIN_SUCCESS);
			//Set giá trị admin và cái LOGIN_SUCCESS kia vào key admin với admin_login_flag
			request.getRequestDispatcher("manager.jsp").forward(request, response);
			//chuyển qua trang manager.jsp
		}
	}

	/**
	 * Thao tác đăng xuất của quản trị viên
	 * @param request  yêu cầu
	 * @param response  phản ứng
	 * @throws ServletException  Xây dựng một ngoại lệ servlet mới.
	 * @throws IOException
	 */
	public void admin_logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("admin");
		session.setAttribute("admin_login_flag", Constant.LOGIN_FAILURE);
		//Set giá trị admin và cái LOGIN_SUCCESS kia vào key admin với admin_login_flag
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	/** 
	 * Quản trị viên đã thêm
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void admin_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"); // lấy giá trị của username //Lệnh gán vô username
		String password = request.getParameter("password"); // lấy giá trị của password //Là lệnh gán vô password
		Date register = new Date();
		Date lastDate = new Date();
		
		Admin adminData = new Admin();
		adminData.setAdminUsername(username); //Lấy mấy cái hàm trong adminData để set giá trị vào
		adminData.setAdminPassword(password);
		adminData.setAdminRegisterDate(register);
		adminData.setAdminLastDate(lastDate);
		
		AdminDao adminDao = new AdminDaoImpl();
		
		request.setAttribute("message", adminDao.save(adminData) ? "Thành công!" : "Thất Bại!");
		request.setAttribute("flag", true);
		//hàm này, mình chỉ thấy, nó sẽ đưa vào "message" 1 cái giá trị là thành công hay thất bại, kèm theo "flag" có giá trị là true
		//adminDao.save(adminData) ? "Thành công!" : "Thất Bại!")
		request.getRequestDispatcher("admin/newAdmin.jsp").forward(request, response); // chuyển trang khác
	}
	/**
	 * Thay đổi mật khẩu quản trị viên
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void admin_modifyPsw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		
		Admin adminData = new Admin();
		adminData.setAdminUsername(username);
		adminData.setAdminPassword(password);
		
		// Kiểm tra xem mật khẩu có đúng không
		
		AdminDao adminDao = new AdminDaoImpl();
		Admin admin = adminDao.login(adminData);
		if (admin != null){
			System.out.println("Mật khẩu chính xác!");
			admin.setAdminPassword(newPassword);
			if (adminDao.update(admin)){
				request.setAttribute("message", "Mật khẩu đã được cập nhật!");
			} else {
				request.setAttribute("message","Không đổi được mật khẩu");
			}
		} else {
			request.setAttribute("message", "Không đổi được mật khẩu");
			System.out.println("Sai mật khẩu!");
		}
		System.out.println("Kết thúc!");
		request.setAttribute("flag", true); //Đặt giá trị của một thuộc tính trên phần tử được chỉ định. Nếu thuộc tính đã tồn tại, giá trị được cập nhật; 
		                                   //nếu không thì một thuộc tính mới được thêm vào với tên và giá trị đã chỉ định.
		request.getRequestDispatcher("admin/psw.jsp").forward(request, response); // đối tượng gửi yêu cầu đến admin
		
	}
}
