package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Objects;

public class Player {

    private static int highscore;
    private static int nivelAtual;
    private final FileHandle playerData;
    private int score;
    private int vidas;

    public Player() {
        score = 0;
        vidas = 3;
        nivelAtual = 1;

        playerData = Gdx.files.local("player.dat");

        if (!playerData.exists() || Objects.equals(playerData.readString(), "")) {
            playerData.writeString("0", false);
            highscore = 0;
        } else {
            highscore = Integer.parseInt(playerData.readString());
        }

        getHighscore();
    }

    public void drawHighscore(int x, int y, SpriteBatch batch, BitmapFont font) {

        getHighscore();

        String highscoreFormat = String.valueOf(highscore);
        while (highscoreFormat.length() < 6) {
            highscoreFormat = "0".concat(highscoreFormat);
        }

        font.draw(batch, highscoreFormat, x, y);
    }

    public void drawScore(int x, int y, SpriteBatch batch, BitmapFont font) {
        String scoreFormat = String.valueOf(score);
        while (scoreFormat.length() < 6) {
            scoreFormat = "0".concat(scoreFormat);
        }

        font.draw(batch, scoreFormat, x, y);
    }

    public void drawLives(int x, int y, SpriteBatch batch) {
        for (int i = 0; i < vidas; i++) {
            Texture vidasTextura = new Texture("Vaus/vaus-normal.png");
            batch.draw(vidasTextura, x, y - (float) vidasTextura.getHeight() / 2, 32, 7);
            x += 34;
        }
    }

    public void reset() {
        score = 0;
        vidas = 3;
        nivelAtual = 1;
    }

    public void getHighscore() {
        if (Objects.equals(playerData.readString(), "")) {
            playerData.writeString("0", false);
            highscore = 0;
        } else {
            highscore = Integer.parseInt(playerData.readString());
        }

    }

    public void setHighscore() {
        if (score > highscore) {
            highscore = Math.min(score, 999999);
        }

        playerData.writeString(String.valueOf(highscore), false);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVidas() {
        return vidas;
    }

    public void incVidas() {
        if (vidas < 5)
            vidas++;
    }

    public void decVidas() {
        if (vidas > 0)
            vidas--;
    }

    public int getNivelAtual() {
        return nivelAtual;
    }

    public void incNivelAtual() {
        if (nivelAtual < 6)
            nivelAtual++;
        else nivelAtual = 1;
    }
}
