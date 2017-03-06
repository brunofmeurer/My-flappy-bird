package com.brunomeurer.myflappybird.componentes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

/**
 * Created by bruno-meurer on 04/03/17.
 */

public class MoedaPremio {

    private Texture moeda;
    private Circle colisorMoeda;
    private boolean pegou;
    private float x;
    private float y;
    private boolean aparece;

    public MoedaPremio(){
        moeda = new Texture("moeda_premio.png");
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isAparece() {
        return aparece;
    }

    public void setAparece(boolean aparece) {
        this.aparece = aparece;
    }
}
