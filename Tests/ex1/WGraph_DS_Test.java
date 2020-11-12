package ex1;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DS_Test {
    private static Random _rnd = null;


//    public static weighted_graph graphCreator(int v, int e, int seed){
//        weighted_graph wg = new WGraph_DS();
//        _rnd = new Random(seed);
//        for(int i=0;i<v;i++) {
//            wg.addNode(n);
//        }    }

    //Test Basic Functionalities
    @Test
    public void test_0() {
        weighted_graph WG = new WGraph_DS();
        WG.addNode(0);
        WG.addNode(1);
        WG.connect(0, 1, 4.3);
        assertTrue(WG.hasEdge(0, 1));
        assertEquals(WG.getEdge(0, 1), 4.3);
        WG.removeEdge(0, 1);
        assertFalse(WG.hasEdge(0, 1));
        assertEquals(WG.getEdge(0, 1), -1);
        WG.addNode(2);
        WG.addNode(3);
        WG.connect(0, 2, 3.5);
        WG.connect(0, 3, 2.6);
        assertEquals(WG.getEdge(0, 2), 3.5);
        assertEquals(WG.getEdge(0, 3), 2.6);
        WG.removeNode(2);
        assertFalse(WG.hasEdge(0, 2));
        assertEquals(WG.edgeSize(), 1);
        WG.connect(0, 0, 8);
        assertEquals(WG.edgeSize(), 1);
    }

    @Test
    public void test_1() {
        /** Test Deep Copy **/

        // Tes 2.1 //
        weighted_graph WG = new WGraph_DS();
        for (int i = 0; i < 10; i++) {
            WG.addNode(i);
        }

        for (int i = 0; i < 9; i++) {
            double rnd_weight = Math.random() * (5) + 1;
            WG.connect(i, i + 1, rnd_weight);
        }
        weighted_graph WG_Copy = new WGraph_DS(WG);
        boolean flag =true;
        flag &= WG.getV().containsAll(WG_Copy.getV()) ;
        flag &= WG_Copy.getV().containsAll(WG.getV()) ;
        assertTrue(flag);
        for(node_info node : WG.getV()){
            for(node_info ni : WG.getV()){
                if(WG.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG_Copy.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        for(node_info node : WG_Copy.getV()){
            for(node_info ni : WG_Copy.getV()){
                if(WG_Copy.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        assertTrue(flag);

        // Test 2.2 - Remove 1 edge  //
        node_info curr = WG.getV().iterator().next();
        node_info node_to_remove = WG.getV(curr.getKey()).iterator().next();
        WG.removeEdge(curr.getKey(), node_to_remove.getKey());
        flag &= WG.getV().containsAll(WG_Copy.getV()) ;
        flag &= WG_Copy.getV().containsAll(WG.getV()) ;
        for(node_info node : WG.getV()){
            for(node_info ni : WG.getV()){
                if(WG.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG_Copy.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        for(node_info node : WG_Copy.getV()){
            for(node_info ni : WG_Copy.getV()){
                if(WG_Copy.hasEdge(node.getKey(), ni.getKey())){
                    flag &= WG.hasEdge(node.getKey(), ni.getKey());
                }
            }
        }
        assertFalse(flag);


    }

    @Test
    public void test_2() {
        /** Test isConnected() **/
        weighted_graph WG_1 = new WGraph_DS();
        for (int i = 0; i < 10; i++) {
            WG_1.addNode(i);
        }

        for (int i = 0; i < 9; i++) {
            double rnd_weight = Math.random() * (5) + 1;
            WG_1.connect(i, i + 1, rnd_weight);
        }

        //TEST 2.1//
        weighted_graph_algorithms WGA = new WGraph_Algo();
        WGA.init(WG_1);
        assertTrue(WGA.isConnected());
        WGA.getGraph().removeNode(1);
        assertFalse(WGA.isConnected());

        weighted_graph WG_2 = new WGraph_DS();
        WGA.init(WG_2);
        assertTrue(WGA.isConnected());
        WGA.getGraph().addNode(0);
        assertTrue(WGA.isConnected());

        //TEST 2.2 -

    }


    @Test
    public void test_3() {
        /** Test shortestPath() **/
        //TEST 3.1 - Path 0->2->3->4, pathDist = 6.00; //
        weighted_graph WG_1 = new WGraph_DS();
        for (int i = 0; i < 5; i++) {
            WG_1.addNode(i);
        }

        WG_1.connect(0, 1, 1);
        WG_1.connect(0, 2, 2);
        WG_1.connect(1, 2, 1);
        WG_1.connect(1, 4, 6);
        WG_1.connect(2, 3, 3);
        WG_1.connect(3, 4, 1);
        weighted_graph_algorithms WGA = new WGraph_Algo();
        WGA.init(WG_1);
        List<node_info> shortestPath = WGA.shortestPath(0, 4);
        for(node_info node : shortestPath){
            System.out.print(node.getKey()+", ");
        }
        System.out.println();
        assertEquals(WGA.shortestPathDist(0,4), 6);

        //TEST 3.2 - Path 0->4->2->3->10, pathDist = 6.00; //
        weighted_graph WG_2 = new WGraph_DS();
        for (int i = 0; i < 11; i++) {
            WG_2.addNode(i);
        }
        WG_2.connect(0, 1, 5);
        WG_2.connect(0, 4, 2);
        WG_2.connect(0, 6, 4);
        WG_2.connect(1, 2, 9);
        WG_2.connect(1, 3, 7);
        WG_2.connect(2, 3, 1);
        WG_2.connect(2, 4, 2);
        WG_2.connect(3, 10, 1);
        WG_2.connect(4, 7, 4);
        WG_2.connect(4, 9, 5);
        WG_2.connect(5, 6, 2);
        WG_2.connect(5, 8, 4);
        WG_2.connect(6, 7, 7);
        WG_2.connect(6, 10, 3);
        WG_2.connect(7, 8, 1);
        WG_2.connect(8, 9, 3);
        WG_2.connect(9, 10, 6);
        WGA.init(WG_2);
        shortestPath = WGA.shortestPath(0, 10);
        for(node_info node : shortestPath){
            System.out.print(node.getKey()+", ");
        }
        System.out.println();
        assertEquals(WGA.shortestPathDist(0,10), 6);

        //TEST 3.3 - Path 0->6->10, pathDist = 7.00; //
        WGA.getGraph().removeEdge(3,10);
        shortestPath = WGA.shortestPath(0, 10);
        for(node_info node : shortestPath){
            System.out.print(node.getKey()+", ");
        }
        assertEquals(WGA.shortestPathDist(0,10), 7);

        //TEST 3.4 - No Path, pathDist = -1.00//
        WGA.getGraph().removeEdge(6,10);
        WGA.getGraph().removeEdge(9,10);
        shortestPath = WGA.shortestPath(0, 10);
        assertTrue(shortestPath.isEmpty());
        assertEquals(WGA.shortestPathDist(0,10), -1);


        //TEST 3.5 - 1 Node Graph, pathDist = 0.00; //
        weighted_graph WG_3 = new WGraph_DS();
        WG_3.addNode(0);
        WGA.init(WG_3);
        assertEquals(WGA.shortestPathDist(0,0), 0);

























    }
}





