package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bloco extends GameObject{
    private final CorBlocos cor;
    private int durabilidade;
    private int pontos;

    public Bloco(float x, float y, CorBlocos cor, SpriteBatch batch) {
        super(x, y, 32, 16, batch);

        this.cor = cor;
        durabilidade = cor.durabilidade;
        pontos = cor.pontos;
        setTextura(new Texture("blocos.png"));
    }

    @Override
    public void draw() {
        if (durabilidade == 0)  return;
        TextureRegion texturaBloco = new TextureRegion(getTextura(), cor.texX, cor.texY, (int) getWidth(), (int) getHeight());
        getBatch().draw(texturaBloco, getX(), getY(),getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        getTextura().dispose();
    }

    public int getPontos() {
        return pontos;
    }

    public boolean isQuebrado() {
        return durabilidade <= 0;
    }

    public void decDurabilidade() {
        if(durabilidade != 0)
            durabilidade--;
    }

    protected void setDurabilidade(int durabilidade) {this.durabilidade = durabilidade; }

    protected void setPontos(int pontos) {this.pontos = pontos; }

    public CorBlocos getCor() {
        return cor;
    }
}
