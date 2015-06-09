package cn.weibo.model;

import java.util.List;

public class TagWapper {
	private List<Tag> tags;
	private String id;
	
	public TagWapper(List<Tag> tags, String id) {
		this.tags = tags;
		this.id = id;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		String str = "";
		str += "TagWapper [tags=[";
		for (Tag t : tags) {
			str += t.toString() + " ";
		}
		str += "], ";
		str += "id=" + id + "]";
		return str;
	}
	
}
