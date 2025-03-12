import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Adrián Largo Monteagudo (bu0257)
 * @author Jorge López Sosa
 * @version     1.0
 */
public class ListaEnvios {
    private Envio[] envios;
    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaEnvios(int capacidad) {
        envios = new Envio[capacidad];
    }
    // TODO: Devuelve el número de envíos que hay en la lista
    public int getOcupacion() {
        int contador = 0;
        for (int i = 0; i<envios.length ; i++){
            if (envios[i]!=null){
                contador++;
            }
        }
        return contador;
    }
    // TODO: ¿Está llena la lista de envíos?
    public boolean estaLlena() {
        return (getOcupacion()== envios.length);
    }
	//TODO: Devuelve el envio dado un indice
    public Envio getEnvio(int i) {
        return envios[i];
    }

    /**
     * TODO: insertamos un nuevo envío en la lista
     * @param envio
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarEnvio(Envio envio) {
        boolean aniadida = false;
        if (!estaLlena()){
            envios[getOcupacion()] = envio;
            aniadida = true;
        }
        return aniadida;
    }

    /**
     * TODO: Buscamos el envio a partir del localizador pasado como parámetro
     * @param localizador
     * @return el envio que encontramos o null si no existe
     */
    public Envio buscarEnvio(String localizador) {
        Envio envio = null;
        for (int i = 0; i< getOcupacion(); i++){
            if (envios[i].getLocalizador().equals(localizador)){
                envio = envios[i];
            }
        }
        return envio;
    }

    /**
     * TODO: Buscamos el envio a partir del idPorte, fila y columna pasados como parámetros
     * @param idPorte
     * @param fila
     * @param columna
     * @return el envio que encontramos o null si no existe
     */
    public Envio buscarEnvio(String idPorte, int fila, int columna) {
        Envio envio = null;
        for (int i = 0; i< getOcupacion(); i++){
            if (envios[i].getPorte().getID().equals(idPorte)){
                if (envios[i].getFila() == fila){
                    if (envios[i].getColumna() == columna){
                        envio = envios[i];
                    }
                }
            }
        }
        return envio;
    }

    /**
     * TODO: Eliminamos un envio a través del localizador pasado por parámetro
     * @param localizador
     * @return True si se ha borrado correctamente, false en cualquier otro caso
     */
    public boolean eliminarEnvio (String localizador) {
        boolean eliminado = false;
        for (int i= 0; i< getOcupacion(); i++){
            if(envios[i].getLocalizador().equals(localizador)){
                if (envios[i].getPorte().desocuparHueco(envios[i].getLocalizador())){
                    for (int d = i; d<getOcupacion(); d++){
                        if (d == getOcupacion()-1){
                            envios[d] = null;
                            eliminado = true;
                            System.out.println("Envío cancelado correctamente");
                        }
                        envios[d] = envios[d+1];
                    }
                }
            }
        }
        return eliminado;
    }

    /**
     * TODO: Muestra por pantalla los Envios de la lista, con el formato que aparece
     * en el enunciado
     */
    public void listarEnvios() {
        for (int i= 0; i< getOcupacion(); i++){
            if (envios[i]!=null){
                System.out.println(envios[i]);
            }
        }
    }

    /**
     * TODO: Permite seleccionar un Envio existente a partir de su localizador, usando el mensaje pasado como argumento
     *  para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente hasta que se introduzca un localizador correcto
     * @param teclado
     * @param mensaje
     * @return
     */
    public Envio seleccionarEnvio(Scanner teclado, String mensaje) {
        Envio envio = null;
        String localizador;
        boolean encontrado = false;
        do{
            localizador = Utilidades.leerCadena(teclado, mensaje);
            if (buscarEnvio(localizador) != null){
                envio = buscarEnvio(localizador);
                encontrado = true;
            } else System.out.println("Localizador incorrecto");
        }while (!encontrado);
        return envio;
    }



    /**
     * TODO: Añade los Envios al final de un fichero CSV, SIN SOBREESCRIBIR la información
     * @param fichero
     * @return
     */
    public boolean aniadirEnviosCsv(String fichero) {
        PrintWriter pw = null;
        BufferedReader bf = null;
        try {
            pw = new PrintWriter(new FileWriter(fichero, true));
            bf = new BufferedReader(new FileReader(fichero));
            String linea;
            for (int i =0; i<getOcupacion(); i++){
                while ((linea=bf.readLine())!=null){
                    if (linea.contains(envios[i].getLocalizador())){
                        return false;
                    }
                }
                pw.println();
                pw.print(envios[i].getLocalizador());
                pw.print(";");
                pw.print(envios[i].getPorte().getID());
                pw.print(";");
                pw.print(envios[i].getCliente().getEmail());
                pw.print(";");
                pw.print(envios[i].getFila());
                pw.print(";");
                pw.print(envios[i].getColumna());
                pw.print(";");
                pw.print(envios[i].getPrecio());
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error de escritura en fichero " + fichero);
            return false;
        } finally {
            pw.close();
        }
    }

    /**
     * TODO: Lee los Envios del fichero CSV y los añade a las listas de sus respectivos Portes y Clientes
     * @param ficheroEnvios
     * @param portes
     * @param clientes
     */
    public static void leerEnviosCsv(String ficheroEnvios, ListaPortes portes, ListaClientes clientes) {
        Scanner sc;
        try {
            sc = new Scanner(new FileReader(ficheroEnvios));
            String leido;
            while (sc.hasNext() && (leido= sc.nextLine())!=null){
                String[] leidos = leido.split(";");
                int filas = Integer.parseInt(leidos[3]);
                int columnas = Integer.parseInt(leidos[4]);
                double precio = Double.parseDouble(leidos[5]);
                Envio envio = new Envio(leidos[0], portes.buscarPorte(leidos[1]), clientes.buscarClienteEmail(leidos[2]), filas, columnas, precio);
                if (portes.buscarPorte(leidos[1])!=null){
                    portes.buscarPorte(leidos[1]).ocuparHueco(envio);
                } else System.out.println("Porte del envio " + envio.getLocalizador() + " no encontrado");
                if (clientes.buscarClienteEmail(leidos[2])!=null){
                    clientes.buscarClienteEmail(leidos[2]).aniadirEnvio(envio);
                } else System.out.println("Cliente  del envio " + envio.getLocalizador() + " no encontrado");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero "+ficheroEnvios+" no encontrado.");
        } finally {
            System.out.println("Lista de Envíos leída.");
        }
    }
}
