package org.cuml2svg.eclipse.ui.editors.layout;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class LayoutPartitionScanner extends RuleBasedPartitionScanner {
	public final static String MODEL_COMMENT = "__model_comment";

	public final static String MODEL_STRING = "__model_string";
	//public final static String XML_TAG = "__xml_tag";

	public LayoutPartitionScanner() {

		IToken comment = new Token(MODEL_COMMENT);
		IToken string = new Token(MODEL_STRING);

		IPredicateRule[] rules = new IPredicateRule[2];

		rules[0] = new MultiLineRule("/*", "*/", comment);
		rules[1] = new MultiLineRule("\"", "\"", string);

		setPredicateRules(rules);
	}
}
