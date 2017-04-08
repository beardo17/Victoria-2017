package org.usfirst.frc.team6121.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team6121.robot.Robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSubsystem extends Subsystem {
	
	private ArrayList<Integer> Y = new ArrayList<Integer>();
	private ArrayList<Integer> X = new ArrayList<Integer>();
	private ArrayList<Integer> width = new ArrayList<Integer>();
	private ArrayList<Integer> height = new ArrayList<Integer>();
	int[] targets = new int[3];

	private final Object XLock = new Object();
	private final Object YLock = new Object();
	private final Object widthLock = new Object();
	private final Object heightLock = new Object();
	
	
	public Thread visionThread = new Thread(() -> {
		// Get the UsbCamera from CameraServer
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
//		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(1);
		// Set the resolution
		CameraServer.getInstance().startAutomaticCapture(camera);
//		CameraServer.getInstance().startAutomaticCapture(camera1);

		camera.setResolution(320, 240);
		camera.setBrightness(0);
		camera.setExposureManual(5);
		camera.setExposureHoldCurrent();
		camera.setFPS(30);

		// Get a CvSink. This will capture Mats from the camera
		CvSink cvSink = CameraServer.getInstance().getVideo();
//		CvSink cvSink1 = CameraServer.getInstance().getVideo();
		
		// Setup a CvSource. This will send images back to the Dashboard
		CvSource outputStream = CameraServer.getInstance().putVideo("Vision Camera", 320, 240);
//		CvSource outputStream1 = CameraServer.getInstance().putVideo("Utility Camera", 320, 240);
		
		

		// Mats are very memory expensive. Lets reuse this Mat.
		Mat mat = new Mat();
//		Mat mat1 = new Mat();

		// This cannot be 'true'. The program will never exit if it is. This
		// lets the robot stop this thread when restarting robot code or
		// deploying.
		while (!Thread.interrupted()) {
			// Tell the CvSink to grab a frame from the camera and put it
			// in the source mat.  If there is an error notify the output.
			if (cvSink.grabFrame(mat) == 0) { // || cvSink1.grabFrame(mat1) == 0) {
				// Send the output the error.
				outputStream.notifyError(cvSink.getError());
//				outputStream1.notifyError(cvSink1.getError());
				// skip the rest of the current iteration
				continue;
				
			}
			
			// Put a rectangle on the image
			Robot.vision.process(mat);
			for (int i = 0; i < Robot.vision.filterContoursOutput().size(); i++) {
				SmartDashboard.putNumber("Number of Contours", Robot.vision.filterContoursOutput().size());
				final MatOfPoint contour = Robot.vision.filterContoursOutput().get(i);
				final Rect bb = Imgproc.boundingRect(contour);
				
				synchronized (YLock) {
					Y.add(i, bb.y);
				}
				
				synchronized (XLock) {
					X.add(i, bb.x);
				}
				
				synchronized (widthLock) {
					width.add(i, bb.width);
				}
				
				synchronized (heightLock) {
					height.add(i, bb.height);
				}
				
				Imgproc.rectangle(mat, new Point(X.get(i), Y.get(i)), new Point((X.get(i) + width.get(i)) , (Y.get(i) + height.get(i))),
						new Scalar(255, 255, 255), 5);	
			}
			
			// Give the output stream a new image to display
			outputStream.putFrame(mat);
//			outputStream1.putFrame(mat1);
		}
	});
	
	private static final double bH = 10/12;
	private static final double fovP = 240;
	private static final double camVertAngle = 34.3;
	private static final int gearOff = 6;
	double tP;
	
	public enum Target {
		None,
		Boiler,
		Gear;
	}
    
    public void printVision() {
    	for (int i = 0; i < Robot.vision.filterContoursOutput().size(); i++) {
    		if (i < 3) {
				SmartDashboard.putNumber(("Contour Y: " + i),  Y.get(i));
				SmartDashboard.putNumber(("Contour X: " + i),  X.get(i));
				SmartDashboard.putNumber(("Contour width: " + i),  width.get(i));
				SmartDashboard.putNumber(("Contour height: " + i),  height.get(i));
			}
    	}
		SmartDashboard.putString("Target: ", Robot.VISION.getTargetString());
		SmartDashboard.putNumber("Distance: ", getDistance());
		SmartDashboard.putNumber("Target Height: ", getTargetHeight(getTarget()));
    }
    
    /**
     * Get Target
     * 
     * @return The target seen by the camera. Returns NONE if there is no valid target
     */
    @SuppressWarnings("unchecked")
	public Target getTarget() {
    	try {
    		Target target = Target.None;
    		double blRatio, bhRatio, bwRatio, blScore, bhScore, bwScore;
    		double gtRatio = 0, ghRatio = 0, gwRatio = 0, gtScore, ghScore, gwScore;
    		ArrayList<Integer> YLocal;
    		ArrayList<Integer> XLocal;
    		ArrayList<Integer> widthLocal;
    		ArrayList<Integer> heightLocal;
    		synchronized(YLock){
    			YLocal = (ArrayList<Integer>) Y.clone();
    		}
    		
    		synchronized(XLock){
    			XLocal = (ArrayList<Integer>) X.clone();
    		}
    		
    		synchronized(widthLock){
    			widthLocal = (ArrayList<Integer>) Y.clone();
    		}
    		
    		synchronized(heightLock){
    			heightLocal = (ArrayList<Integer>) Y.clone();
    		}
    		
    		if (Robot.vision.filterContoursOutput().size() > 1) {
    			for (int i = 0; i < YLocal.size() - 1; i++) {
    				blRatio = ((XLocal.get(i) - XLocal.get(i+1)) / XLocal.get(i) + 1);
    				bhRatio = (heightLocal.get(i) / (heightLocal.get(i+1) * 2));
    				bwRatio = (widthLocal.get(i) / widthLocal.get(i+1));
    				
    				blScore = (100 - (100 * Math.abs(1-blRatio)));
    				bhScore = (100 - (100 * Math.abs(1-bhRatio)));
    				bwScore = (100 - (100 * Math.abs(1-bwRatio)));
    				
    				if (blScore + bhScore + bwScore >= 250) {
    					target = Target.Boiler;
    					targets[0] = i;
    					targets[1] = i + 1;
    					break;
    				}
    			
    				if (Math.abs(XLocal.get(i) - XLocal.get(i+1)) <= 5 && Robot.vision.filterContoursOutput().size() > 2) {
    					if (((heightLocal.get(i) + heightLocal.get(i+1)) / 2 + (YLocal.get(i) + YLocal.get(i+1)) / 2) - (heightLocal.get(i+2) / 2 + YLocal.get(i+2)) >= 5) {
    						gtRatio = ((YLocal.get(i) - YLocal.get(i+2)) / YLocal.get(i+2));
    						gwRatio = (widthLocal.get(i) / widthLocal.get(i+2));
    						ghRatio = (YLocal.get(i) - YLocal.get(i+1)) / (heightLocal.get(i+2));
    					}	
    				} else if (Robot.vision.filterContoursOutput().size() > 2 && Math.abs(XLocal.get(i+1) - XLocal.get(i+2)) <= 5) {
    					if (((heightLocal.get(i+1) + heightLocal.get(i+2)) / 2 + (YLocal.get(i+1) + YLocal.get(i+2)) / 2) - (heightLocal.get(i) / 2 + YLocal.get(i)) >= 5) {
    						gtRatio = ((YLocal.get(i+1) - YLocal.get(i+2)) / YLocal.get(i));
    						gwRatio = (widthLocal.get(i) / widthLocal.get(i+2));
    						ghRatio = (YLocal.get(i+2) - YLocal.get(i+1)) / (heightLocal.get(i));
    					}	
    				} else {
    					gtRatio = ((YLocal.get(i) - YLocal.get(i+1)) / heightLocal.get(i) + 1);
    					gwRatio = (widthLocal.get(i) / widthLocal.get(i+1));
    					ghRatio = (heightLocal.get(i) / heightLocal.get(i+1));
    				}	
    			
    				gtScore = (100 - (100 * Math.abs(1-gtRatio)));
    				ghScore = (100 - (100 * Math.abs(1-ghRatio)));
    				gwScore = (100 - (100 * Math.abs(1-gwRatio)));
    				
    				if (gtScore + ghScore + gwScore >= 250) {
    					target = Target.Gear;
    					targets[0] = i;
    					targets[1] = i + 1;
    					targets[2] = i + 2;
    					break;
    				}	
    			}
    		}
    		return target;
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(Y);
    		System.out.println(X);
    		System.out.println(width);
    		System.out.println(height);
    	}
    	return Target.None;
    }
    
    public String getTargetString() {
    	if (getTarget() == Target.Boiler) {
    		return "Boiler";
    	} else if (getTarget() == Target.Gear) {
    		return "Gear";
    	} else {
    		return "None";
    	}
    }
    
    /**
     * The height of the target seen by the camera
     * 
     * @param t The current Target in view 
     * @return the height of the target
     */
    @SuppressWarnings("unchecked")
	public double getTargetHeight(Target t) {
    	double h = 0;
    	double T = 0, B = 0;
		ArrayList<Integer> YLocal;
		ArrayList<Integer> XLocal;
		ArrayList<Integer> heightLocal;
		synchronized(YLock){
			YLocal = (ArrayList<Integer>) Y.clone();
		}
		
		synchronized(XLock){
			XLocal = (ArrayList<Integer>) X.clone();
		}
		
		synchronized(heightLock){
			heightLocal = (ArrayList<Integer>) Y.clone();
		}
		
    	if (targets.length > 1) {
    		for (int i = 0; i < targets.length; i++) {
    			switch (t) {
    			case Boiler:
    				//TODO: Fix this stuff
    				break;
    			case Gear:
    				if (Math.abs(XLocal.get(i) - XLocal.get(i+1)) <= 5) {
    					T = (YLocal.get(targets[i]) + heightLocal.get(targets[i]) / 2);
    					B = (YLocal.get(targets[i+1]) + heightLocal.get(targets[i+1]) / 2);
    				} else if (Math.abs(XLocal.get(i+1) - XLocal.get(i+2)) <= 5) {
    					T = (YLocal.get(targets[i]) + heightLocal.get(targets[i]) / 2);
    					B = (YLocal.get(targets[i]) + heightLocal.get(targets[i]) / 2);
    				} else {
    					return heightLocal.get(i);
    				}
    				break;
    			default:
    			}
    		}
    	}
    	h = T - B;
		return h;
    }
    
    public double getDistance() {
    	double d = 0;
    	try {
    		switch (getTarget()) {
    		case Boiler:
    			tP = getTargetHeight(Target.Boiler);
    			d = bH * fovP / (2 * tP * Math.tan(camVertAngle));
    			break;
    		case Gear:
    			tP = getTargetHeight(Target.Gear);
    			d = bH * fovP / (2 * tP * Math.tan(camVertAngle));
    			break;
    		default:
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(Y);
    		System.out.println(X);
    		System.out.println(width);
    		System.out.println(height);
    	}
    	printVision();
		return d;
    }
    
    @SuppressWarnings("unchecked")
	public double getCenterX() {
		ArrayList<Integer> XLocal;
		ArrayList<Integer> widthLocal;
		
		synchronized(XLock){
			XLocal = (ArrayList<Integer>) X.clone();
		}
		
		synchronized(widthLock){
			widthLocal = (ArrayList<Integer>) Y.clone();
		}
		
    	Target t = getTarget();
    	if (t == Target.Boiler) {
    		return XLocal.get(0) + widthLocal.get(0) / 2;
    	} else if (t == Target.Gear) {
    		return (XLocal.get(0) + widthLocal.get(0) / 2) * 1.025625;
    	} else {
    		return 0;
    	}
    }
    
    @SuppressWarnings("unchecked")
	public double getWidth() {
		ArrayList<Integer> widthLocal;
		
		synchronized(widthLock){
			widthLocal = (ArrayList<Integer>) Y.clone();
		}
		
    	Target t = getTarget();
    	if (t == Target.Boiler) {
    		return widthLocal.get(0);
    	} else if (t == Target.Gear) {
    		return widthLocal.get(0) * 5.125;
    	} else {
    		return 0;
    	}
    }
    
    public double getTurn() {
    	System.out.println("Getting Turn...");
    	try {
    		if (getTarget() == Target.Gear) {
    			return Math.asin((160 - getCenterX())/getDistance()) + 
    					(90 - (Math.asin(Math.sqrt(getDistance()*getDistance() - gearOff*gearOff)/getDistance())));
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println(Y);
    		System.out.println(X);
    		System.out.println(width);
    		System.out.println(height);
    	}
    	return 0;
    }
    
    public double getTimeToGear() {
    	return getDistance() / 27 + 0.3;
    }
	
	public void initDefaultCommand() {
	}
}