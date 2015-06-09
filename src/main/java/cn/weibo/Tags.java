package cn.weibo;

import java.util.List;

import cn.weibo.model.Paging;
import cn.weibo.model.PostParameter;
import cn.weibo.model.Tag;
import cn.weibo.model.TagWapper;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.WeiboConfig;

public class Tags extends Weibo {

	private static final long serialVersionUID = 7047254100483792467L;

	public Tags(String access_token) {
		this.access_token = access_token;
	}

	/*----------------------------标签接口----------------------------------------*/
	/**
	 * 返回指定用户的标签列表
	 * 
	 * @param uid
	 *            要获取的标签列表所属的用户ID
	 * @return list of the tags
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/tags
	 * @since JDK 1.5
	 */
	public List<Tag> getTags(String uid) throws WeiboException {
		return Tag.constructTags(client.get(WeiboConfig.getValue("baseURL")
				+ "tags.json", new PostParameter[] { new PostParameter("uid",
				uid) }, access_token));
	}

	/**
	 * 返回指定用户的标签列表
	 * 
	 * @param uid
	 *            要获取的标签列表所属的用户ID
	 * @param page
	 *            返回结果的页码，默认为1
	 * @return list of the tags
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/tags
	 * @since JDK 1.5
	 */
	public List<Tag> getTags(String uid, int count, Paging page)
			throws WeiboException {
		return Tag.constructTags(client.get(WeiboConfig.getValue("baseURL")
				+ "tags.json", new PostParameter[] {
				new PostParameter("uid", uid),
				new PostParameter("count", count) }, page, access_token));
	}

	/**
	 * 批量获取用户的标签列表
	 * 
	 * @param uids
	 *            要获取标签的用户ID。最大20，逗号分隔
	 * @return list of the tags
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/tags/tags_batch
	 * @since JDK 1.5
	 */
	public TagWapper getTagsBatch(String uids) throws WeiboException {
		return Tag.constructTagWapper(client.get(
				WeiboConfig.getValue("baseURL") + "tags/tags_batch.json",
				new PostParameter[] { new PostParameter("uids", uids) },
				access_token));
	}

	/**
	 * 获取系统推荐的标签列表
	 * 
	 * @return list of the tags
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/tags/suggestions
	 * @since JDK 1.5
	 */

	public List<Tag> getTagsSuggestions() throws WeiboException {
		return Tag.constructTags(client.get(WeiboConfig.getValue("baseURL")
				+ "tags/suggestions.json", access_token));
	}

	/**
	 * 获取系统推荐的标签列表
	 * 
	 * @param count
	 *            返回记录数，默认10，最大10
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/tags/suggestions
	 * @since JDK 1.5
	 */
	public List<Tag> getTagsSuggestions(int count) throws WeiboException {
		return Tag.constructTags(client.get(WeiboConfig.getValue("baseURL")
				+ "tags/suggestions.json",
				new PostParameter[] { new PostParameter("count", count) },
				access_token));
	}

	/**
	 * 为当前登录用户添加新的用户标签
	 * 
	 * @param tags
	 *            要创建的一组标签，用半角逗号隔开，每个标签的长度不可超过7个汉字，14个半角字符
	 * @return tag_id
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/tags/create
	 * @since JDK 1.5
	 */
	public JSONArray createTags(String tags) throws WeiboException {
		return client.post(
				WeiboConfig.getValue("baseURL") + "tags/create.json",
				new PostParameter[] { new PostParameter("tags", tags) },
				access_token).asJSONArray();
	}

	/**
	 * 删除一个用户标签
	 * 
	 * @param tag_id
	 *            要删除的标签ID
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @throws JSONException
	 * @see http://open.weibo.com/wiki/2/tags/destroy
	 * @since JDK 1.5
	 */
	public JSONObject destoryTag(Integer tag_id) throws WeiboException {
		return client.post(
				WeiboConfig.getValue("baseURL") + "tags/destroy.json",
				new PostParameter[] { new PostParameter("tag_id", tag_id
						.toString()) }, access_token).asJSONObject();
	}

	/**
	 * 批量删除一组标签
	 * 
	 * @param ids
	 *            要删除的一组标签ID，以半角逗号隔开，一次最多提交10个ID
	 * @return tag_id
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/tags/destroy_batch
	 * @since JDK 1.5
	 */
	public List<Tag> destroyTagsBatch(String ids) throws WeiboException {
		return Tag.constructTags(client.post(WeiboConfig.getValue("baseURL")
				+ "tags/destroy_batch.json",
				new PostParameter[] { new PostParameter("ids", ids) },
				access_token));
	}
}
