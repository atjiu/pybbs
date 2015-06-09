package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 获取用户的粉丝数、关注数、微博数、悄悄关注数
 * 
 * @author xiaoV
 * 
 */
public class UserCounts extends WeiboResponse {

	private static final long serialVersionUID = -2841255612083504764L;

	private long id;
	private long followersCount;// 粉丝数
	private long friendsCount;// 关注数
	private long statusesCount;// 微博数
	private long privateFriendsCount;// 悄悄关注数

	public UserCounts(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			id = json.getLong("id");
			followersCount = json.getLong("followers_count");
			friendsCount = json.getLong("friends_count");
			statusesCount = json.getLong("statuses_count");
			privateFriendsCount = json.getLong("private_friends_count");
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public UserCounts(JSONObject json) throws WeiboException {
		try {
			id = json.getLong("id");
			followersCount = json.getLong("followers_count");
			friendsCount = json.getLong("friends_count");
			statusesCount = json.getLong("statuses_count");
			privateFriendsCount = json.getLong("private_friends_count");
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		}
	}

	public static List<UserCounts> constructUserCount(Response res)
			throws WeiboException {
		try {
			JSONArray json = res.asJSONArray();
			int size = json.length();
			List<UserCounts> list = new ArrayList<UserCounts>(size);
			for (int i = 0; i < size; i++) {
				list.add(new UserCounts(json.getJSONObject(i)));
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

	public long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	public long getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(long friendsCount) {
		this.friendsCount = friendsCount;
	}

	public long getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(long statusesCount) {
		this.statusesCount = statusesCount;
	}

	public long getPrivateFriendsCount() {
		return privateFriendsCount;
	}

	public void setPrivateFriendsCount(long privateFriendsCount) {
		this.privateFriendsCount = privateFriendsCount;
	}

	@Override
	public String toString() {
		return "UserCount [" + "id=" + id
				+ ", friends_count=" + friendsCount
				+ ", followers_count=" + followersCount
				+ ", statuses_count=" + statusesCount
				+ ", private_friends_count=" + privateFriendsCount + "]";
	}

}
