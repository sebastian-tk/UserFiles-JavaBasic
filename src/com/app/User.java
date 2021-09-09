package com.app;
/*
    Przygotuj klasę User. Klasa zawiera w sobie pola opisujące użytkownika, hasło oraz rolę (administrator lub user).
    Dla klasy należy przewidzieć metody zapewniające jej poprawne działanie. Przygotuj kolekcję bez powtarzających się
    elementów, która przechowuje przykładowe obiekty klasy User.

 */
public record User(String name, char[] password, AccountType role ) {
    public User{
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Invalid name  argument of user");
        }
        if(password == null || password.length <8){
            throw new IllegalArgumentException("Invalid password argument of user");
        }
        if(role == null){
            throw new IllegalArgumentException("Role is not valid");
        }
    }
}
