package netdb.courses.softwarestudio.messageboard;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class MessageServlet extends HttpServlet {
	
	// TODO: Register this servlet in web.xml 

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: Get a list of messages from database in the descending order of time field
		List<Message> messages = ObjectifyService.ofy().load().type(Message.class).order("-time").list();
		
		
		// TODO: Serialize the list to JSON
		String jsonstring = JSON.toJSONString(messages);
		
		
		// TODO: Response 200 and the list of messages
		resp.setStatus(200);
		resp.setContentType("text/plain");
		resp.getWriter().write("200 OK");
		resp.getWriter().write(jsonstring);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO: Deserialize the JSON to a Message object
		
		Message msg = new Message();
		
		String jsonstring = getRequestBody(req);
		Message post = JSON.parseObject(jsonstring,Message.class);
		// TODO: Set the time of the new message to current time
		Long time;
		time = System.currentTimeMillis();
		post.setTime(time);
		// TODO: Save data into database
		ObjectifyService.ofy().save().entity(msg).now();
		// TODO: Response 201 created
		resp.setStatus(201);
		resp.setContentType("text/plain");
		resp.getWriter().write("201 Created");
	}

	private String getRequestBody(HttpServletRequest req) throws IOException {
		StringBuffer buf = new StringBuffer();
		char[] c = new char[1024];
		int len;
		while ((len = req.getReader().read(c)) != -1) {
			buf.append(c, 0, len);
		}
		return buf.toString();
	}
}
