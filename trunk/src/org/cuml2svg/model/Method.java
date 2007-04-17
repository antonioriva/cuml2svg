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
 * The method object
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Method implements Renderable {
	private static final String SVG_METHOD_TEMPLATE = "templates/SVGMethod.vm";
	private static final String VISIBILITY_PUBLIC = "public";
	private static final String VISIBILITY_PRIVATE = "private";
	private static final String VISIBILITY_PROTECTED = "protected";

	/**
	 * The name of the method
	 */
	private String methodName;
	
	/**
	 * The visibility of the method
	 */
	private String visibility;

	/**
	 * The list of parameters of the method
	 */
	private List<Attribute> parameters;
	

	/**
	 * Create a new method object
	 * 
	 * @param methodName
	 *            the name of the method
	 */
	public Method(String methodName) {
		this.methodName = methodName;
		this.visibility = VISIBILITY_PUBLIC;

		this.parameters = new ArrayList<Attribute>();
	}

	/**
	 * Get the name of the method
	 * 
	 * @return the name of the method
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Set the name of the method
	 * 
	 * @param methodName
	 *            the name for the method
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Add a new parameter to the method
	 * 
	 * @param parameter
	 *            the parameter to add
	 */
	public void addParameter(Attribute parameter) {
		this.parameters.add(parameter);
	}

	/**
	 * Remove the given parameter from the list
	 * 
	 * @param parameter
	 *            the parameter to be removed
	 */
	public void removeParameter(Attribute parameter) {
		this.parameters.remove(parameter);
	}

	/* (non-Javadoc)
	 * @see org.cuml2svg.model.Renderable#render(org.cuml2svg.model.Diagram.OutputType, org.apache.velocity.VelocityContext, java.io.Writer)
	 */
	public boolean render(OutputType type, VelocityContext context,
			Writer writer) {
		try {
			Template template = Velocity.getTemplate(SVG_METHOD_TEMPLATE);
			context.put("methodName", this.methodName);
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
	 * Get the visibility of the method
	 * @return the visibility of the method
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * Set the visibility of the method
	 * @param visibility The visibility level
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

}
