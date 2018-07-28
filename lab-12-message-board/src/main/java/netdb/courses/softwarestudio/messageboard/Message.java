package netdb.courses.softwarestudio.messageboard;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Message {
	@Id
	private Long id;
	
	@Index
	private Long time;
	private String content;
	
	public Message() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
