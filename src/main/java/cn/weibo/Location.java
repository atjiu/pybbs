package cn.weibo;

import java.util.List;
import java.util.Map;

import cn.weibo.model.Geos;
import cn.weibo.model.Poisition;
import cn.weibo.model.PostParameter;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.ArrayUtils;
import cn.weibo.util.WeiboConfig;

public class Location extends Weibo {

	private static final long serialVersionUID = -1725959237036370434L;

	public Location(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 生成一张静态的地图图片
	 * 
	 * @param centerCoordinate
	 *            中心点坐标，经度纬度用逗号分隔，与城市代码两者必选其一，中心点坐标优先
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/base/get_map_image
	 * @since JDK 1.5
	 */
	public JSONObject getMapImageByCenter(String centerCoordinate)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/base/get_map_image.json",
				new PostParameter[] { new PostParameter("center_coordinate",
						centerCoordinate) }, access_token).asJSONObject();
	}

	/**
	 * 生成一张静态的地图图片
	 * 
	 * @param city
	 *            城市代码，与中心点坐标两者必选其一，中心点坐标优先
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/base/get_map_image
	 * @since JDK 1.5
	 */
	public JSONObject getMapImageByCity(String city) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/base/get_map_image.json",
				new PostParameter[] { new PostParameter("city", city) },
				access_token).asJSONObject();
	}

	/**
	 * 生成一张静态的地图图片
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/base/get_map_image
	 * @since JDK 1.5
	 */
	public JSONObject getMapImage(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/base/get_map_image.json", parList, access_token)
				.asJSONObject();
	}

	/**
	 * 根据IP地址返回地理信息坐标
	 * 
	 * @param ip
	 *            需要获取坐标的IP地址，多个IP用逗号分隔，最多不超过10个
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/geo/ip_to_geo
	 * @since JDK 1.5
	 */
	public List<Geos> ipToGeo(String ip) throws WeiboException {
		return Geos.constructGeos(client.get(WeiboConfig.getValue("baseURL")
				+ "location/geo/ip_to_geo.json",
				new PostParameter[] { new PostParameter("ip", ip) },
				access_token));
	}

	/**
	 * 根据实际地址返回地理信息坐标
	 * 
	 * @param address
	 *            需要获取坐标的实际地址，必须进行URLencodes
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/geo/address_to_geo
	 * @since JDK 1.5
	 */
	public List<Geos> addressToGeo(String address) throws WeiboException {
		return Geos.constructGeos(client.get(WeiboConfig.getValue("baseURL")
				+ "location/geo/address_to_geo.json",
				new PostParameter[] { new PostParameter("address", address) },
				access_token));
	}

	/**
	 * 根据地理信息坐标返回实际地址
	 * 
	 * @param coordinate
	 *            需要获取实际地址的坐标，经度纬度用逗号分隔
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/geo/geo_to_address
	 * @since JDK 1.5
	 */
	public List<Geos> geoToAddress(String coordinate) throws WeiboException {
		return Geos.constructGeos(client.get(WeiboConfig.getValue("baseURL")
				+ "location/geo/geo_to_address.json",
				new PostParameter[] { new PostParameter("coordinate",
						coordinate) }, access_token));
	}

	/**
	 * 根据GPS坐标获取偏移后的坐标
	 * 
	 * @param coordinate
	 *            需要获取偏移坐标的源坐标，经度纬度用逗号分隔
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/geo/gps_to_offset
	 * @since JDK 1.5
	 */
	public JSONObject gpsToOffset(String coordinate) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/geo/gps_to_offset.json",
				new PostParameter[] { new PostParameter("coordinate",
						coordinate) }, access_token).asJSONObject();
	}

	/**
	 * 判断地理信息坐标是否是国内坐标
	 * 
	 * @param coordinates
	 *            需要判断的坐标，格式：经度,纬度,字符标识|经度,纬度,字符标识。其中经度纬度用逗号分隔，字符标识用于返回结果中的返回值标识
	 *            。“|”分隔多个坐标。一次最多50个坐标。示例：coordinates=120.035847163,
	 *            23.1014362572,g1|116.035847163,38.1014362572,g2
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/geo/is_domestic
	 * @since JDK 1.5
	 */
	public JSONObject isDomestic(String coordinates) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/geo/is_domestic.json",
				new PostParameter[] { new PostParameter("coordinates",
						coordinates) }, access_token).asJSONObject();
	}

	/**
	 * 根据关键词按地址位置获取POI点的信息
	 * 
	 * @param q
	 *            查询的关键词，必须进行URLencode
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_location
	 * @since JDK 1.5
	 */
	public List<Poisition> searchPoisByLocationByQ(String q)
			throws WeiboException {
		return Poisition
				.constructPois(client.get(WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_location.json",
						new PostParameter[] { new PostParameter("q", q) },
						access_token));
	}

	/**
	 * 根据分类代码按地址位置获取POI点的信息
	 * 
	 * @param category
	 *            查询的分类代码
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_location
	 * @since JDK 1.5
	 */
	public List<Poisition> searchPoisByLocationByCategory(String category)
			throws WeiboException {
		return Poisition
				.constructPois(client.get(WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_location.json",
						new PostParameter[] { new PostParameter("category",
								category) }, access_token));
	}

	/**
	 * 根据分类代码按地址位置获取POI点的信息
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_location
	 * @since JDK 1.5
	 */
	public List<Poisition> searchPoisByLocation(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Poisition.constructPois(client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_location.json", parList,
				access_token));
	}

	/**
	 * 根据关键词获取POI点的信息
	 * 
	 * @param q
	 *            查询的关键词，必须进行URLencode
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_geo
	 * @since JDK 1.5
	 */
	public JSONObject searchPoisByGeoByQ(String q) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_geo.json",
						new PostParameter[] { new PostParameter("q", q) },
						access_token).asJSONObject();
	}

	/**
	 * 根据中心点坐标查询周边poi
	 * 
	 * @param q
	 *            查询的关键词，必须进行URLencode
	 * @param coordinate
	 *            查询的中心点坐标，经度纬度用逗号分隔
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_geo
	 * @since JDK 1.5
	 */
	public JSONObject searchPoisByGeoByCoordinate(String q, String coordinate)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_geo.json",
				new PostParameter[] { new PostParameter("q", q),
						new PostParameter("coordinate", coordinate) },
				access_token).asJSONObject();
	}

	/**
	 * 根据中心点关键字查询周边poi
	 * 
	 * @param q
	 *            查询的关键词，必须进行URLencode
	 * @param cenname
	 *            中心点名称
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_geo
	 * @since JDK 1.5
	 */
	public JSONObject searchPoisByGeoByCenname(String q, String cenname)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_geo.json",
				new PostParameter[] { new PostParameter("q", q),
						new PostParameter("cenname", cenname) }, access_token)
				.asJSONObject();
	}

	/**
	 * 根据中心点关键字查询周边poi
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_geo
	 * @since JDK 1.5
	 */
	public JSONObject searchPoisByGeo(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_geo.json", parList, access_token)
				.asJSONObject();
	}

	/**
	 * 根据关键词按矩形区域获取POI点的信息
	 * 
	 * @param q
	 *            查询的关键词，必须进行URLencode
	 * @param coordinates
	 *            查询的矩形区域坐标，第一个坐标为左上角的点，第二个为右下角，经度纬度用逗号分隔，坐标间用“|”分隔
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_area
	 * @since JDK 1.5
	 */
	public List<Poisition> searchPoisByAreaByQ(String q, String coordinates)
			throws WeiboException {
		return Poisition.constructPois(client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_area.json",
				new PostParameter[] { new PostParameter("q", q),
						new PostParameter("coordinates", coordinates) },
				access_token));
	}

	/**
	 * 根据分类代码按矩形区域获取POI点的信息
	 * 
	 * @param category
	 *            查询的分类代码
	 * @param coordinates
	 *            查询的矩形区域坐标，第一个坐标为左上角的点，第二个为右下角，经度纬度用逗号分隔，坐标间用“|”分隔
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_area
	 * @since JDK 1.5
	 */
	public List<Poisition> searchPoisByAreaByCategory(String category,
			String coordinates) throws WeiboException {
		return Poisition.constructPois(client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_area.json",
				new PostParameter[] { new PostParameter("category", category),
						new PostParameter("coordinates", coordinates) },
				access_token));
	}

	/**
	 * 根据分类代码按矩形区域获取POI点的信息
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/search/by_area
	 * @since JDK 1.5
	 */
	public List<Poisition> searchPoisByAreaByCategory(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Poisition
				.constructPois(client.get(WeiboConfig.getValue("baseURL")
						+ "location/pois/search/by_area.json", parList, access_token));
	}

	/**
	 * 批量获取POI点的信息
	 * 
	 * @param srcids
	 *            需要获取POI的来源ID，是由用户通过add接口自己提交的，多个ID用逗号分隔，最多不超过5个
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/show_batch
	 * @since JDK 1.5
	 */
	public List<Poisition> showPoisBatch(String srcids) throws WeiboException {
		return Poisition.constructPois(client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/pois/show_batch.json",
				new PostParameter[] { new PostParameter("srcids", srcids) },
				access_token));
	}

	/**
	 * 提交一个新增的POI点信息
	 * 
	 * @param srcid
	 *            来源ID，用户自己设置，用于取回自己提交的POI信息，为2-8位的数字
	 * @param name
	 *            POI点的名称，不超过30个字符，UTF-8编码
	 * @param address
	 *            POI点的地址，不超过60个字符，UTF-8编码
	 * @param cityName
	 *            POI点的城市中文名称，不超过30个字符，UTF-8编码
	 * @param category
	 *            POI点的类别中文名称，不超过30个字符，UTF-8编码
	 * @param longitude
	 *            POI点的经度，2-15个字符，在180到-180之间
	 * @param latitude
	 *            POI点的维度，2-15个字符，在90到-90之间
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/add
	 * @since JDK 1.5
	 */
	public Poisition addPois(String srcid, String name, String address,
			String cityName, String category, String longitude, String latitude)
			throws WeiboException {
		return new Poisition(client.post(WeiboConfig.getValue("baseURL")
				+ "location/pois/add.json", new PostParameter[] {
				new PostParameter("srcid", srcid),
				new PostParameter("name", name),
				new PostParameter("address", address),
				new PostParameter("city_name", cityName),
				new PostParameter("category", category),
				new PostParameter("latitude", latitude),
				new PostParameter("longitude", longitude) }, access_token));
	}

	/**
	 * 提交一个新增的POI点信息
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/pois/add
	 * @since JDK 1.5
	 */
	public Poisition addPois(Map<String, String> map) throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return new Poisition(client.post(WeiboConfig.getValue("baseURL")
				+ "location/pois/add.json", parList, access_token));
	}

	/**
	 * 根据移动基站WIFI等数据获取当前位置信息
	 * 
	 * @param json
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/mobile/get_location
	 * @since JDK 1.5
	 */
	public JSONObject getLocation(String json) throws WeiboException {
		return client.post(
				WeiboConfig.getValue("baseURL")
						+ "location/mobile/get_location.json",
				new PostParameter[] { new PostParameter("json", json) },
				access_token).asJSONObject();
	}

	/**
	 * 根据起点与终点数据查询自驾车路线信息
	 * 
	 * @param beginPid
	 *            查询起点POI的ID，与begin_coordinate参数必选其一，begin_pid优先
	 * @param endPid
	 *            查询终点POI的ID，与end_coordinate参数必选其一，end_pid优先
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/drive_route
	 * @since JDK 1.5
	 */
	public JSONObject searchDriveRouteByPid(String beginPid, String endPid)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/line/drive_route.json",
				new PostParameter[] { new PostParameter("begin_pid", beginPid),
						new PostParameter("end_pid", endPid) }, access_token)
				.asJSONObject();
	}

	/**
	 * 根据起点与终点数据查询自驾车路线信息
	 * 
	 * @param beginCoordinate
	 *            查询起点的坐标，经度纬度用逗号分隔，与begin_pid参数必选其一，begin_pid优先
	 * @param endCoordinate
	 *            查询终点的坐标，经度纬度用逗号分隔，与end_pid参数必选其一，end_pid优先
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/drive_route
	 * @since JDK 1.5
	 */
	public JSONObject searchDriveRouteByCoordinate(String beginCoordinate,
			String endCoordinate) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/line/drive_route.json",
				new PostParameter[] {
						new PostParameter("begin_coordinate", beginCoordinate),
						new PostParameter("end_coordinate", endCoordinate) },
				access_token).asJSONObject();
	}

	/**
	 * 根据起点与终点数据查询自驾车路线信息
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/drive_route
	 * @since JDK 1.5
	 */
	public JSONObject searchDriveRoute(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/line/drive_route.json", parList, access_token)
				.asJSONObject();
	}

	/**
	 * 根据起点与终点数据查询公交乘坐路线信息
	 * 
	 * @param beingPid
	 *            查询起点POI的ID，与begin_coordinate参数必选其一，begin_pid优先
	 * @param endPid
	 *            查询终点POI的ID，与end_coordinate参数必选其一，end_pid优先
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/bus_route
	 * @since JDK 1.5
	 */
	public JSONObject searchBusRouteByPid(String beingPid, String endPid)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/line/bus_route.json",
				new PostParameter[] { new PostParameter("being_pid", beingPid),
						new PostParameter("end_pid", endPid) }, access_token)
				.asJSONObject();
	}

	/**
	 * 根据起点与终点数据查询公交乘坐路线信息
	 * 
	 * @param beginCoordinate
	 *            查询起点的坐标，经度纬度用逗号分隔，与begin_pid参数必选其一，begin_pid优先
	 * @param endCoordinate
	 *            查询终点的坐标，经度纬度用逗号分隔，与end_pid参数必选其一，end_pid优先
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/bus_route
	 * @since JDK 1.5
	 */
	public JSONObject searchBusRouteByCoordinate(String beginCoordinate,
			String endCoordinate) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/line/bus_route.json",
				new PostParameter[] {
						new PostParameter("begin_coordinate", beginCoordinate),
						new PostParameter("end_coordinate", endCoordinate) },
				access_token).asJSONObject();
	}

	/**
	 * 根据起点与终点数据查询公交乘坐路线信息
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/bus_route
	 * @since JDK 1.5
	 */
	public JSONObject searchBusRoute(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/line/bus_route.json", parList, access_token)
				.asJSONObject();
	}

	/**
	 * 根据关键词查询公交线路信息
	 * 
	 * @param q
	 *            查询的关键词，必须进行URLencode
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/bus_line
	 * @since JDK 1.5
	 */
	public JSONObject searchBusLine(String q) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "location/line/bus_line.json",
						new PostParameter[] { new PostParameter("q", q) },
						access_token).asJSONObject();
	}

	/**
	 * 根据关键词查询公交线路信息
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/bus_line
	 * @since JDK 1.5
	 */
	public JSONObject searchBusLine(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "location/line/bus_line.json", parList, access_token)
				.asJSONObject();
	}

	/**
	 * 根据关键词查询公交站点信息
	 * 
	 * @param q
	 *            查询的关键词，必须进行URLencode
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/bus_station
	 * @since JDK 1.5
	 */
	public JSONObject searchBusStation(String q) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "location/line/bus_station.json",
						new PostParameter[] { new PostParameter("q", q) },
						access_token).asJSONObject();
	}

	/**
	 * 根据关键词查询公交站点信息
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/location/line/bus_station
	 * @since JDK 1.5
	 */
	public JSONObject searchBusStation(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "location/line/bus_station.json", parList, access_token)
				.asJSONObject();
	}

}
