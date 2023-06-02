package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 16;

    //PLAYERS
    int paddleWidth = 16;
    int paddleHeight= 64;

    int playerOnePaddleX = 16;
    int playerOnePaddleY = 20;

    int playerTwoPaddleX = screenWidth - 32;
    int playerTwoPaddleY = 40;

    int paddleSpeed = 5;

    // SYSTEM
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
            // Pause the game loop for 16 milliseconds to regulate the frame rate
            try {
                Thread.sleep(FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        paddleControl();
        paddleCollision();
    }


    private void paddleCollision() {

        //PLAYER ONE
        if (playerOnePaddleY <= 0) {
            playerOnePaddleY = 0;
        }
        if (playerOnePaddleY >= screenHeight - paddleHeight) {
            playerOnePaddleY = screenHeight - paddleHeight;
        }

        //PLAYER TWO
        if (playerTwoPaddleY <= 0) {
            playerTwoPaddleY = 0;
        }
        if (playerTwoPaddleY >= screenHeight - paddleHeight) {
            playerTwoPaddleY = screenHeight - paddleHeight;
        }
    }


    private void paddleControl() {

        //PLAYER ONE
        if (keyH.upPressedOne) {
            playerOnePaddleY -= paddleSpeed;
        }
        if (keyH.downPressedOne) {
            playerOnePaddleY += paddleSpeed;
        }

        //PLAYER TWO
        if (keyH.upPressedTwo) {
            playerTwoPaddleY -= paddleSpeed;
        }
        if (keyH.downPressedTwo) {
            playerTwoPaddleY += paddleSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.WHITE);
        paddleDrawing(g2D);
        boardDrawing(g2D);
        g2D.dispose();
    }

    private void boardDrawing(Graphics2D g2D){

    }

    private void paddleDrawing(Graphics2D g2D) {

        //PLAYER ONE
        g2D.fillRect(playerOnePaddleX, playerOnePaddleY, paddleWidth, paddleHeight);

        //PLAYER TWO
        g2D.fillRect(playerTwoPaddleX, playerTwoPaddleY, paddleWidth, paddleHeight);
    }
}