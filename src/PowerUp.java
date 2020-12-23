
public abstract class PowerUp extends GameObject implements Comparable<PowerUp> {
        
    public PowerUp(int xpos, int ypos, int s, int courtWidth, int courtHeight) {
        super(xpos, ypos, s, courtWidth, courtHeight);
    }
    
    public abstract void interact(Player p);
        
    public int compareTo(PowerUp p) {
        if (getX() != p.getX()) {
            return getX() - p.getX();
        } else {
            return getY() - p.getY();
        }
    }
    
    public boolean contains(int ox, int oy) {
        return Math.abs(ox - getX()) < getSize() / 2 && Math.abs(oy - getY()) < getSize() / 2;
    }
    
    public boolean contains(Player p) {
        int ox = p.getX();
        int oy = p.getY();
        return Math.abs(ox - getX()) < getSize() / 2 && Math.abs(oy - getY()) < getSize() / 2;
    }
}
