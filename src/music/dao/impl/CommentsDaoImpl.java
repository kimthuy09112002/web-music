package music.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import music.dao.CommentsDao;
import music.util.JdbcUtil;
import music.vo.Comments;

public class CommentsDaoImpl implements CommentsDao {

	/**
	* Lưu đối tượng bình luận vào cơ sở dữ liệu
	* @param comment Đối tượng comment để lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	@Override
	public boolean save(Comments comment) {
		
		boolean result = false;
		List<Object> params = new ArrayList<Object>();
		String sql = "INSERT INTO comments(userId, songId, commentText, commentDate) VALUES(?,?,?,?)";
		JdbcUtil jdbc = null;
		
		int userId = comment.getUserId();
		int songId = comment.getSongId();
		String commentText = comment.getCommentText();
		Date commentDate = comment.getCommentDate();
		
		params.add(userId);
		params.add(songId);
		if (commentText != null && !"".equals(commentText)){
			params.add(commentText);
		} else {
			return result;
		}
		params.add(commentDate);
		
		System.out.println("Lưu nhận xét để bắt đầu lặp lại!");
		for (Object object : params) {
			System.out.println(object);
		}
		System.out.println("lưu nhận xét để kết thúc lặp lại");
		try {
			jdbc = new JdbcUtil();
			jdbc.getConnection();
			if (jdbc.updateByPreparedStatement(sql, params)){
				// Nếu lưu thành công thì kết quả trả về là true
				result = true;
			}
		} catch (SQLException e) {
			throw new RuntimeException("lưu nhận xét ngoại lệ!", e);
		} finally {
			if (jdbc != null) {
				jdbc.releaseConn();
			}
		}
		System.out.println("Đã lưu bình luận thành công!");
		return result;
	}

}
