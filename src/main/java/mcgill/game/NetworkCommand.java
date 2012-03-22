package mcgill.game;

public class NetworkCommand {
	
	private String session;
	private String command;
	private String[] args;
	
	public NetworkCommand(String session, String command, String[] args) {
		this.session = session;
		this.command = command;
		this.args = args;
	}

	public String getSession() {
		return session;
	}

	public String getCommand() {
		return command;
	}

	public String[] getArgs() {
		return args;
	}
	
}