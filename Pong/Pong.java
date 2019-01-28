package Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;


public class Pong extends Game implements ActionListener, KeyListener{

    public static Pong pong;    //constructor of pong
    protected int width = 700, height = 700;    //size of window
    public Renderer renderer; //create the thing that will make a window popup where game will be played
    public Paddle player1;  //create paddle for the user
    public Paddle player2;  //create paddle for the computer
    public Ball ball;   //instance of ball object
    public boolean AI = false, difficultyType;  //for AI player and boolean for turning on and off whenever the user wants to play against the AI
    public boolean w, s, up, down; //keys for movement
    public int play = 0, gameOver = 5, playerWon; //0 = stop, 1 = pause, 2 = play, 3 = game over
    public int AIdifficulty, AImoves, AIslow;   //these mark the level of difficulty the AI can have ***please use the HARD mode*****
    public Random random;
    public JFrame jFrame;   //instance of the JFrame to make a window pop up

    public Pong() {
        //timer to start task on delay
        Timer timer = new Timer(20, this);  //it will first start the followng before starting a game.
        random = new Random(); //will be used for the posiotion of the ball after its been hit.
        //this initializes the jFrame
        jFrame = new JFrame("Pong"); //gives the window that will pop up the title of pong.

        renderer = new Renderer(); //instance of the render that will make things pop up on the screen

        jFrame.setSize(width, height); //dimensions of the window
        jFrame.setVisible(true);    //displays the window, if false it wouldnt show anything.
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //program will run until you close the window
        jFrame.add(renderer);
        jFrame.addKeyListener(this);//in order to recognize hitting the key
        //str=art the timer
        timer.start(); //now things will start as there needed to be some things set up.

    }

    public void start(){
        play = 2;   //game is paused nothing is happening.
        player1 = new Paddle(this, 1); //creates the left paddle
        player2 = new Paddle(this, 2); //creates the right paddle
        ball = new Ball(this);  //makes the ball object.
    }

    private void update() {

        if (player1.score >= gameOver) {   //when player1's score has either equaled the max pointage
            playerWon = 1;      //indicates that player one has won
            play = 3;       //game is now over
        } else if (player2.score >= gameOver) { //same logic as above only for player2
            play = 3;
            playerWon = 2;
        }
        if(w){
            player1.move(true); //since move is true it will move paddle up
        }
        if(s){
            player1.move(false); //since move is false it will move paddle up
        }
        if (!AI){
            if(up){
                player2.move(true); //since move is true it will move paddle up
            }
            if(down){
                player2.move(false); //since move is false it will move paddle up
            }
        } else{     //how the AI moves in the game * this only fully works for when playing AI inthe hard setting.
            if(AIslow > 0){
                AIslow--;
                if(AIslow == 0){
                    AImoves = 0;
                }
            }
            if(AImoves < 10){
                if (player2.y + (player2.height / 2) < ball.y)
                {
                    player2.move(false);
                    AImoves++;
                }
                if (AIdifficulty == 0)
                {
                    AIslow= 20;
                }
                if (AIdifficulty == 1)
                {
                    AIslow = 15;
                }
                if (AIdifficulty == 2)
                {
                    AIslow = 10;
                }
            }
        }
        ball.update(player1, player2);
    }

