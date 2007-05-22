package org.cuml2svg.eclipse.ui.editors.model;

import org.eclipse.jface.text.rules.*;

public class ModelPartitionScanner extends RuleBasedPartitionScanner {
	public final static String MODEL_COMMENT = "__model_comment";

	public final static String MODEL_STRING = "__model_string";
	//public final static String XML_TAG = "__xml_tag";

	public ModelPartitionScanner() {

		IToken comment = new Token(MODEL_COMMENT);
		IToken string = new Token(MODEL_STRING);

		IPredicateRule[] rules = new IPredicateRule[4];

		rules[0] = new MultiLineRule("/*", "*/", comment);
		rules[2] = new SingleLineRule("#", "\n", comment);
		rules[1] = new SingleLineRule("//", "\n", comment);
		rules[3] = new MultiLineRule("\"", "\"", string);

		setPredicateRules(rules);
	}

}
