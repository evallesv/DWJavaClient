package solutions.vpf.docuware.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Organization {
	
	@JsonProperty(value = "Id")
	private int id;
	@JsonProperty(value = "Guid")
	private String guid;
	@JsonProperty(value = "Name")
	private String name;
	@JsonProperty(value = "Links")
	private List<Link> links;
	
	public Organization() { }
	
	/**
	 * @param id
	 * @param name
	 * @param links
	 */
	public Organization(int id, String guid, String name, List<Link> links) {
		super();
		this.setId(id);
		this.setName(name);
		this.setLinks(links);
		this.setGuid(guid);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}
