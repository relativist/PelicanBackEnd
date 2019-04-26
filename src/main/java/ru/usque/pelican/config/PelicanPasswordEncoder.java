package ru.usque.pelican.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PelicanPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return String.valueOf(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return String.valueOf(charSequence).equalsIgnoreCase(s);
    }
}
