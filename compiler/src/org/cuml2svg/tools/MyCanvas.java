package org.cuml2svg.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;

import org.cuml2svg.svg.GraphicsManager;

import sun.dc.pr.PathStroker;

public class MyCanvas extends JPanel {
	
	float zoomLevel=4;
	int xstart=400;
	int ystart=400;
	
	int pathStart;
	int pathStop;
	final ArrayList<Rectangle> rectangleArray;
	PathGenerator pathGenerator;
	
	public MyCanvas() {
		GraphicsManager gr = GraphicsManager.getInstance();
		rectangleArray= gr.getRectangles();
		
		
		pathStart=(int)Math.round( Math.random()*(rectangleArray.size()-1));
		do{
		pathStop=(int)Math.round( Math.random()*(rectangleArray.size()-1));
		}while(pathStart==pathStop);
		
		changePath(pathStart,pathStop);
		
	}

	public void changePath(int pathStart, int pathStop)
	{
		if(pathStart>=0 && pathStart<this.rectangleArray.size()){
		
		
		
		if(pathStop>=0 && pathStop<this.rectangleArray.size()){
			this.pathStart= pathStart;
			this.pathStop= pathStop;
		
		System.out.println("generating path from: "+pathStart+" to: "+pathStop);
		pathGenerator= new PathGenerator(this,rectangleArray,pathStart,pathStop);
		pathGenerator.start();}else{
			System.err.println("Non esiste il punto di start indicato");
		}}else{
			System.err.println("Non esiste il punto di stop indicato");}
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		int i =0;
		for (final Rectangle r : rectangleArray) {
			
			if(i==pathStart){
				g.setColor(new Color(0,255,0,255));
			}
			if(i==pathStop){
				g.setColor(new Color(0,255,255,255));
			}
			
			g.drawRect((int)((r.x+xstart)/zoomLevel), (int)((r.y+ystart)/zoomLevel), (int)(r.width/zoomLevel), (int)(r.height/zoomLevel));
			g.setFont(new Font("Arial",1,(int)(40/zoomLevel)));
			g.drawString("id: "+i, (int)((r.x+10+xstart)/zoomLevel), (int)((r.y+50+ystart)/zoomLevel));
			
			g.setColor(new Color(200,200,200,255));
			g.drawRect((int)((r.x-this.pathGenerator.defaultBorder+xstart)/zoomLevel), 
					(int)((r.y-this.pathGenerator.defaultBorder+ystart)/zoomLevel), 
					(int)((r.width+2*this.pathGenerator.defaultBorder)/zoomLevel),
					(int)((r.height+2*this.pathGenerator.defaultBorder)/zoomLevel));
			
			g.setColor(new Color(0,0,0,255));
			drawHandler(g, r);
			//print collision border
			
			i++;
		}
		
		ArrayList<Point> l = this.pathGenerator.currentPath;
		drawPath(g, l);
		
		ArrayList<ArrayList<Point>> l1 = this.pathGenerator.storedPaths;
		for (Iterator k = l1.iterator(); k.hasNext();) {
			ArrayList<Point> element = (ArrayList<Point>) k.next();
			drawPath(g, element);
		}
		
	}

	private void drawPath(Graphics g, ArrayList<Point> l) {
		if (l.size()>1){
			Point oldP;
			Point newP=l.get(0);
			for (int j = 1; j < l.size(); j++) {
				oldP= newP;
				newP=l.get(j);
				g.drawLine(
						(int)((oldP.x+xstart)/zoomLevel), 
						(int)((oldP.y+ystart)/zoomLevel), 
						(int)((newP.x+xstart)/zoomLevel), 
						(int)((newP.y+ystart)/zoomLevel));
			}
		}
	}
	
	
	
	
	private void drawHandler(Graphics g, Rectangle r){
		g.setColor(new Color(255,0,0,255));
	
		g.fillOval(
				(int)((r.x-5+xstart+(r.width/2))/zoomLevel),
				(int)((r.y-5+ystart+(r.height/2))/zoomLevel), 
				(int)(10/zoomLevel), 
				(int)(10/zoomLevel));
		g.setColor(new Color(0,0,255,255));
		g.fillRect(
				(int)((r.x-5+xstart)/zoomLevel),
				(int)((r.y-5+ystart+(r.height/2))/zoomLevel), 
				(int)(10/zoomLevel), 
				(int)(10/zoomLevel));
		
		g.fillRect(
				(int)((r.x-5+xstart+(r.width/2))/zoomLevel),
				(int)((r.y-5+ystart)/zoomLevel), 
				(int)(10/zoomLevel), 
				(int)(10/zoomLevel));
		g.fillRect(
				(int)((r.x-5+xstart+(r.width/2))/zoomLevel),
				(int)((r.y-5+ystart+(r.height))/zoomLevel), 
				(int)(10/zoomLevel), 
				(int)(10/zoomLevel));
		g.fillRect(
				(int)((r.x-5+xstart+(r.width))/zoomLevel),
				(int)((r.y-5+ystart+(r.height/2))/zoomLevel), 
				(int)(10/zoomLevel), 
				(int)(10/zoomLevel));
		g.setColor(new Color(0,0,0,255));
	}

	public void setZoomLevel(int value) {
		this.zoomLevel=value;
		this.repaint();
		
	}
	
	

}
