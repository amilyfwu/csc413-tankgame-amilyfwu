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

    private final float ROTATIONSPEED = 3.0f;
    private ArrayList<Bullet> ammo;

    private Rectangle hitboxH; //horizontal
    private Rectangle hitboxV; //vertical

    //Tank stats
    private int hp = 100;
    private int lives = 3; //live 3 times
    private int tempR = 2; //Temporary speed and a way to Change the speed
    private int tempAttackPts = 10; //Temporary atk pts to pass into bullet class

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean collide;

    Tank(int x, int y, int vx, int vy, float angle, BufferedImage img,GameID id, Handler handler) {
        super(x,y,vx,vy,angle,img,id,handler);
        this.ammo = new ArrayList<>();
        hitboxH = new Rectangle(getX()+getVx(),getY(),this.img.getWidth()+ getVx()/2, this.img.getHeight());
        hitboxV = new Rectangle(getX(),getY() + getVy(),this.img.getWidth(), this.img.getHeight()+getVy()/2);
    }

    void setCollision(boolean collide){
        this.collide = collide;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getTempAttackPts() {
        return tempAttackPts;
    }

    public void setTempAttackPts(int tempAttackPts) {
        this.tempAttackPts = tempAttackPts;
    }

    void setR(){
        R = tempR;
    }

    public void changeR(int changeR){ //modifying the speed for powerup
        tempR = changeR;
    }

    public Rectangle getBoundH(){ //two rectangle going slightly ahead of the tank rectangle to check for collision
        hitboxH.setBounds(getX() + getVx(),getY(),this.img.getWidth() + getVx()/2, this.img.getHeight());
        return hitboxH;
    }
    public Rectangle getBoundV(){
        hitboxV.setBounds(getX(),getY() + getVy(),this.img.getWidth(), this.img.getHeight()+getVy()/2);
        return hitboxV;
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

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        setR();
        moveBackward();
    }

    void moveForwards() {
        setR();
        moveForward();
    }

    private void doCollision(){
        if(this.UpPressed){
            for(int i = 0; i<=5; i++){
                this.moveBackwards();
            }
        }
        if (this.DownPressed){
            for(int i = 0; i<=5; i++){
                this.moveForwards();
            }
        }
        this.collide = false;
    }

    private void doCollision2(){
        this.handler.getGameObjects().forEach(gameObject -> {
            GameID gameIDTemp = gameObject.getId();
            //tank colliding will walls
            if (gameIDTemp == GameID.Wall && gameObject instanceof Wall) {
                checkWallCollision(gameObject);
            }
            //tank colliding with tank
            else if ((gameIDTemp == GameID.Tank1 && this.getId() != GameID.Tank1) || (gameIDTemp == GameID.Tank2 && this.getId() != GameID.Tank2)) {
                checkTankCollision(gameObject);
            }
            //tank colliding with PowerUps
            else if(gameIDTemp == GameID.PowerUp && ((Stationary) gameObject).getState() == 2){
                checkPowerUpCollision(gameObject);
            }

            //bullets colliding with everything
            //exclude the tank that is shooting the bullet
            if (gameIDTemp != this.getId()) {
                try {
                    //checking bullet Collision
                    this.ammo.forEach(bullet -> {
                        //bullet hitting gameObjects and removing that bullet
                        checkBulletCollision(gameObject,bullet,gameIDTemp);
                    });
                } catch (ConcurrentModificationException ex) {}
            }
        });
    }

    private void checkBulletCollision(GameObject gameObject, Bullet bullet,GameID gameIDTemp) {
        if (bullet.getHitBox().intersects(gameObject.getHitBox())) {
            if((gameIDTemp == GameID.Wall && gameObject instanceof Breakable && ((Breakable) gameObject).getState() == 1) || gameIDTemp == GameID.PowerUp){
                //bullets ignore breakable walls that were already broken, power ups, if bullet reaches the outer walls
            }
            else{
                this.ammo.remove(bullet);
            }
            if (gameIDTemp == GameID.Tank1 || gameIDTemp == GameID.Tank2) { //if the bullet hit the tank reduce the hp or lives
                //health bar
                ((Tank)gameObject).setHp(((Tank)gameObject).getHp() - bullet.getAttackPts());
                //lives left check
                //if hp is less than or equal to zero
                // the live count reduces by one and the hp is reset to 100 again
                if(((Tank)gameObject).getHp() <= 0){
                    ((Tank)gameObject).setLives(((Tank)gameObject).getLives() - 1);
                    ((Tank)gameObject).setHp(100);
                }

            }
            else if (gameIDTemp == GameID.Wall && gameObject instanceof Breakable) { //if bullet hits a breakable wall, breakable wall breaks
                ((Breakable) gameObject).setState(1);
                //if getState is more than zero reduce the state by 1;
                //if getState is zero dont do anything
            }
        }
        if(bullet.isBorderBullet()){ //if the bullet is on the outer walls
            this.ammo.remove(bullet);
        }
    }

    private void checkPowerUpCollision(GameObject gameObject) {
        if(this.getHitBox().intersects(gameObject.getHitBox())){
            //powerup : hp, speed, and 2x damage
            if(gameObject instanceof PowerUpHp){
                if(getHp() < 100){
                    setHp(getHp() + 10);
                }
                ((PowerUpHp) gameObject).setState(1);
            }
            else if (gameObject instanceof PowerUpSpd){
                tempR++;
                changeR(tempR);
                ((PowerUpSpd) gameObject).setState(1);
                //System.out.println("im speed " + tempR);
            }
            else if (gameObject instanceof PowerUp2xDmg){
                setTempAttackPts(getTempAttackPts()*2);
                ((PowerUp2xDmg) gameObject).setState(1);
            }
        }
    }

    private void checkTankCollision(GameObject gameObject) {
        if (this.getHitBox().intersects(gameObject.getHitBox()) || gameObject.getHitBox().intersects(this.getHitBox())) {
            setCollision(true);
        }
    }

    private void checkWallCollision(GameObject gameObject) {
        if (this.getBoundH().intersects(gameObject.getHitBox()) && (((Wall) gameObject).getState() == 2)) {
            //setCollision(true);
            if(getVx() > 0){ //right
                setVx(0);
                setX((int) gameObject.getHitBox().getX() - 55);
            }else if(getVx() < 0){ //left
                setVx(0);
                setX((int) gameObject.getHitBox().getMaxX() + 10);
            }
        }
        if(this.getBoundV().intersects(gameObject.getHitBox()) && (((Wall) gameObject).getState() == 2)){ //getBounds apparently not needed?
            if(getVy() > 0){ //down
                setVy(0);
                setY((int) gameObject.getHitBox().getY() - 55);
            }else if(getVy() < 0){ //up
                setVy(0);
                setY((int) gameObject.getHitBox().getMaxY() + 10);
            }
        }
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

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

        if(this.ShootPressed && TRE.tick % 20 == 0){
            Bullet b = new Bullet(x,y,vx,vy,angle, TRE.bulletImage,GameID.Bullet, this.handler);
            b.setAttackPts(getTempAttackPts()); //modify the attack points
            this.ammo.add(b);
        }
        this.ammo.forEach(bullet -> bullet.update());
 //       for(int i = 0 ; i < this.ammo.size(); i++){
 //           this.ammo.get(i).update();
 //           }
        //if some ammo object intersects another object then remove that ammo from the list
    }

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
        this.ammo.forEach(bullet -> bullet.drawImage(g));
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight());
    }



}
