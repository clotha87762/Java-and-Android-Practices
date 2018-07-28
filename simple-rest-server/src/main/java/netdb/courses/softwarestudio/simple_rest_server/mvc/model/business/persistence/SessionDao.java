package netdb.courses.softwarestudio.simple_rest_server.mvc.model.business.persistence;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.simple_rest_server.mvc.ModelAwareServlet;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.User;
import netdb.courses.softwarestudio.simple_rest_server.service.md5.MD5Encoder;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class SessionDao extends ModelAwareServlet<User> {
	public static final String AUTH_CHECK_ATTR = "AuthCheck";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// retrieve the user object with a specified user name from database 
		User loginUser = getModel(req);
		User fetchedUser = ObjectifyService.ofy().load().type(User.class)
				.id(loginUser.getUsername()).now();
		boolean checkResult = false;
		
		// check if it exists
		if (fetchedUser != null) {
			// MD5 Hashing
			String password = loginUser.getPassword();
			password = MD5Encoder.encrypt(password);

			// check password
			if (password.equals(fetchedUser.getPassword()))
				checkResult = true;
		}
		
		// save the result of checking
		req.setAttribute(AUTH_CHECK_ATTR, checkResult);
	}
}
