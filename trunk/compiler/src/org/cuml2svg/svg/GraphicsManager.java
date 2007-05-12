package org.cuml2svg.svg;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.cuml2svg.model.Groupable;
import org.cuml2svg.model.Object;

/**
 * Class for handling graphics
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class GraphicsManager {
	private static GraphicsManager instance;

	private ArrayList<Rectangle> objects;

	private GraphicsManager() {
		this.objects = new ArrayList<Rectangle>();
	}

	/**
	 * @return the instance of the object
	 */
	public static GraphicsManager getInstance() {
		if (GraphicsManager.instance == null) {
			GraphicsManager.instance = new GraphicsManager();
		}
		return GraphicsManager.instance;
	}

	/**
	 * Add an object to the array
	 * 
	 * @param object
	 *            the object to add to the array
	 */
	public void addObject(Object object) {
		
		if (object instanceof org.cuml2svg.model.Class) {
			Rectangle rectangle = new Rectangle();
			Groupable groupable = (Groupable) object;
			rectangle.setLocation(groupable.getXtran(), groupable.getYtran());
			rectangle.setSize(groupable.computeWidth(), groupable
					.computeHeight());
			this.objects.add(rectangle);
		}
	}

	/**
	 * Get the object array
	 * 
	 * @return the array of objects
	 */
	public ArrayList<Rectangle> getObjects() {
		System.out.println("getting "+this.objects.size()+" objects");
		return this.objects;
	}

	/**
	 * Get the object in the given position
	 * 
	 * @param pos
	 *            the position of the object in the array
	 * @return the object in the given position
	 */
	public Rectangle getObject(int pos) {
		return this.objects.get(pos);
	}
	
	public void print() {
		for (Rectangle rectangle : objects) {
			System.out.printf("X: %d Y:%d W: %d H: %d \n", rectangle.x,rectangle.y, rectangle.width, rectangle.height);
		}
	}
}
