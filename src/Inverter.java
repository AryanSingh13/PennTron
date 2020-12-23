import java.awt.Color;
import java.awt.Graphics;

public class Inverter extends PowerUp {

    public Inverter(int xpos, int ypos) {
        super(xpos, ypos, 10,  300, 300);
    }

    @Override
    public void interact(Player p) {
        if (this.contains(p)) {
            p.clearHistory();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(getX() - getSize() / 2, getY() - getSize() / 2, getSize(), getSize());
    }

}
