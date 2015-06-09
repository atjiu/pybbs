package cn.weibo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 获取当前用户的收藏列表的ID
 * 
 * @author xiaoV
 * 
 */
public class FavoritesIds extends WeiboResponse {

	private static final long serialVersionUID = -2969906105821204489L;
	private Date favoritedTime; // 添加收藏的时间
	private String statusId; // 收藏的statusId
	private List<FavoritesTag> tags; // 收藏的tags
	private static long totalNumber; // 收藏总数

	public FavoritesIds(Response res) throws WeiboException {
		super(res);
		JSONObject json = null;
		try {
			json = res.asJSONObject();
			favoritedTime = parseDate(json.getString("favorited_time"),
					"EEE MMM dd HH:mm:ss z yyyy");
			statusId = json.getString("status");
			if (!json.isNull("tags")) {
				JSONArray list = json.getJSONArray("tags");
				int size = list.length();
				List<FavoritesTag> tag = new ArrayList<FavoritesTag>(size);
				for (int i = 0; i < size; i++) {
					tag.add(new FavoritesTag(list.getJSONObject(i)));
				}
			}
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	FavoritesIds(JSONObject json) throws WeiboException, JSONException {
		favoritedTime = parseDate(json.getString("favorited_time"),
				"EEE MMM dd HH:mm:ss z yyyy");
		if (!json.isNull("status")) {
			statusId = json.getString("status");
		}
		if (!json.isNull("tags")) {
			JSONArray list = json.getJSONArray("tags");
			int size = list.length();
			tags = new ArrayList<FavoritesTag>(size);
			for (int i = 0; i < size; i++) {
				tags.add(new FavoritesTag(list.getJSONObject(i)));
			}
		}

	}

	public static List<FavoritesIds> constructFavoritesIds(Response res)
			throws WeiboException {
		try {
			JSONArray list = res.asJSONObject().getJSONArray("favorites");
			int size = list.length();
			List<FavoritesIds> favoritesIds = new ArrayList<FavoritesIds>(size);
			for (int i = 0; i < size; i++) {
				favoritesIds.add(new FavoritesIds(list.getJSONObject(i)));
			}
			totalNumber = res.asJSONObject().getLong("total_number");
			return favoritesIds;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public Date getFavoritedTime() {
		return favoritedTime;
	}

	public void setFavoritedTime(Date favoritedTime) {
		this.favoritedTime = favoritedTime;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public static long getTotalNumber() {
		return totalNumber;
	}

	public static void setTotalNumber(long totalNumber) {
		FavoritesIds.totalNumber = totalNumber;
	}

	@Override
	public String toString() {
		return "Favorites [" + "favorited_time=" + favoritedTime
				+ ", statusId=" + statusId + ", FavoritesTag="
				+ ((tags == null) ? "null" : tags.toString())
				+ ", total_number = " + totalNumber + "]";
	}
}
