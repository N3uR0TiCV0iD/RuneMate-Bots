package com.loldie.bots.common.inherit;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import com.sun.jna.ptr.IntByReference;
import com.runemate.system.windows.User32;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Screen;
import com.runemate.game.api.hybrid.input.Mouse.Button;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import com.runemate.game.api.hybrid.local.hud.InteractableRectangle;
public abstract class RSOverlayWindow extends JFrame
{
	private static final long serialVersionUID = 4537286003994185874L;
	private InteractablePoint mousePoint;
	private IntByReference foregroundPID;
	private Button clickButton;
	private int runescapePID;
	private boolean newClick;
	private boolean newMove;
	public RSOverlayWindow()
	{
		System.out.println("Environment.getRuneScapeProcessId() => " + Environment.getRuneScapeProcessId());
        this.runescapePID = Integer.parseInt(Environment.getRuneScapeProcessId());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addMouseListener(new MouseAdapter()
		{
        	@Override
        	public void mouseMoved(MouseEvent e)
        	{
        		mouseMove(e);
        	}
        	@Override
            public void mouseClicked(MouseEvent e)
            {
        		mouseClick(e);
            }
		});
        this.foregroundPID = new IntByReference();
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setOpacity(0.05F);
        this.updateWindow();
	}
	private void mouseMove(MouseEvent e)
	{
		mousePoint = new InteractablePoint(e.getPoint());
		newMove = true;
	}
    private void mouseClick(MouseEvent e)
    {
		switch (e.getButton())
		{
			default:
				clickButton = Button.LEFT;
			break;
			case 2:
				clickButton = Button.RIGHT;
			break;
			case 3:
				clickButton = Button.WHEEL;
			break;
		}
		mousePoint = new InteractablePoint(e.getPoint());
		newClick = true;
    }
    public void update()
	{
    	User32.GetWindowThreadProcessId(User32.GetTopWindow(null), foregroundPID);
    	System.out.println("foregroundPID = " + foregroundPID.getValue());
    	if (foregroundPID.getValue() == runescapePID)
    	{
        	this.setVisible(true);
        	updateWindow();
            if (newClick)
            {
        		Mouse.click(mousePoint, clickButton);
        		newClick = false;
            }
            else if (newMove)
            {
            	Mouse.move(mousePoint);
            	newMove = false;
            }
    	}
    	else
    	{
        	this.setVisible(false);
    	}
	}
    private void updateWindow()
    {
		InteractableRectangle clientBounds = Screen.getBounds();
		InteractablePoint clientLocation = Screen.getLocation();
        this.setSize(clientBounds.width, clientBounds.height);
        this.setLocation(clientLocation.x, clientLocation.y);
    }
}
