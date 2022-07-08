package com.poo.arkanoid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Arkanoid extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
        Texture vausImage;
        Vaus vaus;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("backgroud-blue.png");
                vausImage = new Texture("prop-bar-normal.png");
                
                vaus = new Vaus();
                vaus.x = (512-98)/2 - 64/2;
                vaus.y = 56 - 7;
                vaus.width = 64;
                vaus.height = 14;
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0, 512, 480);
                batch.draw(vausImage, vaus.x, vaus.y, vaus.width, vaus.height);
                
                vaus.Mover();

                
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
