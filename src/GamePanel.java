import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener{

    private static final long serialVersionIF = 1L;
    public static final int WIDTH = 500, HEIGHT = 500 ;

    private Thread t;
    private boolean running;

    private boolean right = true, left = false, up = false, down = false;
    
    private Snake snake;
    private ArrayList<Snake> snakes;

    private Bird bird;
    private ArrayList<Bird> birds;

    private Random r;

    private int xCoor = 10, yCoor = 10, size =5;
    private int ticks = 0;



    public GamePanel(){
        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        snakes = new ArrayList<Snake>();
        birds = new ArrayList<Bird>();

        r = new Random();
        start();
    }

    public void start(){
        running = true;
        t = new Thread(this);
        t.start();
    }

    public void stop(){
        running = false;

        try{
            t.join();

        }catch(InterruptedException e){
            System.out.println("Exception thrown in run() of GamePanel");
            e.printStackTrace();
        }
        

    }

    public void tick(){
        if(snakes.size() == 0){
            snake = new Snake(xCoor, yCoor, 10);
            snakes.add(snake);
        }
        ticks++;
        if(ticks >250000){
            if(right) xCoor++;
            if(left) xCoor--;
            if(up ) yCoor--;
            if(down) yCoor++;

            ticks = 0;

            snake = new Snake(xCoor, yCoor, 10);
            snakes.add(snake);
        }

        if(snakes.size() > size){
            snakes.remove(0);
        }

        if(birds.size() == 0){
            int xCoor = r.nextInt(49);
            int yCoor = r.nextInt(49);
             
            bird = new Bird(xCoor, yCoor, 10);
            birds.add(bird);
           
        }

        for(int i = 0; i < birds.size(); i++){
            if(xCoor == birds.get(i).getxCoor() && yCoor == birds.get(i).getyCoor()){
                size++;
                birds.remove(i);
                i++;
            }
        }

        //Snake Collision
        for(int i = 0; i < snakes.size(); i++){
            if(xCoor == snakes.get(i).getxCoor() && yCoor == snakes.get(i).getyCoor()){
                if(i != snakes.size() - 1){
                    System.out.println("Game Over");
                    stop();
                }
            }
        }

        //Border Collision    
        if(xCoor < 0 || xCoor >49 || yCoor < 0 || yCoor>49){
            System.out.println("Game Over");
            stop();
        }



    }

    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for(int i = 0; i < WIDTH/10; i++){
            g.drawLine(i *10, 0, i*10, HEIGHT);
        }

        for(int i = 0; i < HEIGHT/10; i++){
            g.drawLine(0, i*10, HEIGHT, i * 10);
        }

        for(int i = 0; i < snakes.size(); i++){
            snakes.get(i).draw(g);
        }

        for(int i = 0; i < birds.size(); i++){
            birds.get(i).draw(g);
        }

    }

    @Override
    public void run(){
        while(running){
            tick();
            repaint();

        }

    }

    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT && !left){
            right = true;
            up =false;
            down = false;
        }
        if(key == KeyEvent.VK_LEFT && !right){
            left = true;
            up =false;
            down = false;
        }

        if(key == KeyEvent.VK_UP && !down){
            up = true;
            left =false;
            right = false;
        }

        if(key == KeyEvent.VK_DOWN && !up){
            down = true;
            left =false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
       
    }

    @Override
    public void keyTyped(KeyEvent e){
        
    }

}