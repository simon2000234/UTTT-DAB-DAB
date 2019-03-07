/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.bll.bot;

import dk.easv.bll.field.IField;
import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;
import dk.easv.bll.move.Move;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Caspe
 */
public class BotTest implements IBot
{
    private static final String BOTNAME = "BotTest";
    private Random rand = new Random();
    int me;
    int enemy;
    protected int[][] preferredMoves = {
                                            //{0, 0}, {2, 2}, {0, 2}, {2, 0},
            {0, 1}, {2, 1}, {1, 0}, {1, 2},
            {1, 1}};
    protected List<Integer> myMarksX = new ArrayList<>();
    protected List<Integer> myMarksY = new ArrayList<>();

    @Override
    public IMove doMove(IGameState state)
    {
        String[][] currentBoard = state.getField().getBoard();
        String[][] checkstuffBoard = new String[9][9];
        String[][] currentMacro = state.getField().getMacroboard();
        String[][] macroCopy = new String[3][3];
        List<IMove> moves = state.getField().getAvailableMoves();
        List<IMove> prefRandomMoves = new ArrayList<>();
        List<IMove> macroFields = new ArrayList<>();
        List<IMove> macroMicroMoves = new ArrayList<>();
        int microX = ((moves.get(0).getX() / 3) * 3);
        int microY = ((moves.get(0).getY()) / 3 * 3);
        me = (state.getMoveNumber()) % 2;
        enemy = (state.getMoveNumber()+1) % 2;
        int numberOfMarksOnCurrentMicro = 0;
        int numberOfEnemyMarks = 0;
        
        

        
        macroFields.addAll(getAvailableMacroMoves(currentMacro));
        for (IMove macroField : macroFields)
        {
            copyMacroboard(macroCopy, currentMacro);
            macroCopy[macroField.getX()][macroField.getY()] = ""+me;
            if(isWin(macroCopy, macroField, ""+me))
            {
                copyCurrentMicro(checkstuffBoard, currentBoard);
                copyAndSetNextMicroForMacro(checkstuffBoard, macroField);
                macroMicroMoves.addAll(getAvailableMoves(checkstuffBoard));
                for (IMove macroMicroMove : macroMicroMoves)
                {
                    copyCurrentMicro(checkstuffBoard, currentBoard);
                    copyAndSetNextMicroForMacro(checkstuffBoard, macroField);
                    checkstuffBoard[macroMicroMove.getX()][macroMicroMove.getY()] = ""+me;
                    if(isWin(checkstuffBoard, macroMicroMove, ""+me))
                    {
                        return macroMicroMove;
                    }
                    copyCurrentMicro(checkstuffBoard, currentBoard);
                    copyAndSetNextMicroForMacro(checkstuffBoard, macroField);
                    checkstuffBoard[macroMicroMove.getX()][macroMicroMove.getY()] = ""+enemy;
                    if(isWin(checkstuffBoard, macroMicroMove, ""+enemy))
                    {
                        return macroMicroMove;
                    }
                }
            }
        }
            
            for (IMove macroField2 : macroFields)
            {
            copyMacroboard(macroCopy, currentMacro);
            macroCopy[macroField2.getX()][macroField2.getY()] = ""+enemy;
            if(isWin(macroCopy, macroField2, ""+enemy))
            {
                copyCurrentMicro(checkstuffBoard, currentBoard);
                copyAndSetNextMicroForMacro(checkstuffBoard, macroField2);
                macroMicroMoves.addAll(getAvailableMoves(checkstuffBoard));
                for (IMove macroMicroMove : macroMicroMoves)
                {
                    copyCurrentMicro(checkstuffBoard, currentBoard);
                    copyAndSetNextMicroForMacro(checkstuffBoard, macroField2);
                    checkstuffBoard[macroMicroMove.getX()][macroMicroMove.getY()] = ""+me;
                    if(isWin(checkstuffBoard, macroMicroMove, ""+me))
                    {
                        return macroMicroMove;
                    }
                    copyCurrentMicro(checkstuffBoard, currentBoard);
                    copyAndSetNextMicroForMacro(checkstuffBoard, macroField2);
                    checkstuffBoard[macroMicroMove.getX()][macroMicroMove.getY()] = ""+enemy;
                    if(isWin(checkstuffBoard, macroMicroMove, ""+enemy))
                    {
                        return macroMicroMove;
                    } 
                    
                }
            }   
            }
            
        for (IMove macroField : macroFields)
        {
            copyCurrentMicro(checkstuffBoard, currentBoard);
            copyAndSetNextMicroForMacro(checkstuffBoard, macroField);
            macroMicroMoves.addAll(getAvailableMoves(checkstuffBoard));
            for (IMove macroMicroMove : macroMicroMoves)
                {
                    copyCurrentMicro(checkstuffBoard, currentBoard);
                    copyAndSetNextMicroForMacro(checkstuffBoard, macroField);
                    checkstuffBoard[macroMicroMove.getX()][macroMicroMove.getY()] = ""+me;
                    if(isWin(checkstuffBoard, macroMicroMove, ""+me)==true)
                    {
                        return macroMicroMove;
                    }
                    copyCurrentMicro(checkstuffBoard, currentBoard);
                    copyAndSetNextMicroForMacro(checkstuffBoard, macroField);
                    checkstuffBoard[macroMicroMove.getX()][macroMicroMove.getY()] = ""+enemy;
                    if(isWin(checkstuffBoard, macroMicroMove, ""+enemy))
                    {
                        return macroMicroMove;
                    } 
                }
            
            
        }
        
        for (int i = 0 + microX; i < 3 + microX; i++)
        {
            for (int j = 0 + microY; j < 3 + microY; j++)
            {
                if (state.getField().getBoard()[i][j].equals("" + me))
                {
                    numberOfMarksOnCurrentMicro++;
                }
                if(state.getField().getBoard()[i][j].equals("" + enemy))
                {
                    numberOfEnemyMarks++;
                }
            }

        }
        if (numberOfMarksOnCurrentMicro > 1)
        {
            String[][] cMicro = new String[9][9];
            String myNumber = ""+me;
            for (IMove move : moves)
            {
                copyCurrentMicro(cMicro, currentBoard);
                cMicro[move.getX()][move.getY()] = myNumber;
                if(isWin(cMicro, move, myNumber)==true)
                {
//                    System.out.println("USED WINNING MICROBOARD MOVE");
                    return move;
                }
            }
        }
        if (numberOfEnemyMarks > 1)
        {
            String[][] cMicro = new String[9][9];
            String enemyNumber = ""+enemy;
            for (IMove move : moves)
            {
                copyCurrentMicro(cMicro, currentBoard);
                cMicro[move.getX()][move.getY()] = enemyNumber;
                if(isWin(cMicro, move, enemyNumber)==true)
                {
//                    System.out.println("USED ANTI ENEMY MOVE");
                    return move;
                }         
            }
        }
        if (moves.size()>0)
        {
            String[][] cMicro = new String[9][9];
            copyCurrentMicro2(cMicro, currentBoard, moves);
            String[][] nMicro = new String[9][9];
            String theEnemy = ""+enemy;
            for (int i = 0; i < moves.size(); i++)
            {
                if(moves.get(i).getX()==0 || moves.get(i).getX()==3 || moves.get(i).getX()==6)
                {
                    if(moves.get(i).getY()==0 || moves.get(i).getY()==3 || moves.get(i).getY()==6)
                    {
                        IMove aMove = new Move(moves.get(i).getX(),moves.get(i).getY()); 
                        copyCurrentMicro2(nMicro, cMicro, moves);
                        copyAndSetNextMicro(nMicro, aMove);
                        List<IMove> nextEnemyMoves = getAvailableMoves(nMicro);
                        boolean enemyCanWin = false;
                        
                            for (IMove nextEnemyMove : nextEnemyMoves)
                            {
                                copyCurrentMicro2(nMicro, cMicro, moves);
                                copyAndSetNextMicro(nMicro, aMove);
                                nMicro[nextEnemyMove.getX()][nextEnemyMove.getY()] = theEnemy;
                                
                                if(isWin(nMicro, nextEnemyMove, theEnemy))
                                {
                                    enemyCanWin = true;
                                }
                            }
                            if(enemyCanWin == false)
                                {
                                    prefRandomMoves.add(aMove);
                                }
                            
                    }
                }
                if(moves.get(i).getX()==2 || moves.get(i).getX()==5 || moves.get(i).getX()==8)
                {
                    if(moves.get(i).getY()==2 || moves.get(i).getY()==5 || moves.get(i).getY()==8)
                    {
                        IMove aMove = new Move(moves.get(i).getX(),moves.get(i).getY()); 
                        copyCurrentMicro2(nMicro, cMicro, moves);
                        copyAndSetNextMicro(nMicro, aMove);
                        List<IMove> nextEnemyMoves = getAvailableMoves(nMicro);
                        boolean enemyCanWin = false;
                        
                            for (IMove nextEnemyMove : nextEnemyMoves)
                            {
                                copyCurrentMicro2(nMicro, cMicro, moves);
                                copyAndSetNextMicro(nMicro, aMove);
                                nMicro[nextEnemyMove.getX()][nextEnemyMove.getY()] = theEnemy;
                                
                                if(isWin(nMicro, nextEnemyMove, theEnemy))
                                {
                                    enemyCanWin = true;
                                }
                            }
                            if(enemyCanWin == false)
                                {
                                    prefRandomMoves.add(aMove);
                                }
                    }
                }
                if(moves.get(i).getX()==0 || moves.get(i).getX()==3 || moves.get(i).getX()==6)
                {
                    if(moves.get(i).getY()==2 || moves.get(i).getY()==5 || moves.get(i).getY()==8)
                    {
                        IMove aMove = new Move(moves.get(i).getX(),moves.get(i).getY()); 
                        copyCurrentMicro2(nMicro, cMicro, moves);
                        copyAndSetNextMicro(nMicro, aMove);
                        List<IMove> nextEnemyMoves = getAvailableMoves(nMicro);
                        boolean enemyCanWin = false;
                        
                            for (IMove nextEnemyMove : nextEnemyMoves)
                            {
                                copyCurrentMicro2(nMicro, cMicro, moves);
                                copyAndSetNextMicro(nMicro, aMove);
                                nMicro[nextEnemyMove.getX()][nextEnemyMove.getY()] = theEnemy;
                                
                                if(isWin(nMicro, nextEnemyMove, theEnemy))
                                {
                                    enemyCanWin = true;
                                }
                            }
                            if(enemyCanWin == false)
                                {
                                    prefRandomMoves.add(aMove);
                                }
                    }
                }
                if(moves.get(i).getX()==2 || moves.get(i).getX()==5 || moves.get(i).getX()==8)
                {
                    if(moves.get(i).getY()==0 || moves.get(i).getY()==3 || moves.get(i).getY()==6)
                    {
                        IMove aMove = new Move(moves.get(i).getX(),moves.get(i).getY()); 
                        copyCurrentMicro2(nMicro, cMicro, moves);
                        copyAndSetNextMicro(nMicro, aMove);
                        List<IMove> nextEnemyMoves = getAvailableMoves(nMicro);
                        boolean enemyCanWin = false;
                        
                            for (IMove nextEnemyMove : nextEnemyMoves)
                            {
                                copyCurrentMicro2(nMicro, cMicro, moves);
                                copyAndSetNextMicro(nMicro, aMove);
                                nMicro[nextEnemyMove.getX()][nextEnemyMove.getY()] = theEnemy;
                                
                                if(isWin(nMicro, nextEnemyMove, theEnemy))
                                {
                                    enemyCanWin = true;
                                }
                            }
                            if(enemyCanWin == false)
                                {
                                    prefRandomMoves.add(aMove);
                                }
                    }
                }
            }
            if (prefRandomMoves.size() > 0) {
//                System.out.println("USED A PREFERED MOVE");
            return prefRandomMoves.get(rand.nextInt(prefRandomMoves.size())); /* get random move from available moves */
            }
            for (int[] move : preferredMoves)
            {
            if(state.getField().getMacroboard()[move[0]][move[1]].equals(IField.AVAILABLE_FIELD))
            {
                //find move to play
                for (int[] selectedMove : preferredMoves)
                {
                    int x = move[0]*3 + selectedMove[0];
                    int y = move[1]*3 + selectedMove[1];
                    if(state.getField().getBoard()[x][y].equals(IField.EMPTY_FIELD))
                    {
                        IMove aMove = new Move(x,y);
                        copyCurrentMicro2(nMicro, cMicro, moves);
                        copyAndSetNextMicro(nMicro, aMove);
                        List<IMove> nextEnemyMoves = getAvailableMoves(nMicro);
                        boolean enemyCanWin = false;
                        
                            for (IMove nextEnemyMove : nextEnemyMoves)
                            {
                                copyCurrentMicro2(nMicro, cMicro, moves);
                                copyAndSetNextMicro(nMicro, aMove);
                                nMicro[nextEnemyMove.getX()][nextEnemyMove.getY()] = ""+enemy;
                                
                                if(isWin(nMicro, nextEnemyMove, theEnemy))
                                {
                                    enemyCanWin = true;
                                }
                            }
                            if(enemyCanWin == false)
                                {
//                                    System.out.println("USED A BACKUP MOVE AS NO PREFEREDMOVES WAS AVAILABLE");
                                    return aMove;
                                }
                    }
                }
            }
            }
        }

        //NOTE: Something failed, just take the first available move I guess!
//        System.out.println("SOMETHING WENT WRONG");
        return state.getField().getAvailableMoves().get(0);
    }

