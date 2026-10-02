class OperadoresLogicos {
    public static void main(String[] args) {
        int edad = 20;
        boolean tieneCredencial = true;

        boolean puedeEntrar = (edad >= 18) && (tieneCredencial == true);
        boolean esMenorOInvalido = (edad < 18) || (!tieneCredencial);

        System.out.println("puedeEntrar: " + puedeEntrar);
        System.out.println("esMenorOInvalido: " + esMenorOInvalido);
    }
}
