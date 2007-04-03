package org.cuml2svg.model;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
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
	/**
	 * The name of the method
	 */
	private String methodName;

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
		// TODO Auto-generated method stub
		return false;
	}

}
