package de.bamero.dmn;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.Decision;
import org.camunda.bpm.model.dmn.instance.DecisionTable;
import org.camunda.bpm.model.dmn.instance.Definitions;
import org.camunda.bpm.model.dmn.instance.Input;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import de.bamero.dmn.enums.DevelopmentType;
import de.bamero.dmn.enums.Level;
import de.bamero.dmn.objects.Characteristic;
import de.bamero.dmn.objects.ComplexityPair;
import de.bamero.dmn.objects.Rating;
import de.bamero.dmn.objects.SoftwareType;
import de.bamero.endpoint.Endpoint;
import de.bamero.endpoint.RestApplication;

@ProcessApplication("DMN Decision for Softwaredevelopment")
public class DecisionApplication extends ServletProcessApplication {

	protected final static Logger LOGGER = Logger.getLogger(DecisionApplication.class.getName());
	private static HashMap<String, String> listKeyEverything = new HashMap<String, String>();
	private static DecisionService decisionServiceEverything;

	@PostDeploy
	public void evaluateDecisionTable(ProcessEngine processEngine) {
		DecisionService decisionService = processEngine.getDecisionService();
		decisionServiceEverything = decisionService;
		HashMap<String, String> listKey;

		String definitionsId;

		// Data for survey

		File file = new File(VariableFile.fileRisk);
		File fileMeta = new File(VariableFile.fileMeta);
		File fileConclusion = new File(VariableFile.fileConclusion);

		DmnModelInstance modelInstance = Dmn.readModelFromFile(file);
		DmnModelInstance modelInstanceMeta = Dmn.readModelFromFile(fileMeta);
		DmnModelInstance modelInstanceConclusion = Dmn.readModelFromFile(fileConclusion);

		ModelElementType definitionsType = modelInstance.getModel().getType(Definitions.class);
		ModelElementType decisionType = modelInstance.getModel().getType(Decision.class);
		ModelElementType decisionTableType = modelInstance.getModel().getType(DecisionTable.class);
		ModelElementType inputType = modelInstance.getModel().getType(Input.class);

		Collection<ModelElementInstance> definitionsInstances = modelInstance.getModelElementsByType(definitionsType);
		Definitions definitions = (Definitions) definitionsInstances.iterator().next();
		definitionsId = definitions.getId();
		System.out.println("definitions " + definitionsId);

		Definitions definitionsMeta = (Definitions) modelInstanceMeta.getModelElementsByType(definitionsType).iterator()
				.next();
		String definitionsMetaId = definitionsMeta.getId();

		Iterator<ModelElementInstance> iteratorDecision = definitions.getChildElementsByType(decisionType).iterator();
		Iterator<ModelElementInstance> iteratorDecisionMeta = definitionsMeta.getChildElementsByType(decisionType)
				.iterator();

		List<JSONObject> object = Form.createJsonObjectForCharacteristic(modelInstance, iteratorDecision);
		List<JSONObject> objectMeta = Form.createJsonObjectForCharacteristic(modelInstance, iteratorDecisionMeta);

		JSONArray combined = new JSONArray();
		for (JSONObject ob : object) {
			combined.put(ob);
		}

		for (JSONObject ob : objectMeta) {
			combined.put(ob);
		}

		JSONObject pages = new JSONObject();
		pages.put("pages", combined);

		System.out.println(pages);

		// Input

		InputData inputData = new InputData(new Object());
		VariableMap variables = inputData.loadInput();
		List<Rating> listRating = inputData.loadRating();

		// Calculation

		List<DecisionDefinition> listDecisionDefinition = processEngine.getRepositoryService()
				.createDecisionDefinitionQuery().latestVersion().unlimitedList();

		listKey = ProcessDmn.getDecisionDefinitionFromDmn(listDecisionDefinition, definitionsId);

		HashMap<String, String> listKeyMeta = ProcessDmn.getDecisionDefinitionFromDmn(listDecisionDefinition,
				definitionsMetaId);

		ProcessDmn processDmn = new ProcessDmn(decisionService);

		DevelopmentType[] developmentTypeArray = DevelopmentType.values();

		// new

		HashMap<String, String> listKeyNew = listKeyMeta;
		listKeyNew.putAll(listKey);

		listKeyEverything = listKeyNew;

	}

	public static HashMap<String, String> getKeys() {
		return listKeyEverything;
	}

	public static DecisionService getService() {
		return decisionServiceEverything;
	}
}
