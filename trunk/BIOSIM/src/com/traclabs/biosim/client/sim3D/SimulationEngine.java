package biosim.client.sim3D;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.vecmath.*;
import com.xith3d.scenegraph.*;
import com.xith3d.test.*;
import com.xith3d.render.*;
import com.xith3d.render.jogl.*;
import com.xith3d.userinterface.*;
import biosim.idl.framework.*;
import biosim.client.util.*;

/**
 * Creates a 3D representation of the simulation
 * @author    Scott Bell
 */	

public class SimulationEngine
{
	private int myID;
	private float rotY = 0;

	private TransformGroup objRotate;
	private Transform3D rotate;
	private View view;

	public SimulationEngine(int pID){
		myID = pID;
		// create the virtual universe
		VirtualUniverse universe = new VirtualUniverse();

		// add a view to the universe
		view = new View();
		universe.addView(view);

		// add a locale
		Locale locale = new Locale();
		universe.addLocale(locale);

		// create a BranchGroup
		BranchGroup scene = new BranchGroup();
		locale.addBranchGraph(scene);

		// let objects along this path rotate
		rotate = new Transform3D();
		objRotate = new TransformGroup(rotate);
		scene.addChild(objRotate);

		// create Cube
		Geometry geo = Cube.createCubeViaTriangles(0, 0, 0, 1, true);
		Shape3D sh = new Shape3D(geo, new Appearance());
		objRotate.addChild(sh);

		// create a canvas for our graphics
		RenderPeer rp = new RenderPeerImpl();
		CanvasPeer cp = rp.makeCanvas(null, 800, 600, 32, false);
		Canvas3D canvas = new Canvas3D();
		canvas.set3DPeer(cp);

		// modify our view so we can see the cube
		view.addCanvas3D(canvas);
		view.getTransform().lookAt(new Vector3f( 0, 0, 2),   // location of eye
		                           new Vector3f( 0, 0, 0),   // center of view
		                           new Vector3f( 0, 1, 0));  // vector pointing up

		// create a framerate counter
		TickCounter counter = new TickCounter(canvas);

		rotY = 0.0f;

	}

	public void runEngine(){
		// main render loop
		while(true)
		{
			// rotate around a bit
			rotY += (0.003 * BioHolderInitializer.getBioHolder().theBioDriver.getTicks());
			rotate.rotXYZ((float)Math.PI/4, rotY, (float)Math.PI/4);
			objRotate.setTransform(rotate);
			// render the whole scene
			view.renderOnce();
		}
	}

	private class TickCounter
	{
		// Swing components
		private JTextField textField;
		private JPanel panel;
		private TickWindow window;

		/**
		 * This is the window for the frame counter and the exit button.
		 */
		class TickWindow extends UIWindow
		{
			/**
			 * Creates the window.
			 * @param width              The width of the window.
			 * @param height             The height of the window.
			 */
			public TickWindow(int width, int height){
				super(width, height, false, false);
				// create a double buffered JPanel
				panel = new JPanel(true);
				panel.setSize(new Dimension(width,height));
				panel.setBackground(Color.darkGray);

				// create a text field with appropriate properties
				textField = new JTextField("0",4);
				textField.setBackground(new Color(0.0f, 0.0f, 0.0f));
				textField.setForeground(new Color(1.0f, 1.0f, 1.0f));
				textField.setHorizontalAlignment(JTextField.CENTER);
				panel.add(textField);

				// include the exit button if it's wanted
				JButton exitButton = new JButton();
				exitButton.setText("Exit");
				exitButton.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e){
							System.exit(0);
						}
					}
				);
				panel.add(exitButton);
				// specify the root component of the UI window
				setRoot(panel);
			}

			/**
			 * This method is called once a frame. We use it to update the frame
			 * counter. This can be disabled by calling setAutoUpdate(false).
			 */
			public void update(){
				super.update();
				nextFrame();
			}

		}

		/**
		 * Simple constructor, which places the counter in the upper-left
		 * corner and displays an exit button. Added for convenience.
		 *
		 * @param canvas The Canvas3D the userinterface is responsible for.
		 */
		public TickCounter(Canvas3D canvas){
			this(canvas, 10, 10);
		}

		/**
		 * Creates a framerate counter at a certain positon with or without
		 * an exit button.
		 *
		 * @param canvas  The Canvas3D the userinterface is responsible for.
		 * @param xPos    The x-position of the framerate counter.
		 * @param yPos    The y-position of the framerate counter.
		 */
		public TickCounter(Canvas3D canvas, int xPos, int yPos){
			// configure window manager
			UIWindowManager windowMgr = new UIWindowManager(canvas);
			// make the panel a bit smaller, if there's no exit button
			window = new TickWindow(80, 60);
			windowMgr.addOverlay(window);
			windowMgr.setPosition(window, xPos, yPos);
			windowMgr.setVisible(window, true);
			// add a mouse listener for the exit button
			UIEventAdapter eventAdapter = new UIEventAdapter(windowMgr);
			canvas.get3DPeer().getComponent().addMouseListener(eventAdapter);
		}

		/**
		 * Method must be called by everytime a frame was rendered.
		 */
		public void nextFrame(){
			textField.setText(BioHolderInitializer.getBioHolder().theBioDriver.getTicks() + "");
			panel.revalidate();
		}
	}

}


