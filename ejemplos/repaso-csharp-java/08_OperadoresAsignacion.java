class OperadoresAsignacion {
    public static void main(String[] args) {
        int contador = 0;
        contador++;
        contador += 5;
        contador -= 2;
        contador *= 3;
        System.out.println("contador: " + contador);

        String entrada = null;
        String nombre = (entrada != null) ? entrada : "Invitado";
        System.out.println("nombre: " + nombre);
    }
}
