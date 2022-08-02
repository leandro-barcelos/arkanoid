package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Gate extends Animavel {

    public boolean isEspelhar() {
        return espelhar;
    }

    public int getRotacao() {
        return rotacao;
    }

    public enum estadoParede {
        ABERTA,
        FECHADA
    }
    estadoParede estado;
    Texture imgFechada, imgAberta;
    Animacao openAnimation;
    private final boolean espelhar;
    private final int rotacao;

    public Gate(float x, float y, float width, float height, boolean espelhar, int rotacao, SpriteBatch batch) {
        super(x, y, width, height, batch);

        this.espelhar = espelhar;
        this.rotacao = rotacao;

        // CARREGAR TEXTURAS
        imgFechada = new Texture("Gates/wall-close.png");
        imgAberta = new Texture("Gates/wall-open.png");

        // CARREGAR ANIMACOES
        openAnimation = new Animacao(new Texture("Gates/wall-open-spritesheet.png"), 1, 8);

        estado = estadoParede.FECHADA;


    }

    @Override
    void draw() {
        if (getAnimationActive()) return;

        switch (estado) {
            case ABERTA:
                setTextura(imgAberta);
                break;
            case FECHADA:
                setTextura(imgFechada);
                break;
        }

        setWidth(getTextura().getWidth());
        batch.draw(getTextura(), getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, rotacao, 0, 0, (int) getWidth(), (int) getHeight(), espelhar, false);
    }

    public void mudarEstado(SpriteBatch batch, estadoParede paraEstado) {
        if (estado == paraEstado) return;

        Animation<TextureRegion> toModeAnimation;

        if (paraEstado == estadoParede.FECHADA) toModeAnimation = openAnimation.backward;
        else toModeAnimation = openAnimation.foward;

        setAnimationActive(true);

        setStateTime(getStateTime() + Gdx.graphics.getDeltaTime());

        if (toModeAnimation.isAnimationFinished(getStateTime())) {
            estado = paraEstado;
            setAnimationActive(false);
            setStateTime(0f);
            return;
        }

        TextureRegion currentFrame = toModeAnimation.getKeyFrame(getStateTime(), true);
        batch.draw(currentFrame, getX() - getWidth() / 2, getY(), 0, getHeight(),  getWidth() / 2, getHeight() / 2, (espelhar ? -1 : 1), 1, rotacao);
    }

    @Override
    void dispose() {
        imgFechada.dispose();
        imgAberta.dispose();
    }


}
