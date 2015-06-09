package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 获取@当前用户的最新微博的ID
 * 
 * @author xiaoV
 * 
 */
public class MentionsIds extends WeiboResponse {

	private static final long serialVersionUID = -7507725013094889640L;

	private long nextCursor;
	private long previousCursor;
	private long totalNumber;
	private long interval;
	private List<String> statusesIds; // ID列表
	private boolean hasvisible;

	public MentionsIds(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			nextCursor = json.getLong("next_cursor");
			previousCursor = json.getLong("previous_cursor");
			totalNumber = json.getLong("total_number");
			hasvisible = json.getBoolean("hasvisible");
			interval = json.getLong("interval");
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

	public MentionsIds(JSONObject json) throws WeiboException {
		try {
			nextCursor = json.getLong("next_cursor");
			previousCursor = json.getLong("previous_cursor");
			totalNumber = json.getLong("total_number");
			hasvisible = json.getBoolean("hasvisible");
			interval = json.getLong("interval");
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

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
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
		return "MentionsIds [" + "next_cursor=" + nextCursor
				+ ", previous_cursor=" + previousCursor
				+ ", interval=" + interval
				+ ", hasvisible=" + hasvisible
				+ ", statusesIds=" + statusesIds
				+ ", total_number = " + totalNumber + "]";
	}

}
