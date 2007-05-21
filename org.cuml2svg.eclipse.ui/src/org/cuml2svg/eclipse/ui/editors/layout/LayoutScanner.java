package org.cuml2svg.eclipse.ui.editors.layout;

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

public class LayoutScanner extends RuleBasedScanner {
	
	//TODO controllare tutte le keyword
	private static String[] keywordslayout= {"@layout"};
	private static String[] keywords= { "class","import"};
	
	public LayoutScanner(ColorManager manager) {
//		IToken procInstr =
//			new Token(
//				new TextAttribute(
//					manager.getColor(ColorConstants.DEFAULT)));
		
		ArrayList<IRule> rules= new ArrayList<IRule>();
		
		
		Token token= new Token(new TextAttribute(
				manager.getColor(ColorConstants.KEYWORD), 	//parola
				null, 										//sfondo
				SWT.BOLD));
		Token tokenlayout= new Token(new TextAttribute(
				manager.getColor(ColorConstants.LAYOUT), 	//parola
				null, 										//sfondo
				SWT.BOLD));
		
		WordRule wordRule = new WordRule(new WordDetector());
		//wordRule.addWord("function", token);
		
		for (int i = 0; i < keywords.length; i++) {
			wordRule.addWord(keywords[i], token);
		}
		for (int i = 0; i < keywordslayout.length; i++) {
			wordRule.addWord(keywordslayout[i], tokenlayout);
		}
		
		rules.add(wordRule);
		

		Token tokencomment = new Token(new TextAttribute(manager.getColor(ColorConstants.COMMENT)));
		//Add rule for processing instructions		
		rules.add( new SingleLineRule("//", "", tokencomment));
		rules.add( new SingleLineRule("#", "", tokencomment));
		rules.add( new MultiLineRule("/*", "*/", tokencomment));
		
		
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
		
//		gestione dei numeri
//		token = new Token(new TextAttribute(manager.getColor(ColorConstants.NUMBER)));
//		RuleNumber numberRule = new RuleNumber(token);
//		rules.add(numberRule);
		
		
		//gestione colore parentesi
//		token = new Token(new TextAttribute(manager.getColor(new RGB(255, 0,
//				0)),manager.getColor(new RGB(255, 255, 255)), SWT.BOLD));
		token = new Token(new TextAttribute(manager.getColor(ColorConstants.BRACET)));
		RuleBrace braceRule = new RuleBrace(token);
		rules.add(braceRule);
		
		
		
		IRule[] r= new IRule[rules.size()];
		setRules(rules.toArray(r));
	}
}
