
package boatgame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Boat
{
    
    JFrame jf = new JFrame();

    JPanel p01 = new JPanel();

    
    
    Board b = new Board();
    
    public Boat()
    {
        
        JFrame jf = new JFrame();
        jf.setBounds(10, 10, 905, 700);
        
        
        Board b = new Board();
        
        p01.add(b);
        
        jf.add(p01, BorderLayout.CENTER);
        
        
        jf.pack();
      
        //TO SET LOCATION
        jf.setLocationRelativeTo(null); // TO PLACE IT ON THE CENTRE OF THE SCREEN
        
        //TO SET TITLE
        jf.setTitle("Boat Game");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true); 
        
       
    }
    
    public static void main(String[] args)
    {        
         Boat bt = new Boat();
    }

  
    
}
