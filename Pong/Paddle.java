package Pong;

import java.awt.*;

public class Paddle {

    public int paddleUser; //this will determine what type of paddle it is creating;
    public int x, y, width = 10, height = 100; // this will make the paddle, I am using coordinates instead of a graphic rectangle.
    public int score; // this will keep score

    public Paddle(Pong pong, int paddleUser) {
        this.paddleUser = paddleUser; //constructor for the paddle

        if(paddleUser == 1){    //player1 postion for the x
            this.x = 0;
        }
        if (paddleUser == 2){   //player2 position for the x
            this.x = (pong.width) - width;
        }
        this.y = (pong.height / 2) - (this.height / 2); // this is how coordinate plane works in java   //paddle length is the same for both paddles

    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);//the color of the paddle
        g.fillRect(x, y, width, height);// these could change by just changing the data members size.
    }

    public void move(boolean direction) {       //move is dependent on whether the keys pressed are either true or false    //true moves paddle up  //false moves it down
        int velocity = 15;//this is how fast the paddle is able to move
        if (direction){
            if(y - velocity > 0){ // y is height of the padddle, velocity is how fast it moves or how many spaces, height is the height of the thing and that should be less than the height of the window itself.
                y -= velocity;
            }else{
                y = 0;
            }
        } else {
            if(y + height + velocity < Pong.pong.height){   //when paddle is moved down
                y+= velocity;   //y changes by how fast the paddle is able to move
            } else{
                y = Pong.pong.height - height;
            }
        }

    }
}
