/*
 * (c) Copyright Improve S.A., 2002.
 * All Rights Reserved.
 */
package org.cuml2svg.eclipse.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.cuml2svg.eclipse.ui.Activator;
import org.cuml2svg.eclipse.ui.view.ConsoleView;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.framework.internal.core.ConsoleMsg;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;




/**
 * Class that defines the action of compiling the current C Sharp file
 */

public class cUml2SvgCompilerAction extends TextEditorAction {


	/**
	 * Constructs and updates the action.
	 */
	private cUml2SvgCompilerAction() {
		super(ResourceBundle.getBundle("org.cuml2svg.eclipse.ResourceMessage"),"CompilerAction.", null); //$NON-NLS-1$
		update();
	}

	  private static class cUml2SvgCompilerActionHolder{ 
	    private final static cUml2SvgCompilerAction instance = new cUml2SvgCompilerAction();
	  }
	 
	  public static cUml2SvgCompilerAction getInstance(){
	    return cUml2SvgCompilerActionHolder.instance;
	  }

	/**
	 * Code called when the action is fired.
	 */
	public void run() {

		IFile fileToCompile = findCurrentLayoutFile();
		if (fileToCompile == null) {
			// should never happen
			System.err.println("Error : no file in the editor");
			// should throw an exception
			return;
		}
		System.out.println("compiling: "+fileToCompile.getName());
		Runtime r = Runtime.getRuntime();

		try {

			String command = buildCommand(fileToCompile);
			
			String filePath = fileToCompile.getLocation().toOSString();
			String fileFolderPath =	filePath.substring(0, filePath.length() - fileToCompile.getName().length());			

			// runs the command
			Process p = r.exec(command.toString(), new String[] {}, new File(fileFolderPath));

			// gets the input stream to have the post-compile-time information
			InputStream stream = p.getInputStream();

			// and get the string from it
			String compilerOutput = getStringFromStream(stream);

			// prints out the information
			printResultInConsole(compilerOutput);

			// parse the buffer to find the errors and create markers
			createMarkers(compilerOutput, fileToCompile);

			// And refresh the compilation unit folder
			fileToCompile.getParent().refreshLocal(IResource.DEPTH_ONE, null);

		} catch (IOException e) {
			// $$$ should throw the exception again
			System.err.println("Problem");
			e.printStackTrace();
		} catch (CoreException e) {
			// $$$ do something here !
		}
	}


	/**
	 * Finds the file that's currently opened in the C# Text Editor
	 */
	protected IFile findCurrentLayoutFile() {
		ITextEditor editor = getTextEditor();

		IEditorInput editorInput = null;
		if (editor != null) {
			editorInput = editor.getEditorInput();
		}

		if (editorInput instanceof IFileEditorInput)
			return ((IFileEditorInput) editorInput).getFile();

		// if nothing was found, which should never happen
		return null;
	}

	/**
	 * Builds the command for compiling the current file. <br/>
	 * It uses the persistent properties of the file to know the
	 * compile-time arguments.
	 */
	protected String buildCommand(IFile fileToCompile) {
		//StringBuffer command = new StringBuffer(getCSharpCompilerLocation());
		
		String command="java -cp /home/antonio/Progetti/cUml2Svg/cuml2svg/bin Compiler -i "+fileToCompile+" -o /home/antonio/Progetti/cUml2Svg/FirstStep.svg";
		System.out.println(command);
		return command.toString();
	}
	

	/**
	 * Prints out the string represented by the string buffer
	 */
	protected void printResultInConsole(String output) {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			ConsoleView console = (ConsoleView)page.findView(ConsoleView.CONSOLE_ID);
			
			if (console!=null) {
				console.setOutputText(output);
			} else  {
				page.showView(ConsoleView.CONSOLE_ID);
				console = (ConsoleView)page.findView(ConsoleView.CONSOLE_ID);			
				console.setOutputText(output);
			}
		} catch (PartInitException e) {
			e.printStackTrace();}
		
	}

	/**
	 * Creates a string buffer from the given input stream
	 */
	protected String getStringFromStream(InputStream stream) throws IOException {
		StringBuffer buffer = new StringBuffer();
		byte[] b = new byte[100];
		int finished = 0;
		while (finished != -1) {
			finished = stream.read(b);
			if (finished != -1) {
				String current = new String(b, 0, finished);
				buffer.append(current);
			}
		}
		return buffer.toString();
	}

	/**
	 * Create markers according to the compiler output
	 */
	protected void createMarkers(String output, IFile file) throws CoreException {
		// first delete all the previous markers
		file.deleteMarkers(IMarker.PROBLEM, false, 0);

		//CompilerMessageParser lc_compilerMessageParser = getCompilerMessageParser();
		//lc_compilerMessageParser.parseCompilerMessage(file, output);

	}


}