package tankrotationexample.game;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends Stationary{
    public PowerUp(int x, int y, BufferedImage img, GameID id, Handler handler) {
        super(x, y, img, id, handler);
    }

}
