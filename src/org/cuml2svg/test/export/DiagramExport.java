package org.cuml2svg.test.export;

import org.cuml2svg.model.Attribute;
import org.cuml2svg.model.Diagram;
import org.cuml2svg.model.Class;
import org.cuml2svg.model.Group;
import org.cuml2svg.model.Method;
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
		Class firstClass = new Class("Classe di prova");
		Class secondClass = new Class("Seconda classe");
		Attribute attribute1 = new Attribute("counter");
		attribute1.setAttributeType("int");
		firstClass.addAttribute(attribute1);
		Method method1 = new Method("print()");
		Method method2 = new Method("toString()");
		firstClass.addMethod(method1);
		firstClass.addMethod(method2);
		
		secondClass.addAttribute(attribute1);
		secondClass.addMethod(method1);
		secondClass.addMethod(method2);
		Group group = new Group();
		group.addObject(firstClass);
		group.addObject(secondClass);
		diagram.addObject(group);
		
		SVGExporter exporter = new SVGExporter(diagram,"output/FirstStep.svg");
		exporter.export();
	}
}
