package org.cuml2svg.tools;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
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
	public MyCanvas myCanvas1;
	private JButton computeButton;
	private JTextField stopTextLayout;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JTextField startTextLayout;
	private JPanel jPanel1;
	private JSlider jSlider1;
	private JButton randomButton;

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
				myCanvas1 = new MyCanvas();
				getContentPane().add(myCanvas1, BorderLayout.CENTER);
				myCanvas1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 3, false));
			}
			{
				jPanel1 = new JPanel();
				GridBagLayout jPanel1Layout = new GridBagLayout();
				jPanel1Layout.rowWeights = new double[] {1.0, 0.01, 0.01, 0.01, 0.01, 0.01, 0.01};
				jPanel1Layout.rowHeights = new int[] {7, 7, 7, 20, 20, 20, 20};
				jPanel1Layout.columnWeights = new double[] {0.01};
				jPanel1Layout.columnWidths = new int[] {7};
				jPanel1.setLayout(jPanel1Layout);
				getContentPane().add(jPanel1, BorderLayout.WEST);
				{
					jSlider1 = new JSlider();
					jPanel1.add(jSlider1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
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
					startTextLayout = new JTextField();
					jPanel1.add(startTextLayout, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					startTextLayout.setSize(40, 40);
					startTextLayout.setPreferredSize(new java.awt.Dimension(40, 40));
					startTextLayout.setFont(new java.awt.Font("DejaVu Sans",0,14));
				}
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 0, 0, 1), 0, 0));
					jLabel1.setText("Start");
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText("Stop");
				}
				{
					stopTextLayout = new JTextField();
					jPanel1.add(stopTextLayout, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					stopTextLayout.setSize(40, -1);
					stopTextLayout.setPreferredSize(new java.awt.Dimension(40,40));
					stopTextLayout.setFont(new java.awt.Font("DejaVu Sans",0,14));
				}
				{
					computeButton = new JButton();
					jPanel1.add(computeButton, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					computeButton.setText("Go!");
					computeButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							computeButtonMouseClicked(evt);
						}
					});
				}
				{
					randomButton = new JButton();
					jPanel1.add(randomButton, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					randomButton.setText("Rnd!");
					randomButton.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							randomButtonMouseClicked(evt);
						}
					});
				}
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
	
	private void computeButtonMouseClicked(MouseEvent evt) {
		try {
			//myCanvas1.changePath(Integer.parseInt(startTextLayout.getText()),Integer.parseInt(stopTextLayout.getText()));
		} catch (Exception e) {
			System.err.println("Controlla l'input probabilmente non hai inserito un numero");
		}		
	}
	private void randomButtonMouseClicked(MouseEvent evt) {
		try {
			int pathStart = (int)Math.round( Math.random()*(myCanvas1.rectangleArray.size()-1));
			int pathStop;
			do{
			pathStop=(int)Math.round( Math.random()*(myCanvas1.rectangleArray.size()-1));
			}while(pathStart==pathStop);
			//myCanvas1.changePath(pathStart,pathStop);
		} catch (Exception e) {
			System.err.println("Controlla l'input probabilmente non hai inserito un numero");
		}		
	}

}
