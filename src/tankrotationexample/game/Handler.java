package tankrotationexample.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Handler {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int x1,y1,x2,y2, hp1,hp2,live1,live2;

    public ArrayList<GameObject> getGameObjects() { //used in the tank class
        return gameObjects;
    }

    public void update(){
        //this.gameObjects.forEach(gameObject -> gameObject.update());
        this.gameObjects.forEach(gameObject -> {
            gameObject.update();  //updates all the gameObjects
            switch (gameObject.getId()){ //this is for the TRE paintComponent
                case Tank1:
                    x1 = gameObject.getX();
                    y1 = gameObject.getY();
                    hp1 = ((Tank)gameObject).getHp();
                    live1 = ((Tank)gameObject).getLives();
                    break;
                case Tank2:
                    x2 = gameObject.getX();
                    y2 = gameObject.getY();
                    hp2 = ((Tank)gameObject).getHp();
                    live2 = ((Tank)gameObject).getLives();
                    break;
            }
        });
    }

    public void drawImage(Graphics g){
        try{
            this.gameObjects.forEach(gameObject -> gameObject.drawImage(g));
        }catch (ConcurrentModificationException ex){}
    }

    public void addGameObject(GameObject temp){
        gameObjects.add(temp);
    }

    public void removeGameObject(GameObject temp){
        gameObjects.remove(temp);
    }

    public int getTank1X(){ //this is for the moving screen
        return x1;
    }
    public int getTank1Y(){
        return y1;
    }
    public int getTank2X(){
        return x2;
    }
    public int getTank2Y(){
        return y2;
    }
    public int getHp1() {
        return hp1;
    }
    public int getHp2() {
        return hp2;
    }
    public int getLive1() {
        return live1;
    }
    public int getLive2() {
        return live2;
    }

    public void resetObjects(){
        gameObjects.forEach(gameObject -> {
            switch (gameObject.getId()){
                case Wall:
                case PowerUp:
                    ((Stationary)gameObject).setState(2);
                    break;
                case Tank1:
                    ((Tank)gameObject).setHp(100);
                    ((Tank)gameObject).setLives(3);
                    ((Tank)gameObject).changeR(2);
                    ((Tank)gameObject).setTempAttackPts(10);
                    ((Tank)gameObject).setVx(0);
                    ((Tank)gameObject).setVy(0);
                    ((Tank)gameObject).setAngle(0);
                    gameObject.setX(200);
                    gameObject.setY(200);
                    break;
                case Tank2:
                    ((Tank)gameObject).setHp(100);
                    ((Tank)gameObject).setLives(3);
                    ((Tank)gameObject).changeR(2);
                    ((Tank)gameObject).setTempAttackPts(10);
                    ((Tank)gameObject).setVx(0);
                    ((Tank)gameObject).setVy(0);
                    ((Tank)gameObject).setAngle(180);
                    gameObject.setX(1800);
                    gameObject.setY(1800);
                    break;
            }
        });
    }
}
