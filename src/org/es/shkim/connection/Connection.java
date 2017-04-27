package org.es.shkim.connection;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.es.shkim.configure.Configure;

public class Connection
{
	private String	host					= Configure.get_conf_value("es.host");
	private int		port					= Integer.parseInt(Configure.get_conf_value("es.port"));
	private String	cluster_name			= Configure.get_conf_value("es.cluster");
	private String		ping_timeout			= Configure.get_conf_value("es.ping.timeout")+"s";
	private String		nodes_sampler_interval	= Configure.get_conf_value("es.nodes.interval")+"s";
	private boolean	sniff					= Boolean.getBoolean(Configure.get_conf_value("es.nodes.interval"));

	// settings
	private Settings settings()
	{
		Settings settings = Settings.builder().put("cluster.name", cluster_name).put("client.transport.sniff", sniff)
				.put("client.transport.ping_timeout", ping_timeout)
				.put("client.transport.nodes_sampler_interval", nodes_sampler_interval).build();

		return settings;
	}

	// connecting return es connect
	@SuppressWarnings({ "resource", "unchecked" })
	public TransportClient connection()
	{
		TransportClient client = null;
		try
		{
			client = new PreBuiltTransportClient(settings())
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
		} catch (Exception e)
		{
			System.err.println(e);
		}
		return client;
	}

	public void dis_connection(TransportClient client)
	{
		if (null != client)
		{
			client.close();
			client = null;
		}
	}

}
