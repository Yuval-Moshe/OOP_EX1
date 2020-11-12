package ex1;

import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    weighted_graph _wg;

    public WGraph_Algo(){
        _wg = new WGraph_DS();
    }


    @Override
    public void init(weighted_graph g) {
        _wg = g;

    }

    @Override
    public weighted_graph getGraph() {
        return _wg;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph wg_copy = new WGraph_DS(_wg);
        return wg_copy;
    }

    @Override
    public boolean isConnected() {
        Collection<node_info> nodes = _wg.getV();
        if(nodes.isEmpty()){
            return true;
        }
        Queue<node_info> q = new LinkedList<node_info>();
        node_info node = nodes.iterator().next();
        q.add(node);
        HashMap<Integer, Boolean> connected = new HashMap<Integer, Boolean>();
        connected.put(node.getKey(), true);
        while(!q.isEmpty()&& connected.size()!=nodes.size()){
            node_info curr = q.poll();
            Collection<node_info> neighbors = _wg.getV(curr.getKey());
            for(node_info next : neighbors){
                if(connected.get(next.getKey())==null){
                    q.add(next);
                    connected.put(next.getKey(),true);
                }
            }
        }
        if(connected.size()==nodes.size()){
            return true;
        }
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        List<node_info> path = shortestPath(src,dest);
        if(!path.isEmpty()){
            return _wg.getNode(dest).getTag();
        }
        return -1;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        node_info src_node = _wg.getNode(src);
        node_info dest_node = _wg.getNode(dest);
        HashMap<Integer, node_info> prev = Dijkstra(src_node, dest_node);
        List<node_info> path = reconstructPath(prev, src_node, dest_node);
        return path;

    }


    public HashMap<Integer, node_info> Dijkstra (node_info src, node_info dest){
        PriorityQueue<node_info> pq = new PriorityQueue<node_info>((node1, node2) -> Double.compare(node1.getTag(), node2.getTag()));
        double dist = Double.POSITIVE_INFINITY;
        HashSet<Integer> visited = new HashSet<Integer>();
        HashMap<Integer, node_info> prev = new HashMap<Integer, node_info>();
//        HashMap<Integer, HashMap<Integer, node_info>> prev = new  HashMap<Integer, HashMap<Integer, node_info>>();
        src.setTag(0);
        pq.add(src);
        while (!pq.isEmpty()){
            node_info curr = pq.poll();
            int curr_key = curr.getKey();
            if(!visited.contains(curr_key) && curr!=dest){
                visited.add(curr_key);
                Collection<node_info> curr_ni = _wg.getV(curr_key);
                for(node_info ni : curr_ni) {
                    int ni_key = ni.getKey();
                    if (!visited.contains(ni_key)) {
                        double ni_dist_from_src = curr.getTag() + _wg.getEdge(curr_key, ni_key);
                        if (ni_dist_from_src < dist) {
                            if (ni == dest) {
                                if (ni_dist_from_src < dist) {
                                    dist = ni_dist_from_src;
                                }
                            }
                            if(prev.get(ni_key)==null){
                                ni.setTag(ni_dist_from_src);
                                prev.put(ni_key, curr);
                            }
                            else if(ni_dist_from_src<ni.getTag()) {
                                ni.setTag(ni_dist_from_src);
                                prev.put(ni_key, curr);
                            }
                            pq.add(ni);
                        }
                    }
                }
            }
        }
        return prev;
    }
    private List<node_info> reconstructPath (HashMap<Integer, node_info> prev, node_info src, node_info dest){
        List<node_info> path_temp = new ArrayList<node_info>();
        List<node_info> path = new ArrayList<node_info>();
        path_temp.add(dest);
        for(int i = dest.getKey(); prev.get(i)!=null; i=prev.get(i).getKey()){
            path_temp.add(prev.get(i));
        }
        if(!path_temp.isEmpty() && path_temp.get(path_temp.size()-1).getKey()==src.getKey()) {
            for (int i = path_temp.size() - 1; i >= 0; i--) {
                path.add(path_temp.get(i));
            }
        }
        return path;


    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
