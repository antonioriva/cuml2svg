package org.cuml2svg.compiler;

import java.io.File;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class Compiler {

	private static boolean pathGui;
	private static String templatePath="";
	/**
	 * Print a help text
	 *
	 */
	public static void printHelp(){
		String h="";
		h+="cUml2Svg - generation of svg diagram\n\tfrom a coded rappresentation";
		h+="\n";
		h+="\n  Usage: u2sc [option]";

		h+="\n ";
		h+="\n --help\t\tprint this message";
		h+="\n ";
		h+="\n --disable-warning, -dw\tdisable warning messages";
		h+="\n --disable-error, -de\tdisable error messages";
		h+="\n --disable-notice, -dn\tdisable notice mesages";
		h+="\n ";
		h+="\n --check, -c\tonly check syntax";
		h+="\n ";
		h+="\n --input, -i\t\tinput layout file path";
		h+="\n --output, -o\t\toutput svg file path";
		h+="\n -t path\tpath to the template folder";
		

	}
	/**
	 * @param args explained in the help function
	 */
	public static void main(String[] args) {
		System.setErr(System.out);
		//printHelp();
		String input="";
		String output="";
        boolean warningFlag= true;
        boolean noticeFlag= true;
        boolean errorFlag= true;
    	boolean check=false,checkLayout=false,checkModel=false;
		String arg;
        
		for (int i = 0; i < args.length; i++) {
			arg=args[i];
		
			if(arg.compareTo("--help")==0){
				printHelp();
				System.exit(0);
			}
			if((arg.compareTo("--disable-error")==0)||(arg.compareTo("-de")==0)){
				errorFlag=false;
			}
			if((arg.compareTo("--disable-warning")==0)||(arg.compareTo("-dw")==0)){
				warningFlag=false;
			}
			if((arg.compareTo("--disable-notice")==0)||(arg.compareTo("-dn")==0)){
				noticeFlag=false;
			}
			if((arg.compareTo("--check")==0)||(arg.compareTo("-c")==0)){
				check=true;
			}

			if(arg.indexOf("--input")==0){
				try{
				input=arg.split("=")[1];
				File f = new File(input.trim());
				if(!f.isFile()){
					System.err.println("FATAL_ERROR: input file doesn't exist; the schema is --input=complete_file_path");
					System.exit(1);
				}
				}catch(Exception e){
					System.err.println("FATAL_ERROR: missing input file; the schema is --input=complete_file_path");
					System.exit(1);
				}
			}
			if(arg.compareTo("-i")==0){
				i++;
				if(i>=args.length){
					System.err.println("FATAL_ERROR: missing input file");
					System.exit(1);
				}
				input=args[i];
				File f = new File(input);
				if(!f.isFile()){
					System.err.println("FATAL_ERROR: input file doesn't exist");
					System.exit(1);
				}
			}
			
			if(arg.compareTo("-t")==0){
				i++;
				if(i>=args.length){
					System.err.println("FATAL_ERROR: missing template path");
					System.exit(1);
				}
				templatePath=args[i];
			}
			if(arg.indexOf("--output")==0){
				try{
					output=arg.split("=")[1];
				}catch(Exception e){
					System.err.println("FATAL_ERROR: missing output file; the schema is --input=complete_file_path");
				}
			}
			if(arg.compareTo("-o")==0){
				i++;
				if(i>=args.length){
					System.err.println("FATAL_ERROR: missing output file");
					System.exit(1);
				}
				output=args[i];
			}
		}
		if(!check){
			if(output.length()==0){
				System.err.println("FATAL_ERROR: missing output file");
				System.exit(1);
			}
		}else{
			if(input.endsWith("u2sl")){
				checkModel=false;
				checkLayout=true;
			}else{
				checkLayout=false;
				checkModel=true;
			}
		}
		
		Calendar cal = new GregorianCalendar();
//		System.out.println(cal.get(Calendar.HOUR_OF_DAY)+":"
//				+cal.get(Calendar.MINUTE)+":"
//				+cal.get(Calendar.SECOND)+"."
//				+cal.get(Calendar.MILLISECOND));
		System.out.println("--------------------------------------------------\n");
		
		System.out.println("Launching compiler");
		System.out.println("\tInput: \""+(new File(input)).getAbsolutePath()+"\"");
		System.out.println("\tOutput: \""+output+"\"");
		System.out.println("");
		
		System.out.print("\tPrint Notice: ");
		if(noticeFlag==true){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
		
		System.out.print("\tPrint Error: ");
		if(errorFlag==true){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
		
		System.out.print("\tPrint Warning: ");
		if(warningFlag==true){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
		System.out.println("\n--------------------------------------------------\n");
		
		new cuml2svg(input,output,templatePath,warningFlag, noticeFlag, errorFlag,checkModel,checkLayout);
	}
}
