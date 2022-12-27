package music.vo;

public class Recommend {

	int recmdId;	
	int songId;		
	
	Song song;		
	Album album;	
	Singer singer;	
	

	public Recommend() {
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



	public int getRecmdId() {
		return recmdId;
	}
	public void setRecmdId(int recmdId) {
		this.recmdId = recmdId;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	
	
}
