package com.brunomeurer.myflappybird.componentes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

/**
 * Created by bruno-meurer on 04/03/17.
 */

public class Moeda {

    private Texture moeda;
    private Circle colisorMoeda;
    private boolean pegou;
    public Moeda(){
        moeda = new Texture("moeda.png");
        colisorMoeda = new Circle();
    }

    public Texture getMoeda() {
        return moeda;
    }

    public Circle getColisorMoeda() {
        return colisorMoeda;
    }

    public boolean isPegou() {
        return pegou;
    }

    public void setPegou(boolean pegou) {
        this.pegou = pegou;
    }

    public void updateColisor(float x, float y){
        colisorMoeda.set(x,y, 51/2 -1);

    }
}
