class Metodos {
    static int sumar(int a, int b) {
        return a + b;
    }

    // Java no tiene parámetros por defecto: se simula con sobrecarga
    static int sumar(int a) {
        return sumar(a, 0);
    }

    static void saludar(String nombre) {
        System.out.println("Hola, " + nombre);
    }

    public static void main(String[] args) {
        System.out.println(sumar(3, 4));
        System.out.println(sumar(5));
        saludar("Nerial");
    }
}
