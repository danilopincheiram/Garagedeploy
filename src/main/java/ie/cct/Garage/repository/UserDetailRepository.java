package ie.cct.Garage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.cct.Garage.model.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {

}
