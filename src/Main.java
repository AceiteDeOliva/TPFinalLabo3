
import Modelos.Entidades.Personaje;
import Modelos.Exceptions.ExcepcionSwitch;
import Modelos.Sistema.Ejecucion;


public class Main {

    public static void main(String[] args) throws ExcepcionSwitch {
        Ejecucion.figuras();
        Ejecucion.esperar(1);
        Ejecucion.pantallaDeCarga();
        Ejecucion.esperar(2);

        Ejecucion.ejecucion();








    }
}


