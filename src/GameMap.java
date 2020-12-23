import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameMap extends JPanel {
    
    private static final String FILENAME = 
            "/Users/aryansingh/eclipse-workspace/CIS120Workspace/PennTron/files/scores.txt";
    private ScoreFile sf;
    
    private Map<Integer, Player> players;
    private Set<PowerUp> interactables;
    
    private boolean playing = false;
    
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 300;
    
    public static final int INTERVAL = 35;
    private JLabel status;
    private JLabel score;
    private JLabel current;
    private String player1;
    private String player2;
    private String player3;
    private String player4;
    
    private long startTime;
    private long timePlayed;
    
    private static final int[][] KEYSETS = 
        {{KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN},
         {KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S},
         {KeyEvent.VK_H, KeyEvent.VK_T, KeyEvent.VK_F, KeyEvent.VK_G},
         {KeyEvent.VK_L, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K}};
    
    public GameMap(JLabel status, JLabel score, JLabel current, 
            String name1, String name2, String name3, String name4) {        
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        setBackground(Color.BLACK);
        
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();
        
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                changePlayerDir(e, 1);
                changePlayerDir(e, 2);
                if (name3 != null) {
                    changePlayerDir(e, 3);
                }
                if (name4 != null) {
                    changePlayerDir(e, 4);
                }
            }
        });
        
        this.status = status;
        this.score = score;
        this.current = current;
        this.player1 = name1;
        this.player2 = name2;
        this.player3 = name3;
        this.player4 = name4;
    }
    
    public void changePlayerDir(KeyEvent e, int playerID) {
        if (e.getKeyCode() == KEYSETS[playerID - 1][2]) {
            players.get(playerID).setDirection(3);
        } else if (e.getKeyCode() == KEYSETS[playerID - 1][0]) {
            players.get(playerID).setDirection(1);
        } else if (e.getKeyCode() == KEYSETS[playerID - 1][3]) {
            players.get(playerID).setDirection(4);
        } else if (e.getKeyCode() == KEYSETS[playerID - 1][1]) {
            players.get(playerID).setDirection(2);
        } 
    }
    
    public void reset() {        
        players = new TreeMap<Integer, Player>();
        interactables = new TreeSet<PowerUp>();
        sf = new ScoreFile(FILENAME);
        
        players.put(1, new Player(player1, 1, COURT_WIDTH / 3, COURT_HEIGHT / 3, 
                Color.CYAN, 4, COURT_WIDTH, COURT_HEIGHT)); 
        players.put(2, new Player(player2, 2, COURT_WIDTH / 3 * 2, COURT_HEIGHT / 3 * 2, 
                Color.RED, 2, COURT_WIDTH, COURT_HEIGHT));     
        
        if (player3 != null) {
            players.put(3, new Player(player3, 3, COURT_WIDTH / 3 * 2, COURT_HEIGHT / 3, 
                Color.WHITE, 3, COURT_WIDTH, COURT_HEIGHT));   
        }
        if (player4 != null) {
            players.put(4, new Player(player4, 4, COURT_WIDTH / 3, COURT_HEIGHT / 3 * 2, 
                Color.PINK, 1, COURT_WIDTH, COURT_HEIGHT));   
        }
                
        interactables.add(new SpeedUp(20, 150));
        interactables.add(new SpeedUp(150, 10));
        interactables.add(new SlowDown(250, 200));
        interactables.add(new Bomb(150, 150));
        interactables.add(new Bomb(250, 20));
        interactables.add(new Inverter(150, 250));

        playing = true;
        status.setText("Running...");
        sf.flush();
        score.setText("High Scores: " + sf.readTopScore()[0] + ", " + sf.readTopScore()[1] + ", " 
                + sf.readTopScore()[2]);
        current.setText("0");

        requestFocusInWindow();
        startTime = System.nanoTime();
    }
    
    public void checkPowerUps(Player p) {
        Iterator<PowerUp> iter = interactables.iterator();
        while (iter.hasNext()) {
            PowerUp pu = iter.next();
            if (pu.contains(p)) {
                pu.interact(p);
                iter.remove();
            }
        }
    }
    
    public void checkWon(Map<Integer, Player> t) {
        if (t.size() == 1) {
            Player p = (Player) t.values().toArray()[0];
            status.setText(p.getName() + " won!");
            timePlayed = System.nanoTime() - startTime;
            playing = false;
        } else if (t.size() == 0) {
            status.setText("Game over.");
        }
    }
    
    public boolean checkDead(Map<Integer, Player> t, Player p) {
        int x = p.getX();
        int y = p.getY();
        if (p.inPlay() && !p.isBombed()) {
            for (Player pn : players.values()) {
                boolean own = pn.equals(p);
                int[] ps = p.getLastSpot();
                int xshift = 0;
                int yshift = 0;
                if (ps != null) {
                    xshift = (p.getLastSpot()[0] - x) / 2;
                    yshift = (p.getLastSpot()[1] - y) / 2;
                }
                if ((pn.hasBeen(x, y, own) || pn.hasBeen(x + xshift, y + yshift, own)) && playing) {
                    t.remove(p.getID());
                    status.setText(p.getName() + " died!");
                }
            } 
        } else {
            t.remove(p.getID());
            status.setText(p.getName() + " died!");
        }
        return !t.containsKey(p.getID());
    }
        
    void tick() {
        if (playing) {
            Map<Integer, Player> temp = new TreeMap<Integer, Player>();
            temp.putAll(players);
            for (Player p : players.values()) {
                p.move();      
                checkPowerUps(p);               
                checkDead(temp, p); 
                checkWon(temp);
            }   
            players.clear();
            players.putAll(temp);
            
            if (!playing && players.size() != 0) {
                sf.writeNewScore(((Player) players.values().toArray()[0]).getName(), timePlayed);
            }
            
            current.setText("Score: " + String.valueOf((System.nanoTime() - startTime) / 1000000));
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        for (Player p : players.values()) {
            p.draw(g);           
        }
        
        for (PowerUp p : interactables) {
            p.draw(g);           
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
    
    //
    //METHODS ONLY TO TEST / ENSURED NOT TO BREAK ENCAPSULATION / NOT USED IN GAME FUNCTIONALITY
    //
    
    public void resetFull() {
        players = new TreeMap<Integer, Player>();
        interactables = new TreeSet<PowerUp>();
        playing = true;
    }
    
    public void addPlayer(int x, int y, int id) {
        while (players.containsKey(id)) {
            id++;
        }
        players.put(id, new Player("", id, x, y, 
                Color.WHITE, 4, COURT_WIDTH, COURT_HEIGHT)); 
    }
    
    public void addPlayer(Player p) {
        players.put(p.getID(), p);
    }
    
    public void removePlayer(int id) {
        players.remove(id);
    }
    
    public void addPowerUp(PowerUp p) {
        interactables.add(p);
    }
    
    public boolean checkPowerUp(PowerUp p) {
        return interactables.contains(p);
    }
    
    public Map<Integer, Player> getPlayers() {
        Map<Integer, Player> t = new TreeMap<Integer, Player>();
        t.putAll(players);
        return t;
    }
    
    public void movePlayer(Player p, int x, int y) {
        p.moveTo(x, y);
    }
    
    public boolean isPlaying() {
        return playing;
    }
}
