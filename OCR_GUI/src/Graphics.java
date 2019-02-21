import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Graphics{
    
	public hashMap compMap;
    
  
	public void displayImage(Mat org_image,String img,String name){
		Component component = compMap.getComponentByName(img);
    	
    	if (component instanceof JLabel) {

		JLabel lbl_img = (JLabel) component;
		
		Mat m = org_image.clone();
		Size sizeOfimg = new Size(500,450);
		
		Imgproc.resize(m, m, sizeOfimg);
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
	
		lbl_img.setIcon(new ImageIcon(image));
		
    	}
    	
    	Component componentLbl = compMap.getComponentByName("lbl_"+img);
    	
    	if (componentLbl instanceof JLabel) {
    		((JLabel) componentLbl).setText(name);
    		
    	}
  }
	  
	
	  
	  


    static {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    }
 

 
	public void startProccess(hashMap hm,String fileName,int first,int second,int third, int erode, int dilate) {
		compMap = hm;
		String inputFile = ".\\bin\\img\\"+fileName;
		 
	    
	     
	     
	     
	     
	     Mat org_img = Imgcodecs.imread(inputFile);
	     Imgproc.resize(org_img, org_img, new Size(1000,1000) );

	     
			
	        List<Rect> arrOfEl = new ArrayList<Rect>();
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
	    		destination2 = new Mat(bw.rows(),bw.cols(),bw.type()),
	    		erosion_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(erosion_size , erosion_size)),
	    		dilation_element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(dilation_size , dilation_size));

		    
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
			
	     Imgproc.resize(org_img, org_img, new Size(1000,1000) );

	     Imgproc.cvtColor(org_img, greyscale, Imgproc.COLOR_BGR2GRAY);

displayImage(org_img,"img_1","Org");


		

		Imgproc.GaussianBlur(greyscale, imageBlurr, new Size(5,5), 0);

	    

       //Imgproc.adaptiveThreshold(imageBlurr, imageA, 255,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV,3, 9);
        Imgproc.adaptiveThreshold(imageBlurr, imageA, first,Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV,second, third);

displayImage(imageA,"img_2","Adaptive Threshold");

        
        destination = imageA.clone();
        
        destination2 = imageA.clone();
        
        

        
        
//
//        Imgproc.erode(imageA, destination, erosion_element);
//        
//displayImage(destination,"img_3", "erode");
//
//
//        Imgproc.dilate(imageA, destination2, dilation_element);
//        
//displayImage(destination2,"img_4","dilate");
//        Imgproc.findContours(destination2, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);



        Imgproc.erode(imageA, destination, erosion_element);
        
displayImage(destination,"img_3", "erode");


        Imgproc.dilate(destination, destination, dilation_element);
        
