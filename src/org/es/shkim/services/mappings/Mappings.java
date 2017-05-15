package org.es.shkim.services.mappings;

import java.util.Map;

import org.elasticsearch.client.transport.TransportClient;

public class Mappings
{
	// fields set "field_name" : "field_type" -> "field_name" : { "type" : "field_type" }
	private String map_set_fields(Map<String, Object> fields)
	{
		String json = "{\n" + "	\"properties\": {\n";
		Object keys[] = fields.keySet().toArray();
		
		for (int i = 0; i < keys.length; i++)
		{
			if (i == keys.length - 1)
			{
				json = json +"		\"" + keys[i] +"\": {\n" + "		\"type\": \"" + fields.get(keys[i]) + "\"\n"
						+ "		}\n";
				break;
			} else
			{
				json = json + "		\"" + keys[i] + "\": {\n" + "		\"type\": \"" + fields.get(keys[i]) + "\"\n"
						+ "		},\n";
			}
		}
		json = json + "}\n}";
		return json;
	}

	public boolean create_mapping(TransportClient client, String index, String type, Map<String,Object> fields)
	{
		return client.admin().indices().preparePutMapping(index).setType(type).setSource(map_set_fields(fields)).get()
				.isAcknowledged();
	}

}
