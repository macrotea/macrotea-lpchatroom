package com.mtea.lpcr.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LongPollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Set保证每个session一个线程,消除重复
	 */
	private static Set<Thread> longPollThreads = new HashSet<Thread>();

	/**
	 * 唤醒所有的轮询线程,从而推送
	 * @author liangqiye / 2013-1-5 下午8:20:29
	 */
	public static synchronized void awakeAll() {
		for (Iterator<Thread> iterator = longPollThreads.iterator(); iterator.hasNext();) {
			Thread t = (Thread) iterator.next();
			t.interrupt();
			
			//当用户关闭了客户端,确保无用的线程被删除,保证仅仅是在线的用户线程
			iterator.remove();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获得区间偏移
		int messageOffset = 0;
		try {
			messageOffset = Integer.parseInt(request.getParameter("messageOffset"));
		} catch (NumberFormatException e) {
			response.setStatus(400);
			return;
		}

		try {
			longPollThreads.add(Thread.currentThread());
			System.out.println("正在轮询线程数: " + longPollThreads.size());
			
			//睡眠
			Thread.sleep(5000);
		} catch (InterruptedException ignore) {
		} finally {
			request.setAttribute("messages", ChatServlet.getMessagesFrom(messageOffset));
			request.getRequestDispatcher("messages.json.jsp").forward(request, response);
		}
	}

}
