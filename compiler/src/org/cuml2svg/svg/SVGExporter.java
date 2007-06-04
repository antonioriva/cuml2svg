package org.cuml2svg.svg;

import java.io.PrintWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.cuml2svg.model.Diagram;

/**
 * The exporter class
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 *
 */
public class SVGExporter {
	/**
	 * The diagram to be exported
	 */
	private Diagram diagram;

	/**
	 * The name of the exported file
	 */
	private String fileName;

	private String templatePath;

	/**
	 * Create a new Exporter object
	 * 
	 * @param diagram
	 *            the diagram to be exported
	 * @param fileName
	 *            the name of the exported file
	 */
	public SVGExporter(Diagram diagram, String fileName, String templatePath) {
		this.diagram = diagram;
		this.fileName = fileName;
		this.templatePath = templatePath;
		setTemplatePath(templatePath);
	}
	
	/**
	 * @param path the new path for templates
	 */
	public void setTemplatePath(String path) {
		if(!path.equals("")) {
			GraphicsManager.setTemplatePath(path);
			System.out.println("INFO: new template path "+path);
		}
	}

	/**
	 * Export the diagram to the given output file
	 * @return true if export succeed, false otherwise
	 */
	public boolean export() {
		try {
			System.out.println("INFO: Starting export");
			//Initialize the Velocity engine
			Properties p = new Properties();
			p.put("file.resource.loader.path", GraphicsManager.getTemplatePath());
			Velocity.init(p);
			
			//Create a new VelocityContext
			VelocityContext context = new VelocityContext();
			
			//Create a new PrintWriter for file export
			PrintWriter writer = new PrintWriter(this.fileName);
			
			//Render the diagram to the context
			this.diagram.render(Diagram.OutputType.SVG, context, writer);
			
			//Flush buffer and close file
			writer.flush();
			writer.close();
			System.out.println("INFO: Export completed");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return false;
	}
}
