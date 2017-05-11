package org.es.shkim.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.es.shkim.connection.Connection;
import org.es.shkim.services.indicies.Indices;

public class Main
{
	private static Connection		con				= new Connection();
	private static final long		pr_start_time	= System.currentTimeMillis();
	private static SimpleDateFormat	sdf				= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSZ");

	public static void main(String[] args)
	{
		System.out.println("pr start at - " + sdf.format(new Date(pr_start_time)));
		TransportClient client = con.get_connection();

		Indices i = new Indices();
		
		String index = "test0";
		
		boolean result = false;
		
		result = i.delete_index(client, index);
		System.out.println(result);
		
//		result= i.create_index(client, index,3, 0);
//		System.out.println(result);
		
		con.dis_connection(client);
		long e_time = System.currentTimeMillis();
		System.out.println("Pr end at - " + sdf.format(new Date(e_time)));
		System.out.println("Pr run diff - " + (e_time - pr_start_time) / 1000.0 + "s");
	}
}
