package tankrotationexample.game;



import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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


    Tank(int x, int y, int vx, int vy, float angle, BufferedImage img) {
        super(x,y,vx,vy,angle,img);
        //this.x = x;
        //this.y = y;
        //this.vx = vx;
        //this.vy = vy;
        //this.img = img;
        //this.angle = angle;
        //this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.ammo = new ArrayList<>();

    }

    void setX(int x){ this.x = x; }

    void setY(int y) { this. y = y;}

    void setVx(int vx){
        this.vx = vx;
    }
    void setVy(int vy){
        this.vy = vy;
    }
    int getX(){
        return x;
    }

    int getY(){
        return y;
    }

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

    void update() {
        if(this.collide){
            if(this.UpPressed){
                this.moveBackwards();
                this.x-=2;
                this.y-=2;
            }
            if (this.DownPressed){
                this.moveForwards();
                this.x+=2;
                this.y+=2;
            }
            this.collide = false;
        }else {
            if (this.UpPressed) {
                this.moveForwards();
            }
            if (this.DownPressed) {
                this.moveBackwards();
            }
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed && TRE.tick % 20 == 0){
            Bullet b = new Bullet(x,y,vx,vy,angle, TRE.bulletImage);
            this.ammo.add(b);
        }
        this.ammo.forEach(bullet -> bullet.update());
 //       for(int i = 0 ; i < this.ammo.size(); i++){
 //           this.ammo.get(i).update();
 //           }
    }

    void setCollision(boolean collide){
        this.collide = collide;
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
    void drawImage(Graphics g) {
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
        //g2d.rotate(Math.toRadians(angle), bounds.x + bounds.width/2, bounds.y + bounds.height/2);
        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight());
    }



}
