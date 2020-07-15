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

    public static final int WORLD_WIDTH = 2000;
    public static final int WORLD_HEIGHT = 2000;
    private BufferedImage world; //the black screen
    private Tank t1;
    private Tank t2; //added
    private Launcher lf;
    static long tick = 0; //this was private long static previously
    public static BufferedImage bulletImage;
    ArrayList<Wall> walls;


    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update(); // update tank
                this.repaint();   // redraw game
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
        this.t1.setX(200);
        this.t1.setY(200);
        this.t2.setX(600);
        this.t2.setY(600);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() { //have to change the width and height later to world width and height
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                                       GameConstants.GAME_SCREEN_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage breakableWall = null;
        BufferedImage unBreakableWall = null;
        walls = new ArrayList<>();
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
                            Breakable br = new Breakable(curCol*32, curRow*32, breakableWall);
                            this.walls.add(br);
                            break;
                        case "3":
                        case "9":
                            Unbreakable unBr = new Unbreakable(curCol*32, curRow*32, unBreakableWall);
                            this.walls.add(unBr);
                            break;

                    }
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(200, 200, 0, 0, 0, t1img);
        t2 = new Tank(600, 600, 0, 0, 180, t2img); //should be a different image
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
        //make a t2 - from starter code demo video 37:21-ish
    }

    public long getTick(){
        return tick;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2); //added
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
        this.walls.forEach(wall -> wall.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        //world.getSubimage();
        g2.drawImage(world,0,0,null);
    }

}
