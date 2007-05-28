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

	private ArrayList<String> cardinalities = new ArrayList<String>();

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
				
				//Handling cardinality text
				//Start point
				if(points.size() > 0) {
					ArrayList<Point> places = new ArrayList<Point>();
					ArrayList<String> alignment = new ArrayList<String>();
					Point cardPoint = (Point) points.get(0).clone();
					if(points.size() > 1) {
						Point cardPoint2 = (Point) points.get(1).clone();
						if(cardPoint2.x > cardPoint.x) {
							//Right line
							alignment.add("start");
						}else {
							//Left line
							alignment.add("end");
						}
						if(cardPoint2.y > cardPoint.y) {
							//Vertical top-down
							cardPoint.translate(0, 30);
						}else {
							//Vertical bottom-up
							cardPoint.translate(0, -10);
						}
					}
					cardPoint.translate(10, 0);
					places.add(cardPoint);

					//Mid point
					cardPoint = (Point) points.get(points.size()/2).clone();
					if(points.size() > 1) {
						Point cardPoint2 = (Point) points.get(points.size()/2 -1).clone();
						alignment.add("middle");
						int xtran = (cardPoint.x - cardPoint2.x) / 2;
						int ytran = (cardPoint.y - cardPoint2.y) / 2;
						cardPoint.translate(-xtran, -ytran);
						context.put("cardinalityRotate",(ytran != 0));
					}
					cardPoint.translate(-10, 0);
					places.add(cardPoint);

					//End point
					cardPoint = (Point) points.get(points.size() -1).clone();
					if(points.size() > 1) {
						Point cardPoint2 = (Point) points.get(points.size()-2).clone();
						if(cardPoint2.x > cardPoint.x) {
							//Right line
							alignment.add("start");
						}else {
							//Left line
							alignment.add("end");
						}
						if(cardPoint2.y > cardPoint.y) {
							//Vertical top-down
							cardPoint.translate(0, 30);
						}else {
							//Vertical bottom-up
							cardPoint.translate(0, -10);
						}
					}
					cardPoint.translate(10, 0);
					places.add(cardPoint);

					context.put("cardinalityCoord",places);
					context.put("cardinalityAlign",alignment);
				}
				if(cardinalities.size() > 0) {
					context.put("cardinality", this.cardinalities);
				}
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
	}

	public String getEndClass() {
		return endClass;
	}

	public String getStartClass() {
		return startClass;
	}

	public void setCardinality(String[] cards) {
		if(cards.length == 3) {
			this.cardinalities.add(cards[0]);
			this.cardinalities.add(cards[1]);
			this.cardinalities.add(cards[2]);
		}
	}
}
