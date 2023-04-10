package SnakeGame;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.random.*;

public class GamePanel extends JPanel implements ActionListener {
    /*
    Toolkit tk=Toolkit.getDefaultToolkit(); //Initializing the Toolkit class.
    Dimension screenSize = tk.getScreenSize(); //Get the Screen resolution of our device.

     */

     static final int SCREEN_WIDTH=700;
    //1537 ;
     static final int SCREEN_HEIGHT=700;
    // 864;

    static final int UNIT_SIZE=20;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=90;
    static final int x[]=new int[GAME_UNITS];
    static final int y[] =new int[GAME_UNITS];

    int bodyParts=4;
    int apple;
    int appleX;
    int appleY;

    char direction='R';
    boolean running= false;
    boolean gameOver= false;
    Timer timer;
    Random random;




    GamePanel(){
        random= new Random();
        this.setPreferredSize(new DimensionUIResource(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);

        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        startGame();
    }
    public void startGame(){
        move();
        newApple();
        running = true;
        timer= new Timer(DELAY,this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);


    }
    public void newApple(){
        appleX= random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE ;
        appleY= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE ;


    }
    public void draw(Graphics g){
        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);


                }


            }
            g.setColor(Color.red);
            g.setFont(new Font("MV BOLI",Font.BOLD,20));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("SCORE : "+apple,(SCREEN_WIDTH- metrics.stringWidth("SCORE: "+ apple))/2,g.getFont().getSize());

        /*
        for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);*/

        }

        else {
            gameOver(g);
        }
    }
    public void move(){
        for (int i = bodyParts; i>0;i--){
            x[i]= x[i-1];
            y[i]= y[i-1];
        }
        switch (direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;

        }

    }
    public void checkApple(){
        if ((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            apple++;

            newApple();
        }

    }


    public void checkCollisions(){
        //check head collides with body
        for (int i= bodyParts;i>0;i--){
            if ((x[0]==x[i]) && (y[0]==y[i])){
                running =false;
            }
        }
        //check border touch
        if (x[0]<0){
            running=false;
        }
        if (x[0]>SCREEN_WIDTH){
            running=false;
        }
        if (y[0]<0){
            running=false;
        }
        if (y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if (!running){
            timer.stop();
        }


    }
    public void restartGame() {
        // Reset game variables
        bodyParts = 4;
        apple = 0;
        direction = 'R';
        running = false;
        gameOver = false;
        timer.stop();

        // Clear snake position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        // Start new game
        startGame();
    }

    public void gameOver(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont(new Font(" Arial",Font.BOLD,20));
        FontMetrics metrics1= getFontMetrics(g.getFont());
        g.drawString("SCORE : "+apple,(SCREEN_WIDTH- metrics1.stringWidth("SCORE: "+ apple))/2,g.getFont().getSize());

        //gameOver
        g.setColor(Color.red);
        g.setFont(new Font("MV BOLI",Font.BOLD,50));

        g.drawString("GAME OVER",200,300);

        g.setColor(Color.white);
        g.setFont(new Font("MV BOL",Font.PLAIN,20));

        g.drawString("Press Space to Restart",250,450);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkCollisions();
            checkApple();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_SPACE:restartGame();

                case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                    if (direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                    if (direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP, KeyEvent.VK_W:
                    if (direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                    if (direction!='U'){
                        direction='D';
                    }
                    break;

            }
            System.out.println("move");

        }
    }
}
