import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal de Planet Express App, la práctica de Taller de Programación
 *
 * @author Adrián Largo Monteagudo (bu0257)
 * @author Jorge López Sosa
 * @version     1.0
 */
public class PlanetExpress {
    private final int maxPortes;
    private final int maxNaves;
    private final int maxClientes;
    private final int maxEnviosPorCliente;
    private ListaPuertosEspaciales listaPuertosEspaciales;
    private final int maxPuertosEspaciales;
    private ListaNaves listaNaves;
    private ListaClientes listaClientes;
    private ListaPortes listaPortes;

    /**
     * TODO: Rellene el constructor de la clase
     *
     * @param maxPuertosEspaciales Máximo número de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * @param maxNaves Máximo número de naves que tendrá la lista de naves de PlanetExpress App.
     * @param maxPortes Máximo número de portes que tendrá la lista de portes de PlanetExpress App.
     * @param maxClientes Máximo número de clientes que tendrá la lista de clientes de PlanetExpress App.
     * @param maxEnviosPorCliente Máximo número de envíos por cliente.
     */
    public PlanetExpress(int maxPuertosEspaciales, int maxNaves, int maxPortes, int maxClientes, int maxEnviosPorCliente) {
        this.maxPuertosEspaciales = maxPuertosEspaciales;
        this.maxNaves = maxNaves;
        this.maxPortes = maxPortes;
        this.maxClientes = maxClientes;
        this.maxEnviosPorCliente = maxEnviosPorCliente;
    }


    /**
     * TODO: Metodo para leer los datos de los ficheros específicados en el enunciado y los agrega a
     *  la información de PlanetExpress (listaPuertosEspaciales, listaNaves, listaPortes, listaClientes)
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void cargarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        listaPuertosEspaciales = ListaPuertosEspaciales.leerPuertosEspacialesCsv(ficheroPuertos, maxPuertosEspaciales);
        listaNaves = ListaNaves.leerNavesCsv(ficheroNaves, maxNaves);
        listaPortes = ListaPortes.leerPortesCsv(ficheroPortes, maxPortes, listaPuertosEspaciales, listaNaves);
        listaClientes = ListaClientes.leerClientesCsv(ficheroClientes, maxClientes, maxEnviosPorCliente);
        ListaEnvios.leerEnviosCsv(ficheroEnvios, listaPortes, listaClientes);
    }


    /**
     * TODO: Metodo para almacenar los datos de PlanetExpress en los ficheros .csv especificados
     *  en el enunciado de la práctica
     * @param ficheroPuertos
     * @param ficheroNaves
     * @param ficheroPortes
     * @param ficheroClientes
     * @param ficheroEnvios
     */
    public void guardarDatos(String ficheroPuertos, String ficheroNaves, String ficheroPortes, String ficheroClientes, String ficheroEnvios) {
        listaPuertosEspaciales.escribirPuertosEspacialesCsv(ficheroPuertos);
        listaNaves.escribirNavesCsv(ficheroNaves);
        listaPortes.escribirPortesCsv(ficheroPortes);
        listaClientes.escribirClientesCsv(ficheroClientes);
        for (int i=0; i< listaClientes.getOcupacion(); i++){
            listaClientes.getCliente(i).getListaEnvios().aniadirEnviosCsv(ficheroEnvios);
        }
    }
    public boolean maxPortesAlcanzado() {
        return listaPortes.estaLlena();
    }
    public boolean insertarPorte (Porte porte) {
        return listaPortes.insertarPorte(porte);
    }
    public boolean maxClientesAlcanzado() {
        return listaClientes.estaLlena();
    }
    public boolean insertarCliente (Cliente cliente) {
        return listaClientes.insertarCliente(cliente);
    }

