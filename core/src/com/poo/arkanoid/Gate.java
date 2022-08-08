package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Gate extends Animavel {
    public enum estadoParede {
        ABERTA,
        FECHADA
    }
    private estadoParede estado;
    private final Texture imgFechada;
    private final Texture imgAberta;
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
        setAnimacao(new Animacao(new Texture("Gates/wall-open-spritesheet.png"), 1, 8, 1f));

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
        getBatch().draw(getTextura(), getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, rotacao, 0, 0, (int) getWidth(), (int) getHeight(), espelhar, false);
    }

    public void mudarEstado(estadoParede paraEstado) {
        if (estado == paraEstado) return;

        if (paraEstado == estadoParede.FECHADA) {
            getAnimacao().ativarBackward();
        }
        else {
            getAnimacao().ativarForward();
        }

        if(rodarAnimacao(espelhar, rotacao)) estado = paraEstado;
    }

    @Override
    void dispose() {
        imgFechada.dispose();
        imgAberta.dispose();
    }

    public boolean isEspelhar() {
        return espelhar;
    }

    public int getRotacao() {
        return rotacao;
    }

    public estadoParede getEstado() {
        return estado;
    }

    public Texture getImgFechada() {
        return imgFechada;
    }

}
