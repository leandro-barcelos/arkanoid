package com.poo.arkanoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PoderBreak extends Poder implements PoderGate{
    public PoderBreak(float x, float y, SpriteBatch batch) {
        super(x, y, new Texture("Powerups/power-break-spritesheet.png"), batch);
    }

    @Override
    public void ativar(Gate gate, Vaus vaus) {
        if (gate.getEstado() != Gate.estadoParede.ABERTA) {
            gate.mudarEstado(Gate.estadoParede.ABERTA);
            setX(vaus.getX());
            setY(vaus.getY());
        }
    }
}
