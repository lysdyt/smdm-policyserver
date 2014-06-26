package at.fhooe.mcm.lri.smdm.server;


import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//import com.google.appengine.api.users.UserService; 
//import com.google.appengine.api.users.UserServiceFactory;

/**
 * Servlet implementation class LoginServlet
 */	
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getAnonymousLogger();
	private Hashtable<String, Boolean> validUserIds;

	@Override
	public void init() {
		validUserIds = new Hashtable<String, Boolean>();
		validUserIds.put("dev.seisop@gmail.com", true);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		// authentication

		try {
			if (!this.authenticate(req, resp)) {
				log.log(Level.INFO, "authentication failed ");
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.log(Level.INFO, "authentication passed ");
		String actionClass = DefaultAction.class.getCanonicalName();

		// parse cmd
		String cmd = req.getParameter("cmd");
		if (cmd != null) {
			actionClass = String.format("%s.%sAction", WebAction.class
					.getPackage().getName(), cmd);
		}
		log.log(Level.INFO, "cmd : " + cmd);
		log.log(Level.INFO, "actionClass : " + actionClass);

		// perform WebAction based on cmd
		try {
			Class<WebAction> clazz = (Class<WebAction>) Class
					.forName(actionClass);
			WebAction action = clazz.newInstance();
			// log.log(Level.INFO, "call action ");
			action.perform(req, resp);
		} catch (Exception ex) {
			// build log msg
			String msg = ex.toString() + ": ";
			for (StackTraceElement trace : ex.getStackTrace()) {
				if (trace.getClassName().startsWith(
						LoginServlet.class.getPackage().getName())) {
					msg += trace.toString();
					break;
				}
			}
			log.warning(msg);

		}
	}

	protected boolean authenticate(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
////		UserService userService = UserServiceFactory.getUserService();
//		String thisURL = req.getRequestURI();
//		if (req.getUserPrincipal() != null) {
//			String key = req.getUserPrincipal().getName();
//			log.log(Level.INFO, "user id : " + key);
//			req.setAttribute("UserID", key);
//			if (validUserIds.containsKey(key)) {
//				log.log(Level.INFO, "valid user id : " + key);
//				// create logout url for the navigation bar
//				String url = userService.createLogoutURL(thisURL);
//				req.setAttribute("LogoutURL", url);
//				log.log(Level.INFO, "LogoutURL: " + url);
//				return true;
//			}
//			log.log(Level.INFO, "invalid user id : " + key);
//			resp.getWriter().println(
//					"<p>Please <a href=\""
//							+ userService.createLoginURL(thisURL)
//							+ "\">sign in</a>.</p>");
//			return false;
//		} else {
//			log.log(Level.INFO, "user id is null");
//			resp.getWriter().println(
//					"<p>Please <a href=\""
//							+ userService.createLoginURL(thisURL)
//							+ "\">sign in</a>.</p>");
//			return false;
//		}
		return false;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
