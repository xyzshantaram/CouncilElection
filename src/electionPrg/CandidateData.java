/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package defaultPkg;

/**
 *
 * @author Student
 */
public class CandidateData {
    int posn = 0;
    int votes = 0;
    int serialFromFile = 0;
    String name = "";
    String img = "";
    
    public CandidateData(int post, int serial, int votes, String s, String image) {
        posn = post;
        serialFromFile = serial;
        name = s;
        img = image;
    }
    
    public void addVote() {
        votes++;
    }
    
    public int getSerial() {
        return serialFromFile;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPosn() {
        return posn;
    }
    
    public int getVotes() {
        return votes;
    }

    public void setVotes(int i) {
        votes = i;
    }
    
    public String getImageName() {
        return img;
    }
    
    public void setImageName(String s) {
        img = s;
    }
    
}
