import java.util.Scanner;

public class BatallaNaval {
    static Scanner lectura = new Scanner(System.in);

    public static void main(String[] args) {
        boolean play = true;

        //reinicio de partidas
        while (play) {
            char[][] J1Tablero = new char[5][5];
            char[][] J2Tablero = new char[5][5];
            char[][] J1TableroAux = new char[5][5];
            char[][] J2TableroAux = new char[5][5];

            //llenar tableros de los jugadores
            for (int i = 0; i < J1Tablero.length; i++) {
                for (int j = 0; j < J1Tablero[i].length; j++) {
                    J1Tablero[i][j] = '°';
                    J2Tablero[i][j] = '°';
                    J1TableroAux[i][j] = ' ';
                    J2TableroAux[i][j] = ' ';
                }
            }

            System.out.println("HOLA BIENVENIDO AL JUEGO DE BATALLA NAVAL");
            System.out.println("POR FAVOR JUGADOR PRESIONA ENTER PARA INICIAR LA PARTIDA");
            lectura.nextLine();

            for (int i = 0; i < 100; i++) {
                System.out.println("");
            }
            System.out.println("POR FAVOR JUGADOR 1 INGRESA LAS 3 POSICIONES DE TUS BARCOS. A CONTINUACION LA DISTRIBUCION DEL TABLERO");
            int contador = 1;
            for (int j = 0; j < J1Tablero.length; j++) {
                for (int h = 0; h < J1Tablero[j].length; h++) {
                    System.out.printf("[%2d]", contador);
                    contador++;
                }
                System.out.println();
            }
            //colocar los 3 barcos del jugador 1
            for (int j = 1; j <= 3; j++) {
                System.out.println("Por favor indique el lugar del barco " + j);
                int posicion = lectura.nextInt();
                lectura.nextLine();
                contador = 1;
                for (int h = 0; h < J1Tablero.length; h++) {
                    for (int k = 0; k < J1Tablero[h].length; k++) {
                        if (contador == posicion) {
                            J1Tablero[h][k] = 'b';
                        }
                        contador++;
                    }
                }
            }
            System.out.println("JUGADOR 1 TU TABLERO: ");
            for (int j = 0; j < J1Tablero.length; j++) {
                for (int h = 0; h < J1Tablero[j].length; h++) {
                    System.out.printf("[" + J1Tablero[j][h] + "]");
                }
                System.out.println();
            }

            System.out.println("POR FAVOR JUGADOR 1 PRESIONA ENTER Y PASE EL JUEGO AL J2");
            lectura.nextLine();

            for (int i = 0; i < 100; i++) {
                System.out.println("");
            }
            System.out.println("POR FAVOR JUGADOR 2 INGRESA LAS 3 POSICIONES DE TUS BARCOS. A CONTINUACION LA DISTRIBUCION DEL TABLERO");
            contador = 1;
            for (int j = 0; j < J2Tablero.length; j++) {
                for (int h = 0; h < J2Tablero[j].length; h++) {
                    System.out.printf("[%2d]", contador);
                    contador++;
                }
                System.out.println();
            }
            //colocar los 3 barcos del jugador 2
            for (int j = 1; j <= 3; j++) {
                System.out.println("Por favor indique el lugar del barco " + j);
                int posicion = lectura.nextInt();
                lectura.nextLine();
                contador = 1;
                for (int h = 0; h < J2Tablero.length; h++) {
                    for (int k = 0; k < J2Tablero[h].length; k++) {
                        if (contador == posicion) {
                            J2Tablero[h][k] = 'b';
                        }
                        contador++;
                    }
                }
            }
            System.out.println("JUGADOR 2 TU TABLERO: ");
            for (int j = 0; j < J2Tablero.length; j++) {
                for (int h = 0; h < J2Tablero[j].length; h++) {
                    System.out.printf("[" + J2Tablero[j][h] + "]");
                }
                System.out.println();
            }

            System.out.println("LOS BARCOS YA ESTAN COLOCADOS. COMIENZA LA BATALLA");
            System.out.println("PRESIONA ENTER PARA CONTINUAR");
            lectura.nextLine();

            //fase de batalla: los jugadores se turnan para atacar el tablero contrario
            int J1BarcosRestantes = 3;
            int J2BarcosRestantes = 3;
            int turno = 1; // 1 = ataca el jugador 1, 2 = ataca el jugador 2
            boolean hayGanador = false;

            while (!hayGanador) {
                for (int i = 0; i < 100; i++) {
                    System.out.println("");
                }

                if (turno == 1) {
                    System.out.println("TURNO DEL JUGADOR 1 - ATACANDO EL TABLERO DEL JUGADOR 2");
                    System.out.println("TU TABLERO (JUGADOR 1):");
                    for (int j = 0; j < J1Tablero.length; j++) {
                        for (int h = 0; h < J1Tablero[j].length; h++) {
                            System.out.printf("[" + J1Tablero[j][h] + "]");
                        }
                        System.out.println();
                    }
                    System.out.println("ESTO ES LO QUE HAS DESCUBIERTO DEL TABLERO ENEMIGO:");
                    for (int j = 0; j < J2TableroAux.length; j++) {
                        for (int h = 0; h < J2TableroAux[j].length; h++) {
                            System.out.printf("[" + J2TableroAux[j][h] + "]");
                        }
                        System.out.println();
                    }

                    System.out.println("Elige la posicion (1-25) donde quieres disparar:");
                    int posicion = lectura.nextInt();
                    lectura.nextLine();

                    contador = 1;
                    for (int h = 0; h < J2Tablero.length; h++) {
                        for (int k = 0; k < J2Tablero[h].length; k++) {
                            if (contador == posicion) {
                                if (J2Tablero[h][k] == 'b') {
                                    J2TableroAux[h][k] = 'X';
                                    J2Tablero[h][k] = 'X';
                                    J2BarcosRestantes--;
                                    System.out.println("¡TOCADO! Le diste a un barco del Jugador 2");
                                } else {
                                    J2TableroAux[h][k] = 'O';
                                    System.out.println("AGUA. No le diste a ningun barco");
                                }
                            }
                            contador++;
                        }
                    }

                    if (J2BarcosRestantes == 0) {
                        hayGanador = true;
                        System.out.println("EL JUGADOR 2 YA NO TIENE BARCOS. ¡EL JUGADOR 1 GANA LA PARTIDA!");
                    } else {
                        turno = 2;
                    }
                } else {
                    System.out.println("TURNO DEL JUGADOR 2 - ATACANDO EL TABLERO DEL JUGADOR 1");
                    System.out.println("TU TABLERO (JUGADOR 2):");
                    for (int j = 0; j < J2Tablero.length; j++) {
                        for (int h = 0; h < J2Tablero[j].length; h++) {
                            System.out.printf("[" + J2Tablero[j][h] + "]");
                        }
                        System.out.println();
                    }
                    System.out.println("ESTO ES LO QUE HAS DESCUBIERTO DEL TABLERO ENEMIGO:");
                    for (int j = 0; j < J1TableroAux.length; j++) {
                        for (int h = 0; h < J1TableroAux[j].length; h++) {
                            System.out.printf("[" + J1TableroAux[j][h] + "]");
                        }
                        System.out.println();
                    }

                    System.out.println("Elige la posicion (1-25) donde quieres disparar:");
                    int posicion = lectura.nextInt();
                    lectura.nextLine();

                    contador = 1;
                    for (int h = 0; h < J1Tablero.length; h++) {
                        for (int k = 0; k < J1Tablero[h].length; k++) {
                            if (contador == posicion) {
                                if (J1Tablero[h][k] == 'b') {
                                    J1TableroAux[h][k] = 'X';
                                    J1Tablero[h][k] = 'X';
                                    J1BarcosRestantes--;
                                    System.out.println("¡TOCADO! Le diste a un barco del Jugador 1");
                                } else {
                                    J1TableroAux[h][k] = 'O';
                                    System.out.println("AGUA. No le diste a ningun barco");
                                }
                            }
                            contador++;
                        }
                    }

                    if (J1BarcosRestantes == 0) {
                        hayGanador = true;
                        System.out.println("EL JUGADOR 1 YA NO TIENE BARCOS. ¡EL JUGADOR 2 GANA LA PARTIDA!");
                    } else {
                        turno = 1;
                    }
                }

                if (!hayGanador) {
                    System.out.println("PRESIONA ENTER Y PASA EL JUEGO AL SIGUIENTE JUGADOR");
                    lectura.nextLine();
                }
            }

            System.out.println();
            System.out.println("¿QUIERES JUGAR OTRA PARTIDA? (S/N)");
            String respuesta = lectura.nextLine();
            if (respuesta.equalsIgnoreCase("S")) {
                play = true;
            } else {
                play = false;
            }
        }

        System.out.println("GRACIAS POR JUGAR BATALLA NAVAL");
    }
}