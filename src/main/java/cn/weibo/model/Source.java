package cn.weibo.model;

public class Source implements java.io.Serializable{

	private static final long serialVersionUID = -8972443458374235866L;
    private String url;               //来源连接
    private String relationShip;      
    private String name;              //来源文案名称
	public Source(String str) {
		super();
		String[] source = str.split("\"",5);
        url = source[1];
        relationShip = source[3];
        name = source[4].replace(">", "").replace("</a", "");
	}
    
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getRelationship() {
		return relationShip;
	}


	public void setRelationship(String relationShip) {
		this.relationShip = relationShip;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

    
	@Override
	public String toString() {
		return "Source [url=" + url + ", relationShip=" + relationShip
				+ ", name=" + name + "]";
	}


}
