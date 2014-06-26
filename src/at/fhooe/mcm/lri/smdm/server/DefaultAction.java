package at.fhooe.mcm.lri.smdm.server;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultAction extends WebAction {
	private static final Logger log = Logger.getLogger(DefaultAction.class
			.getName());

	@Override
	public void perform(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String page = "WEB-INF/jsp/default.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(page);
		if (rd == null)
			log.log(Level.SEVERE, "cannot get " + page);
		rd.forward(request, response);
	}

}
