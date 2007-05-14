package org.cuml2svg.tools;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

public class PathGenerator extends Thread {
	
	
	
	
	MyCanvas canvas=null;
	ArrayList<Rectangle> rectangleArray=null;
	ArrayList<Point> visitedPoint = new ArrayList<Point>();
	
	
	int pathStart=0;
	int pathStop=0;
	
	
	int timing = 10;

	private double lastDistance=Double.MAX_VALUE;
	private double currentDistance=Double.MAX_VALUE;
	
	//enum Direction {TOP, RIGHT, BOTTOM, LEFT;
	public static final int TOP=0;
	public static final int RIGHT=1;
	public static final int BOTTOM=2;
	public static final int LEFT=3;
	
	public static final int TOP_RIGHT=4;
	public static final int RIGHT_BOTTOM=5;
	public static final int BOTTOM_LEFT=6;
	public static final int LEFT_TOP=7;
	
	int defaultStepLenght=20;
	int defaultBorder=30;
	int externalBoxMaxY;
	int externalBoxMaxX;
	int externalBoxMinY=-100;
	int externalBoxMinX=-100;
	
	ArrayList<Point> currentPath = new ArrayList<Point>();
	ArrayList<ArrayList<Point>> storedPaths = new ArrayList<ArrayList<Point>>();
	private int maxPathSize=1000;
	private ArrayList<Integer> pathDirections= new ArrayList<Integer>();
	
	static int currentPathCost=0;
	
	
	public ArrayList<Point> getPath(int startId,int stopId){
		this.pathStart= startId;
		this.pathStop= stopId;
		run();
		int minLenght= Integer.MAX_VALUE;
		ArrayList<Point> currentPath=null;
		for (ArrayList<Point> path : storedPaths) {
			if(path.size()<minLenght){
				minLenght=path.size();
				currentPath= path;
			}
		}
		return currentPath;
	}
	
	public PathGenerator(MyCanvas canvas, ArrayList<Rectangle> rectangleArray, int pathStart, int pathStop) {
		this.canvas=canvas;
		this.rectangleArray= rectangleArray;
		this.pathStart= pathStart;
		this.pathStop= pathStop;
		
		
		for (Rectangle r : rectangleArray) {
			this.externalBoxMaxX=Math.max(externalBoxMaxX, r.x)+100;
			this.externalBoxMaxY=Math.max(externalBoxMaxY, r.y)+100;
		}
		
		
	}
	
	protected boolean isOutOfExternalBox(Point p) {
		if(p.x<externalBoxMinX || p.y<externalBoxMinY || p.x> externalBoxMaxX || p.y >externalBoxMaxY) return true;
		return false;
	}
	
	private void collapsePath(){
		ArrayList<Point> tmpPath= new ArrayList<Point>();
		//ArrayList<Integer> tmpDirections= new ArrayList<Integer>();
		int d1=0;
		int d2=pathDirections.get(0);
		tmpPath.add(currentPath.get(0));
		for (int i = 1; i < currentPath.size(); i++) {
			d1=d2;
			d2=pathDirections.get(i);
			//trovato cambio di direzione
			if(d1!=d2){
				System.out.println("Cambio di direzione in: "+i);
				tmpPath.add(currentPath.get(i-1));
			}

		}
		tmpPath.add(currentPath.get(currentPath.size()-1));
		//storedPaths.add(tmpPath);
		this.currentPath= tmpPath;
	}
	
