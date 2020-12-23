=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: aryansng
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. The first core concept I utilized was collections. With this, I utilized collections to model
  the game state with a map storing the players in the game and a set storing the PowerUps in the 
  game. Additionally, I utilized a list to store the move history for each player. To iterate 
  through the move history, I utilized an iterator that went through the LinkedList and drew
  lines from consecutive points. For this, I used a collection since the number of moves a player 
  makes is not a fixed size but must rather be dynamically allocated in size which a collection
  does, and a list was the correct choice since the order of the moves mattered in order to draw 
  the lines on the board that represented the move history. Additionally, I utilized an iterator
  to iterate through the set of PowerUps. Again, a collection was ideal for storing the number of
  PowerUps because this should be dependent on however many I wish to add on any given game and 
  not constrained by the original size of the array, and the order of the PowerUps did not matter. 
  Lastly, I utilized a map to store the players and iterated through this with a for-each loop, 
  utilizing a temporary map for removal so as to avoid a ConcurrentModificationException being 
  thrown. I utilized a map over a different collections since I needed to be able to quickly lookup
  certain players in order to call methods on them after key events, and a map was the best way to 
  do so, rather than a set or list. I ended up utilizing collection instead of a 2D array for the
  map as I had previously planned since I realized that an array would be an inefficient way to
  store move history and I would have to create a unique code for each PowerUp in order to place 
  it into an integer array and there would be large amounts of wasted space and computational time
  looking through an array rather than simply utilizing a collection to model game state. NOTE: 
  I had to use a regular for loop once to iterate through the move history as I needed to exclude
  the final move in some cases and could not do this with a for each loop or iterator.

  2. The next type of core concept I utilized was File I/O. As in my README, I incorporated high
  score functionality by including a writer that writes the time that the winner of any given
  round survived for to a text file, storing both the name and the score of the winner of that round
  in a text file. Then, instead of utilizing a reader to read in game levels, I realized it would 
  make more sense for my game to parse the test file and show the high scores, which I utilized
  a reader for. The reader is robust and handles all sorts of exceptions, and since the game has a
  condition that names cannot have spaces in them (and formats this in the code), also works with 
  bad inputs such as null or no name given, storing the default value of "Unknown" in the text file.
  Additionally, for other bad input it does not break down, but will rather skip that input, which 
  cannot happen unless intentionally passed in, and yet is still handled. This data for high scores 
  is persistent across game sessions.

  3. The third game component I utilized was inheritance and subtyping for dynamic dispatch. I 
  created a class hierarchy with GameObject at the top being an abstract class, and the children 
  of this being abstract class PowerUp and class Player. Then, PowerUp has four children which
  are the different styles of PowerUps. I utilized an abstract class for the PowerUp as I required
  each child if this class to be able to interact with the player that touches it in a unique way,
  in addition to being drawn a unique way, so subtyping made sense of this use case. The interaction
  method was used in addition to changing several fields, such as size for the SpeedUp/SlowDown 
  against the bomb, and clearly the way they were drawn. Each subtype has a distinct method
  implementation for the interact method, and this is different than just creating a subtype to
  change a specific field such as speed, since these do not actually change fields but change how
  they interact amongst players. The draw method is inherited from the base which is the GameObject
  class. Then, in the code of GameMap the PowerUps are statically typed by their abstract parent 
  type, and the interact method is called on each in checkPowerUps which relies on the distinct
  implementation of the method of the objects dynamic type.

  4. My final core component that I utilized for this project was the use of a testable component.
  I utilized JUnit tests in the test folder of my project to test specific methods of the model
  of my game, which were designed to each do one separate thing and divided so that when tested
  they would each test one functionality at a time. I did not test GUI components or rely on these
  in order to design methods and test, as per the instructions. I was sure to utilize the correct
  assertions in order to maintain good testing style and tested all possible player and PowerUp
  functionality, which is what is executed in the GameMap file. The methods in the GameMap file that
  were not built off of already testes functionality relied on user input such as KeyEvents and 
  the piazza cited not to test these but rather only the model instead of the view or controller as
  in this case. Then, I also utilized testing of edge cases such as one pixel distinctions in 
  object interaction and bad values being passed in for speed or direction. I allow bad values
  to be passed in for location and utilize the inPlay method, which is also tested for edge cases
  to handle this.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Each class and an overview of its functionality is described below:
  Bomb - this class extends PowerUp and thus is a grandchild of GameObject. It is used as a 
  class that when a player comes close to it interacts with this player and "kills them," removing
  them from the game. This is drawn using the image in the files folder.
  SlowDown - this class also extends PowerUp and is a grandchild of GameObject. When this PowerUp
  contains a player it interacts with the player by affecting the player's speed, slowing the player
  to half of their speed, but also keeping in mind a players speed cannot hit 0. It also has its own
  unique color and draw capability, as with any child class.
  SpeedUp - this class also extends PowerUp and is a grandchild of GameObject. When this PowerUp
  contains a player it interacts with the player by affecting the player's speed, speeding the 
  player to twice their speed. It also has its own unique color and draw capability, as with any 
  child class.
  Inverter - this final extending class of PowerUp which essentially removes all of the previous
  moves and thus obstacles players must avoid for any specific player who this PowerUp is interacted
  with.
  Game - this is the main Game class which utilizes Java Swing, shows the user the instructions,
  takes and validates the user input for the names and essentially lays out the entire game
  application for use.
  GameMap - this is the class that is used to store the interaction between user input and game
  components as well as actually run the game by collecting all of the players and utilizing
  methods in the other classes that interact with these. Additionally this class paints the canvas
  and handles functionality for adding players and PowerUps to the game.
  GameObject - this is the abstract parent class for a general object in the same that validates 
  that objects are created within the court and then provides position and size functionality while
  mandating that children implement how to draw themselves, and also adds a method to check if an 
  object is in the game map.
  Player - this is the class where most of the backend game functionality is held, in that it
  represents a player of the game and gives all the functionality of movement and position and
  drawing needed for a player. It also houses the movement history of a player to later check if 
  any player goes somewhere another player has already been.
  ScoreFile - this is the class that holds the File I/O functionality, in that it has functionality
  to iterate through a text file which holds names and high scores and then can read the file to
  return the three highest scores recorded for the game or write another score to the file to check
  if it is the highest score.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  For this game in particular, one significant stumbling block was at first I attempted to 
  implement the housing of the game state with 2D arrays and ended up reusing collections and arrays
  to house the same game state, which led to design overlap and many inefficiencies when attempting
  to move players and test each method. Another stumbling block was incorporating the current score,
  which I was unsure how to display but then found System.nanoTime() in the javadocs.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  The functionality of the design is well separated I believe, and all private state is well
  encapsulated since values cannot be changed outside of class and only necessary ones are even
  accessible. If I were to refactor, I would potentially change the user input handing with the
  strings into the GameMap constructor, but the implementation in the actual Game class has the same
  impact, just being slightly more cluttered to read.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  Citation for bomb image utilized in game:
  cartoon icons PNG Designed By ๋๋JidapaM from <a href="https://pngtree.com">Pngtree.com</a>
  
  Additionally I utilized the Javadocs heavily for the java swing components seen in the Game class.
  Particularly this held true for the use of JOptionPane in order to input player names.
  
  https://mkyong.com/swing/java-swing-joptionpane-showoptiondialog-example/
  I also utilized this tutorial for the JOptionPane to learn how to use showing an option dialog.
  
