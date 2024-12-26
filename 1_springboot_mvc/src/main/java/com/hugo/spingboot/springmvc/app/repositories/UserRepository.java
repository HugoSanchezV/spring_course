package com.hugo.spingboot.springmvc.app.repositories;

import com.hugo.spingboot.springmvc.app.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
