package com.ua.passlocker.auth.utils;

public class Utils {

    public static String formatedSecret(String _rawData01, String _rawData02) {
        return String.format("%s.%s", _rawData01.substring(0, _rawData01.length() / 2), _rawData02.substring(0, _rawData02.length() / 2));
    }

}
