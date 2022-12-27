package music.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import music.dao.NewSongDao;
import music.dao.impl.NewSongDaoImpl;
import music.vo.NewSong;
import music.vo.Song;

/**
 * Servlet implementation class NewSongServlet
 */
@WebServlet("/NewSongServlet")
public class NewSongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String info = "";
	private NewSongDao newSongDao = new NewSongDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewSongServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		info = request.getParameter("info");
		
		// Thêm các bài hát được đề xuất
		if (info.equals("add")){
			this.add(request, response);
		}
		// xóa bài hát được đề xuất
		if (info.equals("remove")){
			this.remove(request, response);
		}
		// Xem tất cả các bài hát theo số trang
		if (info.equals("find")){
			this.findAll(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		
		Song songData = new Song();
		songData.setSongId(songId);
		
		if (newSongDao.add(songData)){
			request.setAttribute("message", "Đã thêm bài hát thành công!");
		} else {
			request.setAttribute("message",  "Không thể thêm bài hát!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/newSong.jsp").forward(request, response);
	}
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int newSongId = Integer.parseInt(request.getParameter("newSongId"));
		
		NewSong newSong = new NewSong();
		newSong.setNewSongId(newSongId);
		
		if (newSongDao.remove(newSong)){
			request.setAttribute("message", "Đã xóa thành công!");
		} else {
			request.setAttribute("message", "Xóa không thành công!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/newSong.jsp").forward(request, response);
	}
	protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<NewSong> newSongs = newSongDao.findAll();
		
		request.setAttribute("newSongs", newSongs);
		request.getRequestDispatcher("admin/newSong.jsp").forward(request, response);
	}

}
