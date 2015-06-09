package cn.weibo;

import java.util.List;
import java.util.Map;

import cn.weibo.http.ImageItem;
import cn.weibo.model.Places;
import cn.weibo.model.PoisitionCategory;
import cn.weibo.model.PostParameter;
import cn.weibo.model.Status;
import cn.weibo.model.StatusWapper;
import cn.weibo.model.User;
import cn.weibo.model.UserWapper;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.ArrayUtils;
import cn.weibo.util.WeiboConfig;

public class Place extends Weibo {

	private static final long serialVersionUID = 7603310952641351531L;

	public Place(String access_token) {
		this.access_token = access_token;
	}

	/**************** 动态读取 ************************/
	/**
	 * 获取当前登录用户与其好友的位置动态
	 * 
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/friends_timeline
	 * @since JDK 1.5
	 */
	public StatusWapper friendsTimeLine() throws WeiboException {
		return Status
				.constructWapperStatus(client.get(
						WeiboConfig.getValue("baseURL")
								+ "place/friends_timeline.json", access_token));
	}

	/**
	 * 获取当前登录用户与其好友的位置动态
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/friends_timeline
	 * @since JDK 1.5
	 */
	public StatusWapper friendsTimeLine(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Status
				.constructWapperStatus(client.get(
						WeiboConfig.getValue("baseURL")
								+ "place/friends_timeline.json", parList,
						access_token));
	}

