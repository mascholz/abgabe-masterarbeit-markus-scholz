package de.bamero.dmn;

import java.util.List;

import de.bamero.dmn.objects.SoftwareType;

public class Output {

	public static void printRisks(String header, List<String[]> listDecisionResultRisks) {
		System.out.println(header);
		for (String[] risk : listDecisionResultRisks) {
			System.out.println(risk[1]);
		}
	}

	public static void printOutput(SoftwareType[] allSoftwareType) {

		Output.printRisks("--- No Code Risk ---", allSoftwareType[0].getListDecisionResultRisks());
		Output.printRisks("--- Low Code Risk ---", allSoftwareType[1].getListDecisionResultRisks());
		Output.printRisks("--- Code Risk ---", allSoftwareType[2].getListDecisionResultRisks());
	}
}
