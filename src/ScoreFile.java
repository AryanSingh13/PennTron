import java.io.*;
import java.util.*;

public class ScoreFile {
    
    private String line;
    private BufferedReader bfr;    
    private BufferedWriter bfw;
    
    List<String[]> scores;
    
    public ScoreFile(String filename) {
        scores = new ArrayList<String[]>();
        try {
            if (filename != null) {
                bfr = new BufferedReader(new FileReader(filename));
                bfw = new BufferedWriter(new FileWriter(new File(filename), true));
            }  else {
                throw new FileNotFoundException();
            } 
            
            line = bfr.readLine();
            bfw.newLine();
        } catch (FileNotFoundException e) {
            System.out.println("Get a more existent file.");
            line = null;
        } catch (IOException e) {
            System.out.println("Get a better file.");
            line = null;
        }
    }
    
    public boolean hasNext() {
        return line != null;
    }
    
    public String next() {
        if (!hasNext()) {
            try {
                bfr.close();
            } catch (IOException e) {
                throw new NoSuchElementException();
            }
            throw new NoSuchElementException();
        }
        
        String temp = line;
        
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            System.out.println("There's nothing left.");
        }
        
        return temp;
    }
    
    public String[] readTopScore() {
        while (hasNext()) {
            scores.add(next().split("\\s+"));
        }
        
        String first = "none 0";
        String second = "none 0";
        String third = "none 0";
                
        for (String[] s : scores) {
            if (s.length >= 2) {
                try { 
                    long record = Long.parseLong(first.split("\\s+")[1]);
                    long secondRecord = Long.parseLong(second.split("\\s+")[1]);
                    long thirdRecord = Long.parseLong(third.split("\\s+")[1]);
                    long curr = Long.parseLong(s[1]);
                    
                    if (curr > record) {
                        String temp = first;
                        String temp2 = second;
                        first =  s[0] + ": " + s[1];
                        second = temp;
                        third = temp2;
                    } else if (curr > secondRecord) {
                        String temp = second;
                        second = s[0] + ": " + s[1];
                        third = temp;
                    } else if (curr > thirdRecord) {
                        third = s[0] + ": " + s[1];
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Who wrote these scores?");
                }
            }
        }
        
        
        return new String[] {first, second, third};
    }
    
    public void writeNewScore(String name, long time) {
        long timeScore  = time / 1000000;
        if (name == null || name.equals("")) {
            name = "Unknown";
        }
        
        boolean writ = false;
        for (String[] c : scores) {
            if (c[0].equals(name) && c[1].equals(String.valueOf(timeScore))) {
                writ = true;
            }
        }
        
        if (!writ) {
            String score = name + " " + String.valueOf(timeScore);
            
            try {
                bfw.write(score);
                bfw.newLine();
                bfw.flush();
            } catch (IOException e) {
                System.out.println("Nice try.");
            }
        }
    }
    
    public void flush() {
        try {
            bfw.flush();
        } catch (IOException e) {
            System.out.println("Fine. Stay open.");
        }
    }
    
}
