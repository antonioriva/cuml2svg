package org.cuml2svg.eclipse.ui.editors;

import org.eclipse.jface.text.rules.IWordDetector;

public class WordDetector implements IWordDetector {


	public boolean isWordStart(char c) {
		return Character.isJavaIdentifierStart(c);
	}


	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}
}