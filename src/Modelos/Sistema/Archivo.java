package Modelos.Sistema;
import java.io.*;
import java.util.ArrayList;

import java.io.Serializable;


public class Archivo <T>  {
    public Archivo() {
    }

    //Pasar datos de archivos a listas use genericos para no tener que escribir codigo para cada cosa que se pasa a una lista
    public  ArrayList<T> leerArchivoAArray(String archivo) {
        ObjectInputStream objectInputStream = null;
        ArrayList<T> listaItems = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(archivo);
            objectInputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                Object objeto = objectInputStream.readObject();
                listaItems.add((T) objeto);
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

    public void grabarArchivo(ArrayList<T> items, String archivo) {
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (T item : items) {
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
