package org.otus.services;

public interface UserAuthService {
    boolean isAdmin(String login, String password);
}
