package cn.weibo.model;

import java.util.ArrayList;
import java.util.List;

import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * 话题
 *
 * @author SinaWeibo
 * @since weibo4j-V2 1.0.0
 */
public class UserTrend extends WeiboResponse{
    private String num;               
    private String hotword = null;
    private String trendId = null;
    private static final long serialVersionUID = 1925956704460743946L;
 
	public UserTrend() {
		super();
	}
	public UserTrend(Response res) throws WeiboException {
		super(res);
		JSONObject json = res.asJSONObject();
		try {
			num = json.getString("num");
			hotword = json.getString("hotword");
			trendId = json.getString("trend_id");
			if( json.getString("topicid")!=null)
				trendId = json.getString("topicid");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}
	public UserTrend(JSONObject json) throws WeiboException {
		try {
			num = json.getString("num");
			hotword = json.getString("hotword");
			trendId = json.getString("trend_id");
		} catch (JSONException je) {
			throw new WeiboException(je.getMessage() + ":" + json.toString(),
					je);
		}
	}
	
	public static List<UserTrend> constructTrendList(Response res) throws WeiboException {
	   	 try {
	            JSONArray list = res.asJSONArray();
	            int size = list.length();
	            List<UserTrend> trends = new ArrayList<UserTrend>(size);
	            for (int i = 0; i < size; i++) {
	            	trends.add(new UserTrend(list.getJSONObject(i)));
	            }
	            return trends;
	        } catch (JSONException jsone) {
	            throw new WeiboException(jsone);
	        } catch (WeiboException te) {
	            throw te;
	        }

	   }
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getHotword() {
		return hotword;
	}
	public void setHotword(String hotword) {
		this.hotword = hotword;
	}
	public String gettrendId() {
		return trendId;
	}
	public void settrendId(String trendId) {
		this.trendId = trendId;
	}
	@Override
	public String toString() {
		return "Trend [num=" + num + ", hotword=" + hotword + ", trendId="
				+ trendId + "]";
	}
    
    
}