import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Adrián Largo Monteagudo (bu0257)
 * @author Jorge López Sosa
 * @version     1.0
 */
public class Porte {
    private boolean[][] huecos;
    private String id;
    private Nave nave;
    private PuertoEspacial origen;
    private int muelleOrigen;
    private Fecha salida;
    private PuertoEspacial destino;
    private int muelleDestino;
    private Fecha llegada;
    private double precio;
    private ListaEnvios listaEnvios;


    /**
     * TODO: Completa el constructo de la clase
     *
     * @param id
     * @param nave
     * @param origen
     * @param muelleOrigen
     * @param salida
     * @param destino
     * @param muelleDestino
     * @param llegada
     * @param precio
     */
    public Porte(String id, Nave nave, PuertoEspacial origen, int muelleOrigen, Fecha salida, PuertoEspacial destino, int muelleDestino, Fecha llegada, double precio) {
        this.id=id;
        this.nave=nave;
        this.origen=origen;
        this.muelleOrigen=muelleOrigen;
        this.salida=salida;
        this.destino=destino;
        this.muelleDestino=muelleDestino;
        this.llegada=llegada;
        this.precio=precio;
        this.huecos = new boolean[nave.getFilas()][nave.getColumnas()];
        this.listaEnvios = new ListaEnvios(nave.getFilas()+ nave.getColumnas());

    }
    public String getID() {
        return id;
    }
    public Nave getNave(){
        return nave;
    }
    public PuertoEspacial getOrigen() {
        return origen;
    }
    public int getMuelleOrigen() {
        return muelleOrigen;
    }
    public Fecha getSalida(){
        return salida;
    }
    public PuertoEspacial getDestino() {
        return destino;
    }
    public int getMuelleDestino() {
        return muelleDestino;
    }
    public Fecha getLlegada() {
        return llegada;
    }
    public double getPrecio() {
        return precio;
    }
    // TODO: Devuelve el número de huecos libres que hay en el porte
    public int numHuecosLibres() {
        int contador = 0;
        for (int i = 0; i < huecos.length; i++){
            for (int j = 0; j < i ;j++){
                if (!huecos[i][j]){
                    contador++;
                }
            }
        }
        return contador;
    }
    // TODO: ¿Están llenos todos los huecos?
    public boolean porteLleno() {
        return (numHuecosLibres()==0);
    }
    // TODO: ¿Está ocupado el hueco consultado?
    public boolean huecoOcupado(int fila, int columna) {
        return huecos[fila-1][columna-1];
    }
    public Envio buscarEnvio(String localizador) {
        return listaEnvios.buscarEnvio(localizador);
    }


    /**
     * TODO: Devuelve el objeto Envio que corresponde con una fila o columna,
     * @param fila
     * @param columna
     * @return el objeto Envio que corresponde, o null si está libre o se excede en el límite de fila y columna
     */
    public Envio buscarEnvio(int fila, int columna) {
        if (fila > huecos.length || columna>huecos[0].length || !huecos[fila-1][columna-1])
            return null;
        else
            return listaEnvios.buscarEnvio(getID(), fila, columna);
    }


    /**
     * TODO: Método que Si está desocupado el hueco que indica el envio, lo pone ocupado y devuelve true,
     *  si no devuelve false
     * @param envio
     * @return
     */
    public boolean ocuparHueco(Envio envio) {
        if (huecos[envio.getFila()-1][envio.getColumna()-1] || porteLleno()){
            return false;
        }
        else {
            huecos[envio.getFila()-1][envio.getColumna()-1] = true;
            listaEnvios.insertarEnvio(envio);
            return true;
        }
    }


    /**
     * TODO: A través del localizador del envio, se desocupará el hueco
     * @param localizador
     * @return
     */
    public boolean desocuparHueco(String localizador) {
        boolean desocupado = false;
        Envio envio = buscarEnvio(localizador);
        if (envio!=null){
            if(huecoOcupado(envio.getFila(), envio.getColumna())){
                huecos[envio.getFila()-1][envio.getColumna()-1] = false;
                desocupado = true;
            }
        }
        return desocupado;
    }

    /**
     * TODO: Devuelve una cadena con información completa del porte
     * @return ejemplo del formato -> "Porte PM0066 de Gaia Galactic Terminal(GGT) M5 (01/01/2023 08:15:00) a
     *  Cidonia(CID) M1 (01/01/2024 11:00:05) en Planet Express One(EP-245732X) por 13424,56 SSD, huecos libres: 10"
     */
    public String toString() {
        return "Porte "+ getID()+" de "+getOrigen().toStringSimple()+" M"+getMuelleOrigen()+" ("+getSalida()+") a "+getDestino().toStringSimple()+
                " M"+getMuelleDestino()+" ("+getLlegada()+") en " +getNave().getMarca() + "(" + getNave().getModelo() + "-"+
                getNave().getMatricula() + ") por " + getPrecio() + ", huecos libres " +numHuecosLibres();
    }


