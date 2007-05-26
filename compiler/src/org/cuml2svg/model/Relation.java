package org.cuml2svg.model;

import java.awt.Point;
import java.io.Writer;
import java.util.ArrayList;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.cuml2svg.model.Diagram.OutputType;
import org.cuml2svg.svg.GraphicsManager;
import org.cuml2svg.tools.PathGenerator;

/**
 * Object for class relation management
 * 
 * @author Antonio Riva
 * @author Fabio Marini
 * @author Luca Cividini
 * 
 */
public class Relation implements Renderable {
	private static final String RELATION_TEMPLATE = "templates/SVGRelation.vm";

	private int startId;

	private int endId;

	private RelationType relationType;

	private String startClass;

	private String endClass;

	private ArrayList<Point> points = new ArrayList<Point>();

	//TODO aggiungere i campi della cardinalità
	
	
	/**
	 * Creates a new Relation object
	 * 
	 * @param startId
	 *            the ID of the first class
	 * @param endId
	 *            the ID of the second class
	 * @param relationType
	 *            the type of the relation
	 */
	public Relation(int startId, int endId, RelationType relationType) {
		this.startId = startId;
		this.endId = endId;
		this.relationType = relationType;
	}

	/**
	 * Creates a new Relation object
	 * 
	 * @param startClass
	 *            the name of the first class
	 * @param endClass
	 *            the name of the second class
	 * @param relationType
	 *            the type of the relation
	 */
	public Relation(String startClass, String endClass,
			RelationType relationType) {
		this.startClass = startClass;
		this.endClass = endClass;
		this.relationType = relationType;
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
			PathGenerator pathGenerator = PathGenerator.getInstance();
			// System.out.println("STID: "+getStartId() + " ENID: "+getEndId());
			ArrayList<Point> points = pathGenerator.getPath(getStartId(),
					getEndId());
			if (points != null) {
				this.addPoints(points);
				Template template;
				ArrayList<Point> pointsCopy = (ArrayList<Point>) points.clone();
				Point point = pointsCopy.remove(0);
				String path = "M " + point.getX() + "," + point.getY();
				context.put("points", pointsCopy);
				context.put("path", path);
				template = Velocity.getTemplate(RELATION_TEMPLATE);
				template.merge(context, writer);
			}
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * @return the endId
	 */
	public int getEndId() {
		if (endId == 0) {
			Class aClass = GraphicsManager.getInstance().find(endClass);
			if (aClass != null) {
				this.endId = aClass.getId();
			}
		}
		return endId;
	}

	/**
	 * @param endId
	 *            the endId to set
	 */
	public void setEndId(int endId) {
		this.endId = endId;
	}

	/**
	 * @return the relationType
	 */
	public RelationType getRelationType() {
		return relationType;
	}

	/**
	 * @param relationType
	 *            the relationType to set
	 */
	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	/**
	 * @return the startId
	 */
	public int getStartId() {
		if (startId == 0) {
			Class aClass = GraphicsManager.getInstance().find(startClass);
			if (aClass != null) {
				this.startId = aClass.getId();
			}
		}
		return startId;
	}

	/**
	 * @param startId
	 *            the startId to set
	 */
	public void setStartId(int startId) {
		this.startId = startId;
	}

	/**
	 * @param point
	 *            the point to add
	 */
	public void addPoint(Point point) {
		this.points.add(point);
	}

	/**
	 * @param pos
	 *            the position of the point
	 * @return the point in the given position
	 */
	public Point getPoint(int pos) {
		return this.points.get(pos);
	}

	/**
	 * @param points
	 *            an array of points to add
	 */
	public void addPoints(ArrayList<Point> points) {
		this.points.addAll(points);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cuml2svg.model.Renderable#place()
	 */
	public void place() {
		// TODO Auto-generated method stub

	}

	public String getEndClass() {
		return endClass;
	}

	public String getStartClass() {
		return startClass;
	}

	public void setCardinality(String[] cards) {
		// TODO Auto-generated method stub
	}
}
