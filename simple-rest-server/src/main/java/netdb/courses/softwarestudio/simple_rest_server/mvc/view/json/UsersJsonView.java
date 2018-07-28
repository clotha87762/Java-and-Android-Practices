package netdb.courses.softwarestudio.simple_rest_server.mvc.view.json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.simple_rest_server.mvc.ModelAwareServlet;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class UsersJsonView extends ModelAwareServlet<User> {
	private static final Log log = LogFactory.getLog(UsersJsonView.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(log.isDebugEnabled())
			log.debug("Responsing 201 Created");
		
		// 201 Created
		resp.setStatus(201);
	}
}
