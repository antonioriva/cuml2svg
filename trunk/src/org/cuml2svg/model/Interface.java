package org.cuml2svg.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.cuml2svg.model.Diagram.OutputType;

/**
 * The model for the Class object
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Interface implements Object {

	private static final String SVG_INTERFACE_TEMPLATE = "templates/SVGInterface.vm";

	/**
	 * The name of the class
	 */
	private String interfaceName;

	/**
	 * The list of attributes of the class
	 */
	private List<Attribute> attributes;

	/**
	 * The list of methods of the class
	 */
	private List<Method> methods;

	/**
	 * Create a new Interface object
	 * 
	 * @param interfaceName
	 *            the name of the interface object
	 */
	public Interface(String interfaceName) {
		this.interfaceName = interfaceName;

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
		// TODO Auto-generated method stub
		try {
			Template template = Velocity.getTemplate(SVG_INTERFACE_TEMPLATE);
			template.merge(context, writer);
			return true;
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get the interface name
	 * 
	 * @return the name of the interface
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * Set the interface name
	 * 
	 * @param interfaceName
	 *            the name of the interface
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * Add the given attribute to the interface attribute list
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
	 * Add the given method to the interface method list
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

}
