import java.awt.Component;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.*;
import org.opencv.*;

import SWT.SWTPoint2d;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.Utils;

import org.apache.commons.io.FilenameUtils;

import SWT.*;
public class FirstElemFullWidth {
	
	public hashMap compMap;
	public Mat img_to_save = new Mat();
	public String fileToSaveImgs = "/Users/greg/Desktop/imgs_final_proj/";

	
	public double distance (double x1,double x2,double y1,double y2) {
	    double ycoordDiff = Math.abs (y1 - y2);
	    double xcoordDiff = Math.abs (x1- x2);    
	    double distance = Math.sqrt((ycoordDiff)*(ycoordDiff) +(xcoordDiff)*(xcoordDiff));
	    return distance; 
	    }
	
	public void mergeTogether(Rect rect,Rect el) {
		if(el.x < rect.x) {
			int max =Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));
			  el.width = max;

				}else {

					int max = Math.max(rect.width,Math.max(el.width,Math.abs(rect.x-(el.x+el.width))));

					el.width = max;
				
				} 		    	    		  
			 
			 if(el.y < rect.y) {
				 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
			 }else {
				 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));

			 }
			 
			 
			 el.y = Math.min(el.y,rect.y);
			 el.x = Math.min(el.x,rect.x);
	}
	

	public String doOcr(Mat img) {
		//Imgcodecs.imwrite("N:\\Desktop\\output\\Capture12.jpg",imageA);    		
	       
			ITesseract instance = new Tesseract();
			//File imgFile = new File("N:\\Desktop\\output\\Capture12.jpg");
			//System.out.println(imgFile.canRead());
			
			Mat m = img.clone();
			double TARGET_PIXEL_AREA = 800000.0;

			double ratio = m.size().width / m.size().height;
			double new_h = Math.sqrt(TARGET_PIXEL_AREA / ratio) + 0.5;
			double new_w = (new_h * ratio) + 0.5;
			
			//Size sizeOfimg = new Size(500,450);
			//double scaleX, scaleY;
			//scaleX = 1000  / m.size().width;
			//scaleY = 500 / m.size().height;
		//	double   scale = Math.min( scaleX, scaleY );
		//	double new_h = Math.round(m.size().height*scale);
		//	double new_w = Math.round(m.size().width*scale);
			//Imgproc.resize(m, m, new Size(new_w,new_h));
		//'/	Imgproc.resize(m, m, new Size(new_w,new_h),0,0,Imgproc.INTER_NEAREST);
			int type = BufferedImage.TYPE_BYTE_GRAY;
			
			if ( m.channels() > 1 ) {
				type = BufferedImage.TYPE_3BYTE_BGR;
			}
			int bufferSize = m.channels()*m.cols()*m.rows();
			byte [] b = new byte[bufferSize];
			m.get(0,0,b); // get all the pixels
			BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
			final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
			System.arraycopy(b, 0, targetPixels, 0, b.length); 
			

			instance.setDatapath("/Users/greg/Desktop/Placemnet Year/Tess4J/tessdata/"); 
			instance.setLanguage("eng");
			
			try {
				String result = instance.doOCR(image);
//				System.out.println(name);
			//	System.out.println(result); 
				
				
				
				
				return result;
			} catch(TesseractException e) {
				System.out.println(e);
	
			}
			return "";
	}
	  
	public void displayImage(Mat org_image,String img,String name,Size size,Boolean do_ocr){
		Component component = compMap.getComponentByName(img);
    	
    	if (component instanceof JLabel) {

		JLabel lbl_img = (JLabel) component;
		
		Mat m = org_image.clone();
		//Size sizeOfimg = new Size(500,450);
		
//		double TARGET_PIXEL_AREA = 100000.0;
//
//		double ratio = m.size().width / m.size().height;

	
		
		double      scaleX, scaleY;
		scaleX = 500  / m.size().width;
		scaleY = size.height / m.size().height;
		double   scale = Math.min( scaleX, scaleY );
		double new_h = Math.round(m.size().height*scale);
		double new_w = Math.round(m.size().width*scale);
		Imgproc.resize(m, m, new Size(new_w,new_h));
     
	//	Imgproc.resize(m, m, size,0,0,Imgproc.INTER_NEAREST);
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if ( m.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels()*m.cols()*m.rows();
		byte [] b = new byte[bufferSize];
		m.get(0,0,b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length); 
		
		//doOcr(org_image,img);
//		if(do_ocr) {
//			doOcr(image,img);
//		}
		lbl_img.setIcon(new ImageIcon(image));
		
    	}
    	
    	
    	Component componentLbl = compMap.getComponentByName("lbl_"+img);
    	
    	if (componentLbl instanceof JLabel) {
    		((JLabel) componentLbl).setText(name);
    		
    	}
  }
	  
	
	  
	  


    static {
//		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	nu.pattern.OpenCV.loadShared();

    }
 
    
    public void start_loop(hashMap hm,String fileName) {
    	long startTime = System.nanoTime();   

    	int iteration = 0;
    	int savedimgs = 0;
    	int first = 255;
    	
    	int second;
    	int third; 
    	int erode; 
    	int dilate;
    	int every1000 = 0;
    	
    	for(int f = 3; f<150; f+=2) {
    		for(int s = 3; s<150; s+=2) {
    			for(int e = 1; e<20; e++) {
    				for(int d = 1; d<20; d++) {
    					int numOfBoxes = startProccess( hm, fileName, first, f, s, e, d);
						System.out.println("working..."+f+"_"+s+"_"+e+"_"+d);
						iteration++;
						every1000++;
						if(every1000 == 1000) {
					    	System.out.println("************************_ " +iteration+"_************************");
					    	every1000=0;
						}
						
    					if(numOfBoxes >20 && numOfBoxes < 40) {
//    						Imgcodecs.imwrite("N:\\Desktop\\res_multi_run\\img_"+f+"_"+s+"_"+e+"_"+d+".jpg",img_to_save);
    						Imgcodecs.imwrite(fileToSaveImgs + "img_"+f+"_"+s+"_"+e+"_"+d+".jpg",img_to_save);

    					
    						//System.out.println("working..."+f+"_"+s+"_"+e+"_"+d);
    						savedimgs++;
    					}
    		    	}	
    	    	}	
        	}
    	}
    	
    	System.out.println("Elapsed time: " + ((double)(System.nanoTime() - startTime)/1000000000.0) + " seconds");
    	System.out.println("Total iterations: "+iteration);
    	System.out.println("Total Found solutions "+ savedimgs);

    }
    
    
    

 
	public int startProccess(hashMap hm,String fileName,int first,int second,int third, int erode, int dilate) {
	
		
		compMap = hm;
//		String inputFile = ".\\bin\\img\\"+fileName;
		String inputFile = "./src/img/"+fileName;

	    
	     Mat org_img = Imgcodecs.imread(inputFile);

			double TARGET_PIXEL_AREA = 1000000.0;

			double ratio = org_img.size().width / org_img.size().height;
			double new_h = Math.sqrt(TARGET_PIXEL_AREA / ratio) + 0.5;
			double new_w = (new_h * ratio) + 0.5;
	     

	     Imgproc.resize(org_img, org_img, new Size(new_w,new_h) );

			Size largSize = new Size(500,450);
	        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

	        int erosion_size = erode;
	        int dilation_size = dilate;
	        
	        Mat imageBlurr = new Mat(org_img.size(), CvType.CV_8UC4),
        		imageA = new Mat(org_img.size(), CvType.CV_32F),
        		hierarchy = new Mat(),
	    		mask = new Mat(),mask2 = new Mat(), 
	    		greyscale = new Mat(), 
	    		bw = new Mat(),
	    		destination = new Mat(bw.rows(),bw.cols(),bw.type()),
	    		erosion_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(erosion_size , erosion_size)),
	    		dilation_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(dilation_size , dilation_size));

		    
	        
	        
			
	     //Imgproc.resize(org_img, org_img, new Size(1000,1000) );

	     Imgproc.cvtColor(org_img, greyscale, Imgproc.COLOR_BGR2GRAY);


//Imgproc.createCLAHE(2, new Size(8,8)).apply(greyscale, greyscale);

displayImage(greyscale,"img_1","Org",largSize,false);
		

		Imgproc.GaussianBlur(greyscale, imageBlurr, new Size(5,5), 0);

		Core.addWeighted(imageBlurr, 1.5, imageBlurr, -0.5, 0, imageBlurr);
		
		
		
	     Mat copyToReadData = imageBlurr.clone();
//	 /    doOcr(copyToReadData,"ORG",new Size(copyToReadData.size().width,copyToReadData.size().height));

		

displayImage(greyscale,"img_2","Org",largSize,false);

	    

        Imgproc.adaptiveThreshold(imageBlurr, imageA, first,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV,second, third);

displayImage(imageA,"img_3","Adaptive Threshold",largSize,false);
//doOcr(imageA,"ADP",new Size(imageA.size().width,imageA.size().height));

        
        destination = imageA.clone();
        

        Imgproc.erode(imageA, destination, erosion_element);
        
//displayImage(destination,"img_3", "erode",largSize,false);
displayImage(destination,"img_4", "erode",largSize,false);
//doOcr(destination,"ERODE",new Size(destination.size().width,destination.size().height));

        Imgproc.dilate(destination, destination, dilation_element);
        
//displayImage(destination,"img_4","dilate",largSize,false);    

displayImage(destination,"img_5","dilate",largSize,false);    
   
 
//_______________________________________________________FINDING CONTURS / FIRST ELEMS / CUTING IMAGE______________________________________________________    
    Imgproc.findContours(destination, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

    

    
    Imgproc.cvtColor(greyscale,mask,Imgproc.COLOR_GRAY2BGR);
    Imgproc.cvtColor(greyscale,mask2,Imgproc.COLOR_GRAY2BGR);

    List<Rect> filteredElems = new ArrayList<Rect>();

    for(int idx = 0; idx < contours.size();idx++){
 	   
	   
 	   Rect rect = Imgproc.boundingRect(contours.get(idx));

 	   if(rect.height>20 && rect.height < 250 && rect.width>20 && rect.width < 400) {
 		  int count = 0;
 		   
//		   outerloop:
	 		   for(Rect el : filteredElems) {
	 	    	  // Rect el = Imgproc.boundingRect(contour);
	 	    	   if(!el.equals(rect)) {


	    
	    double x1 =el.x, 
			    y1 =el.y, 
			    x1b = el.x + el.width, 
			    y1b = el.y + el.height, 
			    x2 = rect.x, 
			    y2 = rect.y, 
			    x2b = rect.x + rect.width, 
			    y2b = rect.y + rect.height;
	    
	    
	    
	    
	 	    			    boolean left = x2b < x1,
	 	    			    right = x1b < x2,
	 	    			    bottom = y2b < y1,
	 	    			    top = y1b < y2;
	 	    			    
	 	    			    if (top && left) {

	 	    			    	 if(distance(x1, y1b, x2b, y2) < 10) { 
//	 	    			    		  System.out.print(rect);
//	 	    		 	    		  System.out.print("  /  ");
//	 	    		 	    		  System.out.print(el);
//	 	    		 	    		  System.out.print("  /  ");
//	 	    			    	System.out.println(distance(x1, y1b, x2b, y2) + " - top && left");
	 	    			    	count++;
//	 	    			    	break outerloop;
	 	    			    	 }
	 	    			    }else if (left && bottom) {

	 	    			    	 if(distance(x1, y1, x2b, y2b) < 10) { 
//	 	    			    		  System.out.print(rect);
//	 	    		 	    		  System.out.print("  /  ");
//	 	    		 	    		  System.out.print(el);
//	 	    		 	    		  System.out.print("  /  ");
//	 	    			    	System.out.println(distance(x1, y1, x2b, y2b)+ " - left && bottom");
	 	    			    	count++;
//	 	    			    	break outerloop;
	 	    			    	 }
	 	    			   }else if (bottom && right) {

	 	    				  if(distance(x1b, y1, x2, y2b) < 10) { 
//	 	    					  System.out.print(rect);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    	 	    		  System.out.print(el);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    				   System.out.println( distance(x1b, y1, x2, y2b)+ " - bottom && right");
	 	    				  count++;
//	 	    				 break outerloop;
	 	    				  }
	 	    			   }else if (right && top) {

	 	    				  if(distance(x1b, y1b, x2, y2) < 10) { 
//	 	    					  System.out.print(rect);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    	 	    		  System.out.print(el);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    				   System.out.println( distance(x1b, y1b, x2, y2)+ " - right && top");
	 	    				  count++;
//	 	    				 break outerloop;
	 	    				  }
	 	    			   }else if (left) {

	 	    				  if(x1 - x2b < 4) { 
//	 	    					  System.out.print(rect);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    	 	    		  System.out.print(el);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    				   System.out.println(x1 - x2b+ " - left");
	 	    				   
	 	    				  mergeTogether(rect,el);

//	 	    				 break outerloop;
	 	    				  count++;
	 	    				  }
	 	    			   }else if (right) {

	 	    				  if(x2 - x1b < 4) { 
//	 	    					  System.out.print(rect);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    	 	    		  System.out.print(el);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    				   System.out.println( x2 - x1b+ " - right");
		 	    				  mergeTogether(rect,el);

	 	    				  count++;
//	 	    					break outerloop;
	 	    				  }
	 	    			   }else if (bottom) {
	 	    				 if(y1 - y2b < 4) { 
//	 	    					  System.out.print(rect);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    	 	    		  System.out.print(el);
//	 	    	 	    		  System.out.print("  /  ");
//	 	 	    				  System.out.println( y1 - y2b+ " - bottom");
	 	 	    				count++;
//		 	    				break outerloop;
	 	    				 }
	 	    				
	 	    			   }else if (top) {
	 	    				 
	 	    				  if(y2 - y1b < 4) { 
//	 	    					  System.out.print(rect);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    	 	    		  System.out.print(el);
//	 	    	 	    		  System.out.print("  /  ");
//	 	    					  System.out.println( y2 - y1b+ " - top");
	 	    					 count++;
//	 	    				 break outerloop;
	 	    				  }
	 	    			   }else {
//	 	    				  System.out.print(rect);
//	 	    	    		  System.out.print("  /  ");
//	 	    	    		  System.out.print(el);
//	 	    	    		  System.out.print("  /  ");
//	 	    				  System.out.println(0+ " - INTERSECT");
	 	    				  
	 	    				  mergeTogether(rect,el);

	 	    				 
	 	    				 count++;
//	 	    				break outerloop;
	 	    			   }
	 	    		    
	 	    	   }
	 		   }
	 	    	
 		   
		  if(idx==0 || count == 0) {

			  filteredElems.add(rect);
		  }
	//	  Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));

 	   }else {
 		  //contours.remove(idx);
 	   }
        
   }
 Collections.sort(filteredElems,new Comparator<Rect>() {
    	
		public int compare(Rect o1, Rect o2) {
			   
			   int result = Double.compare(o1.x,o2.x);
		    if ( result == 0 ) {
		 	   result = Double.compare(o1.y,o2.y);
		    }
			   return result;
		}
		});
    List<Rect> startY = new ArrayList<Rect>();
    for(int idx = 0; idx < filteredElems.size(); idx++) {
    	Rect rect = filteredElems.get(idx);
    	
    	if(rect.x < 100 && rect.width > 30 && rect.height < 300) {
//			r1.width = (int)((mask.size().width)-r1.x-10);
  		  Imgproc.rectangle(mask2, rect.br() , new Point( (mask2.size().width)-rect.x-10 ,rect.br().y-rect.height),  new Scalar(0, 0, 255));
  		//Rect limits = new Rect();
  		//startY.add(rect.y);
  		
  		//Rect _rect = filteredElems.get(idx);
    	
    	//outerloop:
  		for(Rect obj :filteredElems) {
  			int diff_upper = Math.abs(rect.y - obj.y),diff_lower = Math.abs(obj.y -(rect.y+rect.height));
//				System.out.println(diff_lower);

  			if(obj.y >= rect.y && obj.y < rect.y+rect.height || diff_upper < 30 && diff_upper > 0) { //diff_upper < 30 && diff_upper > 0) {
  				//System.out.println(diff_upper);

//  				rect.y = obj.y;
//  				rect.height += diff_upper;.
  				
  				obj.y = rect.y;
  				obj.height += diff_upper;
  				//break outerloop;
  			}
    	}
//		  Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));

		 // Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));
			
		 // Mat img = new Mat(copyToReadData,rect);

		  
		  //doOcr(img,"img_sub_"+idx,new Size(500,60));
		  
		  

    }
    	
    	
    }
		for(Rect rect :filteredElems) {
			  Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));

		}
    
    
   
