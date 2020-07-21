package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Breakable extends Wall{
    //int x, y;
    //BufferedImage wallImage;
    //int state = 2;

    public Breakable(int x, int y, BufferedImage wallImage) {
        super(x,y,wallImage);
    }

    //if bullet hits it remove the wall from the list otherwise act as a wall



    @Override
    public void drawImage(Graphics g){
        if(state == 2){
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(this.wallImage,x, y, null);
        }else if(state == 1){

        }
    }

    @Override
    public boolean canBreak() {
        return true;
    }
}
