package example.mongodb.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import example.mongodb.model.Customer;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Qualifier("personRepository")
public interface PersonRepository extends MongoRepository<Customer, String> {

	List<Customer> findByLastName(@Param("name") String name);
	
	@Query("{'contacts.contactValue': ?0}")
	List<Customer> findByContactValue(final String contactValue);
}