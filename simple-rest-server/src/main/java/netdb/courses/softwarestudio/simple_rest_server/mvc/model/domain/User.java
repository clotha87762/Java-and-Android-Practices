package netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
	@Id
	private String username;
	private String password;
	
	public User() {	
	}
	
	public User(String userName, String password) {
		this.username = userName;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
