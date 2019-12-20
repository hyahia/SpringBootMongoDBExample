package example.mongodb.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import example.mongodb.model.Order;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Qualifier("orderRepository")
public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{'customer': ?0}")
    List<Order> findByCustomerId(final String customerId);

    @Query("{'product': ?0}")
    List<Order> findByProductId(final String productId);
}