class OperadoresAritmeticos {
    public static void main(String[] args) {
        int a = 7, b = 2;

        System.out.println("Suma: " + (a + b));
        System.out.println("Resta: " + (a - b));
        System.out.println("Multiplicación: " + (a * b));
        System.out.println("División entera: " + (a / b));         // 3
        System.out.println("División exacta: " + (a / (double) b)); // 3.5
        System.out.println("Módulo: " + (a % b));
        System.out.println("Potencia: " + Math.pow(a, b));
    }
}
