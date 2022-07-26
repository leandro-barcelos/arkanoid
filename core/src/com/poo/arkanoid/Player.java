package com.poo.arkanoid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Player
{
    private int highscore;
    private int score;
    private int vidas;

    public Player() throws FileNotFoundException {
        score = 0;
        vidas = 3;

        try {
            File playerData = new File("player.dat");
            if (playerData.createNewFile()) {
                FileWriter wt = new FileWriter("player.dat");
                wt.write(0);
                wt.close();
                highscore = 0;
            } else {
                Scanner sc = new Scanner(playerData);
                highscore = sc.nextInt();
            }
        } catch (IOException e) {
            System.out.println("ERRO: Nao foi possivel ler os dados do jogador");
            e.printStackTrace();
        }
    }

    public int getHighscore() {
        return highscore;
    }

    public int getScore() {
        return score;
    }

    public int getVidas() {
        return vidas;
    }
}
