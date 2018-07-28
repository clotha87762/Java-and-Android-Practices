package netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Message {
	
	@Id
	private Long id;
	private String content;
	@Index // for sorting time
	private long time;
	private String userName;
	
	public Message() {
	}
	
	public Message(String content, String userName) {
		this.content = content;
		this.userName = userName;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
