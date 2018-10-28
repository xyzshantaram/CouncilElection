package defaultPkg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author sid
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class basicParser {

    //TODO: create setters for each parameter of candidateData();
    static final Object[] houses = {"0", "1", "2", "3"};
    
    static int house = Integer.parseInt(JOptionPane.showInputDialog(null, "Please select: \n0 for Blue,\n 1 for Green,\n 2 for Red,\n 3 for Yellow\n\n\n ",
            "Select house: ",
            JOptionPane.INFORMATION_MESSAGE,
            null,
            houses,
            "0").toString());
    
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
                FileIO.SOP("i: "+i+" j: "+j+" candidateVotes[i].get(j): " + candidateVotes[i].get(j));

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
        
        boolean quit = false;
        
        while (!quit) {
            for (int x = 0; x<= 5; x++) {
                votingDialog dialog = new votingDialog(null, true, x, candidates[x]);
                dialog.setVisible(true);
                int votedCandidate = dialog.getVote();
                candidates[x].get(votedCandidate).addVote(); 
            }

            switch (house) {
                case 0: for (int i = 6; i<= 7; i++) {
                            votingDialog dialog = new votingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            int votedCandidate = dialog.getVote();
                            candidates[i].get(votedCandidate).addVote(); 
                        }
                        break;
                case 1: for (int i = 8; i<= 9; i++) {
                            votingDialog dialog = new votingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            int votedCandidate = dialog.getVote();
                            candidates[i].get(votedCandidate).addVote();
                        }
                        break;
                case 2: for (int i = 10; i<= 11; i++) {
                            votingDialog dialog = new votingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            int votedCandidate = dialog.getVote();
                            candidates[i].get(votedCandidate).addVote();
                        }
                        break;
                case 3: for(int i = 12; i <= 13; i++) {
                        votingDialog dialog = new votingDialog(null, true, i, candidates[i]);
                        dialog.setVisible(true);
                        int votedCandidate = dialog.getVote();
                        candidates[i].get(votedCandidate).addVote();
                        }
                        break;
                default: break;
            }

            switch(JOptionPane.showConfirmDialog(null, "Quit?", "Please select: ", JOptionPane.YES_NO_OPTION)) {
                case JOptionPane.YES_OPTION: quit = true;
                                             break;
                case JOptionPane.NO_OPTION: quit = false;
                                            break;
                default: break;
            }
        
        }
        
        for (int i = 0; i <= 13; i++) {
            for (int x = 0; x < numCandidates[i]; x++) {
                lineArr.set(candidateVoteLineNos[i].get(x), Integer.toString(candidates[i].get(x).getVotes()));
            }
        }
        DBFileObject.dumpToFile(lineArr);
    }
    
}
