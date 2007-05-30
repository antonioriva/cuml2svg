package org.cuml2svg.compiler;

import org.cuml2svg.model.Attribute;
import org.cuml2svg.model.Class;
import org.cuml2svg.model.Method;
import org.cuml2svg.model.Relation;

class MethodRef{
	private Method m;
	private int line;
	private int column;
	private String fileName;
	private Class parentClass;

	public MethodRef(Method m, int line,int column,String fileName) {
		this.m = m;
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

	public Method getMethod() {
		return m;
	}
}