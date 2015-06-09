package cn.weibo;

import java.util.List;
import java.util.Map;

import cn.weibo.model.Favorites;
import cn.weibo.model.FavoritesIds;
import cn.weibo.model.FavoritesTag;
import cn.weibo.model.Paging;
import cn.weibo.model.PostParameter;
import cn.weibo.model.Tag;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.ArrayUtils;
import cn.weibo.util.WeiboConfig;

public class Favorite extends Weibo {

	private static final long serialVersionUID = 2298934944028795652L;

	public Favorite(String access_token) {
		this.access_token = access_token;
	}

	/*----------------------------收藏接口----------------------------------------*/
	/**
	 * 获取当前登录用户的收藏列表
	 * 
	 * @return list of the Status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/favorites
	 * @since JDK 1.5
	 */
	public List<Favorites> getFavorites() throws WeiboException {
		return Favorites.constructFavorites(client.get(
				WeiboConfig.getValue("baseURL") + "favorites.json",
				access_token));
	}

	/**
	 * 获取当前登录用户的收藏列表
	 * 
	 * @param page
	 * @return list of the Status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/favorites
	 * @since JDK 1.5
	 */
	public List<Favorites> getFavorites(Paging page) throws WeiboException {
		return Favorites.constructFavorites(client.get(
				WeiboConfig.getValue("baseURL") + "favorites.json", null, page,
				access_token));
	}

	/**
	 * 获取当前登录用户的收藏列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/favorites
	 * @since JDK 1.5
	 */
	public List<Favorites> getFavorites(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Favorites.constructFavorites(client.get(
				WeiboConfig.getValue("baseURL") + "favorites.json", parList,
				access_token));
	}

	/**
	 * 获取当前登录用户的收藏列表ID
	 * 
	 * @return list of the Status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/favorites
	 * @since JDK 1.5
	 */
	public List<FavoritesIds> getFavoritesIds() throws WeiboException {
		return FavoritesIds.constructFavoritesIds(client.get(
				WeiboConfig.getValue("baseURL") + "favorites/ids.json",
				access_token));
	}

