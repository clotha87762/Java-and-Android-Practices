package netdb.courses.softwarestudio.simple_rest_server.mvc.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import netdb.courses.softwarestudio.simple_rest_server.mvc.model.business.persistence.SessionDao;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.User;
import netdb.courses.softwarestudio.simple_rest_server.service.json.JsonService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class SessionController extends ResourceController<User> {
	private static final Log log = LogFactory.getLog(SessionController.class);

	public static final String USER_NAME_ATTR = "UserName";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		User user = null;

		// setup model in request
		try {
			if (log.isDebugEnabled())
				log.debug("Setting up model in request");
			
			// check the header
			if(!req.getHeader("Content-Type").contains("application/json"))
				throw new RuntimeException("Unacceptable type.");

			// deserialize the content of the message
			String body = getRequestBody(req);
			user = JsonService.deserialize(body, User.class);

			// check format
			if (user.getUsername() == null || user.getUsername().isEmpty())
				throw new RuntimeException("Username is empty.");
			if (user.getPassword() == null || user.getPassword().isEmpty())
				throw new RuntimeException("Password is empty.");

			// save the user object
			setModel(req, user);
		} catch (Exception e) {
			forward(req, resp, "/view/400-bad-request-view");
			if (log.isInfoEnabled())
				log.info("Bad request: " + e.getMessage());
			return;
		}

		// invoke business logics
		if (log.isDebugEnabled())
			log.debug("Invoking business logics");
		include(req, resp, "/model/business/persistene/session-dao");

		// check the result
		boolean authCorrect = (boolean) req.getAttribute(SessionDao.AUTH_CHECK_ATTR);
		if (!authCorrect) {
			forward(req, resp, "/view/400-bad-request-view");
			if (log.isDebugEnabled())
				log.debug("Wrong username or password");
			return;
		}

		// create session
		HttpSession session = req.getSession();
		session.setAttribute(USER_NAME_ATTR, user.getUsername());
		if (log.isInfoEnabled()) {
			String display = new String(user.getUsername().getBytes("UTF-8"), "UTF-8");
			log.info("A new session created for user: " + display);
		}

		// dispatch to view
		if (log.isDebugEnabled())
			log.debug("Dispatching to view");
		forward(req, resp, "/view/json/session");
	}
}
