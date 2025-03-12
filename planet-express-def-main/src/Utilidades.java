import java.util.Scanner;

/**
 * Description of the class
 *
 * @author Adrián Largo Monteagudo (bu0257)
 * @author Jorge López Sosa
 * @version     1.0
 */
public class Utilidades {
    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return int numero
     */

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        System.out.print(mensaje);
        int numero = teclado.nextInt();
        while (numero > maximo || numero < minimo) {
            System.out.println("Número incorrecto");
            System.out.print(mensaje);
            numero = teclado.nextInt();
        }
        return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return long numero
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        System.out.print(mensaje);
        long numero = teclado.nextLong();
        while (numero > maximo || numero < minimo) {
            System.out.println("Número Incorrecto");
            System.out.print(mensaje);
            numero = teclado.nextLong();
        }
        return numero;
    }

    /**
     * TODO: Solicita un número repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return double numero
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        System.out.print(mensaje);
        double numero = teclado.nextLong();
        while (numero > maximo || numero < minimo) {
            System.out.println("Número incorrecto");
            System.out.print(mensaje);
            numero = teclado.nextDouble();

        }
        return numero;
    }

    /**
     * TODO: TODO: Solicita una letra repetidamente hasta que se introduzca uno correcto (dentro de los límites)
     *
     * @param teclado
     * @param mensaje
     * @param minimo
     * @param maximo
     * @return char letra
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        System.out.print(mensaje);
        char letra = teclado.next().charAt(0);
        while (letra > minimo || letra < maximo) {
            System.out.println("Carácter incorrecto");
            System.out.print(mensaje);
            letra = teclado.next().charAt(0);
        }
        return letra;
    }


    /**
     * TODO: Solicita una fecha repetidamente hasta que se introduzca una correcta
     *
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        boolean fecha = false;
        int dia, mes, anio;
        do {
            System.out.println(mensaje);
            System.out.print("Día: ");
            dia = teclado.nextInt();
            System.out.print("Mes: ");
            mes = teclado.nextInt();
            System.out.print("Año: ");
            anio = teclado.nextInt();
            if (anio >= 2023) {
                if (mes >= 1 && mes <= 12) {
                    if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                        if (dia >= 1 && dia <= 31) {
                            fecha = true;
                        }
                    } else if (mes == 2) {
                        if (dia >= 1 && dia <= 28) {
                            fecha = true;
                        }
                    } else {
                        if (dia >= 1 && dia <= 30) {
                            fecha = true;
                        }
                    }
                }
            }
        }while (!fecha) ;
        return new Fecha(dia, mes, anio);
    }



    /**
     * TODO: Solicita una fecha y hora repetidamente hasta que se introduzcan unas correctas
     * @param teclado
     * @param mensaje
     * @return Fecha
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        int dia;
        int mes;
        int anio;
        int hora;
        int minuto;
        int segundo;
        boolean fecha = false;
        boolean hour = false;
        do {
            System.out.println(mensaje);
            System.out.print("Día: ");
            dia = teclado.nextInt();
            System.out.print("Mes: ");
            mes = teclado.nextInt();
            System.out.print("Año: ");
            anio = teclado.nextInt();
            System.out.print("Hora: ");
            hora = teclado.nextInt();
            System.out.print("Minuto: ");
            minuto = teclado.nextInt();
            System.out.print("Segundo: ");
            segundo = teclado.nextInt();
            if (anio >= 2023) {
                if (mes >= 1 && mes <= 12) {
                    if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                        if (dia >= 1 && dia <= 31) {
                            fecha = true;
                        }
                    } else if (mes == 2) {
                        if (dia >= 1 && dia <= 28) {
                            fecha = true;
                        }
                    } else {
                        if (dia >= 1 && dia <= 30) {
                            fecha = true;
                        }
                    }
                }
            }
            if (hora>=00 && hora<=24){
                if(minuto>=00 && minuto<=59){
                    if (segundo>=00 && segundo<=59){
                        hour = true;
                    }
                }
            }
        }while (!fecha || !hour) ;
        return new Fecha(dia, mes, anio, hora, minuto, segundo);
    }

    /**
     * TODO: Imprime por pantalla el String pasado por parámetro
     * @param teclado
     * @param s
     * @return
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.print(s);
        return teclado.next();
    }
}



