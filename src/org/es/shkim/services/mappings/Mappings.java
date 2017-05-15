package org.es.shkim.services.mappings;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class Mappings
{
	public boolean create_mapping(TransportClient client, String index, String type, Map<String,Object> fields){
		
		XContentBuilder builder = null;
		Set<String> keys = fields.keySet();
		try
		{
			builder = XContentFactory.jsonBuilder();
			builder.startObject();
			
			for (String k : keys)
			{
				builder.field(k, fields.get(k));
			}
			builder.endObject();
			
		} catch (IOException e)
		{
			System.err.println(e);
		}
		
		return client.admin().indices().preparePutMapping(index).setType(type).setSource(builder).get().isAcknowledged();
	}
	
	public void delete_mapping(TransportClient client, String index, String type)
	{
		
	}
}
