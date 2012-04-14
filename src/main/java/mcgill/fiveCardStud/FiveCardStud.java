package mcgill.fiveCardStud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mcgill.game.ClientNotification;
import mcgill.game.Config;
import mcgill.game.Database;
import mcgill.game.Server;
import mcgill.game.User;
import mcgill.poker.Deck;
import mcgill.poker.Hand;
import mcgill.poker.HandRank;
import mcgill.poker.Player;
import mcgill.poker.Pot;
import mcgill.poker.OutOfMoneyException;
import mcgill.poker.TooFewCardsException;
import mcgill.poker.TooManyCardsException;

/**
 * The FiveCardStud class implements the rule of the five-card stud poker to the game.
 */
public class FiveCardStud implements Runnable {
	/**
	 * Player folds
	 */
	public static final int FOLDED = -1;
	
	/**
	 * Player bets
	 */
	public static final int BETTING = 0;
	
	/**
	 * Player puts all in
	 */
	public static final int ALL_IN = 1;
	
	/**
	 * Max raises
	 */
	private int maxRaises;
	
	/**
	 * The round the game is in
	 */
	private int street;
	
	/**
	 * Raises
	 */
	private int raises;
	
	/**
	 * Minimum bet
	 */
	private int lowBet;
	
	/**
	 * Bring in 
	 */
	private int bringIn;
	
	/**
	 * The deck of cards
	 */
	private Deck deck;
	
	/**
	 * Players in the game
	 */
	private List<Player> players;
	
	/**
	 * List of all the player's pots
	 */
	private List<Pot> pots;
	
	/**
	 * The starting player
	 */
	private int startingPlayer;
	
	/**
	 * Winner of the game
	 */
	private String trueWinner;
	
	/**
	 * FiveCardStud constructor
	 * @param players
	 * @param lowBet
	 * @param maxRaises
	 * @param bringIn
	 */
	public FiveCardStud(List<Player> players, int lowBet, int maxRaises, int bringIn) {
		this.raises = 0;
		this.players = players;
		this.pots= new ArrayList<Pot>();
		this.deck = new Deck();
		this.street = 2;
		this.lowBet = lowBet;
		this.maxRaises = maxRaises;
		this.bringIn = bringIn;
		this.startingPlayer = 0;
		
		this.trueWinner = "";
	}
	
	/**
	 * Starts the game
	 */
	public void run() {
		try {
			this.playRound();
			System.out.println("Round is done");
		} catch (Exception e) {
			System.out.println("*** PLAY ROUND EXCEPTION ***");
			e.printStackTrace();
		}
	}
	
	/**
	 * Return the amount in the pot
	 * @return int
	 */
	public int getTotalPot() {
		int total = 0;
		
		for (Player player : this.players) {
			total += player.getAmountInPots();
		}
		
		return total;
	}
	
	/**
	 * Plays the round
	 * @throws TooFewCardsException
	 * @throws TooManyCardsException
	 * @throws OutOfMoneyException
	 */
	public void playRound() throws TooFewCardsException, TooManyCardsException, OutOfMoneyException {
		while (this.street < 6) {
			if (this.street == 2) {
				initialize();
			}
			
			if (onlyOneBetting()) break;
			
			for(Player player : this.players) {
				if (!player.isFolded()) {
					player.addCard(this.deck.getTop());
				}
				emitHands();
			}
			
			betting();
		}
		
		makePots();
		dividePots();
		
		Database db = new Database(Config.REDIS_HOST, Config.REDIS_PORT);
		Map<String, Integer> credit_map = new HashMap<String, Integer>();
		
		for (Player player : this.players) {
			potAndStatusNotification(player);
			
			User user = db.getUser(player.getUsername(), false);
			user.setCredits(player.getTotalMoney());
			db.setUser(user);
			
			credit_map.put(player.getUsername(), player.getTotalMoney());
		}
		
		emitEndOfRound(credit_map);
		db.close();
	}
	
	/**
	 * Notifies client at the end of round
	 * @param credit_map
	 */
	private void emitEndOfRound(Map<String, Integer> credit_map) {
		EndOfRound end = new EndOfRound(this.trueWinner, credit_map);
				
		for (Player player : this.players) {
			String session_str = Server.getUserSession(player.getUsername());
			ClientNotification notification = new ClientNotification(session_str);
			notification.sendEndOfRound(end);
			notification.close();
		}
	}

	/**
	 * Pot and status notification to the client server
	 * @param player
	 */
	private void potAndStatusNotification(Player player) {
		int[] current = new int[2];
		
		current[0] = getTotalPot();
		current[1] = player.getStatus();
		
		String session_str = Server.getUserSession(player.getUsername());
		ClientNotification notification = new ClientNotification(session_str);
		
		notification.potAndStatus(current);
		notification.close();
	}
	
