package cn.weibo;

import java.util.List;

import cn.weibo.model.Paging;
import cn.weibo.model.PostParameter;
import cn.weibo.model.Trends;
import cn.weibo.model.UserTrend;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.WeiboConfig;

public class Trend extends Weibo {

	private static final long serialVersionUID = 903299515334415487L;

	public Trend(String access_token) {
		this.access_token = access_token;
	}

	/*----------------------------话题接口----------------------------------------*/
	/**
	 * 获取某人的话题列表
	 * 
	 * @param uid
	 *            需要获取话题的用户的UID
	 * @return list of the userTrend
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/trends
	 * @since JDK 1.5
	 */
	public List<UserTrend> getTrends(String uid) throws WeiboException {
		return UserTrend.constructTrendList(client.get(
				WeiboConfig.getValue("baseURL") + "trends.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取某人的话题列表
	 * 
	 * @param uid
	 *            需要获取话题的用户的UID
	 * @param page
	 *            返回结果的页码，默认为1
	 * @return list of the userTrend
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/trends
	 * @since JDK 1.5
	 */
	public List<UserTrend> getTrends(String uid, Paging page)
			throws WeiboException {
		return UserTrend.constructTrendList(client.get(
				WeiboConfig.getValue("baseURL") + "trends.json",
				new PostParameter[] { new PostParameter("uid", uid) }, page,
				access_token));
	}

	/**
	 * 判断当前用户是否关注某话题
	 * 
	 * @param trend_name
	 *            话题关键字，必须做URLencode
	 * @return jsonobject
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @throws JSONException
	 * @see http://open.weibo.com/wiki/2/trends/is_follow
	 * @since JDK 1.5
	 */
	public JSONObject isFollow(String trend_name) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "trends/is_follow.json",
				new PostParameter[] { new PostParameter("trend_name",
						trend_name) }, access_token).asJSONObject();
	}

	/**
	 * 返回最近一小时内的热门话题
	 * 
	 * @param base_app
	 *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
	 * @return list of trends
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @throws JSONException
	 * @see http://open.weibo.com/wiki/2/trends/hourly
	 * @since JDK 1.5
	 */
	public List<Trends> getTrendsHourly() throws WeiboException {
		return Trends.constructTrendsList(client.get(
				WeiboConfig.getValue("baseURL") + "trends/hourly.json",
				access_token));
	}

	public List<Trends> getTrendsHourly(Integer base_app) throws WeiboException {
		return Trends.constructTrendsList(client.get(
				WeiboConfig.getValue("baseURL") + "trends/hourly.json",
				new PostParameter[] { new PostParameter("base_app", base_app
						.toString()) }, access_token));
	}

	/**
	 * 返回最近一天内的热门话题
	 * 
	 * @param base_app
	 *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
	 * @return list of trends
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @throws JSONException
	 * @see http://open.weibo.com/wiki/2/trends/daily
	 * @since JDK 1.5
	 */
	public List<Trends> getTrendsDaily() throws WeiboException {
		return Trends.constructTrendsList(client.get(
				WeiboConfig.getValue("baseURL") + "trends/daily.json",
				access_token));
	}

	public List<Trends> getTrendsDaily(Integer base_app) throws WeiboException {
		return Trends.constructTrendsList(client.get(
				WeiboConfig.getValue("baseURL") + "trends/daily.json",
				new PostParameter[] { new PostParameter("base_app", base_app
						.toString()) }, access_token));
	}

	/**
	 * 返回最近一周内的热门话题
	 * 
	 * @param base_app
	 *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
	 * @return list of trends
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @throws JSONException
	 * @see http://open.weibo.com/wiki/2/trends/weekly
	 * @since JDK 1.5
	 */
	public List<Trends> getTrendsWeekly() throws WeiboException {
		return Trends.constructTrendsList(client.get(
				WeiboConfig.getValue("baseURL") + "trends/weekly.json",
				access_token));
	}

	public List<Trends> getTrendsWeekly(Integer base_app) throws WeiboException {
		return Trends.constructTrendsList(client.get(
				WeiboConfig.getValue("baseURL") + "trends/weekly.json",
				new PostParameter[] { new PostParameter("base_app", base_app
						.toString()) }, access_token));
	}

	/**
	 * 关注某话题
	 * 
	 * @param trend_name
	 *            要关注的话题关键词。
	 * @return UserTrend
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @throws JSONException
	 * @see http://open.weibo.com/wiki/2/trends/follow
	 * @since JDK 1.5
	 */
	public UserTrend trendsFollow(String trend_name) throws WeiboException {
		return new UserTrend(client.post(WeiboConfig.getValue("baseURL")
				+ "trends/follow.json",
				new PostParameter[] { new PostParameter("trend_name",
						trend_name) }, access_token));
	}

	/**
	 * 取消对某话题的关注
	 * 
	 * @param trend_id
	 *            要取消关注的话题ID
	 * @return jsonobject
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @throws JSONException
	 * @see http://open.weibo.com/wiki/2/trends/destroy
	 * @since JDK 1.5
	 */
	public JSONObject trendsDestroy(Integer trend_id) throws WeiboException {
		return client.post(
				WeiboConfig.getValue("baseURL") + "trends/destroy.json",
				new PostParameter[] { new PostParameter("trend_id", trend_id
						.toString()) }, access_token).asJSONObject();
	}

}
