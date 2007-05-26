package org.cuml2svg.compiler;

import org.cuml2svg.model.Class;
import org.cuml2svg.model.Relation;

class RelationRef{
	private Relation r;
	private int line;
	private int column;
	private String fileName;
	private Class parentClass;

	public RelationRef(Relation r, int line,int column,String fileName,Class currentDefinition) {
		this.r = r;
		this.line = line;
		this.column= column;
		this.fileName = fileName;
		this.parentClass = currentDefinition;
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

	public Relation getR() {
		return r;
	}
}