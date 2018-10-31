package defaultPkg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 * @author sid
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BasicParser {

    //TODO: create setters for each parameter of candidateData();
    static final Object[] HOUSES = {"0", "1", "2", "3", "4"};
    
    static int house = Integer.parseInt(JOptionPane.showInputDialog(null, "Please select: \n0 for Blue,\n 1 for Green,\n 2 for Red,\n 3 for Yellow,\n4 if this is a teacher's machine\n\n\n ",
            "Select house: ",
            JOptionPane.INFORMATION_MESSAGE,
            null,
            HOUSES,
            "0").toString());
    
    
    static ArrayList<CandidateData>[] candidates = new ArrayList[14];
        
    public static void main(String[] args) {
        FileIO DBFileObject = new FileIO(JOptionPane.showInputDialog(null, "Enter database filename: "));
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
        ArrayList<String>[] candidateImgs;
        candidateImgs = new ArrayList[14];
        ArrayList<String>[] candidateSlogans;
        candidateSlogans = new ArrayList[14];
        
        String position[] = {"HB", "HG", "Cul", "ACul", "SC", "ASC", "BHC", "ABHC", "GHC", "AGHC", "RHC", "ARHC", "YHC", "AYHC"};
        int numCandidates[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0};
        
        for (int i = 0; i <= 13; i++) {
            candidateNames[i] = new ArrayList();
            candidates[i] = new ArrayList();
            candidateVotes[i] = new ArrayList();
            candidateSerials[i] = new ArrayList();
            candidateImgs[i] = new ArrayList();
            candidateVoteLineNos[i] = new ArrayList();
            candidateSlogans[i] = new ArrayList();
            
            int lineNoOfNum = DBFileObject.searchForLineNumber("<"+position[i]+"No>", lineArr, lineArr.size())+1;
            numCandidates[i] = Integer.parseInt(DBFileObject.getFromLineNo(lineNoOfNum, lineArr));
        
            for (int j = 0; j < numCandidates[i]; j++) {
                String openingTag = "<" + position[i] + j + "Votes>";
                String openingImgTag = "<" + position[i] + j +"Image>";
                String closingTag = "</" + position[i] + j +"Votes>";
                String sloganOpening = "<" +position[i] + j + "Slogan>";
                int reqNo = DBFileObject.searchForLineNumber(openingTag, lineArr, lineArr.size());
                int reqImgNo = DBFileObject.searchForLineNumber(openingImgTag, lineArr, lineArr.size());
                int sloganNo = DBFileObject.searchForLineNumber(sloganOpening, lineArr, lineArr.size());
                candidateVotes[i].add(Integer.parseInt(DBFileObject.getFromLineNo(reqNo+1, lineArr)));
                candidateImgs[i].add(DBFileObject.getFromLineNo(reqImgNo+1, lineArr));
                candidateSlogans[i].add(DBFileObject.getFromLineNo(sloganNo+1, lineArr));
                candidateVoteLineNos[i].add(reqNo+1);

                String nextName = DBFileObject.getFromLineNo(DBFileObject.searchForLineNumber(closingTag, lineArr, lineArr.size())+1, lineArr);                  
                candidateNames[i].add(j, nextName);
                candidateSerials[i].add(j);
            }
            
        }
        
        for (int i = 0; i <= 13; i++) {

            for (int x = 0; x < candidateNames[i].size(); x++) {
                candidates[i].add(new CandidateData(i, candidateSerials[i].get(x), candidateVotes[i].get(x),  candidateNames[i].get(x), candidateImgs[i].get(x), candidateSlogans[i].get(x)));
                candidates[i].get(x).setVotes(candidateVotes[i].get(x));
                DBFileObject.SOP("Created candidate with name " + candidates[i].get(x).getName() + ", serial "
                + candidates[i].get(x).getSerial() + ", Position: " + position[candidates[i].get(x).getPosn()] 
                       + ", votes: " + candidates[i].get(x).getVotes() + " and Slogan: " + candidates[i].get(x).getSlogan());
            }

        }
        
        boolean quit = false;
        
        while (!quit) {
            int[] votedCandidates = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
            for (int x = 0; x<= 5; x++) {
                VotingDialog dialog = new VotingDialog(null, true, x, candidates[x]);
                dialog.setVisible(true);
                votedCandidates[x] = dialog.getVote();
                /* int votedCandidate = dialog.getVote();
                candidates[x].get(votedCandidate).addVote(); 
                */
            }

            switch (house) {
                case 0: for (int i = 6; i<= 7; i++) {
                            VotingDialog dialog = new VotingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            votedCandidates[i] = dialog.getVote();
                            /* int votedCandidate = dialog.getVote();
                            candidates[i].get(votedCandidate).addVote();
                            */
                        }
                        break;
                case 1: for (int i = 8; i<= 9; i++) {
                            VotingDialog dialog = new VotingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            votedCandidates[i] = dialog.getVote();
                            /*
                            int votedCandidate = dialog.getVote();
                            candidates[i].get(votedCandidate).addVote();
                            */
                        }
                        break;
                case 2: for (int i = 10; i<= 11; i++) {
                            VotingDialog dialog = new VotingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            votedCandidates[i] = dialog.getVote();
                            /*
                            int votedCandidate = dialog.getVote();
                            candidates[i].get(votedCandidate).addVote();
                            */
                        }
                        break;
                case 3: for(int i = 12; i <= 13; i++) {
                            VotingDialog dialog = new VotingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            votedCandidates[i] = dialog.getVote();
                        /*
                        int votedCandidate = dialog.getVote();
                        candidates[i].get(votedCandidate).addVote();
                        */
                        }
                        break;
                case 4: for (int i = 6; i <= 13; i++) {
                            VotingDialog dialog = new VotingDialog(null, true, i, candidates[i]);
                            dialog.setVisible(true);
                            votedCandidates[i] = dialog.getVote();
                            /*
                            int votedCandidate = dialog.getVote();
                            candidates[i].get(votedCandidate).addVote();
                            */
                        }
                        break;
                default: break;
            }
            VoterIDDialog dialog2 = new VoterIDDialog(null, true, "dupes.txt");
            dialog2.setVisible(true);
            if (!dialog2.checkForDupes()) {
                for (int i = 0; i <= 13; i++) {
                    if (votedCandidates[i] != -1) {
                    candidates[i].get(votedCandidates[i]).addVote();
                    }
                }
            } 
            else {
                DBFileObject.message("Duplication detected, discarded votes.");
            }
            
            switch(JOptionPane.showConfirmDialog(null, "Thank you for voting. Stop? ", "Please select: ", JOptionPane.YES_NO_OPTION)) {
                case JOptionPane.YES_OPTION: PasswordDialog dialog = new PasswordDialog(null, true);
                                             dialog.setVisible(true);
                                             if (dialog.validate("APSElection")) {
                                                 quit = true;
                                                 DBFileObject.message("Password validated successfully, quitting.");
                                             }
                                             else {
                                                 DBFileObject.message("Invalid password, will not exit.");
                                             }
                                             break;
                case JOptionPane.NO_OPTION: quit = false;
                                            DBFileObject.message("Next voter please!");
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
        System.exit(0);
    }
    
}
