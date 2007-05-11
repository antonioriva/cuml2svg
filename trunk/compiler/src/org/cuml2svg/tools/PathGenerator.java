package org.cuml2svg.tools;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class PathGenerator extends Thread {
	MyCanvas canvas=null;
	ArrayList<Rectangle> rectangleArray=null;
	int pathStart=0;
	int pathStop=0;
	
	//enum Direction {TOP, RIGHT, BOTTOM, LEFT;
	public static final int TOP=0;
	public static final int RIGHT=1;
	public static final int BOTTOM=2;
	public static final int LEFT=3;
	
	public static final int TOP_RIGHT=4;
	public static final int RIGHT_BOTTOM=5;
	public static final int BOTTOM_LEFT=6;
	public static final int LEFT_TOP=7;
	
	private int defaultStepLenght=5;
	private int defaultBorder=20;
	int externalBoxMaxY;
	int externalBoxMaxX;
	int externalBoxMinY=-100;
	int externalBoxMinX=-100;
	
	ArrayList<Point> currentPath = new ArrayList<Point>();
	
	static int currentPathCost=0;
	
	protected boolean isOutOfExternalBox(Point p) {
		if(p.x<externalBoxMinX || p.y<externalBoxMinY || p.x> externalBoxMaxX || p.y >externalBoxMaxY) return true;
		return false;
	}
	
	public PathGenerator(MyCanvas canvas, ArrayList<Rectangle> rectangleArray, int pathStart, int pathStop) {
		this.canvas=canvas;
		this.rectangleArray= rectangleArray;
		this.pathStart= pathStart;
		this.pathStop= pathStop;
		
		
		for (Rectangle r : rectangleArray) {
			this.externalBoxMaxX=Math.max(externalBoxMaxX, r.x);
			this.externalBoxMaxY=Math.max(externalBoxMaxY, r.y);
		}
		
		
	}
	
	protected Point makeStep(Point p, int direction) throws Exception{
		return makeStep( p,  direction, this.defaultStepLenght);
	}
	
	protected Point makeStep(Point p, int direction,int stepLenght) throws Exception{
		int x=p.x;
		int y=p.y;
		if(direction==TOP){ y-=stepLenght; }
		else if(direction==LEFT){ x-=stepLenght; }
		else if(direction==RIGHT){ x+=stepLenght; }
		else if(direction==BOTTOM){y+=stepLenght;}
		
		

		else if(direction==TOP_RIGHT){y-=stepLenght;x+=stepLenght;}
		else if(direction==RIGHT_BOTTOM){y+=stepLenght;x+=stepLenght;}
		else if(direction==BOTTOM_LEFT){y+=stepLenght;x-=stepLenght;}
		else if(direction==LEFT_TOP){y-=stepLenght;x-=stepLenght;}
		
		else{ throw new Exception("not valid direction: "+direction);}
		Point p1= new Point(x,y);
		this.currentPath.add(p1);
		this.canvas.repaint();
		return p1;	
	}
	
	protected void removeStep(){
		if(currentPath.size()>0)
		this.currentPath.remove(currentPath.size()-1);
		this.canvas.repaint();
	}
	private boolean inRectangle(Point p ,Rectangle r){
		return  inRectangle( p , r, this.defaultBorder);
	}
	
	private boolean inRectangle(Point p ,Rectangle r,int border){
		if((r.x-border<=p.x && (r.x+r.width+border)>=p.x)
				&&
			(r.y-border<=p.y && (r.y+r.height+border)>=p.y)){
			return true;
		}
		return false;
	}
	
	protected boolean isArrived(Point p){
		return this.inRectangle(p, this.rectangleArray.get(this.pathStop),0);
	}
	
	protected Point[] getStartPoint(){
		Point[] sPoint = new Point[4];
		Rectangle r= rectangleArray.get(pathStart);
		sPoint[this.BOTTOM]=new Point(r.x+(r.width/2),r.y+r.height);
		sPoint[this.TOP]=new Point(r.x+(r.width/2),r.y);
		sPoint[this.LEFT]=new Point(r.x,r.y+(r.height/2));
		sPoint[this.RIGHT]=new Point(r.x+r.width,r.y+(r.height/2));
		return sPoint;
	}
	

	
	protected static Point getCenterOfRectangle(Rectangle r){
		return new Point(r.x+(r.width/2),r.y+(r.height/2));
	}
	
	protected int getAbsoluteDirectionToStopPoint(Point p){
		return getAbsoluteDirection(p,getCenterOfRectangle(this.rectangleArray.get(this.pathStop)));
	}
	protected int getAbsoluteDirectionToStopPoint4(Point p){
		return getAbsoluteDirection4(p,getCenterOfRectangle(this.rectangleArray.get(this.pathStop)));
	}
	
	protected int[] getAbsoluteDirectionListToStopPoint8(Point p){
		int[] list = new int[8];
		list[0]=getAbsoluteDirection(p,getCenterOfRectangle(this.rectangleArray.get(this.pathStop)));
		list[1]=(list[0]+1)%8;
		list[2]=(list[0]+7)%8;
		list[3]=(list[1]+1)%8;
		list[4]=(list[2]+7)%8;
		list[5]=(list[3]+1)%8;
		list[6]=(list[4]+7)%8;
		list[7]=(list[5]+1)%8;
		for (int i = 0; i < list.length; i++) {
			
		}
		return list;
	}
	
	protected int[] getAbsoluteDirectionListToStopPoint4(Point p){
		int[] list = new int[4];
		list[0]=getAbsoluteDirection4(p,getCenterOfRectangle(this.rectangleArray.get(this.pathStop)));
		list[1]=(list[0]+1)%4;
		list[2]=(list[0]+7)%4;
		list[3]=(list[1]+1)%4;

		return list;
	}
	
	public double getAbsoluteDistance(Point startPoint,Point stopPoint){
		return Math.sqrt((Math.pow(startPoint.x-stopPoint.x,2)+Math.pow(startPoint.y-stopPoint.y,2)));
	}
	
	public double getDistanceToStopPoint(Point s){
		return getAbsoluteDistance(s, this.getCenterOfRectangle(rectangleArray.get(this.pathStop)));
	}
	
	public static int getAbsoluteDirection(Point startPoint,Point stopPoint){
		return getAbsoluteDirection8(startPoint,stopPoint);
	}
	
	public static int getAbsoluteDirection4(Point startPoint,Point stopPoint){
		
		double angle=Math.atan2(stopPoint.x-startPoint.x,(startPoint.y-stopPoint.y));
		if(angle<0){
			angle+=(2*Math.PI);
		}
		//System.out.println("x:"+(stopPoint.x-startPoint.x)+" y:"+((startPoint.y-stopPoint.y))+" d:"+Math.toDegrees(angle));
		if(angle>=Math.toRadians(45)&&(angle<Math.toRadians(135))){
			//System.out.println("RIGHT");
			return RIGHT;
		}if(angle>=Math.toRadians(135)&&(angle<Math.toRadians(225))){
			//System.out.println("BOTTOM");
			return BOTTOM;
		}if(angle>=Math.toRadians(225)&&(angle<Math.toRadians(315))){
			//System.out.println("LEFT");
			return LEFT;
		}else{
			//System.out.println("TOP");
			return TOP;
		}
	}
	
	public static int getAbsoluteDirection8(Point startPoint,Point stopPoint){
		
		double angle=Math.atan2(stopPoint.x-startPoint.x,(startPoint.y-stopPoint.y));
		if(angle<0){
			angle+=(2*Math.PI);
		}
		//System.out.println("x:"+(stopPoint.x-startPoint.x)+" y:"+((startPoint.y-stopPoint.y))+" d:"+Math.toDegrees(angle));
		if(angle>=Math.toRadians(22)&&(angle<Math.toRadians(67))){
			return TOP_RIGHT;
		}if(angle>=Math.toRadians(67)&&(angle<Math.toRadians(112))){
			return RIGHT;
		}if(angle>=Math.toRadians(112)&&(angle<Math.toRadians(157))){
			return RIGHT_BOTTOM;
		}if(angle>=Math.toRadians(157)&&(angle<Math.toRadians(202))){
			return BOTTOM;
		}if(angle>=Math.toRadians(202)&&(angle<Math.toRadians(247))){
			return BOTTOM_LEFT;
		}if(angle>=Math.toRadians(247)&&(angle<Math.toRadians(292))){
			return LEFT;
		}if(angle>=Math.toRadians(292)&&(angle<Math.toRadians(337))){
			return LEFT_TOP;
		}else{
			return TOP;
		}
	}
	
	protected boolean isCollided(Point p){
		
		
		for (int i = 0; i < rectangleArray.size(); i++) {
			if(i!=pathStop && i!=pathStart){
				//System.out.println(k);
				Rectangle r = (Rectangle) rectangleArray.get(i);
				if(inRectangle(p, r)){
					System.out.println("Collided with numeber: "+i);
					return true;
				}
			}
		}
		
		return false;
	}
	

}
