package org.cuml2svg.compiler;

import org.cuml2svg.model.Attribute;
import org.cuml2svg.model.Class;
import org.cuml2svg.model.Relation;

class AttributeRef{
	private Attribute a;
	private int line;
	private int column;
	private String fileName;
	private Class parentClass;

	public AttributeRef(Attribute a, int line,int column,String fileName) {
		this.a = a;
		this.line = line;
		this.column= column;
		this.fileName = fileName;
	}

	public int getColumn() {
		return column;
	}
	
	public Class getParentClass() {
		return parentClass;
	}

	public String getFileName() {
		return fileName;
	}

	public int getLine() {
		return line;
	}

	public Attribute getAttribute() {
		return a;
	}
}