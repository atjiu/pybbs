package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 地理信息
 * 
 * @author xiaoV
 * 
 */
public class Geos extends WeiboResponse {

	private static final long serialVersionUID = -3147781298339233908L;

	private String longitude; // 经度坐标
	private String latitude; // 维度坐标
	private String city; // 所在城市的城市代码
	private String province; // 所在省份的省份代码
	private String cityName; // 所在城市的城市名称
	private String provinceName;// 所在省份的省份名称
	private String address; // 所在的实际地址，可以为空
	private String pinyin; // 地址的汉语拼音，不是所有情况都会返回该字段
	private String more; // 更多信息，不是所有情况都会返回该字段

	public Geos(Response res) throws WeiboException {
		super(res);
		JSONObject json = null;
		try {
			json = res.asJSONObject();
			longitude = json.getString("longitude");
			latitude = json.getString("latitude");
			city = json.getString("city");
			province = json.getString("province");
			cityName = json.getString("city_name");
			provinceName = json.getString("province_name");
			address = json.getString("address");
			pinyin = json.getString("pinyin");
			more = json.getString("more");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public Geos(JSONObject json) throws WeiboException {
		try {
			longitude = json.getString("longitude");
			latitude = json.getString("latitude");
			city = json.getString("city");
			province = json.getString("province");
			cityName = json.getString("city_name");
			provinceName = json.getString("province_name");
			address = json.getString("address");
			pinyin = json.getString("pinyin");
			more = json.getString("more");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public static List<Geos> constructGeos(Response res) throws WeiboException {
		try {
			JSONArray list = res.asJSONObject().getJSONArray("geos");
			int size = list.length();
			List<Geos> geos = new ArrayList<Geos>(size);
			for (int i = 0; i < size; i++) {
				geos.add(new Geos(list.getJSONObject(i)));
			}
			return geos;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	@Override
	public String toString() {
		return "Geos [" + "longitude = " + longitude
				+ ", latitude = " + latitude
				+ ", city = " + city 
				+ ", province = " + province
				+ ", city_name = " + cityName
				+ ", province_name = " + provinceName
				+ ", address = " + address
				+ ", pinyin = " + pinyin
				+ ", more = " + more + "]";
	}

}
