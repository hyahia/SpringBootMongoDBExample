package de.idealo.shopping.mongodb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class PersonService {
	@Autowired
	PersonRepository personRepository;

	@GetMapping(value = {"/persons", "persons/{name}"})
	private List<Person> getPersons(@PathVariable("name") Optional<String> lastName) throws InterruptedException {
		if(lastName.isPresent())
			return personRepository.findByLastName(lastName.get());
		return personRepository.findAll();
	}
	
	@PostMapping("/persons")
    public Person create(@RequestBody Person person){
        return personRepository.save(person);
    } 

	@PutMapping("/persons")
    public Person update(@RequestBody Person person){
        return personRepository.save(person);
    }
	
	@DeleteMapping("/persons")
    public void delete(@RequestBody Person person){
         try {
			personRepository.delete(person);
		} catch (IllegalStateException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Person: {%s} cannot be found", person));
		}
    }
}
