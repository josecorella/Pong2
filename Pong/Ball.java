package Pong;

import java.awt.*;
import java.util.Random;

public class Ball {
    public int x, y, width =25, height= 25;     //coordinates for the oval shape we will be creating
    public int motionXD, motionYD;  //motion in the x direction & y direction
    public Random random;       //use this for when the ball rebounds from paddle to have some unexpected bounces
    private Pong pong;  //this is in order to use some of the data members up in Pong
    public int diffIncrease;//this will make the game progressively more difficult

    public Ball(Pong pong) {
        this.pong = pong; //construct a pong
        random = new Random();
        createBall();   //make the ball
    }

    public void update(Paddle paddleUser1, Paddle paddleUser2){
        //paddle types are passed so that they respond accordingly.
        int speed = 5;
        this.x += motionXD * speed;     // xpostion is marked by the speed of the ball and the motion in the x direction
        this.y += motionYD * speed;     // same logic as above only in the y directiom

        if (this.y + height - motionYD > pong.height || this.y + motionYD < 0) {
            if (this.motionYD < 0) {
                this.y = 0;
                this.motionYD = random.nextInt(4);
                if (motionYD == 0) {
                    motionYD = 1;
                }
            } else {
                this.motionYD = -random.nextInt(4);
                this.y = pong.height - height;
                if (motionYD == 0) {
                    motionYD = -1;
                }
            }
        }

        if (checkCollision(paddleUser1) == 1) {
            this.motionXD = 1 + (diffIncrease / 7);
            this.motionYD = -2 + random.nextInt(4);

            if (motionYD == 0) {
                motionYD = 1;
            }
            diffIncrease++;

        }
        else if (checkCollision(paddleUser2) == 1) {
            this.motionXD = -1 - (diffIncrease / 7);
            this.motionYD = -2 + random.nextInt(4);

            if (motionYD == 0) {
                motionYD = 1;
            }
            diffIncrease++;
        }

        if (checkCollision(paddleUser1) == 2) {
            paddleUser2.score++;
            createBall(); //makes a new ball after scoring
        }
        else if (checkCollision(paddleUser2) == 2) {
            paddleUser1.score++;
            createBall(); //makes a new ball after scoring
        }

    }

    public void createBall(){
        this.diffIncrease = 0;
        this.x = pong.width / 2 - this.width / 2;
        this.y = pong.height / 2 - this.height / 2;

        this.motionYD = -2 + random.nextInt(4);

        if (motionYD == 0) {
            motionYD = 1;
        }
        if (random.nextBoolean()) {
            motionXD = 1;
        } else {
            motionXD = -1;
        }
    }

    public int checkCollision(Paddle paddle){
        if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y) {
            return 1; //bounce
        } else if ((paddle.x > x && paddle.paddleUser == 1) || (paddle.x < x - width && paddle.paddleUser == 2)) {
            return 2; //score
        }
        return 0; //nothing
    }

    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);

    }

}
