package com.app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/*
    Klasa Przetwarzanie Danych zawiera w sobie mapę, której kluczem jest obiekt klasy User, natomiast wartością obiekt
    klasy Dane. W klasie należy przewidzieć metodę dodającą do mapy kolejną parę użytkownik - dane.

    Każda para oznacza użytkownika, który postanowił przetwarzać dane plików tekstowych podanych jako wartość w postaci
    obiektu klasy Dane.

    Kiedy użytkownik jest administratorem może zmieniać zawartość plików modyfikując wartości w mapie klasy.
    Użytkownik o zwykłych uprawnieniach może odczytać, ile razy w plikach wystąpił podany przez niego wzorzec.

    Na koniec wszystkie pliki, są nadpisywane zmodyfikowaną zawartością, o ile użytkownik był administratorem.

    Na podstawie przedstawionego opisu przygotuj aplikację, która zasymuluje przykładowy scenariusz zarządzania plikami.

 */
public class DataProcessing {
    private Map<User, Data> usersDataMap;

    private DataProcessing() {
        this.usersDataMap = new HashMap<>();
    }

    /**
     * @param users object Set<User>
     * @param data  object Data
     * @return new object DataProcessing with pairs from users and data
     */
    static DataProcessing createDataProcessing(Set<User> users, Data data) {
        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException("Invalid users argument");
        }
        if (data == null) {
            throw new IllegalArgumentException("Invalid data argument");
        }
        DataProcessing dataProcessing = new DataProcessing();
        for (var oneUser : users) {
            dataProcessing.add(oneUser, data);
        }
        return dataProcessing;
    }

    /**
     * Methods  allows simulates file management.Depending on the type of account, users have the option to
     * modify or read only. Finally, saves the changes
     */
    public void serviceUsers() {
        for (var userDataEntry : usersDataMap.entrySet()) {
            if (isRoleTheSame(userDataEntry.getKey().role(), AccountType.USER)) {
                processForUser(userDataEntry);
            }
            if (isRoleTheSame(userDataEntry.getKey().role(), AccountType.ADMIN)) {
                userDataEntry.setValue(processForAdmin(userDataEntry).getValue());
            }
        }
        saveData();
    }

    /**
     * Method saves changes admins files from map to files
     */
    private void saveData() {
        for (var pairUserData : usersDataMap.entrySet()) {
            if(pairUserData.getKey().role().equals(AccountType.ADMIN)){
                for (var pairFileNameData : pairUserData.getValue().getData().entrySet()) {
                    try (PrintWriter printFile = new PrintWriter(new FileWriter(pairFileNameData.getKey()))) {
                        printFile.println(pairFileNameData.getValue());
                    } catch (IOException exc) {
                        throw new IllegalStateException("Error writing to file");
                    }
                }
            }
        }
    }

    /**
     * @param typeLeft  first object of AccountType
     * @param typeRight second object of AccountType
     * @return true, if the same type , else false
     */
    private boolean isRoleTheSame(AccountType typeLeft, AccountType typeRight) {
        return typeLeft.equals(typeRight);
    }

    /**
     * @param user object User
     * @param data object Data
     *             Method adds new pair User,Data to map
     */
    public void add(User user, Data data) {
        if (user == null) {
            throw new IllegalArgumentException("User is not valid");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        usersDataMap.put(user, data);
    }

    /**
     * @param entryInput object Map.Entry<User,Data>
     * @return Map.Entry<User, Data> with modified or unmodified Data
     */
    private Map.Entry<User, Data> processForAdmin(Map.Entry<User, Data> entryInput) {
        System.out.println("Hello " + entryInput.getKey().name() + ", account: " + entryInput.getKey().role());
        int counterFile = 1;
        for (Map.Entry<String, String> dataPair : entryInput.getValue().getData().entrySet()) {
            System.out.println("*** " + counterFile + " file ***");
            dataPair.setValue(serviceModifyData(dataPair.getValue()));
            counterFile++;
        }
        return entryInput;
    }

    /**
     * @param data String as expression to modify
     * @return String as an empty string, with either new data or unchanged
     */
    private String serviceModifyData(String data) {
        boolean answer;
        StringBuilder bufferData = new StringBuilder(data);
        System.out.print("Do you want remove data?");
        answer = answerYes();
        if (answer) {
            removeData(bufferData);
        }
        System.out.print("Do you want add data?");
        answer = answerYes();
        if (answer) {
            bufferData.append(readExpression("enter: "));
        }
        System.out.println("Do you want print current data?");
        answer = answerYes();
        if (answer) {
            System.out.println(bufferData);
        }
        return bufferData.toString();
    }

    /**
     * @param data StringBuilder as data
     */
    private void removeData(StringBuilder data) {
        data.setLength(0);
    }

    /**
     * @param entry object Map.Entry<User,Data>
     *              The method asks the user for an expression, then search for the number of times the given
     *              expression has occurred
     */
    private void processForUser(Map.Entry<User, Data> entry) {
        System.out.println("*** Hello " + entry.getKey().name() + ", account: " + entry.getKey().role() + " ***");
        String phraseToLookFor = readExpression("enter expression to find: ");
        System.out.println("expression: \"" + phraseToLookFor +
                "\" number of appearances: " + howOftenExpressionOccurs(phraseToLookFor, entry.getValue()) + "\n");
    }

    /**
     * @param expression String as expression
     * @param data       object Data
     * @return integer value as how many times a given expression occurs
     */
    private int howOftenExpressionOccurs(String expression, Data data) {
        int counter = 0;
        for (String dataFile : data.getData().values()) {
            counter += calculateFrequency(dataFile, expression);
        }
        return counter;
    }

    /**
     * @param allExpression String as  a whole phrase
     * @param expression    String as expression to find
     * @return integer value as the number of occurrences of expression  in allExpression
     */
    private int calculateFrequency(String allExpression, String expression) {
        int lastIndex = 0;
        int counter = 0;
        while (lastIndex != -1) {
            lastIndex = allExpression.indexOf(expression, lastIndex);
            if (lastIndex != -1) {
                counter++;
                lastIndex += expression.length();
            }
        }
        return counter;
    }

    /**
     * @param message String as message to user
     * @return String as expression from user
     */
    private String readExpression(String message) {
        System.out.print(message);
        return new Scanner(System.in).nextLine();
    }

    /**
     * @return boolean value.Ask Yes is true, ask No is false
     */
    private boolean answerYes() {
        System.out.print("yes/no: ");
        Scanner scanIn = new Scanner(System.in);
        String input;
        do {
            input = scanIn.nextLine();
            if (isNotEqual(input, "yes") && isNotEqual(input, "no")) {
                input = null;
                System.out.println("\t=> Invalid input");
            }
        } while (input == null);
        return input.equals("yes");
    }

    /**
     * @param expressionLeft  first String to compare
     * @param expressionRight second String to compare
     * @return true, if expressions are equal, else false
     */
    private boolean isNotEqual(String expressionLeft, String expressionRight) {
        return !expressionLeft.equals(expressionRight);
    }
}
