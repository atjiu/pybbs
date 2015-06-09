package cn.weibo;

import cn.weibo.model.PostParameter;
import cn.weibo.model.WeiboException;
import cn.weibo.org.json.JSONObject;
import cn.weibo.util.WeiboConfig;

public class Register extends Weibo {

	private static final long serialVersionUID = -6809545704064413209L;

	public Register(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 验证昵称是否可用，并给予建议昵称
	 * 
	 * @param nickname
	 *            需要验证的昵称。4-20个字符，支持中英文、数字、"_"或减号。必须做URLEncode，采用UTF-8编码
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see http://open.weibo.com/wiki/2/register/verify_nickname
	 * @since JDK 1.5
	 */
	public JSONObject verifyNickname(String nickname) throws WeiboException {
		return client
				.get(WeiboConfig.getValue("baseURL")
						+ "register/verify_nickname.json",
						new PostParameter[] { new PostParameter("nickname",
								nickname) }, access_token).asJSONObject();
	}
}
