
package boatgame;

import java.awt.*;
import java.awt.event.*;
import static java.lang.Compiler.command;
//import static java.lang.Thread.sleep;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;


public class Board extends JPanel implements ActionListener
{
    private Image boat;
    private Image fish;
    
    private final int BOAT_SIZE = 10; //500 * 500 = 250000 /100 = 2500 // 500 * 500 = 250000/100 = 2500
    private final int ALL_BOATS = 2500; //2500; //BOATS THAT CAN FIT ON SCREEN
    
    private final int RANDOM_POSTION = 29;
    
    private int fish_x;
    private int fish_y;
    private final int FISH_SIZE = 10;
    
    
    //TO SET POSITION
    private final int x[] = new int[ALL_BOATS];
    private final int y[] = new int[ALL_BOATS];
    
    //BOOLEAN VARIABLES FOR KEYS
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;
    
    private int score = 0;
    private int speed = 0;

    JPanel jp = new JPanel();
    
    
    JProgressBar fuelTank = new JProgressBar(0, 1000);

    
    //SPEED
    private Timer timer;

    
    
    Board()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(500, 500));
        
        setFocusable(true);
        
        loadImages();
        
        initGame();
        
        //Fuel Capacity
        fuelTank.setForeground(Color.ORANGE);
        fuelTank.setStringPainted(true);
        fuelTank.setString("Fuel Tank");
        fuelTank.setValue(1000);      
                
        //Fuel Tank
        jp.add(fuelTank);
        
        setLayout(new BorderLayout());
        add(jp, BorderLayout.SOUTH);
    }

    
    //TO LOAD IMAGES
    public void loadImages()
    {
        ImageIcon b ;
        b = new ImageIcon(ClassLoader.getSystemResource("boatgame/images/boat1.png"));
        boat = b.getImage();
        
        ImageIcon m;
        m = new ImageIcon(ClassLoader.getSystemResource("boatgame/images/seafish.png"));
        fish = m.getImage();
    }
    
    
    public void initGame()
    {
        x[100]= 10 * BOAT_SIZE; //location of boat
        y[0] = 10;
        catchFish();
         
        boatSpeed(150);
    }
    
    
    //============TIMER
    public Timer boatSpeed(int sp)
    {
        timer = new Timer(sp, this);
        timer.start(); 
        speed = sp;
        return timer;
    }
    
    
    public void catchFish()
    {
        int r = (int)(Math.random() * RANDOM_POSTION); 
        fish_x = (r * FISH_SIZE);
        
        r = (int)(Math.random() * RANDOM_POSTION); 
        fish_y = (r * FISH_SIZE);
    }
    
    
    //TO CHECK IF FISH IS CATCHED
    public void checkFish()
    {
        if((x[0] == fish_x) && (y[0] == fish_y))
        {
            catchFish(); 
            score++;
        }
    }
    
    //TO DRAW COMPONENTS ON THE SCREEN
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
       
        //Scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Score:" +  score, 440, 18);
        
        //Direction
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 11));
        g.drawString("Direcion: " +  x[0] + "," + y[0], 6, 10);
        
        //Speed
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 11));
        g.drawString("Speed: " + speed + " ms", 6, 22);

        
        //Instructions
        g.setFont(new Font("arial", Font.PLAIN, 11));
        g.drawString("Press 'S' to Increase Speed", 355, 448);
        g.drawString("Press 'D' to Decrease Speed", 355, 463);
        g.drawString("Press 'N' to Reset Speed", 5, 448);
        g.drawString("Press 'R' to Refill Tank", 5, 463);
        g.drawString("Press 'Y' to Stop Boat", 190, 448);
        g.drawString("Press 'U' to Restart Boat", 190, 463);
        
        draw(g);
    }
    
    public void draw(Graphics g)
    {
        if(inGame)
        {
            
            g.drawImage(fish, fish_x, fish_y, this);
            
            g.drawImage(boat, x[0], y[0], this);
            
            Toolkit.getDefaultToolkit().sync();
           
        }
        else
        {
            gameOver(g);   
        }
    }
    

    
    public void gameOver(Graphics g)
    {
        String msg = "Game Over";
        String msg1 = "Created by: Karanveer Singh";
        Font font = new Font("SAN_SERIF", Font.BOLD, 20);
        FontMetrics metrics = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (490 - metrics.stringWidth(msg))/2, 450/2);
        g.drawString(msg1, (490 - metrics.stringWidth(msg1))/2, 500/2);
    }
    
    
    //TO CHECK COLLISION OF BOAT WITH FRAME
    public void checkCollision()
    {
        if(y[0] >= 460) //frame size is 500
        {
            inGame = false;
        }
        if(x[0] >= 500) //frame size is 500
        {
            inGame = false;
        }
        if(x[0] < 0) //frame size is 500
        {
            inGame = false;
        }
        if(y[0] < 0) //frame size is 500
        {
            inGame = false;
        }
        if(!inGame)
        {
            timer.stop();
        }
    }
    
    
    
    //ANIMATING BOAT
    public void move()
    {
        if(leftDirection)
        {
            x[0] -= BOAT_SIZE; 
        }
        if(rightDirection)
        {
            x[0] += BOAT_SIZE; 
        }
        if(upDirection)
        {
            y[0] -= BOAT_SIZE; 
        }
        if(downDirection)
        {
            y[0] += BOAT_SIZE; 
        }
    }
    
    
    public void actionPerformed(ActionEvent ae)
    {        
        if(inGame)
        {
            checkFish();
            checkCollision();
            move();
            
            
            //FUEL TANK
            fuelTank.setValue((int)(fuelTank.getValue() - 2));
            if(fuelTank.getValue() == 300)
            {
                //DISPLAY MESSAGE OF LOW FUEL
                fuelTank.setString("Low Fuel");
                fuelTank.setForeground(Color.red);
            }
            
            //FUEL TANK EMPTY
            if(fuelTank.getValue() == 0)
            {
                timer.stop();
                showMessageDialog(null, "Fuel Tank Empty!!\nGame Over");
                repaint();
            }
        }
        
        repaint(); 
    }
    
    
    private class TAdapter extends KeyAdapter
    {
        //OVERRIDE
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            
            
            //STOP BOAT
            if(key == KeyEvent.VK_Y)
            {
                timer.stop();
            }
            //RESTART BOAT
            if(key == KeyEvent.VK_U)
            {
                timer.restart();
            }
            
            //SPEED CHANGE
            if(key == KeyEvent.VK_S) // INCREASE THE SPEED
            {
                timer.stop();
                boatSpeed(100);
                timer.restart();
            }
            if(key == KeyEvent.VK_N) //NORMALISE THE SPEED
            {
                timer.stop();
                boatSpeed(150);
                timer.restart();
            }
            if(key == KeyEvent.VK_D)//DECREASE THE SPEED
            {
                timer.stop();
                boatSpeed(200);
                timer.restart();
            }
            
            //To Refill Tank
            if(key == KeyEvent.VK_R)
            {
                fuelTank.setValue(1000);
                fuelTank.setString("Fuel Tank");
                fuelTank.setForeground(Color.ORANGE);
            }
            
            
            //DIRECTIONS CHANGE
            if(key == KeyEvent.VK_LEFT && (!rightDirection))
            {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection))
            {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection))
            {
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection))
            {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}
