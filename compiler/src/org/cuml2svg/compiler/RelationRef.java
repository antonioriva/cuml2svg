package org.cuml2svg.compiler;

import org.cuml2svg.model.Relation;

class RelationRef{
	private Relation r;
	private int line;
	private int column;
	private String fileName;

	public RelationRef(Relation r, int line,int column,String fileName) {
		this.r = r;
		this.line = line;
		this.column= column;
		this.fileName = fileName;
	}

	public int getColumn() {
		return column;
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