//    for(int idx = 0; idx < filteredElems.size(); idx++) {
//    	Rect rect = filteredElems.get(idx);
//    	
//    	outerloop:
//  		for(int _y :startY) {
//  			int diff = Math.abs(rect.y - _y);
//  			if(diff < 30 && diff > 0) {
//  			//	System.out.println(diff);
//  				rect.y = _y;
//  				rect.height += diff;
//  				break outerloop;
//  			}
//  		}
//
//		  Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));
//			
////		  Mat img = new Mat(copyToReadData,rect);
////
////		  
////		  doOcr(img,"img_sub_"+idx,new Size(500,60));
//		  
//		  
//
//    }
//    
    
    
Collections.sort(filteredElems,new Comparator<Rect>() {
    	
  		public int compare(Rect o1, Rect o2) {
  			   
  			   int result = Double.compare(o1.y,o2.y);
  		    if ( result == 0 ) {
  		 	   result = Double.compare(o1.x,o2.x);
  		    }
  			   return result;
  		}
  		});
//try(FileWriter fw = new FileWriter("N:\\Desktop\\resultsOCR.txt", true);
try(FileWriter fw = new FileWriter(fileToSaveImgs + "resultsOCR.txt", true);

	    BufferedWriter bwri = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bwri))

	{
		out.println("***************************"+fileName+"***************************");
		for(int idx = 0; idx < filteredElems.size(); idx++) {
			
			Rect rect = filteredElems.get(idx);
		
			 //Mat img = new Mat(copyToReadData,rect);
		
			  
			 // BufferedReader reader = new BufferedReader(new StringReader(doOcr(img,"img_sub_"+idx,new Size(500,60))));
			  
			//  reader.lines().forEach(line -> out.println(line));
			  
			  
		
		}
    
	} catch (IOException e) {
	    //exception handling left as an exercise for the reader
	}
    


	img_to_save = mask2;
	displayImage(mask2,"img_6","all contours ("+filteredElems.size()+")",largSize,false);
	//Imgcodecs.imwrite("N:\\Desktop\\imgContoursGreen.jpg",mask2);
	
	
	
	return filteredElems.size();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//
//    
//    
//    List<MatOfPoint> sortedByX =  new ArrayList<MatOfPoint>(contours);
//
//    
//	//sorting list of found counturs by X to find first element 	
//		Collections.sort(sortedByX,new Comparator<MatOfPoint>() {
//
//			public int compare(MatOfPoint m1, MatOfPoint m2) {
//				   Rect o1 = Imgproc.boundingRect(m1),
//						   o2 = Imgproc.boundingRect(m2);	
//
//				   
//				   int result = Double.compare(o1.x,o2.x);
//			    if ( result == 0 ) {
//			 	   result = Double.compare(o1.y,o2.y);
//			    }
//				   return result;
//			}
//			});
//		
////		Finding all first Elements and extending their width to width of image
//		List<Rect> firstElems =  new ArrayList<Rect>();
//		System.out.println("*********sortedByX*************");
//		for(int i = 0;i<sortedByX.size();i++) {
//			Rect r1 = Imgproc.boundingRect(sortedByX.get(i));
//			if(r1.x < 100 && r1.width > 30 && r1.height < 300) {
//				r1.width = (int)((mask.size().width)-r1.x-10);
//				//if(firstElems.size() == 0 ) {
//					firstElems.add(r1);
//				//}
//				for(int g = 0;g<firstElems.size();g++) {
//					Rect o3 = firstElems.get(g);
//					System.out.println(r1);
//
//					if( !r1.equals(o3) && o3.x < r1.x + r1.width && o3.x + o3.width > r1.x && o3.y < r1.y + r1.height && o3.y + o3.height > r1.y) {
////	    				System.out.println("________________INTERSECT_______________");
////						System.out.println(o1);
////						System.out.println(o3);
////						System.out.println("_________________________________________");
//	
//
//						if(r1.x < o3.x) {
//							int max = Math.max(o3.width,Math.max(r1.width,Math.abs(o3.x-(r1.x+r1.width))));
//							r1.width = max;
//
//							}else {
//								int max =Math.max(r1.width,Math.max(o3.width,Math.abs(r1.x-(o3.x+o3.width))));
//								r1.width = max;
//							
//							}
//	 	    				 
//	 	    				 if(r1.y < o3.y) {
//	 	    					r1.height =  Math.max(r1.height,Math.max(o3.height,Math.abs(r1.y-(o3.y+o3.height))));
//	 	    				 }else {
//	 	    					r1.height =  Math.max(o3.height,Math.max(r1.height,Math.abs(o3.y-(r1.y+r1.height))));
//
//	 	    				 }
//	 	    				 
//	 	    				r1.y = Math.min(r1.y,o3.y);
//	 	    				r1.x = Math.min(r1.x,o3.x);
//	 	    				
//	 	    				firstElems.remove(o3);
//	 	    				
//
//					}
//				}
//				
//
//			}
//
//		}
//		System.out.println("**********************************");
//
//	//Sorting First elements by Y
//		Collections.sort(firstElems,new Comparator<Rect>() {
//
//			public int compare(Rect o1, Rect o2) {
//				   
//				   int result = Double.compare(o1.y,o2.y);
//			    if ( result == 0 ) {
//			 	   result = Double.compare(o1.x,o2.x);
//			    }
//				   return result;
//			}
//			});
//	//Checking if there is a big gap between two elements
//	
//		try(FileWriter fw = new FileWriter("N:\\Desktop\\resultsOCR.txt", true);
//			    BufferedWriter bwri = new BufferedWriter(fw);
//			    PrintWriter out = new PrintWriter(bwri))
//
//			{
//			out.println("***************************"+fileName+"***************************");
//			
//			System.out.println("*********sortedByX_P2*************");
//
//			
//			for(int i = 0;i<firstElems.size();i++) {
//
//				Rect o1 = firstElems.get(i);
//				System.out.println(o1);
//				
//				
//
//			if(i+1<=firstElems.size()-1) {
//				Rect o2=firstElems.get(i+1);
//				
//				if(Math.abs((o1.y+o1.height)-o2.y)>30){
//
//					o1.height = o1.height+Math.abs((o1.y+o1.height)-o2.y)-10;
//		
//				}
//				
//			
//			}
//
//		
//			
//			
//			Rect o1CLONE = o1.clone();
//			int scale = 15;
//			if((o1CLONE.y + o1CLONE.height) < greyscale.size().height) {
//				o1CLONE.height = o1CLONE.height + (scale*2);
//			}
//			if(o1CLONE.y >15) {
//				o1CLONE.y = o1CLONE.y - scale;
//			}
//			System.out.println(copyToReadData.size());
//			System.out.println(o1CLONE);
//			Mat img = new Mat(copyToReadData,o1CLONE);
//
//			
//			Imgproc.rectangle(mask, o1.br() , new Point( o1.br().x-o1.width ,o1.br().y-o1.height),   new Scalar(0, 0, 255));
//			
//			displayImage(img,"img_sub_"+i,"img_"+i,new Size(500,60),true);
//			
//	        BufferedReader reader = new BufferedReader(new StringReader(doOcr(img,"img_sub_"+i,new Size(500,60))));
//
//	        reader.lines().forEach(line -> out.println(line));
//			
//
//		
//			
//			
//			}
//			System.out.println("**********************************");
//
//			} catch (IOException e) {
//			    //exception handling left as an exercise for the reader
//			}
//	
//		displayImage(mask,"img_6","Sorted Contours ("+firstElems.size()+")",largSize,false);
//		  
//		Imgcodecs.imwrite("N:\\Desktop\\imgContoursGreen.jpg",mask2);
//		Imgcodecs.imwrite("N:\\Desktop\\imgContoursBlue.jpg",mask);
//		
//____________________________________________________________________________________________________________________________________________________________
	}
