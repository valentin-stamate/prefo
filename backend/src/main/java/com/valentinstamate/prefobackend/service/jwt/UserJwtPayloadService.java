package com.valentinstamate.prefobackend.service.jwt;

import com.valentinstamate.prefobackend.models.UserJwtPayload;
import com.valentinstamate.prefobackend.persistence.consts.UserType;
import com.valentinstamate.prefobackend.persistence.models.UserModel;
import jakarta.ws.rs.core.HttpHeaders;
import java.util.Date;
import java.util.List;
import java.util.Map;

public  class UserJwtPayloadService {

    public static Map<String, Object> payloadFromUser(UserModel userModel) {
        return Map.of(
                JwtUserPayloadKey.USERNAME_KEY, userModel.getUsername(),
                JwtUserPayloadKey.USER_TYPE_KEY, userModel.getUserType().toString()
        );
    }

    public static UserJwtPayload getUser(Map<String, Object> payload) {
        if (payload == null) {
            return null;
        }

        return new UserJwtPayload(
                (String) payload.get(JwtUserPayloadKey.USERNAME_KEY),
                UserType.valueOf((String) payload.get(JwtUserPayloadKey.USER_TYPE_KEY))
        );
    }

    public static UserJwtPayload getUserPayloadFromHeaders(HttpHeaders headers) {
        List<String> values = headers.getRequestHeaders().get(HttpHeaders.AUTHORIZATION);
        String token = values.get(0);

        return getUser(JwtService.decode(token));
    }

    public static Date getExpirationDate(Map<String, Object> payload) {
        int expirationTime = (Integer) payload.get(JwtUserPayloadKey.EXPIRATION_DATE);
        return new Date(expirationTime * 1000L);
    }

}
