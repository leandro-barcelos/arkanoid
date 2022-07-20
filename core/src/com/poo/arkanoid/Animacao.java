package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacao {
    Animation<TextureRegion> foward;
    Animation<TextureRegion> backward;

    public Animacao(Texture spritesheet, int rows, int columns) {
        TextureRegion[][] tmp = TextureRegion.split(spritesheet,
                spritesheet.getWidth() / columns,
                spritesheet.getHeight() / rows);

        TextureRegion[] fowardFrames = new TextureRegion[columns * rows];
        TextureRegion[] backwardFrames = new TextureRegion[columns * rows];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                fowardFrames[index++] = tmp[i][j];
            }
        }


        index = columns * rows - 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                backwardFrames[index--] = tmp[i][j];
            }
        }

        foward = new Animation<>(1f / (columns * rows), fowardFrames);
        backward = new Animation<>(1f / (rows * columns), backwardFrames);
    }


}
