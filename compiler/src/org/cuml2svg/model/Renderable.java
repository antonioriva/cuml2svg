package org.cuml2svg.model;

import java.io.Writer;

import org.apache.velocity.VelocityContext;
import org.cuml2svg.model.Diagram.OutputType;

/**
 * A common interface for all renderable objects
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public interface Renderable {
	/**
	 * @param type
	 * @param context
	 * @param writer
	 * @return true if render succeed, false otherwise
	 */
	public boolean render(OutputType type, VelocityContext context,
			Writer writer);

	/**
	 * Place objects for rendering
	 */
	public void place();
}
