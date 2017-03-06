package com.brunomeurer.myflappybird.componentes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by bruno-meurer on 04/03/17.
 */

public class TuboInferior {

    private Rectangle colisorTuboInferior;
    private Texture tuboInferior;

    public TuboInferior(){
        tuboInferior = new Texture("cano_baixo_maior.png");
        colisorTuboInferior = new Rectangle();
    }

    public Rectangle getColisorTuboInferior() {
        return colisorTuboInferior;
    }

    public void updateColisor(float x, float y) {
        colisorTuboInferior = new Rectangle(
                x,
                y,
                getWidth(),
                getHeight()
        );
    }

    public Texture getTuboInferior() {
        return tuboInferior;
    }

    public float getWidth(){
        return tuboInferior.getWidth();
    }

    public float getHeight(){
        return tuboInferior.getHeight();
    }
}
