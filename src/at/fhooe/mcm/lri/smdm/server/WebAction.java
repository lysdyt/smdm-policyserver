package at.fhooe.mcm.lri.smdm.server;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class WebAction {
	private long timeStart, timeStop;
	private long cpuStart, cpuEnd;
	private HttpServletRequest request;

	public abstract void perform(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public void startTiming() {
		timeStart = System.currentTimeMillis();
	}

	public void endTiming(HttpServletRequest req, int itemCounter) {
		request = req;
		timeStop = System.currentTimeMillis();
	}
}