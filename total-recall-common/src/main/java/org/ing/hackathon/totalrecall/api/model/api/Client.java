package org.ing.hackathon.totalrecall.api.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.stereotype.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@Indexed
@Entity
public class Client{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	private Long id;

	@JsonProperty("zip")
	private String zip;

	@JsonProperty("birthday")
	private String birthday;

	@JsonProperty("country")
	private String country;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("addresss")
	private String addresss;

	@JsonProperty("city")
	private String city;

	@JsonProperty("title")
	private String title;

	@JsonProperty("userName")
	private String userName;

	@JsonProperty("idNumber")
	private String idNumber;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("surname")
	private String surname;

	@JsonProperty("email")
	private String email;

	@Tolerate
	public Client(){}
}