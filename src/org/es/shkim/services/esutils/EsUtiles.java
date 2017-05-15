package org.es.shkim.services.esutils;

import org.elasticsearch.client.transport.TransportClient;

public class EsUtiles
{
	public static boolean index_isExist(TransportClient client, String index)
	{
		return client.admin().indices().prepareExists(index).get().isExists();
	}
	
	public static boolean mapping_isExist(TransportClient client, String index, String type)
	{
		if (!index_isExist(client, index))
		{
			return false;
		}
		return client.admin().indices().prepareGetMappings().get().getMappings().get(index).get(type) != null;
	}
}
