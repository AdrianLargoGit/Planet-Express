import java.io.*;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Adrián Largo Monteagudo (bu0257)
 * @author Jorge López Sosa
 * @version     1.0
 */
public class ListaPortes {
    private Porte[] portes;
    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaPortes(int capacidad) {
        portes = new Porte[capacidad];
    }
    // TODO: Devuelve el número de portes que hay en la lista
    public int getOcupacion() {
        int contador = 0;
        for (int i = 0; i<portes.length ; i++){
            if (portes[i]!=null){
                contador++;
            }
        }
        return contador;
    }
    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return (getOcupacion() == portes.length);
    }

	//TODO: devuelve un porte dado un indice
    public Porte getPorte(int i) {
        return portes[i];
    }


    /**
     * TODO: Devuelve true si puede insertar el porte
     * @param porte
     * @return false en caso de estar llena la lista o de error
     */
    public boolean insertarPorte(Porte porte) {
        boolean insertado = false;
        if (!estaLlena()){
            portes[getOcupacion()] = porte;
            insertado = true;
        }
        return insertado;
    }


    /**
     * TODO: Devuelve el objeto Porte que tenga el identificador igual al parámetro id
     * @param id
     * @return el objeto Porte que encontramos o null si no existe
     */
    public Porte buscarPorte(String id) {
        Porte porte = null;
        for (int i=0; i<getOcupacion(); i++){
            if (portes[i].getID().equalsIgnoreCase(id)){
                porte = getPorte(i);
            }
        }
        return porte;
    }

    /**
     * TODO: Devuelve un nuevo objeto ListaPortes conteniendo los Portes que vayan de un puerto espacial a otro
     *  en una determinada fecha
     * @param codigoOrigen
     * @param codigoDestino
     * @param fecha
     * @return
     */
    public ListaPortes buscarPortes(String codigoOrigen, String codigoDestino, Fecha fecha) {
        ListaPortes listaPortes = null;
        int contador = 0;
        for (int i=0; i<getOcupacion(); i++){
            if (portes[i].coincide(codigoOrigen, codigoDestino, fecha)){
                contador++;
            }
        }
        if (contador>0){
            listaPortes = new ListaPortes(contador);
            for (int i=0; i<getOcupacion(); i++){
                boolean repetido = false;
                if (portes[i].coincide(codigoOrigen, codigoDestino, fecha)){
                    for (int d = 0; d< listaPortes.getOcupacion(); d++){
                        if (portes[i]==portes[d]){
                            repetido = true;
                        }
                    }
                    if(!repetido){
                        listaPortes.insertarPorte(portes[i]);
                    }
                }
            }
        }
        return listaPortes;
    }

    /**
     * TODO: Muestra por pantalla los Portes siguiendo el formato de los ejemplos del enunciado
     */
    public void listarPortes() {
        for (int i=0; i<getOcupacion(); i++){
            System.out.println(portes[i].toStringSimple());
        }
    }


    /**
     * TODO: Permite seleccionar un Porte existente a partir de su ID, usando el mensaje pasado como argumento para
     *  la solicitud y siguiendo el orden y los textos mostrados en el enunciado, y usando la cadena cancelar para
     *  salir devolviendo null.
     *  La función solicita repetidamente hasta que se introduzca un ID correcto
     * @param teclado
     * @param mensaje
     * @param cancelar
     * @return
     */
    public Porte seleccionarPorte(Scanner teclado, String mensaje, String cancelar) {
        Porte porte = null;
        boolean cancelado = false;
        String id = Utilidades.leerCadena(teclado, mensaje);
        if (id.equals(cancelar)){
            cancelado = true;
        } else{
            for (int i=0; i<getOcupacion(); i++){
                if (portes[i].getID().equalsIgnoreCase(id)){
                    porte = getPorte(i);
                }
            }
            while (porte == null && !cancelado){
                System.out.println("No se ha encotrado su porte.");
                id = Utilidades.leerCadena(teclado, mensaje);
                if (id.equals(cancelar)){
                    cancelado = true;
                } else {
                    for (int i = 0; i < getOcupacion(); i++) {
                        if (portes[i].getID().equalsIgnoreCase(id)) {
                            porte = getPorte(i);
                        }
                    }
                }
            }
        }
        return porte;
    }

    /**
     * TODO: Ha de escribir la lista de Portes en la ruta y nombre del fichero pasado como parámetro.
     *  Si existe el fichero, SE SOBREESCRIBE, si no existe se crea.
     * @param fichero
     * @return
     */
    public boolean escribirPortesCsv(String fichero) {
        PrintWriter salida = null;
        try{
            File file = new File(fichero);
            if (!file.exists()){
                file.createNewFile();
            }
            salida = new PrintWriter((fichero));
            for (int i = 0; i<getOcupacion(); i++){
                salida.print(portes[i].getID());
                salida.print(";");
                salida.print(portes[i].getNave().getMatricula());
                salida.print(";");
                salida.print(portes[i].getOrigen().getCodigo());
                salida.print(";");
                salida.print(portes[i].getMuelleOrigen());
                salida.print(";");
                salida.print(portes[i].getSalida().toString());
                salida.print(";");
                salida.print(portes[i].getDestino().getCodigo());
                salida.print(";");
                salida.print(portes[i].getMuelleDestino());
                salida.print(";");
                salida.print(portes[i].getLlegada().toString());
                salida.print(";");
                salida.println(portes[i].getPrecio());

            }
        } catch (IOException ex){
            System.out.println("Error de escritura de fichero " + fichero);
        } finally {
            salida.close();
        }
            return true;
    }

    /**
     * TODO: Genera una lista de Portes a partir del fichero CSV, usando los límites especificados como argumentos para
     *  la capacidad de la lista
     * @param fichero
     * @param capacidad
     * @param puertosEspaciales
     * @param naves
     * @return
     */
    public static ListaPortes leerPortesCsv(String fichero, int capacidad, ListaPuertosEspaciales puertosEspaciales, ListaNaves naves) {
        ListaPortes listaPortes = new ListaPortes(capacidad);
        Scanner sc;
        try {
            sc = new Scanner(new FileReader(fichero));
            String leido;
            while (sc.hasNext() && (leido= sc.nextLine())!=null && !listaPortes.estaLlena()){
                String[] leidos = leido.split(";");
                Nave nave = naves.buscarNave(leidos[1]);
                PuertoEspacial puertoEspacial0 = puertosEspaciales.buscarPuertoEspacial(leidos[2]);
                int muelleO = Integer.parseInt(leidos[3]);
                Fecha fechaS = Fecha.fromString(leidos[4]);
                PuertoEspacial puertoEspacialD = puertosEspaciales.buscarPuertoEspacial(leidos[5]);
                int muelleD = Integer.parseInt(leidos[6]);
                Fecha fechaL = Fecha.fromString(leidos[7]);
                double precio = Double.parseDouble(leidos[8]);
                Porte porte = new Porte(leidos[0], nave, puertoEspacial0, muelleO, fechaS, puertoEspacialD, muelleD, fechaL, precio);
                listaPortes.insertarPorte(porte);
            }
        } catch (Exception e) {
            System.out.println("Error de lectura de fichero " + fichero);
        } finally {
            System.out.println("Lista de Portes creada");
        }
        return listaPortes;
    }
}