	/**
	 * 获取当前登录用户的收藏列表ID
	 * 
	 * @return list of the Status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/favorites
	 * @since JDK 1.5
	 */
	public JSONObject getFavoritesIds(Paging page) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "favorites/ids.json", null,
				page, access_token).asJSONObject();
	}

	/**
	 * 获取当前登录用户的收藏列表ID
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/favorites/ids
	 * @since JDK 1.5
	 */
	public JSONObject getFavoritesIds(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL") + "favorites/ids.json", parList,
				access_token).asJSONObject();
	}

	/**
	 * 根据收藏ID获取指定的收藏信息
	 * 
	 * @param id
	 * @return Status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/favorites/show
	 * @since JDK 1.5
	 */
	public Favorites showFavorites(String id) throws WeiboException {
		return new Favorites(client.get(WeiboConfig.getValue("baseURL")
				+ "favorites/show.json",
				new PostParameter[] { new PostParameter("id", id) },
				access_token));
	}

	/**
	 * 根据标签获取当前登录用户该标签下的收藏列表
	 * 
	 * @param tid
	 * @return list of the favorite Status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/favorites/by_tags
	 * @since JDK 1.5
	 */
	public List<Favorites> getFavoritesByTags(String tid) throws WeiboException {
		return Favorites.constructFavorites(client.get(
				WeiboConfig.getValue("baseURL") + "favorites/by_tags.json",
				new PostParameter[] { new PostParameter("tid", tid) },
				access_token));
	}

	/**
	 * 根据标签获取当前登录用户该标签下的收藏列表
	 * 
	 * @param tid
	 * @param page
	 * @return list of the favorite Status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/show
	 * @since JDK 1.5
	 */
	public List<Favorites> getFavoritesByTags(String tid, Paging page)
			throws WeiboException {
		return Favorites.constructFavorites(client.get(
				WeiboConfig.getValue("baseURL") + "favorites/by_tags.json",
				new PostParameter[] { new PostParameter("tid", tid) }, page,
				access_token));
	}

	/**
	 * 根据标签获取当前登录用户该标签下的收藏列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/favorites/by_tags
	 * @since JDK 1.5
	 */
	public List<Favorites> getFavoritesByTags(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Favorites.constructFavorites(client.get(
				WeiboConfig.getValue("baseURL") + "favorites/by_tags.json", parList,
				access_token));
	}

	/**
	 * 获取当前登录用户的收藏标签列表
	 * 
	 * @param page
	 * @return list of the favorite tags
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/favorites/tags
	 * @since JDK 1.5
	 */
	public List<FavoritesTag> getFavoritesTags() throws WeiboException {
		return Tag.constructTag(client.get(WeiboConfig.getValue("baseURL")
				+ "favorites/tags.json", access_token));

	}

	/**
	 * 获取当前登录用户的收藏标签列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/favorites/tags
	 * @since JDK 1.5
	 */
	public List<FavoritesTag> getFavoritesTags(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Tag.constructTag(client.get(WeiboConfig.getValue("baseURL")
				+ "favorites/tags.json", parList, access_token));

	}

	/**
	 * 获取当前用户某个标签下的收藏列表的ID
	 * 
	 * @param tid
	 *            需要查询的标签ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/favorites/by_tags/ids
	 * @since JDK 1.5
	 */
	public List<FavoritesIds> getFavoritesIdsByTags(String tid)
			throws WeiboException {
		return FavoritesIds.constructFavoritesIds(client.get(
				WeiboConfig.getValue("baseURL") + "favorites/by_tags/ids.json",
				new PostParameter[] { new PostParameter("tid", tid) },
				access_token));
	}

	/**
	 * 获取当前用户某个标签下的收藏列表的ID
	 * 
	 * @param map
	 *            参数
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/favorites/by_tags/ids
	 * @since JDK 1.5
	 */
	public List<FavoritesIds> getFavoritesIdsByTags(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return FavoritesIds.constructFavoritesIds(client.get(
				WeiboConfig.getValue("baseURL") + "favorites/by_tags/ids.json",
				parList, access_token));
	}

	/**
	 * 添加一条微博到收藏里
	 * 
	 * @param 要收藏的微博ID
	 * @return Favorites status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/create
	 * @since JDK 1.5
	 */
	public Favorites createFavorites(String id) throws WeiboException {
		return new Favorites(client.post(WeiboConfig.getValue("baseURL")
				+ "favorites/create.json",
				new PostParameter[] { new PostParameter("id", id) },
				access_token));
	}

	/**
	 * 取消收藏一条微博
	 * 
	 * @param 要取消收藏的微博ID
	 * @return Favorites status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/destroy
	 * @since JDK 1.5
	 */
	public Favorites destroyFavorites(String id) throws WeiboException {
		return new Favorites(client.post(WeiboConfig.getValue("baseURL")
				+ "favorites/destroy.json",
				new PostParameter[] { new PostParameter("id", id) },
				access_token));
	}

	/**
	 * 批量删除收藏
	 * 
	 * @param ids
	 *            要取消收藏的收藏ID，用半角逗号分隔，最多不超过10个。
	 * @return destroy list of Favorites status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/destroy_batch
	 * @since JDK 1.5
	 */
	public Boolean destroyFavoritesBatch(String ids) throws WeiboException {
		try {
			return client
					.post(WeiboConfig.getValue("baseURL")
							+ "favorites/destroy_batch.json",
							new PostParameter[] { new PostParameter("ids", ids) },
							access_token).asJSONObject().getBoolean("result");
		} catch (JSONException e) {
			throw new WeiboException(e);
		}
	}

	/**
	 * 更新一条收藏的收藏标签
	 * 
	 * @param id
	 *            要需要更新的收藏ID
	 * @return update tag of Favorites status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/tags/update
	 * @since JDK 1.5
	 */
	public Favorites updateFavoritesTags(String id) throws WeiboException {
		return new Favorites(client.post(WeiboConfig.getValue("baseURL")
				+ "favorites/tags/update.json",
				new PostParameter[] { new PostParameter("id", id) },
				access_token));
	}

	/**
	 * 更新一条收藏的收藏标签
	 * 
	 * @param id
	 *            要需要更新的收藏ID
	 * @param tags
	 *            需要更新的标签内容，必须做URLencode，用半角逗号分隔，最多不超过2条。
	 * @return update tag of Favorites status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/tags/update
	 * @since JDK 1.5
	 */
	public Favorites updateFavoritesTags(String id, String tags)
			throws WeiboException {
		return new Favorites(client.post(WeiboConfig.getValue("baseURL")
				+ "favorites/tags/update.json", new PostParameter[] {
				new PostParameter("id", id), new PostParameter("tags", tags) },
				access_token));
	}

	/**
	 * 更新当前登录用户所有收藏下的指定标签
	 * 
	 * @param tid
	 *            需要更新的标签ID
	 * @param tag
	 *            需要更新的标签内容，必须做URLencode
	 * @return update tags of Favorites status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/tags/update_batch
	 * @since JDK 1.5
	 */
	public JSONObject updateFavoritesTagsBatch(String tid, String tag)
			throws WeiboException {
		return client.post(
				WeiboConfig.getValue("baseURL")
						+ "favorites/tags/update_batch.json",
				new PostParameter[] { new PostParameter("tid", tid),
						new PostParameter("tag", tag) }, access_token)
				.asJSONObject();
	}

	/**
	 * 删除当前登录用户所有收藏下的指定标签
	 * 
	 * @param id
	 *            需要删除的标签ID。。
	 * @return destroy tags of Favorites status
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/favorites/tags/destroy_batch
	 * @since JDK 1.5
	 */
	public Boolean destroyFavoritesTagsBatch(String ids) throws WeiboException {
		try {
			return client
					.post(WeiboConfig.getValue("baseURL")
							+ "favorites/destroy_batch.json",
							new PostParameter[] { new PostParameter("ids", ids) },
							access_token).asJSONObject().getBoolean("result");
		} catch (JSONException e) {
			throw new WeiboException(e);
		}
	}
}
