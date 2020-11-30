package br.com.tripsapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import br.com.tripsapi.model.Trip;

public class TripRepository {

	private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

	public Trip save(final Trip trip) {
		mapper.save(trip);
		return trip;
	}

	public List<Trip> findByPeriod(final String starts, final String ends) {

		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(starts));
		eav.put(":val2", new AttributeValue().withS(ends));

		final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
				.withKeyConditionExpression("date between :val1 and :val2").withExpressionAttributeValues(eav);

		final List<Trip> trips = mapper.query(Trip.class, queryExpression);

		return trips;
	}

	public List<Trip> findByCountry(final String country) {

		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(country));
		// talvez retornar pois essa query pode estar estranha com o tagindex
		final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
				.withIndexName("tagIndex").withConsistentRead(false).withKeyConditionExpression("Country = :val1")
				.withExpressionAttributeValues(eav);

		final List<Trip> trips = mapper.query(Trip.class, queryExpression);

		return trips;
	}

	public List<Trip> findByCity(String country, String city) {
		final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(country));
		eav.put(":val2", new AttributeValue().withS(city));

		final Map<String, String> expression = new HashMap<>();

		// guilherme: comentei a linha 62 pq n�o entra no nome das variaveis
		// consumed is a reserver word in DynamoDB
		// expression.put("#consumed", "consumed");

		final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
				// .withIndexName("consumedIndex").withConsistentRead(false) gui:comentado pois
				// nao segue indice, ou segue?
				.withKeyConditionExpression("Country = :val1 and city=:val2").withExpressionAttributeValues(eav)
				.withExpressionAttributeNames(expression);

		final List<Trip> trips = mapper.query(Trip.class, queryExpression);

		return trips;
	}
}