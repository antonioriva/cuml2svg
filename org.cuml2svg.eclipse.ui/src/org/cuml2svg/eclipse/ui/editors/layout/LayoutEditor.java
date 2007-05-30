package org.cuml2svg.eclipse.ui.editors.layout;


import org.cuml2svg.eclipse.compiler.LayoutCompilerAction;
import org.cuml2svg.eclipse.ui.editors.ColorManager;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;


	public class LayoutEditor extends TextEditor {

		private ColorManager colorManager;

		public LayoutEditor() {
			super();
			colorManager = new ColorManager();
			setSourceViewerConfiguration(new LayoutConfiguration(colorManager));
			setDocumentProvider(new LayoutDocumentProvider());
		}
		public void dispose() {
			colorManager.dispose();
			super.dispose();
		}

	    public void doSave(IProgressMonitor monitor) {
	    	super.doSave(monitor);
	    
	    	System.out.println("Starting compilation");
	    	TextEditorAction a = LayoutCompilerAction.getInstance();
	    	a.setEditor(this);
    		if (a != null){
    			a.run();
    		}
	    }
		
	}