    public void render(Graphics2D g) {
        g.setColor(Color.GREEN);    //sets color of the screen to green
        g.fillRect(0,0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (play == 0){   //MENU        //menu screen
            g.setColor(Color.BLACK);    //sets the color that will be used for te lettering
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("WELCOME TO PONG", width /2 - 225, 250);

            if(!difficultyType){   //difficulty is  not true so it will display this.
                g.setFont(new Font("Arial", 1, 20));
                g.drawString("HIT SPACE TO START", width /2 - 100, 350); // plays with another user
                g.drawString("HIT A TO PLAY AGAINST AI", width /2 - 120, 385); //plays against AI
                g.drawString("ScoreLimit: " + gameOver + " ", width /2 - 50, 420);
            }

        }
        if (difficultyType)     //here difficulty has been turned on, so this means that there is an AI player
        {
            String string = AIdifficulty == 0 ? "Easy" : (AIdifficulty == 1 ? "Medium" : "Hard");

            g.setFont(new Font("Arial", 1, 30));

            g.drawString("<< AI Difficulty: " + string + " >>", width / 2 - 180, height / 2 - 25);
            g.drawString("Press Space to Play", width / 2 - 150, height / 2 + 25);
        }
        if (play == 1 || play == 2){  //things displayed on the playing screen
            g.setColor(Color.WHITE);    //color of the paddle
            g.setStroke(new BasicStroke(3));    //middle line thickens up a bit
            g.drawLine((width / 2), 0, (width / 2), height);    //where the line in the middle will go

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString(String.valueOf(player1.score), width /2 - 225, 75);
            g.drawString(String.valueOf(player2.score), width /2 + 225, 75);

            player1.render(g);      //displays the paddle of user 1 on the left side of the screen
            player2.render(g);      //displays the paddle of user 2 on the right side of the screen
            ball.render(g);

        }
        if (play == 3){ //what happens when the match of pong is over.
            g.setColor(Color.WHITE); //sets up new font color
            g.setFont(new Font("Arial", 1, 50));    //font style and stuff

            g.drawString("PONG RESULTS", width / 2 - 75, 50); //displays the string value

            if (AI && playerWon == 2) //AI is true at this point and playerWon should equal 2, this means that paddle two has won
            {
                g.drawString("AI WINS!", width / 2 - 170, 200); //AI won
            }
            else
            {
                g.drawString("PLAYER " + playerWon + " WINS!", width / 2 - 165, 200); //takes care of one condition a bove and there's only one ohter option.
            }

            g.setFont(new Font("Arial", 1, 30));//sets up new font

            g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25); //displays options after match has ended
            g.drawString("Press ESC for Menu", width / 2 - 140, height / 2 + 25); //you can go back and play with another user
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //update before you make a jframe
        if (play == 2) {        //if game is paused
            update();           //update the screen and what the paddles an ball are doing
        }
        renderer.repaint();     //resets the whole thing
    }

    public static void main(String [] args) {
        GamesRecord gamesRecord = new GamesRecord(); //instance of the gamesrecord implementation
        pong = new Pong();  //makes instance of pong.
        gamesRecord.add(pong); //adds the pong game to the list
        pong.getPlayerId(); //playerId

        //  All the implementation stuff from GamesRecord
        System.out.println("Here is your game average: " + gamesRecord.average());
        System.out.println("Enter the player whose average score you wish to see: ");

        Scanner scanner = new Scanner(System.in);
        String usuario = scanner.next();

        System.out.println(gamesRecord.averageInd(usuario));

        System.out.println("Here is an ordered list fo players from lowest to highest score: " + gamesRecord.highGameList());
        System.out.println("Thanks for playing Hangman!");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();       //here everytime you press a key, data member key gets the code using object e

        if(key == KeyEvent.VK_W){ //if key pressed is the w key, w changes boolean to true
            w = true;
        } else if(key == KeyEvent.VK_S){ //if key pressed is the s key, s changes boolean to true
            s = true;
        }else if(key == KeyEvent.VK_UP){//if key pressed is the up arrow, up changes boolean to true
            up = true;
        }else if(key == KeyEvent.VK_DOWN){ //if key pressed is the down arrow, down changes boolean to true
            down = true;
        }else if(key == KeyEvent.VK_RIGHT){ //what happens when you press the right arrow //this only happens when you are going throught the AI difficulty.
            if(difficultyType){  //no matter if true or false it will visit and go and perform actions here
                if(AIdifficulty < 2){ //AI difficulty has three types 0, 1, 2    //so if you hit the key, it either goes from 0 to 1 - or 1 to 2
                    AIdifficulty++;
                }else{
                    AIdifficulty = 0;   //AI difficulty is at max, so it cycles back around.
                }
            }else if (play == 0){   //nothing is hapenning in the screen
                gameOver++;     //this happens when
            }
        }else if(key == KeyEvent.VK_LEFT){      //same logic as when you press the right arrow.
            if(difficultyType){
                if(AIdifficulty > 0){
                    AIdifficulty--;
                }else{
                    AIdifficulty = 2;
                }
            }else if (play == 0 && gameOver > 2){   //nothing is on the screen and
                gameOver--;
            }
        }else if(key == KeyEvent.VK_ESCAPE && (play == 2 || play == 3)){
            play = 0;
        }else if(key == KeyEvent.VK_A && play == 0){
            AI = true;      //what happens when user decides to play the 'AI'
            difficultyType = true;  //difficulty gets turned on
        }else if(key == KeyEvent.VK_SPACE){ //space is when you are paused
            if(play == 0 || play == 3){ //if nothing is on the screen or game is over, the game is "paused"
                if (!difficultyType) { //AI is not engaged
                    AI = false; //AI is then turned off
                } else {
                    difficultyType = false; //as well as difficulty options
                }
                start(); //you play with another user
            }
            else if(play == 1){ //when game is paused and then resumes after hitting the space
                play = 2;
            }
            else if(play == 2){ //game is running and is then paused.
                play = 1;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //key are just turned off when they are not pressed.
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W){
            w = false;
        }else if(key == KeyEvent.VK_S){
            s = false;
        }else if(key == KeyEvent.VK_UP){
            up = false;
        }else if(key == KeyEvent.VK_DOWN){
            down = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public int getScore() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which players' score would you like to view?" + "\n" + "player1 or player2");
        String choice = scanner.next();
        if (choice.equals("player1")){
            return player1.score;
        }
        else
            return player2.score;
    }

    @Override
    public String getPlayerId() {
        String player1 = "player1";
        String player2 = "player2";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which players' score would you like to view?" + "\n" + "player1 or player2");
        String choice = scanner.next();
        if (choice.equals("player1")){
            return player1;
        }
        else
            return player2;
    }
}
