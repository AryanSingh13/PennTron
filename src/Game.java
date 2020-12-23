import java.awt.event.*;
import javax.swing.*;

public class Game implements Runnable {
    
    public String[] getPlayerNames(JFrame f) {
        String[] playerCounts = {"4", "3", "2"};
        
        int n = JOptionPane.showOptionDialog(f, "How many players?", "Welcome.", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
                playerCounts, playerCounts[2]);
        
        if (n == -1) {
            n = 0;
        }
        
        int count = Integer.valueOf(playerCounts[n]);
        
        String p1 = JOptionPane.showInputDialog("Who is the first player?");
        String p2 = JOptionPane.showInputDialog("Who is the second player");
        String p3 = null;
        String p4 = null;
        
        if (count >= 3) {
            p3 = JOptionPane.showInputDialog("Who is the third player");
        } 
        if (count == 4) {
            p4 = JOptionPane.showInputDialog("Who is the fourth player");
        }
        
        //input validation
        if (p1 == null) {
            p1 = "";
        } else {
            p1 = p1.replaceAll("\\s+", "");
        }
        
        if (p2 == null) {
            p2 = "";
        } else {
            p2 = p2.replaceAll("\\s+", "");
        }
        
        if (p3 != null) {
            p3 = p3.replaceAll("\\s+", "");
        }
        
        if (p4 != null) {
            p4 = p4.replaceAll("\\s+", "");
        }
        
        return new String[] {p1, p2, p3, p4};
    }
    
    public String instructions() {
        return "Hello. Welcome to PennTron. Choose the number of players (between 2 and 4) and "
                    + "\n" + "input the player names (no spaces allowed) to begin your journey. "
                    + "Players will use key "
                    + "sets " + "\n" + "up arrow, left arrow, down arrow, right arrow to move up "
                    + "left down and right." + "\n" + "For players 2, 3, and 4, for the same "
                    + "directions the keysets " + "are WASD, TFGH, " + "\n" + "and IJKL "
                    + "respectively. As a player moves around the " + "map, they leave a trail" + 
                    "\n" + "behind them. Hitting your own or another " + "players trail will cause "
                    + "you to die." + "\n" + "Moving off of the map will " + "cause you to die. "
                    + "Hitting a bomb, depicted by the " + "\n" + "bomb image, will cause you to "
                    + "die. The player that stays alive the longest " + "\n" + "wins. Run into a "
                    + "yellow square to decrease your speed or a magenta square to " + "\n" + 
                    "increase it. Running into a green circle will collapse all of your prior walls"
                    + "\n" + ", giving you and the enemy more room to move about. At the top of the"
                    + " game, " + "\n" + "you can notice the death of recent players and the high "
                    + "score - which is the" + "\n" + " longest anyone has stayed alive. "
                    + "Good luck.";
    }
    
    public void run() {
        final JFrame frame = new JFrame("PENN TRON");
        frame.setLocation(300, 300);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        JOptionPane.showMessageDialog(frame, instructions(), "Instructions", 
                JOptionPane.INFORMATION_MESSAGE);
        
        String[] players = getPlayerNames(frame);
                
        final JPanel score_panel = new JPanel();
        frame.add(score_panel);
        final JLabel score = new JLabel("");
        score_panel.add(score);
        
        final JPanel status_panel = new JPanel();
        frame.add(status_panel);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        
        final JPanel current_score = new JPanel();
        frame.add(current_score);
        final JLabel current = new JLabel("Test");
        current_score.add(current);
       
        final GameMap map = new GameMap(status, score, current, players[0], players[1], players[2], 
                players[3]);
        
        frame.add(map);
        
        final JPanel control_panel = new JPanel();
        frame.add(control_panel);
        
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                map.reset();
            }
        });
        control_panel.add(reset);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        map.reset();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
