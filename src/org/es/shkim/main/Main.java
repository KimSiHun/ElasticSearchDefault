package org.es.shkim.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.client.transport.TransportClient;
import org.es.shkim.connection.Connection;
import org.es.shkim.services.indicies.Indicies;

public class Main
{
	private static Connection		con				= new Connection();
	private static final long		pr_start_time	= System.currentTimeMillis();
	private static SimpleDateFormat	sdf				= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSZ");

	public static void main(String[] args)
	{
		System.out.println("pr start at - " + sdf.format(new Date(pr_start_time)));
		TransportClient client = con.get_connection();

		Indicies i = new Indicies();
		Map<String, List<String>> indices = i.get_all_indices_info(client);
		Set<String> keyset = indices.keySet();

		for (String k : keyset)
		{
			List<String> types = i.get_type_list(client, k);
			for (String t : types)
			{
				System.out.println(t);
			}
			System.out.println("----------------------------------");
			
		}

		con.dis_connection(client);
		long e_time = System.currentTimeMillis();
		System.out.println("Pr end at - " + sdf.format(new Date(e_time)));
		System.out.println("Pr run diff - " + (e_time - pr_start_time) / 1000.0 + "s");
	}
}
