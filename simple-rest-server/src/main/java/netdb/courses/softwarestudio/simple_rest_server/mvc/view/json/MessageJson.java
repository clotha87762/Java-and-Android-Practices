package netdb.courses.softwarestudio.simple_rest_server.mvc.view.json;

import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.Message;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.User;

public class MessageJson {
	
	private long id;
	private String content;
	private long time;
	private User sender = new User();
	
	public MessageJson() {
	}
	
	public MessageJson(Message msg) {
		id = msg.getId();
		content = msg.getContent();
		time = msg.getTime();
		sender.setUsername(msg.getUserName());
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
}
