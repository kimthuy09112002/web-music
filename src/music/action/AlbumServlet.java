package music.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import music.dao.AlbumDao;
import music.dao.NormalUserAlbumDao;
import music.dao.SingerDao;
import music.dao.impl.AlbumDaoImpl;
import music.dao.impl.NormalUserAlbumDaoImpl;
import music.dao.impl.SingerDaoImpl;
import music.vo.Album;
import music.vo.AlbumAllInfo;
import music.vo.NormalUser;
import music.vo.NormalUserAlbum;
import music.vo.Pager;
import music.vo.Singer;

/**
 * Servlet implementation class AlbumServlet
 */
@WebServlet("/AlbumServlet")
public class AlbumServlet extends HttpServlet {
       
	private static final long serialVersionUID = -8349590988971902657L;
	
	private ServletConfig servletConfig;
	private String info = "";
	private AlbumDao albumDao = new AlbumDaoImpl();
	private SingerDao singerDao = new SingerDaoImpl();
	private NormalUserAlbumDao normalUserAlbumDao = new NormalUserAlbumDaoImpl();
	
  
    public AlbumServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		this.servletConfig = config;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		info = request.getParameter("info");
		
		// thêm album
		if (info.equals("add")){
			this.album_add(request, response);
		}
		// xóa album
		if (info.equals("del")){
			this.album_del(request, response);
		}
		// chỉnh sửa album
		if (info.equals("update")){
			this.album_update(request, response);
		}
		// Xem tất cả các album theo số trang
		if (info.equals("find")){
			this.findByPage(request, response);
		}
		//Chuyển đến trang album hiện tại
		if (info.equals("play")){
			this.album_play(request, response);
		}
		// album yêu thích
		if (info.equals("follow")){
			this.album_follow(request, response);
		}
		// Chuyển đến tất cả các trang album
		if (info.equals("list")){
			this.album_list(request, response);
		}
		// Chuyển đến trang quản lý theo dõi
		if (info.equals("followmgr")){
			this.album_followmgr(request, response);
		}
		// hủy đăng ký
		if (info.equals("removeFollow")){
			this.remove_follow(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void album_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			//Cài đặt cấm upload file (hạn chế bởi extension), cấm upload file có đuôi exe, bat, jsp, htm, html và file không có đuôi
			su.setDeniedFilesList("exe,bat,jsp,htm,html");
			
			//tải tập tin lên
			su.upload();
			
			// Nhận hoạt động tập tin tải lên
			Files files = su.getFiles();
			
			// lấy một tập tin duy nhất
			File singleFile = files.getFile(0);
			
			// Nhận phần mở rộng của tệp đã tải lên
			String fileType = singleFile.getFileExt();
			
			// Đặt phần mở rộng của tệp đã tải lên
			String[] type = {"JPG","jpg"};
			
			// Xác định xem loại tệp đã tải lên có đúng không
			int place = java.util.Arrays.binarySearch(type, fileType);
			
			// Xác định xem phần mở rộng tệp có đúng không
			if (place != -1){
				// Xác định xem tệp có được chọn không
				if (!singleFile.isMissing()){
		
					
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.ALBUM_PATH + fileName + "." + singleFile.getFileExt();
					
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					
					// Thực hiện thao tác tải lên
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("Tải lên: " + filedir);
					
					
					String albumTitle = su.getRequest().getParameter("albumTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					String albumPubCom = su.getRequest().getParameter("albumPubCom");
				
					String albumPubDateString = su.getRequest().getParameter("albumPubDate");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date albumPubDate = sdf.parse(albumPubDateString);
					
					Album albumData = new Album();
					albumData.setAlbumTitle(albumTitle);
					albumData.setSingerId(singerId);
					albumData.setAlbumPubCom(albumPubCom);
					albumData.setAlbumPubDate(albumPubDate);
					albumData.setAlbumPic(saveFileName);
					
					AlbumDao albumDao = new AlbumDaoImpl();
					
					if (albumDao.save(albumData)){
						request.setAttribute("message", "Đã thêm album thành công!");
					} else {
						request.setAttribute("message", "Không thể thêm album!");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Thêm Ngoại lệ Album Servlet", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// xóa album
	protected void album_del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		
		Album albumData = new Album();
		albumData.setAlbumId(albumId);
		
		AlbumDao albumDao = new AlbumDaoImpl();
		if (albumDao.delete(albumData)){
			request.setAttribute("message", "Đã xóa thành công!");
		} else {
			request.setAttribute("message", "Không xóa được!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// chỉnh sửa album
	protected void album_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Tạo đối tượng SmartUpload mới
			SmartUpload su = new SmartUpload();

			//khởi tạo tải lên
			su.initialize(servletConfig, request, response);
			
			// Giới hạn độ dài tối đa của mỗi tệp được tải lên
			su.setMaxFileSize(1000000);
			
			//Giới hạn độ dài của tổng dữ liệu đã tải lên
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
			
			//Đặt phần mở rộng của tệp đã tải lên
			String[] type = {"JPG","jpg"};
			
			// Xác định xem loại tệp đã tải lên có đúng không
			int place = java.util.Arrays.binarySearch(type, fileType);
			
			// Xác định xem phần mở rộng tệp có đúng không
			if (place != -1){
				// Xác định xem tệp có được chọn không
				if (!singleFile.isMissing()){
					
					//Sử dụng thời gian hệ thống làm tên của tệp đã tải lên và đặt đường dẫn tải lên đầy đủ
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.ALBUM_PATH + fileName + "." + singleFile.getFileExt();
					
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					// Thực hiện thao tác tải lên
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("Tải lên: " + filedir);
					
					int albumId = Integer.parseInt(su.getRequest().getParameter("albumId"));
					String albumTitle = su.getRequest().getParameter("albumTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					String albumPubCom = su.getRequest().getParameter("albumPubCom");
				
					String albumPubDateString = su.getRequest().getParameter("albumPubDate");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date albumPubDate = sdf.parse(albumPubDateString);
					
					Album albumData = new Album();
					albumData.setAlbumId(albumId);
					albumData.setAlbumTitle(albumTitle);
					albumData.setSingerId(singerId);
					albumData.setAlbumPubCom(albumPubCom);
					albumData.setAlbumPubDate(albumPubDate);
					albumData.setAlbumPic(saveFileName);
					
					AlbumDao albumDao = new AlbumDaoImpl();
					
					if (albumDao.update(albumData)){
						request.setAttribute("message", "Sửa đổi album thành công !");
					} else {
						request.setAttribute("message", "Không thể chỉnh sửa album !");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Sửa đổi ngoại lệ album Servlet!", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// Xem tất cả album theo số trang
	protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNumStr = request.getParameter("pageNum"); 
		System.out.println("pageNumStr : " + pageNumStr);
		
		int pageNum = 1; // Hiển thị một vài trang dữ liệu đầu tiên
		if(pageNumStr!=null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		int pageSize = 10;  // Có bao nhiêu bản ghi để hiển thị trên mỗi trang
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr!=null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		// Tập hợp các điều kiện truy vấn
		Album searchModel = new Album();
		
		// Nhận kết quả truy vấn
		Pager<Album> result = albumDao.findAlbums(searchModel, pageNum, pageSize);
		
		List<Singer> singers = singerDao.findAllSingers();
		
		
		// Trả kết quả về trang
		request.setAttribute("result", result);
		request.setAttribute("singers", singers);
		System.out.println("result : " + result);
		request.getRequestDispatcher("admin/albummgr.jsp").forward(request, response);
	}
	// Chuyển đến trang album hiện tại
	protected void album_play(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int playId = Integer.parseInt(request.getParameter("playId"));
		Album albumData = new Album();
		albumData.setAlbumId(playId);
		
		AlbumAllInfo albumAllInfo = albumDao.findAlbumInfo(albumData);
		
		System.out.println("Cài đặt albumInfo");
		request.setAttribute("albumInfo", albumAllInfo);
		System.out.println("Chuyển đến song.jsp");
		System.out.println(request.getSession().getAttribute("login_flag"));
		System.out.println(request.getSession().getAttribute("admin_login_flag"));
		request.getRequestDispatcher("album.jsp").forward(request, response);
	}
	// album yêu thích
	protected void album_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		
		NormalUserAlbum nusData = new NormalUserAlbum();
		nusData.setAlbumId(albumId);
		nusData.setUserId(userId);
		
		// Đánh giá xem nó đã được yêu thích chưa
		if (normalUserAlbumDao.isfollow(nusData)){
			if (normalUserAlbumDao.delete(nusData)){
				request.setAttribute("message", "Hủy yêu thích thành công!");
			} else {
				request.setAttribute("message", "Hủy yêu thích không thành công!");
			}
		} else {
			if (normalUserAlbumDao.save(nusData)){
				request.setAttribute("message", "Bộ sưu tập thành công!");
			} else {
				request.setAttribute("message", "Bộ sưu tập không thành công");
			}
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("AlbumServlet?info=play&playId=" + albumId).forward(request, response);
	}
	// Chuyển đến tất cả các trang album
	protected void album_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Album> albumList = albumDao.findAllAlbums();
		request.setAttribute("albumList", albumList);
		request.getRequestDispatcher("albumlist.jsp").forward(request, response);
	}
	// Chuyển đến trang quản lý theo dõi
	protected void album_followmgr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user"); //Người dùng Thông thường  có nghĩa là bất kỳ người nào được Quản trị viên chỉ định và chỉ định để sử dụng
		int userId = user.getUserId(); //chuỗi công khai Tên người dùng
		NormalUserAlbum nus = new NormalUserAlbum(); //người dùng album
		nus.setUserId(userId);
		List<NormalUserAlbum> list = normalUserAlbumDao.findAllAlbum(nus);
		request.setAttribute("list", list);
		request.getRequestDispatcher("user/followAlbum.jsp").forward(request, response);
	}
	// hủy đăng ký
	protected void remove_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserAlbum nuaData = new NormalUserAlbum();
		nuaData.setUserId(userId);
		nuaData.setAlbumId(albumId);
		if (!normalUserAlbumDao.delete(nuaData)){
			request.setAttribute("message","Không thể xóa!");
			request.setAttribute("flag", true);
		}
		request.getRequestDispatcher("AlbumServlet?info=followmgr").forward(request, response);
	}
}
