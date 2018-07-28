package netdb.courses.softwarestudio.lab.model;

import com.alibaba.fastjson.annotation.JSONField;

public class User {
	private String id;
	private String name;

	@JSONField(name = "username")
	private String userName;

	private String gender;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
