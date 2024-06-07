package Modelos.Sistema;

public enum NombreArchivos {

    Partidas("Partidas.dat"),
    EscenariosM("src/Escenarios Monstruos"),
    EscenariosI("src/Escenarios Items");

    private String nombre;

    NombreArchivos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
