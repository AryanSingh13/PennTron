import java.awt.Color;
import java.awt.Graphics;

public class SlowDown extends PowerUp {
    
    public SlowDown(int xpos, int ypos) {
        super(xpos, ypos, 8, 300, 300);
    }

    @Override
    public void interact(Player p) {
        if (this.contains(p)) {
            p.setSpeed(p.getSpeed() / 2);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(getX() - getSize() / 2, getY() - getSize() / 2, getSize(), getSize());
    }
}
