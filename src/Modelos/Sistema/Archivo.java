package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Sistema.JsonUtiles;

import java.io.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.util.HashSet;
import java.util.Iterator;


public class Archivo {
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

    public void EscenariosAJson(HashSet<EscenarioMonstruo> listaEscenariosItem) {


        JSONObject object = new JSONObject();
        try {
            Iterator<EscenarioMonstruo> iterator = listaEscenariosItem.iterator();
            while (iterator.hasNext()) {
                EscenarioMonstruo escenarioMonstruo = iterator.next();
                object.put("nombre", escenarioMonstruo.getNombre());
                object.put("nivel", escenarioMonstruo.getNivel());
                object.put("descripcion", escenarioMonstruo.getDescripcion());
                JSONArray monstruosJSONArray = new JSONArray(); // Create a JSONArray for monsters

                // Loop through Monstruos and add their data to the JSONArray
                for (Monstruo monstruo : escenarioMonstruo.getListaMonstruos()) {
                    JSONObject monstruoJSONObject = new JSONObject();
                    monstruoJSONObject.put("nombre", monstruo.getNombre()); // Add monster properties
                    monstruoJSONObject.put("ataque", monstruo.getDanio());
                    monstruoJSONObject.put("defensa", monstruo.getVelocidad());

                    monstruosJSONArray.put(monstruoJSONObject);
                }
                object.put("monstruos", monstruosJSONArray);

            }
            JsonUtiles.grabar(object,NombreArchivos.EscenariosM.getNombre());



        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public HashSet<EscenarioMonstruo> jsonAEscenario() throws JSONException {

        try {
            HashSet<EscenarioMonstruo> listaEscenarios = new HashSet<>();
            JSONObject object = new JSONObject(JsonUtiles.leer(NombreArchivos.EscenariosM.getNombre()));
            String nombre = object.getString("nombre");
            int nivel = object.getInt("nivel");
            String descripcion = object.getString("descripcion");

            ArrayList<Monstruo> listaMonstruos = new ArrayList<>();
            JSONArray lista = object.getJSONArray("monstruo");


            for (int i = 0; i < lista.length(); i++) {
                JSONObject monstruoJson = lista.getJSONObject(i);
                String nombreMonstruo = monstruoJson.getString("nombre");
                int salud = monstruoJson.getInt("salud");
                int danio = monstruoJson.getInt("danio");
                int velocidad = monstruoJson.getInt("velocidad");
                int armadura = monstruoJson.getInt("armadura");
                Monstruo monstruo = new Monstruo(nombreMonstruo, salud, danio, velocidad, armadura);
                listaMonstruos.add(i, monstruo);
            }
            EscenarioMonstruo escenario = new EscenarioMonstruo(nombre, nivel, descripcion, listaMonstruos);
            listaEscenarios.add(escenario);
            return listaEscenarios;
        }
        finally {

        }



    }

}
