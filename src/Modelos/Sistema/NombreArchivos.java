package Modelos.Sistema;

public enum NombreArchivos {

    Partidas("Partidas.dat"),
    EscenariosM("Escenarios Monstruos"),
    EscenrariosI("Escenarios items");

    private String nombre;

    NombreArchivos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
