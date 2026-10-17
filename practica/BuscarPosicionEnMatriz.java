public class BuscarPosicionEnMatriz {
    public static void main(String[] args) {
        int contador = 1;
        int posicion = 13;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                    System.out.print( " ["+ contador+ "]");
                contador++;
                
            }
            System.out.println("");
        }
    }
}
