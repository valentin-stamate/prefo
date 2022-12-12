package com.valentinstamate.prefobackend.service.jwt;

import jakarta.ws.rs.core.HttpHeaders;
import java.util.Date;
import java.util.List;
import java.util.Map;

//public  class UserJwtPayloadService {
//
//    public static Map<String, Object> payloadFromUser(User user) {
//        return Map.of(
//                JwtUserPayloadKey.USERNAME_KEY, user.getUsername(),
//                JwtUserPayloadKey.USER_TYPE_KEY, user.getUserType().toString()
//        );
//    }
//
//    public static UserPayload getUser(Map<String, Object> payload) {
//        return new UserPayload(
//                (String) payload.get(JwtUserPayloadKey.USERNAME_KEY),
//                UserType.valueOf((String) payload.get(JwtUserPayloadKey.USER_TYPE_KEY))
//        );
//    }
//
//    public static UserPayload getUserPayloadFromHeaders(HttpHeaders headers) {
//        List<String> values = headers.getRequestHeaders().get(HttpHeaders.AUTHORIZATION);
//        String token = values.get(0);
//
//        return getUser(JwtService.decode(token));
//    }
//
//    public static Date getExpirationDate(Map<String, Object> payload) {
//        int expirationTime = (Integer) payload.get(JwtUserPayloadKey.EXPIRATION_DATE);
//        return new Date(expirationTime * 1000L);
//    }
//
//}
