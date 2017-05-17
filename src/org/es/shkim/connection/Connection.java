package org.es.shkim.connection;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.es.shkim.configure.Configure;

public class Connection
{
	// default set
	private String	host					= "localhost";
	private int		port					= 9300;
	private String	cluster_name			= "my-cluster";
	private String	ping_timeout			= "5s";
	private String	nodes_sampler_interval	= "5s";
	private boolean	sniff					= true;

	private Connection(){};
	
	private static class Singleton{
		private static final Connection instance = new Connection();
	}
	
	public static Connection get_instance(){
		return Singleton.instance;
	}
	
	
	// connecting return es connect
	@SuppressWarnings({ "resource", "unchecked" })
	public TransportClient get_connection()
	{
		// read configure & set
		String es_host = Configure.get_conf_value("es.host");
		int es_port = Integer.parseInt(Configure.get_conf_value("es.port"));
		String cs_name = Configure.get_conf_value("es.cluster");
		String timeout = Configure.get_conf_value("es.ping.timeout") + "s";
		String interval = Configure.get_conf_value("es.nodes.interval") + "s";
		String es_sniff = Configure.get_conf_value("es.nodes.interval");

		if (null != cs_name)
		{
			cluster_name = cs_name;
		}
		if (null != timeout)
		{
			ping_timeout = timeout;
		}
		if (null != interval)
		{
			nodes_sampler_interval = interval;
		}
		if (null != es_sniff)
		{
			sniff = Boolean.getBoolean(es_sniff);
		}

		Settings settings = null;
		if (null != cluster_name && null != ping_timeout && null != nodes_sampler_interval)
		{
			settings = Settings.builder().put("cluster.name", cluster_name).put("client.transport.sniff", sniff)
					.put("client.transport.ping_timeout", ping_timeout)
					.put("client.transport.nodes_sampler_interval", nodes_sampler_interval).build();
			if (null != settings)
			{
				System.out.println("configure settings is ok");
			}else{
				System.out.println("check settings configure File for ES settings");
				System.exit(0);
			}
		} else
		{
			System.out.println("Invalid properties and ES settings fail - " + System.currentTimeMillis());
			System.exit(0);
		}

		TransportClient client = null;
		if (null != es_host && es_port > 0 && null != settings)
		{
			host = es_host;
			port = es_port;
			try
			{
				client = new PreBuiltTransportClient(settings)
						.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
				
			} catch (UnknownHostException e)
			{
				System.err.println(e);
			}

			if (null != client)
			{
				System.out.println("ES TransportClient set ok");
			} else
			{
				System.out.println("check settings configure File for ES TransportClient");
			}
		} else
		{
			System.out.println("Invalid properties and ES TransportClient set fail - " + System.currentTimeMillis());
		}
		return client;
	}

}
