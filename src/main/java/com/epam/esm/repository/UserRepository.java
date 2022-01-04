package com.epam.esm.repository;

import com.epam.esm.model.entity.User;

public interface UserRepository extends CrudRepository<User> {
    User findUserWithMaxSpentMoney();
    void delete(User user);
}
