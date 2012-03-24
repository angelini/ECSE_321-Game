package mcgill.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

public class Client {

	private String session;
	private Gson gson;
	private Jedis jedis;
	
	public static void main(String[] args) {
    	Client client = new Client(Config.REDIS_HOST, Config.REDIS_PORT);
    	client.start();
    }
	
	public Client(String host, int port) {
		this.session = UUID.randomUUID().toString();
		this.gson = new Gson();
		this.jedis = new Jedis(host, port);
	}
	
	public void createUser() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print("Username: ");
			String username = br.readLine();
			System.out.print("Password: ");
			String password = br.readLine();
			
			String[] args = {this.session, username, password};
			NetworkCommand nc = new NetworkCommand(this.session, Config.REGISTER, args);
			
			this.jedis.publish("server::" + this.session, this.gson.toJson(nc));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void login() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print("Username: ");
			String username = br.readLine();
			System.out.print("Password: ");
			String password = br.readLine();
			
			String[] args = {this.session, username, password};
			NetworkCommand nc = new NetworkCommand(this.session, Config.LOGIN, args);
			
			this.jedis.publish("server::" + this.session, this.gson.toJson(nc));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public void start() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.println("Client Started, choose action:");
			System.out.println("1. Login");
			System.out.println("2. Create User");
			System.out.println("3. Exit");
			System.out.print("=> ");
		
			try {
				String command = br.readLine();
				
				switch(Integer.parseInt(command)) {
				case 1:
					this.login();
					break;
					
				case 2:
					this.createUser();
					break;
					
				case 3:
					this.exit();
					break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	}
	
}
