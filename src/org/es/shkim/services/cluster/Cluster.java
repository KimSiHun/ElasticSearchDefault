package org.es.shkim.services.cluster;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableOpenMap;

public class Cluster
{
	private ClusterState get_cluster_state(TransportClient client)
	{
		ClusterState cs = client.admin().cluster().prepareState().execute().actionGet().getState();
		return cs;
	}

	public ImmutableOpenMap<String, DiscoveryNode> get_data_nodes_list(TransportClient client)
	{
		ClusterState state = get_cluster_state(client);
		ImmutableOpenMap<String, DiscoveryNode> data_nodes = state.getNodes().getDataNodes();
		return data_nodes;
	}

	public ImmutableOpenMap<String, DiscoveryNode> get_master_nodes_list(TransportClient client)
	{
		ClusterState state = get_cluster_state(client);
		ImmutableOpenMap<String, DiscoveryNode> master_nodes = state.getNodes().getMasterNodes();
		return master_nodes;
	}
	
}
