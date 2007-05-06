package org.cuml2svg.eclipse.ui.editors.model;

import org.cuml2svg.eclipse.ui.editors.ColorConstants;
import org.cuml2svg.eclipse.ui.editors.ColorManager;
import org.cuml2svg.eclipse.ui.editors.NonRuleBasedDamagerRepairer;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class ModelConfiguration extends SourceViewerConfiguration {
	//private XMLDoubleClickStrategy doubleClickStrategy;
	private ModelScanner scanner;
	private ColorManager colorManager;

	public ModelConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			ModelPartitionScanner.MODEL_COMMENT,
			ModelPartitionScanner.MODEL_STRING};
	}
//	public ITextDoubleClickStrategy getDoubleClickStrategy(
//		ISourceViewer sourceViewer,
//		String contentType) {
//		if (doubleClickStrategy == null)
//			doubleClickStrategy = new XMLDoubleClickStrategy();
//		return doubleClickStrategy;
//	}

	protected ModelScanner getModelScanner() {
		if (scanner == null) {
			scanner = new ModelScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ColorConstants.DEFAULT))));
		}
		return scanner;
	}


	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr  = new DefaultDamagerRepairer(getModelScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(ColorConstants.COMMENT)));
		reconciler.setDamager(ndr, ModelPartitionScanner.MODEL_COMMENT);
		reconciler.setRepairer(ndr, ModelPartitionScanner.MODEL_COMMENT);
		
		ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(ColorConstants.STRING)));
		reconciler.setDamager(ndr, ModelPartitionScanner.MODEL_STRING);
		reconciler.setRepairer(ndr, ModelPartitionScanner.MODEL_STRING);	

		return reconciler;
	}

}