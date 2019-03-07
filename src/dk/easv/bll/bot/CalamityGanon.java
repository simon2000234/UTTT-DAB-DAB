/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.bll.bot;

import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;

/**
 *
 * @author Melchertsen
 */
public class CalamityGanon extends LocalPrioritisedListBot
{

    private static final String BOTNAME = "Calamity Ganon";

    public CalamityGanon()
    {
        int[][] bestMove = {{1,0},{2,1},{2,0},{0,0},{2,2}};
        super.preferredMoves = bestMove;
    }
    @Override
    public String getBotName()
    {
        return BOTNAME;
    }
    
}
