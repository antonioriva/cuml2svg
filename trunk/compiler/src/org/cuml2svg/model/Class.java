package org.cuml2svg.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.cuml2svg.model.Diagram.OutputType;
import org.cuml2svg.svg.GraphicsManager;

/**
 * The model for the Class object
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Class extends Groupable implements Object, Comparable<Class> {

	private static final String SVG_CLASS_TEMPLATE = "templates/SVGClass.vm";

	private static final int TYPE_CLASS = 0;

	private static final int TYPE_INTERFACE = 1;

	private boolean methodsCollapsed = false;

	private boolean attributesCollapsed = false;

	private ArrayList<Relation> relations = new ArrayList<Relation>();

	/**
	 * The name of the class
	 */
	private String className;

	/**
	 * Class or interface
	 */
	private int type;

	/**
	 * The list of attributes of the class
	 */
	private List<Attribute> attributes;

	/**
	 * The list of methods of the class
	 */
	private List<Method> methods;

	/**
	 * Create a new Class object
	 * 
	 * @param className
	 *            the name of the class object
	 */
	public Class(String className) {
		this.className = className;

		this.attributes = new ArrayList<Attribute>();
		this.methods = new ArrayList<Method>();
	}

	/**
	 * Render the object to the output file
	 * 
	 * @param type
	 *            the type of the output for rendering
	 * @param context
	 *            the VelocityContext to use
	 * @param writer
	 *            the output writer
	 * @return true if render succeed, false otherwise
	 */
	public boolean render(OutputType type, VelocityContext context,
			Writer writer) {
		try {
			this.methodsCollapsed = (Boolean) context.get("methodsCollapsed");
			this.attributesCollapsed = (Boolean) context
					.get("attributesCollapsed");
			Template template = Velocity.getTemplate(SVG_CLASS_TEMPLATE);
			context.put("x", 0);
			context.put("y", 0);
			context.put("width", this.computeWidth());
			context.put("height", this.computeHeight());
			context.put("xtran", this.getXtran());
			context.put("ytran", this.getYtran());

			context.put("className", this.className);
			context.put("attributes", this.attributes);
			context.put("methods", this.methods);
			template.merge(context, writer);

			for (Relation relation : relations) {
				GraphicsManager.getInstance().addRelation(relation);
			}
			return true;
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get the class name
	 * 
	 * @return the name of the class
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Set the class name
	 * 
	 * @param className
	 *            the name of the class
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Add the given attribute to the class attribute list
	 * 
	 * @param attribute
	 *            the attribute to add
	 */
	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}

	/**
	 * Remove the give attribute from the list
	 * 
	 * @param attribute
	 *            the attribute to be removed
	 */
	public void removeAttribute(Attribute attribute) {
		this.attributes.remove(attribute);
	}

	/**
	 * Add the given method to the class method list
	 * 
	 * @param method
	 *            the method to be added
	 */
	public void addMethod(Method method) {
		this.methods.add(method);
	}

	/**
	 * Remove the given method from the list
	 * 
	 * @param method
	 *            the method to be removed
	 */
	public void removeMethod(Method method) {
		this.methods.remove(method);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Class compared) {
		return this.className.compareTo(compared.className);
	}

	/**
	 * Get the type of the class
	 * 
	 * @return the type of the class
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set the type of the class
	 * 
	 * @param type
	 *            The type of the class TYPE_CLASS or TYPE_INTERFACE
	 */
	public void setType(int type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Groupable#computeHeight()
	 */
	@Override
	public int computeWidth() {
		int maxLength = this.className.length();
		if (!this.methodsCollapsed) {
			for (Iterator i = methods.iterator(); i.hasNext();) {
				Method method = (Method) i.next();
				if (method.getMethodName().length() > maxLength) {
					maxLength = method.getMethodName().length();
				}
			}
		}
		if (!this.attributesCollapsed) {
			for (Iterator i = attributes.iterator(); i.hasNext();) {
				Attribute attribute = (Attribute) i.next();
				if (attribute.getAttributeName().length() > maxLength) {
					maxLength = attribute.getAttributeName().length();
				}
			}
		}
		return 18 * (maxLength + 4);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Groupable#computeWidth()
	 */
	@Override
	public int computeHeight() {
		int numItems = 2;
		if (!this.methodsCollapsed) {
			numItems += methods.size();
		}
		if (!this.attributesCollapsed) {
			numItems += attributes.size();
		}
		return 38 * numItems;
	}

	/**
	 * @return the attributesCollapsed
	 */
	public boolean isAttributesCollapsed() {
		return attributesCollapsed;
	}

	/**
	 * @param attributesCollapsed
	 *            the attributesCollapsed to set
	 */
	public void setAttributesCollapsed(boolean attributesCollapsed) {
		this.attributesCollapsed = attributesCollapsed;
	}

	/**
	 * @return the methodsCollapsed
	 */
	public boolean isMethodsCollapsed() {
		return methodsCollapsed;
	}

	/**
	 * @param methodsCollapsed
	 *            the methodsCollapsed to set
	 */
	public void setMethodsCollapsed(boolean methodsCollapsed) {
		this.methodsCollapsed = methodsCollapsed;
	}

	/**
	 * @param relation
	 *            the relation to add
	 */
	public void addRelation(Relation relation) {
		this.relations.add(relation);
	}

	/**
	 * @param pos
	 *            the position of the relation
	 * @return the relation in the given position
	 */
	public Relation getRelation(int pos) {
		return this.relations.get(pos);
	}

	public void place() {
		// TODO Auto-generated method stub

	}

	public void updateReference() {
		GraphicsManager.getInstance().addRectangle(this);

	}
}
