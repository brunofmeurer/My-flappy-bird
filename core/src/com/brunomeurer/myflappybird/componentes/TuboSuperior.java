package com.brunomeurer.myflappybird.componentes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by bruno-meurer on 04/03/17.
 */

public class TuboSuperior {

    private Rectangle colisorTuboSuperior;
    private Texture tuboSuperior;

    public TuboSuperior(){
        tuboSuperior = new Texture("cano_topo_maior.png");
        colisorTuboSuperior = new Rectangle();
    }

    public Rectangle getColisorTuboSuperior() {
        return colisorTuboSuperior;
    }

    public void updateColisor(float x, float y) {
        colisorTuboSuperior = new Rectangle(
                x,
                y,
                getWidth(),
                getHeight()
        );
    }

    public Texture getTuboSuperior() {
        return tuboSuperior;
    }

    public float getWidth(){
        return tuboSuperior.getWidth();
    }

    public float getHeight(){
        return tuboSuperior.getHeight();
    }
}
