package org.cuml2svg.model;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.velocity.VelocityContext;
import org.cuml2svg.model.Diagram.OutputType;
import org.cuml2svg.svg.GraphicsManager;

/**
 * Object used to group classes and packages in the layout
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Group extends Groupable implements Object, Renderable {

	private static final int HORIZONTAL_SPACING = 50;

	private static final int VERTICAL_SPACING = 50;

	private static final int ROW_LAYOUT = 0;

	private static final int COLUMN_LAYOUT = 1;

	private static final int SQUARE_LAYOUT = 2;

	private ArrayList<Object> objects;

	private HashMap<String, String> properties;

	private int layoutRows = -1;

	private int layoutCols = -1;

	private boolean methodsCollapsed = false;

	private boolean attributesCollapsed = false;

	private boolean hideArgs;

	/**
	 * Initializes the group
	 */
	public Group() {
		this.objects = new ArrayList<Object>();
		this.properties = new HashMap<String, String>();

		this.properties.put("layout", "*x*");
		this.properties.put("margin-top", "0");
		this.properties.put("margin-left", "0");
		this.properties.put("margin-bottom", "0");
		this.properties.put("margin-right", "0");
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
		object.setHideArgs(this.hideArgs);
		if(object instanceof Class) {
			((Class)object).setAttributesCollapsed(attributesCollapsed);
			((Class)object).setMethodsCollapsed(methodsCollapsed);
		}
		if(object instanceof Group) {
			((Group)object).setAttributesCollapsed(attributesCollapsed);
			((Group)object).setMethodsCollapsed(methodsCollapsed);
		}
		this.objects.add(object);
		GraphicsManager.getInstance().addObject(object);
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
		/*
		 * int layout = this.getLayout(); int rows = 0; int cols = 0; int
		 * current = 0; int leftMargin = this.getLeftMargin(); int topMargin =
		 * this.getTopMargin(); int origXtran = this.getXtran() + leftMargin;
		 * int origYtran = this.getYtran() + topMargin;
		 * 
		 * //Add properties to the context context.put("methodsCollapsed",
		 * this.methodsCollapsed); context.put("attributesCollapsed",
		 * this.attributesCollapsed);
		 * 
		 * //Choose the rendering method depending on the selected layout switch
		 * (layout) { case ROW_LAYOUT: cols = (int) Math.ceil(objects.size() /
		 * (double)this.layoutRows); current = 0; for (Iterator i =
		 * objects.iterator(); i.hasNext();) { Groupable object = (Groupable)
		 * i.next(); object.setXtran(this.getXtran() + leftMargin);
		 * object.setYtran(this.getYtran() + topMargin); object.render(type,
		 * context, writer);
		 * 
		 * this.setXtran(this.getXtran() + object.computeWidth() +
		 * HORIZONTAL_SPACING); if (((++current) % cols) == 0) {
		 * this.setYtran(this.getYtran() + object.computeHeight() +
		 * VERTICAL_SPACING); this.setXtran(origXtran); }
		 * 
		 * GraphicsManager.getInstance().addObject(object); } break; case
		 * COLUMN_LAYOUT: rows = (int) Math.ceil(objects.size() /
		 * (double)this.layoutCols); current = 0; int maxColWidth = 0; for
		 * (Iterator i = objects.iterator(); i.hasNext();) { Groupable object =
		 * (Groupable) i.next(); object.setXtran(this.getXtran() + leftMargin);
		 * object.setYtran(this.getYtran() + topMargin); object.render(type,
		 * context, writer);
		 * 
		 * this.setYtran(this.getYtran() + object.computeHeight() +
		 * VERTICAL_SPACING); int width = object.computeWidth(); if(width >
		 * maxColWidth) { maxColWidth = width; } if (((++current) % rows) == 0) {
		 * this.setXtran(this.getXtran() + maxColWidth + HORIZONTAL_SPACING);
		 * this.setYtran(origYtran); maxColWidth = 0; }
		 * GraphicsManager.getInstance().addObject(object); } break; case
		 * SQUARE_LAYOUT: rows = (int) Math.ceil(Math.sqrt(objects.size()));
		 * cols = rows; current = 0; int maxHeight = 0; for (Iterator i =
		 * objects.iterator(); i.hasNext();) { Groupable object = (Groupable)
		 * i.next(); object.setXtran(this.getXtran() + leftMargin);
		 * object.setYtran(this.getYtran() + topMargin); object.render(type,
		 * context, writer);
		 * 
		 * if (object.computeHeight() > maxHeight) { maxHeight =
		 * object.computeHeight(); } this.setXtran(this.getXtran() +
		 * object.computeWidth() + HORIZONTAL_SPACING); if (((++current) % cols) ==
		 * 0) { this.setYtran(this.getYtran() + maxHeight + VERTICAL_SPACING);
		 * this.setXtran(origXtran); }
		 * 
		 * GraphicsManager.getInstance().addObject(object); } break; }
		 */
		// place();
		// Add properties to the context
		context.put("methodsCollapsed", this.methodsCollapsed);
		context.put("attributesCollapsed", this.attributesCollapsed);
		context.put("hideArgs", this.hideArgs);
		for (Object object : objects) {
			object.render(type, context, writer);
		}
		return true;
	}

	/**
	 * @return the margin of the group
	 */
	private int getTopMargin() {
		int margin = 0;
		if (this.getProperty("margin-top") != null) {
			margin = Integer.parseInt(this.getProperty("margin-top"));
		}
		return margin;
	}

	/**
	 * @return the margin of the group
	 */
	private int getRightMargin() {
		int margin = 0;
		if (this.getProperty("margin-right") != null) {
			margin = Integer.parseInt(this.getProperty("margin-right"));
		}
		return margin;
	}

	/**
	 * @return the margin of the group
	 */
	private int getBottomMargin() {
		int margin = 0;
		if (this.getProperty("margin-bottom") != null) {
			margin = Integer.parseInt(this.getProperty("margin-bottom"));
		}
		return margin;
	}

	/**
	 * @return the margin of the group
	 */
	private int getLeftMargin() {
		int margin = 0;
		if (this.getProperty("margin-left") != null) {
			margin = Integer.parseInt(this.getProperty("margin-left"));
		}
		return margin;
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
		int maxHeight = 0;
		int maxRowHeight = 0;
		int layout = this.getLayout();
		int rows;
		int cols;
		int current;
		switch (layout) {
		case ROW_LAYOUT:
			cols = objects.size() / this.layoutRows;
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				if (maxRowHeight < object.computeHeight()) {
					maxRowHeight = object.computeHeight() + VERTICAL_SPACING;
				}
				if (((++current) % cols) == 0) {
					maxHeight += maxRowHeight;
					maxRowHeight = 0;
				}
			}
			break;
		case COLUMN_LAYOUT:
			rows = objects.size() / this.layoutCols;
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				maxRowHeight += object.computeHeight() + VERTICAL_SPACING;
				if (((++current) % rows) == 0) {
					if (maxHeight < maxRowHeight) {
						maxHeight = maxRowHeight;
						maxRowHeight = 0;
					}
				}
			}
			break;
		case SQUARE_LAYOUT:
			rows = (int) Math.ceil(Math.sqrt(objects.size()));
			cols = rows;
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				if (object.computeHeight() > maxRowHeight) {
					maxRowHeight = object.computeHeight() + VERTICAL_SPACING;
				}
				if (((++current) % cols) == 0) {
					maxHeight += maxRowHeight;
					maxRowHeight = 0;
				}
			}
			break;
		}
		return maxHeight + 2 * VERTICAL_SPACING + this.getTopMargin()
				+ this.getBottomMargin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Groupable#computeWidth()
	 */
	@Override
	public int computeWidth() {
		int maxWidth = 0;
		int maxRowWidth = 0;
		int layout = this.getLayout();
		int rows;
		int cols;
		int current;
		switch (layout) {
		case ROW_LAYOUT:
			cols = (int) Math.ceil(objects.size() / (double) this.layoutRows);
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				maxRowWidth += object.computeWidth() + HORIZONTAL_SPACING;
				if (((++current) % cols) == 0) {
					maxWidth += maxRowWidth;
					maxRowWidth = 0;
				}
			}
			break;
		case COLUMN_LAYOUT:
			rows = (int) Math.ceil(objects.size() / (double) this.layoutCols);
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				if (((++current) % rows) == 0) {
					maxWidth += object.computeWidth() + HORIZONTAL_SPACING;
				}
			}
			break;
		case SQUARE_LAYOUT:
			rows = (int) Math.ceil(Math.sqrt((double) objects.size()));
			cols = rows;
			current = 0;
			maxRowWidth = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				maxRowWidth += object.computeWidth() + HORIZONTAL_SPACING;
				if (((++current) % cols) == 0) {
					if (maxRowWidth > maxWidth) {
						maxWidth = maxRowWidth;
					}
					maxRowWidth = 0;
				}
			}
			break;
		}
		return maxWidth + 2 * HORIZONTAL_SPACING + this.getLeftMargin()
				+ this.getRightMargin();
	}

	/**
	 * @return the collapseAttributes
	 */
	public boolean isAttributesCollapsed() {
		return attributesCollapsed;
	}

	/**
	 * @param collapseAttributes
	 *            the collapseAttributes to set
	 */
	public void setAttributesCollapsed(boolean collapseAttributes) {
		this.attributesCollapsed = collapseAttributes;
		for (Object object : this.objects) {
			if(object instanceof Class) {
				Class aClass = (Class) object;
				aClass.setAttributesCollapsed(collapseAttributes);
			}
			if(object instanceof Group) {
				Group group = (Group) object;
				group.setAttributesCollapsed(collapseAttributes);
			}
		}
	}

	/**
	 * @return the collapseMethods
	 */
	public boolean isMethodsCollapsed() {
		return methodsCollapsed;
	}

	/**
	 * @param collapseMethods
	 *            the collapseMethods to set
	 */
	public void setMethodsCollapsed(boolean collapseMethods) {
		this.methodsCollapsed = collapseMethods;
		for (Object object : this.objects) {
			if(object instanceof Class) {
				Class aClass = (Class) object;
				aClass.setMethodsCollapsed(collapseMethods);
			}
			if(object instanceof Group) {
				Group group = (Group) object;
				group.setMethodsCollapsed(collapseMethods);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Renderable#place()
	 */
	public void place() {
		int layout = this.getLayout();
		int rows = 0;
		int cols = 0;
		int current = 0;
		int leftMargin = this.getLeftMargin();
		int topMargin = this.getTopMargin();
		int origXtran = this.getXtran() + leftMargin;
		int origYtran = this.getYtran() + topMargin;

		// Choose the rendering method depending on the selected layout
		switch (layout) {
		case ROW_LAYOUT:
			cols = (int) Math.ceil(objects.size() / (double) this.layoutRows);
			current = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				object.setXtran(this.getXtran() + leftMargin + HORIZONTAL_SPACING);
				object.setYtran(this.getYtran() + topMargin + VERTICAL_SPACING);

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
			rows = (int) Math.ceil(objects.size() / (double) this.layoutCols);
			current = 0;
			int maxColWidth = 0;
			for (Iterator i = objects.iterator(); i.hasNext();) {
				Groupable object = (Groupable) i.next();
				object.setXtran(this.getXtran() + leftMargin + HORIZONTAL_SPACING);
				object.setYtran(this.getYtran() + topMargin + VERTICAL_SPACING);

				this.setYtran(this.getYtran() + object.computeHeight()
						+ VERTICAL_SPACING);
				int width = object.computeWidth();
				if (width > maxColWidth) {
					maxColWidth = width;
				}
				if (((++current) % rows) == 0) {
					this.setXtran(this.getXtran() + maxColWidth
							+ HORIZONTAL_SPACING);
					this.setYtran(origYtran);
					maxColWidth = 0;
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
				object.setXtran(this.getXtran() + leftMargin + HORIZONTAL_SPACING);
				object.setYtran(this.getYtran() + topMargin + VERTICAL_SPACING);

				if (object.computeHeight() > maxHeight) {
					maxHeight = object.computeHeight();
				}
				this.setXtran(this.getXtran() + object.computeWidth()
						+ HORIZONTAL_SPACING);
				if (((++current) % cols) == 0) {
					this.setYtran(this.getYtran() + maxHeight
							+ VERTICAL_SPACING);
					this.setXtran(origXtran);
				}

			}
			break;
		}
		this.setXtran(origXtran - leftMargin);
		this.setYtran(origYtran - topMargin);
	}

	public void updateReference() {
		GraphicsManager.getInstance().addRectangle(this);
	}

	public void setHideArgs(boolean hide) {
		this.hideArgs = hide;
	}

}