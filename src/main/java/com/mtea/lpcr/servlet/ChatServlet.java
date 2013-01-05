package com.mtea.lpcr.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtea.lpcr.model.ChatMessage;

/**
 * 聊天Servlet
 * @author 	liangqiye@gz.iscas.ac.cn
 * @version 1.0 , 2013-1-5 下午8:19:22
 */
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 聊天内容集合
	 */
	private static List<ChatMessage> messages = new ArrayList<ChatMessage>();
	
	/**
	 * 添加聊天内容
	 * @param message
	 * @author liangqiye / 2013-1-5 下午8:19:43
	 */
	private static synchronized void addMessage(ChatMessage message) {
		messages.add(message);
		LongPollServlet.awakeAll();
	}

	/**
	 * 获取特定的区间的聊天内容信息
	 * @param messageOffset
	 * @return
	 * @author liangqiye / 2013-1-5 下午8:20:07
	 */
	public static List<ChatMessage> getMessagesFrom(int messageOffset) {
		return messages.subList(messageOffset, messages.size());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String message = request.getParameter("message");
		System.out.println("message: " + message);
		addMessage(new ChatMessage(request.getRemoteAddr(), message));
	}

}
