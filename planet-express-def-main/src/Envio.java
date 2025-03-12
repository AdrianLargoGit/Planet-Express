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
public class Envio {

    private String localizador;
    private Porte porte;
    private Cliente cliente;
    private int fila;
    private int columna;
    private double precio;

    /**
     * Constructor of the class
     *
     * @param localizador
     * @param porte
     * @param cliente
     * @param fila
     * @param columna
     * @param precio
     */
    public Envio(String localizador, Porte porte, Cliente cliente, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.porte = porte;
        this.cliente = cliente;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }
    public String getLocalizador() {
        return localizador;
    }
    public Porte getPorte() {
        return porte;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public int getFila() {
        return fila;
    }
    public int getColumna() {
        return columna;
    }
    // TODO: Ejemplos: "1A" para el hueco con fila 1 y columna 1, "3D" para el hueco con fila 3 y columna 4
    public String getHueco() {
        return getFila() + "" + (char)(90-(25-(getColumna()-1)));
    }
    public double getPrecio() {
        return precio;
    }
    //TODO: Texto que debe generar: Envío PM1111AAAABBBBC para Porte PM0066 de GGT M5 (01/01/2023 08:15:00) a CID M1 (01/01/2024 11:00:05) en hueco 6C por 13424,56 SSD
    public String toString() {
        return "Envío " + getLocalizador() + " para " + getPorte().toStringSimple() + " en hueco " + getHueco() + " por " + getPrecio() + " SSD";
    }
    // TODO: Cancela este envío, eliminándolo de la lista de envíos del porte y del cliente correspondiente
    public boolean cancelar() {
        return getCliente().cancelarEnvio(getLocalizador());
    }

    /**
     * TODO: Método para imprimir la información de este envío en un fichero que respecta el formato descrito en el
     *  enunciado
     * @param fichero
     * @return Devuelve la información con el siguiente formato como ejemplo ->
     *     -----------------------------------------------------
     *     --------- Factura del envío PM1111AAAABBBBC ---------
     *     -----------------------------------------------------
     *     Porte: PM0066
     *     Origen: Gaia Galactic Terminal (GGT) M5
     *     Destino: Cidonia (CID) M1
     *     Salida: 01/01/2023 08:15:00
     *     Llegada: 01/01/2024 11:00:05
     *     Cliente: Zapp Brannigan, zapp.brannigan@dop.gov
     *     Hueco: 6C
     *     Precio: 13424,56 SSD
     */
    public boolean generarFactura(String fichero) {
        PrintWriter salida = null;
        try {
            File factura = new File(fichero);
            salida = new PrintWriter(factura);
            salida.println("-----------------------------------------------------");
            salida.println("--------- Factura del envío " + getLocalizador() + " ---------");
            salida.println("-----------------------------------------------------");
            salida.println("Porte: " + getPorte().getID());
            salida.println("Origen: " + getPorte().getOrigen().getNombre() + " (" + getPorte().getOrigen().getCodigo() + ") " + getPorte().getOrigen().getMuelles());
            salida.println("Destino: " + getPorte().getDestino().getNombre() + " (" + getPorte().getDestino().getCodigo() + ") " + getPorte().getDestino().getMuelles());
            salida.println("Salida: " + getPorte().getSalida());
            salida.println("LLegada: " + getPorte().getLlegada());
            salida.println("Cliente: " + getCliente().toString());
            salida.println("Hueco: " + getHueco());
            salida.println("Precio: " + getPrecio() + " SSD");
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }finally {
            salida.close();
        }
    }



    /**
     *	TODO: Genera un localizador de envío. Este consistirá en una cadena de 15 caracteres, de los cuales los seis
	 *   primeros será el ID del porte asociado y los 9 siguientes serán letras mayúsculas aleatorias. Ejemplo: PM0123ABCD
     *   NOTA: Usar el objeto rand pasado como argumento para la parte aleatoria.
     * @param rand
     * @param idPorte
     * @return
     */
    public static String generarLocalizador(Random rand, String idPorte) {
        StringBuilder localizador = new StringBuilder(idPorte);
        String aleatorio = "";
        for (int i = 0; i<9; i++) {
            char car = (char) rand.nextInt(65,90);
            aleatorio += car;
        }
        StringBuilder localizador2 = new StringBuilder(aleatorio.toUpperCase());
        return localizador + "" + localizador2;
    }


    /**
     * TODO: Método para crear un nuevo envío para un porte y cliente específico, pidiendo por teclado los datos
     *  necesarios al usuario en el orden y con los textos indicados en los ejemplos de ejecución del enunciado
     *  La función solicita repetidamente los parámetros hasta que sean correctos
     * @param teclado
     * @param rand
     * @param porte
     * @param cliente
     * @return Envio para el porte y cliente especificados
     */
    public static Envio altaEnvio(Scanner teclado, Random rand, Porte porte, Cliente cliente) {
        String localizador = generarLocalizador(rand, porte.getID());
        porte.imprimirMatrizHuecos();
        int fila = Utilidades.leerNumero(teclado, "Fila del Hueco: ", 0, porte.getNave().getFilas());
        int columna = Utilidades.leerNumero(teclado, "Columna del Hueco: ", 0, porte.getNave().getColumnas());
        while (porte.huecoOcupado(fila, columna)){
            System.out.println("Hueco ocupado");
            fila = Utilidades.leerNumero(teclado, "Fila del Hueco: ", 0, porte.getNave().getFilas());
            columna = Utilidades.leerNumero(teclado, "Columna del Hueco: ", 0, porte.getNave().getColumnas());
        }
        System.out.print("Precio del Envío: ");
        double precio = teclado.nextDouble(); //no hay maximo
        Envio envio = new Envio(localizador, porte, cliente, fila, columna,precio);
        cliente.aniadirEnvio(envio);
        porte.ocuparHueco(envio);
        System.out.println("Envío "+ localizador + " creado correctamente.");
        return envio;
    }
}