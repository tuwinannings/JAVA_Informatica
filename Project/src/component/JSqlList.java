package component;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * @author Willem van Vliet
 * heavily refactored in december 2008 by linda
 */



public class JSqlList extends JScrollPane {

  private JList list = null;
  private Object[] dbObjecten;

  public JSqlList() {
    super();
    list = new JList();
    this.setViewportView(list);
    list.setFont(new Font("Courier 10 Pitch", Font.PLAIN, 11));
  }

  public void fillList(Object[] objecten) {
    this.dbObjecten = objecten;

    ArrayList<String> fillList = new ArrayList<String>();
    for (Object o : dbObjecten) {
      fillList.add(o.toString());      
    }
    this.list.setListData(fillList.toArray());
  }
  
  private int getSelectedIndex() {
    return this.list.getSelectedIndex();
  }

  public Object getSelectedObject() {
    if (dbObjecten == null || getSelectedIndex() < 0) {
      System.out.println("JSqlList: getSelectedObject: geen rij uit de lijst geselecteerd");
      return null;
    } else if (getSelectedIndex() < dbObjecten.length) {
      return dbObjecten[getSelectedIndex()];
    }
    return null;
  }

  public void setListFont(Font f){
//    kies een mono spaced font, zodat setSpaces werkt
    list.setFont(f);
    System.out.println("listfont: " + list.getFont());

  }
  
  public int getRowCount() {
    return this.list.getLastVisibleIndex();
  }

  public JList getJList() {
    // Backup for forgotten methods
    return this.list;
  } 

  public void setSelectedIndex(int index) {
    this.list.setSelectedIndex(index);
  }

  public void setSelectedIndices(int[] index) {
    this.list.setSelectedIndices(index);
  }
  /**
   * 
   * geeft alleen nuttige output als er een monospaced font geset is.
   * 
   * @param input
   * @param length
   * @return
   */
  public static String setSpaces(String input, int length) {
    String output = input;
    if (length > 255) {
      length = 255;
    }
    if (input.length() >= length) {
      return input.substring(0, length) + " ";
    }
    int aantal = 0;
    for (int i = input.length(); i <= length; i++) {
      aantal++;
      output += " ";
    }
//    System.out.println(input + "("+aantal+"):" + length);
    return output + " ";
  }

}
