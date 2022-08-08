package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacao {
    private final Animation<TextureRegion> forward;
    private final Animation<TextureRegion> backward;

    private Animation<TextureRegion> ativa;

    public Animacao(Texture spritesheet, int rows, int columns, float tempoAnimacao) {
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

        forward = new Animation<>(tempoAnimacao / (columns * rows), fowardFrames);
        backward = new Animation<>(tempoAnimacao / (rows * columns), backwardFrames);
    }

    public void ativarForward() {
        if (ativa != forward) ativa = forward;
    }

    public void ativarBackward() {
        if (ativa != backward) ativa = backward;
    }

    public Animation<TextureRegion> getAtiva() {
        return ativa;
    }
}
