import java.io.File;
import java.io.FileNotFoundException;
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
public class ListaNaves {
    private Nave[] naves;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaNaves(int capacidad) {
        naves = new Nave[capacidad];
    }
    // TODO: Devuelve el número de naves que hay en la lista
    public int getOcupacion() {
        int contador = 0;
        for (int i = 0; i<naves.length ; i++){
            if (naves[i]!=null){
                contador++;
            }
        }
        return contador;
    }

    // TODO: ¿Está llena la lista de naves?
    public boolean estaLlena() {
        return (getOcupacion()==naves.length);
    }
    // TODO: Devuelve nave dado un indice
    public Nave getNave(int posicion) {
        return naves[posicion];
    }

    /**
     * TODO: insertamos una nueva nave en la lista
     * @param nave
     * @return true en caso de que se añada correctamente, false en caso de lista llena o error
     */
    public boolean insertarNave(Nave nave) {
        boolean insertado = false;
        if (!estaLlena()){
            naves[getOcupacion()] = nave;
            insertado = true;
        }
        return insertado;

    }
    /**
     * TODO: Buscamos la nave a partir de la matricula pasada como parámetro
     * @param matricula
     * @return la nave que encontramos o null si no existe
     */
    public Nave buscarNave(String matricula) {
        Nave nave = null;
        for (int i= 0; i<getOcupacion(); i++){
            if (naves[i].getMatricula().equals(matricula)){
                nave = naves[i];
            }
        }
        return nave;

    }
    // TODO: Muestra por pantalla las naves de la lista con el formato indicado en el enunciado
    public void mostrarNaves() {
        for (int i = 0; i <getOcupacion();i++){
            System.out.println(naves[i].toString());
        }
    }



    /**
     * TODO: Permite seleccionar una nave existente a partir de su matrícula, y comprueba si dispone de un alcance
     *  mayor o igual que el pasado como argumento, usando el mensaje pasado como argumento para la solicitud y
     *  siguiendo el orden y los textos mostrados en el enunciado.
     *  La función solicita repetidamente la matrícula de la nave hasta que se introduzca una con alcance suficiente
     * @param teclado
     * @param mensaje
     * @param alcance
     * @return
     */
    public Nave seleccionarNave(Scanner teclado, String mensaje, double alcance) {
        mostrarNaves();
        String matricula = Utilidades.leerCadena(teclado, mensaje);
        while (buscarNave(matricula)== null || buscarNave(matricula).getAlcance()<alcance){
            if (buscarNave(matricula)== null) {
                System.out.println("Matricula de avión no encontrada.");
                matricula = Utilidades.leerCadena(teclado, mensaje);
            } else {
                System.out.println("Avion seleccionado con alcance insuficiente");
                matricula = Utilidades.leerCadena(teclado, mensaje);
            }
        }
        Nave nave = buscarNave(matricula);
        return nave;
    }



    /**
     * TODO: Genera un fichero CSV con la lista de Naves, SOBREESCRIBIÉNDOLO
     * @param nombre
     * @return
     */
    public boolean escribirNavesCsv(String nombre) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(nombre);
            for (int i =0; i<getOcupacion(); i++){
                salida.print(naves[i].getMarca());
                salida.print(";");
                salida.print(naves[i].getModelo());
                salida.print(";");
                salida.print(naves[i].getMatricula());
                salida.print(";");
                salida.print(naves[i].getFilas());
                salida.print(";");
                salida.print(naves[i].getColumnas());
                salida.print(";");
                salida.println(naves[i].getAlcance());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Fichero "+nombre+" no encontrado.");
            return false;
        } finally {
            salida.close();
        }
        return true;
    }



    /**
     * TODO: Genera una lista de naves a partir del fichero CSV, usando el argumento como capacidad máxima de la lista
     * @param fichero
     * @param capacidad
     * @return
     */
    public static ListaNaves leerNavesCsv(String fichero, int capacidad) {
        ListaNaves listaNaves = new ListaNaves(capacidad);
        Scanner sc;
        try {
            sc = new Scanner(new FileReader(fichero));
            String nave;
            while ((sc.hasNext() && (nave= sc.nextLine())!=null && !listaNaves.estaLlena())){
                String[] datos = nave.split(";");
                int columnas = Integer.parseInt(datos[4]);
                int filas = Integer.parseInt(datos[3]);
                double alcance = Double.parseDouble(datos[5]);
                Nave nuevaNave = new Nave(datos[0], datos[1], datos[2], columnas, filas, alcance);
                listaNaves.insertarNave(nuevaNave);
            }
        } catch (Exception e) {
            if (e.getCause() == null){
                System.out.print("");
            } else System.out.println("Error de lectura de fichero "+ fichero);
        } finally {
            System.out.println("Lista de Naves creada.");
        }
        return listaNaves;
    }
}
