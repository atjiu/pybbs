package cn.weibo;

import cn.weibo.model.PostParameter;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.WeiboConfig;

public class PublicService extends Weibo {

	private static final long serialVersionUID = -2783541874923814897L;

	public PublicService(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 通过地址编码获取地址名称
	 * 
	 * @param codes
	 *            需要查询的地址编码，多个之间用逗号分隔
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/common/code_to_location
	 * @since JDK 1.5
	 */
	public JSONArray getLocationByCode(String codes) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL")
						+ "common/code_to_location.json",
				new PostParameter[] { new PostParameter("codes", codes) },
				access_token).asJSONArray();
	}

	/**
	 * 获取省份列表
	 * 
	 * @param country
	 *            国家的国家代码
	 * @param capital
	 *            省份的首字母，a-z，可为空代表返回全部，默认为全部
	 * @param language
	 *            返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/common/get_province
	 * @since JDK 1.5
	 */
	public JSONArray provinceList(String country) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "common/get_province.json",
				new PostParameter[] { new PostParameter("country", country) },
				access_token).asJSONArray();
	}

	public JSONArray provinceListOfCapital(String country, String capital)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "common/get_province.json",
				new PostParameter[] { new PostParameter("country", country),
						new PostParameter("capital", capital) }, access_token)
				.asJSONArray();
	}

	public JSONArray provinceList(String country, String language)
			throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "common/get_province.json",
						new PostParameter[] {
								new PostParameter("country", country),
								new PostParameter("language", language) },
						access_token).asJSONArray();
	}

	public JSONArray provinceList(String country, String capital,
			String language) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "common/get_province.json",
						new PostParameter[] {
								new PostParameter("country", country),
								new PostParameter("capital", capital),
								new PostParameter("language", language) },
						access_token).asJSONArray();
	}

	/**
	 * 获取城市列表
	 * 
	 * @param province
	 *            省份的省份代码
	 * @param capital
	 *            城市的首字母，a-z，可为空代表返回全部，默认为全部
	 * @param language
	 *            返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/common/get_city
	 * @since JDK 1.5
	 */
	public JSONArray cityList(String province) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL") + "common/get_city.json",
						new PostParameter[] { new PostParameter("province",
								province) }, access_token).asJSONArray();
	}

	public JSONArray cityListOfCapital(String province, String capital)
			throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "common/get_city.json",
				new PostParameter[] { new PostParameter("province", province),
						new PostParameter("capital", capital) }, access_token)
				.asJSONArray();
	}

	public JSONArray cityList(String province, String language)
			throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL") + "common/get_city.json",
						new PostParameter[] {
								new PostParameter("province", province),
								new PostParameter("language", language) },
						access_token).asJSONArray();
	}

	public JSONArray cityList(String province, String capital, String language)
			throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL") + "common/get_city.json",
						new PostParameter[] {
								new PostParameter("province", province),
								new PostParameter("capital", capital),
								new PostParameter("language", language) },
						access_token).asJSONArray();
	}

	/**
	 * 获取国家列表
	 * 
	 * @param capital
	 *            国家的首字母，a-z，可为空代表返回全部，默认为全部
	 * @param language
	 *            返回的语言版本，zh-cn：简体中文、zh-tw：繁体中文、english：英文，默认为zh-cn
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/common/get_country
	 * @since JDK 1.5
	 */
	public JSONArray countryList() throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "common/get_country.json",
				access_token).asJSONArray();
	}

	public JSONArray countryListOfCapital(String capital) throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "common/get_country.json",
				new PostParameter[] { new PostParameter("capital", capital) },
				access_token).asJSONArray();
	}

	public JSONArray countryList(String language) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "common/get_country.json",
						new PostParameter[] { new PostParameter("language",
								language) }, access_token).asJSONArray();
	}

	public JSONArray countryList(String capital, String language)
			throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "common/get_country.json",
						new PostParameter[] {
								new PostParameter("capital", capital),
								new PostParameter("language", language) },
						access_token).asJSONArray();
	}

	/**
	 * 获取时区配置表
	 * 
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/common/get_timezone
	 * @since JDK 1.5
	 */
	public JSONObject getTomeZone() throws WeiboException {
		return client.get(
				WeiboConfig.getValue("baseURL") + "common/get_timezone.json",
				access_token).asJSONObject();
	}

}
