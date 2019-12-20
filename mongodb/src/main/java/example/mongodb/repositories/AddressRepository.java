package example.mongodb.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import example.mongodb.model.Address;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Qualifier("addressRepository")
public interface AddressRepository extends MongoRepository<Address, String> {
    @Query("{'customer': ?0}")
    List<Address> findByCustomerId(final String customerId);
}