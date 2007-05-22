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
	 * The attribute type
	 */
	private String attributeType;

	/**
	 * Initialize a new attribute object
	 * 
	 * @param attributeName
	 *            the name of the attribute
	 */
	public Attribute(String attributeName) {
		this.attributeName = attributeName;
		this.attributeType = "";
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

}
