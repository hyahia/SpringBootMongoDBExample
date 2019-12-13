package example.mongodb.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import example.mongodb.model.Order;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Qualifier("orderRepository")
public interface OrderRepository extends MongoRepository<Order, String> {
}