import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Cursor;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
public class main {

	private JFrame frame,frame2;
	public DefaultListModel<String> arrFileList= new DefaultListModel<>();///= new String[]();
	public String selectedFile;
	public static void main(String[] args) {
		
	
	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frame.setVisible(true);
					
//					main window2 = new main();
//					window2.frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public main() {
		
		initialize();
		
		
	}

	
	private hashMap hm = new hashMap();
//	private Graphics graphics = new Graphics();
	private FirstElemFullWidth graphics = new FirstElemFullWidth();

	
	private void initialize() {
		
		
		
		
//		File dir = new File(".\\src\\img\\");
		File dir = new File("./src/img/");

		File[] filesList = dir.listFiles();
		for (File file : filesList) {
		    if (file.isFile()) {
		    	arrFileList.addElement(file.getName());
		    	//System.out.println(file.getName());
		    }
		}
		
		
		ArrayList<String> list = Collections.list(arrFileList.elements());
		Collections.sort(list);
		
		arrFileList.clear();
		for(Object o:list){ arrFileList.addElement(o.toString()); }
		
		frame = new JFrame();
		frame.setBounds(new Rectangle(10, 0, 0, 0));
		
		frame.setBounds(100, 10, 1786, 1056);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JPanel main_panel_1 = new JPanel();
		main_panel_1.setVisible(true);
		frame.getContentPane().add(main_panel_1, BorderLayout.CENTER);
		main_panel_1.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(1519, 11, 241, 55);
		main_panel_1.add(panel_1);
		panel_1.setLayout(null);
		
		JSlider slider = new JSlider();
		slider.setEnabled(false);
		slider.setMaximum(300);
		slider.setBounds(5, 5, 200, 45);
		slider.setName("slider");
		slider.setValue(255);
		
		panel_1.add(slider);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(50);
		
		JLabel label = new JLabel("value");
		label.setBounds(210, 20, 26, 14);
		label.setName("label");
		panel_1.add(label);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(1519, 77, 241, 55);
		main_panel_1.add(panel_2);
		
		JSlider slider_1 = new JSlider();
		slider_1.setEnabled(false);
		slider_1.setMaximum(200);
		slider_1.setMinimum(1);
		slider_1.setValue(7);
		slider_1.setName("slider_1");
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setMajorTickSpacing(25);
		panel_2.add(slider_1);
		
		JLabel label_1 = new JLabel("value");
		label_1.setName("label_1");

		panel_2.add(label_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(1519, 143, 241, 55);
		main_panel_1.add(panel_3);
		
		JSlider slider_2 = new JSlider();
		slider_2.setEnabled(false);
		slider_2.setMinimum(1);
		slider_2.setValue(7);
		slider_2.setName("slider_2");
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		slider_2.setMajorTickSpacing(25);
		panel_3.add(slider_2);
		
		JLabel label_2 = new JLabel("value");
		label_2.setName("label_2");
		panel_3.add(label_2);
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_4.setBounds(5, 11, 500, 500);
		main_panel_1.add(panel_4);
		
		JLabel lbl_img_1 = new JLabel("value");
		lbl_img_1.setSize(new Dimension(500, 30));
		lbl_img_1.setLocation(new Point(250, 0));
		lbl_img_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_img_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_img_1.setBounds(new Rectangle(250, 0, 500, 60));
		lbl_img_1.setAlignmentX(1.0f);
		lbl_img_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lbl_img_1.setName("lbl_img_1");
		panel_4.add(lbl_img_1);
		
		
		
		JLabel img_1 = new JLabel("");
		panel_4.add(img_1);
		img_1.setName("img_1");

		
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(515, 11, 500, 500);
		main_panel_1.add(panel_5);
		
		JLabel lbl_img_2 = new JLabel("value");
		lbl_img_2.setSize(new Dimension(100, 0));
		lbl_img_2.setName("lbl_img_2");
		lbl_img_2.setLocation(new Point(250, 0));
		lbl_img_2.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_img_2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_img_2.setBounds(new Rectangle(250, 0, 100, 0));
		lbl_img_2.setAlignmentY(1.0f);
		lbl_img_2.setAlignmentX(1.0f);
		panel_5.add(lbl_img_2);
		
		JLabel img_2 = new JLabel("");
		img_2.setName("img_2");
		panel_5.add(img_2);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(1009, 11, 500, 500);
		main_panel_1.add(panel_6);
		
		JLabel lbl_img_3 = new JLabel("value");
		lbl_img_3.setIgnoreRepaint(true);
		lbl_img_3.setSize(new Dimension(100, 0));
		lbl_img_3.setName("lbl_img_3");
		lbl_img_3.setLocation(new Point(250, 0));
		lbl_img_3.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_img_3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_img_3.setBounds(new Rectangle(250, 0, 100, 0));
		lbl_img_3.setAlignmentY(1.0f);
		lbl_img_3.setAlignmentX(1.0f);
		panel_6.add(lbl_img_3);
		
		JLabel img_3 = new JLabel("");
		img_3.setName("img_3");
		panel_6.add(img_3);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(5, 515, 500, 500);
		main_panel_1.add(panel_7);
		
		JLabel lbl_img_4 = new JLabel("value");
		lbl_img_4.setSize(new Dimension(100, 0));
		lbl_img_4.setName("lbl_img_4");
		lbl_img_4.setLocation(new Point(250, 0));
		lbl_img_4.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_img_4.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_img_4.setBounds(new Rectangle(250, 0, 100, 0));
		lbl_img_4.setAlignmentY(1.0f);
		lbl_img_4.setAlignmentX(1.0f);
		panel_7.add(lbl_img_4);
		
		JLabel img_4 = new JLabel("");
		img_4.setName("img_4");
		panel_7.add(img_4);

		
		
		JButton btnSee = new JButton("See");
		
		btnSee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// graphics.startProccess(hm,selectedFile,getSliderVal("slider"),getSliderVal("slider_1"),getSliderVal("slider_2"),getSliderVal("slider_3"),getSliderVal("slider_4"));
			
//	            graphics.process2(hm,selectedFile,getSliderVal("slider_3"));
//	            graphics.process2(hm,selectedFile);
//	            graphics.SWT(hm,selectedFile);
//	            graphics.process3(hm,selectedFile);
//
//


			}
		});
		btnSee.setBounds(1602, 370, 89, 23);
		main_panel_1.add(btnSee);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(507, 515, 500, 500);
		main_panel_1.add(panel_8);
		
		JLabel lbl_img_5 = new JLabel("value");
		lbl_img_5.setSize(new Dimension(100, 0));
		lbl_img_5.setName("lbl_img_5");
		lbl_img_5.setLocation(new Point(250, 0));
		lbl_img_5.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_img_5.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_img_5.setBounds(new Rectangle(250, 0, 100, 0));
		lbl_img_5.setAlignmentY(1.0f);
		lbl_img_5.setAlignmentX(1.0f);
		panel_8.add(lbl_img_5);
		
		JLabel img_5 = new JLabel("");
		img_5.setName("img_5");
		panel_8.add(img_5);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBounds(1009, 515, 500, 500);
		main_panel_1.add(panel_9);
		
		JLabel lbl_img_6 = new JLabel("value");
		lbl_img_6.setSize(new Dimension(100, 0));
		lbl_img_6.setName("lbl_img_6");
		lbl_img_6.setLocation(new Point(250, 0));
		lbl_img_6.setHorizontalTextPosition(SwingConstants.CENTER);
		lbl_img_6.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_img_6.setBounds(new Rectangle(250, 0, 100, 0));
		lbl_img_6.setAlignmentY(1.0f);
		lbl_img_6.setAlignmentX(1.0f);
		panel_9.add(lbl_img_6);
		
		JLabel img_6 = new JLabel("");
		img_6.setName("img_6");
		panel_9.add(img_6);

		
		
		JPanel panel_10 = new JPanel();
		panel_10.setBounds(1522, 404, 240, 603);
		main_panel_1.add(panel_10);
		
		
		
		
		JList fileList = new JList<>(arrFileList);
		fileList.setName("fileList");
		fileList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		fileList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(fileList);
		listScroller.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        listScroller.setPreferredSize(new Dimension(230, 400));

		panel_10.add(listScroller);
		
		
		
		JPanel panel_11 = new JPanel();
		panel_11.setBounds(1519, 209, 241, 72);
		main_panel_1.add(panel_11);
		
		JSlider slider_3 = new JSlider();
		slider_3.setMaximum(30);
		slider_3.setValue(2);
		slider_3.setPaintTicks(true);
		slider_3.setPaintLabels(true);
		slider_3.setName("slider_3");
		slider_3.setMajorTickSpacing(5);
		panel_11.add(slider_3);
		
		JLabel label_5 = new JLabel("value");
		label_5.setName("label_5");
		panel_11.add(label_5);
		
		JLabel lblErode = new JLabel("Erode");
		lblErode.setName("Erode");
		panel_11.add(lblErode);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBounds(1519, 287, 241, 72);
		main_panel_1.add(panel_12);
		
		JSlider slider_4 = new JSlider();
		slider_4.setEnabled(false);
		slider_4.setMajorTickSpacing(4);
		slider_4.setMaximum(20);
		slider_4.setValue(16);
		slider_4.setPaintTicks(true);
		slider_4.setPaintLabels(true);
		slider_4.setName("slider_4");
		panel_12.add(slider_4);
		
		JLabel label_3 = new JLabel("value");
		label_3.setName("label_3");
		panel_12.add(label_3);
		
		JLabel lblDilate = new JLabel("Dilate");
		lblDilate.setName("Dilate");
		panel_12.add(lblDilate);

		
