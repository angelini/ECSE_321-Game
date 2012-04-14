package mcgill.game;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

public class MessageTest {
	
	private static String ID = "changethis";
	private static String MESSAGE = "changethis";
	private static String USERNAME = "changethis";
	private static String TIME = "changethis";
	private static String REDIS_HOST = "142.157.150.10";
	private static int REDIS_PORT = 6379;
	private static Database db;
	private static Message m1;
	private static Message m2;
	private static Map<String, String> INFO;

	@BeforeClass
	public static void constructTestObjects() {
		db = new Database(REDIS_HOST, REDIS_PORT);
		INFO = new HashMap<String, String>();
		
		m1 = new Message(MESSAGE, USERNAME, db);
		m2 = new Message(INFO);
	}
	
	@Test
	public void testGetMap() {
		assertEquals(INFO, m1.getMap());
		assertEquals(INFO, m2.getMap());
	}
	
	@Test
	public void testGetId() {
		assertEquals(ID, m1.getId());
		assertEquals(ID, m2.getId());
	}
	
	@Test
	public void testGetMessage() {
		assertEquals(MESSAGE, m1.getMessage());
		assertEquals(MESSAGE, m2.getMessage());
	}
	
	@Test
	public void testGetUsername() {
		assertEquals(USERNAME, m1.getUsername());
		assertEquals(USERNAME, m2.getUsername());
	}

	@Test
	public void testGetTime() {
		assertEquals(TIME, m1.getTime());
		assertEquals(TIME, m2.getTime());
	}
}
