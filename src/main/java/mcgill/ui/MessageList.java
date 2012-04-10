package mcgill.ui;

import javax.swing.DefaultListModel;

public class MessageList extends DefaultListModel {

	private static final long serialVersionUID = 3020497786278181063L;
	
	private String chatId;
	
	public String getChatId() {
		return this.chatId;
	}
	
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

}
