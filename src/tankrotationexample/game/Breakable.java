package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Breakable extends Wall{
    private BufferedImage wallImage2;
    public Breakable(int x, int y, BufferedImage wallImage, GameID id, Handler handler, BufferedImage wallImage2) {
        super(x,y,wallImage,id, handler);
        this.wallImage2 = wallImage2;
    }
    @Override
    public void drawImage(Graphics g){
        if(state == 2){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.img,x, y, null);
        }else if(state == 1){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.wallImage2,x, y, null);
        }
    }
}
