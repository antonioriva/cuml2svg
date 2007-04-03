package org.cuml2svg.test.export;

import org.cuml2svg.model.Diagram;
import org.cuml2svg.svg.SVGExporter;


/**
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 *
 */
public class DiagramExport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Diagram diagram = new Diagram("Diagramma di prova",Diagram.DiagramType.UML);
		org.cuml2svg.model.Class firstClass = new org.cuml2svg.model.Class();
		diagram.addObject(firstClass);
		
		SVGExporter exporter = new SVGExporter(diagram,"output/FirstStep.svg");
		exporter.export();
	}
}