	/**
	 * 获取某个用户的位置动态
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/user_timeline
	 * @since JDK 1.5
	 */
	public StatusWapper userTimeLine(String uid) throws WeiboException {
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/user_timeline.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取某个用户的位置动态
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/user_timeline.json
	 * @since JDK 1.5
	 */
	public StatusWapper userTimeLine(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/user_timeline.json",
				parList, access_token));
	}

	/**
	 * 获取某个位置地点的动态
	 * 
	 * @param poiid
	 *            需要查询的POI点ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/poi_timeline
	 * @since JDK 1.5
	 */
	public StatusWapper poisTimeLine(String poiid) throws WeiboException {
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/poi_timeline.json",
				new PostParameter[] { new PostParameter("poiid", poiid) },
				access_token));
	}

	/**
	 * 获取某个位置地点的动态
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/poi_timeline
	 * @since JDK 1.5
	 */
	public StatusWapper poisTimeLine(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/poi_timeline.json",
				parList, access_token));
	}

	/**
	 * 获取某个位置周边的动态
	 * 
	 * @param lat
	 *            纬度。有效范围：-90.0到+90.0，+表示北纬
	 * @param lon
	 *            经度。有效范围：-180.0到+180.0，+表示东经
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby_timeline
	 * @since JDK 1.5
	 */
	public StatusWapper nearbyTimeLine(String lat, String lon)
			throws WeiboException {
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/nearby_timeline.json",
				new PostParameter[] { new PostParameter("lat", lat),
						new PostParameter("long", lon) }, access_token));
	}

	/**
	 * 获取某个位置周边的动态
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby_timeline
	 * @since JDK 1.5
	 */
	public StatusWapper nearbyTimeLine(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/nearby_timeline.json",
				parList, access_token));
	}

	/**
	 * 根据ID获取动态的详情
	 * 
	 * @param id
	 *            需要获取的动态ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/statuses/show
	 * @since JDK 1.5
	 */
	public Status statusesShow(String id) throws WeiboException {
		return new Status(client.get(WeiboConfig.getValue("baseURL")
				+ "place/statuses/show.json",
				new PostParameter[] { new PostParameter("id", id) },
				access_token));
	}

	/**************** 用户读取 ************************/
	/**
	 * 获取LBS位置服务内的用户信息
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/users/show
	 * @since JDK 1.5
	 */
	public JSONObject userInfoInLBS(String uid) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "place/users/show.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token).asJSONObject();
	}

	/**
	 * 获取LBS位置服务内的用户信息
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @param base_app
	 *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/users/show
	 * @since JDK 1.5
	 */
	public JSONObject userInfoInLBS(String uid, int base_app)
			throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL") + "place/users/show.json",
						new PostParameter[] { new PostParameter("uid", uid),
								new PostParameter("base_app", base_app) },
						access_token).asJSONObject();
	}

	/**
	 * 获取用户签到过的地点列表
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/users/checkins
	 * @since JDK 1.5
	 */
	public List<Places> checkinsList(String uid) throws WeiboException {
		return Places.constructPlace(client.get(WeiboConfig.getValue("baseURL")
				+ "place/users/checkins.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户签到过的地点列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/users/checkins
	 * @since JDK 1.5
	 */
	public List<Places> checkinsList(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Places.constructPlace(client.get(WeiboConfig.getValue("baseURL")
				+ "place/users/checkins.json", parList, access_token));
	}

	/**
	 * 获取用户的照片列表
	 * 
	 * @param uid
	 *            需要查询的用户ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/users/photos
	 * @since JDK 1.5
	 */
	public StatusWapper userPhotoList(String uid) throws WeiboException {
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/users/photos.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token));
	}

	/**
	 * 获取用户的照片列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/users/photos
	 * @since JDK 1.5
	 */
	public StatusWapper userPhotoList(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/users/photos.json",
				parList, access_token));
	}

	/**
	 * 获取用户的点评列表（已废弃）
	 * 
	 * @param uid
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/users/tips
	 * @since JDK 1.5
	 */
	public JSONObject tipsList(String uid) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "place/users/tips.json",
				new PostParameter[] { new PostParameter("uid", uid) },
				access_token).asJSONObject();
	}

	/**************** 地点读取 ************************/
	/**
	 * 获取地点详情
	 * 
	 * @param poiid
	 *            需要查询的POI地点ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/show
	 * @since JDK 1.5
	 */
	public Places poisShow(String poiid) throws WeiboException {
		return new Places(client.get(WeiboConfig.getValue("baseURL")
				+ "place/pois/show.json",
				new PostParameter[] { new PostParameter("poiid", poiid) },
				access_token));
	}

	/**
	 * 获取地点详情
	 * 
	 * @param poiid
	 *            需要查询的POI地点ID
	 * @param base_app
	 *            是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/show
	 * @since JDK 1.5
	 */
	public List<Places> poisShow(String poiid, int base_app)
			throws WeiboException {
		return Places.constructPlace(client.get(WeiboConfig.getValue("baseURL")
				+ "place/pois/show.json", new PostParameter[] {
				new PostParameter("poiid", poiid),
				new PostParameter("base_app", base_app) }, access_token));
	}

	/**
	 * 获取在某个地点签到的人的列表
	 * 
	 * @param poiid
	 *            需要查询的POI地点ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/users
	 * @since JDK 1.5
	 */
	public UserWapper poisUsersList(String poiid) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "place/pois/users.json",
				new PostParameter[] { new PostParameter("poiid", poiid) },
				access_token));
	}

	/**
	 * 获取在某个地点签到的人的列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/users
	 * @since JDK 1.5
	 */
	public UserWapper poisUsersList(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "place/pois/users.json", parList,
				access_token));
	}

	/**
	 * 获取地点点评列表(已废弃)
	 * 
	 * @param poiid
	 *            需要查询的POI地点ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/tips
	 * @since JDK 1.5
	 */
	public User poisTipsList(String poiid) throws WeiboException {
		return new User(client.get(
				WeiboConfig.getValue("baseURL") + "place/pois/tips.json",
				new PostParameter[] { new PostParameter("poiid", poiid) },
				access_token).asJSONObject());
	}

	/**
	 * 获取地点照片列表
	 * 
	 * @param poiid
	 *            需要查询的POI地点ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/photos
	 * @since JDK 1.5
	 */
	public StatusWapper poisPhotoList(String poiid) throws WeiboException {
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/pois/photos.json",
				new PostParameter[] { new PostParameter("poiid", poiid) },
				access_token));
	}

	/**
	 * 获取地点照片列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/photos
	 * @since JDK 1.5
	 */
	public StatusWapper poisPhotoList(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/pois/photos.json", parList,
				access_token));
	}

	/**
	 * 按省市查询地点
	 * 
	 * @param keyword
	 *            查询的关键词，必须进行URLencode
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/search
	 * @since JDK 1.5
	 */
	public List<Places> poisSearch(String keyword) throws WeiboException {
		return Places.constructPlace(client.get(WeiboConfig.getValue("baseURL")
				+ "place/pois/search.json",
				new PostParameter[] { new PostParameter("keyword", keyword) },
				access_token));
	}

	/**
	 * 按省市查询地点
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/search
	 * @since JDK 1.5
	 */
	public List<Places> poisSearch(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Places.constructPlace(client.get(WeiboConfig.getValue("baseURL")
				+ "place/pois/search.json", parList, access_token));
	}

	/**
	 * 获取地点分类
	 * 
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/category
	 * @since JDK 1.5
	 */
	public List<PoisitionCategory> poisCategory() throws WeiboException {
		return PoisitionCategory.constructPoisCategory(client.get(
				WeiboConfig.getValue("baseURL") + "place/pois/category.json",
				access_token));
	}

	/**
	 * 获取地点分类
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/category
	 * @since JDK 1.5
	 */
	public List<PoisitionCategory> poisCategory(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return PoisitionCategory.constructPoisCategory(client.get(
				WeiboConfig.getValue("baseURL") + "place/pois/category.json",
				parList, access_token));
	}

	/**************** 附近读取 ************************/
	/**
	 * 获取附近地点
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            经度
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby/pois
	 * @since JDK 1.5
	 */
	public List<Places> nearbyPois(String lat, String lon)
			throws WeiboException {
		return Places.constructPlace(client.get(WeiboConfig.getValue("baseURL")
				+ "place/nearby/pois.json",
				new PostParameter[] { new PostParameter("lat", lat),
						new PostParameter("long", lon) }, access_token));
	}

	/**
	 * 获取附近地点
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby/pois
	 * @since JDK 1.5
	 */
	public List<Places> nearbyPois(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Places.constructPlace(client.get(WeiboConfig.getValue("baseURL")
				+ "place/nearby/pois.json", parList, access_token));
	}

	/**
	 * 获取附近发位置微博的人
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            经度
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby/users
	 * @since JDK 1.5
	 */
	public UserWapper nearbyUsers(String lat, String lon) throws WeiboException {
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "place/nearby/users.json",
				new PostParameter[] { new PostParameter("lat", lat),
						new PostParameter("long", lon) }, access_token));
	}

	/**
	 * 获取附近发位置微博的人
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby/users
	 * @since JDK 1.5
	 */
	public UserWapper nearbyUsers(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return User.constructWapperUsers(client.get(
				WeiboConfig.getValue("baseURL") + "place/nearby/users.json",
				parList, access_token));
	}

	/**
	 * 获取附近照片
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            经度
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby/photos
	 * @since JDK 1.5
	 */
	public StatusWapper nearbyPhoto(String lat, String lon)
			throws WeiboException {
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/nearby/photos.json",
				new PostParameter[] { new PostParameter("lat", lat),
						new PostParameter("long", lon) }, access_token));
	}

	/**
	 * 获取附近照片
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/nearby/photos
	 * @since JDK 1.5
	 */
	public StatusWapper nearbyPhoto(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Status.constructWapperStatus(client.get(
				WeiboConfig.getValue("baseURL") + "place/nearby/photos.json",
				parList, access_token));
	}

	/**************** 地点写入 ************************/
	/**
	 * 签到
	 * 
	 * @param poiid
	 *            需要签到的POI地点ID
	 * @param status
	 *            签到时发布的动态内容，必须做URLencode，内容不超过140个汉字
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_checkin
	 * @since JDK 1.5
	 */
	public Status addCheckin(String poiid, String status) throws WeiboException {
		return new Status(client.post(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_checkin.json", new PostParameter[] {
				new PostParameter("poiid", poiid),
				new PostParameter("status", status) }, access_token));
	}

	/**
	 * 签到同时可以上传一张图片
	 * 
	 * @param poiid
	 *            需要签到的POI地点ID
	 * @param status
	 *            签到时发布的动态内容，必须做URLencode，内容不超过140个汉字
	 * @param pic
	 *            需要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_checkin
	 * @since JDK 1.5
	 */
	public Status addCheckin(String poiid, String status, ImageItem item)
			throws WeiboException {
		return new Status(client.multPartURL(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_checkin.json", new PostParameter[] {
				new PostParameter("poiid", poiid),
				new PostParameter("status", status) }, item, access_token));
	}

	/**
	 * 签到同时可以上传一张图片
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_checkin
	 * @since JDK 1.5
	 */
	public Status addCheckin(Map<String, String> map) throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return new Status(client.post(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_checkin.json", parList, access_token));
	}

	/**
	 * 添加照片(有问题)
	 * 
	 * @param poiid
	 *            需要签到的POI地点ID
	 * @param status
	 *            签到时发布的动态内容，必须做URLencode，内容不超过140个汉字
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_photo
	 * @since JDK 1.5
	 */
	public Status addPhoto(String poiid, String status) throws WeiboException {
		return new Status(client.post(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_photo.json", new PostParameter[] {
				new PostParameter("poiid", poiid),
				new PostParameter("status", status) }, access_token));
	}

	/**
	 * 添加照片(有问题)
	 * 
	 * @param poiid
	 *            需要签到的POI地点ID
	 * @param status
	 *            签到时发布的动态内容，必须做URLencode，内容不超过140个汉字
	 * @param pic
	 *            需要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_checkin
	 * @since JDK 1.5
	 */
	public Status addPhoto(String poiid, String status, ImageItem item)
			throws WeiboException {
		return new Status(client.multPartURL(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_checkin.json", new PostParameter[] {
				new PostParameter("poiid", poiid),
				new PostParameter("status", status) }, item, access_token));
	}

	/**
	 * 添加照片(有问题)
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_checkin
	 * @since JDK 1.5
	 */
	public Status addPhoto(Map<String, String> map) throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return new Status(client.post(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_checkin.json", parList, access_token));
	}

	/**
	 * 添加点评
	 * 
	 * @param poiid
	 *            需要签到的POI地点ID
	 * @param status
	 *            签到时发布的动态内容，必须做URLencode，内容不超过140个汉字
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_tip
	 * @since JDK 1.5
	 */
	public Status addTip(String poiid, String status) throws WeiboException {
		return new Status(client.post(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_tip.json", new PostParameter[] {
				new PostParameter("poiid", poiid),
				new PostParameter("status", status) }, access_token));
	}

	/**
	 * 添加点评
	 * 
	 * @param poiid
	 *            需要签到的POI地点ID
	 * @param status
	 *            签到时发布的动态内容，必须做URLencode，内容不超过140个汉字
	 * @param pub
	 *            是否同步到微博，1：是、0：否，默认为0
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/place/pois/add_tip
	 * @since JDK 1.5
	 */
	public Status addTip(String poiid, String status, String pub)
			throws WeiboException {
		return new Status(client.post(WeiboConfig.getValue("baseURL")
				+ "place/pois/add_tip.json", new PostParameter[] {
				new PostParameter("poiid", poiid),
				new PostParameter("status", status),
				new PostParameter("pub", pub) }, access_token));
	}

}
