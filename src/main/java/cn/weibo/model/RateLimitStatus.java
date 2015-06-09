/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;
import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

public class RateLimitStatus extends WeiboResponse {

	private static final long serialVersionUID = -3153374766679996576L;

	private int ipLimit;
	private int remainingIpHits;
	private long remainingUserHits;
	private String resetTime;
	private int resetTimeInSeconds;
	private long userLimit;
	private List<ApiRateLimits> apiRateLimit;

	public RateLimitStatus(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			JSONArray list = json.getJSONArray("api_rate_limits");
			int size = list.length();
			apiRateLimit = new ArrayList<ApiRateLimits>(size);
			for (int i = 0; i < size; i++) {
				apiRateLimit.add(new ApiRateLimits(list.getJSONObject(i)));
			}
			ipLimit = json.getInt("ip_limit");
			remainingIpHits = json.getInt("remaining_ip_hits");
			remainingUserHits = json.getLong("remaining_user_hits");
			resetTime = json.getString("reset_time");
			resetTimeInSeconds = json.getInt("reset_time_in_seconds");
			userLimit = json.getLong("user_limit");
		} catch (JSONException jsone) {
			throw new WeiboException(
					jsone.getMessage() + ":" + json.toString(), jsone);
		}
	}

	public int getIpLimit() {
		return ipLimit;
	}

	public void setIpLimit(int ipLimit) {
		this.ipLimit = ipLimit;
	}

	public int getRemainingIpHits() {
		return remainingIpHits;
	}

	public void setRemainingIpHits(int remainingIpHits) {
		this.remainingIpHits = remainingIpHits;
	}

	public long getRemainingUserHits() {
		return remainingUserHits;
	}

	public void setRemainingUserHits(long remainingUserHits) {
		this.remainingUserHits = remainingUserHits;
	}

	public String getResetTime() {
		return resetTime;
	}

	public void setResetTime(String resetTime) {
		this.resetTime = resetTime;
	}

	public int getResetTimeInSeconds() {
		return resetTimeInSeconds;
	}

	public void setResetTimeInSeconds(int resetTimeInSeconds) {
		this.resetTimeInSeconds = resetTimeInSeconds;
	}

	public long getUserLimit() {
		return userLimit;
	}

	public void setUserLimit(long userLimit) {
		this.userLimit = userLimit;
	}

	public List<ApiRateLimits> getApiRateLimit() {
		return apiRateLimit;
	}

	public void setApiRateLimit(List<ApiRateLimits> apiRateLimit) {
		this.apiRateLimit = apiRateLimit;
	}

	@Override
	public String toString() {
		return "RateLimitStatus [ipLimit=" + ipLimit + ", remainingIpHits="
				+ remainingIpHits + ", remainingUserHits=" + remainingUserHits
				+ ", resetTime=" + resetTime + ", resetTimeInSeconds="
				+ resetTimeInSeconds + ", userLimit=" + userLimit
				+ ", apiRateLimit=" + apiRateLimit + "]";
	}

}
