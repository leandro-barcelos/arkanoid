package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Objects;


public class Vaus extends Prop{
    private final Texture imgNormal, imgLazer, imgGrande, enlargeSheet, lazerSheet;
    private final Animation<TextureRegion> enlargeAnimation, shrinkAnimation, lazerAnimation, delazerAnimation;
    private String habilidade;
    private Boolean animationActive;
    private float stateTime;
    
    public Vaus(int x, int y, int width, int height) {

        super(x,y,width,height);

        // CARREGAR TEXTURAS
        imgNormal = new Texture("prop-bar-normal.png");
        imgLazer = new Texture("prop-bar-lazer.png");
        imgGrande = new Texture("prop-bar-enlarged.png");

        enlargeSheet = new Texture("prop-bar-enlarging-spritesheet.png");
        lazerSheet = new Texture("prop-bar-lazer-spritesheet.png");

        // CARREGAR ANIMACOES
        TextureRegion[][] tmp = TextureRegion.split(enlargeSheet,
                enlargeSheet.getWidth() / 4,
                enlargeSheet.getHeight());

        TextureRegion[] enlargeFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                enlargeFrames[index++] = tmp[i][j];
            }
        }

        enlargeAnimation = new Animation<>(0.1f, enlargeFrames);

        TextureRegion[] shrinkFrames = new TextureRegion[4];
        for (int i = enlargeFrames.length - 1, j = 0; i >= 0; i--, j++) {
            shrinkFrames[j] = enlargeFrames[i];
        }

        shrinkAnimation = new Animation<>(0.1f, shrinkFrames);

        tmp = TextureRegion.split(lazerSheet,
                lazerSheet.getWidth(),
                lazerSheet.getHeight() / 8);

        TextureRegion[] lazerFrames = new TextureRegion[8];
        index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1; j++) {
                lazerFrames[index++] = tmp[i][j];
            }
        }

        lazerAnimation = new Animation<>(0.1f, lazerFrames);

        TextureRegion[] delazerFrames = new TextureRegion[8];
        for (int i = lazerFrames.length - 1, j = 0; i >= 0; i--, j++) {
            delazerFrames[j] = lazerFrames[i];
        }

        delazerAnimation = new Animation<>(0.1f, delazerFrames);

        stateTime = 0f;
        animationActive = false;

        habilidade = "normal";
    }
    
    public void Mover() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) setX(getX() - 250 * Gdx.graphics.getDeltaTime());
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) setX(getX() + 250 * Gdx.graphics.getDeltaTime());

        if(getX() - getWidth() / 2 < 33) setX(33 + getWidth() / 2);
        if(getX() - getWidth() / 2 > 381 - getWidth()) setX(381 - getWidth() / 2);
    }

    public void enlarge(SpriteBatch batch) {
        if(Objects.equals(habilidade, "large")) return;

        animationActive = true;
        setWidth(imgGrande.getWidth());

        stateTime += Gdx.graphics.getDeltaTime();

        if (enlargeAnimation.isAnimationFinished(stateTime)) {
            habilidade = "large";
            animationActive = false;
            stateTime = 0f;
            return;
        }

        TextureRegion currentFrame = enlargeAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
    }

    public void shrink(SpriteBatch batch) {
        if(Objects.equals(habilidade, "normal")) return;

        animationActive = true;
        setWidth(imgGrande.getWidth());

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = shrinkAnimation.getKeyFrame(stateTime, true);

        if (shrinkAnimation.isAnimationFinished(stateTime)) {
            habilidade = "normal";
            animationActive = false;
            stateTime = 0f;
            return;
        }

        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
    }

    public void lazerMode(SpriteBatch batch) {
        if(Objects.equals(habilidade, "lazer")) return;

        animationActive = true;
        setWidth(imgLazer.getWidth());

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = lazerAnimation.getKeyFrame(stateTime, true);

        if (lazerAnimation.isAnimationFinished(stateTime)) {
            habilidade = "lazer";
            animationActive = false;
            stateTime = 0f;
            return;
        }

        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
    }

    public void lazerToNormal(SpriteBatch batch) {
        if(Objects.equals(habilidade, "normal")) return;

        animationActive = true;
        setWidth(imgLazer.getWidth());

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = delazerAnimation.getKeyFrame(stateTime, true);

        if (delazerAnimation.isAnimationFinished(stateTime)) {
            habilidade = "normal";
            animationActive = false;
            stateTime = 0f;
            return;
        }

        batch.draw(currentFrame, getX() - getWidth() / 2, getY());
    }

    public void draw(SpriteBatch batch) {
        if (animationActive) return;

        setWidth(64);

        switch(habilidade) {
            case "normal":
                setTextura(imgNormal);
                break;
            case "lazer":
                setTextura(imgLazer);
                break;
            case "large":
                setTextura(imgGrande);
                break;
        }

        setWidth(getTextura().getWidth());
        batch.draw(getTextura(), getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }

    public void dispose() {
        imgNormal.dispose();
        imgLazer.dispose();
        imgGrande.dispose();
        enlargeSheet.dispose();
    }

    public String getHabilidade() {
        return habilidade;
    }
}
