/*
 * Created on 16 juin 2004
 *
 */
package org.cuml2svg.eclipse.compiler;

import java.io.File;

import org.cuml2svg.eclipse.ui.editors.model.ModelEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorInputTransfer;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author apelletier
 *
 */
public class MessageParser extends AbstractCompilerMessageParser implements CompilerMessageParser {

    /**
     * @param in_file
     * @param lc_fileName
     * @param current
     * @throws CoreException
     */
    protected void parseLine(IFile in_file, String in_ligne) throws CoreException {
    	if(in_ligne.startsWith("ERROR")||in_ligne.startsWith("NOTICE")||in_ligne.startsWith("WARNING")){
        String msg = in_ligne; 
        
        String fileName="";
		try{
			fileName=msg.substring(msg.indexOf("in file:")+("in file:").length());
			fileName=fileName.substring(0,fileName.indexOf(";"));
			fileName=fileName.trim();
			fileName=fileName.substring(1,fileName.length()-1);
						
		}catch (Exception e1) {
		}
		
		
		int l=1;
		try{
			String line="";
			line=msg.substring(msg.indexOf("at line")+("at line").length());
			line=line.substring(0,line.indexOf(","));
			l = Integer.parseInt(line.trim());
		}catch (Exception e1) {
			l=1;
		}
		
		
        setMarker(in_file, msg, l);
        }
    }
    
}
