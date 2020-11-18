package com.privacy.drools.model;

import java.math.BigDecimal;
import java.util.Date;

public class ClaimSummary {

	private String id;
	private String status;
	private String type;
	private String patient;
	private String provider;
	private String priority;
	private String insurance;
	private BigDecimal total;
	private Date billDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public ClaimSummary(String id, String status, String type, String patient, String provider, String priority,
			String insurance, BigDecimal total, Date billDate) {
		super();
		this.id = id;
		this.status = status;
		this.type = type;
		this.patient = patient;
		this.provider = provider;
		this.priority = priority;
		this.insurance = insurance;
		this.total = total;
		this.billDate = billDate;
	}

}