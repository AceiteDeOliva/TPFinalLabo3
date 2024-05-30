package Modelos.Sistema;
import Modelos.Escenarios.Escenario;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Items.Item;
import java.io.*;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.Iterator;



public class  Archivo   {
    public Archivo() {
    }

    //Pasar datos de archivos a listasde items
    public ArrayList<Partida> leerArchivoPartidas(String archivo) {
        ObjectInputStream objectInputStream = null;
        ArrayList<Partida> listaPartidas = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(archivo);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                Object objeto = objectInputStream.readObject();
                listaPartidas.add((Partida) objeto);
            }
        } catch (EOFException ex) {
            System.out.println("FIN");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("El archivo no existe");
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return listaPartidas;
    }

    public void grabarArchivoPartidas(ArrayList<Partida> partidas, String archivo) {
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Partida partida : partidas) {
                objectOutputStream.writeObject(partida);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void jsonEscenarios(HashSet<EscenarioMonstruo> listaEscenariosItem){


        JSONObject object = new JSONObject();
        JSONArray JsonArray = new JSONArray();
        try{
            Iterator<String> iterator = HashSet.iterator();
            while (iterator.hasNext()) {
                String escenarioMonstruo = iterator.next();
                object.put("nombre",escenarioMonstruo.getNombre);



            }


        }

    }

}