/*	public int find_merge(Rect rect,List<Rect> elems) {
		int count=0;
		 for(Rect el : elems) {
   		   if(!el.equals(rect) && Math.abs((rect.x+rect.width)-el.x)<30 && Math.abs(rect.y - el.y)<20  
   			 || !el.equals(rect) && Math.abs((el.x+el.width)-rect.x)<30 && Math.abs(rect.y - el.y)<20 
				 || !el.equals(rect) && Math.abs((rect.y+rect.height)-el.y)<10 && Math.abs(rect.x - el.x)<(rect.width)
				 || !el.equals(rect) && Math.abs((el.y+el.height)-rect.y)<10 && Math.abs(rect.x - el.x)<(rect.width) 
				 ||  !el.equals(rect) && rect.x < el.x + el.width && rect.x + rect.width > el.x && rect.y < el.y + el.height && rect.y + rect.height > el.y
				  ) {
		   
		   
   				 if(el.x < rect.x) {
   					int max =Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));
   					el.width = max;

					}else {

						int max = Math.max(rect.width,Math.max(el.width,Math.abs(rect.x-(el.x+el.width))));

						el.width = max;
					
					} 		    	    		  
	    					 
					 if(el.y < rect.y) {
						 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
					 }else {
						 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));

					 }
					 
					 
					 el.y = Math.min(el.y,rect.y);
					 el.x = Math.min(el.x,rect.x);
   			
					 count++;
   	   }
	
		 }
		 return count;
	}
	
	public void process2(hashMap hm,String fileName,int erosion_size) {
		
    	long startTime = System.nanoTime();   

    	
    	
		compMap = hm;
		String inputFile = ".\\src\\img\\"+fileName;

	     Mat large = Imgcodecs.imread(inputFile),
    		 rgb = new Mat(),
    		 small =  new Mat(),
			 kernel =  new Mat(),
			 grad = new Mat(),
			 bw = new Mat(),
			 connected = new Mat(),
			 hierarchy  =  new Mat(),
			 rgb2 = new Mat();
	     List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		 List<Rect> elems = new ArrayList<Rect>();
		 Set<Rect> setUnsorted = new HashSet<Rect>();
		 List<Rect> dupl= new ArrayList<Rect>();


	    
	     Imgproc.pyrDown(large, rgb);
	     Imgproc.cvtColor(rgb,small, Imgproc.COLOR_BGR2GRAY);
	     displayImage(small,"img_1","grey",new Size(small.size().width,small.size().height),false);

	     Mat test = small.clone();

		 Imgproc.cvtColor(small,test,Imgproc.COLOR_GRAY2BGR);			 

	     rgb2 = test.clone();
	     
	     
	     
	      kernel =  Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3,3));
	     
	      Imgproc.morphologyEx(small, grad, Imgproc.MORPH_GRADIENT, kernel);
	      displayImage(grad,"img_2","morphologyEx",new Size(grad.size().width,grad.size().height),false);

	      Imgproc.threshold(grad, bw, 0.0, 255.0, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
	      displayImage(bw,"img_3","threshold",new Size(bw.size().width,bw.size().height),false);
	    		 
	      
	      
  		Mat erosion_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(erosion_size , erosion_size));
	        Imgproc.erode(bw, bw, erosion_element);

	        
	        
	      kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9,1));

	      Imgproc.morphologyEx(bw, connected, Imgproc.MORPH_CLOSE, kernel);
	      displayImage(connected,"img_4","morphologyEx_2",new Size(connected.size().width,connected.size().height),false);

	    		 	
	      Imgproc.findContours(connected, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
	    		 
	    		
		 
		 

	     
	     //first sort of found objects 
	 	 for(int idx = 0; idx < contours.size();idx++){
	 		  int count = 0;
	 	 	  Rect rect = Imgproc.boundingRect(contours.get(idx));
	 	 	  
		 	 if(rect.height > 20 && rect.height < 100 && rect.width < 400 && (rect.x+rect.width) < test.size().width) {
		 		 count = find_merge(rect,elems);
			 		
			 		 
		 		if(count==0 || idx == 0) {
		 			elems.add(rect);

		 		}
		 	 }
	 	 }
	 	 
	 	
	 	//second sort and merging even more 
	 	 for(Rect rect:elems) {
	 		 int count = find_merge(rect,elems);
	 		 if(rect.width > 40 && rect.height > 30) {
	 			setUnsorted.add(rect);
	 			
	 		 }
		 	 	Imgproc.rectangle(test, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,0,255));
	 		 
	 	 }
			displayImage(test,"img_5","all ("+contours.size()+")",new Size(test.size().width,test.size().height),false);
	 	
			//sorting by y and finding duplicates 
			List<Rect> setY = new ArrayList<Rect>(setUnsorted);
			 Collections.sort(setY,new Comparator<Rect>() {
			    	
					public int compare(Rect o1, Rect o2) {
		
						   int result = Double.compare(o1.y,o2.y);
					    if ( result == 0 ) {
					 	   result = Double.compare(o1.x,o2.x);
					 	   if(result == 0) {
					 		  dupl.add(o2);
					 	   }
					    }
						   return result;
					}
				});
			 //removing duplicates 
			 for(Rect del:dupl) {
				 setY.remove(del);
			 }
		
	//finding first object 		 
	List<Rect> firstObj = new ArrayList<Rect>();
	 for(Rect rect:setY) {
		if(rect.x < 100 ) {
			firstObj.add(rect);
		}
		
	 }
	 
	 //looking for large gaps between first objects 
	 for(int j = 0;j<firstObj.size();j++) {
		 Rect obj = firstObj.get(j);
		 if(j-1>=0 && j+1 <= firstObj.size()-1) {
			 Rect obj2 = firstObj.get(j+1),obj_1 = firstObj.get(j-1);
			 boolean top = Math.abs((obj_1.y+obj_1.height)-obj.y)>40,
					 bottom = Math.abs((obj.y+obj.height)-obj2.y)>40;
			 if(top && bottom){
				 obj.y = (obj_1.y+obj_1.height)+10;
				 obj.height = Math.abs(obj2.y-obj.y)-10;
			 }else if(top) {
				 obj.y = (obj_1.y+obj_1.height)+10;
				 obj.height += (obj_1.y+obj_1.height)-10;
			 }else if(bottom) {
				 obj.height = Math.abs(obj2.y-obj.y)-10;
				 }
		 }
		 
		 //draws red border 
		  Imgproc.rectangle(rgb2, new Point(obj.x,obj.y) , new Point( (rgb2.size().width)-obj.x-10 ,obj.y+obj.height),  new Scalar(0, 0, 255));

	 }
	 
	 //draws green border
	 for(Rect rect:setY) {
	 	 	Imgproc.rectangle(rgb2, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,255,0));

	 }
	 
		displayImage(rgb2,"img_6","filtered ("+setY.size()+")",new Size(rgb2.size().width,rgb2.size().height),false);

		
		//sorts by x
		List<Rect> setX = new ArrayList<Rect>(setY);
		 Collections.sort(setX,new Comparator<Rect>() {
		    	
				public int compare(Rect o1, Rect o2) {
	
					   int result = Double.compare(o1.x,o2.x);
				    if ( result == 0 ) {
				 	   result = Double.compare(o1.y,o2.y);
				    }
					   return result;
				}
			});
		
		 //goes throught first object and find object which fit between red border 
		 //to then read the text using OCR
		 for(Rect first:firstObj) {
//	 		 Nutri nutri = new Nutri();

			 int upper = first.y,lower = first.y+first.height;
				Mat img = new Mat(small,first);
				String res = doOcr(img);
	 			System.out.println(res);
	 			
			 for(Rect rect:setX) {
				 if(!first.equals(rect) && rect.y >= upper && rect.y <= lower
					 || !first.equals(rect) && Math.abs(rect.y - upper)<20) {
						 img = new Mat(small,rect);
				 	 	
			 	 		 res = doOcr(img);
			 	 		 System.out.println(res);
				 }
			 }
			 System.out.println("*************************************************");
		 }

		
		
		
		
		
		
		
		
		
		
		
		
	    Imgcodecs.imwrite("N:\\Desktop\\testPro2_rgb1.jpg",test);
		Imgcodecs.imwrite("N:\\Desktop\\testPro2_rgb2.jpg",rgb2);
	 
    	System.out.println("************* Elapsed time: " + ((double)(System.nanoTime() - startTime)/1000000000.0) + " seconds ************* ");

	}*/
	
	
	
	
	public Rect find_merge(Rect rect,List<Rect> elems) {
		int count=0;
		Rect elementR = new Rect();
		 for(Rect el : elems) {
   		   if(!el.equals(rect) && Math.abs((rect.x+rect.width)-el.x)<30 && Math.abs(rect.y - el.y)<20  
   			 || !el.equals(rect) && Math.abs((el.x+el.width)-rect.x)<30 && Math.abs(rect.y - el.y)<20 
//				 || !el.equals(rect) && Math.abs((rect.y+rect.height)-el.y)<10 && Math.abs(rect.x - el.x)<(rect.width)
//				 || !el.equals(rect) && Math.abs((el.y+el.height)-rect.y)<10 && Math.abs(rect.x - el.x)<(rect.width) 
				 ||  !el.equals(rect) && rect.x < el.x + el.width && rect.x + rect.width > el.x && rect.y < el.y + el.height && rect.y + rect.height > el.y
				  ) {
		   
		   
   				 if(el.x < rect.x) {
   					int max =Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));
   					el.width = max;

					}else {

						int max = Math.max(rect.width,Math.max(el.width,Math.abs(rect.x-(el.x+el.width))));

						el.width = max;
					
					} 		    	    		  
	    					 
					 if(el.y < rect.y) {
						 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
					 }else {
						 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));

					 }
					 
					 
					 el.y = Math.min(el.y,rect.y);
					 el.x = Math.min(el.x,rect.x);
   			
					 count++;
					 elementR = el;
					 //return el;

   		   }
	
		 }
		 if(count != 0) {
			 return elementR;
		 }else {
		 return null;
	}
	}
	
	
public void process2(hashMap hm,String fileName) {
		
    	long startTime = System.nanoTime();   

    	
    	
		compMap = hm;
//		String inputFile = ".\\src\\img\\"+fileName;
		String inputFile = "./src/img/"+fileName;

		String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);

		
//		System.out.println(erosion_size);
		
		int erosion_size = 2;
		
	     Mat large = Imgcodecs.imread(inputFile),
    		 rgb = new Mat(),
    		 small =  new Mat(),
			 kernel =  new Mat(),
			 grad = new Mat(),
			 bw = new Mat(),
			 connected = new Mat(),
			 hierarchy  =  new Mat(),
			 rgb2 = new Mat();
	     List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		 List<Rect> elems = new ArrayList<Rect>();
		 Set<Rect> setUnsorted = new HashSet<Rect>();
		 List<Rect> dupl= new ArrayList<Rect>();


	    
	     Imgproc.pyrDown(large, rgb);
	     Imgproc.cvtColor(rgb,small, Imgproc.COLOR_BGR2GRAY);
	     displayImage(small,"img_1","grey",new Size(small.size().width,small.size().height),false);

	     Mat test = small.clone();

		 Imgproc.cvtColor(small,test,Imgproc.COLOR_GRAY2BGR);			 

	     rgb2 = test.clone();
	     
	     
	     
	      kernel =  Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3,3));
	     
	      Imgproc.morphologyEx(small, grad, Imgproc.MORPH_GRADIENT, kernel);
	      displayImage(grad,"img_2","morphologyEx",new Size(grad.size().width,grad.size().height),false);

	      Imgproc.threshold(grad, bw, 0.0, 255.0, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
	      displayImage(bw,"img_3","threshold",new Size(bw.size().width,bw.size().height),false);
	    		 
	      
	      
  		Mat erosion_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(erosion_size , erosion_size));
	        Imgproc.erode(bw, bw, erosion_element);

	        
	        
	      kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9,1));

	      Imgproc.morphologyEx(bw, connected, Imgproc.MORPH_CLOSE, kernel);
	      displayImage(connected,"img_4","morphologyEx_2",new Size(connected.size().width,connected.size().height),false);

	    		 	
	      Imgproc.findContours(connected, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
	    		 
	    		
		 
		 

	     
	     //first sort of found objects 
	 	 for(int idx = 0; idx < contours.size();idx++){
	 		  int count = 0;
	 	 	  Rect rect = Imgproc.boundingRect(contours.get(idx));
	 	 	  
		 	 if(rect.height > 20 && rect.height < 100 && rect.width < 400 && rect.width > 10 && (rect.x+rect.width) < test.size().width) {
		 		Rect rectN = find_merge(rect,elems);
			 	
		 		if(rectN == null || idx == 0) {
		 			elems.add(rect);
		 		}else{
		 			do {
			 			 rectN = find_merge(rectN,elems);

			 		 }while(rectN != null);
			 		}
		 		
		 	 }
		 	 	Imgproc.rectangle(test, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,0,255));

		 	 
	 	 }
	 	 
		 Collections.sort(elems,new Comparator<Rect>() {
		    	
				public int compare(Rect o1, Rect o2) {
	
					   int result = Double.compare(o1.x,o2.x);
				    if ( result == 0 ) {
				 	   result = Double.compare(o1.y,o2.y);
				 	   if(result == 0) {
				 		  dupl.add(o2);
				 	   }
				    }
					   return result;
				}
			});
		 //removing duplicates 
		 for(Rect del:dupl) {
			 elems.remove(del);
		 }
		 
		 

		 List<List<Rect>> arr_elems = new ArrayList< List<Rect>>();

		 List<Rect> top_first = new ArrayList<Rect>();

		 
		 List<Rect> top_max = new ArrayList<Rect>();

 		 int countArr = 0;		 
	 	 for(int r = 0;r<elems.size();r++) {
	 		 Rect rect = elems.get(r);
	 		 if(r+1 <= elems.size()-1) {
		 		 Rect rect2 = elems.get(r+1);
		 		 if((rect2.x-rect.x)<=100) {
		 			 if(!arr_elems.isEmpty() && arr_elems.size()-1==countArr) {
		 				
		 				addToMainList( arr_elems, top_max, countArr, rect);

		 				
		 			 }else {
		 				 List<Rect> col = new ArrayList<Rect>();
		 				 col.add(rect);
		 				arr_elems.add(col);
		 			 }
		 		 }else {
		 			addToMainList( arr_elems, top_max, countArr, rect);
		 			 countArr++;
		 		 }

	 		 }else {
	 			addToMainList( arr_elems, top_max, countArr, rect);

	 		 }
	 		 
	 		 
	 		 
	 		 System.out.println(rect);
	 		 

		 		Imgproc.rectangle(rgb2, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,0,255));

	 	
	 	 }
	 	
	 	 
//	 		List<Text_Rect> text_rect_list = new ArrayList<Text_Rect>();
	 		

	 	
	 	
	 	
	 	
 			int idxList =0;

	 	for(int l = 0;l<arr_elems.size();l++) {
	 		List<Rect> list = arr_elems.get(l);
	 		 System.out.println("<_____________ List " +l+ "_____________>");
	 			Rect max_elem = top_max.get(l);
	 			
	 			//sort by y
	 			 Collections.sort(list,new Comparator<Rect>() {
	 		    	
	 				public int compare(Rect o1, Rect o2) {
	 	
	 					   int result = Double.compare(o1.y,o2.y);
	 				    if ( result == 0 ) {
	 				 	   result = Double.compare(o1.x,o2.x);
	 				 	   
	 				    }
	 					   return result;
	 				}
	 			});
	 		 for(Rect re:list) {
	 			 //extend each elem to max size 
				 if(!max_elem.equals(re)) {
					  re.width = max_elem.width+20;
					  re.x = max_elem.x-20;

				 }

//				 re.y -=10;
//				 re.height += 20;
//				re.width += 20;
//				re.x -=10;
//				 
				 
				 
				 Imgproc.rectangle(rgb2, re.br(),new Point( re.br().x-re.width ,re.br().y-re.height), new Scalar(0,255,0));
	 			
				 if(l==0) {
					 Imgproc.rectangle(rgb2, new Point(re.x,re.y) , new Point( (rgb2.size().width)-re.x-10 ,re.y+re.height),  new Scalar(255, 0, 0));
				 }
				 
//				 //do ocr
//				 Text_Rect newEl = new Text_Rect(re);
//				 
//			
//				 Mat img = new Mat(rgb,re);
//				 String text = doOcr(img); 
//		 		 System.out.println(text);
//
//				 
//				 newEl.setText(text);
//
//				 text_rect_list.add(newEl);
//				 
//				 
//		 	
//		 		Imgcodecs.imwrite("N:\\Desktop\\croped_ocr\\img_"+idxList+".jpg",img);
//		 		 idxList ++;
//		 		 

			 }
		 		//Imgproc.rectangle(rgb2, new Point(max_elem.x,max_elem.y),new Point( max_elem.x+max_elem.width ,rgb2.size().height-10), new Scalar(255,0,0));


		 }
	 	
