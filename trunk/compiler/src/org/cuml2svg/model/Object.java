package org.cuml2svg.model;


/**
 * @author fabio
 * 
 */
public interface Object extends Renderable{
	/**
	 * Set the id of the object
	 * @param id the ID to set
	 */
	public void setId(int id);
	
	/**
	 * Get the object ID
	 * @return the ID of the object
	 */
	public int getId();
}
