package music.vo;

import java.io.Serializable;
import java.util.Map;

public class Singer implements Serializable{
	
	private static final long serialVersionUID = 634615880840859135L;
	
	int singerId;				// ID ca sĩ
	String singerName;			// tên ca sĩ
	int singerSex;				
	String singerThumbnail;		// Hình ảnh ca sĩ
	String singerIntroduction;	// giới thiệu ca sĩ
	

	public Singer() {
	}
	public Singer(Map<String, Object> map){
		this.singerId = (int) map.get("singerId");
		this.singerName = (String) map.get("singerName");
		this.singerSex = (int) map.get("singerSex");
		this.singerThumbnail = (String) map.get("singerThumbnail");
		this.singerIntroduction = (String) map.get("singerIntroduction");
	}
	
	
	public Singer(int singerId, String singerName, int singerSex, String singerThumbnail, String singerIntroduction) {
		super();
		this.singerId = singerId;
		this.singerName = singerName;
		this.singerSex = singerSex;
		this.singerThumbnail = singerThumbnail;
		this.singerIntroduction = singerIntroduction;
	}
	public int getSingerId() {
		return singerId;
	}
	public void setSingerId(int singerId) {
		this.singerId = singerId;
	}
	public String getSingerName() {
		return singerName;
	}
	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}
	public int getSingerSex() {
		return singerSex;
	}
	public void setSingerSex(int singerSex) {
		this.singerSex = singerSex;
	}
	public String getSingerThumbnail() {
		return singerThumbnail;
	}
	public void setSingerThumbnail(String singerThumbnail) {
		this.singerThumbnail = singerThumbnail;
	}
	public String getSingerIntroduction() {
		return singerIntroduction;
	}
	public void setSingerIntroduction(String singerIntroduction) {
		this.singerIntroduction = singerIntroduction;
	}
	
	
}