displayImage(destination,"img_4","dilate");    

    
 
    
    Imgproc.findContours(destination, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

    

    
    Imgproc.cvtColor(greyscale,mask,Imgproc.COLOR_GRAY2BGR);
    Imgproc.cvtColor(greyscale,mask2,Imgproc.COLOR_GRAY2BGR);

    
    
    
//Collections.sort(contours,new Comparator<MatOfPoint>() {
//
//public int compare(MatOfPoint o1, MatOfPoint o2) {
//	   Rect rect = Imgproc.boundingRect(o1);
//	   Rect rect2 = Imgproc.boundingRect(o2);
//
//	   
//	   int result = Double.compare(rect.x,rect2.x);
//    if ( result == 0 ) {
// 	   result = Double.compare(rect.y,rect2.y);
//    }
//	   return result;
//}
//});
//
//
//
//for(int idx = 0; idx < contours.size(); idx++)
//
//{
//	   
//	   Rect rect = Imgproc.boundingRect(contours.get(idx));
//
//		  // System.out.println(rect);
//		   int count=0;
//		   outerloop:
//		   for(Rect el : arrOfEl) {
//	    	   //Rect el = point;
//	    	   if(!el.equals(rect)) {
//	    		   int diff = Math.abs(rect.x - (el.x+(el.width)));
//	    		   System.out.println(rect);
//	    		   //System.out.println(diff);
//	    		   //System.out.println(el);
//
//	    		   
//    	    	   if (diff < 60) {//(Math.abs(el.y - rect.y)) < 30 &&
//    	    		   if(rect.y > el.y) {
//    	    			   int diff_y = rect.y - el.y;
//    	    			   el.height = diff_y;
//    	    			   
//    	    		   }
//    	    		   
//    	    		   if(rect.x > el.x) {
//    	    			    el.width = rect.x + rect.width;
//
//    	    		   }else {
//    	    			   if( el.width == 0) {
//    	    				   el.width = Math.abs((rect.x - el.x))*-1;
//    	    	    		   break outerloop;
//
//    	    			   }
//    	    			  if((rect.x)*-1 > el.width) {
//    	    				  el.width = Math.abs((rect.x-el.x))*-1;
//    	    			  }
//    	    		   }
//    	    		   
//    	    		
//    	    		 //  el.width = (rect.width+rect.x)-el.x;
//    	    		//  el.height =  Math.max(el.height, (Math.abs(el.y-rect.y)+rect.height));
//    	    		  
//    	    		//  el.y = Math.min(el.y,rect.y);
//
//    	    		  
//    	    		   
//    	    		   
//    	    		   
//    	    		   break outerloop;
//    	    	   }else {
//    	    		   count++;
//    	    	   }
//	
//	    	   }	
//		   }
//		   
//		   if(count == arrOfEl.size()) {
//			   rect.height = 0;
//			   rect.width = 0;
//
//			   arrOfEl.add(rect);
//		   }
//		   
//	  Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));
//    
//}

  

Collections.sort(contours,new Comparator<MatOfPoint>() {

public int compare(MatOfPoint o1, MatOfPoint o2) {
	   Rect rect = Imgproc.boundingRect(o1);
	   Rect rect2 = Imgproc.boundingRect(o2);

	   
	   int result = Double.compare(rect.x,rect2.x);
    if ( result == 0 ) {
 	   result = Double.compare(rect.y,rect2.y);
    }
	   return result;
}
});
	for(int idx = 0; idx < contours.size();idx++)//contours.size(); idx++)// 25; idx++)

    {
 	   
// 	  double area = Imgproc.contourArea(contours.get(idx));
	   
 	   Rect rect = Imgproc.boundingRect(contours.get(idx));

 	   if(rect.height > 10 && rect.height < 200 && rect.width >20) {
 		   //System.out.println(rect.x+",-"+rect.y);
// 		   System.out.println(rect);

 		   int count=0;
 		   outerloop:
 		   for(Rect point : arrOfEl) {
 	    	   Rect el = point;
 	    	   if(!el.equals(rect)) {
 	    		
 	    		  
 	    		  //rect_distance((x1, y1, x1b, y1b), (x2, y2, x2b, y2b)):
// 	    		  rect.x < el.x + el.width
// 	    		  
//	  rect.x + rect.width > el.x 	
//    rect.y + rect.height > el.y
//    
    
// 	    		  System.out.print(rect);
// 	    		  System.out.print("  /  ");
// 	    		  System.out.print(el);
// 	    		  System.out.print("  /  ");


    
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
 	    			    	System.out.println(distance(x1, y1b, x2b, y2) + " - top && left");

 	    			    	 if(distance(x1, y1b, x2b, y2) < 10) { 
 	    			    		  System.out.print(rect);
 	    		 	    		  System.out.print("  /  ");
 	    		 	    		  System.out.print(el);
 	    		 	    		  System.out.print("  /  ");
 	    			    	System.out.println(distance(x1, y1b, x2b, y2) + " - top && left");
 	    			    	break outerloop;
 	    			    	 }
 	    			    }else if (left && bottom) {
 	    			    	System.out.println(distance(x1, y1, x2b, y2b)+ " - left && bottom");

 	    			    	 if(distance(x1, y1, x2b, y2b) < 10) { 
 	    			    		  System.out.print(rect);
 	    		 	    		  System.out.print("  /  ");
 	    		 	    		  System.out.print(el);
 	    		 	    		  System.out.print("  /  ");
 	    			    	System.out.println(distance(x1, y1, x2b, y2b)+ " - left && bottom");
 	    			    	break outerloop;
 	    			    	 }
 	    			   }else if (bottom && right) {
 	    				   System.out.println( distance(x1b, y1, x2, y2b)+ " - bottom && right");

 	    				  if(distance(x1b, y1, x2, y2b) < 10) { 
 	    					  System.out.print(rect);
 	    	 	    		  System.out.print("  /  ");
 	    	 	    		  System.out.print(el);
 	    	 	    		  System.out.print("  /  ");
 	    				   System.out.println( distance(x1b, y1, x2, y2b)+ " - bottom && right");
 	    				 break outerloop;
 	    				  }
 	    			   }else if (right && top) {
 	    				   System.out.println( distance(x1b, y1b, x2, y2)+ " - right && top");

 	    				  if(distance(x1b, y1b, x2, y2) < 10) { 
 	    					  System.out.print(rect);
 	    	 	    		  System.out.print("  /  ");
 	    	 	    		  System.out.print(el);
 	    	 	    		  System.out.print("  /  ");
 	    				   System.out.println( distance(x1b, y1b, x2, y2)+ " - right && top");
 	    				 break outerloop;
 	    				  }
 	    			   }else if (left) {
 	    				   System.out.println(x1 - x2b+ " - left");

 	    				  if(x1 - x2b < 4) { 
 	    					  System.out.print(rect);
 	    	 	    		  System.out.print("  /  ");
 	    	 	    		  System.out.print(el);
 	    	 	    		  System.out.print("  /  ");
 	    				   System.out.println(x1 - x2b+ " - left");
 	    				 break outerloop;
 	    				  }
 	    			   }else if (right) {
 	    				   System.out.println( x2 - x1b+ " - right");

 	    				  if(x2 - x1b < 4) { 
 	    					  System.out.print(rect);
 	    	 	    		  System.out.print("  /  ");
 	    	 	    		  System.out.print(el);
 	    	 	    		  System.out.print("  /  ");
 	    				   System.out.println( x2 - x1b+ " - right");
 	    				   
 	 	    				 el.width = Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));//Math.max(((rect.width+rect.x)-el.x),((el.width+el.x)));
 	 	    				 if(el.y < rect.y) {
	 	    					 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
	 	    				 }else {
	 	    					 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));
	
	 	    				 }
 	 	    				 el.y = Math.min(el.y,rect.y);
 	 	    				 
 	    					break outerloop;
 	    				  }
 	    			   }else if (bottom) {
// 	    				 if(y1 - y2b < 4) { 
// 	    					  System.out.print(rect);
// 	    	 	    		  System.out.print("  /  ");
// 	    	 	    		  System.out.print(el);
// 	    	 	    		  System.out.print("  /  ");
// 	 	    				  System.out.println( y1 - y2b+ " - bottom");
// 	 	    				 el.width = Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));//Math.max(((rect.width+rect.x)-el.x),((el.width+el.x)));
// 	 	    				 if(el.y < rect.y) {
//	 	    					 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
//	 	    				 }else {
//	 	    					 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));
//	
//	 	    				 }
// 	 	    				 el.y = Math.min(el.y,rect.y);
//	 	    				break outerloop;
// 	    				 }
 	    				
 	    			   }else if (top) {
 	    				 
// 	    				  if(y2 - y1b < 4) { 
// 	    					  System.out.print(rect);
// 	    	 	    		  System.out.print("  /  ");
// 	    	 	    		  System.out.print(el);
// 	    	 	    		  System.out.print("  /  ");
// 	    					  System.out.println( y2 - y1b+ " - top");
// 	    				  
// 	    					 el.width = Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));//Math.max(((rect.width+rect.x)-el.x),((el.width+el.x)));
// 	 	    				 if(el.y < rect.y) {
//	 	    					 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
//	 	    				 }else {
//	 	    					 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));
//	
//	 	    				 }
// 	 	    				 el.y = Math.min(el.y,rect.y);
// 	 	    				 
// 	 	    				 
// 	    				 break outerloop;
// 	    				  }
 	    			   }else {//if(top && left && right && bottom){     
// 	    				  System.out.print(rect);
// 	    	    		  System.out.print("  /  ");
// 	    	    		  System.out.print(el);
// 	    	    		  System.out.print("  /  ");
// 	    				  System.out.println(0+ " - INTERSECT");
// 	    				 el.width = Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));//Math.max(((rect.width+rect.x)-el.x),((el.width+el.x)));
//	    	    		  
// 	    				 
// 	    				 if(el.y < rect.y) {
// 	    					 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
// 	    				 }else {
// 	    					 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));
//
// 	    				 }
// 	    				 
// 	    				 
// 	    				 
// 	    				 el.y = Math.min(el.y,rect.y);
// 	    				break outerloop;
 	    			   }
 	    			    
 	    			    
 	    			    
 	    			   if(rect.x < el.x + el.width && rect.x + rect.width > el.x && rect.y < el.y + el.height && rect.y + rect.height > el.y) {
 	    			    	  System.out.print(rect);
 	 	    	    		  System.out.print("  /  ");
 	 	    	    		  System.out.print(el);
 	 	    	    		  System.out.print("  /  ");
 	 	    				  System.out.println(0+ " - INTERSECT");
 	 	    				 el.width = Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));//Math.max(((rect.width+rect.x)-el.x),((el.width+el.x)));
 		    	    		  
 	 	    				 
 	 	    				 if(el.y < rect.y) {
 	 	    					 el.height =  Math.max(el.height,Math.max(rect.height,Math.abs(el.y-(rect.y+rect.height))));
 	 	    				 }else {
 	 	    					 el.height =  Math.max(rect.height,Math.max(el.height,Math.abs(rect.y-(el.y+el.height))));

 	 	    				 }
 	 	    				 
 	 	    				 
 	 	    				 
 	 	    				 el.y = Math.min(el.y,rect.y);
	 	    				break outerloop;

 	    			    }
