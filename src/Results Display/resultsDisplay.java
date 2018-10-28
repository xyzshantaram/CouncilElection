/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package results.display;

import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author sid
 */
public class resultsDisplay {
    
    static String[] fancyPosn = {"Head Boy", "Head Girl", "Cultural Secretary", "Assistant Cultural Secretary", "Sports Captain", "Assistant Sports Captain",
                         "Blue House Captain", "Assistant Blue House Captain", "Green House Captain", "Assistant Green House Captain",
                         "Red House Captain", "Assistant Red House Captain", "Yellow House Captain", "Assistant Yellow House Captain"};
    
    static FileIO DBFileObject = new FileIO(JOptionPane.showInputDialog(null, "Enter database filename: "));
    static ArrayList<candidateData>[] candidates = new ArrayList[14];
        
    public static void main(String[] args) {
        int no = 0;
        ArrayList<String> lineArr = new ArrayList<>();
        DBFileObject.readFileIntoArray(lineArr, no);
        ArrayList<String>[] candidateNames;
        candidateNames = new ArrayList[14];
        ArrayList<Integer>[] candidateVotes;
        candidateVotes = new ArrayList[14];
        ArrayList<Integer>[] candidateSerials;
        candidateSerials = new ArrayList[14];
        ArrayList<Integer>[] candidateVoteLineNos;
        candidateVoteLineNos = new ArrayList[14];
        
        String position[] = {"HB", "HG", "Cul", "ACul", "SC", "ASC", "BHC", "ABHC", "GHC", "AGHC", "RHC", "ARHC", "YHC", "AYHC"};
        int numCandidates[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0};
        
        for (int i = 0; i <= 13; i++) {
            candidateNames[i] = new ArrayList();
            candidates[i] = new ArrayList();
            candidateVotes[i] = new ArrayList();
            candidateSerials[i] = new ArrayList();
            candidateVoteLineNos[i] = new ArrayList();
            
            int lineNoOfNum = DBFileObject.searchForLineNumber("<"+position[i]+"No>", lineArr, lineArr.size())+1;
            numCandidates[i] = Integer.parseInt(DBFileObject.getFromLineNo(lineNoOfNum, lineArr));
        
            for (int j = 0; j < numCandidates[i]; j++) {
                String openingTag = "<"+position[i]+j+"Votes>";
                String closingTag = "</" + position[i] + j +"Votes>";
                int reqNo = FileIO.searchForLineNumber(openingTag, lineArr, lineArr.size());
                candidateVotes[i].add(Integer.parseInt(FileIO.getFromLineNo(reqNo+1, lineArr)));
                candidateVoteLineNos[i].add(reqNo+1);

                String nextName = DBFileObject.getFromLineNo(DBFileObject.searchForLineNumber(closingTag, lineArr, lineArr.size())+1, lineArr);                 
                candidateNames[i].add(j, nextName);
                candidateSerials[i].add(j);
            }
            
        }
        
        for (int i = 0; i <= 13; i++) {

            for (int x = 0; x < candidateNames[i].size(); x++) {
                candidates[i].add(new candidateData(i, candidateSerials[i].get(x), candidateVotes[i].get(x),  candidateNames[i].get(x)));
                candidates[i].get(x).setVotes(candidateVotes[i].get(x));
                FileIO.SOP("Created candidate with name " + candidates[i].get(x).getName() + ", serial "
                + candidates[i].get(x).getSerial() + " Position: " + position[candidates[i].get(x).getPosn()] 
                       + " and votes: " + candidates[i].get(x).getVotes());
            }

        }
        
        String resultString = "Results for Student Council Election from this database are as follows: \n";
        
        for (int i = 0; i <= 13; i++) {
            resultString = resultString + "\n\nVotes for the position of " + fancyPosn[i] + " are as follows: \n\n"; 
            for (int j = 0; j < numCandidates[i]; j++) {
                resultString = resultString + candidates[i].get(j).getName() + ": " + candidates[i].get(j).getVotes() + " \n";
            }
        }
        
        new ScrollPaneForDisplay(null, true, resultString).setVisible(true);
    }
}
