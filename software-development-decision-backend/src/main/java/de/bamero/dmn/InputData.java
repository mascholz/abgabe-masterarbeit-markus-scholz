package de.bamero.dmn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import de.bamero.dmn.objects.Rating;

public class InputData {

	private String jsonString;
	private JSONObject jsonObject;
	private VariableMap variables = Variables.createVariables();
	private List<Rating> listRating = new ArrayList<Rating>();

	public InputData(Object object) {
		super();
		this.jsonString = "{\"timeBehavior\":true,\"ressourceUsage\":true,\"performance\":[\"importance\"],\"capacity\":12,\"coexistence\":false,\"interoperability\":\"wenige\",\"compatibility\":[\"importance\",\"uncertainty\"],\"reliability\":[\"uncertainty\"],\"recoverability\":false,\"faultTolerance\":true,\"availability\":\"Unternehmenskritisch\",\"maturity\":true,\"confidentiality\":true,\"integrity\":true,\"indisputability\":true,\"accountability\":true,\"authenticity\":true,\"security\":[\"importance\",\"uncertainty\"],\"modularity\":true,\"reusability\":false,\"evaluability\":true,\"modifiability\":true,\"testability\":true,\"adaptability\":true,\"installability\":\"Appstore\",\"interchangeability\":\"iOS\",\"portability\":[\"importance\"],\"numberEntities\":4,\"relationship\":\"eins-zu-viele-Beziehung\",\"inputValues\":\"Varianten\",\"outputValues\":\"Standardisierung\",\"calculationLogic\":false,\"datastructure\":[\"importance\",\"uncertainty\"],\"peopleGroup\":\"Kunden\",\"userType\":\"Gelegenheitsuser\",\"simplicity\":true,\"inputError\":true,\"userInterface\":true,\"accessibility\":\"Tablet\",\"numberRole\":3,\"minimumViableProduct\":true,\"ownership\":true,\"companySecret\":true,\"priceDependency\":true,\"specialFeatures\":true,\"metaQuestions\":[\"uncertainty\"],\"completeness\":true,\"correctness\":true,\"appropriateness\":\"kompliziert\",\"changingRequirements\":\"nein\",\"numberSoftwareDevelopers\":true,\"experience\":\"hoch\",\"collaborationExperts\":true,\"support\":true,\"resources\":[\"importance\"]}";
		this.jsonObject = new JSONObject(jsonString);

		Gson gson = new Gson();

		if (object.toString().contains("body")) {
			JSONObject jsonObjectFromFrontend = new JSONObject(gson.toJson(object));

			jsonObject = (JSONObject) jsonObjectFromFrontend.get("body");
		}

		Iterator<String> keys = this.jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();
			Object value = this.jsonObject.get(key);
			if (!(value instanceof JSONArray)) {
				variables.putValue(key, value);
			} else {
				JSONArray castedValueArray = (JSONArray) value;

				boolean uncertainty = false;
				boolean importance = false;
				for (Object castedValue : castedValueArray) {

					if (castedValue.toString().contains("uncertainty")) {
						uncertainty = true;
					}
					if (castedValue.toString().contains("importance")) {
						importance = true;
					}
				}
				this.listRating.add(new Rating(key, uncertainty, importance));
			}
		}
		variables.putValue("conclusion", true);
	}

	public VariableMap loadInput() {
		return this.variables;
	}

	public List<Rating> loadRating() {
		return this.listRating;
	}

}