//		 System.out.println("________________________________________________________________");
//		 try(FileWriter fw = new FileWriter("N:\\Desktop\\resultsOCR.txt", true);
//				    BufferedWriter bwri = new BufferedWriter(fw);
//				    PrintWriter out = new PrintWriter(bwri))
//	
//				{
//			 			
//			 		out.println("***************************"+fileName+"***************************");
//
//			 		for(Text_Rect re:text_rect_list) {
//						 String ocrBefore = re.text.replace("\n", "");
//						 String ocr_text = re.text.replaceAll("([^\\w.]+ \\(.*?\\) ?)|(\\(.*?\\) ?)|([^\\w. ]+)|((?<=\\d) +(?=\\d))", "");
////						 if(ocr_text.endsWith("9") || ocr_text.endsWith("8") || ocr_text.endsWith("0")) {
////							 ocr_text = ocr_text.substring(0,ocr_text.length()-1)+"g";
////						 };
//						 System.out.println(ocrBefore + " -> " +ocr_text);
//						 
//						 BufferedReader reader = new BufferedReader(new StringReader(ocr_text));
//						 
//						 reader.lines().forEach(line -> out.println(line));
//						 			
//						   
//						
//
//					 }
//	 	 
//
//			} catch (IOException e) {
//			    //exception handling left as an exercise for the reader
//			}


	 	 
			displayImage(test,"img_5","all ("+contours.size()+")",new Size(test.size().width,test.size().height),false);
			displayImage(rgb2,"img_6","filtered ("+elems.size()+")",new Size(rgb2.size().width,rgb2.size().height),false);

		
	    Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__allBoxes.jpg",test);
		Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__filteredBoxes.jpg",rgb2);
		
		
    	System.out.println("************* Elapsed time: " + ((double)(System.nanoTime() - startTime)/1000000000.0) + " seconds ************* ");

	}





public void addToMainList( List<List<Rect>> arr_elems,List<Rect> top_max,int countArr,Rect rect) {
	arr_elems.get(countArr).add(rect);
		
	 Rect max_elem = arr_elems.get(countArr).get(0).clone();
		 for(Rect re:arr_elems.get(countArr)) {
		 if(!max_elem.equals(re)) {
			 if((re.width+re.x)>(max_elem.width+max_elem.x)) {
				 max_elem.width = Math.abs(Math.min(re.x, max_elem.x) - Math.max((re.width+re.x),(max_elem.width+max_elem.x)));//re.width;
			 }
			 
			 max_elem.y = Math.min(max_elem.y, re.y);
			 
			 
		 }
		 }
		top_max.add(countArr, max_elem);
		
		
}










public void process3(hashMap hm,String fileName) {
	
	long startTime = System.nanoTime();   

	
	
	compMap = hm;
	String inputFile = "./src/img/"+fileName;
	String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);

	
	int erosion_size = 1;
	
     Mat large = Imgcodecs.imread(inputFile),
		 rgb = new Mat(),
		 small =  new Mat(),
				 hsv =  new Mat(),
						 smallOrg =  new Mat(),

		 kernel =  new Mat(),
		 grad = new Mat(),
		 bw = new Mat(),
		 connected = new Mat(),
		 hierarchy  =  new Mat(),
		 rgb2 = new Mat();
     List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	 List<Rect> elems = new ArrayList<Rect>();
	 Set<Rect> setUnsorted = new HashSet<Rect>();
	 List<Rect> dupl= new ArrayList<Rect>();

	 List<Rect> mergedEl = new ArrayList<Rect>();
	 List<Rect> rectCountours = new ArrayList<Rect>();

    
     Imgproc.pyrDown(large, rgb);
  		Mat test = rgb.clone();
  	
    rgb2 = test.clone();

    
    

     Imgproc.cvtColor(rgb,small, Imgproc.COLOR_BGR2GRAY);
     Imgproc.cvtColor(rgb,hsv, Imgproc.COLOR_BGR2HSV);
     
     
     
     
     
     
     
 		 
     
     Mat grayMat = new Mat();
     Mat cannyEdges = new Mat();
     Mat lines = new Mat();
     Mat blur_gray = new Mat();
    
     Mat blur = new Mat();
     Mat bin_img = new Mat();

     Imgproc.cvtColor(rgb, grayMat, Imgproc.COLOR_BGR2GRAY);
     Imgcodecs.imwrite(fileToSaveImgs + "0.1  "+fileNameWithOutExt + "__grayMat.jpg",grayMat);    

     
//     int kernel_size = 3;
//     Imgproc.GaussianBlur(grayMat, blur_gray, new Size(kernel_size, kernel_size), 0);
//     int low_threshold = 50;
//     int  high_threshold = 150;
//     Imgproc.Canny(blur_gray, cannyEdges, low_threshold, high_threshold);   
//     Imgcodecs.imwrite(fileToSaveImgs + "0.2  "+fileNameWithOutExt + "__cannyEdges.jpg",cannyEdges);       
//     int rho = 1;//  # distance resolution in pixels of the Hough grid
//	   double theta = Math.PI / 180;//  # angular resolution in radians of the Hough grid
//	   int threshold = 50;//  # minimum number of votes (intersections in Hough grid cell)
//	   int min_line_length = 50;//  # minimum number of pixels making up a line
//	   int max_line_gap = 10;//  # maximum gap in pixels between connectable line segments		 
//     Imgproc.HoughLinesP(cannyEdges, lines, rho, theta, threshold, min_line_length, max_line_gap);
//     Imgcodecs.imwrite(fileToSaveImgs + "0.3  "+fileNameWithOutExt + "__lines.jpg",lines);    
//     Mat houghLines = new Mat();
//     houghLines.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);
//     System.out.println("lines.cols(): " +lines.cols());
//     System.out.println("lines.rows(): " +lines.rows());
//     for (int i = 0; i < lines.rows(); i++) {
//         double[] points = lines.get(i, 0);
//         double x1, y1, x2, y2;
//
//         x1 = points[0];
//         y1 = points[1];
//         x2 = points[2];
//         y2 = points[3];
//
//         Point pt1 = new Point(x1, y1);
//         Point pt2 = new Point(x2, y2);
//       Imgproc.line(houghLines, pt1, pt2, new Scalar(255, 0, 0), 1);
//     }	    	 
//     Imgcodecs.imwrite(fileToSaveImgs + "0.4  "+fileNameWithOutExt + "__houghLines.jpg",houghLines);    

     
     
     
     
     
    		
     Imgproc.medianBlur(grayMat,blur, 5);
     Imgcodecs.imwrite(fileToSaveImgs + "0.2  "+fileNameWithOutExt + "__blur.jpg",blur);    

     Imgproc.adaptiveThreshold(blur, bin_img, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 11, 2);
     Imgcodecs.imwrite(fileToSaveImgs + "0.3  "+fileNameWithOutExt + "__bin_img.jpg",bin_img);    

     
     int rho = 1;//  # distance resolution in pixels of the Hough grid
	   double theta = Math.PI / 180;//  # angular resolution in radians of the Hough grid
	   int threshold = 50;//  # minimum number of votes (intersections in Hough grid cell)
	   int min_line_length = 50;//  # minimum number of pixels making up a line
	   int max_line_gap = 10;//  # maximum gap in pixels between connectable line segments		
//     Imgproc.HoughLines(bin_img,lines, 1, Math.PI / 180, 400);
     Imgproc.HoughLinesP(bin_img, lines, rho, theta, threshold, min_line_length, max_line_gap);
     //(bin_img,lines, 1, Math.PI / 180, 400);

//     Imgcodecs.imwrite(fileToSaveImgs + "0.4  "+fileNameWithOutExt + "__lines.jpg",lines);    


//     Mat houghLines = new Mat(grayMat.height(), grayMat.width(), CvType.CV_8UC4);
//     houghLines.create(grayMat.rows(), grayMat.cols(), CvType.CV_8UC4);
     
   System.out.println("lines.cols(): " +lines.cols());
   System.out.println("lines.rows(): " +lines.rows());
   
   int horizontal = 0;
   int vertical = 0;
   
//	 List<> horizontal_list = new ArrayList<Point>();
//	 List<Point> vertical_list = new ArrayList<Point>();
	 List<double[]> horizontal_list = new ArrayList<double[]>();
	 List<double[]> vertical_list = new ArrayList<double[]>();
   
     for (int i = 0; i < lines.rows(); i++) {
    	 double data[] = lines.get(i,0);

         double rho1 = data[0];
         double theta1 = data[1];
//        	System.out.println("data: "  +rho1 + "   "+theta1);

         double costheta = Math.cos(theta1);
         double sintheta = Math.sin(theta1);
         double x0 = costheta * rho1;
         double y0 = sintheta * rho1;
         Point pt1 = new Point(x0 + 10000 * (-sintheta), y0 + 10000 * costheta);
         Point pt2 = new Point(x0 - 10000 * (-sintheta), y0 - 10000 * costheta);

         
         int angle = (int) (Math.atan((pt1.y-pt2.y)/(pt2.x-pt1.x))*180/Math.PI);
         
         double angle_db = Math.atan((pt2.y-pt1.y)/(pt2.x-pt1.x))*180/Math.PI;

//         System.out.printf("dexp: %.0f\n", dexp);

         double[] points = new double[4];
		 points[0] = pt1.x;
		 points[1] = pt1.y;
		 points[2] = pt2.x;
		 points[3] = pt2.y;

         
         if( theta1>Math.PI/180*170 || theta1<Math.PI/180*10){ 
          	System.out.printf("vertical: %f\n",angle_db);
	   		 System.out.printf("x1: %f\n",points[0]);
	   		 System.out.printf("y1: %f\n",points[1]);
	   		 System.out.printf("x2: %f\n",points[2]);
	   		 System.out.printf("y2: %f\n\n",points[3]);

        	// vertical
        		 Imgproc.line(rgb, pt1, pt2, new Scalar(0, 0, 255), 2);
             	vertical++;
             	vertical_list.add(points);

         }
         
         if( theta1>Math.PI/180*80 && theta1<Math.PI/180*100) {


        	 //horizontal
//        	 if(angle_db >= 0 && angle_db < 1) {
        		 Imgproc.line(rgb, pt1, pt2, new Scalar(0, 255, 0), 2);
        		 System.out.printf("horizontal: %f\n",angle_db);
        		 System.out.printf("x1: %f\n",points[0]);
        		 System.out.printf("y1: %f\n",points[1]);
        		 System.out.printf("x2: %f\n",points[2]);
        		 System.out.printf("y2: %f\n\n",points[3]);
        		 horizontal++;
        		 horizontal_list.add(points);
//        	 }
         }
         
         


         
         

     }
     
     
     
     System.out.println("vertical: " +vertical);
     System.out.println("horizontal: " +horizontal);
     
     
     int intersection = 0;
     
     for (double[] hor : horizontal_list) {    
		for (double[] ver : vertical_list) {
			
			double x1 = hor[0];
			double x2 = hor[2];
			double y1 = hor[1];
			double y2 = hor[3];
			
			double x3 = ver[0];
			double x4 = ver[2];
			double y3 = ver[1];
			double y4 = ver[3];
			
			double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
    	    if (d == 0) {
//       	     System.out.println("intersection: " +new Point(xi,yi));
    	    }else {

    	    double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
    	    double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
    	    
    	    
//    	     System.out.println("intersection: " +new Point(xi,yi));
    	     Imgproc.drawMarker(rgb, new Point(xi,yi), new Scalar(255, 0, 0), Imgproc.MARKER_CROSS, 10, 1,1);
    	     intersection++;
    	    }
		}
     }
     
     System.out.println("intersections: " +intersection);

     
     
     
//     public Point intersection(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
//    	    int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
//    	    if (d == 0) return null;
//
//    	    int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
//    	    int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
//
//    	    return new Point(xi,yi);
//    	}
     
     
     
   Imgcodecs.imwrite(fileToSaveImgs + "0.4  "+fileNameWithOutExt + "__houghLines.jpg",rgb);    

     
     
     
     Imgcodecs.imwrite(fileToSaveImgs + "1.  "+fileNameWithOutExt + "__COLOR_BGR2HSV.jpg",hsv);    
     List<Mat> lRgb = new ArrayList<Mat>(3);
     Core.split(hsv, lRgb);
     Mat mR = lRgb.get(0);
     Mat mG = lRgb.get(1);
     Mat mB = lRgb.get(2);
       
     Imgcodecs.imwrite(fileToSaveImgs + "1.1.  "+fileNameWithOutExt + "__mR.jpg",mR);
     Imgcodecs.imwrite(fileToSaveImgs + "1.2.  "+fileNameWithOutExt + "__mG.jpg",mG);
     Imgcodecs.imwrite(fileToSaveImgs + "1.3.  "+fileNameWithOutExt + "__mB.jpg",mB);

     Mat  greeeeey = new Mat();
     Imgproc.cvtColor(rgb,greeeeey, Imgproc.COLOR_BGR2GRAY);
     Imgcodecs.imwrite(fileToSaveImgs + "1.4.  "+fileNameWithOutExt + "__gray.jpg",greeeeey);
     
     
     
     
     
     
     Mat  lab = new Mat();
     Imgproc.cvtColor(rgb,lab, Imgproc.COLOR_BGR2Lab);
     Imgcodecs.imwrite(fileToSaveImgs + "1.5.  "+fileNameWithOutExt + "__lab.jpg",lab);

     List<Mat> ch_lab = new ArrayList<Mat>(3);
     Core.split(lab, ch_lab);
     Mat ch_l = ch_lab.get(0);
     Mat ch_a = ch_lab.get(1);
     Mat ch_b = ch_lab.get(2);
     
     
     Imgcodecs.imwrite(fileToSaveImgs + "1.6.  "+fileNameWithOutExt + "__ch_l.jpg",ch_l);
     Imgcodecs.imwrite(fileToSaveImgs + "1.7.  "+fileNameWithOutExt + "__ch_a.jpg",ch_a);
     Imgcodecs.imwrite(fileToSaveImgs + "1.8.  "+fileNameWithOutExt + "__ch_b.jpg",ch_b);

   
     
