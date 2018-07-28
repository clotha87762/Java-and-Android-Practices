package netdb.courses.softwarestudio.simple_rest_server.mvc.model.business.persistence;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.simple_rest_server.mvc.ModelAwareServlet;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.User;
import netdb.courses.softwarestudio.simple_rest_server.service.md5.MD5Encoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class UsersDao extends ModelAwareServlet<User> {
	private static final Log log = LogFactory.getLog(UsersDao.class);

	public static final String EXIST_FLAG_ATTR = "existed";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// retrieve the model object
		User newUser = getModel(req);
		
		if (log.isDebugEnabled())
			log.debug("Get a new user object with username: " + newUser.getUsername());

		// check if it exists
		User fetchedUser = ObjectifyService.ofy().load().type(User.class)
				.id(newUser.getUsername()).now();
		if (fetchedUser != null) {
			req.setAttribute(EXIST_FLAG_ATTR, true);
		} else {
			// MD5 Hashing
			String password = newUser.getPassword();
			password = MD5Encoder.encrypt(password);
			newUser.setPassword(password);

			// save it into database
			ObjectifyService.ofy().save().entity(newUser).now();
			
			if (log.isInfoEnabled())
				log.info("Create a new user: " + newUser.getUsername());
			
			req.setAttribute(EXIST_FLAG_ATTR, false);
		}
	}
}
