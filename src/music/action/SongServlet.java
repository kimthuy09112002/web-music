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
import music.dao.AlbumDao;
import music.dao.NormalUserSongDao;
import music.dao.SingerDao;
import music.dao.SongDao;
import music.dao.impl.AlbumDaoImpl;
import music.dao.impl.NormalUserSongDaoImpl;
import music.dao.impl.SingerDaoImpl;
import music.dao.impl.SongDaoImpl;
import music.vo.Album;
import music.vo.NormalUser;
import music.vo.NormalUserSong;
import music.vo.Pager;
import music.vo.Singer;
import music.vo.Song;
import music.vo.SongAllInfo;

/**
 * Servlet implementation class SongServlet
 */
@WebServlet("/SongServlet")
public class SongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletConfig servletConfig;
	
	private String info = "";
	private SongDao songDao = new SongDaoImpl();
	private SingerDao singerDao = new SingerDaoImpl();
	private AlbumDao albumDao = new AlbumDaoImpl();
	private NormalUserSongDao normalUserSongDao = new NormalUserSongDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongServlet() {
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
		
		// thêm bài hát
		if (info.equals("add")){
			this.song_add(request, response);
		}
		// xóa bài hát
		if (info.equals("del")){
			this.song_del(request, response);
		}
		// chỉnh sửa bài hát
		if (info.equals("update")){
			this.song_update(request, response);
		}
		// Xem tất cả các bài hát theo số trang
		if (info.equals("find")){
			this.findByPage(request, response);
		}
		// Nhảy để phát trang bài hát hiện tại
		if (info.equals("play")){
			this.song_play(request, response);
		}
		// tải bài hát hiện tại
		if (info.equals("download")){
			this.song_download(request, response);
		}
		// bài hát yêu thích hiện tại
		if (info.equals("follow")){
			this.song_follow(request, response);
		}
		// tìm kiếm mờ cho các bài hát
		if (info.equals("search")){
			this.song_search(request, response);
		}
		// Chuyển đến trang quản lý theo dõi
		if (info.equals("followmgr")){
			this.song_followmgr(request, response);
		}
		// xóa theo dõi
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
	// thêm bài hát
	protected void song_add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//Tạo đối tượng SmartUpload mới
			SmartUpload su = new SmartUpload();
			
			//khởi tạo tải lên
			su.initialize(servletConfig, request, response);
			
			//Giới hạn độ dài tối đa của mỗi tệp tải lên là 10MB
			su.setMaxFileSize(10*1024*1024);
			
			//Giới hạn độ dài của tổng dữ liệu đã tải lên
//			su.setTotalMaxFileSize(200000);
			
			// Đặt các tệp được phép tải lên (bị giới hạn bởi tiện ích mở rộng), chỉ cho phép các tệp jpg, png
//			su.setAllowedFilesList("jpg,png");
			
			//Cài đặt cấm upload file (hạn chế bởi extension), cấm upload file có đuôi exe, bat, jsp, htm, html và file không có đuôi
			su.setDeniedFilesList("exe,bat,jsp,htm,html");
			
			//tải tập tin lên
			su.upload();
			
			//Nhận hoạt động tập tin tải lên
			Files files = su.getFiles();
			
			//lấy một tập tin duy nhất
			File singleFile = files.getFile(0);
			
			// Nhận phần mở rộng của tệp đã tải lên
			String fileType = singleFile.getFileExt();
			
			//Đặt phần mở rộng của tệp đã tải lên
			String[] type = {"MP3","mp3"};
			
			// Xác định xem loại tệp đã tải lên có đúng không
			int place = java.util.Arrays.binarySearch(type, fileType);
			
			//Xác định xem phần mở rộng tệp có đúng không
			if (place != -1){
				//Xác định xem tệp có được chọn không
				if (!singleFile.isMissing()){
					
					
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.MUSIC_PATH + fileName + "." + singleFile.getFileExt();
					
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("Tải lên: " + filedir);
					
					
					String songTitle = su.getRequest().getParameter("songTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					int albumId = Integer.parseInt(su.getRequest().getParameter("albumId"));
					
					Song songData = new Song();
					songData.setSingerId(singerId);
					songData.setAlbumId(albumId);
					songData.setSongTitle(songTitle);
					songData.setSongPlaytimes(0);
					songData.setSongDldtimes(0);
					songData.setSongFile(saveFileName);
					
					SongDao songDao = new SongDaoImpl();
					if (songDao.save(songData)){
						request.setAttribute("message", "Đã thêm bài hát thành công!");
					} else {
						request.setAttribute("message", "Không thể thêm bài hát!");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Thêm bài hát ngoại lệ Servlet!", e);
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}
	// xóa bài hát
	protected void song_del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		
		Song songData = new Song();
		songData.setSongId(songId);;
		
		SongDao songDao = new SongDaoImpl();
		if (songDao.delete(songData)){
			request.setAttribute("message", "Xóa thành công!");
		} else {
			request.setAttribute("message", "Không thể xóa!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}
	// chỉnh sửa bài hát
	protected void song_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//Tạo đối tượng SmartUpload mới
			SmartUpload su = new SmartUpload();
			
			// khởi tạo tải lên
			su.initialize(servletConfig, request, response);
			
			//Giới hạn độ dài tối đa của mỗi tệp tải lên là 10MB
			su.setMaxFileSize(10*1024*1024);
			
			//Giới hạn độ dài của tổng dữ liệu đã tải lên
//			su.setTotalMaxFileSize(200000);
			
			//	Đặt các tệp được phép tải lên (bị giới hạn bởi tiện ích mở rộng), chỉ cho phép các tệp jpg, png
//			su.setAllowedFilesList("jpg,png");
			
			//Cài đặt cấm upload file (hạn chế bởi extension), cấm upload file có đuôi exe, bat, jsp, htm, html và file không có đuôi
			su.setDeniedFilesList("exe,bat,jsp,htm,html");
			
			// Tải tập tin lên
			su.upload();
			
			//lấy một tập tin duy nhất
			Files files = su.getFiles();
			
			//Nhận phần mở rộng của tệp đã tải lên
			File singleFile = files.getFile(0);
			
			// Đặt phần mở rộng của tệp đã tải lên
			String fileType = singleFile.getFileExt();
			
			// Đặt phần mở rộng của tệp đã tải lên
			String[] type = {"MP3","mp3"};
			
		// 	Xác định xem loại tệp đã tải lên có đúng không
			int place = java.util.Arrays.binarySearch(type, fileType);
			
			//Xác định xem phần mở rộng tệp có đúng không
			if (place != -1){
				
				if (!singleFile.isMissing()){
					
					
					String fileName = String.valueOf(System.currentTimeMillis());
					String filedir = Constant.MUSIC_PATH + fileName + "." + singleFile.getFileExt();
				
					String saveFileName = fileName + "." + singleFile.getFileExt();
					
					
					singleFile.saveAs(filedir, File.SAVEAS_VIRTUAL);
					System.out.println("Tải về: " + filedir);
					
					int songId = Integer.parseInt(su.getRequest().getParameter("songId"));
					String songTitle = su.getRequest().getParameter("songTitle");
					int singerId = Integer.parseInt(su.getRequest().getParameter("singerId"));
					int albumId = Integer.parseInt(su.getRequest().getParameter("albumId"));
					
					Song songData = new Song();
					songData.setSongId(songId);
					songData.setSingerId(singerId);
					songData.setAlbumId(albumId);
					songData.setSongTitle(songTitle);
					songData.setSongPlaytimes(0);
					songData.setSongDldtimes(0);
					songData.setSongFile(saveFileName);
					
					SongDao songDao = new SongDaoImpl();
					if (songDao.update(songData)){
						request.setAttribute("message","Đã chỉnh sửa bài hát thành công!");
					} else {
						request.setAttribute("message",  "Không chỉnh sửa được bài hát!");
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Sửa đổi ngoại lệ Servlet bài hát!", e);
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}
	//Xem tất cả các bài hát theo số trang
	protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNumStr = request.getParameter("pageNum"); 
		System.out.println("pageNumStr : " + pageNumStr);
		
		int pageNum = 1; //Hiển thị một vài trang dữ liệu đầu tiên
		if(pageNumStr!=null && !"".equals(pageNumStr.trim())){
			pageNum = Integer.parseInt(pageNumStr);
		}
		
		int pageSize = 10;  // Có bao nhiêu bản ghi để hiển thị trên mỗi trang
		String pageSizeStr = request.getParameter("pageSize");
		if(pageSizeStr!=null && !"".equals(pageSizeStr.trim())){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		// Tập hợp các điều kiện truy vấn
		Song searchModel = new Song();
		
		// Nhận kết quả truy vấn
		Pager<Song> result = songDao.findSongs(searchModel, pageNum, pageSize);
		
		List<Singer> singers = singerDao.findAllSingers();
		List<Album> albums = albumDao.findAllAlbums();
		
		// trả kết quả về trang
		request.setAttribute("result", result);
		request.setAttribute("singers", singers);
		request.setAttribute("albums", albums);
		System.out.println("result : " + result);
		request.getRequestDispatcher("admin/songmgr.jsp").forward(request, response);
	}

	// Nhảy để phát trang bài hát hiện tại
	protected void song_play(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int playId = Integer.parseInt(request.getParameter("playId"));
		Song songData = new Song();
		songData.setSongId(playId);
		
		SongAllInfo songInfo = songDao.findSongInfo(songData);
		System.out.println("Cài đặt songInfo");
		request.setAttribute("songInfo", songInfo);
		System.out.println("Chuyển hướng đến song.jsp");
		System.out.println(request.getSession().getAttribute("login_flag"));
		System.out.println(request.getSession().getAttribute("admin_login_flag"));
		request.getRequestDispatcher("song.jsp").forward(request, response);
		
	}
	// tải bài hát hiện tại
	protected void song_download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("path");
		int songId = Integer.parseInt(request.getParameter("songId"));
		Song songData = new Song();
		songData.setSongId(songId);

		// Cập nhật số lượt tải cơ sở dữ liệu
		songDao.downloadSong(songData);
		response.sendRedirect(path);
	}
	// Bài hát hiện tại yêu thích
	protected void song_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		NormalUserSong nusData = new NormalUserSong();
		nusData.setSongId(songId);
		nusData.setUserId(userId);
		
		// Xác định xem nó đã được yêu thích chưa
		if (normalUserSongDao.isfollow(nusData)){
			if (normalUserSongDao.delete(nusData)){
				request.setAttribute("message", "Hủy yêu thích thành công!");
			} else {
				request.setAttribute("message", "Hủy yêu thích không thành công!");
			}
		} else {
			if (normalUserSongDao.save(nusData)){
				request.setAttribute("message", "Bộ sưu tập thành công!");
			} else {
				request.setAttribute("message","Bộ sưu tập không thành công");
			}
		}
		
		request.setAttribute("flag", true);
		request.getRequestDispatcher("SongServlet?info=play&playId=" + songId).forward(request, response);
	}
	
	protected void song_search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String songTitle = request.getParameter("songTitle");
		
		Song songData = new Song();
		songData.setSongTitle(songTitle);
		
		List<Song> songList = songDao.findSongsByTitle(songData);
		
		request.setAttribute("songList", songList);
		request.getRequestDispatcher("search.jsp").forward(request, response);
		
	}
	// Chuyển đến trang quản lý theo dõi
	protected void song_followmgr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSong data = new NormalUserSong();
		data.setUserId(userId);
		List<NormalUserSong> list = normalUserSongDao.findAllSong(data);
		request.setAttribute("list", list);
		request.getRequestDispatcher("user/followSong.jsp").forward(request, response);
	}
	protected void remove_follow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		HttpSession session = request.getSession();
		NormalUser user = (NormalUser) session.getAttribute("user");
		int userId = user.getUserId();
		NormalUserSong nusData = new NormalUserSong();
		nusData.setUserId(userId);
		nusData.setSongId(songId);
		if (!normalUserSongDao.delete(nusData)){
			request.setAttribute("message", "Không thể xóa!");
			request.setAttribute("flag", true);
		}
		request.getRequestDispatcher("SongServlet?info=followmgr").forward(request, response);
	}
}
