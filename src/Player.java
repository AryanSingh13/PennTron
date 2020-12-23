import java.awt.*;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class Player extends GameObject implements Comparable<Player> {
    
    private int id;
    
    private int speed;
    private int dir;
    
    private final Color color;
    
    private boolean bombed = false;
    private String name;
    
    private List<int[]> moveHistory = new LinkedList<int[]>();
    
    public Player(String n, int number, int px, int py, Color c, int d, int courtWidth, 
            int courtHeight) {
        super(px, py, 4, courtWidth, courtHeight);
        this.name = n;
        
        this.id = number;             
        
        this.speed = 2;
        
        this.color = c;
        
        this.dir = Math.min(Math.max(1, d), 4);
    }
    
    /*** GETTERS **********************************************************************************/
    
    public String getName() {
        return this.name;
    }
    
    public int getID() {
        return this.id;
    }
    
    public int getSpeed() {
        return this.speed;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public int getDirection() {
        return this.dir;
    }
    
    public boolean isBombed() {
        return this.bombed;
    }
    
    /*** SETTERS **********************************************************************************/
    
    public void setSpeed(int s) {
        this.speed = Math.max(1, s);
    }
    
    public void setDirection(int d) {
        this.dir = Math.min(Math.max(1, d), 4);
    }
    
    public void setBombed() {
        this.bombed = true;
    }
    
    public void clearHistory() {
        this.moveHistory.clear();
    }
    
    /*** UPDATES AND OTHER METHODS ****************************************************************/
    
    public void move() {
        switch (dir) {
            case 1:
                this.setX(getX() + this.speed);
                moveHistory.add(new int[] {getX() - this.speed, getY()});
                moveHistory.add(new int[] {getX(), getY()});
                break;
            case 2:
                this.setY(getY() - this.speed);
                moveHistory.add(new int[] {getX(), getY() + this.speed});
                moveHistory.add(new int[] {getX(), getY()});
                break;
            case 3:
                this.setX(getX() - this.speed);
                moveHistory.add(new int[] {getX() + this.speed, getY()});
                moveHistory.add(new int[] {getX(), getY()});
                break;
            case 4:
                this.setY(getY() + this.speed);
                moveHistory.add(new int[] {getX(), getY() - this.speed});
                moveHistory.add(new int[] {getX(), getY()});
                break;
            default:
                break;
        }
    }
    
    //for testing purposes
    public void move(int dir) {
        this.setDirection(dir);
        this.move();
    }
    
    //for testing purposes
    public void moveTo(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
    
    public boolean hasBeen(int x, int y, boolean own) {
        int k = own ? 2 : 1;        
        
        for (int i = 0; i < moveHistory.size() - k; i++) {
            int[] prev = moveHistory.get(i);
            int[] next = moveHistory.get(i + 1);
            
            if (next[0] == x && ((prev[1] <= y && y <= next[1]) || 
                    (y <= prev[1] && y >= next[1]))) {
                return true;
            }
            
            if (next[1] == y && ((prev[0] <= x && x <= next[0]) || 
                    (x <= prev[0] && x >= next[0]))) {
                return true;
            }
        }
        
        return false;
    }
    
    public int[] getLastSpot() {
        if (moveHistory.isEmpty()) {
            return null;
        }
        return this.moveHistory.get(this.moveHistory.size() - 2);
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        Iterator<int[]> iter = moveHistory.iterator();
        
        if (iter.hasNext()) {
            int[] first = iter.next();
            while ((iter.hasNext())) {
                int[] second = iter.next();
                g.drawLine(first[0], first[1], second[0], second[1]);
                first = second;
            }
        }
        
        g.fillOval(getX() - getSize() / 2, getY() - getSize() / 2, getSize(), getSize());
    }

    public int compareTo(Player p) {
        return this.id - p.getID();
    }
    
    public boolean equals(Player p) {
        return this.id == p.getID();
    }
}
