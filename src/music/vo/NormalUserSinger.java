package music.vo;

import java.io.Serializable;


public class NormalUserSinger implements Serializable {

	private static final long serialVersionUID = -2874289805100663205L;
	
	int userId;		// ID người dùng
	int singerId;	// ID ca sĩ
	
	Singer singer;
	
	public NormalUserSinger() {
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSingerId() {
		return singerId;
	}
	public void setSingerId(int singerId) {
		this.singerId = singerId;
	}


	public Singer getSinger() {
		return singer;
	}


	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	
}
