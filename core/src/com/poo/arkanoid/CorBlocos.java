package com.poo.arkanoid;

public enum CorBlocos {
    WHITE(0, 0, 50, 1, "sfx/ColisaoBloco.wav"),
    ORANGE(32, 0, 60, 1, "sfx/ColisaoBloco.wav"),
    CYAN(0, 16, 70, 1, "sfx/ColisaoBloco.wav"),
    GREEN(32, 16, 80, 1, "sfx/ColisaoBloco.wav"),
    RED(0, 32, 90, 1, "sfx/ColisaoBloco.wav"),
    BLUE(32, 32, 100, 1, "sfx/ColisaoBloco.wav"),
    PINK(0, 48, 110, 1, "sfx/ColisaoBloco.wav"),
    YELLOW(32, 48, 120, 1, "sfx/ColisaoBloco.wav"),
    SILVER(0, 64, 50, 2, "sfx/ColisaoMetal.wav"),
    GOLDEN(32, 64, 0, 999999, "sfx/ColisaoMetal.wav");
    public final int pontos;
    public final int texX;
    public final int texY;
    public final int durabilidade;
    final String pathSfx;

    CorBlocos(int texX, int texY, int pontos, int durabilidade, String pathSfx) {
        this.pontos = pontos;
        this.texX = texX;
        this.texY = texY;
        this.durabilidade = durabilidade;
        this.pathSfx = pathSfx;
    }
}
