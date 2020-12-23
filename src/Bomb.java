import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bomb extends PowerUp {
    
    public static final String IMG_FILE = "files/bomb.png";
    
    private static BufferedImage img;
    
    public Bomb(int xpos, int ypos) {
        super(xpos, ypos, 10, 300, 300);
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }       
    }

    @Override
    public void interact(Player p) {
        if (this.contains(p)) {
            p.setBombed();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getX() - getSize() / 2, this.getY() - getSize() / 2, 
                getSize(), getSize(), null);
    }

}
