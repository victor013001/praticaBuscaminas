package model;

public class MatrizEnForma1 {

    //Atributo
    private NodoDoble nodoCabeza;

    //Constructor
    public MatrizEnForma1(int fila, int columna) {

        Tripleta tripleta = new Tripleta(fila, columna, null);
        NodoDoble nodoCabezaMat = new NodoDoble(tripleta);
        tripleta.setDato(nodoCabezaMat);
        nodoCabezaMat.setValor(tripleta);
        this.nodoCabeza = nodoCabezaMat;
        nodosCabezas();
    }

    //Getters y Setters
    public NodoDoble getNodoCabeza() {
        return nodoCabeza;
    }

    public void setNodoCabeza(NodoDoble nodoCabeza) {
        this.nodoCabeza = nodoCabeza;
    }

    public int retornaNumeroFilas() {

        NodoDoble nodoDoble = this.getNodoCabeza();
        Tripleta tripleta = (Tripleta) nodoDoble.getValor();
        return tripleta.getFila();
    }

    public int retornaNumeroColumas() {

        NodoDoble nodoDoble = this.getNodoCabeza();
        Tripleta tripleta = (Tripleta) nodoDoble.getValor();
        return tripleta.getColumna();
    }

    //Devuelve el primerNodo cabeza que seria la fila/columna numero uno
    public NodoDoble primerNodo() {
        Tripleta tripleta = (Tripleta) this.nodoCabeza.getValor();
        return (NodoDoble) tripleta.getDato();
    }

    //Se crean la cantidad de nodos cabezas que sea mayor entre el numero de filas y columnas
    public void nodosCabezas() {

        NodoDoble mat = this.getNodoCabeza();
        Tripleta tripleta = (Tripleta) mat.getValor();
        int filas = tripleta.getFila();
        int columnas = tripleta.getColumna();
        int mayor = Math.max(filas, columnas);
        NodoDoble ultimo = mat;

        for (int i = 1; i <= mayor; i++) {

            Tripleta tripleta1 = new Tripleta(0, 0, mat);
            NodoDoble nodoDoble = new NodoDoble(tripleta1);
            nodoDoble.setLigaIzquierda(nodoDoble);
            nodoDoble.setLigaDerecha(nodoDoble);
            tripleta1 = (Tripleta) ultimo.getValor();
            tripleta1.setDato(nodoDoble);
            ultimo = nodoDoble;
        }
    }

    //Metodo para conectar por fila
    public void conectarEnFilas(NodoDoble nodoDoble) {

        Tripleta tripleta = (Tripleta) nodoDoble.getValor();
        int fila = tripleta.getFila();
        int columna = tripleta.getColumna();
        NodoDoble primerNodo = this.primerNodo();

        //Busca en la lista de nodos cabezas el nodo que corresponda a la fila del nodo que se va a insertar
        for (int i = 1; i < fila; i++) {

            tripleta = (Tripleta) primerNodo.getValor();
            primerNodo = (NodoDoble) tripleta.getDato();
        }

        //Despues de encontrarlo se mete a la lista ligada que tiene ese nodo como nodo cabeza para buscar donde se inserta y manteniendo el orden
        NodoDoble anterior = primerNodo;
        NodoDoble actual = primerNodo.getLigaDerecha();
        tripleta = (Tripleta) actual.getValor();

        //Se guarda el nodo actual en el que se comparan los valores y el nodo anterior, esto para ayudar a ligar el nuevo nodo con mayor facilidad
        while (actual != primerNodo && tripleta.getColumna() < columna) {

            anterior = actual;
            actual = actual.getLigaDerecha();
            tripleta = (Tripleta) actual.getValor();
        }

        /*Como primero se crea la matriz con las minas que son -1 la matriz no tiene más valores ya que de resto son cero
        y antes de poner una mina se comprueba que esta ya no exista entonces no hace uso de este if
        Luego se crean las pistas que tambien requieren el metodo de conectar pero estas si hacen uso del if ya que debe actualizar el valor en esa posicion
         */
        if (tripleta.getColumna() == columna && actual != primerNodo) {

            tripleta = (Tripleta) nodoDoble.getValor();
            actual.setValor(tripleta);

        } else {

            anterior.setLigaDerecha(nodoDoble);
            nodoDoble.setLigaDerecha(actual);
        }

        //Se actualiza el numero de filas que guarda el nodo cabeza
        tripleta = (Tripleta) primerNodo.getValor();
        tripleta.setFila(tripleta.getFila() + 1);
        primerNodo.setValor(tripleta);
    }

