package de.idealo.shopping.mongodb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(value = "/persons")
public class PersonService {
	@Autowired
	PersonRepository personRepository;
	@Autowired
	AddressRepository addressRepository;

	@GetMapping()
	private List<Person> getPersons() throws InterruptedException {
		List<Person> persons = personRepository.findAll();
		System.out.println(persons);
		return persons;
		
	}
	
	@GetMapping(value = "{name:[a-zA-Z\\s]+}")
	private List<Person> getPersonsByLastName(@PathVariable("name") Optional<String> lastName) throws InterruptedException {
		if(lastName.isPresent())
			return personRepository.findByLastName(lastName.get());
		return null;
	}
	
	@GetMapping(value = "{contact:[\\d]+|.*@.*}")
	private List<Person> getPersonsContactValue(@PathVariable("contact") Optional<String> contact) throws InterruptedException {
		if(contact.isPresent())
			return personRepository.findByContactValue(contact.get());
		return null;
	}
	
	@PostMapping
    public Person create(@RequestBody Person person){
		List<Address> addresses = person.getAddresses();
		List<Address> savedAddresses = null;
		
		if(!CollectionUtils.isEmpty(addresses)) {
			savedAddresses = addressRepository.saveAll(addresses);
			person.setAddresses(savedAddresses);
		}
		
        return personRepository.save(person);
    } 

	@PutMapping
    public Person update(@RequestBody Person person){
        return personRepository.save(person);
    }
	
	@DeleteMapping
    public void delete(@RequestBody Person person){
         try {
			personRepository.delete(person);
		} catch (IllegalStateException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Person: {%s} cannot be found", person));
		}
    }
}
