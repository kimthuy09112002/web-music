package music.dao;

import java.util.List;

import music.vo.Recommend;
import music.vo.Song;

public interface RecommendDao {

	/**
	 * Thêm các bài hát được đề xuất
	 * @param song
	 * @return
	 */
	public boolean add(Song song);
	/**
	 * Xóa bài hát được đề xuất
	 * @param song
	 * @return
	 */
	public boolean remove(Recommend song);
	/**
	 * Truy vấn tất cả các bài hát được đề xuất
	 * @return
	 */
	public List<Recommend> findAll();
}
