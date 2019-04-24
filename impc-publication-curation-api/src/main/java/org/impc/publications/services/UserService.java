package org.impc.publications.services;

import org.impc.publications.models.User;

public interface UserService {
    User findOne(String username);
}
