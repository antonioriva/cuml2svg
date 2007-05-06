package org.cuml2svg.eclipse.ui.editors.model;


import org.cuml2svg.eclipse.ui.editors.ColorManager;
import org.eclipse.ui.editors.text.TextEditor;


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

	}