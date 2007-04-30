package org.cuml2svg.eclipse.ui.editors;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

import com.sun.xml.internal.bind.v2.TODO;

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
