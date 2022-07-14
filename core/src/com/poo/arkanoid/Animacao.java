package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacao {
    Animation<TextureRegion> animacao;
    Boolean reverse;

    public Animacao(Texture spritesheet, int rows, int columns, Boolean reverse) {
        this.reverse = reverse;

        TextureRegion[][] tmp = TextureRegion.split(spritesheet,
                spritesheet.getWidth() / columns,
                spritesheet.getHeight() / rows);

        TextureRegion[] animationFrames = new TextureRegion[columns * rows];

        if (reverse) {
            int index = columns * rows - 1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    animationFrames[index--] = tmp[i][j];
                }
            }
        } else {
            int index = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    animationFrames[index++] = tmp[i][j];
                }
            }
        }


        animacao = new Animation<>(1f / (columns * rows), animationFrames);
    }
}
