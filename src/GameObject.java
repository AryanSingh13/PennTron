import java.awt.Graphics;

public abstract class GameObject {
    
    private int x;
    private int y;
    
    private int size;
    
    private static int width;
    private static int height;
    
    public GameObject(int xpos, int ypos, int s, int courtWidth, int courtHeight) {
        width = courtWidth - getSize() / 2;
        height = courtHeight - getSize() / 2;
        
        this.x = Math.max(0, Math.min(width, xpos));
        this.y = Math.max(0, Math.min(height, ypos));
        this.size = s;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public boolean inPlay() {
        return getX() <= width && getX() >= 0 && getY() <= height && getY() >= 0;
    }
    
    public void setX(int px) {
        this.x = px;
    }
    
    public void setY(int py) {
        this.y = py;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public abstract void draw(Graphics g);
}
