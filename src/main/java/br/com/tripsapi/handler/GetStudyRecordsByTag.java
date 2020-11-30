package br.com.tripsapi.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.tripsapi.dao.StudyRepository;
import br.com.tripsapi.model.HandlerRequest;
import br.com.tripsapi.model.HandlerResponse;
import br.com.tripsapi.model.Study;

public class GetStudyRecordsByTag implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final StudyRepository repository = new StudyRepository();

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String topic = request.getPathParameters().get("topic");
		final String tag = request.getQueryStringParameters().get("tag");

		context.getLogger().log("Searching for registered studies for " + topic + " and tag equals " + tag);

		final List<Study> studies = this.repository.findByTag(topic, tag);

		if (studies == null || studies.isEmpty()) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(studies).build();
	}
}