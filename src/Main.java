
import Modelos.Entidades.Personaje;
import Modelos.Exceptions.ExcepcionSwitch;
import Modelos.Sistema.Ejecucion;
import Modelos.Sistema.Partida;
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


