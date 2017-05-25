package lineoffive;

import java.awt.*;
import javax.swing.*;

public class CellIcon implements Icon
{
    // make an enum for this
    public static final int BLANK = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int MIDDLE = 2;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;
     
    private int size = 9;
    private int state = BLANK;
    private int horizontal = MIDDLE;
    private int vertical = MIDDLE;
    private boolean isStar = false;

    public CellIcon(int size, int state)
    {
        this(size, state, MIDDLE, MIDDLE);
    }
    
    public CellIcon(int size, int state, int horizontal, int vertical)
    {
        this(size, state, horizontal, vertical, false);
    }
    
    public CellIcon(int size, int state, int horizontal, int vertical, boolean isStar)
    {
        this.size = size;
        this.state = state;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.isStar = isStar;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
    
    public void setState(int state)
    {
        this.state = state;
    }
    
    public int getState()
    {
        return this.state;
    }
    
    public int getIconHeight() 
    {
        return size;
    }

    public int getIconWidth() 
    {
        return size;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) 
    {
        g.translate(x, y);
        //g.setColor(c.getBackground());
        g.setColor(Color.green);
        g.fillRect(-1, -1, this.size+1, this.size+1);
        
        if (c.isEnabled()) 
        {
            g.setColor(c.getForeground());
        } 
        else 
        {
            g.setColor(Color.gray);
        }
        
        int x0 = (horizontal == LEFT) ? this.size/2 : -1;
        int x1 = (horizontal == RIGHT) ? this.size/2 : this.size;
        int y0 = (vertical == TOP) ? this.size/2 : -1;
        int y1 = (vertical == BOTTOM) ? this.size/2 : this.size;
        g.drawLine(x0, this.size/2, x1, this.size/2);
        g.drawLine(this.size/2, y0, this.size/2, y1);
        if(this.isStar == true)
        {
            int w = this.size/2;
            g.fillOval(this.size/2-w/2, this.size/2-w/2, w, w);
        }
        
        if(this.state == BLACK)
        {
            g.setColor(Color.black);
            g.fillOval(0, 0, this.size-1, this.size-1);
        }
        else if(this.state == WHITE)
        {
            g.setColor(Color.white);
            g.fillOval(0, 0, this.size-1, this.size-1);
        }
        g.translate(-x, -y);   //Restore graphics object
    }
}

