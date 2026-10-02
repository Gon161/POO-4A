class Arreglos {
    public static void main(String[] args) {
        int[] numeros = { 10, 20, 30 };
        System.out.println("Longitud: " + numeros.length);

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
