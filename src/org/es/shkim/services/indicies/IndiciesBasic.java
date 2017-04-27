package org.es.shkim.services.indicies;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.es.shkim.connection.Connection;

public class IndiciesBasic
{
	private Connection con = new Connection();

	// test
	public void indices_basic()
	{
		TransportClient client = con.get_connection();
		System.out.println(String.valueOf(client.admin().indices().getMappings(new GetMappingsRequest()).actionGet().getMappings()));
		con.dis_connection(client);
	}
}
