package netdb.courses.softwarestudio.simple_rest_server.mvc.model.business.persistence;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import netdb.courses.softwarestudio.simple_rest_server.mvc.ModelAwareServlet;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class MessagesDao extends ModelAwareServlet<Message> {
	private static final Log log = LogFactory.getLog(MessagesDao.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (log.isDebugEnabled())
			log.debug("Getting/listing domain object(s)");
		
		// load the message list from database
		List<Message> ms = ObjectifyService.ofy().load().type(Message.class).list();
		setModel(req, ms);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (log.isDebugEnabled())
			log.debug("Creating a domain object");
		
		// save a new message into database
		Message m = getModel(req);
		m.setTime(System.currentTimeMillis());
		ObjectifyService.ofy().save().entity(m).now();
	}
}
