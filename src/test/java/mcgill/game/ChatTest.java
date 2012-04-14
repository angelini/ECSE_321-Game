package mcgill.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChatTest {

	private static String ID = "changethis";
	private static Message MESSAGE;
	private static List<Message> MESSAGES;
	private static Set<User> USERS;
	private static Chat c1;
	private static Chat c2;
	private static User u1;
	private static User u2;
	private static String USERNAME = "changethis";
	private static String PASSWORD_HASH = "changethis";
	private static int CREDITS = 0; // change this
	
	@BeforeClass
	public void constructTestObjects() {
		USERS = new HashSet<User>();
		
		
		c1 = new Chat(ID, MESSAGES, USERS);
		c2 = new Chat(USERS);
		
		u1 = new User(USERNAME, PASSWORD_HASH, CREDITS);
		u2 = new User(USERNAME, PASSWORD_HASH, CREDITS);
	}
	
	@Test
	public void testGetId() {
		assertNotSame(c1.getId(), c2.getId());
	}
	
	@Test
	public void testAddGetMessages() {
		c1.addMessage(MESSAGE);
		c2.addMessage(MESSAGE);
		
		assertEquals(MESSAGE, c1.getMessages());
		assertEquals(MESSAGE, c2.getMessages());
	}
	
	@Test
	public void testAddRemoveGetUsers() {
		c1.addUser(u1);
		c1.addUser(u2);
		
		c1.addUser(u1);
		c2.addUser(u2);
		
		c1.getUsers();
		c2.getUsers();
		
		assertEquals(USERS, c1.getUsers());
		assertEquals(USERS, c2.getUsers());
	}
	
}
