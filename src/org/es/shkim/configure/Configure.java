package org.es.shkim.configure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configure
{
	protected static String root_dir_path = System.getProperty("user.dir");

	private static String get_conf_dir_path()
	{

		if (null == root_dir_path)
		{
			System.out.println("System root_dir_path is null");
			System.exit(0);
		}
		String conf_dir_path = root_dir_path + "/conf";
		return conf_dir_path;
	}

	private static Properties get_conf_file()
	{

		String conf_dir_path = get_conf_dir_path();
		File conf_file = null;
		if (null == conf_dir_path)
		{
			System.out.println("System conf_dir_path is null");
			System.exit(0);
		} else
		{
			conf_file = new File(conf_dir_path, "conf.yaml");
		}

		Properties conf_properties = new Properties();

		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(conf_file), "utf-8"));
			conf_properties.load(br);
			br.close();
		} catch (IOException ioe)
		{
			System.err.println("conf file read failed - " + ioe);
		}

		return conf_properties;
	}

	public static String get_conf_value(String key)
	{

		Properties conf_properties = get_conf_file();
		String value = null;

		if (null != key && key.equals("es.host"))
		{
			value = conf_properties.getProperty("es.connect.host").replace("\"", "").trim();
		} else if (null != key && key.equals("es.port"))
		{
			value = conf_properties.getProperty("es.connect.port").replace("\"", "").trim();
		} else if (null != key && key.equals("es.cluster"))
		{
			value = conf_properties.getProperty("es.connect.cluster.name").replace("\"", "").trim();
		} else if (null != key && key.equals("es.ping.timeout"))
		{
			value = conf_properties.getProperty("es.connect.transport.ping_timeout").replace("\"", "").trim();
		} else if (null != key && key.equals("es.nodes.interval"))
		{
			value = conf_properties.getProperty("es.transport.nodes_sampler_interval").replace("\"", "").trim();
		} else if (null != key && key.equals("es.sniff"))
		{
			value = conf_properties.getProperty("es.transport.sniff").replace("\"", "").trim();
		}
		return value;
	}
}
