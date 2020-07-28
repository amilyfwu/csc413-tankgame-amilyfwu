package tankrotationexample.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Handler {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int x1,y1,x2,y2, hp1,hp2,live1,live2;

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void update(){
//        for(int i = 0; i<gameObjects.size();i++){
//            GameObject temp = gameObjects.get(i);
//            temp.update(); //updates all the gameObjects
//
//            if(temp.getId() == GameID.Tank1){
//                x1 = temp.getX();
//                y1 = temp.getY();
//            }
//            if(temp.getId() == GameID.Tank2){
//                x2 = temp.getX();
//                y2 = temp.getY();
//            }
//
//        }

        //this.gameObjects.forEach(gameObject -> gameObject.update());
        try {
            this.gameObjects.forEach(gameObject -> {
                GameObject temp = gameObject;
                temp.update();  //updates all the gameObjects

                if (temp.getId() == GameID.Tank1) { //this is for the paintComponent moving tank screens
                    x1 = temp.getX();
                    y1 = temp.getY();
                    hp1 = ((Tank)temp).getHp();
                }
                if (temp.getId() == GameID.Tank2) {
                    x2 = temp.getX();
                    y2 = temp.getY();
                    hp2 = ((Tank)temp).getHp();

                }
            });
        }catch (ConcurrentModificationException ex){}
    }

    public void drawImage(Graphics g){
//        for (int i = 0; i<gameObjects.size();i++){
//            GameObject temp = gameObjects.get(i);
//            temp.drawImage(g);
//        }
        try {
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

    public void resetTanks(){
        for (int i = 0; i<gameObjects.size();i++){
            GameObject temp = gameObjects.get(i);
            if(temp.getId() == GameID.Tank1){
               temp.setX(200);
               temp.setY(200);

            }
            if(temp.getId() == GameID.Tank2){
                temp.setX(600);
                temp.setY(600);
            }
        }
    }

    public void collision(GameObject objectToCheck){
        this.gameObjects.forEach(gameObject -> {
            if(gameObject.getId() == GameID.Wall){
                if(objectToCheck.getHitBox().intersects(gameObject.getHitBox())){

                }
            }
        });
    }

}