    @Override
    public String getBotName()
    {
        return BOTNAME;
    }
    
    public static boolean isWin(String[][] board, IMove move, String currentPlayer){
        int localX = move.getX() % 3;
        int localY = move.getY() % 3;
        int startX = move.getX() - (localX);
        int startY = move.getY() - (localY);

        //check col
        for (int i = startY; i < startY + 3; i++) {
            if (!board[move.getX()][i].equals(currentPlayer))
                break;
            if (i == startY + 3 - 1) return true;
        }

        //check row
        for (int i = startX; i < startX + 3; i++) {
            if (!board[i][move.getY()].equals(currentPlayer))
                break;
            if (i == startX + 3 - 1) return true;
        }

        //check diagonal
        if (localX == localY) {
            //we're on a diagonal
            int y = startY;
            for (int i = startX; i < startX + 3; i++) {
                if (!board[i][y++].equals(currentPlayer))
                    break;
                if (i == startX + 3 - 1) return true;
            }
        }

        //check anti diagonal
        if (localX + localY == 3 - 1) {
            int less = 0;
            for (int i = startX; i < startX + 3; i++) {
                if (!board[i][(startY + 2)-less++].equals(currentPlayer))
                    break;
                if (i == startX + 3 - 1) return true;
            }
        }
        return false;
    }
    
