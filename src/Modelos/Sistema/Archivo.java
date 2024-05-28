package Modelos.Sistema;
import Modelos.Items.Item;

import java.io.*;
import java.util.ArrayList;



public class Archivo   {
    public Archivo() {
    }

    //Pasar datos de archivos a listas use genericos para no tener que escribir codigo para cada cosa que se pasa a una lista
    public  ArrayList<Item> leerArchivoItems(String archivo) {
        ObjectInputStream objectInputStream = null;
        ArrayList<Item> listaItems = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(archivo);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                Object objeto = objectInputStream.readObject();
                listaItems.add((Item) objeto);
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
        return listaItems;
    }

    public void grabarArchivo(ArrayList<Item> items, String archivo) {
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Item item : items) {
                objectOutputStream.writeObject(item);
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
}
