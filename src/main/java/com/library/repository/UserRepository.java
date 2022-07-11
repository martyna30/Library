package com.library.repository;



import com.library.domain.Author;
import com.library.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

   Optional<User> findByUsername(String username);

   @Override
   List<User> findAll();

   @Override
   Optional<User> findById(Long id);


   @Override
   void deleteById(Long id);

   @Override
   User save(User user);

}
