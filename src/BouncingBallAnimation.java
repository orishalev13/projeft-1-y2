import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class BouncingBallAnimation {

    public static void main(String[] args){
        int numBalls = args.length;
        Ball[] balls = new Ball[numBalls];
        int width = 600;
        int height = 600;
        GUI gui = new GUI(" Bouncing balls",width,height);
        for(int i=0; i<numBalls;i++)
        {
            int size = Integer.parseInt(args[i]);
            Random random= new Random();
            int x= random.nextInt(width-size*2)+ size;
            int y= random.nextInt(width-size*2)+ size;
            Color color= new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
            balls[i]= new Ball(x,y,size,color);
             double speed = 100/size;
             double angle = random.nextDouble(360);
            Velocity v=  Velocity.fromAngleAndSpeed(angle,speed);
             balls[i].setVelocity(v);
        }
        Sleeper sleep =new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (int i = 0; i < balls.length; i++) {
                balls[i].moveOneStep(width,height);
                balls[i].drawOn(d);

            }
            gui.show(d);
            sleep.sleepFor(50);
        }
        }
    }


