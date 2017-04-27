package org.es.shkim.main;

import org.elasticsearch.client.transport.TransportClient;
import org.es.shkim.connection.Connection;

public class Main
{
	public static void main(String[] args)
	{
		Connection con = new Connection(); 
		TransportClient client = con.connection();
		
		System.out.println(client.nodeName());
		
		con.dis_connection(client);
	}
}