	private boolean optimizePath() throws Exception{
		int d1=0;
		int d2=pathDirections.get(0);
		Point p;
		
		for (int i = 1; i < currentPath.size(); i++) {
			d1=d2;
			d2=pathDirections.get(i);
			//trovato cambio di direzione
			if(d1!=d2){
				System.out.println("Cambio di direzione in: "+i);
				System.in.read();
				p=currentPath.get(i-1);
				ArrayList<Point> tmp2= new ArrayList<Point>();
				
				while(!isCollided(p) && !isOutOfExternalBox(p)){
					
					p=makeStep(p,d1);
					tmp2.add(p);
					removeStep();
					int f;
					if((f=isInPath(p))>0){
						System.out.println("UOU I can optimize: i:"+i+" f:"+f);
						ArrayList<Point> tmp= new ArrayList<Point>();
						ArrayList<Integer> tmp1= new ArrayList<Integer>();
						for (int k = 0; k < i; k++) {
							tmp.add(currentPath.get(k));
							tmp1.add(pathDirections.get(k));
						}
						for (int k = 0; k < tmp2.size(); k++) {
							tmp.add(tmp2.get(k));
							tmp1.add(d1);
						}
						for (int k = f; k < currentPath.size(); k++) {
							tmp.add(currentPath.get(k));
							tmp1.add(pathDirections.get(k));
						}
						currentPath=tmp;
						pathDirections=tmp1;
						canvas.repaint();
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private int isInPath(Point p) {
		Point p1;
		for (int i = 0; i < currentPath.size(); i++) {
			p1=currentPath.get(i);
			if(p.x==p1.x && p.y==p1.y){
				return i;
			}
		}
		return 0;
	}

	private boolean removeLoopFromPath(){
			for (int i = 0; i < currentPath.size(); i++) {
				Point p1 = currentPath.get(i);
				for (int j = i+1; j < currentPath.size(); j++) {
					Point p2 = currentPath.get(j);
					if(p1.x==p2.x && p1.y==p2.y){
						System.out.println("Cycle discovered");
						ArrayList<Point> tmp= new ArrayList<Point>();

						ArrayList<Integer> tmp1= new ArrayList<Integer>();
						for (int k = 0; k <= i; k++) {
							tmp.add(currentPath.get(k));
							tmp1.add(pathDirections.get(k));
						}
						for (int k = j+1; k < currentPath.size(); k++) {
							tmp.add(currentPath.get(k));
							tmp1.add(pathDirections.get(k));
						}
						currentPath=tmp;
						pathDirections=tmp1;
						return true;
					}
				}
			}
			return false;
		
	} 
	
	private boolean isAlreadyVisited(Point p){
		Point p1= null;
		for (int i = visitedPoint.size()-1; i >0 ; i--) {
			p1=visitedPoint.get(i);
			if(p1.x==p.x && p1.y==p.y ){
				return true;
			}
		}
		return false;
	}
	

	
	/**
	 * 
	 * @param p
	 * @return se 0 algoritmo completato con successo
	 * 			se 1 ancora dei passi da eseguire
	 * 			se >1 allora vengono smontati gli ultimi n passi
	 * 			se -1 raggiunto il massimo coefficiente di costo
	 * 			se -2 da definire
	 * 			in questo modo è possibile fare un controllo su >0 per continuare la ricorsione
	 * @throws Exception
	 */
	public int visitFrom(Point p) throws Exception{
		
		lastDistance= currentDistance;
		currentDistance= getDistanceToStopPoint(p);
		
		if(currentPath.size()>maxPathSize){
			System.out.println("==== FALIED: Max lenght reached ====");
			currentPath= new ArrayList<Point>();
			pathDirections= new ArrayList<Integer>();
			visitedPoint.clear();
			return -1;
		}
		
		if(isArrived(p)){
			System.out.println("==== ARRIVED ====");
			//while(removeLoopFromPath()){Thread.sleep(500);}
			//optimizePath();
			//while(optimizePath()){Thread.sleep(500);}
			collapsePath();Thread.sleep(2000);
			this.storedPaths.add(currentPath);
			currentPath= new ArrayList<Point>();
			pathDirections= new ArrayList<Integer>();
			visitedPoint.clear();
			return 0;
		}
		
		
		
		if(isCollided(p)){
			return 1;
		}
		if(isOutOfExternalBox(p)){
			System.out.println("Collide with externa box: x="+p.x+" y="+p.y);
			return 1;
		}
		
		
		if(isAlreadyVisited(p)){
			//System.out.println("already visited: x="+p.x+" y="+p.y);
			return 1;
		}else{
			visitedPoint.add(p);
		}
		Point next=null;
		int[] directions;
		
		//controlla che non ci siano tre direzioni differenti
		//vuol dire che sta girando su se stesso
		if(pathDirections.size()>2){
			int[] d = {
					pathDirections.get(pathDirections.size()-1),
					pathDirections.get(pathDirections.size()-2),
					pathDirections.get(pathDirections.size()-3)
			};
			if((d[0] !=d[1])&&(d[1]!=d[2])&&(d[2]!=d[0])){
				removeVisitedPoint(2);
				return 3;
			}
		}
		
		//verifico che non ci sia l'effetto scaletta
//		if(pathDirections.size()>3){
//			int[] d = {
//					pathDirections.get(pathDirections.size()-1),
//					pathDirections.get(pathDirections.size()-2),
//					pathDirections.get(pathDirections.size()-3),
//					pathDirections.get(pathDirections.size()-4)
//			};
//			if((d[0] ==d[2])&&(d[1]==d[3])){
//				removeVisitedPoint(1);
//				return 1;
//			}
//		}
		
		if(currentDistance < lastDistance){
			//uso l'ultima direzione
			Thread.sleep(timing);
			next=makeStep(p, pathDirections.get(pathDirections.size()-1));
			int s=visitFrom(next);
			if(s<=0){
				return s;
			}else{
				if(s>1){
					removeStep();
					return (s-1);
				}
			}
			removeStep();
		}
		
		
		
		//prendo la direzione più opportuna in base alla direzione della destinazione
		directions=getAbsoluteDirectionListToStopPoint4(p);
		for (int i = 0; i < directions.length; i++) {
			
			
			Thread.sleep(timing);
			next=makeStep(p, directions[i]);
			int s=visitFrom(next);
			if(s<=0){
				return s;
			}else{
				//posso tornare un s>0 e scalerò di 2 passo
				if(s>1){
					removeStep();
					return (s-1);
				}
			}
			removeStep();
		}
		
		return 1;
	}

	private void removeVisitedPoint(int n) {
		for (int i = 0; i < n; i++) {
			if(visitedPoint.size()>1){
			visitedPoint.remove(visitedPoint.size()-1);
			}	
		}
	}
	


	@Override
	public void run() {
		Point[] startPoint=this.getStartPoint();
		
		int i = getAbsoluteDirectionToStopPoint4(this.getCenterOfRectangle(this.rectangleArray.get(this.pathStart)));
		try {
						
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			this.pathDirections.add(i);
			startPoint[i]=makeStep(startPoint[i], i,this.defaultBorder+defaultStepLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
			
			i=(i+1)%4; //prendo l'ancora a lato del più vicino
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			this.pathDirections.add(i);
			startPoint[i]=makeStep(startPoint[i], i,this.defaultBorder+defaultStepLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
			
			i=(i+2)%4; //prendo l'ancora a lato del più vicino
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			this.pathDirections.add(i);
			startPoint[i]=makeStep(startPoint[i], i,this.defaultBorder+defaultStepLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
			
			i=(i+3)%4;//prendo l'ancora opposta del più vicino
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			this.pathDirections.add(i);
			startPoint[i]=makeStep(startPoint[i], i,this.defaultBorder+defaultStepLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		removeStep();		
		
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
		this.pathDirections.add(direction);
		this.canvas.repaint();
		return p1;	
	}
	
	protected void removeStep(){
		if(currentPath.size()>0){
			this.currentPath.remove(currentPath.size()-1);
			this.pathDirections.remove(pathDirections.size()-1);
		}
//		if(pathDirections.size()>0){
//			this.pathDirections.remove(pathDirections.size()-1);
//		}
		
		
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
			if(i!=pathStop ){//&& i!=pathStart){
				//System.out.println(k);
				Rectangle r = (Rectangle) rectangleArray.get(i);
				if(inRectangle(p, r)){
					//System.out.println("Collided with number: "+i);
					return true;
				}
			}
		}
		
		return false;
	}
	

}
