package com.brunomeurer.myflappybird.personagem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

/**
 * Created by bruno-meurer on 04/03/17.
 */

public class Passaro {
    private Texture[] passaro;
    private float xPassaro;
    private float yPassaro;
    private Circle colisorPassaro;

    public Passaro(float larguraDoDispositivo, float alturaDoDispositivo){
        //inicializa texturas
        passaro = new Texture[3];
        passaro[0] = new Texture("passaro1.png");
        passaro[1] = new Texture("passaro2.png");
        passaro[2] = new Texture("passaro3.png");

        colisorPassaro = new Circle();
        xPassaro = (larguraDoDispositivo/2);
        yPassaro = (alturaDoDispositivo/2);

        updateColisor();
    }

    public Texture getFramePassaro(int posicao){
        return passaro[posicao];
    }

    public float getxPassaro() {
        return xPassaro;
    }

    public void setxPassaro(float xPassaro) {
        this.xPassaro = xPassaro;
    }

    public float getyPassaro() {
        return yPassaro;
    }

    public void setyPassaro(float yPassaro) {
        this.yPassaro = yPassaro;
    }

    public Circle getColisorPassaro() {
        return colisorPassaro;
    }

    public void updateColisor(){
        colisorPassaro.set(150 + passaro[0].getWidth()  / 2, yPassaro + passaro[0].getHeight() / 2, passaro[0].getWidth() /2 -1);
    }
}
