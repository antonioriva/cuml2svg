package org.cuml2svg.eclipse.ui.editors.model;

import java.util.ArrayList;

import org.cuml2svg.eclipse.ui.editors.ColorConstants;
import org.cuml2svg.eclipse.ui.editors.ColorManager;
import org.cuml2svg.eclipse.ui.editors.RuleBrace;
import org.cuml2svg.eclipse.ui.editors.RuleNumber;
import org.cuml2svg.eclipse.ui.editors.WordDetector;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public class ModelScanner extends RuleBasedScanner {
	
	//TODO controllare tutte le keyword
	private static String[] keywords= { "class","package","public","private","interface", 
		"methods","extend",
		"use","include","composed","depend","associate","realize","relations", "attributes"  };
	
	
	public ModelScanner(ColorManager manager) {
//		IToken procInstr =
//			new Token(
//				new TextAttribute(
//					manager.getColor(ColorConstants.DEFAULT)));
		

		ArrayList<IRule> rules= new ArrayList<IRule>();
		
		Token tokencomment = new Token(new TextAttribute(manager.getColor(ColorConstants.COMMENT)));
		//Add rule for processing instructions
		rules.add( new SingleLineRule("//", "\n", tokencomment));
		rules.add( new SingleLineRule("#", "\n", tokencomment));
		rules.add( new MultiLineRule("/*", "*/", tokencomment));
		
		/////////////////////////////////////////////////////
		Token token= new Token(new TextAttribute(
				manager.getColor(ColorConstants.KEYWORD), 	//parola
				null, 	//sfondo
				SWT.BOLD));
		
		WordRule wordRule = new WordRule(new WordDetector());
		//wordRule.addWord("function", token);
		for (int i = 0; i < keywords.length; i++) {
			wordRule.addWord(keywords[i], token);
		}
		
		rules.add(wordRule);
		
		////////////////////////////////////////////////////
		//Add generic whitespace rule.
		//rules.add(new WhitespaceRule(new WhitespaceDetector()));
		////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////
//		token = new Token(new TextAttribute(manager.getColor(ColorConstants.NUMBER)));
//		RuleNumber numberRule = new RuleNumber(token);
//		rules.add(numberRule);
		
		////////////////////////////////////////////////////

		
		token = new Token(new TextAttribute(manager.getColor(ColorConstants.BRACET)));
		RuleBrace braceRule = new RuleBrace(token);
		rules.add(braceRule);
		
		////////////////////////////////////////////////////	
		IRule[] r= new IRule[rules.size()];
		setRules(rules.toArray(r));
	}
}
