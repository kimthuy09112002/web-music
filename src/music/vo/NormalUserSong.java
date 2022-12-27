package music.vo;

import java.io.Serializable;


public class NormalUserSong implements Serializable {

	private static final long serialVersionUID = -3339991183053257247L;
	
	int userId;	// ID người dùng
	int songId;	// ID bài hát
	

	Song song;
	
	public NormalUserSong() {
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}


	public Song getSong() {
		return song;
	}


	public void setSong(Song song) {
		this.song = song;
	}
	
	
}
