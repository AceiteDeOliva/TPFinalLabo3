package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Entidades.TipoDeMonstruo;
import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.pociones.Pocion;
import Modelos.Items.pociones.EfectoCuracion;
import Modelos.Items.pociones.EfectoPocion;
import Modelos.Items.pociones.EfectoVelocidad;
import Modelos.Items.pociones.Pocion;
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


    public void EscenariosMonstruoAJson(HashSet<EscenarioMonstruo> listaEscenarioMounstruos) {

        JSONObject object = new JSONObject();
        try {

            for (EscenarioMonstruo escenarioMonstruo : listaEscenarioMounstruos) {
                object.put("nombre", escenarioMonstruo.getNombre());
                object.put("nivel", escenarioMonstruo.getNivel());
                object.put("descripcion", escenarioMonstruo.getDescripcion());
                JSONArray monstruosJSONArray = new JSONArray();

                // Recorre la lista a monstruos y agrega a json
                for (Monstruo monstruo : escenarioMonstruo.getListaMonstruos()) {
                    JSONObject monstruoJSONObject = new JSONObject();
                    monstruoJSONObject.put("nombre", monstruo.getNombre()); //agrega las propiedades de monstruo
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

    public static HashSet<EscenarioMonstruo> jsonAEscenarioMonstruo() {
        HashSet<EscenarioMonstruo> listaEscenarios = new HashSet<>();
        try {
            // Aquí asumo que el JSON completo está almacenado en una sola cadena
            String jsonCompleto = JsonUtiles.leer(NombreArchivos.EscenariosM.getNombre());
            JSONArray arrayEscenarios = new JSONArray(jsonCompleto);

            for (int i = 0; i < arrayEscenarios.length(); i++) {
                JSONObject object = arrayEscenarios.getJSONObject(i);

                String nombre = object.optString("nombre", null);
                int nivel = object.optInt("nivel", 0);
                String descripcion = object.optString("descripcion", null);

                ArrayList<Monstruo> listaMonstruos = new ArrayList<>();
                if (object.has("monstruo"))  //verifica si el JSONObject tiene la clave "monstruo"
                {
                    JSONArray lista = object.getJSONArray("monstruo"); //si hay, se obtiene un JSONArray a partir de la clave "monstruo"

                    for (int j = 0; j < lista.length(); j++) {
                        JSONObject monstruoJson = lista.getJSONObject(j);
                        String nombreMonstruo = monstruoJson.optString("nombre", null);
                        int salud = monstruoJson.optInt("salud", 0);
                        int danio = monstruoJson.optInt("danio", 0);
                        int velocidad = monstruoJson.optInt("velocidad", 0);
                        int armadura = monstruoJson.optInt("armadura", 0);
                        int especialTEspera = monstruoJson.optInt("especialTEspera", 0);
                        TipoDeMonstruo tipoDeMonstruo = TipoDeMonstruo.valueOf(monstruoJson.optString("tipoDeMonstruo", "DEFAULT"));
                        //valueOf: Convierte el String obtenido ("tipoDeMonstruo" o "DEFAULT") a la constante del enum que corresponda

                        Item botin = null;
                        if (monstruoJson.has("botin")) {
                            JSONObject botinJson = monstruoJson.getJSONObject("botin"); // busca el objeto asociado con la clave "botin"
                            String nombreItem = botinJson.optString("nombre", null); // extrae sus atributos
                            String descripcionItem = botinJson.optString("descripcion", null);

                            if (botinJson.has("danio")) {
                                int danioItem = botinJson.optInt("danio", 0);
                                botin = new Arma(nombreItem, descripcionItem, danioItem);
                            } else if (botinJson.has("defensa")) {
                                int defensaItem = botinJson.optInt("defensa", 0);
                                int velocidadItem = botinJson.optInt("velocidad", 0);
                                botin = new Armadura(nombreItem, descripcionItem, defensaItem, velocidadItem);
                            } else if (botinJson.has("efecto")) {
                                String efectoTipo = botinJson.getString("efecto");
                                EfectoPocion efecto = null;
                                if (efectoTipo.equals("EfectoVelocidad")) {
                                    int cantidadVelocidad = botinJson.optInt("cantidadVelocidad", 0);
                                    efecto = new EfectoVelocidad(cantidadVelocidad);
                                } else if (efectoTipo.equals("EfectoCuracion")) {
                                    int cantidadCuracion = botinJson.optInt("cantidadCuracion", 0);
                                    efecto = new EfectoCuracion(cantidadCuracion);
                                }
                                botin = new Pocion<>(nombreItem, descripcionItem, efecto);

                                Monstruo monstruo = new Monstruo(nombreMonstruo, salud, danio, velocidad, armadura, botin);
                                listaMonstruos.add(monstruo);
                            }
                        }
                        EscenarioMonstruo escenario = new EscenarioMonstruo(nombre, nivel, descripcion, listaMonstruos);
                        listaEscenarios.add(escenario);
                    }
                }
            }
        }catch (JSONException e) {
            throw new RuntimeException(e);
        }catch (IllegalArgumentException e)
        {
            System.err.println("Error de argumento ilegal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Fin");
        }
        return listaEscenarios;
    }

    public void escenarioItemAJson (HashSet<EscenarioItem> listaEscenarioItems)
    {
        JSONObject object = new JSONObject();
        try
        {
            for (EscenarioItem escenarioItem : listaEscenarioItems) {
                object.put("nombre", escenarioItem.getNombre());
                object.put("nivel", escenarioItem.getNivel());
                object.put("descripcion", escenarioItem.getDescripcion());
                JSONArray itemsJSONArray = new JSONArray();

                for (Item item : escenarioItem.getListaItems()) {
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
                    }else if(item instanceof Pocion<?>)
                    {
                        EfectoPocion efecto = (EfectoPocion) ((Pocion<?>) item).getEfecto();
                        itemJSONObject.put("efecto", efecto.getClass().getSimpleName()); //obtiene el nombre del efecto

                        if(efecto instanceof EfectoCuracion)
                        {
                            itemJSONObject.put("cantidad curacion", ((EfectoCuracion) efecto).getCantidadCuracion());
                        }else if(efecto instanceof EfectoVelocidad)
                        {
                            itemJSONObject.put("cantidad velocidad", ((EfectoVelocidad) efecto).getCantidadVelocidad());
                        }
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

    public static HashSet<EscenarioItem> jsonAEscenarioItem(JSONObject object) {
        HashSet<EscenarioItem> escenarioItems = new HashSet<>();
        try {
            Iterator<String> keys = object.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject escenarioItemJson = object.getJSONObject(key);
                EscenarioItem escenarioItem = new EscenarioItem();
                escenarioItem.setNombre(escenarioItemJson.getString("nombre"));
                escenarioItem.setNivel(escenarioItemJson.getInt("nivel"));
                escenarioItem.setDescripcion(escenarioItemJson.getString("descripcion"));

                JSONArray itemsJSONArray = escenarioItemJson.getJSONArray("items");
                for (int i = 0; i < itemsJSONArray.length(); i++) {
                    JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);
                    Item item = null;
                    if (itemJSONObject.getString("tipo").equals("Arma")) {
                        // Construir un objeto Arma
                        item = new Arma(itemJSONObject.getString("nombre"), itemJSONObject.getString("descripcion"), itemJSONObject.getInt("danio"));
                    } else if (itemJSONObject.getString("tipo").equals("Armadura")) {
                        // Construir un objeto Armadura
                        item = new Armadura(itemJSONObject.getString("nombre"), itemJSONObject.getString("descripcion"), itemJSONObject.getInt("defensa"), itemJSONObject.getInt("velocidad"));
                    } else if (itemJSONObject.getString("tipo").equals("Pocion")) {
                        // Construir un objeto Pocion con su efecto correspondiente
                        String efectoType = itemJSONObject.getString("efecto");
                        if (efectoType.equals("EfectoCuracion")) {
                            item = new Pocion<EfectoCuracion>(itemJSONObject.getString("nombre"), itemJSONObject.getString("descripcion"), new EfectoCuracion(itemJSONObject.getInt("cantidadCuracion")));
                        } else if (efectoType.equals("EfectoVelocidad")) {
                            item = new Pocion<EfectoVelocidad>(itemJSONObject.getString("nombre"), itemJSONObject.getString("descripcion"), new EfectoVelocidad(itemJSONObject.getInt("cantidadVelocidad")));
                        }
                    }
                    escenarioItem.agregarItem(item);
                }
                escenarioItems.add(escenarioItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return escenarioItems;
    }


}
