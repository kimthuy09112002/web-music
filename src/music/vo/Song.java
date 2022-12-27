package music.vo;

import java.io.Serializable;
import java.util.Map;

public class Song implements Serializable{

	private static final long serialVersionUID = -733005812710589217L;
	
	int songId;			
	int singerId;		
	int albumId;		
	String songTitle;	
	int songPlaytimes;	
	int songDldtimes;	
	String songFile;	

	Singer singer;		

	public Song() {
	}
	public Song(Map<String, Object> map){
		this.songId = (int) map.get("songId");
		this.singerId = (int) map.get("singerId");
		this.albumId = (int) map.get("albumId");
		this.songTitle = (String) map.get("songTitle");
		this.songPlaytimes = (int) map.get("songPlaytimes");
		this.songDldtimes = (int) map.get("songDldtimes");
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	
	
	public Song(int songId, int singerId, int albumId, String songTitle, int songPlaytimes, int songDldtimes,
			String songFile) {
		super();
		this.songId = songId;
		this.singerId = singerId;
		this.albumId = albumId;
		this.songTitle = songTitle;
		this.songPlaytimes = songPlaytimes;
		this.songDldtimes = songDldtimes;
		this.songFile = songFile;
	}
	public Song(int songId, int singerId, int albumId, String songTitle, int songPlaytimes, int songDldtimes) {
		super();
		this.songId = songId;
		this.singerId = singerId;
		this.albumId = albumId;
		this.songTitle = songTitle;
		this.songPlaytimes = songPlaytimes;
		this.songDldtimes = songDldtimes;
	}
	public int getSongId() {
		return songId;
	}
	public void setSongId(int songId) {
		this.songId = songId;
	}
	public int getSingerId() {
		return singerId;
	}
	public void setSingerId(int singerId) {
		this.singerId = singerId;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public String getSongTitle() {
		return songTitle;
	}
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	public int getSongPlaytimes() {
		return songPlaytimes;
	}
	public void setSongPlaytimes(int songPlaytimes) {
		this.songPlaytimes = songPlaytimes;
	}
	public int getSongDldtimes() {
		return songDldtimes;
	}
	public void setSongDldtimes(int songDldtimes) {
		this.songDldtimes = songDldtimes;
	}
	public String getSongFile() {
		return songFile;
	}
	public void setSongFile(String songFile) {
		this.songFile = songFile;
	}
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	
	
}