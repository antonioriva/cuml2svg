package org.cuml2svg.model;

/**
 * Abstract class to handle coordinates of the grouped objects
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public abstract class Groupable implements Object, Renderable{

	private int xtran = 0;

	private int ytran = 0;

	/**
	 * @return the xtran
	 */
	public int getXtran() {
		return xtran;
	}

	/**
	 * @param xtran
	 *            the xtran to set
	 */
	public void setXtran(int xtran) {
		this.xtran = xtran;
	}

	/**
	 * @return the ytran
	 */
	public int getYtran() {
		return ytran;
	}

	/**
	 * @param ytran
	 *            the ytran to set
	 */
	public void setYtran(int ytran) {
		this.ytran = ytran;
	}

	/**
	 * Compute the width of the object
	 * 
	 * @return the object width
	 */
	public int computeWidth() {
		return 0;
	}

	/**
	 * Compute the height of the object
	 * 
	 * @return the object height
	 */
	public int computeHeight() {
		return 0;
	}
}
