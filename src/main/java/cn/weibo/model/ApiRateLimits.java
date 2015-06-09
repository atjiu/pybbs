package cn.weibo.model;

import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

public class ApiRateLimits implements java.io.Serializable{

	private static final long serialVersionUID = 8550645887134692311L;
	private String api;                 //接口
	private int limit;                  //接口限制
	private String limitTimeUnit;       //限制单元
	private long remainingHits;         //剩余调用次数
    
	ApiRateLimits(JSONObject json) throws WeiboException {
		try {
			api = json.getString("api");
			limit = json.getInt("limit");
			limitTimeUnit = json.getString("limit_time_unit");
			remainingHits = json.getLong("remaining_hits");
		} catch (JSONException jsone) {
			throw new WeiboException(jsone.getMessage() + ":" + json.toString(), jsone);
		}
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getLimitTimeUnit() {
		return limitTimeUnit;
	}

	public void setLimitTimeUnit(String limitTimeUnit) {
		this.limitTimeUnit = limitTimeUnit;
	}

	public long getRemainingHits() {
		return remainingHits;
	}

	public void setRemainingHits(long remainingHits) {
		this.remainingHits = remainingHits;
	}

	@Override
	public String toString() {
		return "api_rate_limits [api=" + api + ", limit=" + limit
				+ ", limitTimeUnit=" + limitTimeUnit + ", remainingHits="
				+ remainingHits + "]";
	}
	
	
}