//     Mat  exp = new Mat();
     
     
//     Imgproc.cvtColor(exp,lab, Imgproc.COLOR_Lab2);

     
//     imshow("HSV to gray", hsv_channels[2]);
//
//     imshow("BGR", im);
//     cvtColor(im, im, CV_BGR2GRAY);
//     imshow("BGR to gray", im);

     
     
//     Imgproc.cvtColor(hsv,small, Imgproc.COLOR_BGR2GRAY);

//     small = ch_a.clone();
     
     displayImage(small,"img_1","grey",new Size(small.size().width,small.size().height),false);
     Imgcodecs.imwrite(fileToSaveImgs + "2.  "+fileNameWithOutExt + "__grey.jpg",small);

     
     
      kernel =  Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3,3));
     
      Imgproc.morphologyEx(small, grad, Imgproc.MORPH_GRADIENT, kernel);
      displayImage(grad,"img_2","morphologyEx",new Size(grad.size().width,grad.size().height),false);
      Imgcodecs.imwrite(fileToSaveImgs + "3.  "+fileNameWithOutExt + "__morphologyEx.jpg",grad);


      Imgproc.threshold(grad, bw, 0.0, 255.0, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
      displayImage(bw,"img_3","threshold",new Size(bw.size().width,bw.size().height),false);
      Imgcodecs.imwrite(fileToSaveImgs + "4.  "+fileNameWithOutExt + "__threshold.jpg",bw);

//      bw = grad.clone();
      
		Mat erosion_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(erosion_size , erosion_size));
        Imgproc.erode(bw, bw, erosion_element);

        
        
      kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9,1));

      Imgproc.morphologyEx(bw, connected, Imgproc.MORPH_CLOSE, kernel);
      displayImage(connected,"img_4","morphologyEx_2",new Size(connected.size().width,connected.size().height),false);
      Imgcodecs.imwrite(fileToSaveImgs + "5.  "+fileNameWithOutExt + "__morphologyEx_2.jpg",connected);

    	
      
      
      
      Imgproc.findContours(connected, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
      
   	 for(int x_ = 0; x_ < test.width();x_+=10){
	 
	 	 	Imgproc.line(test, new Point(x_,0),new Point(x_,test.height()), new Scalar(0,0,0),1);//new Scalar(0,0,255)
	 	 	Imgproc.line(rgb2, new Point(x_,0),new Point(x_,test.height()), new Scalar(0,0,0),1);//new Scalar(0,0,255)

   	 }
   	 
   	 for(int y_ = 0; y_ < test.height();y_+=10){
   		 
	 	 	Imgproc.line(test, new Point(0,y_),new Point(test.width(),y_), new Scalar(0,0,0),1);//new Scalar(0,0,255)
	 	 	Imgproc.line(rgb2, new Point(0,y_),new Point(test.width(),y_), new Scalar(0,0,0),1);//new Scalar(0,0,255)

   	 }
   	 
   	 
   	 
 	 Collections.sort(contours,new Comparator<MatOfPoint>() {
		public int compare(MatOfPoint m1, MatOfPoint m2) {
	 	 	  Rect o1 = Imgproc.boundingRect(m1);
	 	 	  Rect o2 = Imgproc.boundingRect(m2);
			   int result = Double.compare(o1.y,o2.y);
			   return result;
		}
	});
 	 
 	 for(int idx = 0; idx < contours.size();idx++){
// 	 	 for(int idx = 0; idx < 200;idx++){
 	 		 Rect rect = Imgproc.boundingRect(contours.get(idx));
 	 		if(rect.width < 400) {
 	 			rectCountours.add(rect);
 	 		
// 	 			Rect rect = Imgproc.boundingRect(contours.get(idx));
 	 			Imgproc.rectangle(test, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,0,255),1);//new Scalar(0,0,255)

 	 			
// 	 			Imgproc.rectangle(test, new Point(rect.x,rect.y),new Point(rect.x+rect.width,rect.y+rect.height), new Scalar(255,0,0));//new Scalar(0,0,255)

 	 			
 	 		}
 	 }
	 
 	 
 	 
//		Imgproc.rectangle(rgb2, new Point( 50,60),new Point( 70,90), new Scalar(255,0,0));//new Scalar(0,0,255)

		
		
// 		 Collections.sort(rectCountours,new Comparator<Rect>() {
// 			public int compare(Rect m1, Rect m2) {
// 		 	 	  Rect o1 = m1;//Imgproc.boundingRect(m1);
// 		 	 	  Rect o2 = m2;//Imgproc.boundingRect(m2);
// 				   int result = Double.compare(o1.y,o2.y);
// 				   return result;
// 			}
// 		});

     
     //first sort of found objects 
// 	 for(int idx = 0; idx < contours.size();idx++){
//	 Rect rect = Imgproc.boundingRect(contours.get(counter));
// 	  
//// 	  System.out.println("test - x: " + rect.x +", y: " +rect.y + ", h: " + rect.height +", w: " + rect.width);
//
 	  
// 	 	int R = (int)(Math.random()*256);
// 	 	int G = (int)(Math.random()*256);
// 	 	int B= (int)(Math.random()*256);
// 	 	
//
// 	 	Scalar colour = new Scalar(R,G,B);
 	  
//	 	Imgproc.rectangle(test, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), colour);//new Scalar(0,0,255)

// 	 
 	 int counter = 0;
 	 int maxCounter = rectCountours.size();
  	System.out.println("countours: " +rectCountours.size());

 	 while(counter < maxCounter) {
 		 Rect rect = rectCountours.get(counter);//rect_it.next();//Imgproc.boundingRect(rectMatOfPoint); 		
// 		 if(rect.width < 400) {
 			 for (Iterator<Rect> rect_2_it = rectCountours.iterator(); rect_2_it.hasNext();) {
 		
 				 Rect rect_2 = rect_2_it.next();//Imgproc.boundingRect(rectMatOfPoint2);
// 				 if(rect_2.width < 400) {
 					 if(rect.x != rect_2.x && rect.y != rect_2.y && rect.width != rect_2.width && rect.height != rect_2.height) {
 						 int enlargeX = 0;//1;//5; //(int) Math.round(rect.width*0.1);
 						 int enlargeY = 0;//1;//5; //(int) Math.round(rect.height*0.1);

// 						 Rectangle rect1 = new Rectangle((rect.x - (enlargeX/2)), rect.y, (rect.width + (enlargeX)), (rect.height + (enlargeY)));
 						 Rectangle rect1 = new Rectangle((rect.x - enlargeX), rect.y - enlargeY, (rect.width + (rect.width*enlargeX)), (rect.height + (rect.height*enlargeY)));

 						
// 				 	 	int R = (int)(Math.random()*256);
// 				 	 	int G = (int)(Math.random()*256);
// 				 	 	int B= (int)(Math.random()*256);
// 				 	 	Scalar colour = new Scalar(R,G,B);
 				 	 	
// 				 	 	Imgproc.rectangle(test, new Point(rect1.x,rect1.y),new Point((rect1.x + rect1.width), (rect1.y + rect1.height)), new Scalar(255,0,0));//new Scalar(0,0,255)

 					 	
 					 	
 					 	
 						 Rectangle rect2 = new Rectangle(rect_2.x, rect_2.y, rect_2.width, rect_2.height);
// 						 if(rect1.intersects(rect2)) {
					 	if(rect1.x < (rect2.x + rect2.width) && (rect1.x + rect1.width) > rect2.x && rect.y < (rect2.y + rect2.height) && (rect1.y + rect1.height) > rect2.y //||
//							Math.abs(rect1.y - (rect2.y + rect2.height)) <= 5 && rect1.x >= rect2.x && rect1.x <= (rect2.x +rect.width)
							
//							Math.abs(rect1.x - (rect2.x + rect2.width)) <= 5

					 		) {
 							
//					 		System.out.println("*** INTERSECTION ***");
//			 	  	 	 	  System.out.println("rect1 - x: " + rect1.x +", y: " +rect1.y + ", h: " + rect1.height +", w: " + rect1.width);
//			 	 	 	 	  System.out.println("rect2 - x: " + rect2.x +", y: " +rect2.y + ", h: " + rect2.height +", w: " + rect2.width);
//			 	  	 	 	  System.out.println("*****************************************");
//			
//	  
	  						List<Integer> listX = Arrays.asList(rect1.x, (rect1.x+rect1.width) ,rect2.x,(rect2.x +rect2.width));//new ArrayList<Double>();
 							 List<Integer> listY = Arrays.asList(rect1.y, (rect1.y+rect1.height) ,rect2.y,(rect2.y +rect2.height));//new ArrayList<Double>();
  
							//MIN NUMBER
							Collections.sort(listX);
							int minX = listX.get(0);
							
							Collections.sort(listY);
							int minY =  listY.get(0);
							
							//MAX NUMBER
							Collections.sort(listX);
							Collections.reverse(listX);
							int maxX = listX.get(0);
							
							Collections.sort(listY);
							Collections.reverse(listY);
							int maxY =  listY.get(0);
							
							rect.x = minX;
							rect.y = minY;
							rect.width = maxX - minX;
							rect.height = maxY - minY;
							
							rect_2_it.remove();
							  			
							counter = 0;
							maxCounter = rectCountours.size();

 						 }
//					 	else {
////	 	if(rect1.x < (rect2.x + rect2.width) && (rect1.x + rect1.width) > rect2.x && rect.y < (rect2.y + rect2.height) && (rect1.y + rect1.height) > rect2.y) {
//
//					 	if(Math.abs(rect1.y - (rect2.y + rect2.height)) <= 10 && Math.abs(rect1.x - (rect2.x + rect2.width)) <= 50) {
////					 		
////	
//					 	System.out.println("*** on top ***");
//		 	  	 	 	  System.out.println("(rect1.y - (rect2.y + rect2.height)): " + Math.abs(rect1.y - (rect2.y + rect2.height)));
//		 	  	 	 	  System.out.println("(rect1.x - (rect2.x + rect2.width)): " + Math.abs(rect1.x - (rect2.x + rect2.width)));
//
//		 	  	 	 	  System.out.println("rect1 - x: " + rect1.x +", y: " +rect1.y + ", h: " + rect1.height +", w: " + rect1.width);
//		 	 	 	 	  System.out.println("rect2 - x: " + rect2.x +", y: " +rect2.y + ", h: " + rect2.height +", w: " + rect2.width);
//		 	  	 	 	  System.out.println("*****************************************");		 	  	 	 	  
// 						 
//					 	
//					 	
//					 	}
//					 	counter++;
// 						 }
 					 }		  	 	
// 				 }else {
// 					 rect_2_it.remove();
// 				 }
 			 }
 			 counter++;
// 		 }else {
// 			 rectCountours.remove(counter);
// 		 }
 		 counter++;
 	 }
 	 
 	 
  	System.out.println("rectCountours: " +rectCountours.size());

	 	  for (int cou = 0; cou < rectCountours.size(); cou++) {
	 	 		Rect rect = rectCountours.get(cou);//Imgproc.boundingRect(rectMatOfPoint2);

 		 	Imgproc.rectangle(rgb2, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,255,0),1);//new Scalar(0,0,255)

	 	  }
// 	 	 	 	  for (int idx2 = 0; idx2 < 100; idx2++) {
// 	 	 	 		  if(idx != idx2) {
//		
//		 	 	 	 	  
//		 	 	 	 	  
//		 	  	 	 	Rectangle rect1 = new Rectangle(rect.x, rect.y, rect.width, rect.height);
//		 	  	 	 	Rectangle rect2 = new Rectangle(rect_2.x, rect_2.y, rect_2.width, rect_2.height);
//		 	  	 	 	Rectangle intersection = rect1.intersection(rect2);
//		 	  	 	
//		 	  	 	
//		
//		 	  	 	 	if(!intersection.isEmpty()) {
//		 	  		 	 	Imgproc.rectangle(rgb2, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,255,0));//new Scalar(0,0,255)
//		 	  		 	 	Imgproc.rectangle(rgb2, rect_2.br(),new Point( rect_2.br().x-rect_2.width ,rect_2.br().y-rect_2.height), new Scalar(0,255,0));//new Scalar(0,0,255)
//
//		 	  	 	 	  System.out.println("*** INTERSECTION ***");
//		 	  	 	 	  System.out.println(idx + ". rect1 - x: " + rect1.x +", y: " +rect1.y + ", h: " + rect1.height +", w: " + rect1.width);
//		 	 	 	 	  System.out.println(idx2 + ". rect2 - x: " + rect2.x +", y: " +rect2.y + ", h: " + rect2.height +", w: " + rect2.width);
//		 	  	 	 	  System.out.println("*****************************************");
//		
//		 	  	 	 	  
//		 	  	 	 	  
//		 	  	 	 	List<Integer> listX = Arrays.asList(rect1.x, (rect1.x+rect1.width) ,rect2.x,(rect2.x +rect2.width));//new ArrayList<Double>();
//		 	  	 	 	List<Integer> listY = Arrays.asList(rect1.y, (rect1.y+rect1.height) ,rect2.y,(rect2.y +rect2.height));//new ArrayList<Double>();
//
////		 	  	 	 	  Math.min(a, b);
////		 	  	 	 	  Math.max(a, b)
//
//
//		 	  	 	 	  
//		 	  	 	 	//MIN NUMBER
//		 	  	 	 	Collections.sort(listX);
//		 	  	 	 	int minX = listX.get(0);
//		 	  	 	 	
//		 	  	 	 	Collections.sort(listY);
//		 	  	 	 	int minY =  listY.get(0);
//
//		 	  	 	 	//MAX NUMBER
//		 	  	 	 	Collections.sort(listX);
//		 	  	 	 	Collections.reverse(listX);
//		 	  	 	 	int maxX = listX.get(0);
//		 	  	 	 	
//		 	  	 	 	Collections.sort(listY);
//		 	  	 	 	Collections.reverse(listY);
//		 	  	 	 	int maxY =  listY.get(0);
//		 	  	 	 	
////		 	  	 	 	tutaj
//		 	  			Rect newMergeEl = new Rect();
//		 	  			newMergeEl.x = minX;
//		 	  			newMergeEl.y = minY;
//		 	  			newMergeEl.width = maxX - minX;
//		 	  			newMergeEl.height = maxY - minY;
//
//		 	  			mergedEl.add(newMergeEl);
//		 	  	 	 	}else {
//		 	  	 	 		
//		 	  	 	 	}
//				}
// 	 	 	  }
//
//	 	 }	
//	 	 	
// 	 }

  	 
  