    //Metodo para conectar por columna, fuciona igual que el anterior
    public void conectarEnColumnas(NodoDoble nodoDoble) {

        Tripleta tripleta = (Tripleta) nodoDoble.getValor();
        int fila = tripleta.getFila();
        int columna = tripleta.getColumna();
        NodoDoble primerNodo = this.primerNodo();

        for (int i = 1; i < columna; i++) {

            tripleta = (Tripleta) primerNodo.getValor();
            primerNodo = (NodoDoble) tripleta.getDato();
        }

        NodoDoble anterior = primerNodo;
        NodoDoble actual = primerNodo.getLigaIzquierda();
        tripleta = (Tripleta) actual.getValor();

        while (actual != primerNodo && tripleta.getFila() < fila) {

            anterior = actual;
            actual = actual.getLigaIzquierda();
            tripleta = (Tripleta) actual.getValor();
        }

        if (tripleta.getFila() == fila && actual != primerNodo) {

            tripleta = (Tripleta) nodoDoble.getValor();
            actual.setValor(tripleta);
        } else {

            anterior.setLigaIzquierda(nodoDoble);
            nodoDoble.setLigaIzquierda(actual);
        }

        tripleta = (Tripleta) primerNodo.getValor();
        tripleta.setColumna(tripleta.getColumna() + 1);
        primerNodo.setValor(tripleta);
    }


    //Se muestra la matriz con los ceros incluidos
    public void muestraComoCuadricula() {

        String matriz = "";
        Tripleta t = (Tripleta) this.getNodoCabeza().getValor();
        int filas = t.getFila();
        int columnas = t.getColumna();
        NodoDoble actualFila = (NodoDoble) t.getDato();
        int i = 0;

        //Recorre la matriz por filas
        while (i < filas) {

            matriz += "\n";
            NodoDoble actualColumna = (NodoDoble) actualFila.getLigaDerecha();

            //Recorre cada columna de la fila
            for (int j = 1; j <= columnas; j++) {

                Tripleta tripletaActual = (Tripleta) actualColumna.getValor();

                //Si se cumple que el actualColumna es igual al actualFila es porque se encuentra en un nodoCabeza y no hay mas valores, por lo tanto se llenan de 0 para completar la matriz
                if (actualColumna == actualFila) {

                    matriz += "0" + " ";

                } else {

                    //Comprueba que la columna en la cual se encuentra el contador sea igual a la columna con el dato, si son diferentes significa que debe ir un 0 en caso contrario se pone el dato
                    if (j != tripletaActual.getColumna()) {

                        matriz += "0" + " ";

                    } else {

                        if ((int) tripletaActual.getDato() == -1) {

                            matriz += "* ";
                            actualColumna = actualColumna.getLigaDerecha();

                        } else {

                            matriz += tripletaActual.getDato() + " ";
                            actualColumna = actualColumna.getLigaDerecha();
                        }
                    }
                }
            }

            Tripleta siguienteFila = (Tripleta) actualFila.getValor();
            actualFila = (NodoDoble) siguienteFila.getDato();
            i++;
        }
        matriz += "\n";
        System.out.println(matriz);
    }

    /*Comprueba que la matriz en la posicion fila columna no tenga como valor el 1 ya que este indica una mina
     *si encuentra mina devuelve false en caso contrario devuelve true
     */
    public boolean comprobarDato(int fila, int columna) {

        Tripleta tripleta;
        NodoDoble primerNodo = this.primerNodo();

        //Se busca la fila correspondiente
        for (int i = 1; i < fila; i++) {

            tripleta = (Tripleta) primerNodo.getValor();
            primerNodo = (NodoDoble) tripleta.getDato();
        }

        //Se comienza a recorrer esa fila hasta llegar al nodo cabeza de esa fila
        NodoDoble actual = primerNodo.getLigaDerecha();

        while (actual != primerNodo) {

            tripleta = (Tripleta) actual.getValor();

            if (tripleta.getColumna() == columna) {

                int valor = (int) tripleta.getDato();
                if (valor == -1) {
                    return false;
                }
            }
            actual = actual.getLigaDerecha();
        }
        return true;
    }

