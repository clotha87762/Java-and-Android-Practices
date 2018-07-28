package netdb.courses.softwarestudio.messageboard;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

public class ObjectifyInitializer implements ServletContextListener {
	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		ObjectifyService.register(Message.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// Do Nothing
	}
}
