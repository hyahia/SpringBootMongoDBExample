package de.idealo.shopping.mongodb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.idealo.shopping.mongodb.model.Order;
import de.idealo.shopping.mongodb.repositories.OrderRepository;
import de.idealo.shopping.mongodb.repositories.PersonRepository;
import de.idealo.shopping.mongodb.repositories.ProductRepository;

@RestController
@RequestMapping(value = "/orders")
public class OrderService {
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	ProductRepository productRepository;

	@GetMapping()
	private List<Order> getOrders() throws InterruptedException {
		return orderRepository.findAll();

	}

	@PostMapping
	public Order create(@RequestBody Order order) {
		if (order.getCustomer() != null)
			order.setCustomer(personRepository.findById(order.getCustomer().getId().toString()).get());

		if (order.getProduct() != null)
			order.setProduct(productRepository.findById(order.getProduct().getId().toString()).get());

		return orderRepository.save(order);
	}

	@PutMapping
	public Order update(@RequestBody Order order) {
		return orderRepository.save(order);
	}

	@DeleteMapping
	public void delete(@RequestBody Order order) {
		Optional<Order> dbOrder = orderRepository.findById(order.getId().toString());
		if (!dbOrder.isPresent()) {
			throw new ResourceNotFoundException(String.format("Order: {%s} cannot be found", order));
		}

		orderRepository.delete(order);
	}
}