package org.cuml2svg.eclipse.ui.editors.model;


import org.cuml2svg.eclipse.compiler.ModelCompilerAction;
import org.cuml2svg.eclipse.ui.editors.ColorManager;
import org.cuml2svg.eclipse.ui.editors.ViewerConfigurator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.TextEditorAction;


	public class ModelEditor extends TextEditor {

		private ColorManager colorManager;

		public ModelEditor() {
			super();
			colorManager = new ColorManager();
			setSourceViewerConfiguration(new ModelConfiguration(colorManager));
			setDocumentProvider(new ModelDocumentProvider());
		}
		public void dispose() {
			colorManager.dispose();
			super.dispose();
		}
		
		public void doSave(IProgressMonitor monitor) {
	    	super.doSave(monitor);
	    
	    	System.out.println("Starting compilation");
	    	TextEditorAction a = ModelCompilerAction.getInstance();
	    	a.setEditor(this);
    		if (a != null){
    			a.run();
    		}
	    }
	}