package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 获取地点分类
 * 
 * @author xiaoV
 * 
 */
public class PoisitionCategory extends WeiboResponse {

	private static final long serialVersionUID = 6795534455304308918L;

	private long id;
	private String name;
	private long pid;

	public PoisitionCategory(Response res) throws WeiboException {
		super(res);
		JSONObject json = null;
		try {
			json = res.asJSONObject();
			id = json.getLong("id");
			pid = json.getLong("pid");
			name = json.getString("name");
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public PoisitionCategory(JSONObject json) throws WeiboException {
		try {
			id = json.getLong("id");
			pid = json.getLong("pid");
			name = json.getString("name");
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public static List<PoisitionCategory> constructPoisCategory(Response res)
			throws WeiboException {
		try {
			JSONArray json = res.asJSONArray();
			int size = json.length();
			List<PoisitionCategory> list = new ArrayList<PoisitionCategory>(
					size);
			for (int i = 0; i < size; i++) {
				list.add(new PoisitionCategory(json.getJSONObject(i)));
			}
			return list;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "PoisitionCategory [" + "id=" + id
				+ ", name=" + name
				+ ", pid=" + pid + "]";
	}
}