    /**
     * TODO: Metodo para buscar los portes especificados tal y como aparece en el enunciado de la práctica,
     *  Devuelve una lista de los portes entre dos puertos espaciales con una fecha de salida solicitados por teclado
     *  al usuario en el orden y con los textos establecidos (tomar como referencia los ejemplos de ejecución en el
     *  enunciado de la prática)
     * @param teclado
     * @return
     */
    public ListaPortes buscarPorte(Scanner teclado) {
        String codigoOrigen = Utilidades.leerCadena(teclado, "Ingrese código de puerto Origen: ");
        String codigoDestino = Utilidades.leerCadena(teclado , "Ingrese código de puerto Destino: ");
        Fecha fecha = null;
        do{
            fecha = Utilidades.leerFecha(teclado, "Fecha de Salida: ");
        }while (!Fecha.comprobarFecha(fecha.getDia(), fecha.getMes(), fecha.getAnio()));
        return listaPortes.buscarPortes(codigoOrigen, codigoDestino, fecha);
    }


    /**
     * TODO: Metodo para contratar un envio tal y como se indica en el enunciado de la práctica. Se contrata un envio para un porte
     *  especificado, pidiendo por teclado los datos necesarios al usuario en el orden y con los textos (tomar como referencia los
     *  ejemplos de ejecución en el enunciado de la prática)
     * @param teclado
     * @param rand
     * @param porte
     */
    public void contratarEnvio(Scanner teclado, Random rand, Porte porte) {
        if (porte != null) {
            String opcion = Utilidades.leerCadena(teclado, "¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)? ");
            while (!opcion.equals("n") && !opcion.equals("e")){
                System.out.println("El valor de entrada debe ser 'n' o 'e'");
                opcion = Utilidades.leerCadena(teclado, "¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)? ");
            }
            if (opcion.equals("n")){
                Cliente cliente = Cliente.altaCliente(teclado, listaClientes, maxEnviosPorCliente);
                insertarCliente(cliente);
                Envio.altaEnvio(teclado, rand, porte, cliente);
            } else {
                String email = Utilidades.leerCadena(teclado, "Email del cliente: ");
                while (listaClientes.buscarClienteEmail(email)==null){
                    System.out.println("Email no encontrado.");
                    email = Utilidades.leerCadena(teclado, "Email del cliente: ");
                }
                Envio.altaEnvio(teclado, rand, porte, listaClientes.buscarClienteEmail(email));

            }
        }
    }


    /**
     * TODO Metodo statico con la interfaz del menú de entrada a la App.
     * Tiene que coincidir con las trazas de ejecución que se muestran en el enunciado
     * @param teclado
     * @return opción seleccionada
     */
    public static int menu(Scanner teclado) {
        System.out.println("1. Alta de Porte");
        System.out.println("2. Alta de Cliente");
        System.out.println("3. Buscar Porte");
        System.out.println("4. Mostrar envíos de un cliente");
        System.out.println("5. Generar lista de envíos");
        System.out.println("0. Salir");
        return Utilidades.leerNumero(teclado, "Seleccione una opción: ", 0, 5);
    }

