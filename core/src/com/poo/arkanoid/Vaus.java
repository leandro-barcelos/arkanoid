package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Vaus extends Animavel implements Colidivel<Boolean, Bola> {
    private final Texture imgNormal, imgLazer, imgLarge;
    private final Animacao toLargeAnimation, toLazerAnimation, destructionAnimation;
    private final ArrayList<Laser[]> lasers;
    private vausHabilidade habilidade;
    private float cadenciaLaser;
    public Vaus(int x, int y, SpriteBatch batch) {
        super(x, y, 64, 14, batch);

        // CARREGAR TEXTURAS
        imgNormal = new Texture("Vaus/vaus-normal.png");
        imgLazer = new Texture("Vaus/vaus-lazer.png");
        imgLarge = new Texture("Vaus/vaus-large.png");

        // CARREGAR ANIMACOES
        toLargeAnimation = new Animacao(new Texture("Vaus/vaus-large-spritesheet.png"), 6, 1, 0.75f);
        toLazerAnimation = new Animacao(new Texture("Vaus/vaus-lazer-spritesheet.png"), 8, 1, 1f);
        destructionAnimation = new Animacao(new Texture("Vaus/vaus-break-spritesheet.png"), 8, 1, 1f);
        destructionAnimation.ativarForward();

        habilidade = vausHabilidade.NORMAL;

        lasers = new ArrayList<>();

        cadenciaLaser = 1f;
    }

    public vausHabilidade getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(vausHabilidade habilidade) {
        this.habilidade = habilidade;
    }

    public void atirar() {
        if (getHabilidade() != vausHabilidade.LAZER || getAnimationActive()) return;

        if (cadenciaLaser < 0.33) {
            cadenciaLaser += Gdx.graphics.getDeltaTime();
            return;
        }


        Laser[] novo = new Laser[2];
        novo[0] = new Laser(getX() - 27, getY() + getHeight() / 2, getBatch());
        novo[1] = new Laser(getX() + 27, getY() + getHeight() / 2, getBatch());

        cadenciaLaser = 0;

        lasers.add(novo);
    }

    public void Mover(Parede parede) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) setX(getX() - 250 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setX(getX() + 250 * Gdx.graphics.getDeltaTime());

        if (getX() - getWidth() / 2 < parede.getLimXEsq())
            setX(parede.getLimXEsq() + getWidth() / 2);
        if (getX() - getWidth() / 2 > parede.getLimXDir() - getWidth())
            setX(parede.getLimXDir() - getWidth() / 2);

        for (Laser[] i : lasers) {
            if (i[0] != null) i[0].mover();
            if (i[1] != null) i[1].mover();
        }
    }

    public void changeMode(vausHabilidade toMode) {
        if (habilidade == toMode) return;

        if (toMode != vausHabilidade.NORMAL && habilidade != vausHabilidade.NORMAL) {
            changeMode(vausHabilidade.NORMAL);
            return;
        }

        setAnimacao(toLargeAnimation);
        getAnimacao().ativarBackward();

        switch (toMode) {
            case NORMAL:
                if (habilidade == vausHabilidade.LAZER) {
                    setAnimacao(toLazerAnimation);
                    getAnimacao().ativarBackward();
                }
                break;
            case LARGE:
                setWidth(imgLarge.getWidth());
                getAnimacao().ativarForward();
                break;
            case LAZER:
                setAnimacao(toLazerAnimation);
                getAnimacao().ativarForward();
                break;
        }

        if (rodarAnimacao()) habilidade = toMode;
    }

    @Override
    public void draw() {
        if (getAnimationActive()) return;

        setWidth(64);

        switch (habilidade) {
            case NORMAL:
                setTextura(imgNormal);
                break;
            case LAZER:
                setTextura(imgLazer);
                break;
            case LARGE:
                setTextura(imgLarge);
                break;
        }

        setWidth(getTextura().getWidth());
        getBatch().draw(getTextura(), getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight());
    }

    @Override
    public Boolean colisao(Bola objeto) {
        boolean colidiu;
        colidiu = (objeto.getY() - 1 - (objeto.getHeight() / 2) <= (getY() + getHeight() / 2) && objeto.getY() + objeto.getHeight() / 2 >= (getY() - getHeight() / 2))
                && (objeto.getX() >= getX() - (getWidth() / 2) && objeto.getX() <= getX() + (getWidth() / 2));
        if (colidiu && !objeto.isGrudar()) {
            if (objeto.getVelocidade() == 0) objeto.setVelocidade(300);
            else if (objeto.getVelocidade() < 500) objeto.setVelocidade(objeto.getVelocidade() + 2);

            int maxGrau = 70;

            float distancia = objeto.getX() - getX();
            float intervaloAngulos = (float) maxGrau / (getWidth() / 2);
            float teta = maxGrau;

            for (int i = (int) -(getWidth() / 2 - 1); i <= (getWidth() / 2); i++) {
                if (distancia <= i) {
                    if (i <= 0) teta = (maxGrau - i * intervaloAngulos) - 1;
                    else teta = (maxGrau + -(i - 1) * intervaloAngulos) + 1;
                    break;
                }
            }

            objeto.setxSpeed((float) (objeto.getVelocidade() * Math.cos(Math.PI / 180 * teta)));
            objeto.setySpeed((float) (objeto.getVelocidade() * Math.sin(Math.PI / 180 * teta)));
        }

        return colidiu;
    }

    void destroy() {
        setAnimacao(destructionAnimation);
        getAnimacao().ativarForward();

        rodarAnimacao();
    }

    @Override
    public void dispose() {
        imgNormal.dispose();
        imgLazer.dispose();
        imgLarge.dispose();
    }

    public ArrayList<Laser[]> getLasers() {
        return lasers;
    }

    public enum vausHabilidade {
        NORMAL,
        LARGE,
        LAZER
    }
}
