package cn.weibo;

import cn.weibo.model.Paging;
import cn.weibo.model.PostParameter;
import cn.weibo.model.Status;
import cn.weibo.model.StatusWapper;
import cn.weibo.model.User;
import cn.weibo.model.UserWapper;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.WeiboConfig;

public class Suggestion extends Weibo {

	private static final long serialVersionUID = 1861364044145921824L;

	public Suggestion(String access_token) {
		this.access_token = access_token;
	}

	// ---------------------------------推荐接口---------------------------------------------------
	/**
	 * 返回系统推荐的热门用户列表
	 * 
	 * @return list of the users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/suggestions/users/hot
	 * @since JDK 1.5
	 */

	public JSONArray suggestionsUsersHot() throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "suggestions/users/hot.json",
				access_token).asJSONArray();
	}

	public JSONArray suggestionsUsersHot(String category) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "suggestions/users/hot.json",
						new PostParameter[] { new PostParameter("category",
								category) }, access_token).asJSONArray();
	}

	/**
	 * 获取用户可能感兴趣的人
	 * 
	 * @return list of the user's id
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/suggestions/users/may_interested
	 * @since JDK 1.5
	 */
	public JSONArray suggestionsUsersMayInterested() throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "suggestions/users/may_interested.json", access_token)
				.asJSONArray();
	}

	public JSONArray suggestionsUsersMayInterested(int count, int page)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/users/may_interested.json",
				new PostParameter[] { new PostParameter("count", count),
						new PostParameter("page", page) }, access_token)
				.asJSONArray();
	}

	/**
	 * 根据一段微博正文推荐相关微博用户
	 * 
	 * @return list of the users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/suggestions/users/by_status
	 * @since JDK 1.5
	 */
	public UserWapper suggestionsUsersByStatus(String content)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/users/by_status.json",
				new PostParameter[] { new PostParameter("content", content) },
				access_token));
	}

	public UserWapper suggestionsUsersByStatus(String content, int num)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/users/by_status.json",
				new PostParameter[] { new PostParameter("content", content),
						new PostParameter("num", num) }, access_token));
	}

	/**
	 * 当前登录用户的friends_timeline微博按兴趣推荐排序
	 * 
	 * @param section
	 *            排序时间段，距现在n秒内的微博参加排序，最长支持24小时
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/suggestions/statuses/reorder
	 * @since JDK 1.5
	 */
	public StatusWapper suggestionsStatusesReorder(int section)
			throws WeiboException {
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/statuses/reorder.json",
				new PostParameter[] { new PostParameter("section", section) },
				access_token));
	}

	public StatusWapper suggestionsStatusesReorder(int section, int count,
			Paging page) throws WeiboException {
		return Status
				.constructWapperStatus(client.get(
						WeiboConfig.getValue("baseURL")
								+ "suggestions/statuses/reorder.json",
						new PostParameter[] {
								new PostParameter("section", section),
								new PostParameter("count", count) }, page,
						access_token));
	}

	/**
	 * 当前登录用户的friends_timeline微博按兴趣推荐排序的微博ID
	 * 
	 * @param section
	 *            排序时间段，距现在n秒内的微博参加排序，最长支持24小时
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/suggestions/statuses/reorder/ids
	 * @since JDK 1.5
	 */
	public JSONObject suggestionStatusesReorderIds(int section)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/statuses/reorder/ids.json",
				new PostParameter[] { new PostParameter("section", section) },
				access_token).asJSONObject();
	}

	public JSONObject suggestionStatusesReorderIds(int section, int count,
			Paging page) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "suggestions/statuses/reorder/ids.json",
						new PostParameter[] {
								new PostParameter("section", section),
								new PostParameter("count", count) }, page,
						access_token).asJSONObject();
	}

	/**
	 * 返回系统推荐的热门收藏
	 * 
	 * @return list of the status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/suggestions/favorites/hot
	 * @since JDK 1.5
	 */
	public JSONArray suggestionsFavoritesHot() throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/favorites/hot.json", access_token)
				.asJSONArray();
	}

	public JSONArray suggestionsFavoritesHot(int page, int count)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/favorites/hot.json",
				new PostParameter[] { new PostParameter("page", page),
						new PostParameter("count", count) }, access_token)
				.asJSONArray();
	}

	/**
	 * 把某人标识为不感兴趣的人
	 * 
	 * @return user
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/suggestions/users/not_interested
	 * @since JDK 1.5
	 */
	public User suggestionsUsersNotInterested(String uid)
			throws WeiboException {
		return new User(client.post(
				WeiboConfig.getValue("baseURL")
						+ "suggestions/users/not_interested.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token).asJSONObject());
	}
}
