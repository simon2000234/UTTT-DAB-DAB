/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.bll.bot;

/**
 *
 * @author Richart hansen
 */
public class BestBot extends LocalPrioritisedListBot 
{
    private static final String BOTNAME="BestBost";
    int i = 1;
   
    public BestBot(){
         int[][] pref = {
             {2,0},{1,1},{0,2},
             {2,2},{2,1},{1,2},
             {0,1},{1,0},{0,0},};
        super.preferredMoves = pref;
    }
    
    
     @Override
    public String getBotName() {
        return BOTNAME; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
