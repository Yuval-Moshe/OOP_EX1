package ex1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph {
    HashMap<Integer, node_info> _vertices = new HashMap<Integer, node_info>();
    HashMap<Integer, HashMap<Integer, Double>> _weights = new HashMap<Integer, HashMap<Integer, Double>>();
    int _numOfEdges;
    int _mc;

    public WGraph_DS(){
        _vertices = new HashMap<Integer, node_info>();
        _weights = new HashMap<Integer, HashMap<Integer, Double>>();
        _numOfEdges = 0;
        _mc = 0;

    }

    public WGraph_DS (weighted_graph other){
        Collection<node_info> other_v = other.getV();
        for(node_info node : other_v){
            int node_key = node.getKey();
            _vertices.put(node_key, node);
            HashMap<Integer, Double> node_weights = new HashMap<Integer, Double>();
            Collection<node_info> node_ni = other.getV(node_key);
            for(node_info ni : node_ni){
                int ni_key = ni.getKey();
                node_weights.put(ni_key, other.getEdge(node_key, ni_key));
            }
            _weights.put(node_key, node_weights);
        }
        _numOfEdges = other.edgeSize();
        _mc = other.getMC();
    }


    @Override
    public node_info getNode(int key) {
        return _vertices.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if(node1!=node2){
            if(_vertices.containsKey(node1) && _vertices.containsKey(node2)){
                return _weights.get(node1).containsKey(node2);
            }
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(this.hasEdge(node1, node2)){
            return _weights.get(node1).get(node2);
        }
        return -1;
    }

    @Override
    public void addNode(int key) {
        if(_vertices.get(key)==null) {
            _vertices.put(key, new NodeInfo(key));
            _weights.put(key, new HashMap<Integer, Double>());
            _mc++;
        }

    }

    // test what happens if there already a weight betweehn them (should update)
    // What to do if they are the same
    @Override
    public void connect(int node1, int node2, double w) {
        if(node1!=node2 && w>=0) {
            if (_vertices.containsKey(node1) && _vertices.containsKey(node2)) {
                _weights.get(node1).put(node2, w);
                _weights.get(node2).put(node1, w);
                _numOfEdges++;
                _mc++;
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return _vertices.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if(_vertices.containsKey(node_id)){
            Collection<Integer> neighbors_keys = _weights.get(node_id).keySet();
            Collection<node_info> neighbors = new ArrayList<node_info>();
            for(Integer nodeKey : neighbors_keys){
                neighbors.add(_vertices.get(nodeKey));
            }
            return neighbors;
        }
        return new ArrayList<node_info>();
    }

    @Override
    public node_info removeNode(int key) {
        node_info temp = _vertices.get(key);
        if(_vertices.containsKey(key)){
            Collection<Integer> neighbors_keys = new ArrayList<Integer>();
            neighbors_keys.addAll(_weights.get(key).keySet());
            for(int nodeKey : neighbors_keys){
                removeEdge(nodeKey,key);
            }
            _vertices.remove(key);
            _mc++;
        }
        return temp;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1,node2)){
            _weights.get(node1).remove(node2);
            _weights.get(node2).remove(node1);
            _numOfEdges--;
            _mc++;
        }

    }

    @Override
    public int nodeSize() {
        return _vertices.size();
    }

    @Override
    public int edgeSize() {
        return _numOfEdges;
    }

    @Override
    public int getMC() {
        return _mc;
    }

    public String toString (){
        String s="";
        for(node_info node : _vertices.values()){
            for(node_info ni : getV(node.getKey())){
               s+=node.getKey() + " and "+ ni.getKey() + " are connected by: "+getEdge(node.getKey(), ni.getKey())+"\n";
            }
        }
        return s;
    }

    private class NodeInfo implements node_info{
        private String _info;
        private double _tag;
        private int _key;

        public NodeInfo(int key){
            _key = key;
            _info ="";
            _tag=0.0;
        }
        public NodeInfo (node_info other){
            _info = other.getInfo();
            _tag = other.getTag();

        }

        @Override
        public int getKey() {
            return _key;
        }

        @Override
        public String getInfo() {
            return _info;
        }

        @Override
        public void setInfo(String s) {
            _info = s;
        }

        @Override
        public double getTag() {
            return _tag;
        }

        @Override
        public void setTag(double t) {
            _tag = t;

        }

        public int compareTo(node_info other){
            if(_tag == other.getTag()){
                return 0;
            }
            else if (_tag > other.getKey()){
                return 1;
            }
            else {
                return -1;
            }
        }
    }
}
