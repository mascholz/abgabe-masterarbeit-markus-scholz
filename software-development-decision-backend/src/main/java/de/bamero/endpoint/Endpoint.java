package de.bamero.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import de.bamero.dmn.ProcessInstance;
import de.bamero.dmn.Survey;

@Path("")
public class Endpoint {
	
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping() {
		return Response.ok().entity("Service online").build();
	}
	
	@GET
	@Path("/questions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuestions() {
		Survey survey = new Survey();
		String questions = survey.getQuestions().toString();
		return Response.ok().entity(questions).build();
	}
	
	@POST
	@Path("/surveyResult")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSuveyResults(Object object) {
		JSONObject result;
		try {
		ProcessInstance processInstance = new ProcessInstance();
		result = processInstance.processInstance(object);
		} catch (Exception e) {
			return Response.status(201).entity("failed").build();
		}
		return Response.status(201).entity(result.toString()).build();
	}
	

}
