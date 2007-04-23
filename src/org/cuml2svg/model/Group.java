package org.cuml2svg.model;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.velocity.VelocityContext;
import org.cuml2svg.model.Diagram.OutputType;

/**
 * Object used to group classes and packages in the layout
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Group extends Groupable implements Object, Renderable {

	private static final int HORIZONTAL_SPACING = 10;

	private static final int VERTICAL_SPACING = 10;

	private static final int ROW_LAYOUT = 0;

	private static final int COLUMN_LAYOUT = 1;

	private static final int SQUARE_LAYOUT = 2;

	private ArrayList<Object> objects;

	private HashMap<String, String> properties;

	private int layoutRows = -1;

	private int layoutCols = -1;

	/**
	 * Initializes the group
	 */
	public Group() {
		this.objects = new ArrayList<Object>();
		this.properties = new HashMap<String, String>();

		this.properties.put("layout", "*x*");
	}

	/**
	 * Set the value for the given property
	 * 
	 * @param property
	 *            the name of the property
	 * @param value
	 *            the value of the property
	 */
	public void setProperty(String property, String value) {
		this.properties.put(property, value);
	}

	/**
	 * Get the value of the given property
	 * 
	 * @param property
	 *            the name of the property to get
	 * @return the value of the property
	 */
	public String getProperty(String property) {
		return this.properties.get(property);
	}

	/**
	 * Add an object to the group
	 * 
	 * @param object
	 *            the object to add
	 */
	public void addObject(Object object) {
		this.objects.add(object);
	}

	/**
	 * Remove an obhect from the group
	 * 
	 * @param object
	 *            the object to remove
	 */
	public void removeObject(Object object) {
		this.objects.remove(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Renderable#render(org.cuml2svg.model.Diagram.OutputType,
	 *      org.apache.velocity.VelocityContext, java.io.Writer)
	 */
	public boolean render(OutputType type, VelocityContext context,
			Writer writer) {
		int layout = this.getLayout();
		int rows = 0;
		int cols = 0;
		int current = 0;
		int origXtran = this.getXtran();
		int origYtran = this.getYtran();
		switch (layout) {
		case ROW_LAYOUT:
			cols = objects.size() / this.layoutRows;
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				object.setXtran(this.getXtran());
				object.setYtran(this.getYtran());
				object.render(type, context, writer);

				this.setXtran(this.getXtran() + object.computeWidth()
						+ HORIZONTAL_SPACING);
				if (((++current) % cols) == 0) {
					this.setYtran(this.getYtran() + object.computeHeight()
							+ VERTICAL_SPACING);
					this.setXtran(origXtran);
				}
			}
			break;
		case COLUMN_LAYOUT:
			rows = objects.size() / this.layoutCols;
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				object.setXtran(this.getXtran());
				object.setYtran(this.getYtran());
				object.render(type, context, writer);

				this.setYtran(this.getYtran() + this.computeHeight()
						+ VERTICAL_SPACING);
				if (((++current) % rows) == 0) {
					this.setXtran(this.getXtran() + this.computeWidth()
							+ HORIZONTAL_SPACING);
					this.setYtran(origYtran);
				}
			}
			break;
		case SQUARE_LAYOUT:
			rows = (int) Math.ceil(Math.sqrt(objects.size()));
			cols = rows;
			current = 0;
			int maxHeight = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				object.setXtran(this.getXtran());
				object.setYtran(this.getYtran());
				object.render(type, context, writer);

				if(object.computeHeight() > maxHeight) {
					maxHeight = object.computeHeight();
				}
				this.setXtran(this.getXtran() + this.computeWidth()
						+ HORIZONTAL_SPACING);
				if (((++current) % cols) == 0) {
					this.setYtran(this.getYtran() + maxHeight
							+ VERTICAL_SPACING);
					this.setXtran(origXtran);
				}
			}
			break;
		}
		return true;
	}

	private int getLayout() {
		String layout = this.getProperty("layout");
		String params[] = layout.split("x");
		if (params[0].equals("*")) {
			this.layoutRows = -1;
		} else {
			this.layoutRows = Integer.parseInt(params[0]);
		}
		if (params[1].equals("*")) {
			this.layoutCols = -1;
		} else {
			this.layoutCols = Integer.parseInt(params[1]);
		}

		if (this.layoutCols != -1 && this.layoutRows == -1) {
			return COLUMN_LAYOUT;
		}
		if (this.layoutRows != -1 && this.layoutCols == -1) {
			return ROW_LAYOUT;
		}
		return SQUARE_LAYOUT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Groupable#computeHeight()
	 */
	@Override
	public int computeHeight() {
		int height = 0;
		int maxHeight = 0;
		for (Iterator i = objects.iterator(); i.hasNext();) {
			Groupable object = (Groupable) i.next();
			height = object.computeHeight();
			if (maxHeight < height) {
				maxHeight = height;
			}
		}
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Groupable#computeWidth()
	 */
	@Override
	public int computeWidth() {
		int width = 0;
		int maxWidth = 0;
		for (Iterator i = objects.iterator(); i.hasNext();) {
			Groupable object = (Groupable) i.next();
			width = object.computeWidth();
			if (maxWidth < width) {
				maxWidth = width;
			}
		}
		return width;
	}
}
