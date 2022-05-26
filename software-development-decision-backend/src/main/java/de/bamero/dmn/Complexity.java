package de.bamero.dmn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.bamero.dmn.enums.Level;
import de.bamero.dmn.objects.Characteristic;
import de.bamero.dmn.objects.ComplexityPair;
import de.bamero.dmn.objects.Rating;

public class Complexity {

	public static void calculateComplexity(List<ComplexityPair> listDecisionResult) {

		long numberComplex = countNumberComplexity(listDecisionResult, "komplex");
		long numberComplicated = countNumberComplexity(listDecisionResult, "kompliziert");
		long numberSimple = countNumberComplexity(listDecisionResult, "einfach");

		System.out.println("komplex " + numberComplex);
		System.out.println("kompliziert " + numberComplicated);
		System.out.println("einfach " + numberSimple);
	}

	public static long countNumberComplexity(List<ComplexityPair> listDecisionResult, String complexityType) {
		return listDecisionResult.stream().filter(input -> input.getComplexity().equals(complexityType)).count();
	}

	public static long countNumberComplexityWithImportance(List<ComplexityPair> listDecisionResult,
			String complexityType, List<Rating> listRating) {
		long number = 0;
		for (ComplexityPair decisionResult : listDecisionResult) {
			if (decisionResult.getComplexity().equals(complexityType)) {
				number++;
				for (Rating rating : listRating) {
					if (rating.getKey().equals(decisionResult.getKey())) {
						if (rating.isImportance()) {
							number++;
						}
					}
				}
			}
		}

		System.out.println(complexityType + " --------------- " + number);
		return number;
	}

	public static List<ComplexityPair> involveUncertainty(List<ComplexityPair> listDecisionResult,
			List<Rating> listRating) {
		for (Rating rating : listRating) {
			if (rating.isUncertainty()) {
				for (ComplexityPair decisionResult : listDecisionResult) {
					if (decisionResult.getKey().contains(rating.getKey())) {
						updateComplexity(decisionResult);
					}
				}
			}
		}
		return listDecisionResult;
	}

	private static ComplexityPair updateComplexity(ComplexityPair complexityPair) {
		switch (complexityPair.getComplexity()) {

		case "einfach":
			complexityPair.setComplexity(Level.kompliziert.toString());
			break;
		case "kompliziert":
			complexityPair.setComplexity(Level.komplex.toString());
			break;
		}
		return complexityPair;
	}

	public static String updateComplexity(String complexity) {
		switch (complexity) {

		case "einfach":
			return Level.kompliziert.toString();
		case "kompliziert":
			return Level.komplex.toString();
		}
		return complexity;
	}

	public static Map<String, Double> getEffort(List<Characteristic> characteristicList) {
		double numberNoCode = 0;
		double numberLowCode = 0;
		double numberCode = 0;

		for (Characteristic characteristic : characteristicList) {
			int factor = 1;
			if (characteristic.isImportance()) {
				factor = 2;
			}

			numberNoCode += getNumber(characteristic.getNoCodeComplexity(), factor, 0.44);
			numberLowCode += getNumber(characteristic.getLowCodeComplexity(), factor, 0.44);
			numberCode += getNumber(characteristic.getCodeComplexity(), factor, 1);
		}

		System.out.println(
				"numberNoCode " + numberNoCode + " numberLowCode " + numberLowCode + " numberCode " + numberCode);
		HashMap<String, Double> effort = new HashMap<String, Double>();
		effort.put("noCode", numberNoCode);
		effort.put("lowCode", numberLowCode);
		effort.put("code", numberCode);

		System.out.println(effort);

		Map<String, Double> result = effort.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors
				.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		double index = 1;
		for (Map.Entry<String, Double> setResult : result.entrySet()) {
			setResult.setValue(index);
			index += 1;
		}
		System.out.println("sortiert " + result);

		return result;
	}

	public static double getNumber(String complexity, int importanceFactor, double effortFactor) {
		double number = 0;
		switch (complexity) {
		case "einfach":
			number = (importanceFactor * 1 * effortFactor);
			break;

		case "kompliziert":
			number = (long) (importanceFactor * 1.5);
			break;

		case "komplex":
			number = (importanceFactor * 2);
			break;

		case "chaotisch":
			number = (importanceFactor * 3);
			break;
		}

		return number;
	}
}
