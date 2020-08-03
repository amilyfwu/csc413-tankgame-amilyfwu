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
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world; //the black screen
    private BufferedImage bg;
    private Color myColor;
    //private Tank t1;
    //private Tank t2; //added
    private Launcher lf;
    static long tick = 0; //this was private long static previously
    public static BufferedImage bulletImage;
    private ArrayList<Floor> floors;
    private Handler handler;

    public TRE(Launcher lf){
        this.lf = lf;
        this.myColor = new Color(80,81,130);
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

               //when live reaches 0 then game is over and restarts
               if(this.handler.getLive1() == 0 || this.handler.getLive2() == 0){
                   this.lf.setFrame("end");
                   return;
               }
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                //System.out.println(t1);
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                 //this is is what is causing the game to end
//                if(this.tick > 2000){
//                    this.lf.setFrame("end");
//                    return;
//                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    private void setBGImg(BufferedImage img){
        this.bg = img;
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.handler.resetObjects();
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
        BufferedImage breakableWall2 = null;
        BufferedImage unBreakableWall = null;
        BufferedImage powerUpSpd = null;
        BufferedImage powerUpHp = null;
        BufferedImage powerUp2xDmg = null;
        BufferedImage floorTile1 = null;
        BufferedImage floorTile2 = null;
        BufferedImage floorTile3 = null;
        BufferedImage floorTile4 = null;
        BufferedImage floorTile5 = null;

        floors = new ArrayList<>();
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
            breakableWall2 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile4pt2.png")));
            unBreakableWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile14.png")));
            powerUpSpd = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile6.png")));
            powerUpHp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile5.png")));
            powerUp2xDmg = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile7.png")));
            floorTile1 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile15.png")));
            floorTile2 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile10.png")));
            floorTile3 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile1.png")));
            floorTile4 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile12.png")));
            floorTile5 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tile11.png")));

            //setBGImg(floorTile1);

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
                        case "2": //breakable wall
                            //this.walls.add(br);
                            this.handler.addGameObject(new Breakable(curCol*32, curRow*32, breakableWall,GameID.Wall, this.handler, breakableWall2));
                            this.floors.add(new Floor(curCol*32, curRow*32,floorTile5));
                            break;
                        case "3": //unbreakable wall
                            //this.walls.add(unBr);
                            this.handler.addGameObject(new Unbreakable(curCol*32, curRow*32, unBreakableWall,GameID.Wall, this.handler));
                            break;
                        case "4": //Speed powerup
                            this.handler.addGameObject(new PowerUpSpd(curCol*32, curRow*32, powerUpSpd, GameID.PowerUp,this.handler));
                            this.floors.add(new Floor(curCol*32, curRow*32,floorTile2));
                            break;
                        case "5": //HP powerup
                            this.handler.addGameObject(new PowerUpHp(curCol*32, curRow*32, powerUpHp, GameID.PowerUp,this.handler));
                            this.floors.add(new Floor(curCol*32, curRow*32,floorTile3));
                            break;
                        case "6": //2x dmg powerup
                            this.handler.addGameObject(new PowerUp2xDmg(curCol*32, curRow*32, powerUp2xDmg, GameID.PowerUp,this.handler));
                            this.floors.add(new Floor(curCol*32, curRow*32,floorTile4));
                            break;
                        case "9": //outer wall
                            this.floors.add(new Floor(curCol*32, curRow*32,floorTile1));
                            break;
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
        Tank t2 = new Tank(1800, 1800, 0, 0, 180, t2img, GameID.Tank2, this.handler); //should be a different image
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
        if (x1 >= GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2) { //apparently it was screen width/2
            x1 = GameConstants.WORLD_WIDTH - GameConstants.GAME_SCREEN_WIDTH/2;
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
        if (y1 >= GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT) { //screen height
            y1 = GameConstants.WORLD_HEIGHT - GameConstants.GAME_SCREEN_HEIGHT;
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
        buffer.setColor(myColor);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        //buffer.drawImage(bg,0,0,null);
        //this.walls.forEach(wall -> wall.drawImage(buffer));
        //this.t1.drawImage(buffer);
        //this.t2.drawImage(buffer);
        this.floors.forEach(floor -> floor.drawImage(buffer)); //draw floor
        this.handler.drawImage(buffer); // draw all gameObjects
        BufferedImage leftHalf = world.getSubimage(checkBorderScreenX(this.handler.getTank1X() - GameConstants.GAME_SCREEN_WIDTH/4),checkBorderScreenY(this.handler.getTank1Y() - GameConstants.GAME_SCREEN_HEIGHT/2),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(checkBorderScreenX(this.handler.getTank2X() - GameConstants.GAME_SCREEN_WIDTH/4),checkBorderScreenY(this.handler.getTank2Y()-GameConstants.GAME_SCREEN_HEIGHT/2),GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage miniMap = world.getSubimage(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2 + 2,0,null);

        //hp bar
        g.setColor(Color.GRAY);
        g.fillRect(103, 680, 200,20);
        g.setColor(Color.GREEN);
        g.fillRect(103,680,this.handler.getHp1() * 2,20);
        g.setColor(Color.ORANGE);
        g.drawRect(103, 680, 200,20);

        g.setColor(Color.WHITE);
        g.drawString("Live Count: " + this.handler.getLive1(),103, 720);

        g.setColor(Color.GRAY);
        g.fillRect(715, 680, 200,20);
        g.setColor(Color.GREEN);
        g.fillRect(715,680,this.handler.getHp2() * 2,20);
        g.setColor(Color.ORANGE);
        g.drawRect(715, 680, 200,20);

        g.setColor(Color.WHITE);
        g.drawString("Live Count: " + this.handler.getLive2(),715, 720);

        g2.scale(.10,.10);
        g2.drawImage(miniMap,4080,5230,null);

    }

}
