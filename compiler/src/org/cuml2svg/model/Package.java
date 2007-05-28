package org.cuml2svg.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.cuml2svg.model.Diagram.OutputType;
import org.cuml2svg.svg.GraphicsManager;

/**
 * The package object
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Package extends Groupable implements Object, Comparable<Package> {

	private static final String SVG_PACKAGE_HEADER_TEMPLATE = "templates/SVGPackage_header.vm";

	private static final String SVG_PACKAGE_FOOTER_TEMPLATE = "templates/SVGPackage_footer.vm";

	/**
	 * The name of the package
	 */
	private String packageName;

	private ArrayList<Class> classes;

	private boolean hideArgs;

	/**
	 * Initialize a new Package object
	 * 
	 * @param packageName
	 *            The name of the package
	 */
	public Package(String packageName) {
		this.packageName = packageName;
		this.hideArgs = false;

		this.classes = new ArrayList<Class>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Renderable#render(org.cuml2svg.model.Diagram.OutputType,
	 *      org.apache.velocity.VelocityContext, java.io.Writer)
	 */
	public boolean render(OutputType type, VelocityContext context,
			Writer writer) {
		try {
			Template template = Velocity
					.getTemplate(SVG_PACKAGE_HEADER_TEMPLATE);

			context.put("xtran", this.getXtran());
			context.put("ytran", this.getYtran());

			template.merge(context, writer);

			for (Iterator i = this.classes.iterator(); i.hasNext();) {
				Class object = (Class) i.next();
				object.render(type, context, writer);
			}

			template = Velocity.getTemplate(SVG_PACKAGE_FOOTER_TEMPLATE);
			template.merge(context, writer);

			return true;
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Package compared) {
		return this.packageName.compareTo(compared.packageName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Renderable#place()
	 */
	public void place() {
	}

	public void updateReference() {
		GraphicsManager.getInstance().addObject(this);

	}

	public void setHideArgs(boolean hide) {
		this.hideArgs = hide;
	}
}
