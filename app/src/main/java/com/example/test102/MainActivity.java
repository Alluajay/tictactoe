package com.example.test102;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    /**
     * Views
     */
    LinearLayout matLayout, playerOne, playerTwo;
    Button start, refresh;

    Game game;

    static boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        matLayout = (LinearLayout)findViewById(R.id.mat_layout);
        gameOver = false;

        playerOne = (LinearLayout)findViewById(R.id.payer_1);
        playerTwo = (LinearLayout)findViewById(R.id.payer_2);

        /**
         * Load player Details
         */
        TextView playerName = (TextView)playerOne.findViewById(R.id.player_name);
        playerName.setText("Player 1");

        playerName = (TextView)playerTwo.findViewById(R.id.player_name);
        playerName.setText("Player 2");

        ImageView playerStatus = (ImageView)playerOne.findViewById(R.id.player_status_image);
        playerStatus.setVisibility(View.VISIBLE);

        showTurn();
    }

    void showTurn()
    {
        if(game.getTurn() == Game.PLAYER.PLAYER_1)
        {
            ((ImageView)playerOne.findViewById(R.id.player_status_image)).setVisibility(View.VISIBLE);
            ((ImageView)playerTwo.findViewById(R.id.player_status_image)).setVisibility(View.INVISIBLE);
        }else {
            ((ImageView)playerOne.findViewById(R.id.player_status_image)).setVisibility(View.INVISIBLE);
            ((ImageView)playerTwo.findViewById(R.id.player_status_image)).setVisibility(View.VISIBLE);
        }
    }

    void clearTurns()
    {
        ((ImageView)playerTwo.findViewById(R.id.player_status_image)).setVisibility(View.INVISIBLE);
        ((ImageView)playerOne.findViewById(R.id.player_status_image)).setVisibility(View.VISIBLE);
    }

    public static int[] IMAGE_VIEW_IDS = new int[]{R.id.box_0_0, R.id.box_0_1, R.id.box_0_2, R.id.box_1_0, R.id.box_1_1, R.id.box_1_2, R.id.box_2_0, R.id.box_2_1, R.id.box_2_2};
    public void refresh(View v)
    {
        game = new Game();
        gameOver = false;
        for(int i = 0; i < IMAGE_VIEW_IDS.length; i++)
        {
            ImageView imageView = (ImageView) matLayout.findViewById(IMAGE_VIEW_IDS[i]);
            imageView.setImageDrawable(null);
        }
        showTurn();

        ((ImageView)playerOne.findViewById(R.id.player_winner_image)).setVisibility(View.INVISIBLE);
        ((ImageView)playerTwo.findViewById(R.id.player_winner_image)).setVisibility(View.INVISIBLE);

    }


    public void onClick(View v)
    {
        if(gameOver){
            return;
        }
        int[] pos = getPositionForBox(v.getId());

        Game.MOVE_VALUE value = game.getMoveValue();
        ImageView imageView = (ImageView) v;

        try
        {
            Game.PLAYER player = game.setMoveValue(pos[0], pos[1]);
            if(player != null){
                setValue(value, imageView);
                if(player.equals(Game.PLAYER.PLAYER_1))
                {
                    ((ImageView)playerOne.findViewById(R.id.player_winner_image)).setVisibility(View.VISIBLE);
                    Toast.makeText(this,  "Winner Player 1", Toast.LENGTH_SHORT).show();
                }else
                {
                    ((ImageView)playerTwo.findViewById(R.id.player_winner_image)).setVisibility(View.VISIBLE);
                    Toast.makeText(this,  "Winner Player 2", Toast.LENGTH_SHORT).show();
                }

                return;
            }
            setValue(value, imageView);
        }catch (Exception e)
        {
            if(Game.EXCEPTIONS.ALREADY_ADDED.equals(e.getMessage()))
            {
                Toast.makeText(this,  "Already added!", Toast.LENGTH_SHORT).show();
            }
            else if(Game.EXCEPTIONS.GAME_OVER.equals(e.getMessage()))
            {
                Toast.makeText(this,  "Game over", Toast.LENGTH_SHORT).show();
                setValue(value, imageView);
                gameOver = true;
                return;
            }
            return;
        }
        showTurn();
    }

    void setValue(Game.MOVE_VALUE value, ImageView imageView)
    {
        MediaPlayer dropSound = MediaPlayer.create(MainActivity.this, R.raw.placeicon);
        dropSound.start();
        if(value == Game.MOVE_VALUE.X_VALUE){
            imageView.setImageDrawable(getDrawable(R.mipmap.ic_xlogo));
        }else {
            imageView.setImageDrawable(getDrawable(R.mipmap.ic_ologo));
        }
    }

    int[] getPositionForBox(int id)
    {
        int[] positions = new int[2];
        int x = 0, y = 0;
        switch (id)
        {
            case R.id.box_0_0:
                x = 0; y = 0;
                break;
            case R.id.box_0_1:
                x = 0; y = 1;
                break;
            case R.id.box_0_2:
                x = 0; y = 2;
                break;
            case R.id.box_1_0:
                x = 1; y = 0;
                break;
            case R.id.box_1_1:
                x = 1; y = 1;
                break;
            case R.id.box_1_2:
                x = 1; y = 2;
                break;
            case R.id.box_2_0:
                x = 2; y = 0;
                break;
            case R.id.box_2_1:
                x = 2; y = 1;
                break;
            case R.id.box_2_2:
                x = 2; y = 2;
                break;
        }
        positions[0] = x;
        positions[1] = y;
        return positions;
    }


}
