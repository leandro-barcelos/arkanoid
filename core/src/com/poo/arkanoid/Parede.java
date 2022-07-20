package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Parede extends Animatable {

    public enum estadoParede {
        ABERTA,
        FECHADA
    }

    estadoParede estado;
    Texture imgFechada, imgAberta;
    Animacao openAnimation;
    
    public Parede(float x, float y, float width, float height, SpriteBatch batch) {
        super(x, y, width, height, batch);

        // CARREGAR TEXTURAS
        imgFechada = new Texture("wall-close.png");
        imgAberta = new Texture("wall-open.png");

        // CARREGAR ANIMACOES
        openAnimation = new Animacao(new Texture("wall-open-spritesheet.png"), 1, 8);

        estado = estadoParede.FECHADA;
    }

    @Override
    void draw() {
        if (getAnimationActive()) return;

        switch(estado) {
            case ABERTA:
                setTextura(imgAberta);
                break;
            case FECHADA:
                setTextura(imgFechada);
                break;
        }

        setWidth(getTextura().getWidth());
        batch.draw(getTextura(), getX() - getWidth() / 2, getY(), getWidth(), getHeight());
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
        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
    }

    @Override
    void dispose() {
        imgFechada.dispose();
        imgAberta.dispose();
    }
}
