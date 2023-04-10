package SnakeGame;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
       this.add(new GamePanel());
       this.setTitle("Snake Game");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //this.setResizable(false);
       this.pack();
       this.setLocationRelativeTo(null);







        this.setVisible(true);

    }
}
