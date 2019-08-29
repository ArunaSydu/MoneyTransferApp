package com.app.moneytransfer;

import com.app.moneytransfer.exception.MoneyTransferExceptionHandler;
import com.app.moneytransfer.service.AccountService;
import com.app.moneytransfer.service.FundsTransferService;
import com.app.moneytransfer.service.UserService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * @author Aruna
 * This is the main Class which Starts the container and Initialize UserService,AccountService,TransactionService
 */
public class LaunchApplication {
	
	static final int SERVER_PORT = 8083;
	static final String INIT_PARAM = "jersey.config.server.provider.classnames";
	static final String PATH = "/*";
	static final String CONTEXT_PATH ="/";


	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		startService();
	}

	/**
	 * 
	 * @throws Exception
	 */
	private static void startService() throws Exception {
		Server server = new Server(SERVER_PORT);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(CONTEXT_PATH);
		server.setHandler(context);
		ServletHolder servletHolder = context.addServlet(ServletContainer.class, PATH);
		servletHolder.setInitParameter(INIT_PARAM,
					UserService.class.getCanonicalName() + ","
						+ AccountService.class.getCanonicalName() + ","
						+  FundsTransferService.class.getCanonicalName()+","
						+  MoneyTransferExceptionHandler.class.getCanonicalName());
		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}
	}

}
