package br.com.devires.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devires.user.model.User;


@Repository
public interface UserRespository extends JpaRepository<User, Integer> {

}
