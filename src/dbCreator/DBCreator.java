/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbcreator;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Student
 */
public class DBCreator {

    static ArrayList<String> arr = new ArrayList();
    static int lineCounter = 0;
    static String nameStr = JOptionPane.showInputDialog(null, "Enter filename: ");
    
    public static void main(String[] args) {
        FileIO IOReadWrite = new FileIO(nameStr);
        IOReadWrite.readFileIntoArray(arr, lineCounter);
        IOReadWrite.message("Read " + lineCounter + " lines from " + nameStr);
        String position[] = {"HB", "HG", "Cul", "ACul", "SC", "ASC", "BHC", "ABHC", "GHC", "AGHC", "RHC", "ARHC", "YHC", "AYHC"};
        
        int i = JOptionPane.YES_OPTION;
        int j = 0;
        
            IOReadWrite.addXMLTag(false, "Candidates", arr);
            while (j <= 13) {
                IOReadWrite.message("Adding candidates for "+ position[j]);
                int numCand = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter no of candidates for " + position[j]));
                IOReadWrite.addXMLTag(false, position[j], arr);
                IOReadWrite.addXMLTag(false, position[j]+"No", arr);
                arr.add(""+numCand);
                IOReadWrite.addXMLTag(true, position[j]+"No", arr);
                for (int y = 0; y < numCand; y++) {
                    IOReadWrite.addXMLTag(false, position[j] + Integer.toString(y), arr);
                    IOReadWrite.addXMLTag(false, position[j] + Integer.toString(y) + "Votes", arr);
                    arr.add(Integer.toString(0));
                    IOReadWrite.addXMLTag(true, position[j] + Integer.toString(y) + "Votes", arr);
                    arr.add(JOptionPane.showInputDialog(null, "Enter name of " + position[j] + " Candidate no. " + Integer.toString(y)));
                    IOReadWrite.addXMLTag(false, position[j] + Integer.toString(y) + "Image", arr);
                    arr.add(JOptionPane.showInputDialog(null, "Enter image filename of " + position[j] + " Candidate no. " + Integer.toString(y)));
                    IOReadWrite.addXMLTag(true, position[j] + Integer.toString(y) + "Image", arr);
                    IOReadWrite.addXMLTag(true, position[j] + Integer.toString(y), arr);
                }
                
                IOReadWrite.addXMLTag(true, position[j], arr);
                j++;
            }
            IOReadWrite.addXMLTag(true, "Candidates", arr);
        IOReadWrite.dumpToFile(arr);

    }
}
