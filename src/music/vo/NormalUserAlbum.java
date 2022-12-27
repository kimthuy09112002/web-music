package music.vo;

import java.io.Serializable;


public class NormalUserAlbum implements Serializable {

	private static final long serialVersionUID = -4591715018803067818L;
	
	int userId;		//ID người dùng
	int albumId;	//ID album
	
	
	Album album;
	
	public NormalUserAlbum() {
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}


	public Album getAlbum() {
		return album;
	}


	public void setAlbum(Album album) {
		this.album = album;
	}
	
	
}
