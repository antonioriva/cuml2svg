package org.cuml2svg.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The method object
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Method {
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

	private boolean hideArgs;

	private String type;

	/**
	 * Create a new method object
	 * 
	 * @param methodName
	 *            the name of the method
	 */
	public Method(String methodName) {
		this.methodName = methodName;
		this.visibility = VISIBILITY_PUBLIC;
		this.hideArgs = false;
		this.type = "";

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

	/**
	 * Get the visibility of the method
	 * 
	 * @return the visibility of the method
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * Set the visibility of the method
	 * 
	 * @param visibility
	 *            The visibility level
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public List<Attribute> getParameters() {
		return parameters;
	}
	
	/**
	 * @return The fullName of the method populated with parameters
	 */
	public String getFullMethodName(boolean hideArgs) {
		String fullName = "";
		if(this.visibility.equals("private")){
			fullName += "-";
		}else if(this.visibility.equals("public")) {
			fullName += "+";
		}else if(this.visibility.equals("protected")) {
			fullName += "#";
		}
		fullName += this.methodName + "(";
		if(!hideArgs) {
			Attribute parameter;
			for (int i = 0; i < parameters.size()-1; i++) {
				parameter = parameters.get(i);
				fullName += parameter.getFullAttributeName();
				fullName += ",";
			}
			if(parameters.size() > 0) {
				parameter = parameters.get(parameters.size()-1);
				fullName += parameter.getFullAttributeName();
			}
		}
		fullName += ")";
		if(!this.type.equals("")) {
			fullName += ":"+this.type;
		}
		return fullName;
	}
	
	/**
	 * @return the full method name with args
	 */
	public String getFullMethodName() {
		return getFullMethodName(false);
	}

	/**
	 * @param hideArgs
	 */
	public void setHideArgs(boolean hideArgs) {
		this.hideArgs = hideArgs;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
