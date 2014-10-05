package org.xixixi.sweetweather.entity;
/**
 * 用来呈现百度天气api中的tips的实体类。
 * 分别有
 * "title": "穿衣",
 * "zs": "冷",
 * "tipt": "穿衣指数",
 * "des": "天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。"
 * @author Administrator
 *
 */
public class WeatherTip {
	private String title;
	private String zs;
	private String tipt;
	private String des;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getTipt() {
		return tipt;
	}
	public void setTipt(String tipt) {
		this.tipt = tipt;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	
	
}