// 	    				   }else {
// 	    	    			   count++;
// 
// 	    				   }
 	    			//	count++; 	    			        		
 	    			        		
	    	    	  // if(//(Math.abs((el.y+el.height) - rect.y)) < 10 && (Math.abs(rect.x - (el.x+el.width))) < 10 ||
	    	    			   //el.x < rect.x + rect.width && el.x + el.width > rect.x && el.y < rect.y + rect.height && el.y + el.height > rect.y
	    	    	//		   rect.x < el.x + el.width && rect.x + rect.width > el.x && rect.y < el.y + el.height && rect.y + rect.height > el.y) {
	    	    		   
	    	    		   
	    	    		
	    	    	//	   el.width = Math.max(el.width,Math.max(rect.width,Math.abs(el.x-(rect.x+rect.width))));//Math.max(((rect.width+rect.x)-el.x),((el.width+el.x)));
	    	    	//	   el.height =  Math.max(el.height,Math.max(rect.height,Math.abs((el.y+el.height)-(rect.y+rect.height))));
	    	    	//	   el.y = Math.min(el.y,rect.y);
	    	    		  
	    	    		  
	    	    		  //Math.max(el.height, (Math.abs(el.y-rect.y)+rect.height));
	    	    		  
	    	    		  //el.y = Math.min(el.y,rect.y);

	    	    		  
	    	    		//   break outerloop;
	    	    	//   }else {
	    	    		//   count++;
	    	    	  // }
