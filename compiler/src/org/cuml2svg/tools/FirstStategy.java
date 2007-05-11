package org.cuml2svg.tools;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.TreeMap;

import sun.awt.windows.ThemeReader;

import com.sun.corba.se.spi.ior.MakeImmutable;

public class FirstStategy extends PathGenerator {
	ArrayList<Point> visitedPoint = new ArrayList<Point>();
	int timing = 10;
	private int lastDirection=0;
	private double lastDistance=Double.MAX_VALUE;
	private double currentDistance=Double.MAX_VALUE;
	
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
	
	public void visitFrom(Point p) throws Exception{
		lastDistance= currentDistance;
		currentDistance= getDistanceToStopPoint(p);
		
		if(isArrived(p)){
			System.out.println("==== ARRIVED ====");
			this.stop();
		}
		if(isCollided(p)){
			System.out.println("Collided: x="+p.x+" y="+p.y);
			return;
		}
		if(isOutOfExternalBox(p)){
			//System.out.println("Out Of External Box: x="+p.x+" y="+p.y);
			return;
		}
		
		
		if(isAlreadyVisited(p)){
			//System.out.println("already visited: x="+p.x+" y="+p.y);
			return;
		}else{
			visitedPoint.add(p);
		}
		Point next=null;
		int[] directions;
		
		if(currentDistance < lastDistance){
			//uso l'ultima direzione
			//FIXME solo se non peggiora la distanza dall'arrivo
			Thread.sleep(timing);
			next=makeStep(p, lastDirection);
			visitFrom(next);removeStep();
		}
		
		
			//prendo la direzione più opportuna
			directions=getAbsoluteDirectionListToStopPoint4(p);//getAbsoluteDirectionToStopPoint(p);
//			lastDirection=direction;
//			Thread.sleep(timing);
//			next=makeStep(p, direction);
//			visitFrom(next);removeStep();
		
		
		for (int i = 0; i < directions.length; i++) {
			lastDirection=directions[i];
			Thread.sleep(timing);
			next=makeStep(p, directions[i]);
			visitFrom(next);removeStep();
			
		}
			

		
		
	}
	


	public FirstStategy(MyCanvas canvas, ArrayList<Rectangle> rectangleArray,
			int pathStart, int pathStop) {
		super(canvas, rectangleArray, pathStart, pathStop);
	}

	@Override
	public void run() {
		Point[] startPoint=this.getStartPoint();
	
//		for (int i = 0; i < startPoint.length; i++) {
//			try {
//				startPoint[i]=makeStep(startPoint[i], i);
//				visitFrom(startPoint[i]);
//				removeStep();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		int i = getAbsoluteDirectionToStopPoint4(this.getCenterOfRectangle(this.rectangleArray.get(this.pathStart)));
		try {
			int firstMoveLenght=50;
			
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			startPoint[i]=makeStep(startPoint[i], i,firstMoveLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
			
			i=(i+1)%4; //prendo l'ancora a lato del più vicino
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			startPoint[i]=makeStep(startPoint[i], i,firstMoveLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
			
			i=(i+2)%4; //prendo l'ancora a lato del più vicino
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			startPoint[i]=makeStep(startPoint[i], i,firstMoveLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
			
			i=(i+3)%4;//prendo l'ancora opposta del più vicino
			System.out.println("starting with point "+i);
			this.currentPath.add(startPoint[i]);
			startPoint[i]=makeStep(startPoint[i], i,firstMoveLenght);
			visitFrom(startPoint[i]);removeStep();removeStep();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		removeStep();
		
		
		
//		scelgo il punto di partenza quello a distanza minima al centro del
		//riquadro di stop
//		int direction=0;
//		double min=Double.MAX_VALUE;
//		for (int i = 0; i < startPoint.length; i++) {
//			double current=this.getDistanceToStopPoint(startPoint[i]);
//			System.out.println("current min direction: "+current);
//			if(min>current){
//				min=current;
//				direction=i;
//				
//			}
//		}
//		System.out.println("Direction: "+direction);
//		this.currentPath.add(startPoint[direction]);
//		
//		//faccio 2 passi fuori dal quadrato
//		for (int i = 0; i < 2; i++) {
//			
//		
//		currentPoint =this.makeStep(currentPath.get(currentPath.size()-1), direction);
//		if(this.isCollided(currentPoint)){
//			System.out.println("Collided uff");
//			
//		}
//		}
		
		//getAbsoluteDirectionToStopPoint(p)
		
		
	}
	
	

}
