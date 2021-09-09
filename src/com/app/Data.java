package com.app;


import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 Następnie przygotuj klasę Dane, która zawiera w sobie mapę. Kluczem mapy jest napis – nazwa pliku tekstowego,
    a wartością napis zawierający zawartość tego pliku. Dla klasy należy przewidzieć metody zapewniające jej poprawne
    działanie. Mapa wypełniana jest za pomocą konstruktora argumentowego, który dostaje jako argument kolekcję z
    niepowtarzającymi się nazwami plików tekstowych, z których należy czytać dane.
*/
public class Data {
    private Map<String,String> filesMap;

    public Data(Set<String> filesName) {
        filesMap = initMap(filesName);
    }

    /**
     *
     * @param filesName Set with Strings
     * @return  Map<String,String> as pairs from file name and data from this file
     */
    private Map<String,String> initMap(Set<String> filesName){
        if(filesName == null || filesName.isEmpty()){
            throw new IllegalArgumentException("FilesName is not valid");
        }
        Map<String,String> filesAndData = new HashMap<>();
        filesName.forEach(file -> filesAndData.put(file,readData(file)));
        return filesAndData;
    }

    /**
     *
     * @param fileName String as file name
     * @return  String as data from file
     */
    private String readData(String fileName){
        if(fileName == null || filesMap.isEmpty()){
            throw new IllegalArgumentException("Name of file is not valid");
        }
        StringBuilder strBui = new StringBuilder();
        try(Scanner scanFile = new Scanner(new FileReader(fileName))){
            while (scanFile.hasNext()){
                strBui.append(scanFile.nextLine())
                        .append("\n");
            }
        }catch (IOException exc){
            throw new IllegalStateException("File error");
        }
        return strBui.toString();
    }
}
