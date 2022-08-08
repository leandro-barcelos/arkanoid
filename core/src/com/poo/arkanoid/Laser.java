package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Laser extends GameObject implements Colidivel<Integer, MatrizBlocos> {

    public Laser(float x, float y, SpriteBatch batch) {
        super(x, y, 6, 18, batch);

        setTextura(new Texture("Vaus/lazer.png"));
    }

    @Override
    public void draw() {
        getBatch().draw(getTextura(), getX(), getY(), getWidth(), getHeight());
    }

    void mover() {
        setY(getY() + 300 * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        getTextura().dispose();
    }

    @Override
    public Integer colisao(MatrizBlocos objeto) {
        int pontos = -1;

        for (int i = 0; i < objeto.getMatriz().length; i++)
            for (int j = 0; j < objeto.getMatriz()[i].length; j++) {
                if (objeto.getMatriz()[i][j] == null) continue;

                boolean colisao = getY() + getHeight() >= objeto.getMatriz()[i][j].getY() - objeto.getMatriz()[i][j].getHeight() / 2  // Verificação Y
                        && (getX() >= objeto.getMatriz()[i][j].getX() - objeto.getMatriz()[i][j].getWidth() / 2 && getX() <= objeto.getMatriz()[i][j].getX() + objeto.getMatriz()[i][j].getWidth() / 2
                        || getX() + getWidth() >= objeto.getMatriz()[i][j].getX() - objeto.getMatriz()[i][j].getWidth() / 2 && getX() + getWidth() <= objeto.getMatriz()[i][j].getX() + objeto.getMatriz()[i][j].getWidth() / 2);

                if (colisao) {
                    objeto.getMatriz()[i][j].decDurabilidade();
                    if (pontos == -1)
                        pontos = 0;
                    if (objeto.getMatriz()[i][j].isQuebrado()) {
                        pontos += objeto.getMatriz()[i][j].getPontos();
                        objeto.getMatriz()[i][j] = null;
                    }
                }
            }

        return pontos;
    }
}
