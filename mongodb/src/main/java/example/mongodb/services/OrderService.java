package example.mongodb.services;

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

import example.mongodb.model.Order;
import example.mongodb.repositories.OrderRepository;

@RestController
@RequestMapping(value = "/orders")
public class OrderService {
	OrderRepository orderRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@GetMapping()
	public List<Order> getOrders() {
		return orderRepository.findAll();

	}

	@PostMapping
	public Order create(@RequestBody Order order) {
		return orderRepository.save(order);
	}

	@PutMapping
	public Order update(@RequestBody Order order) {
		return orderRepository.save(order);
	}

	@DeleteMapping
	public void delete(@RequestBody Order order) {
		if (orderRepository.existsById(order.getId())) {
			throw new ResourceNotFoundException(String.format("Order: {%s} cannot be found", order));
		}

		orderRepository.delete(order);
	}
}