//		frame2 = new JFrame();
//		frame2.setBounds(new Rectangle(10, 0, 0, 0));
//		
//		frame2.setVisible(true);
//		
//		frame2.setBounds(100, 50, 1786, 1056);
//		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		
//		JPanel main_panel_2 = new JPanel();
//		main_panel_2.setVisible(true);
//		frame2.getContentPane().add(main_panel_2, BorderLayout.CENTER);
//		main_panel_2.setLayout(null);
//		int count = 0;
//		for(int j =0;j<2;j++) {
//			for(int i =0;i<8;i++) {
//				int x = 5,y=11;
//				
//				
//				JPanel panel_sub = new JPanel();
//				panel_sub.setName("panel_sub_"+count);
//				panel_sub.setBounds(x+(511*j), (y+(110*i)), 500, 100);
//				panel_sub.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
//				main_panel_2.add(panel_sub);
//				
//				JLabel lbl_img_ = new JLabel("lbl_img_sub_"+count);
//				lbl_img_.setSize(new Dimension(100, 0));
//				lbl_img_.setLocation(new Point(250, 0));
//				lbl_img_.setHorizontalTextPosition(SwingConstants.CENTER);
//				lbl_img_.setHorizontalAlignment(SwingConstants.CENTER);
//				lbl_img_.setBounds(new Rectangle(250, 0, 100, 0));
//				lbl_img_.setAlignmentX(1.0f);
//				lbl_img_.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//				lbl_img_.setName("lbl_img_sub_"+count);
//				panel_sub.add(lbl_img_);
//				
//				
//				JLabel img_ = new JLabel("");
//				panel_sub.add(img_);
//				img_.setName("img_sub_"+count);
//
//				
//				
//				count++;
//			}
//		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		 JFrame jframe = new JFrame("Title");
// 	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 	    JLabel vidpanel = new JLabel();
// 	    jframe.setSize(640, 480);
// 	   
// 	    
// 	    JPanel panel_100 = new JPanel();
// 	    panel_100.setBounds(0, 0, 640, 480);
// 	    
// 	    panel_100.add(vidpanel);
// 	    jframe.add(panel_100);
// 		
// 	    jframe.setContentPane(vidpanel);
// 	    
// 	    
// 	    
// 	    jframe.setVisible(true);
//		
//		
// 	   hm.getComponentsFromJP(panel_100);
		
		
		
		
		
		
		
		hm.getComponentsFromJP(main_panel_1);
