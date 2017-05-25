/*
 * CellButton.java
 *
 * Created on April 26, 2006, 7:38 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

/**
 *
 * @author aw
 */
package lineoffive;

import javax.swing.*; 
import java.awt.*;

public class CellButton extends JButton 
{    
    //private CellIcon cellIcon;
    
    /** Creates a new instance of CellButton */
    public CellButton(Icon cellIcon) 
    {
        super(cellIcon);
        //this.cellIcon = cellIcon;
    }
    
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);
        ((CellIcon)getIcon()).setSize(width);
    }
    
    public void setBounds(Rectangle r)
    {
        super.setBounds(r);
        ((CellIcon)getIcon()).setSize((int)r.getWidth());
    }
    
    public void setSize(int width, int height)
    {
        super.setSize(width, height);
        ((CellIcon)getIcon()).setSize(width);
    }
    
    public void setSize(Dimension dim)
    {
        super.setSize(dim);
        ((CellIcon)getIcon()).setSize((int)dim.getWidth());
    }
    
    public void setState(int state)
    {
        ((CellIcon)getIcon()).setState(state);
    }
    
    public int getState()
    {
        return ((CellIcon)getIcon()).getState();
    }
}
