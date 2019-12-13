package example.mongodb.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import example.mongodb.model.Address;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Qualifier("addressRepository")
public interface AddressRepository extends MongoRepository<Address, String> {
}