//		hm.getComponentsFromJP(main_panel_2);

		fileList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {

		            // Double-click detected
		            int index = list.locationToIndex(evt.getPoint());
		            selectedFile = arrFileList.getElementAt(index);
					 //graphics.startProccess(hm,selectedFile,getSliderVal("slider"),getSliderVal("slider_1"),getSliderVal("slider_2"),getSliderVal("slider_3"),getSliderVal("slider_4"));
		        
		            
		            
		            //graphics.start_loop(hm,selectedFile);
//		            graphics.process2(hm,selectedFile,getSliderVal("slider_3"));
//		            graphics.process2(hm,selectedFile);
//		            graphics.SWT(hm,selectedFile);
		            
		            graphics.process4(hm,selectedFile);



		        } 
		    }
		});
		

		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 int value = slider.getValue();;
				 label.setText(Integer.toString(value));

			}
		});
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 int value = slider_1.getValue();;
				 label_1.setText(Integer.toString(value));

			}
		});
		slider_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 int value = slider_2.getValue();;
				 label_2.setText(Integer.toString(value));

			}
		});
		slider_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 int value = slider_3.getValue();;
				 label_5.setText(Integer.toString(value));

			}
		});
		slider_4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 int value = slider_4.getValue();;
				 label_3.setText(Integer.toString(value));

			}
		});
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				 label.setText(Integer.toString(getSliderVal("slider")));
				 label_1.setText(Integer.toString(getSliderVal("slider_1")));
				 label_2.setText(Integer.toString(getSliderVal("slider_2")));
				 label_3.setText(Integer.toString(getSliderVal("slider_4")));
				 label_5.setText(Integer.toString(getSliderVal("slider_3")));


				 
			}
		});
		
		

	}
	
	
	public int getSliderVal(String sliderName) {
		Component component = hm.getComponentByName(sliderName);
		
		if (component instanceof JSlider) {

			JSlider slider = (JSlider) component;
			return slider.getValue();

		}
		
		return -1;
		
	}
}
