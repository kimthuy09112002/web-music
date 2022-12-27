package music.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import music.dao.NewSongDao;
import music.dao.RecommendDao;
import music.dao.SongDao;
import music.dao.impl.NewSongDaoImpl;
import music.dao.impl.RecommendDaoImpl;
import music.dao.impl.SongDaoImpl;
import music.vo.NewSong;
import music.vo.Recommend;
import music.vo.Song;

/**
 * Servlet implementation class ShowInfoServlet
 */
@WebServlet("/ShowInfoServlet")
public class ShowInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String info = "";
	RecommendDao recommendDao = new RecommendDaoImpl();
	NewSongDao newSongDao = new NewSongDaoImpl();
	SongDao songDao = new SongDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowInfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		info = request.getParameter("info");
		
		// Chuyển đến trang chủ
		if (info.equals("index")){
			this.to_index(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	// Chuyển đến trang chủ
	protected void to_index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Recommend> recommends = recommendDao.findAll();
		List<NewSong> newSongs = newSongDao.findAll();
		List<Song> downloadRank = songDao.downloadRank();		// Tải xuống Bảng xếp hạng
		
		request.setAttribute("recommends", recommends);
		request.setAttribute("newSongs", newSongs);
		request.setAttribute("dRank", downloadRank);
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

}
