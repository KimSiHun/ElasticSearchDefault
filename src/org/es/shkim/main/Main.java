package org.es.shkim.main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.es.shkim.services.Indices;

public class Main
{
	public static void main(String[] args)
	{
		Indices i = new Indices();
		Map<String, List<String>> indices = i.get_all_indices_info();
		Set<String> keyset = indices.keySet();
		
		for (String k : keyset)
		{
			System.out.println(indices.get(k));
		}
	}
}
