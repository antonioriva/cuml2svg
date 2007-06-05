package org.cuml2svg.model;

import java.awt.Rectangle;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.cuml2svg.svg.GraphicsManager;

/**
 * The model class for the diagram exporter
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Diagram implements Renderable {
	/**
	 * The available diagram types
	 */
	public enum DiagramType {
		/**
		 * Render UML diagram
		 */
		UML
	};

	/**
	 * The available export output types
	 */
	public enum OutputType {
		/**
		 * Export to SVG
		 */
		SVG
	}

	private static final String SVG_TEMPLATE_HEADER = "templates/SVGDiagram_header.vm";

	private static final String SVG_TEMPLATE_FOOTER = "templates/SVGDiagram_footer.vm";

	/**
	 * The type of the diagram
	 */
	private DiagramType diagramType;

	/**
	 * The name of the diagram
	 * 
	 */
	private String diagramName;

	private List<org.cuml2svg.model.Object> diagramObjects;

	private int id = 0;

	/**
	 * Create a new Diagram object
	 * 
	 * @param diagramName
	 *            the name of the diagram
	 * @param diagramType
	 *            the type of the diagram
	 */
	public Diagram(String diagramName, DiagramType diagramType) {
		this.diagramName = diagramName;
		this.diagramType = diagramType;

		// Initialize the array list of objects
		this.diagramObjects = new ArrayList<Object>();
	}

	/**
	 * Add an object to the object list
	 * 
	 * @param object
	 *            the object to be added
	 */
	public void addObject(Object object) {
		// Add the object to the list
		this.diagramObjects.add(object);
		object.setId(++this.id);
	}

	/**
	 * Remove the given object from the list
	 * 
	 * @param object
	 *            the object to be removed
	 */
	public void removeObject(Object object) {
		// Remove the given object from the list
		this.removeObject(object);
	}

	/**
	 * Render the diagram to the velocity context in the given format
	 * 
	 * @param type
	 *            the output type
	 * @param context
	 *            the VelocityContext into witch render the diagram
	 * @param writer
	 *            the writer object
	 * 
	 * @return true if correctly exported, false otherwise
	 */
	public boolean render(OutputType type, VelocityContext context,
			Writer writer) {
		try {
			if (this.diagramType.equals(DiagramType.UML)) {
				Rectangle bbox = GraphicsManager.getInstance().getBoundingBox();
				//System.out.println("BBOX: " + bbox.x + "," + bbox.y + ","+ bbox.width + "," + bbox.height);
				context.put("diagramName", this.diagramName);
				context.put("diagramX", bbox.x - 150);
				context.put("diagramY", bbox.y - 150);
				context.put("diagramWidth", bbox.width + 300);
				context.put("diagramHeight", bbox.height + 300);
				Template template = Velocity.getTemplate(SVG_TEMPLATE_HEADER);
				template.merge(context, writer);

				for (Object object : diagramObjects) {
					object.render(type, context, writer);
				}

				GraphicsManager.getInstance().drawRelations(type, context,
						writer);

				template = Velocity.getTemplate(SVG_TEMPLATE_FOOTER);
				template.merge(context, writer);
			}
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.cuml2svg.model.Renderable#place()
	 */
	public void place() {
	}
}
