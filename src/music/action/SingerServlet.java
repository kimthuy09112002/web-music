package music.action;

import java.io.IOException;
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
import music.dao.NormalUserSingerDao;
import music.dao.SingerDao;
import music.dao.impl.NormalUserSingerDaoImpl;
import music.dao.impl.SingerDaoImpl;
import music.vo.NormalUser;
import music.vo.NormalUserSinger;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.SingerAllInfo;

/**
 * Servlet implementation class SingerServlet
 */
@WebServlet("/SingerServlet")
public class SingerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletConfig servletConfig;
	private String info = "";
	private SingerDao singerDao = new SingerDaoImpl();
	private NormalUserSingerDao normalUserSingerDao = new NormalUserSingerDaoImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingerServlet() {
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
		
		// thêm ca sĩ
		if (info.equals("add")){
			this.singer_add(request, response);
		}
		// xóa ca sĩ
		if (info.equals("del")){
			this.singer_del(request, response);
		}
		// sửa đổi ca sĩ
		if (info.equals("update")){
			this.singer_update(request, response);
		}
		// Xem tất cả ca sĩ theo số trang
		if (info.equals("find")){
			this.findByPage(request, response);
		}
		// Chuyển đến trang ca sĩ
		if (info.equals("play")){
			this.singer_play(request, response);
		}
		// ca sĩ yêu thích
		if (info.equals("follow")){
			this.singer_follow(request, response);
		}
		// Chuyển đến tất cả các trang ca sĩ
		if (info.equals("list")){
			this.singer_list(request, response);
		}
		// Chuyển đến trang quản lý theo dõi
		if (info.equals("followmgr")){
			this.singer_followmgr(request, response);
		}
		// hủy đăng ký
		if (info.equals("removeFollow")){
			this.remove_follow(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// thêm ca sĩ
	protected void singer_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			
			// Lấy phần mở rộng của file đã tải lên
			String fileType = singleFile.getFileExt();
			
			// Đặt phần mở rộng của tệp đã tải lên
			String[] type = {"JPG","jpg"};
			
			// Xác định xem loại tệp đã tải lên có đúng không
			int place = java.util.Arrays.binarySearch(type, fileType);
			
			// Xác định xem phần mở rộng của tập tin có đúng không
			if (place != -1){
				// Kiểm tra xem file đã được chọn chưa
				if (!singleFile.isMissing()){
					
					
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.SINGER_PATH + fileName + "." + singleFile.getFileExt();
					
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					//Thực hiện thao tác tải lên
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("Tải lên : " + filedir);
					
					
					String singerName = su.getRequest().getParameter("singerName");
					int sex = Integer.parseInt(su.getRequest().getParameter("sex"));
					String singerIntroduction = su.getRequest().getParameter("singerIntroduction");
					
					Singer singerData = new Singer();
					singerData.setSingerName(singerName);
					singerData.setSingerSex(sex);
					singerData.setSingerIntroduction(singerIntroduction);
					singerData.setSingerThumbnail(saveFileName);
					
					SingerDao singDao = new SingerDaoImpl();
					if (singDao.save(singerData)){
						request.setAttribute("message", "Đã thêm ca sĩ thành công!");
					} else {
						request.setAttribute("message","Không thể thêm ca sĩ!");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Thêm ngoại lệ Ca sĩ Servlet!", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	// xóa ca sĩ
	protected void singer_del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int singerId = Integer.parseInt(request.getParameter("singerId"));
		
		Singer singerData = new Singer();
		singerData.setSingerId(singerId);
		
		SingerDao singerDao = new SingerDaoImpl();
		if (singerDao.delete(singerData)){
			request.setAttribute("message", "Xoá thành công!");
		} else {
			request.setAttribute("message", "Không thể xóa!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	// sửa đổi ca sĩ
	protected void singer_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Tạo đối tượng SmartUpload mới
			SmartUpload su = new SmartUpload();
			
			// khởi tạo tải lên
			su.initialize(servletConfig, request, response);
			
			//Giới hạn độ dài tối đa của mỗi tệp được tải lên
			su.setMaxFileSize(1000000);
			
			//Giới hạn độ dài của tổng dữ liệu đã tải lên
//			su.setTotalMaxFileSize(200000);
			
			//Đặt các tệp được phép tải lên (bị giới hạn bởi tiện ích mở rộng), chỉ cho phép các tệp jpg, png
//			su.setAllowedFilesList("jpg,png");
			
			//Cài đặt cấm upload file (hạn chế bởi extension), cấm upload file có đuôi exe, bat, jsp, htm, html và file không có đuôi
			su.setDeniedFilesList("exe,bat,jsp,htm,html");
			
			//tải tập tin lên
			su.upload();
			
			//Nhận hoạt động tập tin tải lên
			Files files = su.getFiles();
			
			//lấy một tập tin duy nhất
			File singleFile = files.getFile(0);
			
			//Nhận phần mở rộng của tệp đã tải lên
			String fileType = singleFile.getFileExt();
			
			//	Đặt phần mở rộng của tệp đã tải lên
			String[] type = {"JPG","jpg"};
			
			//Xác định xem loại tệp đã tải lên có đúng không
			int place = java.util.Arrays.binarySearch(type, fileType);
			
			//Xác định xem phần mở rộng tệp có đúng không
			if (place != -1){
				//Xác định xem tệp có được chọn không
				if (!singleFile.isMissing()){
					
					
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.SINGER_PATH + fileName + "." + singleFile.getFileExt();
					
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					// Thực hiện thao tác tải lên
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("Tải lên:" + filedir);
					
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					String singerName = su.getRequest().getParameter("singerName");
					int sex = Integer.parseInt(su.getRequest().getParameter("sex"));
					String singerIntroduction = su.getRequest().getParameter("singerIntroduction");
					
					
					Singer singerData = new Singer();
					singerData.setSingerId(singerId);
					singerData.setSingerName(singerName);
					singerData.setSingerSex(sex);
					singerData.setSingerIntroduction(singerIntroduction);
					singerData.setSingerThumbnail(saveFileName);
					
					SingerDao singDao = new SingerDaoImpl();
					if (singDao.update(singerData)){
						request.setAttribute("message", "Sửa ca sĩ thành công!");
					} else {
						request.setAttribute("message", "Không thể sửa đổi ca sĩ!");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException( "Sửa đổi ngoại lệ ca sĩ Servlet!", e);
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	// Xem tất cả ca sĩ theo số trang
	protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageNumStr = request.getParameter("pageNum"); 
		System.out.println("pageNumStr : " + pageNumStr);
		
		int pageNum = 1; // Hiển thị một vài trang dữ liệu đầu tiên
		if(pageNumStr!=null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		int pageSize = 10;  //Có bao nhiêu bản ghi để hiển thị trên mỗi trang
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr!=null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		// Tập hợp các điều kiện truy vấn
		Singer searchModel = new Singer();
		
		// Gọi dịch vụ để nhận kết quả truy vấn
		Pager<Singer> result = singerDao.findSingers(searchModel, pageNum, pageSize);
		
		// trả kết quả về trang
		request.setAttribute("result", result);
		System.out.println("result : " + result);
//		System.out.println("Title : " + result.getDataList().get(0).getSingerName());
		request.getRequestDispatcher("admin/singermgr.jsp").forward(request, response);
	}
	protected void singer_play(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int playId = Integer.parseInt(request.getParameter("playId"));
		Singer singerData = new Singer();
		singerData.setSingerId(playId);
		
		SingerAllInfo singerAllInfo = singerDao.findSingerInfo(singerData);
		System.out.println("sex : " + singerAllInfo.getSinger().getSingerSex());
		System.out.println("name : " + singerAllInfo.getSinger().getSingerName());
		request.setAttribute("singerInfo", singerAllInfo);
		request.getRequestDispatcher("singer.jsp").forward(request, response);
	}
	protected void singer_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int singerId = Integer.parseInt(request.getParameter("singerId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		
		NormalUserSinger nusData = new NormalUserSinger();
		nusData.setSingerId(singerId);
		nusData.setUserId(userId);
		
		// Đánh giá xem nó đã được yêu thích chưa
		if (normalUserSingerDao.isfollow(nusData)){
			if (normalUserSingerDao.delete(nusData)){
				request.setAttribute("message", "Hủy yêu thích thành công!");
			} else {
				request.setAttribute("message", "Hủy yêu thích không thành công!");
			}
		} else {
			if (normalUserSingerDao.save(nusData)){
				request.setAttribute("message", "Bộ sưu tập thành công!");
			} else {
				request.setAttribute("message", "Bộ sưu tập không thành công!");
			}
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("SingerServlet?info=play&playId=" + singerId).forward(request, response);
	}
	// Chuyển đến tất cả các trang ca sĩ
	protected void singer_list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Singer> singerList = singerDao.findAllSingers();
		request.setAttribute("singerList", singerList);
		request.getRequestDispatcher("singerlist.jsp").forward(request, response);
	}
	//	Chuyển đến trang quản lý theo dõi
	protected void singer_followmgr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSinger nus = new NormalUserSinger();
		nus.setUserId(userId);
		List<NormalUserSinger> list = normalUserSingerDao.findAllSinger(nus);
		request.setAttribute("list", list);
		request.getRequestDispatcher("user/followSinger.jsp").forward(request, response);
	}
	// hủy đăng ký
	protected void remove_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int singerId = Integer.parseInt(request.getParameter("singerId"));
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSinger nusData = new NormalUserSinger();
		nusData.setUserId(userId);
		nusData.setSingerId(singerId);
		if (!normalUserSingerDao.delete(nusData)){
			request.setAttribute("message", "Không thể xóa!");
			request.setAttribute("flag", true);
		}
		request.getRequestDispatcher("SingerServlet?info=followmgr").forward(request, response);
	}
}
