package model;

public class Tripleta {

    //Atributos que guarda la fila, columna y el dato que puede ser un valor entero o un nodo doble
    private int fila;
    private int columna;
    private Object dato;

    //Constructor
    public Tripleta(int fila, int columna, Object dato) {
        this.fila = fila;
        this.columna = columna;
        this.dato = dato;

    }

    //Getters y setters
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

}
