import java.awt.Image;

import model.MatrizEnForma1;
import model.NodoDoble;
import model.Tripleta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Interfaz extends javax.swing.JFrame {


    int numFilas = 8;
    int numColumnas = 8;
    int numMinas = 10;
    //Se crea para no tener que volver a recorrer la matriz con la posicion de las minas
    int[][] posMinas;
    int aux1;
    int aux2;
    int abiertos = 0;
    int contBanderas = 0;
    int marcas = 0;
    long TInicio, TFin, tiempo;

    JButton[][] botones;

    MatrizEnForma1 matrizEnForma1;
    


    public Interfaz() {

        mensaje("Bienvenido al buscaminas \n", "Buscaminas", 1);
        mensaje("Desarrollado por: \n Sara Uribe Zapata \n Victor Manuel Osorio Garcia", "Buscaminas", 1);
        Menu();
    }

    public void Menu() {

        //Se reinician las variables necesarias
        abiertos = 0;
        contBanderas = 0;
        marcas = 0;
        
        String menu = "Ingrese una opción para empezar a jugar: \n" +
                "   1. Para jugar nivel facil \n" +
                "   2. Para jugar el nivel medio \n" +
                "   3. Para jugar el nivel experto \n" +
                "   4. Para jugar el nivel personalizado \n" +
                "   5. Para salir";

        String opcion = "";

        do {

            opcion = JOptionPane.showInputDialog(menu);

            switch (opcion) {

                case "1":

                    numFilas = 8;
                    numColumnas = 8;
                    numMinas = 10;
                    this.posMinas = new int[numMinas][2];
                    initComponents();
                    imprimirBotones();
                    generarMatriz();
                    break;

                case "2":

                    numFilas = 16;
                    numColumnas = 16;
                    numMinas = 40;
                    this.posMinas = new int[numMinas][2];
                    initComponents();
                    imprimirBotones();
                    generarMatriz();
                    break;

                case "3":

                    numFilas = 16;
                    numColumnas = 30;
                    numMinas = 99;
                    this.posMinas = new int[numMinas][2];
                    initComponents();
                    imprimirBotones();
                    generarMatriz();
                    break;

                case "4":

                    //Se pone como -1 para controlar que se vuelva a repetir el numero de minas hasta que ingrese un valor permitido y no permite avanzar hasta no poner datos correctos
                    int fil = -1;

                    do {

                        //El limite de filas en pantalla es 22
                        if (fil > 22) {
                            mensaje("El numero de filas maximas es de 22", "Buscaminas", 1);
                        }

                        String filas = "Ingrese numero de filas";

                        try {
                            fil = Integer.parseInt(JOptionPane.showInputDialog(filas));
                            numFilas = fil;
                            if (fil < 0) {
                                mensaje("El numero de filas no puede ser negativo", "Buscaminas", 1);
                            }
                        } catch (NumberFormatException e) {
                            mensaje("Debe ser un valor numerico", "Buscaminas", 1);
                        }
                    } while (fil > 22 || fil < 0);

                    int col = -1;

                    do {

                        //El limite de columnas en pantalla es 44
                        if (col > 44) {
                            mensaje("El numero de columnas maximas es de 44", "Buscaminas", 1);
                        }

                        String columnas = "Ingrese numero de columnas";

                        try {
                            col = Integer.parseInt(JOptionPane.showInputDialog(columnas));
                            numColumnas = col;
                            if (col < 0) {
                                mensaje("El numero de columnas no puede ser negativo", "Buscaminas", 1);
                            }
                        } catch (NumberFormatException e) {
                            mensaje("Debe ser un valor numerico", "Buscaminas", 1);
                        }
                    } while (col > 44 || col < 0);

                    int min = -1;

                    do {

                        String minas = "Ingrese numero de minas";

                        try {
                            min = Integer.parseInt(JOptionPane.showInputDialog(minas));
                            numMinas = min;
                            if (min < 0) {
                                mensaje("El numero de minas no puede ser negativo", "Buscaminas", 1);
                            }
                        } catch (NumberFormatException e) {
                            mensaje("Debe ser un valor numerico", "Buscaminas", 1);
                        }

                        if (min >= (numFilas * numColumnas)) {
                            mensaje("El numero de minas es mayor a lo esperado ", "Buscaminas", 1);
                        }

                    } while (min < 0 || min >= (numFilas * numColumnas));

                    this.posMinas = new int[numMinas][2];
                    initComponents();
                    imprimirBotones();
                    generarMatriz();
                    break;

                case "5":

                    mensaje("Chao \n Practica pa 5 :v", "Buscaminas", 1);
                    System.exit(0);
                    break;

                default:

                    mensaje("Opción incorrecta", "Buscaminas", 1);
                    break;
            }

        } while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3") && !opcion.equals("4"));
    }

    //metodo que imprime los botones
    private void imprimirBotones() {

        //Inicializamos el tamaño de cada boton 
        int x = 30;
        int y = 30;
        int ancho = 30;
        int alto = 30;

        //Se crean los botones automaticamente y que concuerden su valor con el valor de la matriz
        botones = new JButton[numFilas][numColumnas];
        for (int i = 0; i < botones.length; i++) {

            for (int j = 0; j < botones[i].length; j++) {

                botones[i][j] = new JButton();
                botones[i][j].setName((i + 1) + "," + (j + 1)); //Se pone el +1 para que no empiecen en 0
                botones[i][j].setBorder(null);

                if (i == 0 && j == 0) {

                    botones[i][j].setBounds(x,
                            y, ancho, alto);

                } else if (i == 0 && j != 0) {

                    botones[i][j].setBounds(botones[i][j - 1].getX() + botones[i][j - 1].getWidth(),
                            y, ancho, alto);

                } else {

                    botones[i][j].setBounds(botones[i - 1][j].getX(),
                            botones[i - 1][j].getY() + botones[i - 1][j].getHeight(),
                            ancho, alto);
                }

                //Agrega la funcion del click derecho a los botones. No puedo reducir los aux porque tira error :v
                aux1 = i;
                aux2 = j;
                botones[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
                    int auxFila = aux1;
                    int auxColumna = aux2;

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        formMousePressed(evt, auxFila, auxColumna);
                    }
                });

                //Agrega la funcion darle click izquierdo a un boton
                botones[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }
                });

                //Agrega los botones al pane
                getContentPane().add(botones[i][j]);
            }
        }

        //Organiza el tamaño de la ventana para que salgan todos los botones visibles
        this.setSize(botones[numFilas - 1][numColumnas - 1].getX()
                        + botones[numFilas - 1][numColumnas - 1].getWidth() + 30,
                botones[numFilas - 1][numColumnas - 1].getY()
                        + botones[numFilas - 1][numColumnas - 1].getHeight() + 70
        );
        lblContador.setText(""+marcas);
        TInicio = System.currentTimeMillis(); 
    }

    private void btnClick(ActionEvent e) {

        //Se detecta cual fue el boton que recibio el click
        JButton btn = (JButton) e.getSource();

        //Saca las coordenadas de los botones y las combierte en numero
        String[] coordenada = btn.getName().split(",");
        int posFila = Integer.parseInt(coordenada[0]);
        int posColumna = Integer.parseInt(coordenada[1]);

        //Revisa esas coordenadas en la matriz para saber si es mina o no
        int valor = matrizEnForma1.devolverValor(posFila, posColumna);

        switch (valor) {

            //Caso mina
            case -1:

                for (int i = 0; i < posMinas.length; i++) {

                    //Deja las casillas aseguradas que son minas
                    if (botones[posMinas[i][0] - 1][posMinas[i][1] - 1].getIcon() == null) {
                        botones[posMinas[i][0] - 1][posMinas[i][1] - 1].setIcon(setIcono("/imagenes/mina.png", botones[posMinas[i][0] - 1][posMinas[i][1] - 1]));
                    }
                }

                //Bloquea todas las casillas y desasegura las que no sean minas
                for (int i = 0; i < botones.length; i++) {
                    for (int j = 0; j < botones[i].length; j++) {

                        coordenada = botones[i][j].getName().split(",");
                        posFila = Integer.parseInt(coordenada[0]);
                        posColumna = Integer.parseInt(coordenada[1]);
                        valor = matrizEnForma1.devolverValor(posFila, posColumna);

                        if (valor != -1 && botones[i][j].getIcon() != null) {
                            botones[i][j].setIcon(null);
                            botones[i][j].setText("");
                        }
                        botones[i][j].setEnabled(false);
                    }
                }

                repetir(0);
                break;

            //Caso casilla vacia    
            case 0:

                botones[posFila - 1][posColumna - 1].setVisible(false);
                mostrarSinMinas(posFila, posColumna);
                abiertos += 1;
                break;

            //Caso pista    
            default:
                abiertos += 1;
                botones[posFila - 1][posColumna - 1].setText("" + valor);
                //Esto para que se vea mejor... o no o para controlar mejor cuando se gane
                botones[posFila - 1][posColumna - 1].setEnabled(false);
                break;
        }

        if (abiertos >= (numFilas * numColumnas) - numMinas) {
            repetir(1);
        }
    }

    //Mensaje de volver a jugar o no, si tipo es 0 es porque perdio si es 1 gano
    private void repetir(int tipo) {

        TFin = System.currentTimeMillis();
        tiempo = TFin - TInicio;
        tiempo/=1000;
        int horas = 0;
        int minutos = 0;
        minutos = (int) (tiempo/60);
        if (minutos >= 60){
            horas = (int) minutos/60;
            tiempo = (int) (tiempo - (horas*3600));
            minutos = (int) (tiempo/60);
        }
        int segundos = (int) (tiempo%60);
        
        
        if (tipo == 0) {
            JOptionPane.showMessageDialog(rootPane, "Perdiste \n Tiempo empleado: \n " + "0" + horas + ":0" + minutos + ":0" + segundos);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Ganaste \n Tiempo empleado: \n " + "0" + horas + ":0" + minutos + ":0" + segundos);
        }

        String vuelve = "¿Quieres seguir jugando? s/n";
        String rpta = "";

        do {
            rpta = JOptionPane.showInputDialog(vuelve);
            switch (rpta) {
                case "s":

                    getContentPane().removeAll();
                    Menu();
                    break;

                case "n":

                    mensaje("Gracias por jugar con nosotros \n Practica pa 5 :v", "Buscaminas", 1);
                    System.exit(0);
                    break;

                default:

                    mensaje("opcion incorrecta", "buscaminas", 1);
                    break;
            }
        } while (!rpta.equals("s"));
    }

    //Metodo recursivo que desbloquea las casillas al rededor de una casilla vacia
    private void mostrarSinMinas(int fila, int columna) {

        int limiteFila = numFilas;
        int limiteColumna = numColumnas;
        int contador = 0;
        //Se crea un arreglo con los 8 movimientos posibles para un mejor control de las sumas
        int movimientos[][] = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {-1, 1}, {-1, -1}, {1, 1}, {1, -1}};

        //El contador siempre suma en caso de que la posicion no se pueda destapar
        while (contador != 8) {

            int nuevaFila = fila + movimientos[contador][0];
            int nuevaColumna = columna + movimientos[contador][1];

            if ((nuevaFila > 0) && (nuevaFila <= limiteFila) && (nuevaColumna > 0) && (nuevaColumna <= limiteColumna)) {

                //Saca el valor de la casilla para mirar si tiene pista
                int valor = matrizEnForma1.devolverValor(nuevaFila, nuevaColumna);

                switch (valor) {

                    //Si es cero vuelve y entra al metodo recursivamente a desbloque las 8 al rededor
                    case 0:

                        //Se comprueba que el boton al que va a entrar sea visible y no este asegurado para evitar ciclos infinitos y que no se desaparezca una casilla asegurada
                        if (botones[nuevaFila - 1][nuevaColumna - 1].isVisible() && botones[nuevaFila - 1][nuevaColumna - 1].getIcon() == null) {
                            botones[nuevaFila - 1][nuevaColumna - 1].setVisible(false);
                            mostrarSinMinas(nuevaFila, nuevaColumna);
                            abiertos += 1;
                        }
                        break;

                    //Si es pista le pone ese dato al boton
                    default:
                        if (botones[nuevaFila - 1][nuevaColumna - 1].isEnabled()) {
                            botones[nuevaFila - 1][nuevaColumna - 1].setText("" + valor);
                            //Esto para que se vea mejor... o no o para controlar mejor cuando se gane
                            botones[nuevaFila - 1][nuevaColumna - 1].setEnabled(false);
                            abiertos += 1;
                        }
                        break;
                }
            }
            contador++;
        }
    }

    //Metodo para generar la matriz en forma 1
    private void generarMatriz() {

        int contadorMinas = 0;
        matrizEnForma1 = new MatrizEnForma1(numFilas, numColumnas);
        matrizEnForma1.nodosCabezas();

        //Genera las minas aleatoreamente
        while (contadorMinas != numMinas) {

            int fila = (int) Math.floor(Math.random() * numFilas + 1);
            int columna = (int) Math.floor(Math.random() * numColumnas + 1);

            //Revisa si la posicion ya tenia mina para que se creen el numero de minas diferentes
            if (matrizEnForma1.comprobarDato(fila, columna)) {

                //Se trabaja con -1 para no confundir con las pistas
                Tripleta tripleta = new Tripleta(fila, columna, -1);
                NodoDoble nodoDoble = new NodoDoble(tripleta);
                matrizEnForma1.conectarEnFilas(nodoDoble);
                matrizEnForma1.conectarEnColumnas(nodoDoble);
                //Guarda las coordenadas de la mina
                posMinas[contadorMinas][0] = fila;
                posMinas[contadorMinas][1] = columna;
                contadorMinas++;
            }
        }
        matrizEnForma1.generarPistas();
        matrizEnForma1.muestraComoCuadricula();
    }

    public static void mensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, tipo);
    }

    //Metodo para las funciones del click derecho
    private void formMousePressed(java.awt.event.MouseEvent evt, int fila, int columna) {

        //Se mira si el boton apretado ya estaba previamente asegurado
        if (botones[fila][columna].getIcon() != null) {

            //Si es asegurado y se dio click derecho dos veces se vuelve a desbloquear la casilla
            if (evt.getButton() == 3 && evt.getClickCount() == 2) {

                botones[fila][columna].setIcon(null);
                botones[fila][columna].setText("");
                botones[fila][columna].setEnabled(true);

                String[] coordenada = botones[fila][columna].getName().split(",");
                int posFila = Integer.parseInt(coordenada[0]);
                int posColumna = Integer.parseInt(coordenada[1]);
                int valor = matrizEnForma1.devolverValor(posFila, posColumna);
                marcas--;
                lblContador.setText(""+marcas);

                //En caso que desbloquee una casilla que si tenga mina se resta el contador
                if (valor != -1) {
                    contBanderas--;
                }
            }
        }

        //Se se dio click derecho solo una vez sobre una casilla que no tenga la F y que no tenga una pista se asegurada se vuelve asegurada
        if (evt.getButton() == 3 && evt.getClickCount() == 1 && botones[fila][columna].getText().equals("") && botones[fila][columna].isEnabled()) {

            botones[fila][columna].setIcon(setIcono("/imagenes/icono.jpg", botones[fila][columna]));
            botones[fila][columna].setEnabled(false);

            String[] coordenada = botones[fila][columna].getName().split(",");
            int posFila = Integer.parseInt(coordenada[0]);
            int posColumna = Integer.parseInt(coordenada[1]);
            int valor = matrizEnForma1.devolverValor(posFila, posColumna);
            marcas++;
            lblContador.setText(""+marcas);
            
            //En caso que bloquee una casilla que si tenga mina se suma el contador
            if (valor == -1) {
                contBanderas++;
            }
        }

        if (contBanderas == numMinas) {
            repetir(1);
        }
    }

    public Icon setIcono(String url, JButton boton) {

        ImageIcon icono = new ImageIcon(getClass().getResource(url));
        int ancho = boton.getWidth();
        int alto = boton.getHeight();
        ImageIcon icon = new ImageIcon(icono.getImage().getScaledInstance(alto, ancho, Image.SCALE_DEFAULT));
        return icon;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblContador = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblContador.setBackground(new java.awt.Color(255, 255, 255));
        lblContador.setForeground(new java.awt.Color(255, 0, 0));
        lblContador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContador.setText("0");
        lblContador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Casillas marcadas:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblContador)
                .addContainerGap(281, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContador)
                    .addComponent(jLabel1))
                .addContainerGap(269, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new Interfaz().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel lblContador;
    // End of variables declaration//GEN-END:variables
}
