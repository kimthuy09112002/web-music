package music.dao;

import music.vo.Comments;

public interface CommentsDao {


	/**
	* Lưu đối tượng bình luận vào cơ sở dữ liệu
	* @param comment Đối tượng comment để lưu
	* @return trả về true nếu lưu thành công, ngược lại trả về false
	*/
	public boolean save(Comments comment);
}
