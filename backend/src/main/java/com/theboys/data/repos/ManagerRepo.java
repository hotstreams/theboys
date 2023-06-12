package com.theboys.data.repos;

import com.theboys.data.entities.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepo extends CrudRepository<Manager, Integer> {
}