    //Devuelve el valor de la pista que actualmente tiene la posicion, si no la encuentra devuelve el 0
    public int devolverValor(int fila, int columna) {

        Tripleta tripleta;
        NodoDoble primerNodo = this.primerNodo();

        //Se busca la fila correspondiente
        for (int i = 1; i < fila; i++) {
            tripleta = (Tripleta) primerNodo.getValor();
            primerNodo = (NodoDoble) tripleta.getDato();
        }

        //Se comienza a recorrer esa fila hasta llegar al nodo cabeza de esa fila
        NodoDoble actual = primerNodo.getLigaDerecha();

        while (actual != primerNodo) {
            tripleta = (Tripleta) actual.getValor();
            if (tripleta.getColumna() == columna) {

                //Como los 0 no existen solo basta con comprobar que no sea -1 osea mina, como el metodo que utiliza este metodo comprueba antes que no sea un -1 por lo tanto no lo devuleve
                return (int) tripleta.getDato();
            }
            actual = actual.getLigaDerecha();
        }
        return 0;
    }

    /**
     * Se revisa que nunca sobrepasen los limites
     * para arriba se le resta a la fila 1 y se deja la columna
     * para abajo se le suma a la fila 1 y se deja la columna
     * para el lado derecho se deja la fila y se suma 1 a la columna
     * para el lado izquierdo se deja la fila y se resta 1 a la columna
     * para la esquina superior derecha se resta 1 a la fila y se suma 1 a la columna
     * para la esquina superior izquierda se resta 1 a la fila y se resta 1 a la columna
     * para la esquina inferior derecha se suma 1 a la fila y se suma 1 a la columna
     * para la esquina inferior derecha se suma 1 a la fila y se resta 1 a la columna
     * En matriz dispersa seria: recorrer por filas y dentro por columa
     * si el valor es -1 se sacan las coordenadas
     * se hacen los 8 movimientos uno por uno comprobando que no pasen los limites
     */
    //Se para en las minas, luego en la mina busca las 8 posibles opciones y crea el nodo o le suma el numero de pistas que tiene, los nodos que tiene -1 no los toma en cuenta
    public void generarPistas() {

        //Se recorre la matriz buscando minas
        NodoDoble primerNodo = this.primerNodo();

        while (primerNodo != this.nodoCabeza) {

            Tripleta tripleta;

            NodoDoble actual = primerNodo.getLigaDerecha();

            while (actual != primerNodo) {

                tripleta = (Tripleta) actual.getValor();
                int dato = (int) tripleta.getDato();

                //Se pregunta si la posicion es una mina
                if (dato == -1) {

                    int fila = tripleta.getFila();
                    int columna = tripleta.getColumna();

                    //Aca entra al metodo que crea las pistas y solo sale cuando las tenga creadas, dentro de este va comprobar
                    this.actualizarPistas(fila, columna);
                }

                actual = actual.getLigaDerecha();
            }

            tripleta = (Tripleta) primerNodo.getValor();
            primerNodo = (NodoDoble) tripleta.getDato();
        }
    }

    //Recibe la posicion de la mina y revisa que las posiciones en las que debe y pueda crear pistas
    private void actualizarPistas(int fila, int columna) {

        //Se sacan las dimensiones de la matriz
        Tripleta tripleta = (Tripleta) this.getNodoCabeza().getValor();
        int limiteFila = tripleta.getFila();
        int limiteColumna = tripleta.getColumna();
        int contador = 0;
        //Se crea un arreglo con los 8 movimientos posibles para un mejor control de las sumas
        int movimientos[][] = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}};

        //El contador se suma asi una posicion no sea valida
        while (contador != 8) {

            int nuevaFila = fila + movimientos[contador][0];
            int nuevaColumna = columna + movimientos[contador][1];

            if ((nuevaFila > 0) && (nuevaFila <= limiteFila) && (nuevaColumna > 0) && (nuevaColumna <= limiteColumna)) {

                if (comprobarDato(nuevaFila, nuevaColumna)) {

                    tripleta = new Tripleta(nuevaFila, nuevaColumna, (this.devolverValor(nuevaFila, nuevaColumna) + 1));
                    NodoDoble nodoDoble = new NodoDoble(tripleta);
                    this.conectarEnFilas(nodoDoble);
                    this.conectarEnColumnas(nodoDoble);
                }
            }
            contador++;
        }
    }

}