//  	 for (int i = 0; i < mergedEl_2.size(); i++) {
//  	  	 System.out.println(i+". " + mergedEl_2.get(i));
//
//	 	 Imgproc.rectangle(rgb2, mergedEl_2.get(i).br(),new Point( mergedEl_2.get(i).br().x-mergedEl_2.get(i).width ,mergedEl_2.get(i).br().y-mergedEl_2.get(i).height), new Scalar(0,0,255));//new Scalar(0,0,255)
//
//	 	 
//	 	 
//	 	 
//	 	 
//	 	 
//	 	 
//	 	 
//  	 }
  	 
  	 
  	 
  	 
  	 
  	 
//	 Collections.sort(elems,new Comparator<Rect>() {
//	    	
//			public int compare(Rect o1, Rect o2) {
//
//				   int result = Double.compare(o1.x,o2.x);
//			    if ( result == 0 ) {
//			 	   result = Double.compare(o1.y,o2.y);
//			 	   if(result == 0) {
//			 		  dupl.add(o2);
//			 	   }
//			    }
//				   return result;
//			}
//		});
//	 //removing duplicates 
//	 for(Rect del:dupl) {
//		 elems.remove(del);
//	 }
//	 
//	 
//
//	 List<List<Rect>> arr_elems = new ArrayList< List<Rect>>();
//
//	 List<Rect> top_first = new ArrayList<Rect>();
//
//	 
//	 List<Rect> top_max = new ArrayList<Rect>();
//
//		 int countArr = 0;		 
// 	 for(int r = 0;r<elems.size();r++) {
// 		 Rect rect = elems.get(r);
// 		 if(r+1 <= elems.size()-1) {
//	 		 Rect rect2 = elems.get(r+1);
//	 		 if((rect2.x-rect.x)<=100) {
//	 			 if(!arr_elems.isEmpty() && arr_elems.size()-1==countArr) {
//	 				
//	 				addToMainList( arr_elems, top_max, countArr, rect);
//
//	 				
//	 			 }else {
//	 				 List<Rect> col = new ArrayList<Rect>();
//	 				 col.add(rect);
//	 				arr_elems.add(col);
//	 			 }
//	 		 }else {
//	 			addToMainList( arr_elems, top_max, countArr, rect);
//	 			 countArr++;
//	 		 }
//
// 		 }else {
// 			addToMainList( arr_elems, top_max, countArr, rect);
//
// 		 }
// 		 
// 		 
// 		 
// 		 System.out.println(rect);
// 		 
//
//	 		Imgproc.rectangle(rgb2, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,0,255));
//
// 	
// 	 }
// 	
// 	 
// 		List<Text_Rect> text_rect_list = new ArrayList<Text_Rect>();
// 		
//
// 	
// 
// 	for(int l = 0;l<arr_elems.size();l++) {
// 		List<Rect> list = arr_elems.get(l);
// 		 System.out.println("<_____________ List " +l+ "_____________>");
// 			Rect max_elem = top_max.get(l);
// 			
// 			//sort by y
// 			 Collections.sort(list,new Comparator<Rect>() {
// 		    	
// 				public int compare(Rect o1, Rect o2) {
// 	
// 					   int result = Double.compare(o1.y,o2.y);
// 				    if ( result == 0 ) {
// 				 	   result = Double.compare(o1.x,o2.x);
// 				 	   
// 				    }
// 					   return result;
// 				}
// 			});
// 		 for(Rect re:list) {
// 			 //extend each elem to max size 
//			 if(!max_elem.equals(re)) {
//				  re.width = max_elem.width+20;
//				  re.x = max_elem.x-20;
//
//			 }
//		 
//			 
//			 Imgproc.rectangle(rgb2, re.br(),new Point( re.br().x-re.width ,re.br().y-re.height), new Scalar(0,255,0));
// 			
//			 if(l==0) {
//				 Imgproc.rectangle(rgb2, new Point(re.x,re.y) , new Point( (rgb2.size().width)-re.x-10 ,re.y+re.height),  new Scalar(255, 0, 0));
//			 }		 
//
//		 }
//	 }
 	
 	 
		displayImage(test,"img_5","all ("+contours.size()+")",new Size(test.size().width,test.size().height),false);
		displayImage(rgb2,"img_6","filtered ("+elems.size()+")",new Size(rgb2.size().width,rgb2.size().height),false);

	
    Imgcodecs.imwrite(fileToSaveImgs + "6."+fileNameWithOutExt + "__allBoxes.jpg",test);
	Imgcodecs.imwrite(fileToSaveImgs +  "7."+fileNameWithOutExt + "__filteredBoxes.jpg",rgb2);
	
	
	System.out.println("************* Elapsed time: " + ((double)(System.nanoTime() - startTime)/1000000000.0) + " seconds ************* ");

}













boolean contains(List<Integer> list, int name) {
    for (Integer item : list) {
        if (item.equals(name)) {
            return true;
        }
    }
    return false;
}


 boolean contains(List<Double> list, double name) {
	    for (Double item : list) {
	        if (item.equals(name)) {
	            return true;
	        }
	    }
	    return false;
}

 
 
 
 private Mat preProcessForAngleDetection(Mat image) {
     Mat binary = new Mat();
     //Create binary image
     Imgproc.threshold(image, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
     //"Connect" the letters and words
     Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(20, 1));
     Imgproc.morphologyEx(binary, binary, Imgproc.MORPH_CLOSE, kernel);
     //Convert the image to gray from RGB
     Imgproc.cvtColor(binary, binary, Imgproc.COLOR_BGR2GRAY);
//     Imgcodecs.imwrite("processedImage.jpg", binary);
     return binary;
 }

 //With this we can detect the rotation angle
 //After this function returns we will know the necessary angle
 private double detectRotationAngle(Mat binaryImage) {
     //Store line detections here
     Mat lines = new Mat();
     //Detect lines
     Imgproc.HoughLinesP(binaryImage, lines, 1, Math.PI / 180, 100);

     double angle = 0;

     //This is only for debugging and to visualise the process of the straightening
     Mat debugImage = binaryImage.clone();
     Imgproc.cvtColor(debugImage, debugImage, Imgproc.COLOR_GRAY2BGR);

     //Calculate the start and end point and the angle
     for (int x = 0; x < lines.cols(); x++) {
         double[] vec = lines.get(0, x);
         double x1 = vec[0];
         double y1 = vec[1];
         double x2 = vec[2];
         double y2 = vec[3];

         Point start = new Point(x1, y1);
         Point end = new Point(x2, y2);

         //Draw line on the "debug" image for visualization
         Imgproc.line(debugImage, start, end, new Scalar(255, 255, 0), 5);

         //Calculate the angle we need
         angle = calculateAngleFromPoints(start, end);
     }

//     Imgcodecs.imwrite("detectedLines.jpg", debugImage);

     return angle;
 }

 //From an end point and from a start point we can calculate the angle
 private double calculateAngleFromPoints(Point start, Point end) {
     double deltaX = end.x - start.x;
     double deltaY = end.y - start.y;
     return Math.atan2(deltaY, deltaX) * (180 / Math.PI);
 }

 //Rotation is done here
 private Mat rotateImage(Mat image, double angle) {
     //Calculate image center
     Point imgCenter = new Point(image.cols() / 2, image.rows() / 2);
     //Get the rotation matrix
     Mat rotMtx = Imgproc.getRotationMatrix2D(imgCenter, angle, 1.0);
     //Calculate the bounding box for the new image after the rotation (without this it would be cropped)
     Rect bbox = new RotatedRect(imgCenter, image.size(), angle).boundingRect();

     //Rotate the image
     Mat rotatedImage = image.clone();
     Imgproc.warpAffine(image, rotatedImage, rotMtx, bbox.size());

     return rotatedImage;
 }

 //Sums the whole process and returns with the straight image
// private Mat straightenImage(Mat image) {
//     Mat rotatedImage = image.clone();
//     Mat processed = preProcessForAngleDetection(image);
//     double rotationAngle = detectRotationAngle(processed);
//
//     return rotateImage(rotatedImage, rotationAngle);
// }
 
 
 
 
 public void showWaitDestroy(String winname, Mat img) {

		String fileNameWithOutExt = FilenameUtils.removeExtension(fileName_);	

	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__"+winname+".jpg",img);

	}
 
 public String fileName_ = "";
public void process4(hashMap hm,String fileName) {
	
	long startTime = System.nanoTime();   

	
	
	
	fileName_ = fileName;
	
	
	
	String inputFile = "./src/img/"+fileName;

	String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);	

    Mat src = Imgcodecs.imread(inputFile);//Imgcodecs.imread(args[0]);

    Imgproc.pyrDown(src, src);

    
    
    
//    Mat straightImage = straightenImage(src);
    
	  

    
    
    
    // Transform source image to gray if it is not already
    Mat gray = new Mat();

//    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
    
    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

    
    
    
    
    Mat blur = new Mat();
    		
    Imgproc.blur(gray, blur,new Size(5, 5)); //With kernel size depending upon image size
    double mean_blur = Core.mean(blur).val[0];
    System.out.println("mean_blur: " +mean_blur);
    String light_dark = "";
    boolean darkBackround = false;
    if(mean_blur > 120) {
    	light_dark = "light";
    	darkBackround = false;
    }else {
    	light_dark = "dark";
    	darkBackround = true;

    }
    
    System.out.println("light_dark: " +light_dark);

//    		if cv2.mean(blur) > 127:  # The range for a pixel's value in grayscale is (0-255), 127 lies midway
//    		    return 'light' # (127 - 255) denotes light image
//    		else:
//    		    return 'dark' #
    		    		
    		    		
    		    		
    		    		
    showWaitDestroy("gray" , gray);
    Mat bw_2 = new Mat();
    Core.bitwise_not(gray, gray);
    showWaitDestroy("bitwise_not" , gray);

 
    // Show binary image
    Imgproc.threshold(gray, bw_2, 0.0, 255.0, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
    showWaitDestroy("binary" , bw_2);

    
    if(darkBackround) {
    	Core.bitwise_not(bw_2, bw_2);
    	showWaitDestroy("bitwise_not_2" , bw_2);
	}
    
    // Create the images that will use to extract the horizontal and vertical lines
    Mat horizontal = bw_2.clone();
    Mat vertical = bw_2.clone();
    // Specify size on horizontal axis
    
    int horizontal_size = horizontal.cols() / 15;
    // Create structure element for extracting horizontal lines through morphology operations
    Mat horizontalStructure = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(horizontal_size,1));
    // Apply morphology operations
    Imgproc.erode(horizontal, horizontal, horizontalStructure);
    Imgproc.dilate(horizontal, horizontal, horizontalStructure);
    // Show extracted horizontal lines
    showWaitDestroy("horizontal" , horizontal);
    
    // Specify size on vertical axis
    int vertical_size = vertical.rows() / 5;
    // Create structure element for extracting vertical lines through morphology operations
    Mat verticalStructure = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,vertical_size));
    // Apply morphology operations
    Imgproc.erode(vertical, vertical, verticalStructure);
    Imgproc.dilate(vertical, vertical, verticalStructure);
    // Show extracted vertical lines
    showWaitDestroy("vertical", vertical);

    		
    Mat testing = new Mat();

    
    Core.subtract(bw_2, horizontal, testing);

    Core.subtract(testing, vertical, testing);

	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__testing.jpg",testing);

    
//    
//   Mat lines_removed = new Mat();
////   Mat vertica/l_horizontal = new Mat();
//
//    
////    Core.add(vertical, horizontal, vertical_horizontal);
////  Core.bitwise_not(gray, gray);
//
//    
//    
//    
////    Mat vertical_horizontalStructure = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,vertical_size));
////
////    Imgproc.dilate(vertical_horizontal, vertical_horizontal, vertical_horizontalStructure);
//
//    
//    
//    Core.subtract(gray, horizontal, lines_removed);
//
////	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__vertical_horizontal.jpg",vertical_horizontal);
//	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__lines_removed.jpg",lines_removed);
//






	
	compMap = hm;
//	String inputFile = "./src/img/"+fileName;

//	String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);	
	int erosion_size = 2;
	
    // Mat large = Imgcodecs.imread(inputFile),
	Mat	 rgb = new Mat(),
		 small =  new Mat(),
		 kernel =  new Mat(),
		 grad = new Mat(),
		 bw = new Mat(),
		 connected = new Mat(),
		 hierarchy  =  new Mat();//,
//		 rgb2 = new Mat();
     List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	 List<Rect> elems = new ArrayList<Rect>();
