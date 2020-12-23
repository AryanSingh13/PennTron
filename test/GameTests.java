import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.util.*;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;

class GameTests {
    
    @Test
    public void testCheckInPlay() {
        Player p = new Player("player", 1, 0, 0, Color.BLACK, 4, 100, 100);
        assertTrue(p.inPlay());
        
        p.moveTo(50, 50);
        assertTrue(p.inPlay());
        
        p.moveTo(101, 50);
        assertFalse(p.inPlay());
        
        p.moveTo(50, -1);
        assertFalse(p.inPlay());
    }
    
    @Test
    public void testMove() {
        int startX = 50;
        int startY = 50;
        Player p = new Player("player", 1, startX, startY, Color.BLACK, 3, 100, 100);
        
        p.move(3);
        assertEquals(p.getX(), startX - 2);
        assertEquals(p.getY(), startY);
        
        startX = p.getX();
        startY = p.getY(); 
        
        p.move(2);
        assertEquals(p.getX(), startX);
        assertEquals(p.getY(), startY - 2);
        
        startX = p.getX();
        startY = p.getY(); 
        
        p.move(1);
        assertEquals(p.getX(), startX + 2);
        assertEquals(p.getY(), startY);
        
        startX = p.getX();
        startY = p.getY(); 
        
        p.move(4);
        assertEquals(p.getX(), startX);
        assertEquals(p.getY(), startY + 2);
        
    }
    
    @Test
    public void testEquals() {
        Player p = new Player("player", 1, 0, 0, Color.BLACK, 3, 100, 100);
        Player p2 = new Player("player2", 1, 92, 19, Color.BLACK, 3, 100, 150);
        assertTrue(p.equals(p2));
    }
    
    @Test
    public void testNotEquals() {
        Player p = new Player("player", 1, 0, 0, Color.BLACK, 3, 100, 100);
        Player p2 = new Player("player2", 2, 92, 19, Color.BLACK, 3, 100, 150);
        assertFalse(p.equals(p2));
    }
    
    @Test
    public void testHasBeen() {
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        
        p.move(1);
        p.move(2);
        
        assertTrue(p.hasBeen(50, 50, false));
        assertTrue(p.hasBeen(51, 50, false));
        assertTrue(p.hasBeen(52, 50, false));
        assertTrue(p.hasBeen(52, 49, false));
        assertTrue(p.hasBeen(52, 48, false));
        assertFalse(p.hasBeen(52, 47, false));
    }
    
    @Test
    public void testHasBeenSelf() {
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        
        p.move(1);
        p.move(2);
        
        assertTrue(p.hasBeen(50, 50, true));
        assertTrue(p.hasBeen(51, 50, true));
        assertTrue(p.hasBeen(52, 50, true));
        assertFalse(p.hasBeen(52, 49, true));
        assertFalse(p.hasBeen(52, 48, true));
    }

    @Test
    public void testPowerUpContains() {
        PowerUp bomb = new Bomb(70, 30);
        
        assertTrue(bomb.contains(66, 30));
        assertFalse(bomb.contains(65, 30));        
        assertTrue(bomb.contains(66, 26));
        assertFalse(bomb.contains(66, 35));
    }
    
    @Test
    public void testBomb() {
        PowerUp bomb = new Bomb(70, 30);
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        bomb.interact(p);
        assertFalse(p.isBombed());
        p.moveTo(68, 31);
        bomb.interact(p);
        assertTrue(p.isBombed());
    }
    
    @Test
    public void testSpeedUp() {
        PowerUp su = new SpeedUp(70, 30);
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        su.interact(p);
        assertEquals(p.getSpeed(), 2);
        p.moveTo(68, 31);
        su.interact(p);
        assertEquals(p.getSpeed(), 4);
    }
    
    @Test
    public void testSlowDown() {
        PowerUp sd = new SlowDown(70, 30);
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        sd.interact(p);
        assertEquals(p.getSpeed(), 2);
        p.moveTo(68, 31);
        sd.interact(p);
        assertEquals(p.getSpeed(), 1);
    }
    
