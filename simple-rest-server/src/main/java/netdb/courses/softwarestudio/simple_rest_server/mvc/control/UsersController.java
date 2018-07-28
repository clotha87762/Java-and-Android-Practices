package netdb.courses.softwarestudio.simple_rest_server.mvc.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.simple_rest_server.mvc.model.business.persistence.UsersDao;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.User;
import netdb.courses.softwarestudio.simple_rest_server.service.json.JsonService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class UsersController extends ResourceController<User> {
	private static final Log log = LogFactory.getLog(UsersController.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = null;

		// setup model in request
		try {
			if (log.isDebugEnabled())
				log.debug("Setting up model in request");

			// check type
			if (!req.getHeader("Content-Type").contains("application/json"))
				throw new RuntimeException("Unacceptable type.");

			// deserialize the content of the message
			String body = getRequestBody(req);
			user = JsonService.deserialize(body, User.class);

			// format check
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
		include(req, resp, "/model/business/persistene/users-dao");
		
		// check the result
		Boolean existed = (Boolean) req.getAttribute(UsersDao.EXIST_FLAG_ATTR);
		if (existed) {
			forward(req, resp, "/view/409-conflict-view");
			if (log.isInfoEnabled())
				log.info("User name conflict: " + user.getUsername());
			return;
		}

		// dispatch to view
		if (log.isDebugEnabled())
			log.debug("Dispatching to view");
		forward(req, resp, "/view/json/users");
	}
}
