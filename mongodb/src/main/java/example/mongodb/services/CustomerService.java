package example.mongodb.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import example.mongodb.dto.CustomerDTO;
import example.mongodb.model.Address;
import example.mongodb.model.Customer;
import example.mongodb.model.Order;
import example.mongodb.repositories.AddressRepository;
import example.mongodb.repositories.OrderRepository;
import example.mongodb.repositories.PersonRepository;

@RestController
@RequestMapping(value = "/customer")
public class CustomerService {
	private PersonRepository personRepository;
	// @TODO create then use AddressService instead
	private AddressRepository addressRepository;
	// @TODO use OrderService instead
	private OrderRepository orderRepository;

	@Autowired
	public CustomerService(PersonRepository personRepository, AddressRepository addressRepository, OrderRepository orderRepository) {
		this.personRepository = personRepository;
		this.addressRepository = addressRepository;
		this.orderRepository = orderRepository;
	}

	@GetMapping()
	public List<CustomerDTO> getCustomers() {
		return getAllCustomers();
	}

	@GetMapping(value = "{name:[a-zA-Z\\s]+}")
	public List<CustomerDTO> getCustomerByLastName(@PathVariable("name") String lastName) {
		List<Customer> customers = personRepository.findByLastName(lastName);
		return customers.stream().map(customer -> new CustomerDTO(customer
				, addressRepository.findByCustomerId(customer.getId()),
				orderRepository.findByCustomerId(customer.getId()))).collect(Collectors.toList());
	}

	@GetMapping(value = "{contact:[\\d]+|.*@.*}")
	public List<CustomerDTO> getCustomerContactValue(@PathVariable("contact") String contact) {
		List<Customer> customers = personRepository.findByContactValue(contact);
		return customers.stream().map(customer -> new CustomerDTO(customer
				, addressRepository.findByCustomerId(customer.getId()),
				orderRepository.findByCustomerId(customer.getId()))).collect(Collectors.toList());
	}

	@PostMapping
	public CustomerDTO create(@RequestBody CustomerDTO inputCustomerDTO) {
		return saveCustomer(inputCustomerDTO);
	}

	@PutMapping
	public CustomerDTO update(@RequestBody CustomerDTO customerDTO) {
		if (!personRepository.existsById(customerDTO.getCustomer().getId())) {
			throw new ResourceNotFoundException(String.format("Customer: {%s} cannot be found", customerDTO.getCustomer()));
		}

		return saveCustomer(customerDTO);
	}

	@DeleteMapping
	public void delete(@RequestBody CustomerDTO customerDTO) {
		if (!personRepository.existsById(customerDTO.getCustomer().getId())) {
			throw new ResourceNotFoundException(String.format("Customer: {%s} cannot be found", customerDTO.getCustomer()));
		}
		
		deleteCustomer(customerDTO);
	}

	private List<CustomerDTO> getAllCustomers() {
		List<Customer> customers = personRepository.findAll();
		return customers.stream().map(customer -> new CustomerDTO(customer, addressRepository.findByCustomerId(customer.getId()),
				orderRepository.findByCustomerId(customer.getId()))).collect(Collectors.toList());
	}

	private CustomerDTO saveCustomer(CustomerDTO inputCustomerDTO){
		CustomerDTO savedCustomerDTO = new CustomerDTO();

		// Save Customer
		Customer customer = personRepository.save(inputCustomerDTO.getCustomer());
		savedCustomerDTO.setCustomer(customer);

		// Save Addresses
		if (!CollectionUtils.isEmpty(inputCustomerDTO.getAddresses())) {
			List<Address> addressList = inputCustomerDTO.getAddresses();
			addressList.forEach(address -> address.setCustomer(customer));
			savedCustomerDTO.setAddresses(addressRepository.saveAll(addressList));
		}

		// Save Orders
		if (!CollectionUtils.isEmpty(inputCustomerDTO.getOrders())) {
			List<Order> orderList = inputCustomerDTO.getOrders();
			orderList.forEach(order -> order.setCustomer(customer));
			savedCustomerDTO.setOrders(orderRepository.saveAll(inputCustomerDTO.getOrders()));
		}

		return savedCustomerDTO;
	}

	private void deleteCustomer(CustomerDTO inputCustomerDTO){
		// Delete Addresses
		if (!CollectionUtils.isEmpty(inputCustomerDTO.getAddresses())) {
			addressRepository.deleteAll(inputCustomerDTO.getAddresses());
		}

		// Delete Orders
		if (!CollectionUtils.isEmpty(inputCustomerDTO.getOrders())) {
			orderRepository.deleteAll(inputCustomerDTO.getOrders());
		}

		// Delete Customer
		personRepository.delete(inputCustomerDTO.getCustomer());
	}
}