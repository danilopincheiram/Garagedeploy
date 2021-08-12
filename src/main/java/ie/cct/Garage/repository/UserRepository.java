package ie.cct.Garage.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ie.cct.Garage.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	 public User findByUsername(String username);
}
