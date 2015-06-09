package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 获取当前登录用户及其所关注用户的最新微博的ID
 * 
 * @author xiaoV
 * 
 */
public class FriendsTimelineIds extends WeiboResponse {

	private static final long serialVersionUID = 4785295274677627206L;

	private long nextCursor;
	private long previousCursor;
	private long totalNumber;
	private String ad;
	private String advertises;
	private List<String> statusesIds; // ID列表
	private boolean hasvisible;

	public FriendsTimelineIds(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			nextCursor = json.getLong("next_cursor");
			previousCursor = json.getLong("previous_cursor");
			totalNumber = json.getLong("total_number");
			ad = json.getString("ad");
			advertises = json.getString("advertises");
			hasvisible = json.getBoolean("hasvisible");
			JSONArray list = json.getJSONArray("statuses");
			int size = list.length();
			statusesIds = new ArrayList<String>(size);
			for (int i = 0; i < size; i++) {
				statusesIds.add(list.getString(i));
			}
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}

	}

	public FriendsTimelineIds(JSONObject json) throws WeiboException {
		try {
			nextCursor = json.getLong("next_cursor");
			previousCursor = json.getLong("previous_cursor");
			totalNumber = json.getLong("total_number");
			ad = json.getString("ad");
			advertises = json.getString("advertises");
			hasvisible = json.getBoolean("hasvisible");
			JSONArray list = json.getJSONArray("statuses");
			int size = list.length();
			statusesIds = new ArrayList<String>(size);
			for (int i = 0; i < size; i++) {
				statusesIds.add(list.getString(i));
			}
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public long getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(long nextCursor) {
		this.nextCursor = nextCursor;
	}

	public long getPreviousCursor() {
		return previousCursor;
	}

	public void setPreviousCursor(long previousCursor) {
		this.previousCursor = previousCursor;
	}

	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getAdvertises() {
		return advertises;
	}

	public void setAdvertises(String advertises) {
		this.advertises = advertises;
	}

	public List<String> getStatusesIds() {
		return statusesIds;
	}

	public void setStatusesIds(List<String> statusesIds) {
		this.statusesIds = statusesIds;
	}

	public boolean isHasvisible() {
		return hasvisible;
	}

	public void setHasvisible(boolean hasvisible) {
		this.hasvisible = hasvisible;
	}

	@Override
	public String toString() {
		return "FriendsTimelineIds [" + "next_cursor=" + nextCursor
				+ ", previous_cursor=" + previousCursor
				+ ", ad=" + ad
				+ ", advertises=" + advertises
				+ ", hasvisible=" + hasvisible
				+ ", statusesIds=" + statusesIds
				+ ", total_number = " + totalNumber + "]";
	}
}