    private void copyCurrentMicro(String[][] copyDest, String[][] current)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                copyDest[i][j] = current[i][j];            
            }
            
        }
//         for (String[] strings : copyDest)
//        {
//            System.out.println(""+Arrays.toString(strings));
//        }
        
//        return copyDest;
    }
    
    private void copyMacroboard(String[][] Dest, String[][] current)
    {
        for (int i = 0; i < current.length; i++)
        {
            for (int j = 0; j < current.length; j++)
            {
                Dest[i][j] = current[i][j];
                
            }   
        }
        
//        for (String[] strings : Dest)
//        {
//            System.out.println(""+Arrays.toString(strings));
//        }
        
    }
    
    private void copyCurrentMicro2(String[][] copyDest, String[][] current, List<IMove> moves)
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                copyDest[i][j] = current[i][j];            
            }
            
        }
        for (IMove move : moves)
        {
            copyDest[move.getX()][move.getY()] = IField.AVAILABLE_FIELD;
        }
//         for (String[] strings : copyDest)
//        {
//            System.out.println(""+Arrays.toString(strings));
//        }
        
//        return copyDest;
    }
    
    private void copyAndSetNextMicro(String[][] copiedMicro, IMove move)
    {
        int modX = move.getX() % 3;
        int modY = move.getY() % 3;
        int microX = modX*3;
        int microY = modY*3;        
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if(copiedMicro[i][j].equals(IField.AVAILABLE_FIELD))
                {
                    copiedMicro[i][j] = IField.EMPTY_FIELD;
                }
                
            }
        }
        
        for (int i = 0 + microX; i < 3+microX; i++)
        {
            for (int j = 0 + microY; j < 3+microY; j++)
            {
                if(copiedMicro[i][j].equals(IField.EMPTY_FIELD))
                {
                copiedMicro[i][j] = IField.AVAILABLE_FIELD;
                }
            }
        }
