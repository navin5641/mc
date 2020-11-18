package com.privacy.drools.stub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Consent;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

@Component
public class StubService {

	static IGenericClient client = null;

	public StubService() {
		super();
		FhirContext fhirContext = FhirContext.forR4();
		client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
		client.registerInterceptor(new LoggingInterceptor(false));
	}

	@SuppressWarnings("deprecation")
	public static Patient getSamplePatient() {
		Patient patient = client.read(Patient.class, "1611715");
		return patient;
	}

	public List<Claim> getSampleClaims(String patientId) {
		Bundle bundle = client.search().forResource("Claim")
				.where(Claim.PATIENT.hasId(patientId)).returnBundle(Bundle.class)
				.execute();
		Map<Claim, Claim> map = bundle.getEntry().stream()
				.collect(Collectors.toMap(c -> (Claim) c.getResource(), c -> (Claim) c.getResource()));
		List<Claim> claims = new ArrayList<Claim>(map.keySet());
		
		for(Claim claim : claims.subList(0,3)) {
			claim.getType().getCoding().add(new Coding().setCode("professional"));
		}
		return claims;
	}

	public List<Consent> getSampleConsents(String patientId) {
		Bundle bundle = client.search().forResource("Consent").where(Consent.PATIENT.hasId("1611715"))
				.returnBundle(Bundle.class).execute();
		Map<Consent, Object> map = bundle.getEntry().stream()
				.collect(Collectors.toMap(c -> (Consent) c.getResource(), c -> (Consent) c.getResource()));
		List<Consent> consents = new ArrayList<Consent>(map.keySet());
		Consent consent = consents.iterator().next();
		consent.getProvision().addSecurityLabel(new Coding().setCode("Restricted"));
		return consents;
	}

}