package mcgill.game;

public class Config {
	
	// Server config
	static final int THREADS = 10;
	
	// Redis server info
	
	static final String REDIS_HOST = "localhost";
	static final int REDIS_PORT = 6379;
	
	// Network commands
	
	static final String SERVER = "server";
	static final String CLIENT = "client";
	static final String COMMAND = "command";
	static final String NOTIFICATIONS = "notifications";
	
	static final String LOGIN = "login";
	static final String REGISTER = "register";
	static final String MESSAGE = "message";
	static final String GET_FRIENDS = "get_friends";
	static final String ADD_FRIEND = "add_friend";
	static final String GET_CHATS = "get_chats";
	static final String CREATE_CHAT = "create_chat";
	static final String ADD_CREDITS = "add_credits";
	static final String GET_TABLES = "get_tables";
	static final String CREATE_TABLE = "create_table";
	static final String JOIN_TABLE = "join_table";
	static final String START_ROUND = "start_round";
	
	static final String GET_COMMAND = "get_command";
	
	// Game Configs
	
	static final int REFILL_CREDITS = 1000;
	static final int MAX_PLAYERS = 5;
	static final int LOW_BET = 20;
	static final int MAX_RAISES = 3;
	static final int BRING_IN = 10;

}
