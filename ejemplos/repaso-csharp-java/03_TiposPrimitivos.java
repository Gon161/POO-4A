class TiposPrimitivos {
    public static void main(String[] args) {
        int edad = 20;
        double promedio = 8.75;
        boolean esActivo = true;
        char letra = 'A';
        String nombre = "Nerial";
        byte pequenio = 127;
        short corto = 32000;
        long grande = 10000000000L;
        float flotante = 3.14f;
        // No hay 'decimal'; para precisión exacta se usa BigDecimal

        System.out.println("int: " + edad);
        System.out.println("double: " + promedio);
        System.out.println("boolean: " + esActivo);
        System.out.println("char: " + letra);
        System.out.println("String: " + nombre);
        System.out.println("byte: " + pequenio);
        System.out.println("short: " + corto);
        System.out.println("long: " + grande);
        System.out.println("float: " + flotante);
    }
}
