package cn.weibo.model;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 获取隐私设置信息
 * 
 * @author xiaoV
 * 
 */
public class Privacy extends WeiboResponse {

	private static final long serialVersionUID = 8055620370528957274L;
	private long badge; // 勋章是否可见，0：不可见、1：可见
	private long comment; // 是否可以评论我的微博，0：所有人、1：关注的人、2：可信用户
	private long geo; // 是否开启地理信息，0：不开启、1：开启
	private long message; // 是否可以给我发私信，0：所有人、1：我关注的人、2：可信用户
	private long mobile; // 是否可以通过手机号码搜索到我，0：不可以、1：可以
	private long realname; // 是否可以通过真名搜索到我，0：不可以、1：可以
	private long profileUrlType;
	private long webim; // 是否开启webim， 0：不开启、1：开启

	public Privacy(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			badge = json.getLong("badge");
			comment = json.getLong("comment");
			geo = json.getLong("geo");
			message = json.getLong("message");
			mobile = json.getLong("mobile");
			realname = json.getLong("realname");
			profileUrlType = json.getLong("profileUrlType");
			webim = json.getLong("webim");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public Privacy(JSONObject json) throws WeiboException {
		try {
			badge = json.getLong("badge");
			comment = json.getLong("comment");
			geo = json.getLong("geo");
			message = json.getLong("message");
			mobile = json.getLong("mobile");
			realname = json.getLong("realname");
			profileUrlType = json.getLong("profileUrlType");
			webim = json.getLong("webim");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}

	public long getBadge() {
		return badge;
	}

	public void setBadge(long badge) {
		this.badge = badge;
	}

	public long getComment() {
		return comment;
	}

	public void setComment(long comment) {
		this.comment = comment;
	}

	public long getGeo() {
		return geo;
	}

	public void setGeo(long geo) {
		this.geo = geo;
	}

	public long getMessage() {
		return message;
	}

	public void setMessage(long message) {
		this.message = message;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public long getRealname() {
		return realname;
	}

	public void setRealname(long realname) {
		this.realname = realname;
	}

	public long getProfileUrlType() {
		return profileUrlType;
	}

	public void setProfileUrlType(long profileUrlType) {
		this.profileUrlType = profileUrlType;
	}

	public long getWebim() {
		return webim;
	}

	public void setWebim(long webim) {
		this.webim = webim;
	}

	@Override
	public String toString() {
		return "Privacy [badge=" + badge
				+ ", comment=" + comment
				+ ", geo=" + geo
				+ ", message=" + message
				+ ", mobile=" + mobile
				+ ", profileUrlType=" + profileUrlType
				+ ", webim=" + webim
				+ ", realname=" + realname + "]";
	}
}
