package org.cuml2svg.tools;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class RectDrawer extends javax.swing.JFrame {
	private MyCanvas myCanvas1;
	private JSlider jSlider1;

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		final RectDrawer inst = new RectDrawer();
		inst.setVisible(true);

		
	}
	
	public RectDrawer() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosed(WindowEvent evt) {
					rootWindowClosed(evt);
				}
			});
			{
				jSlider1 = new JSlider();
				getContentPane().add(jSlider1, BorderLayout.EAST);
				jSlider1.setOrientation(1);
				jSlider1.setMaximum(20);
				jSlider1.setMinimum(1);
				jSlider1.setValue(2);
				jSlider1.setPaintTrack(false);
				jSlider1.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						
						myCanvas1.setZoomLevel(jSlider1.getValue());
					}
				});
			}
			{
				myCanvas1 = new MyCanvas();
				getContentPane().add(myCanvas1, BorderLayout.CENTER);
				myCanvas1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
			}
			pack();
			setSize(800, 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void rootWindowClosed(WindowEvent evt) {
		this.myCanvas1.pathGenerator.stop();
	}

}
