package br.com.tripsapi.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.tripsapi.dao.StudyRepository;
import br.com.tripsapi.model.HandlerRequest;
import br.com.tripsapi.model.HandlerResponse;
import br.com.tripsapi.model.Study;

public class GetStudyRecordsByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final StudyRepository repository = new StudyRepository();
	
	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String topic = request.getPathParameters().get("topic");
		final String starts = request.getQueryStringParameters().get("starts");
		final String ends = request.getQueryStringParameters().get("ends");

		context.getLogger().log("Searching for registered studies for " + topic + " topic between " + starts + " and " + ends);

		final List<Study> studies = this.repository.findByPeriod(topic, starts, ends);
		
		if(studies == null || studies.isEmpty()) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}
		
		return HandlerResponse.builder().setStatusCode(200).setObjectBody(studies).build();
	}
}