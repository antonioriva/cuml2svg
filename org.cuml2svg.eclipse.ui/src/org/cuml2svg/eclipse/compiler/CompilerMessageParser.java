/*
 * Created on 16 juin 2004
 *
 */
package org.cuml2svg.eclipse.compiler;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * @author apelletier
 *
 */
public interface CompilerMessageParser {
    public abstract void parseCompilerMessage(IFile in_file,
            String in_compilerOutPut) throws CoreException;
}