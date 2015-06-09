package cn.weibo;

import java.util.List;
import java.util.Map;

import cn.weibo.model.PostParameter;
import cn.weibo.model.Privacy;
import cn.weibo.model.RateLimitStatus;
import cn.weibo.model.School;
import cn.weibo.model.User;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.ArrayUtils;
import cn.weibo.util.WeiboConfig;

public class Account extends Weibo {

	private static final long serialVersionUID = 3816005087976772682L;

	public Account(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * OAuth授权之后，获取授权用户的UID
	 * 
	 * @return uid
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/account/get_uid
	 * @since JDK 1.5
	 */
	public JSONObject getUid() throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "account/get_uid.json",
				access_token).asJSONObject();
	}

	/**
	 * 获取当前登录用户的隐私设置
	 * 
	 * @param uid
	 * @return User's privacy
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/account/get_privacy
	 * @since JDK 1.5
	 */
	public Privacy getAccountPrivacy() throws WeiboException {
		return new Privacy(client.get(WeiboConfig.getValue("baseURL")
				+ "account/get_privacy.json", access_token));
	}

	/**
	 * 获取所有学校列表
	 * 
	 * @param province
	 *            学校名称关键字
	 * @return list of school
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/account/profile/school_list
	 * @since JDK 1.5
	 */
	public List<School> getAccountProfileSchoolList(String keyword)
			throws WeiboException {
		return School.constructSchool(client.get(
				WeiboConfig.getValue("baseURL")
						+ "account/profile/school_list.json",
				new PostParameter[] { new PostParameter("keyword", keyword) },
				access_token));
	}

	/**
	 * 获取所有的学校列表
	 * 
	 * @param province
	 *            省份范围，省份ID
	 * @param capital
	 *            学校首字母
	 * @return list of school
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/account/profile/school_list
	 * @since JDK 1.5
	 */
	public List<School> getAccountProfileSchoolList(String province,
			String capital) throws WeiboException {
		return School.constructSchool(client.get(
				WeiboConfig.getValue("baseURL")
						+ "account/profile/school_list.json",
				new PostParameter[] { new PostParameter("province", province),
						new PostParameter("capital", capital) }, access_token));
	}

	/**
	 * 获取所有的学校列表
	 * 
	 * @param map
	 *            参数列表
	 * @return list of school
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/account/profile/school_list
	 * @since JDK 1.5
	 */
	public List<School> getAccountProfileSchoolList(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return School
				.constructSchool(client.get(WeiboConfig.getValue("baseURL")
						+ "account/profile/school_list.json", parList, access_token));
	}

	/**
	 * 获取当前登录用户的API访问频率限制情况
	 * 
	 * @return rate limit
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/account/rate_limit_status
	 * @since JDK 1.5
	 */
	public RateLimitStatus getAccountRateLimitStatus() throws WeiboException {
		return new RateLimitStatus(client.get(WeiboConfig.getValue("baseURL")
				+ "account/rate_limit_status.json", access_token));
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/account/end_session
	 * @since JDK 1.5
	 */
	public User endSession() throws WeiboException {
		return new User(client.get(WeiboConfig.getValue("baseURL")
				+ "account/end_session.json", access_token));
	}
}
