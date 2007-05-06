package org.cuml2svg.eclipse.ui.editors.model;

import java.util.ArrayList;

import org.cuml2svg.eclipse.ui.editors.ColorConstants;
import org.cuml2svg.eclipse.ui.editors.ColorManager;
import org.cuml2svg.eclipse.ui.editors.RuleBrace;
import org.cuml2svg.eclipse.ui.editors.RuleNumber;
import org.cuml2svg.eclipse.ui.editors.WordDetector;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public class ModelScanner extends RuleBasedScanner {
	
	//TODO controllare tutte le keyword
	private static String[] keywords= { "class","package","method","public","private","relations","interface","extend" };
	
	public ModelScanner(ColorManager manager) {
//		IToken procInstr =
//			new Token(
//				new TextAttribute(
//					manager.getColor(ColorConstants.DEFAULT)));
		
		ArrayList<IRule> rules= new ArrayList<IRule>();
		
		
		/////////////////////////////////////////////////////
		Token token= new Token(new TextAttribute(
				manager.getColor(new RGB(255, 255,255)),
				manager.getColor(new RGB(0,0,0)), 
				SWT.BOLD));
		WordRule wordRule = new WordRule(new WordDetector());
		//wordRule.addWord("function", token);
		for (int i = 0; i < keywords.length; i++) {
			wordRule.addWord(keywords[i], token);
		}
		rules.add(wordRule);
		
		////////////////////////////////////////////////////
		token = new Token(new TextAttribute(manager.getColor(ColorConstants.COMMENT)));
		//Add rule for processing instructions
		rules.add( new SingleLineRule("/*", "*/", token));
		
		////////////////////////////////////////////////////
		//Add generic whitespace rule.
		//rules.add(new WhitespaceRule(new WhitespaceDetector()));
		////////////////////////////////////////////////////
		
		////////////////////////////////////////////////////
		token = new Token(new TextAttribute(manager.getColor(ColorConstants.NUMBER)));
		RuleNumber numberRule = new RuleNumber(token);
		rules.add(numberRule);
		
		////////////////////////////////////////////////////
		token = new Token(new TextAttribute(manager.getColor(new RGB(0, 0,
				0)),manager.getColor(new RGB(255, 0,0)), SWT.BOLD));
		RuleBrace braceRule = new RuleBrace(token);
		rules.add(braceRule);
		
		////////////////////////////////////////////////////	
		IRule[] r= new IRule[rules.size()];
		setRules(rules.toArray(r));
	}
}
