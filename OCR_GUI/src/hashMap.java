import java.awt.Component;
import java.util.HashMap;

import javax.swing.JPanel;

public class hashMap {
	private HashMap<String, Component> componentMap = new HashMap<String,Component>();

	
	public void getComponentsFromJP(Component jp) {
        Component[] components = ((JPanel)jp).getComponents();
        for (int i=0; i < components.length; i++) {
        	   if (components[i] instanceof JPanel) {
        		   getComponentsFromJP(components[i]);
               }else {
            	 //  System.out.println(components[i].getName());
            	   componentMap.put(components[i].getName(), components[i]);
               }
        }
	}
	


		public Component getComponentByName(String name) {
		        if (componentMap.containsKey(name)) {
		                return (Component) componentMap.get(name);
		        }
		        else return null;
		}
		
}
