package music.dao;

import java.util.List;

import music.vo.NewSong;
import music.vo.Song;

public interface NewSongDao {

	/**
	* Thêm các bài hát được đề xuất
	* bài hát @param
	* @return
	*/
	public boolean add(Song song);
	/**
	 * xóa bài hát được đề xuất
	 * @param recommend
	 * @return
	 */
	public boolean remove(NewSong recommend);
	/**
	 * Truy vấn tất cả các bài hát được đề xuất
	 * @return
	 */
	public List<NewSong> findAll();
}
