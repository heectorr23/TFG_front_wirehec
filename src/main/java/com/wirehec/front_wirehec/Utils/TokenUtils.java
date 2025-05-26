package com.wirehec.front_wirehec.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.List;

public class TokenUtils {

    public static String getUserNameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("sub").asString(); // Cambia "name" si el campo en el token tiene otro nombre
    }

    public static String getUserRoleFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class); // Extraer el arreglo de roles
        return roles != null && !roles.isEmpty() ? roles.get(0) : "Sin Rol"; // Devolver el primer rol o "Sin Rol"
    }
}