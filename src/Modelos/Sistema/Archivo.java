package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.Pocion;
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
            System.out.println("El archivo no existe");
            System.out.println("error");

        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("error");

        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException ex) {
                System.out.println("error");

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
                System.out.println("error");
            }
        }
    }

    public void EscenariosAJson(HashSet<EscenarioMonstruo> listaEscenarioMounstruos) {

        JSONObject object = new JSONObject();
        try {
            Iterator<EscenarioMonstruo> iterator = listaEscenarioMounstruos.iterator();
            while (iterator.hasNext()) {
                EscenarioMonstruo escenarioMonstruo = iterator.next();
                object.put("nombre", escenarioMonstruo.getNombre());
                object.put("nivel", escenarioMonstruo.getNivel());
                object.put("descripcion", escenarioMonstruo.getDescripcion());
                JSONArray monstruosJSONArray = new JSONArray();

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

    public HashSet<EscenarioMonstruo> jsonAEscenario()  {

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
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Fin");
        }
    }

    public void escenarioItemAJson (HashSet<EscenarioItem> listaEscenarioItems)
    {
        JSONObject object = new JSONObject();
        try
        {
            Iterator<EscenarioItem> iterator = listaEscenarioItems.iterator();
            while (iterator.hasNext())
            {
                EscenarioItem escenarioItem = iterator.next();
                object.put("nombre", escenarioItem.getNombre());
                object.put("nivel", escenarioItem.getNivel());
                object.put("descripcion", escenarioItem.getDescripcion());
                JSONArray itemsJSONArray = new JSONArray();

                for(Item item : escenarioItem.getListaItems())
                {
                    JSONObject itemJSONObject = new JSONObject();
                    itemJSONObject.put("nombre", item.getNombre());
                    itemJSONObject.put("descripcion", item.getDescripcion());

                    if(item instanceof Arma)
                    {
                        itemJSONObject.put("danio", ((Arma) item).getDanio());
                    }else if(item instanceof Armadura)
                    {
                        itemJSONObject.put("defensa", ((Armadura) item).getDefensa());
                        itemJSONObject.put("velocidad", ((Armadura) item).getVelocidad());
                    }else if(item instanceof Pocion)
                    {
                        itemJSONObject.put("salud", ((Pocion) item).getVelocidad());
                        itemJSONObject.put("velocidad", ((Pocion) item).getVelocidad());
                    }
                    itemsJSONArray.put(itemJSONObject);
                }
                object.put("items", itemsJSONArray);
            }

            JsonUtiles.grabar(object, NombreArchivos.EscenrariosI.getNombre());

        }catch(JSONException e)
        {
            throw new RuntimeException(e);
        }
    }

}
