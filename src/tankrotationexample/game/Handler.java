package tankrotationexample.game;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    ArrayList<GameObject> gameObjects = new ArrayList<>();
    int x1,y1,x2,y2;


    public void update(){
        for(int i = 0; i<gameObjects.size();i++){
            GameObject temp = gameObjects.get(i);
            temp.update(); //updates all the gameObjects

            if(temp.getId() == GameID.Tank1){
                x1 = temp.getX();
                y1 = temp.getY();
            }
            if(temp.getId() == GameID.Tank2){
                x2 = temp.getX();
                y2 = temp.getY();
            }

        }
        //this.gameObjects.forEach(gameObject -> gameObject.update());
    }

    public void drawImage(Graphics g){
//        for (int i = 0; i<gameObjects.size();i++){
//            GameObject temp = gameObjects.get(i);
//            temp.drawImage(g);
//        }
        this.gameObjects.forEach(gameObject -> gameObject.drawImage(g));
    }

    public void addGameObject(GameObject temp){
        gameObjects.add(temp);
    }

    public void removeGameObject(GameObject temp){
        gameObjects.remove(temp);
    }

    public int getTank1X(){
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

}
