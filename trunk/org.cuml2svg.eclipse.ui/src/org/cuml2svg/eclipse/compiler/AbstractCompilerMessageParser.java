/*
 * Created on 16 juin 2004
 *
 */
package org.cuml2svg.eclipse.compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.texteditor.MarkerUtilities;

/**
 * @author apelletier
 *  
 */
public abstract class AbstractCompilerMessageParser implements CompilerMessageParser {

    protected static final String ERROR = "ERROR"; //$NON-NLS-1$
    protected static final String WARNING = "WARNING"; //$NON-NLS-1$
    protected static final String NOTICE = "NOTICE"; //$NON-NLS-1$

    public void parseCompilerMessage(IFile in_file, String in_compilerOutPut)
            throws CoreException {
        String lc_fileName = in_file.getName();
        StringTokenizer tokenizer = new StringTokenizer(in_compilerOutPut, "\n"); //$NON-NLS-1$
        while (tokenizer.hasMoreElements()) {
            String current = (String) tokenizer.nextElement();
            parseLine(in_file, current);
        }
    }

    /**
     * @param in_file
     * @param lc_fileName
     * @param current
     * @throws CoreException
     */
    protected abstract void parseLine(IFile in_file, String in_ligne) throws CoreException;

    /**
     * @param in_file
     * @param message
     * @param lineNumber
     * @throws CoreException
     */
    protected void setMarker(IFile in_file, String message, int lineNumber) throws CoreException {
        Map attributes = new HashMap();
    
        if (message.startsWith(ERROR)){
        	attributes.put(IMarker.SEVERITY, new Integer(
                    IMarker.SEVERITY_ERROR));
        }
        else if (message.startsWith(WARNING)){
            attributes.put(IMarker.SEVERITY, new Integer(
                    IMarker.SEVERITY_WARNING));}
        else
            attributes.put(IMarker.SEVERITY, new Integer(
                    IMarker.SEVERITY_INFO));
    
        MarkerUtilities.setLineNumber(attributes, lineNumber);
        MarkerUtilities.setMessage(attributes, "Message"+message);
        MarkerUtilities.createMarker(in_file, attributes,
                IMarker.PROBLEM);
    }
}