package netdb.courses.softwarestudio.simple_rest_server.mvc.view.json;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.simple_rest_server.mvc.ModelAwareServlet;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.Message;
import netdb.courses.softwarestudio.simple_rest_server.service.json.JsonService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class MessagesJsonView extends ModelAwareServlet<Message> {
	private static final Log log = LogFactory.getLog(MessagesJsonView.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		if(log.isDebugEnabled())
			log.debug("Responsing 200 OK");
		
		// serialize objects
		@SuppressWarnings("unchecked")
		List<Message> messages = (List<Message>) getModel(req);
		List<MessageJson> messageJsons = new LinkedList<MessageJson>();
		for (Message message : messages)
			messageJsons.add(new MessageJson(message));
		
		// print the JSON string on the body of the HTTP response
		resp.setContentType("application/json");
		resp.setHeader("Cache-Control", "no-cache"); // make sure no intermediate node caches the result
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(JsonService.serialize(messageJsons));
		
		// 200 OK
		resp.setStatus(201);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(log.isDebugEnabled())
			log.debug("Responsing 201 Created");
		
		// 201 Created
		resp.setStatus(201);
	}
}
