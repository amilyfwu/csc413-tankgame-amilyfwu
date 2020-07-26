package tankrotationexample.game;



import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 *
 * @author anthony-pc
 */
public class Tank extends Moveable{


    //private int x,y,vx,vy;
    //private float angle;
    //private int final R = 2;
    private final float ROTATIONSPEED = 3.0f;
    //
    //private Rectangle hitBox;
    private ArrayList<Bullet> ammo;

    //private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean collide;


    Tank(int x, int y, int vx, int vy, float angle, BufferedImage img,GameID id, Handler handler) {
        super(x,y,vx,vy,angle,img,id,handler);
        //this.x = x;
        //this.y = y;
        //this.vx = vx;
        //this.vy = vy;
        //this.img = img;
        //this.angle = angle;
        //this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.ammo = new ArrayList<>();
    }

    //void setX(int x){ this.x = x; }

   // void setY(int y) { this. y = y;}

    void setCollision(boolean collide){
        this.collide = collide;
    }

    //int getX(){ return x; }

    //int getY(){ return y; }

    public boolean isUpPressed(){
        return UpPressed;
    }
    public boolean isDownPressed(){
        return DownPressed;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed(){
        this.ShootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed(){
        this.ShootPressed = false;
    }

    private void doCollision(){
        if(this.UpPressed){
            for(int i = 0; i<=10; i++){
                this.moveBackwards();
            }
        }
        if (this.DownPressed){
            for(int i = 0; i<=10; i++){
                this.moveForwards();
            }
        }
        this.collide = false;
    }

    private void doCollision2(){
        try {
            this.handler.gameObjects.forEach(gameObject -> {
                GameID gameIDTemp = gameObject.getId();
                //tank colliding will walls
                if (gameIDTemp == GameID.Wall && gameObject instanceof Wall) {
                    if (this.getHitBox().intersects(gameObject.getHitBox()) && (((Wall) gameObject).getState() == 2)) {
                        setCollision(true);
                    }
                }
                //tank colliding with tank
                if ((gameIDTemp == GameID.Tank1 && this.getId() != GameID.Tank1) || (gameIDTemp == GameID.Tank2 && this.getId() != GameID.Tank2)) {
                    if (this.getHitBox().intersects(gameObject.getHitBox())) {
                        setCollision(true);
                    }
                }
                //tank colliding with PowerUps


                //bullets colliding with everything
                //exclude the tank shooting the bullet
                if (gameIDTemp != this.getId()) {
                    try {
                        //checking bullet Collision
                        this.ammo.forEach(bullet -> {
                            //bullet hitting gameObjects and removing that bullet
                            if (bullet.getHitBox().intersects(gameObject.getHitBox())) {
                                if((gameIDTemp == GameID.Wall && gameObject instanceof Breakable && ((Breakable) gameObject).getState() == 1) || gameIDTemp == GameID.PowerUp ){
                                }else{
                                    this.ammo.remove(bullet);
                                }

                                if (gameIDTemp == GameID.Tank1) {
                                    //health bar and lives left

                                } else if (gameIDTemp == GameID.Tank2) {
                                    //health bar and lives left

                                } else if (gameIDTemp == GameID.Wall && gameObject instanceof Breakable) {
                                    ((Breakable) gameObject).setState(1);
                                }
                            }
                        });
                    } catch (ConcurrentModificationException ex) {}
                }
            });
        }catch (ConcurrentModificationException ex){}

    }

    public void update() {

        doCollision2();
        if(this.collide){
            doCollision();
        }else {
            if (this.UpPressed && !(this.LeftPressed || this.RightPressed)) {
                this.moveForwards();
            }
            if (this.DownPressed && !(this.LeftPressed || this.RightPressed)) {
                this.moveBackwards();
            }
        }

//        if (this.UpPressed && !(this.LeftPressed || this.RightPressed)) {
//            this.moveForwards();
//        }
//        if (this.DownPressed && !(this.LeftPressed || this.RightPressed)) {
//            this.moveBackwards();
//        }
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

        if(this.ShootPressed && TRE.tick % 20 == 0){
            Bullet b = new Bullet(x,y,vx,vy,angle, TRE.bulletImage,GameID.Bullet, this.handler);
            this.ammo.add(b);
            //this.handler.addGameObject(b);
        }
        this.ammo.forEach(bullet -> bullet.update());
 //       for(int i = 0 ; i < this.ammo.size(); i++){
 //           this.ammo.get(i).update();
 //           }
        //if some ammo object intersects another object then remove that ammo from the list
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    @Override
    void setR(){
        R = 2;
    }

    private void moveBackwards() {
//        vx = (int) Math.round(R*Math.cos(Math.toRadians(angle)));
//        vy = (int) Math.round(R*Math.sin(Math.toRadians(angle)));
//        x -= vx;
//        y -= vy;
//        checkBorder();
//        this.hitBox.setLocation(x,y);
        setR();
        moveBackward();
    }

    void moveForwards() {
        //vx = (int) Math.round(R*Math.cos(Math.toRadians(angle)));
        //vy = (int) Math.round(R*Math.sin(Math.toRadians(angle)));
        //x += vx;
        //y += vy;
        //checkBorder();
        //this.hitBox.setLocation(x,y);
        setR();
        moveForward();
    }
    //void checkBorder was here

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        //added 46:00
        g2d.setColor(Color.BLUE);
        //Handling maps vid
        //if (b!= null) b.drawImage(g);
        this.ammo.forEach(bullet -> bullet.drawImage(g));
        //use ammo.remove() to remove the bullet on screen

        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight());
    }



}