    /**
     * TODO: Devuelve una cadena con información abreviada del vuelo
     * @return ejemplo del formato -> "Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05)"
     */
    public String toStringSimple() {
        return "Porte "+ getID()+" de "+getOrigen().toStringSimple()+" M"+getMuelleOrigen()+" ("+ getSalida()+") a "+getDestino().toStringSimple()+" M"+getMuelleDestino()+" ("+getLlegada() + ")";
    }


    /**
     * TODO: Devuelve true si el código origen, destino y fecha son los mismos que el porte
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        boolean coincide = false;
        if (getOrigen().getCodigo().equals(codigoOrigen) && getDestino().getCodigo().equals(codigoDestino) && getSalida().coincide(fecha)) {
            coincide = true;
        }
        return coincide;
    }


    /**
     * TODO: Muestra la matriz de huecos del porte, ejemplo:
     *        A  B  C
     *      1[ ][ ][ ]
     *      2[X][X][X]
     *      3[ ][ ][ ]
     *      4[ ][X][ ]
     *     10[ ][ ][ ]
     */
    public void imprimirMatrizHuecos() {
        char letra = 'A';
        for (int i = 0; i < huecos[0].length; i++){
            System.out.print("  ");
            System.out.print(letra);
            letra++;
        }
        System.out.println();
        int cont = 1;
        for (int i = 0; i < huecos.length;i++){
            System.out.print(cont);
            for (int j = 0; j < huecos[0].length;j++){
                if (huecos[i][j]){
                    System.out.print("[X]");
                }
                else System.out.print("[ ]");
            }
            cont++;
            System.out.println(" ");
        }

    }

    /**
     * TODO: Devuelve true si ha podido escribir en un fichero la lista de envíos del porte, siguiendo las indicaciones
     *  del enunciado
     * @param fichero
     * @return
     */
    public boolean generarListaEnvios(String fichero) {
        PrintWriter pw = null;
        try {
            File file = new File(fichero);
            pw = new PrintWriter(file);
            pw.println("-----------------------------------------------------");
            pw.println("--------- Lista de envíos del porte " + getID() + " ---------");
            pw.println("-----------------------------------------------------");
            for (int i = 0; i<huecos.length; i++){
                for (int d=0; d<huecos[i].length; d++){
                    char columna = (char)('A' + d);
                    pw.print(i+1 + "" + columna + "\t");
                    if (buscarEnvio( i+1, d+1)!=null){
                        pw.print(buscarEnvio(i+1, d+1).getCliente());
                    }
                    pw.println();
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            pw.close();
        }
    }


    /**
     * TODO: Genera un ID de porte. Este consistirá en una cadena de 6 caracteres, de los cuales los dos primeros
     *  serán PM y los 4 siguientes serán números aleatorios.
     *  NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand
     * @return ejemplo -> "PM0123"
     */
    public static String generarID(Random rand) {
        String num = "";
        for (int i = 0; i<4; i++){
            num += (rand.nextInt(0, 9));
        }
        return "PM" + num;
    }

    /**
     * TODO: Crea y devuelve un objeto Porte de los datos que selecciona el usuario de puertos espaciales
     *  y naves y la restricción de que no puede estar repetido el identificador, siguiendo las indicaciones
     *  del enunciado.
     *  La función solicita repetidamente los parametros hasta que sean correctos
     * @param teclado
     * @param rand
     * @param puertosEspaciales
     * @param naves
     * @param portes
     * @return
     */
    public static Porte altaPorte(Scanner teclado, Random rand,
                                  ListaPuertosEspaciales puertosEspaciales,
                                  ListaNaves naves,
                                  ListaPortes portes) {
        Porte porte = null;
        PuertoEspacial puertoEspacialO = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Origen: ");
        int muelleO = Utilidades.leerNumero(teclado, "Ingrese el muelle de origen (1 - " + puertoEspacialO.getMuelles() + "): ", 1, 4);
        PuertoEspacial puertoEspacialD = puertosEspaciales.seleccionarPuertoEspacial(teclado, "Ingrese código de puerto Destino: ");
        int terminald = Utilidades.leerNumero(teclado, "Ingrese Terminal de Destino (1 - " + puertoEspacialD.getMuelles() + "): ", 1, 6);
        Nave nave = naves.seleccionarNave(teclado, "Ingrese matricula de la nave: ", puertoEspacialO.distancia(puertoEspacialD));
        Fecha fechaS = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de salida:");
        Fecha fechaL = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de llegada:");
        while (fechaL.anterior(fechaS)){
            System.out.println("La fecha de llegada debe ser posterior a salida");
            fechaS = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de salida:");
            fechaL = Utilidades.leerFechaHora(teclado, "Introduzca la fecha de llegada:");
        }
        System.out.print("Ingrese precio de pasaje: ");
        double precio = teclado.nextInt();
        String id = generarID(rand);
        while (portes.buscarPorte(id)!=null){
            id = generarID(rand);
        }
        porte = new Porte(id, nave, puertoEspacialO, muelleO, fechaS, puertoEspacialD, terminald, fechaL, precio);
        System.out.println("Porte " + porte.id + " creado correctamente");
        return porte;
    }
}
