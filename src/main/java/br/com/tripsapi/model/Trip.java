package br.com.tripsapi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "trip")
public class Trip {

	@DynamoDBHashKey(attributeName = "Country")
	private String country;
	@DynamoDBRangeKey(attributeName = "Date")
	private String date;

	@DynamoDBAttribute(attributeName = "City")
	private String city;
	@DynamoDBAttribute(attributeName = "Reason")
	private String reason;

 

	public Trip(String country, String date, String city, String reason) {
		super();
		this.country = country;
		this.date = date;
		this.city = city;
		this.reason = reason;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getReason() {
		return reason;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}



	public Trip() {
		super();
	}
 
}
