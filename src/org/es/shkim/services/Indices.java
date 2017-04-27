package org.es.shkim.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.es.shkim.connection.Connection;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

public class Indices
{
	protected Connection con = new Connection();

	// get all {index=[types]}
	public Map<String, List<String>> get_all_indices_info()
	{

		TransportClient client = con.get_connection();

		Map<String, List<String>> index_type_mapping = new HashMap<String, List<String>>();
		List<String> type_list = null;

		// indices mapping return ImmutableOpenMap(es usage)
		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> es = client.admin().indices()
				.getMappings(new GetMappingsRequest()).actionGet().getMappings();
		
		
		Object index_list[] = es.keys().toArray();
		ImmutableOpenMap<String, MappingMetaData> mapping = null;
		String index = null;
		for (Object i : index_list)
		{
			index = String.valueOf(i);
			mapping = es.get(index);
			type_list = new ArrayList<String>();
			
			for (ObjectObjectCursor<String, MappingMetaData> oc : mapping)
			{
				type_list.add(oc.key);
			}
			index_type_mapping.put(index, type_list);
		}
		return index_type_mapping;
	}
}