//	 Set<Rect> setUnsorted = new HashSet<Rect>();
	 List<Rect> dupl= new ArrayList<Rect>();


	 
	 
	 
//	 Mat processed = preProcessForAngleDetection(testing);

	 
	 bw = testing;
//	 small = straightImage;
//     Imgproc.pyrDown(large, rgb);
//     Imgproc.cvtColor(large,small, Imgproc.COLOR_BGR2GRAY);
//     Imgproc.cvtColor(straightImage,small, Imgproc.COLOR_BGR2GRAY);

//	 small = lines_removed;
//     displayImage(small,"img_1","grey",new Size(small.size().width,small.size().height),false);
//	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__grey.jpg",small);

//	  double rotationAngle = detectRotationAngle(testing);
//		
//	  Mat straightImage = rotateImage(src, rotationAngle);
//	  showWaitDestroy("straightImage" , straightImage);
//  
//	 Imgproc.cvtColor(straightImage,small, Imgproc.COLOR_BGR2GRAY);
//     Mat test = small.clone();
//     Mat test2 = small.clone();
//     Mat test3 = small.clone();
//
//     Mat OCR_IMG = small.clone();

//     
//     String ocr = doOcr(small);
//	 System.out.println("ocr: " + ocr);

     
     
//     
//     
//     
//     Mat test_lines = small.clone();
//
//     
//     Mat cannyEdges = new Mat();
//     Mat lines = new Mat();
//    
//
//     
//     
     
     
//
//	 Imgproc.cvtColor(small,test,Imgproc.COLOR_GRAY2BGR);			 
//	 Imgproc.cvtColor(small,test2,Imgproc.COLOR_GRAY2BGR);			 
//	 Imgproc.cvtColor(small,test3,Imgproc.COLOR_GRAY2BGR);			 

     
     
//     Size kernelSize_1 = new Size(3,3);
//      kernel =  Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, kernelSize_1);
//     
//      
////      small = testing;
//      Imgproc.morphologyEx(small, grad, Imgproc.MORPH_GRADIENT, kernel);
//
//      displayImage(grad,"img_2","morphologyEx",new Size(grad.size().width,grad.size().height),false);
// 	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__morphologyEx.jpg",grad);
//
//      Imgproc.threshold(grad, bw, 0.0, 255.0, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
//      displayImage(bw,"img_3","threshold",new Size(bw.size().width,bw.size().height),false);
// 	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__threshold.jpg",bw);
// 
//      
// 	 bw = testing;
      
		Mat erosion_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(erosion_size , erosion_size));
        Imgproc.erode(bw, bw, erosion_element);
		 showWaitDestroy("erode_bw" , bw);

        
        Size kernelSize_2 = new Size(9,1);

      kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, kernelSize_2);

      Imgproc.morphologyEx(bw, connected, Imgproc.MORPH_CLOSE, kernel);
      displayImage(connected,"img_4","morphologyEx_2",new Size(connected.size().width,connected.size().height),false);
//  	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__morphologyEx_2.jpg",connected);
	 showWaitDestroy("morphologyEx_2" , connected);

  	 
  	 
  	 
  	 
//  	 Mat lines = new Mat();
//  	 
//     Imgproc.HoughLinesP(connected, lines, 1, Math.PI / 180, 100);
//
//     double angle = 0;
//     
//     Mat debugImage = connected.clone();
//     Imgproc.cvtColor(debugImage, debugImage, Imgproc.COLOR_GRAY2BGR);
//     
//     for (int x = 0; x < lines.cols(); x++) {
//         double[] vec = lines.get(0, x);
//         double x1 = vec[0];
//         double y1 = vec[1];
//         double x2 = vec[2];
//         double y2 = vec[3];
//
//         Point start = new Point(x1, y1);
//         Point end = new Point(x2, y2);
//
//         //Draw line on the "debug" image for visualization
//         Imgproc.line(debugImage, start, end, new Scalar(255, 255, 0), 5);
//
//         //Calculate the angle we need
////         angle = 
//        		 
//        double deltaX = end.x - start.x;
//         double deltaY = end.y - start.y;
//         angle = Math.atan2(deltaY, deltaX) * (180 / Math.PI);
//         
////         calculateAngleFromPoints(start, end);
//         
//         System.out.println("angle: " + angle);
//     }
//     
// 	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__lines.jpg",debugImage);
//
// 	 
// 	 
// 	 
// 	 
// 	 
//     //Calculate image center
//     Point imgCenter = new Point(connected.cols() / 2, connected.rows() / 2);
//     //Get the rotation matrix
//     Mat rotMtx = Imgproc.getRotationMatrix2D(imgCenter, angle, 1.0);
//     //Calculate the bounding box for the new image after the rotation (without this it would be cropped)
//     Rect bbox = new RotatedRect(imgCenter, connected.size(), angle).boundingRect();
//
//     //Rotate the image
//     Mat rotatedImage = connected.clone();
//     Imgproc.warpAffine(connected, rotatedImage, rotMtx, bbox.size());
//     
// 	 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__rotatedImage.jpg",rotatedImage);
//
//     
//     
     
  	
//      Imgproc.findContours(connected, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
		 List<Integer> all_heights = new ArrayList<Integer>();


		 List<Double> all_areas = new ArrayList<Double>();


		 
		 
		 
		 
		 
		 double rotationAngle = detectRotationAngle(connected);
			
		  Mat straightImage = rotateImage(src, rotationAngle);
		  Mat straightImageConnected = rotateImage(connected, rotationAngle);

		  showWaitDestroy("straightImage" , straightImage);
	//
		 Imgproc.cvtColor(straightImage,small, Imgproc.COLOR_BGR2GRAY);
		 Imgproc.cvtColor(small,straightImage, Imgproc.COLOR_GRAY2BGR);

		 Mat test = straightImage.clone();
		 Mat test2 = straightImage.clone();
		 Mat test3 = straightImage.clone();
	
		 Mat OCR_IMG = straightImage.clone();

	  
//		 String ocr = doOcr(straightImage);
//		 System.out.println("ocr: " + ocr);
		 
//		 Imgproc.cvtColor(small,test,Imgproc.COLOR_GRAY2BGR);			 
//		 Imgproc.cvtColor(small,test2,Imgproc.COLOR_GRAY2BGR);			 
//		 Imgproc.cvtColor(small,test3,Imgproc.COLOR_GRAY2BGR);	
		 
		 
		 
	      Imgproc.findContours(straightImageConnected, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

		 
		 
     //first sort of found objects 
 	 for(int idx = 0; idx < contours.size();idx++){
 	 	  Rect rect = Imgproc.boundingRect(contours.get(idx));
 	 	  
 	 	  if(rect.height > 20  && rect.height < 100) {
 	 		  elems.add(rect);
 	 	   	 	  
 	 	  
 	 	  if(!contains(all_heights,rect.height)) {
 	  	 	all_heights.add(rect.height);

 	 	  }
 	 	  
 	 	  if(!contains(all_areas,rect.area())) {
 	 		all_areas.add(rect.area());
 	 	  }

	 	 	Imgproc.rectangle(test, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,0,255));
 	 	  }
	 	 
 	 }
 	 
 	 
 	 
		displayImage(test,"img_5","all ("+contours.size()+")",new Size(test.size().width,test.size().height),false);
//	    Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__allBoxes.jpg",test);
		  showWaitDestroy("allBoxes_filtered" , test);

	    
	    
	    
   
	 
	    
	 Collections.sort(elems,new Comparator<Rect>() {
	    	
			public int compare(Rect o1, Rect o2) {

				   int result = Double.compare(o1.x,o2.x);
			    if ( result == 0 ) {
			 	   result = Double.compare(o1.y,o2.y);
			 	   if(result == 0) {
			 		  dupl.add(o2);
			 	   }
			    }
				   return result;
			}
		});
	 System.out.println("before: " + elems.size());

	 //removing duplicates 
	 for(Rect del:dupl) {
		 elems.remove(del);
	 }
	 System.out.println("after: "+elems.size());

 	 
	 
     Collections.sort(all_areas);
     double sum_all_areas = 0;
     for (int i = 0; i < all_areas.size(); i++) {
    	 sum_all_areas += all_areas.get(i);
     }
     double mean_all_areas = sum_all_areas / all_areas.size();  
     
     System.out.println("mean_all_areas: " +mean_all_areas);
     System.out.println("all_areas: " +all_areas.toString());

     
     

     //***Remove Outliers Using Normal Distribution and S.D.
//     Collections.sort(all_heights);
//     double sum = 0;
//     for (int i = 0; i < all_heights.size(); i++) {
//         sum += all_heights.get(i);
//     }
//     double mean = sum / all_heights.size();         
//     double standardDeviation = 0.0;     
//     for(int num: all_heights) {
//         standardDeviation += Math.pow(num - mean, 2);
//     }
//
//     double sd = Math.sqrt(standardDeviation/all_heights.size());
//
//    				 List<Integer> final_list = new ArrayList<Integer>();
//    				 List<Integer> final_list_2 = new ArrayList<Integer>();
//
//    			 	 for(int fl = 0; fl < all_heights.size();fl++){
//    			 		if (all_heights.get(fl) > mean - 2 * sd) {
//    			 			final_list.add(all_heights.get(fl));
//    			 		}
//    			 	 }
//    			 	 
//    			 	 for(int fl = 0; fl < final_list.size();fl++){
//     			 		if (final_list.get(fl) < mean + 2 * sd) {
//     			 			final_list_2.add(final_list.get(fl));
//     			 		}
//     			 	 }
//
//     
//    			     System.out.println("sd: " + sd);
//    			     System.out.println("mean: " + mean);
//
//    System.out.println("all_heights: " +all_heights.size() + " - " +all_heights.toString());
//
//	 System.out.println("final_list_2: " +final_list_2.size() + " - " + final_list_2.toString());


	 
	 List<Rect> merged_temp = mergeObj(elems);
	 List<Rect> merged = mergeObj(merged_temp);
	 System.out.println("merged_temp_10: " +  merged_temp.size());
	 System.out.println("merged: " +  merged.size());
	 int ttt = 0;
	 while( !merged.equals(merged_temp) ) { 
		 merged_temp = merged;
		 merged = mergeObj(merged_temp);
		 System.out.println(ttt + ". merged_temp: " +  merged_temp.size() + " / merged: " +  merged.size());
		 ttt++;
	 } 
	 
	 System.out.println("final merged: " +  merged.size());

	 
	    
	    
//			//sort by x
		 Collections.sort(merged,new Comparator<Rect>() {
	    	
			public int compare(Rect o1, Rect o2) {

				   int result = Double.compare(o1.x,o2.x);
			    if ( result == 0 ) {
			 	   result = Double.compare(o1.y,o2.y);
			 	   
			    }
				   return result;
			}
		});
//	    
	 
//		//sort by y
//	 Collections.sort(merged,new Comparator<Rect>() {
// 	
//		public int compare(Rect o1, Rect o2) {
//
//			   int result = Double.compare(o1.y,o2.y);
//		    if ( result == 0 ) {
//		 	   result = Double.compare(o1.x,o2.x);
//		 	   
//		    }
//			   return result;
//		}
//	});
 


		 for(int idx = 0; idx < merged.size();idx++){
	 		 
	 		 Imgproc.rectangle(test2, merged.get(idx).br(),new Point( merged.get(idx).br().x-merged.get(idx).width ,merged.get(idx).br().y-merged.get(idx).height), new Scalar(0,0,255));

	 		 
//  	 	 	  System.out.println(idx + ". rect - x: " + merged.get(idx).x +", y: " +merged.get(idx).y + ", h: " + merged.get(idx).height +", w: " + merged.get(idx).width + ", A: " + merged.get(idx).area());
//  	 	 	  System.out.println("*****************************************");

	 		  
		 }
		 
	 	 
			displayImage(test2,"img_6","filtered ("+merged.size()+")",new Size(test2.size().width,test2.size().height),false);
