package Modelos.Sistema;

import java.io.IOException;

public class LimpiarConsola {
    public static void limpiarConsola() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}
