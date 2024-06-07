import Modelos.Sistema.Ejecucion;
import org.json.JSONException;


public class Main {

    public static void main(String[] args) throws JSONException {
        Ejecucion.figuras();
        Ejecucion.esperar(1);
        Ejecucion.pantallaDeCarga();
        Ejecucion.esperar(2);

        Ejecucion.ejecucion();




    }
}


