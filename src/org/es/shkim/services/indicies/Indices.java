package org.es.shkim.services.indicies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.es.shkim.services.esutils.EsUtiles;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

public class Indices
{

	// get all {index=[types]}
	public Map<String, List<String>> get_all_indices_info(TransportClient client)
	{
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

	// input index name return List<String> index have types
	public List<String> get_type_list(TransportClient client, String index)
	{
		List<String> type_list = new ArrayList<String>();
		GetMappingsResponse res = null;
		try
		{
			res = client.admin().indices().getMappings(new GetMappingsRequest().indices(index)).get();
		} catch (Exception e)
		{
			System.err.println(e);
		}

		ImmutableOpenMap<String, MappingMetaData> mapping = res.mappings().get(index);
		Object keys[] = mapping.keys().toArray();
		for (Object k : keys)
		{
			type_list.add(String.valueOf(k));
		}

		return type_list;
	}

	public boolean create_index(TransportClient client, String index, int shards, int replicas)
	{
		if (EsUtiles.index_isExist(client, index))
		{
			return false;
		}

		return client
				.admin().indices().prepareCreate(index).setSettings(Settings.builder()
						.put("index.number_of_shards", shards).put("index.number_of_replicas", replicas))
				.get().isAcknowledged();
	}

	public boolean delete_index(TransportClient client, String index)
	{
		if (!EsUtiles.index_isExist(client, index))
		{
			return false;
		}
		return client.admin().indices().prepareDelete(index).get().isAcknowledged();
	}

}
