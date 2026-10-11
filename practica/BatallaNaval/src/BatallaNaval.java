import java.util.Scanner;

public class BatallaNaval {
    static Scanner lectura = new Scanner(System.in);
    static void main() {
        char[][] J1Tablero = new char[5][5];
        char[][] J2Tablero = new char[5][5];
        char[][] J1TableroAux = new char[5][5];
        char[][] J2TableroAux = new char[5][5];
        boolean play = true;
        //llenar tableros de los jugadores
        for (int i = 0; i < J1Tablero.length; i++) {
            for (int j = 0; j < J1Tablero[i].length; j++) {
                J1Tablero[i][j] = '°';
                J2Tablero[i][j] = '°';
                J1TableroAux[i][j] = ' ';
                J2TableroAux[i][j] = ' ';
            }
        }
        //reinicio de partidas
        while (play) {
            System.out.println("HOLA BIENVENIDO AL JUEGO DE BATALLA NAVAL");
            System.out.println("POR FAVOR JUGADOR PRESIONA CUALQUIE TECLA PARA INICIAR LA PARTIDA");
            lectura.next();

            for (int i = 0; i < 100; i++) {
                System.out.println("");
            }
            System.out.println("POR FAVOR JUGADOR 1 INGRESA LAS 3 POSICIONES DE TUS BARCOS. A CONTINUACION LA DISTRIBUCION DEL TABLERO");
            int contador = 1;
            for (int j = 0; j < J1Tablero.length; j++) {
                for (int h = 0; h < J1Tablero[j].length; h++) {
                    System.out.printf(contador+"\t");
                    contador++;
                }
                System.out.println();
            }
            for (int j = 1; j <= 3; j++) {
                contador = 1;
                System.out.println("Por favor indique el lugar del barco "+j);
                int posicion = lectura.nextInt();
                for (int h = 0; h < J1Tablero.length; h++) {
                    for (int k = 0; k < J1Tablero[j].length; k++) {
                        if (contador == posicion){
                            J1Tablero[h][k] = 'b';
                        }
                        contador++;
                    }
                }
            }
            System.out.println("JUGADOR 1 TU TABLERO: ");
            contador = 1;
            for (int j = 0; j < J1Tablero.length; j++) {
                for (int h = 0; h < J1Tablero[j].length; h++) {
                    System.out.printf(J1Tablero[j][h]+"\t");
                    contador++;
                }
                System.out.println();
            }

            System.out.println("POR FAVOR JUGADOR 1 PRESIONA CUALQUIE TECLA Y PASE EL JUEGO AL J2");
            lectura.next();

            for (int i = 0; i < 100; i++) {
                System.out.println("");
            }
            System.out.println("POR FAVOR JUGADOR 2 INGRESA LAS 3 POSICIONES DE TUS BARCOS. A CONTINUACION LA DISTRIBUCION DEL TABLERO");
            contador = 1;
            for (int j = 0; j < J2Tablero.length; j++) {
                for (int h = 0; h < J2Tablero[j].length; h++) {
                    System.out.printf(contador+"\t");
                    contador++;
                }
                System.out.println();
            }
            for (int j = 1; j <= 3; j++) {
                contador = 1;
                System.out.println("Por favor indique el lugar del barco "+j);
                int posicion = lectura.nextInt();
                for (int h = 0; h < J2Tablero.length; h++) {
                    for (int k = 0; k < J2Tablero[j].length; k++) {
                        if (contador == posicion){
                            J2Tablero[h][k] = 'b';
                        }
                        contador++;
                    }
                }
            }
            System.out.println("JUGADOR 1 TU TABLERO: ");
            contador = 1;
            for (int j = 0; j < J2Tablero.length; j++) {
                for (int h = 0; h < J2Tablero[j].length; h++) {
                    System.out.printf(J2Tablero[j][h]+"\t");
                    contador++;
                }
                System.out.println();
            }

            play = false;
        }


    }

}
