package minisu.ipdip;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class Decision
{
	@JsonProperty
	private final String id;

	@JsonProperty
	private final String name;

	@JsonProperty
	private final List<String> alternatives;

	@JsonCreator
	public Decision(@JsonProperty("name") String name, @JsonProperty("alternatives") List<String> alternatives)
	{
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.alternatives = alternatives;
	}

	public String getId()
	{
		return id;
	}
}
