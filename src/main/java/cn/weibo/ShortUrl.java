package cn.weibo;

import java.util.Map;

import cn.weibo.model.PostParameter;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.ArrayUtils;
import cn.weibo.util.WeiboConfig;

public class ShortUrl extends Weibo {

	private static final long serialVersionUID = -1312280626965611916L;

	public ShortUrl(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 将一个或多个长链接转换成短链接
	 * 
	 * @param url_long
	 *            需要转换的长链接，需要URLencoded，最多不超过20个
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/shorten
	 * @since JDK 1.5
	 */
	public JSONObject longToShortUrl(String url_long) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL") + "short_url/shorten.json",
						new PostParameter[] { new PostParameter("url_long",
								url_long) }, access_token).asJSONObject();
	}

	/**
	 * 将一个或多个短链接还原成原始的长链接
	 * 
	 * @param url_short
	 *            需要还原的短链接，需要URLencoded，最多不超过20个
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/expand
	 * @since JDK 1.5
	 */
	public JSONObject shortToLongUrl(String url_short) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL") + "short_url/expand.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取短链接的总点击数
	 * 
	 * @param url_short
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/clicks
	 * @since JDK 1.5
	 */
	public JSONObject clicksOfUrl(String url_short) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL") + "short_url/clicks.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取一个短链接点击的referer来源和数量
	 * 
	 * @param url_short
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/referers
	 * @since JDK 1.5
	 */
	public JSONObject referersOfUrl(String url_short) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "short_url/referers.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取一个短链接点击的地区来源和数量
	 * 
	 * @param url_short
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/locations
	 * @since JDK 1.5
	 */
	public JSONObject locationsOfUrl(String url_short) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "short_url/locations.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取短链接在微博上的微博分享数
	 * 
	 * @param url_short
	 *            需要取得分享数的短链接，需要URLencoded，最多不超过20个
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/share/counts
	 * @since JDK 1.5
	 */
	public JSONObject shareCountsOfUrl(String url_short) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "short_url/share/counts.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取包含指定单个短链接的最新微博内容
	 * 
	 * @param url_short
	 *            需要取得关联微博内容的短链接，需要URLencoded
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/share/statuses
	 * @since JDK 1.5
	 */
	public JSONObject statusesContentUrl(String url_short)
			throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "short_url/share/statuses.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取包含指定单个短链接的最新微博内容
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/share/statuses
	 * @since JDK 1.5
	 */
	public JSONObject statusesContentUrl(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "short_url/share/statuses.json", parList, access_token)
				.asJSONObject();
	}

	/**
	 * 获取短链接在微博上的微博评论数
	 * 
	 * @param url_short
	 *            需要取得分享数的短链接，需要URLencoded，最多不超过20个
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/comment/counts
	 * @since JDK 1.5
	 */
	public JSONObject commentCountOfUrl(String url_short) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "short_url/comment/counts.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取包含指定单个短链接的最新微博评论
	 * 
	 * @param url_short
	 *            需要取得关联微博评论内容的短链接，需要URLencoded
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/comment/comments
	 * @since JDK 1.5
	 */
	public JSONObject commentsContentUrl(String url_short)
			throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "short_url/comment/comments.json",
						new PostParameter[] { new PostParameter("url_short",
								url_short) }, access_token).asJSONObject();
	}

	/**
	 * 获取包含指定单个短链接的最新微博评论
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/short_url/comment/comments
	 * @since JDK 1.5
	 */
	public JSONObject commentsContentUrl(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "short_url/comment/comments.json", parList, access_token)
				.asJSONObject();
	}

}
