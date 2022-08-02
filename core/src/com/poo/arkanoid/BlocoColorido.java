package com.poo.arkanoid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlocoColorido extends Bloco{
    enum Color {
        WHITE(0,0,50),
        ORANGE(32,0,60),
        CYAN(0,16,70),
        GREEN(32, 16, 80),
        RED(0, 32, 90),
        BLUE(32,32,100),
        PINK(0, 48, 110),
        YELLOW(32, 48, 120),
        ;
        public int pontos;
        public int texX;
        public int texY;
        Color(int texX, int texY, int pontos) {
            this.pontos = pontos;
            this.texX = texX;
            this.texY = texY;
        }
    }

    public BlocoColorido(Color cor, float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, cor.texX, cor.texY, width, height, cor.pontos, 1, batch);
    }
}
