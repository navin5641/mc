package com.privacy.drools.api;

import java.util.List;
import java.util.stream.Collectors;

import org.hl7.fhir.r4.model.Claim;
import org.hl7.fhir.r4.model.Claim.ClaimStatus;
import org.hl7.fhir.r4.model.Consent;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privacy.drools.model.ClaimSummary;
import com.privacy.drools.stub.StubService;

@RestController
public class PrivacyController {
	@Autowired
	private KieSession session;

	@Autowired
	private StubService stubService;

	@GetMapping("/Claims")
	public List<ClaimSummary> processClaims(@RequestParam("patient") String patient) {
		StringBuffer sb = new StringBuffer();
		List<Claim> claims = stubService.getSampleClaims(patient);
		sb.append("Count Before privacy rules -" + claims.size());
		List<Consent> consents = stubService.getSampleConsents(patient);
		for (Claim cl : claims) {
			session.insert(cl);
		}
		for (Consent co : consents) {
			session.insert(co);
		}
		session.fireAllRules();

		claims = claims.stream().filter(c -> c.getStatus() != ClaimStatus.NULL).collect(Collectors.toList());
		sb.append(" ; Count After privacy rules -" + claims.size());
		return getClaimSummary(claims);
	}

	public static List<ClaimSummary> getClaimSummary(List<Claim> claims) {

		List<ClaimSummary> claimSummaries = claims.stream()
				.map(claim -> new ClaimSummary(claim.getId().split("/")[5], claim.getStatus().getDisplay(),
						claim.getType().getCoding().get(0).getCode(), claim.getPatient().getDisplay(),
						claim.getProvider().getDisplay(), claim.getPriority().getCoding().get(0).getCode(),
						claim.getInsurance().get(0).getCoverage().getDisplay(), claim.getTotal().getValue(),
						claim.getBillablePeriod().getStart()))
				.collect(Collectors.toList());
		return claimSummaries;
	}

}