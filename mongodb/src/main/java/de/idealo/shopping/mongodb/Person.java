package de.idealo.shopping.mongodb;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document
public class Person {

	@Id private ObjectId id;

	private String firstName;
	private String lastName;

	// One-To-Many (Same Document)
	private List<Contact> contacts;
	
	// One-To-Many (Separate Document)
	@DBRef(lazy = true)
    private List<Address> addresses;

}