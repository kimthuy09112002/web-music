package music.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import music.dao.RecommendDao;
import music.dao.impl.RecommendDaoImpl;
import music.vo.Recommend;
import music.vo.Song;

/**
 * Servlet implementation class RecommendServlet
 */
@WebServlet("/RecommendServlet")
public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String info = "";
	private RecommendDao recommendDao = new RecommendDaoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendServlet() {
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

	// Thêm các bài hát được đề xuất
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId = Integer.parseInt(request.getParameter("songId"));
		
		Song songData = new Song();
		songData.setSongId(songId);
		
		if (recommendDao.add(songData)){
			request.setAttribute("message", "Bài hát được đề xuất thành công!");
		} else {
			request.setAttribute("message", "Không đề xuất được bài hát!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/recommend.jsp").forward(request, response);
	}
	// xóa bài hát được đề xuất
	protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int recmdId = Integer.parseInt(request.getParameter("recmdId"));
		
		Recommend recmd = new Recommend();
		recmd.setRecmdId(recmdId);
		
		if (recommendDao.remove(recmd)){
			request.setAttribute("message", "Đã xóa thành công!");
		} else {
			request.setAttribute("message", "Xóa không thành công!");
		}
		request.setAttribute("flag", true);
		request.getRequestDispatcher("admin/recommend.jsp").forward(request, response);
	}
	// Xem tất cả các bài hát theo số trang
	protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Recommend> recommends = recommendDao.findAll();
		
		// trả kết quả về trang
		request.setAttribute("recommends", recommends);
		request.getRequestDispatcher("admin/recommend.jsp").forward(request, response);
	}
}
