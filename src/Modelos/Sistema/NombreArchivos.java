package Modelos.Sistema;

public enum NombreArchivos {
    Items("item.dat"),
    Partidas("Partidas.dat"),
    Escenarios("Escenarios.dat"),
    Monstruos("Monstruos.dat");
    private String nombre;

    NombreArchivos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