// 	    			   count++;
 	    	   }	
 	    	  count++;
 		   }
 		   
 		   if(count == arrOfEl.size()) {
 			   arrOfEl.add(rect);
 		   }
 		   
 		  //Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));
	
 	   }
		  Imgproc.rectangle(mask2, rect.br() , new Point( rect.br().x-rect.width ,rect.br().y-rect.height),  new Scalar(0, 255, 0));

        
   }
	displayImage(mask2,"img_5","all contours ("+contours.size()+")");

	
	
    List<Rect> sortedByX =  new ArrayList<Rect>(arrOfEl);

	
	

	Collections.sort(arrOfEl,new Comparator<Rect>() {

		public int compare(Rect o1, Rect o2) {
			   

			   
			   int result = Double.compare(o1.y,o2.y);
		    if ( result == 0 ) {
		 	   result = Double.compare(o1.x,o2.x);
		    }
			   return result;
		}
		});	
	System.out.println("*********arrOfEl*************");
	for(Rect el : arrOfEl) {
		System.out.println(el);
		
	   //Imgproc.rectangle(mask, el.br() , new Point( el.br().x-el.width ,el.br().y-el.height),  new Scalar(255, 0, 0));

	   
	   
	}
	
displayImage(mask,"img_6","Sorted Contours ("+arrOfEl.size()+")");
  
Imgcodecs.imwrite("N:\\Desktop\\imgContoursGreen.jpg",mask2);
Imgcodecs.imwrite("N:\\Desktop\\imgContoursBlue.jpg",mask);
    
  
	}
	public double distance (double x1,double x2,double y1,double y2) {
	    double ycoordDiff = Math.abs (y1 - y2);
	    double xcoordDiff = Math.abs (x1- x2);    
	    double distance = Math.sqrt((ycoordDiff)*(ycoordDiff) +(xcoordDiff)*(xcoordDiff));
	    return distance; 
	    }
	
}


