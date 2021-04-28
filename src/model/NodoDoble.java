package model;

public class NodoDoble {

    //Atributos
    private NodoDoble ligaDerecha;
    private NodoDoble ligaIzquierda;
    private Object valor;

    //Constructor solo se manda el parametro que va en el valor y las ligas quedan con null
    public NodoDoble(Object valor) {
        this.valor = valor;
    }

    //Getters y setters

    public NodoDoble getLigaDerecha() {
        return ligaDerecha;
    }

    public void setLigaDerecha(NodoDoble ligaDerecha) {
        this.ligaDerecha = ligaDerecha;
    }

    public NodoDoble getLigaIzquierda() {
        return ligaIzquierda;
    }

    public void setLigaIzquierda(NodoDoble ligaIzquierda) {
        this.ligaIzquierda = ligaIzquierda;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

}
