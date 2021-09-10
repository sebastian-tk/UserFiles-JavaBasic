package com.app;


import java.util.HashSet;
import java.util.Set;

/*
    Przygotuj klasę User. Klasa zawiera w sobie pola opisujące użytkownika, hasło oraz rolę (administrator lub user).
    Dla klasy należy przewidzieć metody zapewniające jej poprawne działanie. Przygotuj kolekcję bez powtarzających się
    elementów, która przechowuje przykładowe obiekty klasy User.

    Następnie przygotuj klasę Dane, która zawiera w sobie mapę. Kluczem mapy jest napis – nazwa pliku tekstowego,
    a wartością napis zawierający zawartość tego pliku. Dla klasy należy przewidzieć metody zapewniające jej poprawne
    działanie. Mapa wypełniana jest za pomocą konstruktora argumentowego, który dostaje jako argument kolekcję z
    niepowtarzającymi się nazwami plików tekstowych, z których należy czytać dane.

    Klasa Przetwarzanie Danych zawiera w sobie mapę, której kluczem jest obiekt klasy User, natomiast wartością obiekt
    klasy Dane. W klasie należy przewidzieć metodę dodającą do mapy kolejną parę użytkownik - dane. Każda para oznacza
    użytkownika, który postanowił przetwarzać dane plików tekstowych podanych jako wartość w postaci obiektu klasy Dane.
    Kiedy użytkownik jest administratorem może zmieniać zawartość plików modyfikując wartości w mapie klasy. Użytkownik
    o zwykłych uprawnieniach może odczytać, ile razy w plikach wystąpił podany przez niego wzorzec. Na koniec wszystkie
    pliki, są nadpisywane zmodyfikowaną zawartością, o ile użytkownik był administratorem. Na podstawie przedstawionego
    opisu przygotuj aplikację, która zasymuluje przykładowy scenariusz zarządzania plikami.
 */
public class App {
    public static void main(String[] args) {
        try{
            Set<User> users = Set.of(
                    new User("Iza Nowak", new char[]{'p', 'a', 's', 's','1'},AccountType.USER),
                    new User("Adam Kowal", new char[]{'p', 'a', 's', 's', '2',},AccountType.USER),
                    new User("Ola Kazal", new char[]{'p', 'a', 's', 's', '3'},AccountType.ADMIN),
                    new User("Kamil Szybki", new char[]{'p', 'a', 's', 's', '4'},AccountType.USER),
                    new User("Tomasz Jaki", new char[]{'p', 'a', 's', 's', '5'},AccountType.ADMIN),
                    new User("Marta Mucha", new char[]{'p', 'a', 's', 's', '6'},AccountType.USER)
            );
            final String path = "src/com/app/";
            Set<String> files = new HashSet<>();
            files.add(path.concat("file1.txt"));
            files.add(path.concat("file4.txt"));
            files.add(path.concat("file3.txt"));
            files.add(path.concat("file4.txt"));
            files.add(path.concat("file5.txt"));
            files.add(path.concat("file6.txt"));

            Data data  = new Data(files);
            DataProcessing dataProcessing= DataProcessing.createDataProcessing(users,data);
            dataProcessing.serviceUsers();

        }catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }
}
