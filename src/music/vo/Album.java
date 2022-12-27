package music.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


public class Album implements Serializable{

	private static final long serialVersionUID = 413439954162136346L;
	
	int albumId;		// ID album
	int singerId;		// ID ca sĩ
	String albumTitle;	// tựa đề album
	String albumPic;	// album Nghệ thuật
	Date albumPubDate;	// ngày phát hành album
	String albumPubCom;	// công ty phát hành album
	
	// trường không có trong cơ sở dữ liệu
	Singer singer;
	
	// Phương pháp xây dựng
	public Album() {
		super();
	}
	
	public Album(int albumId, int singerId, String albumTitle, String albumPic, Date albumPubDate, String albumPubCom) {
		super();
		this.albumId = albumId;
		this.singerId = singerId;
		this.albumTitle = albumTitle;
		this.albumPic = albumPic;
		this.albumPubDate = albumPubDate;
		this.albumPubCom = albumPubCom;
	}

	public Album(Map<String, Object> map){
		this.albumId = (int) map.get("albumId");
		this.singerId = (int) map.get("singerId");
		this.albumTitle = (String) map.get("albumTitle");
		this.albumPic = (String) map.get("albumPic");
		this.albumPubDate = (Date) map.get("albumPubDate");
		this.albumPubCom = (String) map.get("albumPubCom");
	}
	
	
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public int getSingerId() {
		return singerId;
	}
	public void setSingerId(int singerId) {
		this.singerId = singerId;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getAlbumPic() {
		return albumPic;
	}
	public void setAlbumPic(String albumPic) {
		this.albumPic = albumPic;
	}
	public Date getAlbumPubDate() {
		return albumPubDate;
	}
	public void setAlbumPubDate(Date albumPubDate) {
		this.albumPubDate = albumPubDate;
	}
	public String getAlbumPubCom() {
		return albumPubCom;
	}
	public void setAlbumPubCom(String albumPubCom) {
		this.albumPubCom = albumPubCom;
	}

	public Singer getSinger() {
		return singer;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	
	
}
