package org.cuml2svg.eclipse.ui.editors.layout;


import org.cuml2svg.eclipse.ui.editors.ColorManager;
import org.eclipse.ui.editors.text.TextEditor;


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

	}