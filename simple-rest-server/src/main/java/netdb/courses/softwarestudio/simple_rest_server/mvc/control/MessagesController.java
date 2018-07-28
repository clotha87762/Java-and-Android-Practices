package netdb.courses.softwarestudio.simple_rest_server.mvc.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.Message;
import netdb.courses.softwarestudio.simple_rest_server.mvc.view.json.MessageJson;
import netdb.courses.softwarestudio.simple_rest_server.service.json.JsonService;
import netdb.courses.softwarestudio.simple_rest_server.service.utf8.UTF8Coder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class MessagesController extends ResourceController<Message> {
	private static final Log log = LogFactory.getLog(MessagesController.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// invoke business logics
		if (log.isDebugEnabled())
			log.debug("Invoking business logics");
		include(req, resp, "/model/business/persistene/messages-dao");

		// dispatch to view
		if (log.isDebugEnabled())
			log.debug("Dispatching to view");
		forward(req, resp, "/view/json/messages");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// setup model in request
		try {
			if (log.isDebugEnabled())
				log.debug("Setting up model in request");

			// check the header
			if (!req.getHeader("Content-Type").contains("application/json"))
				throw new RuntimeException("Unacceptable type.");

			// try to get the user name from session
			String userName = getUserName(req);
			if (userName == null) {
				forward(req, resp, "/view/401-unauthorized-view");
				if (log.isInfoEnabled())
					log.info("Unauthorized login from " + req.getRemoteAddr());
				return;
			}

			// deserialize the content of the message
			String body = getRequestBody(req);
			MessageJson m = JsonService.deserialize(UTF8Coder.encode(body), MessageJson.class);
			
			// check contents
			if (m.getContent() == null)
				throw new RuntimeException("Wrong message format.");
			
			// save the message
			setModel(req, new Message(m.getContent(), userName));
			
		} catch (Exception e) {
			forward(req, resp, "/view/400-bad-request-view");
			if (log.isInfoEnabled())
				log.info("Bad request: " + e.getMessage());
			return;
		}

		// invoke business logics
		if (log.isDebugEnabled())
			log.debug("Invoking business logics");
		include(req, resp, "/model/business/persistene/messages-dao");

		// dispatch to view
		if (log.isDebugEnabled())
			log.debug("Dispatching to view");
		forward(req, resp, "/view/json/messages");
	}
	
	private String getUserName(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null)
			return null;
		return (String) session.getAttribute(SessionController.USER_NAME_ATTR);
	}
}
