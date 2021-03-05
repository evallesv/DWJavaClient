package solutions.vpf.docuware.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Organizations {
	@JsonProperty(value = "Organization")
	public List<Organization> organization;

	public List<Organization> getOrganization() {
		return organization;
	}

	public void setOrganization(List<Organization> organization) {
		this.organization = organization;
	}

	public Organizations() {
	}
	
	/**
	 * @param organization
	 */
	public Organizations(List<Organization> organization) {
		super();
		this.organization = organization;
	}
}