//		    Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__allBoxes_filtered.jpg",test2);
			  showWaitDestroy("mergedBoxes" , test2);

	 	 
	    
			 List<List<Rect>> temp = new ArrayList<List<Rect>>();

			 
			 int counter = 0;
			 int maxCounter = merged.size();
			
			 while(counter < maxCounter) {
				 Rect rect = merged.get(counter);
				 List<Rect> t = new ArrayList<Rect>();

					 for (Iterator<Rect> rect_2_it = merged.iterator(); rect_2_it.hasNext();) {
						 Rect rect_2 = rect_2_it.next();
						 
//							 if((rect_2.y <= rect.y && (rect_2.y+rect_2.height) >= rect.y)) {
//								 t.add(rect_2);
//								 rect_2_it.remove();
//								 maxCounter = merged.size();
//							 }
						 	int rect_top = rect.y;
						 	int rect_bottom = rect.y + rect.height;			
						 	double middle_rect = rect.y + (rect.height/2);
							 
						 	
						 	if((rect_2.y + rect_2.height) >= middle_rect && rect_2.y <= middle_rect) {
						 		 t.add(rect_2);
								 rect_2_it.remove();
								 maxCounter = merged.size();
						 	}
						 	
//							 if((rect_2.y <= rect.y && (rect_2.y+rect_2.height) >= rect.y)) {
//								 t.add(rect_2);
//								 rect_2_it.remove();
//								 maxCounter = merged.size();
//							 }
					 }
					 counter++;
					 if(t.size() >= 2) {
						 temp.add(t);
						 counter = 0;
					 }else {
			  	 	 	 System.out.println("FALSE: " + t.toString());
					 }
						
					 
			 }
	   
  	 	 	 System.out.println("merged: " + merged.size());

			 int tempSize = temp.size();
			 
			 List<List<Rect>> temp_2 = new ArrayList<List<Rect>>();

			 
			 for(int idx = 0; idx < tempSize;idx++){
		  	 		 
	  	 		 System.out.println("*****************************************");
	  	 	 	 System.out.println(idx+": " + temp.get(idx).toString());
	  	 	 	 
	  	 	 	Random rand = new Random();
				 int r = rand.nextInt(255);
				 int g = rand.nextInt(255);
				 int b = rand.nextInt(255);
				 Scalar colour = new Scalar(r,g,b);
				 
				 List<Integer> gaps = new ArrayList<Integer>();

				 

				 
				 
//				 Collections.sort(temp.get(idx),new Comparator<Rect>() {
//				    	
//						public int compare(Rect o1, Rect o2) {
//
//							   int result = Double.compare(o1.x,o2.x);
////						    if ( result == 0 ) {
////						 	   result = Double.compare(o1.y,o2.y);
////						 	   
////						    }
//							   return result;
//						}
//					});
				 
				 
				 for(int idx2 = 0; idx2 < temp.get(idx).size();idx2++){
					 Rect rrr = temp.get(idx).get(idx2);
//					 Imgproc.rectangle(test3, rrr.br(),new Point( rrr.br().x-rrr.width ,rrr.br().y-rrr.height), colour);

					 if(idx2 + 1 < temp.get(idx).size()) {
						 int x_width = rrr.x + rrr.width;
						 Rect rrr_2 = temp.get(idx).get(idx2+1);
						 int x_2 = rrr_2.x;
						 int diff = x_2 - x_width;
						 gaps.add(diff);

					 }
				 }
//				 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "_"+idx+"__allBoxes_filtered_test3.jpg",test3);

				 
	  	 	 	 System.out.println("gaps: " + gaps.toString());

				 
	  	 	 	 
//	  	 	 	int[] gaps_arr = gaps.stream().mapToInt(i->i).toArray();
	  	 	 	//https://www.geeksforgeeks.org/find-a-peak-in-a-given-array/
	  	 	 	//https://www.youtube.com/watch?v=a7D77DdhlFc
//	  	 	 	 System.out.println("peak index: " + findPeak(gaps_arr,gaps_arr.length));

				 if(gaps.size() >= 2) {
				 List<Integer> peaks = peakFinder(gaps);

	  	 	 	 System.out.println("peaks: " + peaks.toString());
	  	 	 	 
//				 List<Rect> new_merged_temp = new ArrayList<Rect>();

				 
				 int maxx = temp.get(idx).size();
				 int counter_ = 0;
				 while (counter_ < maxx) {
//						 Rect rect = merged.get(counter);
						 Rect rrr = temp.get(idx).get(counter_);

						 for (Iterator<Rect> rect_2_it = temp.get(idx).listIterator(counter_); rect_2_it.hasNext();) {
							 Rect rrr_2 = rect_2_it.next();
							 int x_width = rrr.x + rrr.width;
							 if(!rrr.equals(rrr_2)) {
								 int x_2 = rrr_2.x;
								 int diff = x_2 - x_width;
								 System.out.println("diff: " + diff);
								 if(!peaks.contains(diff)) {
									 
									 int x  = rrr.x;//Math.min( rrr.x,   rrr_2.x   );
									 int y  = Math.min( rrr.y,  rrr_2.y  );
									 int height = Math.abs(Math.max((rrr_2.height+rrr_2.y), (rrr.height+rrr.y))- y);
									 int width = rrr.width + diff + rrr_2.width;
											 
											 
//											 Math.abs(Math.max((rrr_2.width + rrr_2.x), (rrr.width+rrr.x))- x);

									 rrr.x  = x;
									 rrr.y  = y;
									 rrr.height = height;
									 rrr.width = width;
									 rect_2_it.remove();
									 maxx = temp.get(idx).size();
									 
 
								 }else {
									 break;
								 }
							 }
						 }
						 counter_++;
				 }
				 
	  	 	 	 System.out.println(idx+": " + temp.get(idx).toString());

	 
	  	 	 	 
	  	 	 	 
	  	 	 	 

				
			 }
			 
			 
			 
			 
			 
			 
			 }
			 
			 
			 
			 
			 
			 
			 
			 String ocr = doOcr(straightImage);
	  	 	 System.out.println("*****************************************");
	  	 	 System.out.println("*****************************************");

			 System.out.println("ocr: " + ocr);
			 
			 temp_2 = temp;
			 
			 
	  	 	 System.out.println("*****************************************");
	  	 	 System.out.println("*****************************************"); 
			 for(int idx = 0; idx < temp_2.size();idx++){
				 Random rand = new Random();
				 int r = rand.nextInt(255);
				 int g = rand.nextInt(255);
				 int b = rand.nextInt(255);
				 Scalar colour = new Scalar(r,g,b);
//		  	 	 System.out.println("*****************************************");
//
		  	 	 System.out.println("");
//		  	 	 System.out.println(idx + ". ");

				 
				 for(int idx2 = 0; idx2 < temp_2.get(idx).size();idx2++){
					 
			  	 	 System.out.print(" ");

					 Rect rrr = temp_2.get(idx).get(idx2);
					 Imgproc.rectangle(test3, rrr.br(),new Point( rrr.br().x-rrr.width ,rrr.br().y-rrr.height), colour);

					 int height_10 = (int) Math.round(rrr.height*0.2);
					 int width_10 = (int) Math.round(rrr.width*0.2);
					 rrr.height = rrr.height + height_10;
					 rrr.width = rrr.width + width_10;
					 rrr.x = Math.abs(rrr.x - (width_10/2));
					 rrr.y =  Math.abs(rrr.y - (height_10/2));
					 
					 if((rrr.x + rrr.width) > OCR_IMG.width()) {
//						 System.out.println(rrr + " - W: " + OCR_IMG.width());	 
						 rrr.width = (OCR_IMG.width() - rrr.x);
					 }
					 
					 if((rrr.y + rrr.height) > OCR_IMG.height()) {
//						 System.out.println(rrr + " - H: " + OCR_IMG.height());	 
						 rrr.height = (OCR_IMG.height() - rrr.y);
					 }

//					 Imgproc.rectangle(test3, rrr.br(),new Point( rrr.br().x-rrr.width ,rrr.br().y-rrr.height), colour);
//					 Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "_"+idx+"_"+idx2+"__allBoxes_filtered_test3.jpg",test3);
//					 
					 
//
							Mat img = new Mat(OCR_IMG,rrr);
							String res = doOcr(img);
				 			System.out.print(res);	 
//					 

				 }
		 		  
			 }	 
		    
//			    Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__allBoxes_filtered_test3.jpg",test3);
				  showWaitDestroy("__allBoxes_filtered_test3" , test3);

		    
	  	 	 	  System.out.println("*****************************************");
	  	 	 	  System.out.println("temp: " + temp.size());
	  	 	 	  System.out.println("temp_2: " + temp_2.size());

	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  

		    
		    
		    
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	  	 	 	  
	    
//	 List<List<Rect>> arr_elems = new ArrayList< List<Rect>>();
//
//	 List<Rect> top_first = new ArrayList<Rect>();
//
//	 
//	 List<Rect> top_max = new ArrayList<Rect>();
//
//		 int countArr = 0;		 
// 	 for(int r = 0;r<elems.size();r++) {
// 		 Rect rect = elems.get(r);
// 		 if(r+1 <= elems.size()-1) {
//	 		 Rect rect2 = elems.get(r+1);
//	 		 if((rect2.x-rect.x)<=100) {
//	 			 if(!arr_elems.isEmpty() && arr_elems.size()-1==countArr) {
//	 				
//	 				addToMainList( arr_elems, top_max, countArr, rect);
//
//	 				
//	 			 }else {
//	 				 List<Rect> col = new ArrayList<Rect>();
//	 				 col.add(rect);
//	 				arr_elems.add(col);
//	 			 }
//	 		 }else {
//	 			addToMainList( arr_elems, top_max, countArr, rect);
//	 			 countArr++;
//	 		 }
//
// 		 }else {
// 			addToMainList( arr_elems, top_max, countArr, rect);
//
// 		 }
// 		 
// 		 
// 		 
// 		 System.out.println(rect);
// 		 
//
//	 		Imgproc.rectangle(rgb2, rect.br(),new Point( rect.br().x-rect.width ,rect.br().y-rect.height), new Scalar(0,0,255));
//
// 	
// 	 }
// 	
// 	 
// 		List<Text_Rect> text_rect_list = new ArrayList<Text_Rect>();
// 		
//
// 	
// 	
// 	
// 	
//			int idxList =0;
//
// 	for(int l = 0;l<arr_elems.size();l++) {
// 		List<Rect> list = arr_elems.get(l);
// 		 System.out.println("<_____________ List " +l+ "_____________>");
// 			Rect max_elem = top_max.get(l);
// 			
// 			//sort by y
// 			 Collections.sort(list,new Comparator<Rect>() {
// 		    	
// 				public int compare(Rect o1, Rect o2) {
// 	
// 					   int result = Double.compare(o1.y,o2.y);
// 				    if ( result == 0 ) {
// 				 	   result = Double.compare(o1.x,o2.x);
// 				 	   
// 				    }
// 					   return result;
// 				}
// 			});
// 		 for(Rect re:list) {
// 			 //extend each elem to max size 
//			 if(!max_elem.equals(re)) {
//				  re.width = max_elem.width+20;
//				  re.x = max_elem.x-20;
//
//			 }
//
//			 
//			 
//			 Imgproc.rectangle(rgb2, re.br(),new Point( re.br().x-re.width ,re.br().y-re.height), new Scalar(0,255,0));
// 			
//			 if(l==0) {
//				 Imgproc.rectangle(rgb2, new Point(re.x,re.y) , new Point( (rgb2.size().width)-re.x-10 ,re.y+re.height),  new Scalar(255, 0, 0));
//			 }
//			  
//
//		 }
//
//	 }
// 	
//
// 	 
////		displayImage(test,"img_5","all ("+contours.size()+")",new Size(test.size().width,test.size().height),false);
////	    Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__allBoxes.jpg",test);
//
//		displayImage(rgb2,"img_6","filtered ("+elems.size()+")",new Size(rgb2.size().width,rgb2.size().height),false);
//
//	
////    Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__allBoxes.jpg",test);
//	Imgcodecs.imwrite(fileToSaveImgs + fileNameWithOutExt + "__filteredBoxes.jpg",rgb2);
	
	
	System.out.println("************* Elapsed time: " + ((double)(System.nanoTime() - startTime)/1000000000.0) + " seconds ************* ");

}


public List<Rect> mergeObj (List<Rect> elems_) {
	
	List<Rect> cluster_ = new ArrayList<Rect>();
	
 	for (Rect rect : elems_) {
 		
// 		if(contains(final_list_2,rect.height)) {
 		boolean matched = false;
 	 	for (Rect cluster : cluster_) {

			 if(rect.x <= cluster.x + cluster.width && rect.x + rect.width >= cluster.x && rect.y <= cluster.y + cluster.height && rect.y + rect.height >= cluster.y){

				matched = true;
								
	  	 	 	int x  = Math.min( cluster.x,   rect.x   );
	            int y  = Math.min( cluster.y,  rect.y  );
	            int height = Math.abs(Math.max((rect.height+rect.y), (cluster.height+cluster.y))- y);
	            int width = Math.abs(Math.max((rect.width + rect.x), (cluster.width+cluster.x))- x);

	            cluster.x  = x;
	            cluster.y  = y;
	            cluster.height = height;
	            cluster.width = width;
	       	    
			 }
 	 	} 
 	 	if(!matched) {
 	 		if(rect.height >= 5 && rect.width >= 5) {
 	 			cluster_.add(rect);
 	 		}
 	 	}
// 	}
 	}
 	
 	return cluster_;
 	
 	
}


static List<Integer> peakFinder (List<Integer> arr){
	
	List<Integer> result = new ArrayList<Integer>();
	
	for(int i = 0; i < arr.size(); i++) {
		int val = arr.get(i);
		int next = i < arr.size()-1 ? arr.get(i+1) : arr.get(arr.size()-1);
		int prev = i >= 1 ? arr.get(i-1) : arr.get(i);
		if(next < val && prev < val || (i == 0 && next < val) || (i == arr.size()-1 && prev < val)) {
			result.add(val);
		}
	}

	return result;
	
}





//var peakFinder = function(array) {
//	  // instantiate an array as result
//	  var result = [];
//	  // iterate over input
//	  _.each(array, function(val,key,col){
//	    // check left and right neighbors
//	    if(col[key+1] < val && col[key-1] < val) {
//	      // add information to results array
//	      result.push([key,val]);
//	    }
//	  });
//	  // ternary check: if results array is not empty give result array, else give false
//	  return result.length ? result : false;
//	};
//	
	
	
//static int findPeakUtil(int arr[], int low, int high, int n) 
//{ 
//    // Find index of middle element 
//    int mid = low + (high - low)/2;  /* (low + high)/2 */
//
//    // Compare middle element with its neighbours (if neighbours 
//    // exist) 
//    if ((mid == 0 || arr[mid-1] <= arr[mid]) && (mid == n-1 || 
//         arr[mid+1] <= arr[mid])) 
//        return mid; 
//
//    // If middle element is not peak and its left neighbor is 
//    // greater than it,then left half must have a peak element 
//    else if (mid > 0 && arr[mid-1] > arr[mid]) 
//        return findPeakUtil(arr, low, (mid -1), n); 
//
//    // If middle element is not peak and its right neighbor 
//    // is greater than it, then right half must have a peak 
//    // element 
//    else return findPeakUtil(arr, (mid + 1), high, n); 
//} 
//
//// A wrapper over recursive function findPeakUtil() 
//static int findPeak(int arr[], int n) 
//{ 
//    return findPeakUtil(arr, 0, n-1, n); 
//} 


}
