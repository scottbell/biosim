package biosim.client.framework.gui;

import javax.swing.*;

/**
 * 
 * @author    Scott Bell
 */
public abstract class UpdatablePanel extends JPanel 
{
	public abstract void refresh();

	public abstract void visibilityChange(boolean nowVisible);

}
