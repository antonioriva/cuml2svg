package org.cuml2svg.test;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String msg="ERROR in file: \"/home/antonio/Progetti/runtime-New_configuration/Test/visitor.u2sl\" at line 1, at column 7; file not found \"[		 @layout *x* 		 		[class visitor.Visitor] 		[class visitor.ConcreteVisitor1] 		[class visitor.ConcreteVisitor1] 		[class visitor.ConcreteVisitor2]	 		 ]\"";
		
		
		
		msg="ERROR in file: \"/home/antonio/Progetti/runtime-New_configuration/Test/visitor.u2sl\"; parser error: Encountered \"<EOF>\"  column 1. Was expecting:     \";\" ...";     
		
		String fileName="";
		try{fileName=msg.substring(msg.indexOf("in file:")+("in file:").length());
		fileName=fileName.substring(0,fileName.indexOf(";"));
		fileName=fileName.trim();
		fileName=fileName.substring(1,fileName.length()-1);
		}catch (Exception e1) {
			fileName="";
		}
		System.out.println(fileName);
		
		String line="";
		int l=1;
		try{
			line=msg.substring(msg.indexOf("at line")+("at line").length());
			line=line.substring(0,line.indexOf(","));
			l = Integer.parseInt(line.trim());
		}catch (Exception e1) {
			l=1;
		}
		System.out.println(l);
		
	}

}
