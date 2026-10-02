class Casting {
    public static void main(String[] args) {
        double pi = 3.9;
        int entero = (int) pi; // 3, trunca (no redondea)
        System.out.println("entero: " + entero);

        String texto = "25";
        int numero = Integer.parseInt(texto);
        System.out.println("numero: " + numero);

        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("Conversión fallida: " + e.getMessage());
        }

        String textoDesdeNumero = String.valueOf(numero);
        System.out.println("textoDesdeNumero: " + textoDesdeNumero);
    }
}
