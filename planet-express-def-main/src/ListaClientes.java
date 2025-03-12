import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Adrián Largo Monteagudo (bu0257)
 * @author Jorge López Sosa
 * @version     1.0
 */
public class ListaClientes {
    private Cliente[] clientes;

    /**
     * TODO: Constructor de la clase para inicializar la lista a una capacidad determinada
     *
     * @param capacidad
     */
    public ListaClientes(int capacidad) {
        clientes = new Cliente[capacidad];
    }
    // TODO: Devuelve el número de clientes que hay en la lista de clientes
    public int getOcupacion() {
        int contador = 0;
        for (int i = 0; i<clientes.length ; i++){
            if (clientes[i]!=null){
                contador++;
            }
        }
        return contador;
    }
    // TODO: ¿Está llena la lista de clientes?
    public boolean estaLlena() {
        return (getOcupacion() == clientes.length);
    }
    // TODO: Devuelve el cliente dada el indice
    public Cliente getCliente(int i) {
        return clientes[i];
    }
    // TODO: Inserta el cliente en la lista de clientes
    public boolean insertarCliente(Cliente cliente) {
        boolean insertado = false;
        if (!estaLlena()){
            clientes[getOcupacion()] = cliente;
            insertado = true;
        }
        return insertado;
    }
    // TODO: Devuelve el cliente que coincida con el email, o null en caso de no encontrarlo
    public Cliente buscarClienteEmail(String email) {
        Cliente cliente = null;
        for (int i= 0; i< getOcupacion(); i++){
            if (clientes[i].getEmail().equals(email)){
                cliente = clientes[i];
            }
        }
        return cliente;
    }

    /**
     * TODO: Método para seleccionar un Cliente existente a partir de su email, usando el mensaje pasado como argumento
     *  para la solicitud y, siguiendo el orden y los textos mostrados en el enunciado.
     *  La función debe solicitar repetidamente hasta que se introduzca un email correcto
     * @param teclado
     * @param mensaje
     * @return
     */
    public Cliente seleccionarCliente(Scanner teclado, String mensaje) {
        String email = Utilidades.leerCadena(teclado, mensaje);
        while (buscarClienteEmail(email) == null){
            System.out.println("Cliente no encontrado");
            email = Utilidades.leerCadena(teclado, mensaje);
        }
        return buscarClienteEmail(email);
    }

    /**
     * TODO: Método para guardar la lista de clientes en un fichero .csv, sobreescribiendo la información del mismo
     *  fichero
     * @param fichero
     * @return
     */
    public boolean escribirClientesCsv(String fichero) {
        PrintWriter salida = null;
        try {
            salida = new PrintWriter(fichero);
            for (int i =0; i<getOcupacion(); i++){
                salida.print(clientes[i].getNombre());
                salida.print(";");
                salida.print(clientes[i].getApellidos());
                salida.print(";");
                salida.println(clientes[i].getEmail());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Fichero "+fichero+" no encontrado.");
            return false;
        } finally {
            salida.close();
        }
        return true;
    }

    /**
     * TODO: Genera una lista de Clientes a partir del fichero CSV, usando los límites especificados como argumentos
     *  para la capacidad de la lista y el número de billetes máximo por pasajero
     * @param fichero
     * @param capacidad
     * @param maxEnviosPorCliente
     * @return lista de clientes
     */
    public static ListaClientes leerClientesCsv(String fichero, int capacidad, int maxEnviosPorCliente) {
        ListaClientes listaClientes = new ListaClientes(capacidad);
        BufferedReader bufferedReader=null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fichero));
            String leido;
            while ((leido= bufferedReader.readLine())!=null && !listaClientes.estaLlena()){
                String[] leidos = leido.split(";");
                Cliente cliente = new Cliente(leidos[0], leidos[1], leidos[2], maxEnviosPorCliente);
                listaClientes.insertarCliente(cliente);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichero "+fichero+" no encontrado.");
        }catch (IOException ge){
            System.out.println("Error de lectura de fichero " + fichero);
        } finally {
            System.out.println("Lista de Clientes creada.");
        }
        return listaClientes;
    }
}
