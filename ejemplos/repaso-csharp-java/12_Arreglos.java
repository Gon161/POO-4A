import java.util.Scanner;

class Arreglos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresa 3 números, uno por línea:");
        int[] numeros = new int[3];
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = scanner.nextInt();
        }

        System.out.println("Longitud: " + numeros.length);

        int suma = 0;
        for (int n : numeros) {
            suma += n;
        }
        System.out.println("Suma de los elementos ingresados: " + suma);

        // "Matriz" de tamaño fijo (en realidad es un arreglo de arreglos)
        int[][] matriz = {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 7, 8, 9 }
        };
        System.out.println("matriz[1][2] = " + matriz[1][2]);
        System.out.println("filas: " + matriz.length);
        System.out.println("columnas (fila 0): " + matriz[0].length);

        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length; col++) {
                System.out.print(matriz[fila][col] + " ");
            }
            System.out.println();
        }

        // Filas de distinto tamaño (siempre posible, es la naturaleza de int[][])
        int[][] jagged = new int[3][];
        jagged[0] = new int[] { 1 };
        jagged[1] = new int[] { 1, 2, 3 };
        jagged[2] = new int[] { 1, 2 };

        for (int fila = 0; fila < jagged.length; fila++) {
            for (int col = 0; col < jagged[fila].length; col++) {
                System.out.print(jagged[fila][col] + " ");
            }
            System.out.println();
        }
    }
}
