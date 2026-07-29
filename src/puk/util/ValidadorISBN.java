/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puk.util;

public class ValidadorISBN {

    public static boolean esValido(String isbn) {

        String limpio = isbn
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase();

        if (limpio.length() == 10) {
            return validarISBN10(limpio);
        }

        if (limpio.length() == 13) {
            return validarISBN13(limpio);
        }

        return false;
    }

    private static boolean validarISBN10(String isbn) {

        int suma = 0;

        for (int i = 0; i < 10; i++) {

            char caracter = isbn.charAt(i);
            int valor;

            if (i == 9 && caracter == 'X') {
                valor = 10;
            } else if (Character.isDigit(caracter)) {
                valor = Character.getNumericValue(caracter);
            } else {
                return false;
            }

            suma += valor * (10 - i);
        }

        return suma % 11 == 0;
    }

    private static boolean validarISBN13(String isbn) {

        int suma = 0;

        for (int i = 0; i < 13; i++) {

            char caracter = isbn.charAt(i);

            if (!Character.isDigit(caracter)) {
                return false;
            }

            int valor = Character.getNumericValue(caracter);

            if (i % 2 == 0) {
                suma += valor;
            } else {
                suma += valor * 3;
            }
        }

        return suma % 10 == 0;
    }

    public static String limpiar(String isbn) {
        return isbn
                .replace("-", "")
                .replace(" ", "")
                .toUpperCase();
    }
}