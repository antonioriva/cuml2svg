package org.cuml2svg.model;

/**
 * The attribute representation
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Attribute {

	/**
	 * The name of the attribute
	 */
	private String attributeName;
	
	/**
	 * The default value of the attribute
	 */
	private String defaultValue;

	/**
	 * The attribute type
	 */
	private String attributeType;
	
	/**
	 * The attribute visibility
	 */
	private String attributeVisibility;

	/**
	 * Initialize a new attribute object
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 */
	public Attribute(String attributeName) {
		this.attributeName = attributeName;
		this.attributeType = "";
		this.attributeVisibility = "";
		this.defaultValue = "";
	}

	/**
	 * Get the attribute name
	 * 
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * Set the attribute name
	 * 
	 * @param attributeName
	 *            the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	/**
	 * Get the attribute name
	 * 
	 * @return the attributeName
	 */
	public String getFullAttributeName() {
		String name = "";
		if(this.attributeVisibility.equals("private")){
			name += "-";
		}else if(this.attributeVisibility.equals("public")) {
			name += "+";
		}else if(this.attributeVisibility.equals("protected")) {
			name += "#";
		}
		name += this.getAttributeName();
		if(!this.attributeType.equals("")) {
			name += ":"+this.attributeType;
		}
		if(!this.defaultValue.equals("")) {
			name += "="+this.defaultValue;
		}
		return name;
	}

	/**
	 * Get the attribute type
	 * 
	 * @return the attributeType
	 */
	public String getAttributeType() {
		return attributeType;
	}

	/**
	 * Set the attribute type
	 * 
	 * @param attributeType
	 *            the attributeType to set
	 */
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the default value to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the attributeVisibility
	 */
	public String getAttributeVisibility() {
		return attributeVisibility;
	}

	/**
	 * @param attributeVisibility the attributeVisibility to set
	 */
	public void setAttributeVisibility(String attributeVisibility) {
		this.attributeVisibility = attributeVisibility;
	}

}
