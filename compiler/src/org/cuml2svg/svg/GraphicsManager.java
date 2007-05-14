package org.cuml2svg.svg;

import java.awt.Rectangle;
import java.io.Writer;
import java.util.ArrayList;

import org.apache.velocity.VelocityContext;
import org.cuml2svg.model.Class;
import org.cuml2svg.model.Groupable;
import org.cuml2svg.model.Object;
import org.cuml2svg.model.Relation;
import org.cuml2svg.model.Diagram.OutputType;

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

	private ArrayList<Rectangle> rectangles;

	private ArrayList<Object> objects;

	private ArrayList<Relation> relations;

	private GraphicsManager() {
		this.rectangles = new ArrayList<Rectangle>();
		this.objects = new ArrayList<Object>();
		this.relations = new ArrayList<Relation>();
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
		this.objects.add(object);
		if (object instanceof org.cuml2svg.model.Class) {
			Rectangle rectangle = new Rectangle();
			Groupable groupable = (Groupable) object;
			rectangle.setLocation(groupable.getXtran(), groupable.getYtran());
			rectangle.setSize(groupable.computeWidth(), groupable
					.computeHeight());
			this.rectangles.add(rectangle);
			object.setId(this.rectangles.size()-1);
		}
	}

	/**
	 * Get the object array
	 * 
	 * @return the array of objects
	 */
	public ArrayList<Rectangle> getRectangles() {
		System.out.println("getting " + this.rectangles.size() + " objects");
		return this.rectangles;
	}

	/**
	 * Get the object in the given position
	 * 
	 * @param pos
	 *            the position of the object in the array
	 * @return the object in the given position
	 */
	public Object getObject(int pos) {
		return this.objects.get(pos);
	}

	/**
	 * Get the rectangle of the object in the given position
	 * 
	 * @param pos
	 *            the position of the object in the array
	 * @return the object in the given position
	 */
	public Rectangle getRectangle(int pos) {
		return this.rectangles.get(pos);
	}

	/**
	 * Find a class given its className
	 * 
	 * @param className
	 *            the name of the class
	 * @return the class reference if found in the diagram, null otherwise
	 */
	public Class find(String className) {
		for (Object object : objects) {
			if (object instanceof Class) {
				Class aClass = (Class) object;
				if (aClass.getClassName().equals(className)) {
					return aClass;
				}
			}
		}
		return null;
	}

	/**
	 * Add a relation to the diagram
	 * 
	 * @param relation
	 *            the new relation to add
	 */
	public void addRelation(Relation relation) {
		this.relations.add(relation);
	}

	/**
	 * @return the list of relations of the diagram
	 */
	public ArrayList<Relation> getRelations() {
		return this.relations;
	}

	/**
	 * Output the coordinate of the rectangles of the objects JUST FOR DEBUG
	 */
	public void print() {
		for (Rectangle rectangle : rectangles) {
			System.out.printf("X: %d Y:%d W: %d H: %d \n", rectangle.x,
					rectangle.y, rectangle.width, rectangle.height);
		}
	}

	/**
	 * Draw relations of the diagram
	 * 
	 * @param type
	 *            the output type
	 * @param context
	 *            the velocityContext object
	 * @param writer
	 *            the writer object
	 */
	public void drawRelations(OutputType type, VelocityContext context,
			Writer writer) {
		for (Relation relation : relations) {
			relation.render(type, context, writer);
		}

	}
}
