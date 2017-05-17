package org.es.shkim.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.client.transport.TransportClient;
import org.es.shkim.connection.Connection;
import org.es.shkim.services.esutils.EsUtiles;

public class Main
{
	private static final long		pr_start_time	= System.currentTimeMillis();
	private static SimpleDateFormat	sdf				= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSZ");

	public static void main(String[] args)
	{
		System.out.println("pr start at - " + sdf.format(new Date(pr_start_time)));
		TransportClient client = Connection.get_instance().get_connection();


		String index = "test0";
		String type = "test0";
//		Map<String, Object> fields = new HashMap<String, Object>();
//		fields.put("test", "date");
//		fields.put("name", "text");
//		boolean result = false;
//
//		result = i.delete_index(client, index);
//		System.out.println(result);
//		
//		result= i.create_index(client, index,3, 0);
//		System.out.println(result);
//		
//		Mappings m = new Mappings();
//		System.out.println(m.create_mapping(client, index, type, fields));
		
		System.out.println(EsUtiles.mapping_isExist(client, index, type));
		
		client.close();
		long e_time = System.currentTimeMillis();
		System.out.println("Pr end at - " + sdf.format(new Date(e_time)));
		System.out.println("Pr run diff - " + (e_time - pr_start_time) / 1000.0 + "s");
	}
}
