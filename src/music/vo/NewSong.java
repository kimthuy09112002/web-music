package music.vo;

public class NewSong {

	int newSongId;
	int songId;
	
	// Các trường không tồn tại trong cơ sở dữ liệu
	Song song;		// Tên bài hát gợi ý
	Singer singer;	// Đối tượng ca sĩ mà bài hát được đề xuất thuộc về
	Album album;	//  Đối tượng của album mà bài hát được đề xuất thuộc về
	
	public NewSong() {
		super();
	}
	
	
	
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}


	public Singer getSinger() {
		return singer;
	}



	public void setSinger(Singer singer) {
		this.singer = singer;
	}



	public Album getAlbum() {
		return album;
	}



	public void setAlbum(Album album) {
		this.album = album;
	}



	public int getNewSongId() {
		return newSongId;
	}
	public void setNewSongId(int newSongId) {
		this.newSongId = newSongId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	
	
}