//        System.out.println("Possible move: "+microX+","+microY+"\n"+"FROM "+move.getX()+","+move.getY()+
//                "\n"+"MODX= "+modX+" MODY= "+modY);
//        
//        for (String[] strings : copiedMicro)
//        {
//            System.out.println(""+Arrays.toString(strings));
//        }
        
    }
    
    private void copyAndSetNextMicroForMacro(String[][] copiedMicro, IMove move)
    {
        int microX = move.getX()*3;
        int microY = move.getY()*3;        
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if(copiedMicro[i][j].equals(IField.AVAILABLE_FIELD))
                {
                    copiedMicro[i][j] = IField.EMPTY_FIELD;
                }
                
            }
        }
        
        for (int i = 0 + microX; i < 3+microX; i++)
        {
            for (int j = 0 + microY; j < 3+microY; j++)
            {
                if(copiedMicro[i][j].equals(IField.EMPTY_FIELD))
                {
                copiedMicro[i][j] = IField.AVAILABLE_FIELD;
                }
            }
        }
//        System.out.println("Possible move: "+microX+","+microY+"\n"+"FROM "+move.getX()+","+move.getY()+
//                "\n"+"MODX= "+modX+" MODY= "+modY);
//        
//        for (String[] strings : copiedMicro)
//        {
//            System.out.println(""+Arrays.toString(strings));
//        }
        
    }
    
    public List<IMove> getAvailableMoves(String[][] nextBoard) {
        ArrayList<IMove> theList = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if(nextBoard[i][j].equals(IField.AVAILABLE_FIELD))
                {
                    theList.add(new Move(i,j));
                }  
            }
        }
        return theList;
    }
    
    public List<IMove> getAvailableMacroMoves(String[][] nextBoard) {
        ArrayList<IMove> theList = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if(nextBoard[i][j].equals(IField.AVAILABLE_FIELD))
                {
                    theList.add(new Move(i,j));
                }  
            }
        }
        return theList;
    }

}
