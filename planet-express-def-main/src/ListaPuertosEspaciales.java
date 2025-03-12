import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Adrián Largo Monteagudo (bu0257)
 * @author Jorge López Sosa
 * @version     1.0
 */
public class ListaPuertosEspaciales {
    private PuertoEspacial[] lista;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaPuertosEspaciales(int capacidad) {
        lista = new PuertoEspacial[capacidad];
    }
    // TODO: Devuelve el número de puertos espaciales que hay en la lista
    public int getOcupacion() {
        int contador = 0;
        for (int i = 0; i<lista.length ; i++){
            if (lista[i]!=null){
                contador++;
            }
        }
        return contador;
    }
    // TODO: ¿Está llena la lista?
    public boolean estaLlena() {
        return (getOcupacion() == lista.length);
    }
	// TODO: Devuelve un puerto espacial dado un indice
    public PuertoEspacial getPuertoEspacial(int i) {
        return lista[i];
    }

    /**
     * TODO: insertamos un Puerto espacial nuevo en la lista
     * @param puertoEspacial
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarPuertoEspacial(PuertoEspacial puertoEspacial) {
        boolean aniadida = false;
        if (!estaLlena()){
            lista[getOcupacion()] = puertoEspacial;
            aniadida = true;
        }
        return aniadida;
    }

    /**
     * TODO: Buscamos un Puerto espacial a partir del codigo pasado como parámetro
     * @param codigo
     * @return Puerto espacial que encontramos o null si no existe
     */
    public PuertoEspacial buscarPuertoEspacial(String codigo) {
        PuertoEspacial puertoEspacial = null;
        for (int i = 0; i< getOcupacion(); i++){
            if (lista[i].getCodigo().equals(codigo)){
                puertoEspacial = getPuertoEspacial(i);
            }
        }
        return puertoEspacial;
    }

    /**
     * TODO: Permite seleccionar un puerto espacial existente a partir de su código, usando el mensaje pasado como
     *  argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente el código hasta que se introduzca uno correcto
     * @param teclado
     * @param mensaje
     * @return
     */
    public PuertoEspacial seleccionarPuertoEspacial(Scanner teclado, String mensaje) {
        String codigo= Utilidades.leerCadena(teclado, mensaje);
        while(buscarPuertoEspacial(codigo)==null){
            System.out.println("Codigo de puerto no encontrado");
            codigo= Utilidades.leerCadena(teclado, mensaje);
        }
        return buscarPuertoEspacial(codigo);
    }

    /**
     * TODO: Genera un fichero CSV con la lista de puertos espaciales, SOBREESCRIBIENDOLO
     * @param nombre
     * @return
     */
    public boolean escribirPuertosEspacialesCsv(String nombre) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(nombre);
            for (int i = 0; i< getOcupacion(); i++){
                pw.println(lista[i].getNombre() + ";" + lista[i].getCodigo() + ";" + lista[i].getRadio() + ";" + lista[i].getAzimut() + ";" + lista[i].getPolar() + ";" + lista[i].getMuelles());
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error de escritura de fichero " + nombre);
            return false;
        } finally {
            pw.close();
        }
    }


    /**
     * TODO: Genera una lista de PuertosEspaciales a partir del fichero CSV, usando el argumento como capacidad máxima
     *  de la lista
     * @param fichero
     * @param capacidad
     * @return
     */
    public static ListaPuertosEspaciales leerPuertosEspacialesCsv(String fichero, int capacidad) {
        ListaPuertosEspaciales listaPuertosEspaciales = new ListaPuertosEspaciales(capacidad);
        Scanner sc;
        try {
            sc = new Scanner(new FileReader(fichero));
            String linea;
            while(sc.hasNext() && (linea= sc.nextLine())!=null && !listaPuertosEspaciales.estaLlena()){
                String[] datos = linea.split(";");
                double radio = Double.parseDouble(datos[2]);
                double azimut = Double.parseDouble(datos[3]);
                double polar = Double.parseDouble(datos[4]);
                int numMuelles = Integer.parseInt(datos[5]);
                PuertoEspacial puertoEspacial = new PuertoEspacial(datos[0], datos[1], radio, azimut, polar, numMuelles);
                listaPuertosEspaciales.insertarPuertoEspacial(puertoEspacial);
            }
        } catch (Exception e) {
            System.out.println("Error de lectura de fichero " + fichero);
        } finally {
            System.out.println("Lista de Puertos creada.");
        }
        return listaPuertosEspaciales;
    }
}
