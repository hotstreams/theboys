package com.theboys.data.repos;

import com.theboys.data.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "users")
@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
}
