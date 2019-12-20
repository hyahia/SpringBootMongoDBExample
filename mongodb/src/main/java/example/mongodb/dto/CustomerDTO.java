package example.mongodb.dto;

import java.util.List;

import example.mongodb.model.Customer;
import example.mongodb.model.Address;
import example.mongodb.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {

	private Customer customer;

	// One-To-Many (Separate Documents)
	private List<Address> addresses;
	
	// Many-To-Many (Via Middle Document "Person-Order-Product")
	private List<Order> orders;
}