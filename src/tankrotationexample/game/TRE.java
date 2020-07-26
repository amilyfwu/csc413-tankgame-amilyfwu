/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;


import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world; //the black screen
    //private Tank t1;
    //private Tank t2; //added
    private Launcher lf;
    static long tick = 0; //this was private long static previously
    public static BufferedImage bulletImage;
    //ArrayList<Wall> walls;

    private Handler handler;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
               this.tick++;
               handler.update(); //update all gameObjects
               //this.t1.update(); // update tank
               //this.t2.update(); // update tank
               this.repaint();   // redraw game

//               if(this.t1.getHitBox().intersects(this.t2.getHitBox())){ //check for tank collisions
//                   System.out.println("tanks are colliding");
//                   this.t1.setCollision(true);
//                   this.t2.setCollision(true);
//               }
//               for(int i = 0; i<this.walls.size() ;i++){
//                   Rectangle wallHitBox = this.walls.get(i).getHitBox();
//
//                   //check bullet collision for wall or tank
//
//                   //check for wall and tank collision
//                   if(wallHitBox.intersects(this.t1.getHitBox())){
//                       this.t1.setCollision(true);
//                       break;
//                   }
//                   if(wallHitBox.intersects(this.t2.getHitBox())){
//                       this.t2.setCollision(true);
//                       break;
//                   }
//               }

                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                //System.out.println(t1);
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                 //this is is what is causing the game to end
 //               if(this.tick > 2000){
 //                   this.lf.setFrame("end");
 //                   return;
 //               }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.handler.resetTanks();
        //this.t1.setX(200);
        //this.t1.setY(200);
        //this.t2.setX(600);
        //this.t2.setY(600);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() { //have to change the width and height later to world width and height
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage breakableWall = null;
        BufferedImage unBreakableWall = null;
        BufferedImage powerUpSpd = null;
        BufferedImage powerUpHp = null;
        BufferedImage powerUp2xDmg = null;
       // walls = new ArrayList<>();
        handler = new Handler();

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png")));
            t2img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank2.png")));
            TRE.bulletImage = read(TRE.class.getClassLoader().getResource("bullet1.png"));
            breakableWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile4.png")));
            unBreakableWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile2.png")));
            powerUpSpd = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile6.png")));
            powerUpHp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile5.png")));


            InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map2.txt"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if(row == null){
                throw new IOException("no data in file");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);
           //System.out.println(numCols + " " + numRows);
            for(int curRow = 0; curRow < numRows; curRow++ ){
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol = 0; curCol < numCols; curCol++ ){
                    switch (mapInfo[curCol]){
                        case "2":
                            Breakable br = new Breakable(curCol*32, curRow*32, breakableWall,GameID.Wall, this.handler);
                            //this.walls.add(br);
                            this.handler.addGameObject(br);
                            break;
                        case "3":
                        case "9":
                            Unbreakable unBr = new Unbreakable(curCol*32, curRow*32, unBreakableWall,GameID.Wall,this.handler);
                            //this.walls.add(unBr);
                            this.handler.addGameObject(unBr);
                            break;
                        case "4":
                            PowerUpSpd spd = new PowerUpSpd(curCol*32, curRow*32, powerUpSpd, GameID.PowerUp, this.handler);
                            this.handler.addGameObject(spd);
                            break;
                        case "5":
                            PowerUpHp hp = new PowerUpHp(curCol*32, curRow*32, powerUpHp, GameID.PowerUp, this.handler);
                            this.handler.addGameObject(hp);

                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        //tanks were created here
        ////moved tanks here
        Tank t1 = new Tank(200, 200, 0, 0, 0, t1img, GameID.Tank1, this.handler);
        Tank t2 = new Tank(600, 600, 0, 0, 180, t2img, GameID.Tank2, this.handler); //should be a different image
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.handler.addGameObject(t1);
        this.handler.addGameObject(t2);

        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }

    public long getTick(){
        return tick;
    }

    private int checkBorderScreenX(int x1){
        //System.out.println("x: " + x1);
        if (x1 < 0) {
            x1 = 0;
            return x1;
        }
        if (x1 >= GameConstants.WORLD_WIDTH - 512) { //apparently it was screen width/2
            x1 = GameConstants.WORLD_WIDTH - 512;
            return x1;
        }
        return x1;
    }
    private int checkBorderScreenY(int y1){
        //System.out.println("y: " + y1);
        if (y1 < 0) {
            y1 = 0;
            return y1;
        }
        if (y1 >= GameConstants.WORLD_HEIGHT - 768) { //screen height
            y1 = GameConstants.WORLD_HEIGHT - 768;
            //System.out.println("yfsdf :" + y1);
            return y1;
        }
        return y1;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2); //added
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        //this.walls.forEach(wall -> wall.drawImage(buffer));
        //this.t1.drawImage(buffer);
        //this.t2.drawImage(buffer);
        this.handler.drawImage(buffer); // draw all gameObjects
        BufferedImage leftHalf = world.getSubimage(checkBorderScreenX(this.handler.getTank1X() - GameConstants.GAME_SCREEN_WIDTH/4),checkBorderScreenY(this.handler.getTank1Y() - GameConstants.GAME_SCREEN_HEIGHT/2),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(checkBorderScreenX(this.handler.getTank2X() - GameConstants.GAME_SCREEN_WIDTH/4),checkBorderScreenY(this.handler.getTank2Y()-GameConstants.GAME_SCREEN_HEIGHT/2),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage miniMap = world.getSubimage(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 6,0,null);
        g2.scale(.10,.10);
        g2.drawImage(miniMap,3000,3000,null);

    }

}
