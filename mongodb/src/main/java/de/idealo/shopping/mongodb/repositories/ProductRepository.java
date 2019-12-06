package de.idealo.shopping.mongodb.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.idealo.shopping.mongodb.model.Product;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@Qualifier("productRepository")
public interface ProductRepository extends MongoRepository<Product, String> {
}