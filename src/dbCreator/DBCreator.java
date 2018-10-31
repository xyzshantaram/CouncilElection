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

    static final Object[] MODES = {"Clear all votes", "New DB"};
    static String modeStr = JOptionPane.showInputDialog(null, "Please select: ",
            "Select house: ",
            JOptionPane.INFORMATION_MESSAGE,
            null,
            MODES,
            "0").toString();

    public static void main(String[] args) {
        boolean notClear = true;
        if (modeStr.equals("Clear All Votes")) {
            notClear = false;
        } else {
            notClear = true;
        }
        if (!notClear) {
            FileIO IOReadWrite = new FileIO(nameStr);
            IOReadWrite.readFileIntoArray(arr, lineCounter);
            IOReadWrite.message("Read " + lineCounter + " lines from " + nameStr);
            String position[] = {"HB", "HG", "Cul", "ACul", "SC", "ASC", "BHC", "ABHC", "GHC", "AGHC", "RHC", "ARHC", "YHC", "AYHC"};

            int i = JOptionPane.YES_OPTION;
            int j = 0;

            IOReadWrite.addXMLTag(false, "Candidates", arr);
            while (j <= 13) {
                IOReadWrite.message("Adding candidates for " + position[j]);
                int numCand;
                try {
                    numCand = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter no of candidates for " + position[j]));
                } catch (NumberFormatException n) {
                    IOReadWrite.message("Numerics only, please!");
                    numCand = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter no of candidates for " + position[j]));
                }
                IOReadWrite.addXMLTag(false, position[j], arr);
                IOReadWrite.addXMLTag(false, position[j] + "No", arr);
                arr.add("" + numCand);
                IOReadWrite.addXMLTag(true, position[j] + "No", arr);
                for (int y = 0; y < numCand; y++) {
                    IOReadWrite.addXMLTag(false, position[j] + Integer.toString(y), arr);
                    IOReadWrite.addXMLTag(false, position[j] + Integer.toString(y) + "Votes", arr);
                    arr.add(Integer.toString(0));
                    IOReadWrite.addXMLTag(true, position[j] + Integer.toString(y) + "Votes", arr);
                    arr.add(JOptionPane.showInputDialog(null, "Enter name of " + position[j] + " Candidate no. " + Integer.toString(y + 1)));
                    IOReadWrite.addXMLTag(false, position[j] + Integer.toString(y) + "Image", arr);
                    arr.add(JOptionPane.showInputDialog(null, "Enter image filename of " + position[j] + " Candidate no. " + Integer.toString(y + 1)));
                    IOReadWrite.addXMLTag(true, position[j] + Integer.toString(y) + "Image", arr);
                    IOReadWrite.addXMLTag(false, position[j] + Integer.toString(y) + "Slogan", arr);
                    arr.add(JOptionPane.showInputDialog(null, "Enter slogan of " + position[j] + " Candidate no. " + Integer.toString(y + 1)));
                    IOReadWrite.addXMLTag(true, position[j] + Integer.toString(y) + "Slogan", arr);
                    IOReadWrite.addXMLTag(true, position[j] + Integer.toString(y), arr);
                }

                IOReadWrite.addXMLTag(true, position[j], arr);
                j++;
            }
            IOReadWrite.addXMLTag(true, "Candidates", arr);
            IOReadWrite.dumpToFile(arr);
        } else {

            FileIO DBFileObject = new FileIO(JOptionPane.showInputDialog(null, "Enter database filename: "));
            int no = 0;
            ArrayList<String> lineArr = new ArrayList<>();
            DBFileObject.readFileIntoArray(lineArr, no);
            ArrayList<Integer>[] candidateSerials;
            candidateSerials = new ArrayList[14];

            String position[] = {"HB", "HG", "Cul", "ACul", "SC", "ASC", "BHC", "ABHC", "GHC", "AGHC", "RHC", "ARHC", "YHC", "AYHC"};
            int numCandidates[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            for (int i = 0; i <= 13; i++) {
                candidateSerials[i] = new ArrayList();
                int lineNoOfNum = DBFileObject.searchForLineNumber("<" + position[i] + "No>", lineArr, lineArr.size()) + 1;
                numCandidates[i] = Integer.parseInt(DBFileObject.getFromLineNo(lineNoOfNum, lineArr));

                for (int j = 0; j < numCandidates[i]; j++) {
                    String openingTag = "<" + position[i] + j + "Votes>";
                    int reqNo = DBFileObject.searchForLineNumber(openingTag, lineArr, lineArr.size());
                    lineArr.set(reqNo + 1, "0");
                }

            }
        }
    }
}
