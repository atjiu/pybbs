package cn.weibo;

import java.util.Map;

import cn.weibo.model.Paging;
import cn.weibo.model.PostParameter;
import cn.weibo.model.User;
import cn.weibo.model.UserWapper;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.ArrayUtils;
import cn.weibo.util.WeiboConfig;

public class Friendships extends Weibo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3603512821159421447L;

	public Friendships(String access_token) {
		this.access_token = access_token;
	}

	/*----------------------------关系接口----------------------------------------*/
	/**
	 * 获取用户的关注列表
	 * 
	 * @return list of the user's follow
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsByID(String id) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/friends.json",
				new PostParameter[] { new PostParameter("uid", id) },
				access_token));
	}

	/**
	 * 获取用户的关注列表
	 * 
	 * @return list of the user's follow
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsByScreenName(String screen_name)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/friends.json",
				new PostParameter[] { new PostParameter("screen_name",
						screen_name) }, access_token));
	}

	/**
	 * 获取用户的关注列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/friends
	 * @since JDK 1.5
	 */
	public UserWapper getFriends(Map<String, String> map) throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/friends.json",
				parList, access_token));
	}

	/**
	 * 获取两个用户之间的共同关注人列表
	 * 
	 * @param uid
	 *            需要获取共同关注关系的用户UID
	 * @return list of the user's follow
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/in_common
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsInCommon(String uid) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends/in_common.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取两个用户之间的共同关注人列表
	 * 
	 * @param uid
	 *            需要获取共同关注关系的用户UID
	 * @param suid
	 *            需要获取共同关注关系的用户UID，默认为当前登录用户
	 * @param count
	 *            单页返回的记录条数，默认为50
	 * @param page
	 *            返回结果的页码，默认为1
	 * @return list of the user's follow
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/in_common
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsInCommon(String uid, String suid, Paging page)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends/in_common.json",
				new PostParameter[] { new PostParameter("uid", uid),
						new PostParameter("suid", suid) }, page, access_token));
	}

	/**
	 * 获取两个用户之间的共同关注人列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/friends/in_common
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsInCommon(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends/in_common.json", parList,
				access_token));
	}

	/**
	 * 获取用户的双向关注列表，即互粉列表
	 * 
	 * @param uid
	 *            需要获取双向关注列表的用户UID
	 * @return list of the user
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsBilateral(String uid) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends/bilateral.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户的双向关注列表，即互粉列表
	 * 
	 * @param uid
	 *            需要获取双向关注列表的用户UID
	 * @param count
	 *            单页返回的记录条数，默认为50。
	 * @param page
	 *            返回结果的页码，默认为1。
	 * @param sort
	 *            排序类型，0：按关注时间最近排序，默认为0。
	 * @return list of the user
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsBilateral(String uid, Integer sort, Paging page)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends/bilateral.json",
				new PostParameter[] { new PostParameter("uid", uid),
						new PostParameter("sort", sort.toString()) }, page,
				access_token));
	}

	/**
	 * 获取用户的双向关注列表，即互粉列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsBilateral(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends/bilateral.json", parList,
				access_token));
	}

	/**
	 * 获取用户双向关注的用户ID列表，即互粉UID列表
	 * 
	 * @param uid
	 *            需要获取双向关注列表的用户UID
	 * @return ids
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsBilateralIds(String uid) throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/friends/bilateral/ids.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户双向关注的用户ID列表，即互粉UID列表
	 * 
	 * @param uid
	 *            需要获取双向关注列表的用户UID
	 * @param count
	 *            单页返回的记录条数，默认为50。
	 * @param page
	 *            返回结果的页码，默认为1。
	 * @param sort
	 *            排序类型，0：按关注时间最近排序，默认为0。
	 * @return ids
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsBilateralIds(String uid, Integer sort, Paging page)
			throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/friends/bilateral/ids.json",
				new PostParameter[] { new PostParameter("uid", uid),
						new PostParameter("sort", sort.toString()) }, page,
				access_token));
	}

	/**
	 * 获取用户双向关注的用户ID列表，即互粉UID列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/friends/bilateral/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsBilateralIds(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/friends/bilateral/ids.json", parList, access_token));
	}

	/**
	 * 获取用户关注的用户UID列表
	 * 
	 * @param uid
	 *            需要查询的用户UID
	 * @return ids
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsIdsByUid(String uid) throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/friends/ids.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户关注的用户UID列表
	 * 
	 * @param uid
	 *            需要查询的用户UID
	 * @return ids
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsIdsByName(String screen_name)
			throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/friends/ids.json",
				new PostParameter[] { new PostParameter("screen_name",
						screen_name) }, access_token));
	}

	/**
	 * 获取用户关注的用户UID列表
	 * 
	 * @param uid
	 *            需要查询的用户UID
	 * @param count
	 *            单页返回的记录条数，默认为500，最大不超过5000
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * @return ids
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsIdsByUid(String uid, Integer count, Integer cursor)
			throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/friends/ids.json",
				new PostParameter[] { new PostParameter("uid", uid),
						new PostParameter("count", count.toString()),
						new PostParameter("cursor", cursor.toString()) },
				access_token));
	}

	/**
	 * 获取用户关注的用户UID列表
	 * 
	 * @param screen_name
	 *            需要查询的用户昵称
	 * @param count
	 *            单页返回的记录条数，默认为500，最大不超过5000
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * @return ids
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsIdsByName(String screen_name, Integer count,
			Integer cursor) throws WeiboException {
		return User
				.constructIds(client.get(WeiboConfig.getValue("baseURL")
						+ "friendships/friends/ids.json", new PostParameter[] {
						new PostParameter("screen_name", screen_name),
						new PostParameter("count", count.toString()),
						new PostParameter("cursor", cursor.toString()) },
						access_token));
	}

	/**
	 * 获取用户关注的用户UID列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/friends/ids
	 * @since JDK 1.5
	 */
	public String[] getFriendsIds(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/friends/ids.json", parList, access_token));
	}

	/**
	 * 批量获取当前登录用户的关注人的备注信息
	 * 
	 * @param uids
	 *            需要获取备注的用户UID，用半角逗号分隔，最多不超过50个
	 * @return list of user's remark
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends/remark_batch
	 * @since JDK 1.5
	 */
	public JSONArray getRemark(String uids) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends/remark_batch.json",
				new PostParameter[] { new PostParameter("uids", uids) },
				access_token).asJSONArray();
	}

	/**
	 * 获取用户的粉丝列表
	 * 
	 * @param screen_name
	 *            需要查询的用户昵称
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers
	 * @since JDK 1.5
	 */
	public UserWapper getFollowersByName(String screen_name)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/followers.json",
				new PostParameter[] { new PostParameter("screen_name",
						screen_name) }, access_token));
	}

	/**
	 * 获取用户的粉丝列表
	 * 
	 * @param screen_name
	 *            需要查询的用户昵称
	 * @param count
	 *            单页返回的记录条数，默认为500，最大不超过5000
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers
	 * @since JDK 1.5
	 */
	public UserWapper getFollowersByName(String screen_name, Integer count,
			Integer cursor) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/followers.json",
				new PostParameter[] {
						new PostParameter("screen_name", screen_name),
						new PostParameter("count", count.toString()),
						new PostParameter("cursor", cursor.toString()) },
				access_token));
	}

	/**
	 * 获取用户的粉丝列表
	 * 
	 * @param screen_name
	 *            需要查询的用户昵称
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers
	 * @since JDK 1.5
	 */
	public UserWapper getFollowersById(String uid) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/followers.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户的粉丝列表
	 * 
	 * @param screen_name
	 *            需要查询的用户昵称
	 * @param count
	 *            单页返回的记录条数，默认为500，最大不超过5000
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers
	 * @since JDK 1.5
	 */
	public UserWapper getFollowersById(String uid, Integer count, Integer cursor)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/followers.json",
				new PostParameter[] { new PostParameter("uid", uid),
						new PostParameter("count", count.toString()),
						new PostParameter("cursor", cursor.toString()) },
				access_token));
	}

	/**
	 * 获取用户的粉丝列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/followers
	 * @since JDK 1.5
	 */
	public UserWapper getFollowers(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "friendships/followers.json",
				parList, access_token));
	}

	/**
	 * 获取用户粉丝的用户UID列表
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers/ids
	 * @since JDK 1.5
	 */
	public String[] getFollowersIdsById(String uid) throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/followers/ids.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户粉丝的用户UID列表
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @param count
	 *            单页返回的记录条数，默认为500，最大不超过5000
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers/ids
	 * @since JDK 1.5
	 */
	public String[] getFollowersIdsById(String uid, Integer count,
			Integer cursor) throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/followers/ids.json",
				new PostParameter[] { new PostParameter("uid", uid),
						new PostParameter("count", count.toString()),
						new PostParameter("cursor", cursor.toString()) },
				access_token));
	}

	/**
	 * 获取用户粉丝的用户UID列表
	 * 
	 * @param screen_name
	 *            需要查询的用户昵称
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers/ids
	 * @since JDK 1.5
	 */
	public String[] getFollowersIdsByName(String screen_name)
			throws WeiboException {
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/followers/ids.json",
				new PostParameter[] { new PostParameter("screen_name",
						screen_name) }, access_token));
	}

	/**
	 * 获取用户粉丝的用户UID列表
	 * 
	 * @param screen_name
	 *            需要查询的用户ID
	 * @param count
	 *            单页返回的记录条数，默认为500，最大不超过5000
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers/ids
	 * @since JDK 1.5
	 */
	public String[] getFollowersIdsByName(String screen_name, Integer count,
			Integer cursor) throws WeiboException {
		return User
				.constructIds(client.get(
						WeiboConfig.getValue("baseURL")
								+ "friendships/followers/ids.json",
						new PostParameter[] {
								new PostParameter("screen_name", screen_name),
								new PostParameter("count", count.toString()),
								new PostParameter("cursor", cursor.toString()) },
						access_token));
	}

	/**
	 * 获取用户粉丝的用户UID列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/followers/ids
	 * @since JDK 1.5
	 */
	public String[] getFollowersIdsByName(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructIds(client.get(WeiboConfig.getValue("baseURL")
				+ "friendships/followers/ids.json", parList, access_token));
	}

	/**
	 * 获取用户的活跃粉丝列表
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return list of user's id
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers/active
	 * @since JDK 1.5
	 */
	public UserWapper getFollowersActive(String uid) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/followers/active.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户的活跃粉丝列表
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @param count
	 *            返回的记录条数，默认为20，最大不超过200。
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/followers/active
	 * @since JDK 1.5
	 */
	public UserWapper getFollowersActive(String uid, Integer count)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/followers/active.json",
				new PostParameter[] { new PostParameter("uid", uid),
						new PostParameter("count", count.toString()) },
				access_token));
	}

	/**
	 * 获取当前登录用户的关注人中又关注了指定用户的用户列表
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return list of users
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/friends_chain/followers
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsChainFollowers(String uid)
			throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends_chain/followers.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取当前登录用户的关注人中又关注了指定用户的用户列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/friends_chain/followers
	 * @since JDK 1.5
	 */
	public UserWapper getFriendsChainFollowers(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL")
						+ "friendships/friends_chain/followers.json", parList,
				access_token));
	}

	/**
	 * 获取两个用户之间的详细关注关系情况
	 * 
	 * @param source
	 *            源用户的UID
	 * @param target
	 *            目标用户的UID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/show
	 * @since JDK 1.5
	 */
	public JSONObject getFriendshipsById(long source, long target)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "friendships/show.json",
				new PostParameter[] { new PostParameter("source_id", source),
						new PostParameter("target_id", target) }, access_token)
				.asJSONObject();
	}

	/**
	 * 获取两个用户之间的详细关注关系情况
	 * 
	 * @param source
	 *            源用户的微博昵称
	 * @param target
	 *            目标用户的微博昵称
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/show
	 * @since JDK 1.5
	 */
	public JSONObject getFriendshipsByName(String source, String target)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "friendships/show.json",
				new PostParameter[] {
						new PostParameter("source_screen_name", source),
						new PostParameter("target_screen_name", target) },
				access_token).asJSONObject();
	}

	/**
	 * 获取两个用户之间的详细关注关系情况
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/show
	 * @since JDK 1.5
	 */
	public JSONObject getFriendships(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL") + "friendships/show.json", parList,
				access_token).asJSONObject();
	}

	/**
	 * 关注一个用户
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return user
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/create
	 * @since JDK 1.5
	 */
	public User createFriendshipsById(String uid) throws WeiboException {
		return new User(client.post(
				WeiboConfig.getValue("baseURL") + "friendships/create.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token).asJSONObject());
	}

	/**
	 * 关注一个用户
	 * 
	 * @param screen_name
	 *            需要查询的用户screen_name
	 * @return user
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/create
	 * @since JDK 1.5
	 */
	public User createFriendshipsByName(String screen_name)
			throws WeiboException {
		return new User(client.post(
				WeiboConfig.getValue("baseURL") + "friendships/create.json",
				new PostParameter[] { new PostParameter("screen_name",
						screen_name) }, access_token).asJSONObject());
	}

	/**
	 * 关注一个用户
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/friendships/create
	 * @since JDK 1.5
	 */
	public User createFriendships(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return new User(client.post(
				WeiboConfig.getValue("baseURL") + "friendships/create.json",
				parList, access_token).asJSONObject());
	}

	/**
	 * 取消关注一个用户
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return user
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/destroy
	 * @since JDK 1.5
	 */
	public User destroyFriendshipsById(String uid) throws WeiboException {
		return new User(client.post(
				WeiboConfig.getValue("baseURL") + "friendships/destroy.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token).asJSONObject());
	}

	/**
	 * 取消关注一个用户
	 * 
	 * @param screen_name
	 *            需要查询的用户screen_name
	 * @return user
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.0
	 * @see http://open.weibo.com/wiki/2/friendships/destroy
	 * @since JDK 1.5
	 */
	public User destroyFriendshipsByName(String screen_name)
			throws WeiboException {
		return new User(client.post(
				WeiboConfig.getValue("baseURL") + "friendships/destroy.json",
				new PostParameter[] { new PostParameter("screen_name",
						screen_name) }, access_token).asJSONObject());
	}
}