    /**
     * TODO: Método Main que carga los datos de los ficheros CSV pasados por argumento (consola) en PlanetExpress,
     *  llama iterativamente al menú y realiza la opción especificada hasta que se indique la opción Salir. Al finalizar
     *  guarda los datos de PlanetExpress en los mismos ficheros CSV.
     * @param args argumentos de la línea de comandos, recibe **10 argumentos** estrictamente en el siguiente orden:
     * 1. Número máximo de puertos espaciales que tendrá la lista de puertos espaciales de PlanetExpress App.
     * 2. Número máximo de naves que tendrá la lista de naves de PlanetExpress App.
     * 3. Número máximo de portes que tendrá la lita de portes de PlanetExpress App.
     * 4. Número máximo de clientes que tendrá la lista de clientes de PlanetExpress App.
     * 5. Número máximo de envíos por pasajero.
     * 6. Nombre del fichero CSV que contiene la lista de puertos espaciales de PlanetExpress App (tanto para lectura como escritura).
     * 7. Nombre del fichero CSV que contiene la lista de naves de PlanetExpress App (tanto para lectura como escritura).
     * 8. Nombre del fichero CSV que contiene la lista de portes de PlanetExpress App (tanto para lectura como escritura).
     * 9. Nombre del fichero CSV que contiene la lista de clientes de PlanetExpress App (tanto para lectura como escritura).
     * 10. Nombre del fichero CSV que contiene los envíos adquiridos en PlanetExpress App (tanto para lectura como escritura).
     * En el caso de que no se reciban exactamente estos argumentos, el programa mostrará el siguiente mensaje
     * y concluirá la ejecución del mismo: `Número de argumentos incorrecto`.
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        if (args.length != 10) {
            System.out.println("Número de argumentos incorrecto");
            return;
        }
        int opcion;
        int maxPuertos = Integer.parseInt(args[0]);
        int maxNaves = Integer.parseInt(args[1]);
        int maxPortes = Integer.parseInt(args[2]);
        int maxClientes = Integer.parseInt(args[3]);
        int maxEnvios = Integer.parseInt(args[4]);
        PlanetExpress planetExpress = new PlanetExpress(maxPuertos, maxNaves, maxPortes, maxClientes, maxEnvios);
        planetExpress.cargarDatos(args[5], args[6], args[7], args[8], args[9]);
        do {
            Random rand = new Random();
            opcion = menu(teclado);
            switch (opcion) {
                case 1:     // TODO: Alta de Porte
                    if (!planetExpress.maxPortesAlcanzado()){
                        planetExpress.insertarPorte(Porte.altaPorte(teclado, rand, planetExpress.listaPuertosEspaciales, planetExpress.listaNaves, planetExpress.listaPortes));
                    } else System.out.println("Numero maximo de portes alcanzado");
                    break;
                case 2:     // TODO: Alta de Cliente
                    if (!planetExpress.maxClientesAlcanzado()){
                        planetExpress.insertarCliente(Cliente.altaCliente(teclado, planetExpress.listaClientes, maxEnvios));
                    } else System.out.println("Numero maximo de clientes alcanzado");
                    break;
                case 3:     // TODO: Buscar Porte
                    ListaPortes listaPortes1 = planetExpress.buscarPorte(teclado);
                    if (listaPortes1!=null) {
                        listaPortes1.listarPortes();
                        planetExpress.contratarEnvio(teclado, rand, listaPortes1.seleccionarPorte(teclado, "Seleccione un porte: ", "CANCELAR"));
                    } else System.out.println("No se ha encontrado ningún porte");
                    break;
                case 4:     // TODO: Listado de envíos de un cliente
                    Cliente cliente = planetExpress.listaClientes.seleccionarCliente(teclado, "Email del cliente: ");
                    if (cliente.getListaEnvios().getOcupacion()!=0){
                        cliente.listarEnvios();
                        Envio envio = cliente.seleccionarEnvio(teclado, "Seleccione un envío: ");
                        String opcion2 = Utilidades.leerCadena(teclado, "¿Cancelar envío (c), o generar factura (f)? ");
                        while (!opcion2.equals("c") && !opcion2.equals("f")){
                            System.out.println("El valor de entrada debe ser 'c' o 'f'");
                            opcion2 = Utilidades.leerCadena(teclado, "¿Cancelar envío (c), o generar factura (f)? ");
                        }
                        if (opcion2.equals("c")){
                            envio.cancelar();
                        } else {
                            if(envio.generarFactura(Utilidades.leerCadena(teclado, "Nombre del fichero: "))){
                                System.out.println("Factura generada correctamente");
                            } else System.out.println("Error en la factura");
                        }
                    } else System.out.println("No hay envíos disponibles");
                    break;
                case 5:     // TODO: Lista de envíos de un porte
                    planetExpress.listaPortes.listarPortes();
                    Porte porte = planetExpress.listaPortes.buscarPorte(Utilidades.leerCadena(teclado, "Seleccione un porte: "));
                    while (porte ==null){
                        System.out.println("Porte no encontrado.");
                        porte = planetExpress.listaPortes.buscarPorte(Utilidades.leerCadena(teclado, "Seleccione un porte: "));
                    }
                    if (porte.generarListaEnvios(Utilidades.leerCadena(teclado, "Nombre del fichero: "))){
                        System.out.println("Fichero creado correctamente");
                    }  else System.out.println("Error en la factura");
                    break;
            }
        } while (opcion != 0);
        planetExpress.guardarDatos(args[5], args[6], args[7], args[8], args[9]);
    }
}
