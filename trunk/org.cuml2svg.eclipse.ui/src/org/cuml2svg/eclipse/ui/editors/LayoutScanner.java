package org.cuml2svg.eclipse.ui.editors;

import java.util.ArrayList;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

public class LayoutScanner extends RuleBasedScanner {
	
	//TODO controllare tutte le keyword
	private static String[] keywordsimport= {"import"};
	private static String[] keywords= { "class"};
	
	public LayoutScanner(ColorManager manager) {
//		IToken procInstr =
//			new Token(
//				new TextAttribute(
//					manager.getColor(ColorConstants.DEFAULT)));
		
		ArrayList<IRule> rules= new ArrayList<IRule>();
		
		
		Token token= new Token(new TextAttribute(
				manager.getColor(new RGB(0, 0 ,255)), 		//parola
				manager.getColor(new RGB(255,255,255)), 	//sfondo
				SWT.BOLD));
		Token tokenimport= new Token(new TextAttribute(
				manager.getColor(new RGB(225, 81,57)), 		//parola
				manager.getColor(new RGB(255,255,255)), 	//sfondo
				SWT.BOLD));
		
		WordRule wordRule = new WordRule(new WordDetector());
		//wordRule.addWord("function", token);
		for (int i = 0; i < keywords.length; i++) {
			wordRule.addWord(keywords[i], token);
		}

		for (int i = 0; i < keywordsimport.length; i++) {
			wordRule.addWord(keywordsimport[i], tokenimport);
		}
		rules.add(wordRule);
		

		token = new Token(new TextAttribute(manager.getColor(ColorConstants.COMMENT)));
		//Add rule for processing instructions
		rules.add( new SingleLineRule("/*", "*/", token));
		rules.add( new SingleLineRule("@", "", token));
		
		// Add generic whitespace rule.
		
//		Token doc = new Token(new TextAttribute(manager.getColor(ColorConstants.NUMBER)));
//		//Add rule for processing instructions
//		rules.add( new SingleLineRule(" ", ".u2sm;", doc));
		

		//rules.add(new WhitespaceRule(new WhitespaceDetector()));

	
		
//		Token token = new Token(new TextAttribute(manager.getColor(new RGB(255, 255,
//				255)),manager.getColor(new RGB(0, 0,0)), SWT.BOLD));
//		WordRule wordRule = new WordRule(new WordDetector(), token);
//		wordRule.addWord("class".toCharArray(), token);
//		rules.add(wordRule);
//		
		token = new Token(new TextAttribute(manager.getColor(ColorConstants.NUMBER)));
		RuleNumber numberRule = new RuleNumber(token);
		rules.add(numberRule);
		
		//gestione colore parentesi
//		token = new Token(new TextAttribute(manager.getColor(new RGB(255, 0,
//				0)),manager.getColor(new RGB(255, 255, 255)), SWT.BOLD));
		token = new Token(new TextAttribute(manager.getColor(ColorConstants.BRACHET)));
		RuleBrace braceRule = new RuleBrace(token);
		rules.add(braceRule);
		
		
		
		IRule[] r= new IRule[rules.size()];
		setRules(rules.toArray(r));
	}
}
