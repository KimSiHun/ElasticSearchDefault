package org.es.shkim.services.cluster;

import org.elasticsearch.action.admin.cluster.stats.ClusterStatsRequest;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.collect.ImmutableOpenMap;

public class Cluster
{
	// cluster state
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

	public ClusterStatsResponse get_cluster_info(TransportClient client)
	{
		ClusterStatsResponse cs_stats = client.admin().cluster().clusterStats(new ClusterStatsRequest()).actionGet();
		return cs_stats;
	}
	
	
}
