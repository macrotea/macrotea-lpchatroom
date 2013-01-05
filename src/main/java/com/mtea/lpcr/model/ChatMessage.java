package com.mtea.lpcr.model;

/**
 * 聊天内容
 * @author 	liangqiye@gz.iscas.ac.cn
 * @version 1.0 , 2013-1-5 下午7:28:46
 */
public class ChatMessage {
	
    private final String from;
    
    private final String content;
 
    public ChatMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }
    
    public String getFrom() {
        return from;
    }

    public String getContent() {
        return content;
    }

}
