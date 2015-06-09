package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * @author SinaWeibo
 * 
 */
public class Emotion extends WeiboResponse {
	private static final long serialVersionUID = -4096813631691846494L;
	private String phrase; // 表情使用的替代文字
	private String type; // 表情类型，image为普通图片表情，magic为魔法表情
	private String url; // 表情图片存放的位置
	private boolean hot; // 是否为热门表情
	private boolean common; // 是否是常用表情
	private String value;
	private String category; // 表情分类
	private String picid;
	private String icon;

	public Emotion(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			phrase = json.getString("phrase");
			type = json.getString("type");
			url = json.getString("url");
			hot = json.getBoolean("hot");
			category = json.getString("category");
			common = json.getBoolean("common");
			value = json.getString("value");
			picid = json.getString("picid");
			icon = json.getString("icon");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public Emotion(JSONObject json) throws WeiboException {
		try {
			phrase = json.getString("phrase");
			type = json.getString("type");
			url = json.getString("url");
			hot = json.getBoolean("hot");
			category = json.getString("category");
			common = json.getBoolean("common");
			value = json.getString("value");
			picid = json.getString("picid");
			icon = json.getString("icon");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public static List<Emotion> constructEmotions(Response res)
			throws WeiboException {
		try {
			JSONArray list = res.asJSONArray();
			int size = list.length();
			List<Emotion> emotions = new ArrayList<Emotion>(size);
			for (int i = 0; i < size; i++) {
				emotions.add(new Emotion(list.getJSONObject(i)));
			}
			return emotions;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		} catch (WeiboException te) {
			throw te;
		}

	}

	public Emotion() {
		super();
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isHot() {
		return hot;
	}

	public void setHot(boolean hot) {
		this.hot = hot;
	}

	public boolean isCommon() {
		return common;
	}

	public void setCommon(boolean common) {
		this.common = common;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPicid() {
		return picid;
	}

	public void setPicid(String picid) {
		this.picid = picid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Emotion [phrase=" + phrase + ", type=" + type + ", url=" + url
				+ ", hot=" + hot + ", common=" + common + ", value=" + value
				+ ", category=" + category + ", picid=" + picid + ", icon="
				+ icon + "]";
	}

}
