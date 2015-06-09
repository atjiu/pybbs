package cn.weibo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 地点信息
 * 
 * @author xiaoV
 * 
 */
public class Places extends WeiboResponse {

	private static final long serialVersionUID = -1423136187811594673L;

	private String poiid;// 地点ID
	private String title;// 地点名称
	private String address;// 地址及
	private double lon;// 经度
	private double lat;// 纬度
	private String category;
	private String city;
	private String province;
	private String country;
	private String url;
	private String phone;
	private String postcode;
	private long weiboId;
	private String categorys;
	private String categoryName;
	private String icon;
	private long checkinNum;
	private long checkinUserNum;
	private Date checkinTime;
	private long tipNum;
	private long photoNum;
	private long todoNum;
	private long distance;

	private static long totalNumber;

	public Places(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			poiid = json.getString("poiid");
			title = json.getString("title");
			address = json.getString("address");
			lon = json.getDouble("lon");
			lat = json.getDouble("lat");
			category = json.getString("category");
			city = json.getString("city");
			province = json.getString("privince");
			country = json.getString("country");
			url = json.getString("url");
			phone = json.getString("url");
			postcode = json.getString("postcode");
			weiboId = json.getLong("weibo_id");
			categorys = json.getString("categorys");
			categoryName = json.getString("category_name");
			icon = json.getString("icon");
			checkinUserNum = json.getLong("checkin_user_num");
			checkinTime = parseDate(json.getString("checkin_time"),
					"yyyy-MM-dd hh:mm:ss");
			checkinNum = json.getLong("checkin_num");
			tipNum = json.getLong("tip_num");
			photoNum = json.getLong("photo_num");
			todoNum = json.getLong("todo_num");
			distance = json.getLong("distance");
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public Places(JSONObject json) throws WeiboException {
		try {
			poiid = json.getString("poiid");
			title = json.getString("title");
			address = json.getString("address");
			lon = json.getDouble("lon");
			lat = json.getDouble("lat");
			category = json.getString("category");
			city = json.getString("city");
			province = json.getString("privince");
			country = json.getString("country");
			url = json.getString("url");
			phone = json.getString("url");
			postcode = json.getString("postcode");
			weiboId = json.getLong("weibo_id");
			categorys = json.getString("categorys");
			categoryName = json.getString("category_name");
			icon = json.getString("icon");
			checkinUserNum = json.getLong("checkin_user_num");
			checkinTime = parseDate(json.getString("checkin_time"),
					"yyyy-MM-dd hh:mm:ss");
			checkinNum = json.getLong("checkin_num");
			tipNum = json.getLong("tip_num");
			photoNum = json.getLong("photo_num");
			todoNum = json.getLong("todo_num");
			distance = json.getLong("distance");
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public static List<Places> constructPlace(Response res)
			throws WeiboException {
		try {
			JSONObject jsonObj = res.asJSONObject();
			totalNumber = jsonObj.getLong("total_number");
			JSONArray json = jsonObj.getJSONArray("pois");
			int size = json.length();
			List<Places> list = new ArrayList<Places>(size);
			for (int i = 0; i < size; i++) {
				list.add(new Places(json.getJSONObject(i)));
			}
			return list;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}

	}

	public String getPoiid() {
		return poiid;
	}

	public void setPoiid(String poiid) {
		this.poiid = poiid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public long getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(long weiboId) {
		this.weiboId = weiboId;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getCheckinUserNum() {
		return checkinUserNum;
	}

	public void setCheckinUserNum(long checkinUserNum) {
		this.checkinUserNum = checkinUserNum;
	}

	public Date getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}

	public long getCheckinNum() {
		return checkinNum;
	}

	public void setCheckinNum(long checkinNum) {
		this.checkinNum = checkinNum;
	}

	public long getTipNum() {
		return tipNum;
	}

	public void setTipNum(long tipNum) {
		this.tipNum = tipNum;
	}

	public long getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(long photoNum) {
		this.photoNum = photoNum;
	}

	public long getTodoNum() {
		return todoNum;
	}

	public void setTodoNum(long todoNum) {
		this.todoNum = todoNum;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Place [" + "poiid=" + poiid
				+ ",title=" + title 
				+ ",address=" + address 
				+ ",lon=" + lon 
				+ ",lat=" + lat 
				+ ",category=" + category 
				+ ",city=" + city 
				+ ",province=" + province
				+ ",country=" + country
				+ ",url=" + url
				+ ",phone=" + phone
				+ ",postcode=" + postcode 
				+ ",weiboId=" + weiboId
				+ ",categorys=" + categorys 
				+ ",categoryName=" + categoryName
				+ ",icon=" + icon
				+ ",checkinUserNum=" + checkinUserNum
				+ ",checkinTime=" + checkinTime
				+ ",tip_num=" + tipNum
				+ ",photo_num=" + photoNum
				+ ",todo_num=" + todoNum
				+ ",distance=" + distance
				+ ",total_number=" + totalNumber + "]";
	}

}
