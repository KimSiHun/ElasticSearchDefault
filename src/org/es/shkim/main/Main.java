package org.es.shkim.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.es.shkim.connection.Connection;
import org.es.shkim.services.cluster.Cluster;

public class Main
{
	private static Connection		con				= new Connection();
	private static final long		pr_start_time	= System.currentTimeMillis();
	private static SimpleDateFormat	sdf				= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSZ");

	public static void main(String[] args)
	{
		System.out.println("pr start at - " + sdf.format(new Date(pr_start_time)));
		TransportClient client = con.get_connection();

		Cluster c = new Cluster();
		
		
		con.dis_connection(client);
		long e_time = System.currentTimeMillis();
		System.out.println("Pr end at - " + sdf.format(new Date(e_time)));
		System.out.println("Pr run diff - " + (e_time - pr_start_time) / 1000.0 + "s");
	}
}
