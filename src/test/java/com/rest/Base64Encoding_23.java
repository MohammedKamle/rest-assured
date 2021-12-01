package com.rest;

import java.util.Base64;

public class Base64Encoding_23 {
    public static void main(String[] args) {
        String usernamePassword = "myUsername:myPassword";
        String base64Encoded = Base64.getEncoder().encodeToString(usernamePassword.getBytes());
        System.out.println("Encoded value :: "+base64Encoded);
        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        System.out.println("Decoded bytes :: "+new String(decodedBytes));

        String s = new String("2");
        System.out.println("");
       // System.out.println(2 + 20 + s + 2 + 20);

    }
}
