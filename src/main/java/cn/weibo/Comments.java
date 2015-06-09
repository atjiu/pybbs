package cn.weibo;

import java.util.List;
import java.util.Map;

import cn.weibo.model.Comment;
import cn.weibo.model.CommentWapper;
import cn.weibo.model.Paging;
import cn.weibo.model.PostParameter;
import cn.weibo.model.WeiboException;
import cn.weibo.util.ArrayUtils;
import cn.weibo.util.WeiboConfig;

public class Comments extends Weibo {

	private static final long serialVersionUID = 3321231200237418256L;

	public Comments(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 根据微博ID返回某条微博的评论列表
	 * 
	 * @param id
	 *            需要查询的微博ID
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/show
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentById(String id) throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/show.json",
				new PostParameter[] { new PostParameter("id", id) },
				access_token));
	}

	/**
	 * 根据微博ID返回某条微博的评论列表
	 * 
	 * @param id
	 *            需要查询的微博ID
	 * @param count
	 *            单页返回的记录条数，默认为50。
	 * @param page
	 *            返回结果的页码，默认为1。
	 * @param filter_by_author
	 *            作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/show
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentById(String id, Paging page,
			Integer filter_by_author) throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/show.json",
				new PostParameter[] {
						new PostParameter("id", id),
						new PostParameter("filter_by_author", filter_by_author
								.toString()) }, page, access_token));
	}

	/**
	 * 根据微博ID返回某条微博的评论列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/comments/show
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentById(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/show.json", parList,
				access_token));
	}

	/**
	 * 获取当前登录用户所发出的评论列表
	 * 
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/by_me
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentByMe() throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/by_me.json",
				access_token));
	}

	/**
	 * 获取当前登录用户所发出的评论列表
	 * 
	 * @param count
	 *            单页返回的记录条数，默认为50
	 * @param page
	 *            返回结果的页码，默认为1
	 * @param filter_by_source
	 *            来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/by_me
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentByMe(Paging page, Integer filter_by_source)
			throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/by_me.json",
				new PostParameter[] { new PostParameter("filter_by_author",
						filter_by_source.toString()) }, page, access_token));
	}

	/**
	 * 获取当前登录用户所发出的评论列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/comments/by_me
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentByMe(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/by_me.json", parList,
				access_token));
	}

	/**
	 * 获取当前登录用户所接收到的评论列表
	 * 
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/to_me
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentToMe() throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/to_me.json",
				access_token));
	}

	/**
	 * 获取当前登录用户所接收到的评论列表
	 * 
	 * @param count
	 *            单页返回的记录条数，默认为50。
	 * @param page
	 *            返回结果的页码，默认为1。
	 * @param filter_by_author
	 *            作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
	 * @param filter_by_source
	 *            来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0。
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/to_me
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentToMe(Paging page, Integer filter_by_source,
			Integer filter_by_author) throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/to_me.json",
				new PostParameter[] {
						new PostParameter("filter_by_source", filter_by_source
								.toString()),
						new PostParameter("filter_by_author", filter_by_author
								.toString()) }, page, access_token));
	}

	/**
	 * 获取当前登录用户所接收到的评论列表
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/comments/to_me
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentToMe(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/to_me.json", parList,
				access_token));
	}

	/**
	 * 获取当前登录用户的最新评论包括接收到的与发出的
	 * 
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/timeline
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentTimeline() throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/timeline.json",
				access_token));
	}

	/**
	 * 获取当前登录用户的最新评论包括接收到的与发出的
	 * 
	 * @param count
	 *            单页返回的记录条数，默认为50。
	 * @param page
	 *            返回结果的页码，默认为1。
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/timeline
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentTimeline(Paging page) throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/timeline.json",
				null, page, access_token));
	}

	/**
	 * 获取当前登录用户的最新评论包括接收到的与发出的
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/comments/timeline
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentTimeline(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/timeline.json", parList,
				access_token));
	}

	/**
	 * 获取最新的提到当前登录用户的评论，即@我的评论
	 * 
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/mentions
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentMentions() throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/mentions.json",
				access_token));
	}

	/**
	 * 获取最新的提到当前登录用户的评论，即@我的评论
	 * 
	 * @param count
	 *            单页返回的记录条数，默认为50。
	 * @param page
	 *            返回结果的页码，默认为1。
	 * @param filter_by_author
	 *            作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
	 * @param filter_by_source
	 *            来源筛选类型，0：全部、1：来自微博的评论、2：来自微群的评论，默认为0。
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/mentions
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentMentions(Paging page,
			Integer filter_by_source, Integer filter_by_author)
			throws WeiboException {
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/mentions.json",
				new PostParameter[] {
						new PostParameter("filter_by_source", filter_by_source
								.toString()),
						new PostParameter("filter_by_author", filter_by_author
								.toString()) }, page, access_token));
	}

	/**
	 * 获取最新的提到当前登录用户的评论，即@我的评论
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/comments/mentions
	 * @since JDK 1.5
	 */
	public CommentWapper getCommentMentions(Map<String, String> map)
			throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return Comment.constructWapperComments(client.get(
				WeiboConfig.getValue("baseURL") + "comments/mentions.json", parList,
				access_token));
	}

	/**
	 * 根据评论ID批量返回评论信息
	 * 
	 * @param cids
	 *            需要查询的批量评论ID，用半角逗号分隔，最大50
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/show_batch
	 * @since JDK 1.5
	 */
	public List<Comment> getCommentShowBatch(String cids) throws WeiboException {
		return Comment.constructComment(client.get(
				WeiboConfig.getValue("baseURL") + "comments/show_batch.json",
				new PostParameter[] { new PostParameter("cids", cids) },
				access_token));
	}

	/**
	 * 对一条微博进行评论
	 * 
	 * @param comment
	 *            评论内容，必须做URLencode，内容不超过140个汉字
	 * @param id
	 *            需要评论的微博ID
	 * @return Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/create
	 * @since JDK 1.5
	 */
	public Comment createComment(String comment, String id)
			throws WeiboException {
		return new Comment(client.post(WeiboConfig.getValue("baseURL")
				+ "comments/create.json", new PostParameter[] {
				new PostParameter("comment", comment),
				new PostParameter("id", id) }, access_token));
	}

	/**
	 * 对一条微博进行评论
	 * 
	 * @param comment
	 *            评论内容，必须做URLencode，内容不超过140个汉字
	 * @param id
	 *            需要评论的微博ID
	 * @param comment_ori
	 *            当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
	 * @return Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/create
	 * @since JDK 1.5
	 */
	public Comment createComment(String comment, String id, Integer comment_ori)
			throws WeiboException {
		return new Comment(client.post(WeiboConfig.getValue("baseURL")
				+ "comments/create.json", new PostParameter[] {
				new PostParameter("comment", comment),
				new PostParameter("id", id),
				new PostParameter("comment_ori", comment_ori.toString()) },
				access_token));
	}

	/**
	 * 对一条微博进行评论
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/comments/create
	 * @since JDK 1.5
	 */
	public Comment createComment(Map<String, String> map) throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return new Comment(client.post(WeiboConfig.getValue("baseURL")
				+ "comments/create.json", parList, access_token));
	}

	/**
	 * 回复一条评论
	 * 
	 * @param comment
	 *            评论内容，必须做URLencode，内容不超过140个汉字
	 * 
	 * @param cid
	 *            需要回复的评论ID
	 * @param id
	 *            需要评论的微博ID
	 * @return Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/reply
	 * @since JDK 1.5
	 */
	public Comment replyComment(String cid, String id, String comment)
			throws WeiboException {
		return new Comment(client.post(WeiboConfig.getValue("baseURL")
				+ "comments/reply.json", new PostParameter[] {
				new PostParameter("cid", cid), new PostParameter("id", id),
				new PostParameter("comment", comment) }, access_token));
	}

	/**
	 * 回复一条评论
	 * 
	 * @param comment
	 *            评论内容，必须做URLencode，内容不超过140个汉字
	 * @param cid
	 *            需要回复的评论ID
	 * @param id
	 *            需要评论的微博ID
	 * @param without_mention
	 *            回复中是否自动加入“回复@用户名”，0：是、1：否，默认为0。
	 * @param comment_ori
	 *            当评论转发微博时，是否评论给原微博，0：否、1：是，默认为0。
	 * @return Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/reply
	 * @since JDK 1.5
	 */
	public Comment replyComment(String cid, String id, String comment,
			Integer without_mention, Integer comment_ori) throws WeiboException {
		return new Comment(
				client.post(
						WeiboConfig.getValue("baseURL") + "comments/reply.json",
						new PostParameter[] {
								new PostParameter("comment", comment),
								new PostParameter("id", id),
								new PostParameter("cid", cid),
								new PostParameter("without_mention",
										without_mention.toString()),
								new PostParameter("comment_ori", comment_ori
										.toString()) }, access_token));
	}

	/**
	 * 回复一条评论
	 * 
	 * @param map
	 *            参数列表
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/comments/reply
	 * @since JDK 1.5
	 */
	public Comment replyComment(Map<String, String> map) throws WeiboException {
		PostParameter[] parList = ArrayUtils.mapToArray(map);
		return new Comment(client.post(WeiboConfig.getValue("baseURL")
				+ "comments/reply.json", parList, access_token));
	}

	/**
	 * 删除一条评论
	 * 
	 * @param cid
	 *            要删除的评论ID，只能删除登录用户自己发布的评论
	 * @return Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/destroy
	 * @since JDK 1.5
	 */
	public Comment destroyComment(String cid) throws WeiboException {
		return new Comment(client.post(WeiboConfig.getValue("baseURL")
				+ "comments/destroy.json",
				new PostParameter[] { new PostParameter("cid", cid) },
				access_token));
	}

	/**
	 * 根据评论ID批量删除评论
	 * 
	 * @param ids
	 *            需要删除的评论ID，用半角逗号隔开，最多20个
	 * @return list of Comment
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.1
	 * @see http://open.weibo.com/wiki/2/comments/destroy_batch
	 * @since JDK 1.5
	 */
	public List<Comment> destoryCommentBatch(String cids) throws WeiboException {
		return Comment
				.constructComment(client.post(
						WeiboConfig.getValue("baseURL")
								+ "comments/destroy_batch.json",
						new PostParameter[] { new PostParameter("cids", cids) },
						access_token));
	}
}
