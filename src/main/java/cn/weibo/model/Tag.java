package cn.weibo.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cn.weibo.Weibo;
import cn.weibo.http.Response;
import cn.weibo.org.json.JSONArray;
import cn.weibo.org.json.JSONException;
import cn.weibo.org.json.JSONObject;

/**
 * @author sinaWeibo
 * 
 */
public class Tag extends WeiboResponse {

	private static final long serialVersionUID = 2177657076940291492L;

	private String id;           //标签id

	private String value;        //标签value
	
	private String weight;

	public Tag(JSONObject json) throws WeiboException, JSONException {			
			if (!json.getString("id").isEmpty()) {
				id = json.getString("id"); 
			}
			if(!json.getString("value").isEmpty()) {
				value = json.getString("value");
			}else {
				Iterator<String> keys = json.sortedKeys();
				if (keys.hasNext()) {
					id = keys.next();
					value = json.getString(id);	
				}
			}
			weight= json.getString("weight");
	}
	public Tag(JSONObject json , Weibo weibo) throws WeiboException,JSONException {
		System.out.println(json);
		id = json.getString("id");
		value = json.getString("count");
		weight= json.getString("weight");
	}


	public static List<Tag> constructTags(Response res) throws WeiboException {
		try {
			JSONArray list = res.asJSONArray();
			int size = list.length();
			List<Tag> tags = new ArrayList<Tag>(size);
			for (int i = 0; i < size; i++) {
				tags.add(new Tag(list.getJSONObject(i)));
			}
			return tags;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		} catch (WeiboException te) {
			throw te;
		}
	}
	public static TagWapper constructTagWapper(Response res){
		try {
			JSONArray tags = res.asJSONArray();
			List<Tag> tagList = new ArrayList<Tag>();
			for(int i=0;i<tags.getJSONObject(0).getJSONArray("tags").length();i++){
				tagList.add(new Tag(tags.getJSONObject(0).getJSONArray("tags").getJSONObject(i)));
			}
			String id = tags.getJSONObject(0).getString("id");
			return new TagWapper(tagList, id);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<FavoritesTag> constructTag(Response res) throws WeiboException {
		try {
			JSONArray list = res.asJSONObject().getJSONArray("tags");
			int size = list.length();
			List<FavoritesTag> tags = new ArrayList<FavoritesTag>(size);
			for (int i = 0; i < size; i++) {
				tags.add(new FavoritesTag(list.getJSONObject(i)));
			}
			return tags;
		} catch (JSONException jsone) {
			throw new WeiboException(jsone);
		} catch (WeiboException te) {
			throw te;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tag other = (Tag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", value=" + value + ", weight=" + weight
				+ "]";
	}

}
