package de.bamero.dmn;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

public class Survey {

	public JSONObject getQuestions() {

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

		Definitions definitionsMeta = (Definitions) modelInstanceMeta.getModelElementsByType(definitionsType).iterator()
				.next();
		String definitionsMetaId = definitionsMeta.getId();

		Iterator<ModelElementInstance> iteratorDecision = definitions.getChildElementsByType(decisionType).iterator();
		Iterator<ModelElementInstance> iteratorDecisionMeta = definitionsMeta.getChildElementsByType(decisionType)
				.iterator();

		List<JSONObject> object = Form.createJsonObjectForCharacteristic(modelInstance, iteratorDecision);
		List<JSONObject> objectMeta = Form.createJsonObjectForCharacteristic(modelInstance, iteratorDecisionMeta);

		JSONArray combined = new JSONArray();
		for (JSONObject jsonObject : object) {
			combined.put(jsonObject);
		}

		for (JSONObject jsonObject : objectMeta) {
			combined.put(jsonObject);
		}

		JSONObject pages = new JSONObject();
		pages.put("pages", combined);

		System.out.println(pages);

		return pages;
	}

}
