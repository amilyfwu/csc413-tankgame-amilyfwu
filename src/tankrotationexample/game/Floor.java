package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Floor {
    private int x,y;
    private BufferedImage img;

    public Floor(int x, int y, BufferedImage img) {
      this.x = x;
      this.y = y;
      this.img = img;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img,x, y, null);
    }
}
