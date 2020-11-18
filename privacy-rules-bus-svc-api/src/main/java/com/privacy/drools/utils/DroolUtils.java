package com.privacy.drools.utils;

import java.util.stream.Collectors;
import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.Consent;

public class DroolUtils {

	public static boolean checkClaimType(Claim claim, String type) {
		if (claim.getType().getCoding().stream().collect(Collectors.toMap(c -> c.getCode(), c -> c.getCode())).keySet()
				.contains(type)) {
			return true;
		}
		return false;
	}

	public static boolean checkConsentSecurity(Consent consent, String security) {
		if (consent.getProvision().getSecurityLabel().stream()
				.collect(Collectors.toMap(c -> c.getCode(), c -> c.getCode())).keySet().contains(security)) {
			return true;
		}
		return false;
	}
}