	/**
	 * 
	 * @param username
	 * @param limits
	 * @return int
	 */
	private int getAction(String username, int[] limits) {
		String session_str = Server.getUserSession(username);
		ClientNotification notification = new ClientNotification(session_str);
		
		String command = notification.getCommand(limits);
		notification.close();
		
		return Integer.parseInt(command);
	}

	/**
	 * Sends the player's hands to the client server
	 */
	private void emitHands() {
		Map<String, Hand> hands = new HashMap<String, Hand>();
		
		for (Player player : this.players) {
			hands.put(player.getUsername(), player.getHand());
		}
		
		for (Player player : this.players) {
			String session_str = Server.getUserSession(player.getUsername());
			ClientNotification notification = new ClientNotification(session_str);
			notification.sendHand(hands);
			notification.close();
		}
	}
	
	/**
	 * Setup player for game: ante and face down card
	 * @throws TooFewCardsException
	 * @throws TooManyCardsException
	 * @throws OutOfMoneyException
	 */
	private void initialize() throws TooFewCardsException, TooManyCardsException, OutOfMoneyException {
		for(Player player : this.players) {
			try {
				player.bet(this.lowBet/4);
				
				player.addCard(this.deck.getTop()); //face down
				emitHands();
			} catch (OutOfMoneyException e) {
				this.players.remove(player);
			} catch (TooManyCardsException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	/**
	 * Players makes bets based on the round
	 * @throws OutOfMoneyException
	 * @throws TooFewCardsException
	 * @throws TooManyCardsException
	 */
	private void betting() throws OutOfMoneyException, TooFewCardsException, TooManyCardsException {
		int i = 1;
		boolean continueStreet = true;
		
		findStartingPlayer();
		
		while (continueStreet) {
			for (int j = startingPlayer; j < (startingPlayer + this.players.size()); j++){
				
				if ((noMoreCalls() || onlyOneBetting()) && i != 1) {
					continueStreet = false;
					this.startingPlayer = 0;
					this.raises = 0;
					this.street++;
					break;
				}
				
				int index = j;
				int betLimit;
				boolean firstPlayer = false;
				boolean secondPlayer = false;
			
				//Betting limits based on which street the round is in
				if (this.street == 2) {
					if (index == this.startingPlayer && i == 1) {
						betLimit = 0; 
						firstPlayer = true;
					} else if (index == (this.startingPlayer + 1) && i == 1) {
						betLimit = 0;
						secondPlayer = true;
					} else {
						betLimit = this.lowBet;
					}
				} else if (this.street == 3) {
					betLimit = this.lowBet;
				} else {
					betLimit = 2*(this.lowBet);
				}
				
				//Wraps around 
				if (index >= this.players.size()) {
					index = j - this.players.size();
				}
				
				Player currentPlayer = players.get(index);
				
				if (currentPlayer.isBetting()) {
					potAndStatusNotification(players.get(index));
					
					int callAmount = getCallAmount() - currentPlayer.getAmountInPots();
					
					if (raises >= maxRaises) {
						betLimit = 0;
					}
					
					int limitAmount;
					
					if (firstPlayer) {
						callAmount = this.bringIn;
						limitAmount = this.lowBet;
					} else if (secondPlayer) {
						limitAmount = this.lowBet;
					} else {
						limitAmount = callAmount + betLimit;
					}
						
					int[] limits = {callAmount, limitAmount};	
					
					int action = getAction(players.get(index).getUsername(), limits);
					
					System.out.println("Action for " + players.get(index).getUsername() + " is: " + action);
					
					if (action == 0);
					else if (action == -1) {
						currentPlayer.setStatus(FOLDED);
					} else {
						if (action > callAmount) {this.raises++;}
						if (action >= currentPlayer.getTotalMoney()) {
							currentPlayer.bet(currentPlayer.getTotalMoney());
							currentPlayer.setStatus(ALL_IN);
						} else {
							currentPlayer.bet(action);
						}
					}
					
					//GameTest.printAmountInPots(players.get(index));
					System.out.println("\n ---------------------------------------- \n");
				}	
			}
			
			i++;
		}
	}

	/**
	 * Finds the starting player by comparing hands
	 * @throws TooFewCardsException
	 * @throws TooManyCardsException
	 */
	//Fix 2,3,4 cards (it for some reason does not just look for pairs, three of a kind, four of a kind, 2 pairs)
	private void findStartingPlayer() throws TooFewCardsException, TooManyCardsException {
		int i = 0; 
		
		if (street == 2) {
			for (Player player : this.players) {
				if (HandRank.compareHands(players.get(startingPlayer).getHand(), player.getHand(), 1) == 0) {
					startingPlayer = i;
				}
				i++;
			}
		} else {
			for (Player player : this.players) {
				if (player.isBetting() && (HandRank.compareHands(players.get(startingPlayer).getHand(), player.getHand(), street - 1) == 1)) {
					startingPlayer = i;
				}
				i++;
			}
		}
	}
	
	/**
	 * Adds the pot of each player's pot
	 */
	private void makePots() {
		Player[] playerArray = new Player[this.players.size()]; 
		this.players.toArray(playerArray);
		
		Arrays.sort(playerArray);
		
		int[] amountInPots = new int[playerArray.length];
		for (int i = 0; i < playerArray.length; i++) {
			amountInPots[i] = playerArray[i].getAmountInPots();
		}
		
		for (int i = 0; i < playerArray.length; i++) {
			Player player = playerArray[i];
			
			if (player.isAllIn() && (amountInPots[i] != 0)) {
				Pot pot = new Pot();
				pot.setLimit(amountInPots[i]);
				
				for (int j = 0; j < playerArray.length; j++) {
					if (amountInPots[j] != 0) {
						if (playerArray[j].isFolded()) {
							if (amountInPots[j] > pot.getLimit()) {
								pot.addToPot(pot.getLimit());
								amountInPots[j] -= pot.getLimit();
							} else {
								pot.addToPot(amountInPots[j]);
								amountInPots[j] = 0;
							}
						} else {
							if (amountInPots[j] > pot.getLimit()) {
								if (pot.containsPlayer(playerArray[j])) {
									pot.addToPot(pot.getLimit());
								} else {
									pot.addPlayer(playerArray[j], pot.getLimit());
								}
								amountInPots[j] -= pot.getLimit();
							} else {
								if (pot.containsPlayer(playerArray[j])) {
									pot.addToPot(amountInPots[j]);
								} else {
									pot.addPlayer(playerArray[j], amountInPots[j]);
								}
								amountInPots[j] = 0;
							}
						}
					}
				}
				
				this.pots.add(pot);
			}
		}
		
		Pot pot = new Pot();
		
		for (int j = 0; j < playerArray.length; j++) {
			if (amountInPots[j] != 0) {
				if (playerArray[j].isFolded()) {
					pot.addToPot(amountInPots[j]);
					amountInPots[j] = 0;
				} else {
					if (pot.containsPlayer(playerArray[j])) {
						pot.addToPot(amountInPots[j]);
					} else {
						pot.addPlayer(playerArray[j], amountInPots[j]);
					}
					amountInPots[j] = 0;
				}
			}
		}
		
		this.pots.add(pot);
	}
	
	/**
	 * Determines if there is only one player left betting
	 * @return boolean
	 */
	private boolean onlyOneBetting() {
		int playersBetting = 0;
		
		for (Player player : this.players) {
			if (player.isBetting()) {
				playersBetting++;
			}
		}
		
		if (playersBetting <= 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Determines if player can call
	 * @return boolean
	 */
	private boolean noMoreCalls() {
		for (Player player : this.players) {
			if (player.isBetting()) {
				int moneyToMatch = player.getAmountInPots();
				for (Player tmpPlayer : this.players) {
					if (tmpPlayer.isBetting()) {
						if (tmpPlayer.getAmountInPots() != moneyToMatch) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Return the call amount
	 * @return int
	 */
	private int getCallAmount() {
		int callAmount = 0;
		
		for (Player player : this.players) {
			if ((!player.isFolded()) && (player.getAmountInPots() > callAmount)) {
				callAmount = player.getAmountInPots();
			}
		}
		
		return callAmount;
	}
	
	/**
	 * Divides the pot after the game ends
	 * @throws TooFewCardsException
	 * @throws TooManyCardsException
	 */
	private void dividePots() throws TooFewCardsException, TooManyCardsException {
		//need to account for ties
		Boolean first = true;
		
		for (Pot pot : this.pots) {
			int winningPlayer = 0;
			int winners = 0;
			int i = 0;
			ArrayList<Player> potPlayers = pot.getPlayers();
			
			for (Player player : potPlayers) {
				if ((!player.isFolded()) && (HandRank.compareHands(potPlayers.get(winningPlayer).getHand(), player.getHand(), 5) == 1)) {
					winningPlayer = i;
				}
				i++;
			}
			
			Player winner = potPlayers.get(winningPlayer);
			
			if (first) {
				this.trueWinner = winner.getUsername();
				first = false;
			}
			
			for (Player player : this.players) {
				if ((!player.isFolded()) && (HandRank.compareHands(winner.getHand(), player.getHand(), 5) == -1)) {
					player.setWinner(true);
					winners++;
				}
			}
			
			for (Player player : this.players) {
				if (player.isWinner()) {
					player.addMoney(pot.getTotalAmount()/winners);
					player.setWinner(false);
				}
			}
		}
	}
}

