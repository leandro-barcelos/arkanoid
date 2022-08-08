package com.poo.arkanoid;

public enum CorBlocos {
    WHITE(0,0,50, 1),
    ORANGE(32,0,60, 1),
    CYAN(0,16,70, 1),
    GREEN(32, 16, 80, 1),
    RED(0, 32, 90, 1),
    BLUE(32,32,100, 1),
    PINK(0, 48, 110, 1),
    YELLOW(32, 48, 120, 1),
    SILVER(0,64, 50, 2 ),
    GOLDEN(32, 64, 0, 999999)
    ;
    public final int pontos;
    public final int texX;
    public final int texY;
    public final int durabilidade;
    CorBlocos(int texX, int texY, int pontos, int durabilidade) {
        this.pontos = pontos;
        this.texX = texX;
        this.texY = texY;
        this.durabilidade = durabilidade;
    }
}
