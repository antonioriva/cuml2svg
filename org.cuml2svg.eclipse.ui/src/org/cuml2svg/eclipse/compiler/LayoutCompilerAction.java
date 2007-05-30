/*
 * (c) Copyright Improve S.A., 2002.
 * All Rights Reserved.
 */
package org.cuml2svg.eclipse.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.cuml2svg.eclipse.ui.Activator;
import org.cuml2svg.eclipse.ui.view.ConsoleView;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
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

public class LayoutCompilerAction extends TextEditorAction {


	/**
	 * Constructs and updates the action.
	 */
	private LayoutCompilerAction() {
		super(ResourceBundle.getBundle("org.cuml2svg.eclipse.ResourceMessage"),"CompilerAction.", null); //$NON-NLS-1$
		update();
	}

	  private static class LayoutCompilerActionHolder{ 
	    private final static LayoutCompilerAction instance = new LayoutCompilerAction();
	  }
	 
	  public static LayoutCompilerAction getInstance(){
	    return LayoutCompilerActionHolder.instance;
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
		Runtime r = Runtime.getRuntime();

		try {

			String[] command = buildCommand(fileToCompile);
			
			String filePath = fileToCompile.getLocation().toOSString();
			String fileFolderPath =	filePath.substring(0, filePath.length() - fileToCompile.getName().length());			

			// runs the command
			Process p = r.exec(command);

			// gets the input stream to have the post-compile-time information
			//InputStream stream = p.getInputStream();

			// and get the string from it
			String compilerOutput = getStringFromStream(p);

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
	protected String[] buildCommand(IFile fileToCompile) {
		//StringBuffer command = new StringBuffer(getCSharpCompilerLocation());
		
		File f= new File("./");
		System.out.println(f.getAbsolutePath());
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath location = root.getLocation();
		URL platformInstalledURL = Platform.getInstallLocation().getURL();
		
		ArrayList<String> cmd= new ArrayList<String>();
		
		cmd.add("java");
		cmd.add("-cp");
		 
		cmd.add(platformInstalledURL.getPath()+"plugins/cuml2svg/lib/commons-collections-3.2.jar:" +
				platformInstalledURL.getPath()+"plugins/cuml2svg/lib/commons-lang-2.3.jar:" +
				platformInstalledURL.getPath()+"plugins/cuml2svg/lib/jakarta-oro-2.0.8.jar:" +
				platformInstalledURL.getPath()+"plugins/cuml2svg/lib/velocity-1.5.jar:" +
				platformInstalledURL.getPath()+"plugins/cuml2svg/cUml2Svg.jar" );
		cmd.add("org.cuml2svg.compiler.Compiler");
		
//		cmd.add("-jar");
//		cmd.add("/opt/development/eclipse/plugins/cUml2Svg.jar");
		cmd.add("-i");
		cmd.add(location+fileToCompile.getFullPath().toOSString());
		cmd.add("-o");
		cmd.add("/home/antonio/FirstStep.svg");
		
		for (Iterator iter = cmd.iterator(); iter.hasNext();) {
			System.out.print(iter.next()+" ");
		}
		System.out.println("");
		
		return cmd.toArray(new String[cmd.size()]);
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
	protected String getStringFromStream(Process p) throws IOException {
		InputStream out=p.getInputStream();
		InputStream err= p.getErrorStream() ;
			
		
		StringBuffer buffer = new StringBuffer();
		byte[] b = new byte[100];
		int finishedOut = 0;
		
		while (finishedOut != -1 ) {
			finishedOut = out.read(b);
			if (finishedOut != -1) {
				String current = new String(b, 0, finishedOut);
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

		CompilerMessageParser lc_compilerMessageParser = new MessageParser();
		lc_compilerMessageParser.parseCompilerMessage(file, output);

	}


}