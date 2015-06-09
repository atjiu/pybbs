package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 搜学校搜索建议
 * 
 * @author xiaoV
 * 
 */
public class SchoolSearch extends WeiboResponse {

	private static final long serialVersionUID = 4059782919675941016L;

	private String schoolName;
	private String location;
	private long id;
	private long type;

	public SchoolSearch(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			id = json.getInt("id");
			location = json.getString("location");
			type = json.getLong("type");
			schoolName = json.getString("school_name");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public SchoolSearch(JSONObject json) throws WeiboException {
		try {
			id = json.getInt("id");
			location = json.getString("location");
			type = json.getLong("type");
			schoolName = json.getString("school_name");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public static List<SchoolSearch> constructSchoolSearch(Response res)
			throws WeiboException {
		try {
			JSONArray list = res.asJSONArray();
			int size = list.length();
			List<SchoolSearch> schools = new ArrayList<SchoolSearch>(size);
			for (int i = 0; i < size; i++) {
				schools.add(new SchoolSearch(list.getJSONObject(i)));
			}
			return schools;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		} catch (WeiboException te) {
			throw te;
		}
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SchoolSearch [id=" + id
				+ ",school_name=" + schoolName
				+ ",location" + location
				+ ", type=" + type + "]";
	}
}
