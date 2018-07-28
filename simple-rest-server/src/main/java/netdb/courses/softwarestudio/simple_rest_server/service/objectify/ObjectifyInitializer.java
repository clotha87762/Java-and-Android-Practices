package netdb.courses.softwarestudio.simple_rest_server.service.objectify;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.Message;
import netdb.courses.softwarestudio.simple_rest_server.mvc.model.domain.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify needs every persistable object to be registered. This class
 * registers all domain objects at the time when the {@link ServletContext} is
 * initialized.
 */
public final class ObjectifyInitializer implements ServletContextListener {
	private static final Log log = LogFactory.getLog(ObjectifyInitializer.class);
	
	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		if(log.isInfoEnabled())
			log.info("Register entity classes");
		
		ObjectifyService.register(User.class);
		ObjectifyService.register(Message.class);
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		// do nothing
	}
}
