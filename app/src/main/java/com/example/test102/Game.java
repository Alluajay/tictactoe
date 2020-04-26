package com.example.test102;

import android.util.Log;

public class Game
{
    private static int X_VALUE = -1;
    private static int Y_VALUE = 1;

    public static class EXCEPTIONS
    {
        public static final String ALREADY_ADDED = "aa";
        public static final String GAME_OVER = "go";
        public static final String GAME_COMPLETED = "gc";
    }

    public enum MOVE_VALUE
    {
        X_VALUE,
        Y_VALUE;
    }

    public enum PLAYER
    {
        PLAYER_1,
        PLAYER_2;
    }

    int move;
    int[][] gameMat;
    PLAYER winner;

    public Game()
    {
        move = 0;
        winner = null;
        gameMat = new int[3][3];
    }

    public void refresh()
    {
        move = 0;
        winner = null;
        gameMat = new int[3][3];
    }

    public PLAYER getTurn()
    {
        return move % 2 == 0 ? PLAYER.PLAYER_1 : PLAYER.PLAYER_2;
    }

    public MOVE_VALUE getMoveValue()
    {
        return (getTurn() == PLAYER.PLAYER_1)? MOVE_VALUE.X_VALUE : MOVE_VALUE.Y_VALUE;
    }

    public PLAYER setMoveValue(int xPos, int yPos) throws Exception
    {
        if(winner != null)
        {
            throw new Exception(EXCEPTIONS.GAME_COMPLETED);
        }

        if(move > 8){
            throw new Exception(EXCEPTIONS.GAME_OVER);
        }

        Log.d("TEXT", "setMoveValue: x - "+xPos+" | y - "+yPos+" | val - "+gameMat[xPos][yPos]);
        if(gameMat[xPos][yPos] != 0){
            throw new Exception(EXCEPTIONS.ALREADY_ADDED);
        }

        MOVE_VALUE value = getMoveValue();

        move++;
        gameMat[xPos][yPos] = (MOVE_VALUE.X_VALUE == value) ? X_VALUE : Y_VALUE;

        if(move > 4 && validate(value))
        {
            winner = value == MOVE_VALUE.X_VALUE ? PLAYER.PLAYER_1 : PLAYER.PLAYER_2;
        }

        if(move > 8 && winner == null){
            throw new Exception(EXCEPTIONS.GAME_OVER);
        }

        return winner;
    }

    public boolean validate(MOVE_VALUE value)
    {
        int validationValue = value == MOVE_VALUE.X_VALUE ? X_VALUE : Y_VALUE;

        for(int i = 0; i < 3; i++){
            if(checkRow(i, validationValue) || checkCol(i, validationValue)){
                return true;
            }
        }

        if(gameMat[1][1] == validationValue){
            /**
             * DiagonalCheck
             */
            if((gameMat[0][0] == validationValue && gameMat[2][2] == validationValue) || (gameMat[0][2] == validationValue && gameMat[2][0] == validationValue)){
                return true;
            }
        }
        return false;
    }

    public boolean checkRow(int pos, int val)
    {
        return gameMat[pos][0] == val && gameMat[pos][1] == val && gameMat[pos][2] == val;
    }

    public boolean checkCol(int pos, int val)
    {
        return gameMat[0][pos] == val && gameMat[1][pos] == val && gameMat[2][pos] == val;
    }

}
