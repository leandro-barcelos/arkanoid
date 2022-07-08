package com.poo.arkanoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class Vaus {
    int x, y, height, width;
    
    void Mover() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= 250 * Gdx.graphics.getDeltaTime();
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += 250 * Gdx.graphics.getDeltaTime();

                if(x < 31) x = 31;
                if(x > 319) x = 319;
    }
}
