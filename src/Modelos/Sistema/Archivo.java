package Modelos.Sistema;

import Modelos.Entidades.Monstruo;
import Modelos.Escenarios.Escenario;

import Modelos.Escenarios.EscenarioItem;
import Modelos.Escenarios.EscenarioMonstruo;
import Modelos.Items.Arma;
import Modelos.Items.Armadura;
import Modelos.Items.Item;
import Modelos.Items.pociones.Pocion;
import Modelos.Items.pociones.EfectoCuracion;
import Modelos.Items.pociones.EfectoVelocidad;

import java.io.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.util.HashSet;



public class Archivo {


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
            System.out.println("ERROR AL LEER EL ARCHIVO");
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


    public static void jsonAEscenarioMonstruo(HashSet<Escenario> listaDeEscenarios) {
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
                        //valueOf: Convierte el String obtenido ("tipoDeMonstruo" o "DEFAULT") a la constante del enum que corresponda

                        Item botin = null;
                        if (monstruoJson.has("botin")) {
                            JSONObject botinJson = monstruoJson.getJSONObject("botin"); // busca el objeto asociado con la clave "botin"
                            String nombreItem = botinJson.optString("nombre", null); // extrae sus atributos
                            String descripcionItem = botinJson.optString("descripcion", null);
                            int especialTEsperaPItem = botinJson.optInt("especialTEsperaP", 0);

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

                                Monstruo monstruo = new Monstruo(nombreMonstruo, salud, especialTEsperaPItem, danio,velocidad, armadura, botin);
                                listaMonstruos.add(monstruo);
                            }
                        }
                        EscenarioMonstruo escenario = new EscenarioMonstruo(nombre, nivel, descripcion, listaMonstruos);
                        listaDeEscenarios.add(escenario);
                    }
                }


                EscenarioMonstruo escenario = new EscenarioMonstruo(nombre, nivel, descripcion, listaMonstruos);
                listaDeEscenarios.add(escenario);
            }
        } catch (JSONException e) {
            System.out.println("ERROR AL LEER EL JASON");

            }
        catch (JSONException e) {

            throw new RuntimeException(e);
        }catch (IllegalArgumentException e)
        {
            System.err.println("Error de argumento ilegal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Fin");
        }
    }




    public void jsonAEscenarioItem(HashSet<Escenario> listaEscenarios) throws JSONException  {

        JSONObject jsonObject = new JSONObject(JsonUtiles.leer(NombreArchivos.EscenariosM.getNombre()));

        try {
            JSONArray escenariosJSONArray = new JSONArray(jsonObject);

            for (int i = 0; i < escenariosJSONArray.length(); i++) {
                JSONObject escenarioJSONObject = escenariosJSONArray.getJSONObject(i);
                String nombre = escenarioJSONObject.getString("nombre");
                int nivel = escenarioJSONObject.getInt("nivel");
                String descripcion = escenarioJSONObject.getString("descripcion");

                JSONArray itemsJSONArray = escenarioJSONObject.getJSONArray("items");
                ArrayList<Item> listaItems = new ArrayList<>();

                for (int j = 0; j < itemsJSONArray.length(); j++) {
                    JSONObject itemJSONObject = itemsJSONArray.getJSONObject(j);
                    String nombreItem = itemJSONObject.getString("nombre");
                    String descripcionItem = itemJSONObject.getString("descripcion");
                    Item item = null;

                    if (itemJSONObject.has("danio")) {
                        int danio = itemJSONObject.getInt("danio");
                        item = new Arma(nombreItem, descripcionItem, danio);
                    } else if (itemJSONObject.has("defensa")) {
                        int defensa = itemJSONObject.getInt("defensa");
                        int velocidad = itemJSONObject.getInt("velocidad");
                        item = new Armadura(nombreItem, descripcionItem, defensa, velocidad);
                    } else if (itemJSONObject.has("tipo")) {
                        String tipo = itemJSONObject.getString("tipo");
                        String efecto = itemJSONObject.getString("efecto");

                        if ("Pocion".equals(tipo)) {
                            int cantidadCuracion = itemJSONObject.optInt("cantidadCuracion", 0);
                            int cantidadVelocidad = itemJSONObject.optInt("cantidadVelocidad", 0);
                            switch (efecto) {
                                case "EfectoCuracion":
                                    item = new Pocion<EfectoCuracion>(nombreItem, descripcionItem, new EfectoCuracion(cantidadCuracion));
                                    break;
                                case "EfectoVelocidad":
                                    item = new Pocion<EfectoVelocidad>(nombreItem, descripcionItem, new EfectoVelocidad(cantidadVelocidad));
                                    break;
                                default:
                                    // Manejar cualquier otro tipo de efecto
                            }
                        }
                    }

                    if (item != null) {
                        listaItems.add(item);
                    }
                }

                EscenarioItem escenarioItem = new EscenarioItem(nombre, nivel, descripcion, listaItems);
                listaEscenarios.add(escenarioItem);
            }
        } catch (JSONException e) {
            System.out.println("ERROR AL LEER EL JASON");
            throw new RuntimeException(e);
        }

    }




}
