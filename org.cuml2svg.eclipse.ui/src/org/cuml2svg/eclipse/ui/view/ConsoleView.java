/*
 * (c) Copyright Improve S.A., 2002.
 * All Rights Reserved.
 */
package org.cuml2svg.eclipse.ui.view;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * The CSharpConsole is used to display the C# compiler's output
 * @see ViewPart
 */
public class ConsoleView extends ViewPart {

	
	private TextViewer viewer = null;
	private Document document = null;
	public static final String CONSOLE_ID = "org.cuml2svg.eclipse.ui.view.ConsoleView";

	/**
	 * The constructor.
	 */
	public ConsoleView() {
	}

	/**
	 * Insert the method's description here.
	 * @see ViewPart#createPartControl
	 */
	public void createPartControl(Composite parent)  {
		viewer = new TextViewer(parent, SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData viewerData = new GridData(GridData.FILL_BOTH);
		viewer.getControl().setLayoutData(viewerData);
		viewer.setEditable(false);
	}


	/**
	 * Set the text for the viewer
	 */
	public void setOutputText(String text) {
		document = new Document(text);
		viewer.setDocument(document);
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"cUml2Svg Console View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
