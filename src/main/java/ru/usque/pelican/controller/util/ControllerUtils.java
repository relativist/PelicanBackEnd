package ru.usque.pelican.controller.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.usque.pelican.config.PelicanUserPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.function.Supplier;

public class ControllerUtils {

    public static boolean hasAccess(HttpServletRequest request,Integer originalUserId) {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal.getClass().isAssignableFrom(PelicanUserPrincipal.class)) {
            Integer userId = ((PelicanUserPrincipal) userPrincipal).getUser().getId();
            return userId.equals(originalUserId);
        }
        return false;
    }

    public static <T> ResponseEntity<T> callResponse(HttpServletRequest request, Integer userId, Supplier<T> supplier) {
        if (ControllerUtils.hasAccess(request, userId)) {
            T t = supplier.get();
            if (t == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