    @Test
    public void testInverter() {
        PowerUp sd = new Inverter(70, 30);
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        p.moveTo(68, 31);
        p.move(1);
        sd.interact(p);
        assertFalse(p.hasBeen(68, 31, false));
        assertFalse(p.hasBeen(69, 31, false));
    }
    
    @Test
    public void testSetSpeed() {
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        p.setSpeed(0);
        assertEquals(p.getSpeed(), 1);
        p.setSpeed(5);
        assertEquals(p.getSpeed(), 5);
    }
    
    @Test
    public void testSetDirection() {
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        p.setDirection(-1);
        assertEquals(p.getDirection(), 1);
        p.setDirection(5);
        assertEquals(p.getDirection(), 4);
    }
    
    @Test
    public void testEncapsulation() {
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        @SuppressWarnings("unused")
        boolean b = p.isBombed();
        b = true;
        assertFalse(p.isBombed());
    }
    
    @Test
    public void testWin() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        gm.resetFull();
        gm.addPlayer(50, 50, 1);
        gm.checkWon(gm.getPlayers());
        assertFalse(gm.isPlaying());
    }
    
    @Test
    public void testNotWon() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        gm.resetFull();
        gm.addPlayer(50, 50, 1);
        gm.addPlayer(70, 75, 2);
        gm.checkWon(gm.getPlayers());
        assertTrue(gm.isPlaying());
    }
    
    @Test
    public void testCheckPowerUpsFast() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        gm.resetFull();
        gm.addPowerUp(new SpeedUp(50, 50));
        gm.checkPowerUps(p);
        assertEquals(p.getSpeed(), 4);
    }
    
    @Test
    public void testCheckPowerUpsSlow() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        gm.resetFull();
        gm.addPowerUp(new SlowDown(51, 51));
        gm.checkPowerUps(p);
        assertEquals(p.getSpeed(), 1);
    }
    
    @Test
    public void testCheckPowerUpsBomb() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        gm.resetFull();
        gm.addPowerUp(new Bomb(49, 49));
        gm.checkPowerUps(p);
        assertTrue(p.isBombed());
    }
    
    @Test
    public void checkDead() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        Player p2 = new Player("player", 2, 52, 48, Color.BLACK, 3, 100, 100);
        gm.resetFull();
        Map<Integer, Player> mp = new TreeMap<Integer, Player>();
        p.move(1);
        p2.move(4);
        gm.addPlayer(p);
        gm.addPlayer(p2);
        mp.put(p.getID(), p);
        mp.put(p2.getID(), p2);
        assertTrue(gm.checkDead(mp, p2));
    }
    
    @Test
    public void checkDeadFirst() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        Player p2 = new Player("player", 2, 50, 48, Color.BLACK, 3, 100, 100);
        gm.resetFull();
        Map<Integer, Player> mp = new TreeMap<Integer, Player>();
        p.move(1);
        p2.move(4);
        gm.addPlayer(p);
        gm.addPlayer(p2);
        mp.put(p.getID(), p);
        mp.put(p2.getID(), p2);
        assertTrue(gm.checkDead(mp, p2));
    }
    
    @Test
    public void checkDeadInMiddle() {
        GameMap gm = new GameMap(new JLabel(), new JLabel(), new JLabel(), "p1", "p2", "p3", "p4");
        Player p = new Player("player", 1, 50, 50, Color.BLACK, 3, 100, 100);
        Player p2 = new Player("player", 2, 51, 49, Color.BLACK, 3, 100, 100);
        gm.resetFull();
        Map<Integer, Player> mp = new TreeMap<Integer, Player>();
        p.move(1);
        p2.move(4);
        gm.addPlayer(p);
        gm.addPlayer(p2);
        mp.put(p.getID(), p);
        mp.put(p2.getID(), p2);
        assertTrue(gm.checkDead(mp, p2));
    }
}
