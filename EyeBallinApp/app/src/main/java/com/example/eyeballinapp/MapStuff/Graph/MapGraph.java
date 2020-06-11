package com.example.eyeballinapp.MapStuff.Graph;
// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph

import android.location.Location;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class MapGraph {
    HashMap<String, MapNode> nodes; //HashMap where the name of the node is the key

    public int getSize() {
        return nodes.size();
    }

    public MapNode getNode(String nodeName) {
        return nodes.get(nodeName);
    }

    // Default constructor
    public MapGraph() {
        this.nodes = new HashMap<String, MapNode>(); //make a new adjacency list
    }

    // Constructor for if you already have a list of nodes that you want to put into the graph.
    public MapGraph(HashMap<String, MapNode> nodes) {
        this.nodes = nodes; // put the nodes in the graph
    }

    // Add a node into the graph that has already been built
    public void addNode(MapNode node) {
        nodes.put(node.getName(), node); // Add a node to the graph.
    }

    // Add a node into the graph that has already been built
    public void addNode(String name, CustomLocation loc) {
        nodes.put(name, new MapNode(name, loc)); // Add a node to the graph.
    }

    // remove a node and all its edges.
    public void removeNode(String name) {
        if (!nodes.containsKey(name))  // if the node isnt in the graph we cannot remove it.
            return;

        MapNode node = nodes.get(name);

        // remove the edges that the node has.
        for (String adjacentNode : node.getAdjacency().keySet()) {
            nodes.get(adjacentNode).removeEdge(name);
        }

        nodes.remove(name); //remove the node
    }

    // Add a single edge to the graph.
    public boolean addEdge(String source, String destination, double weight) {
        if (!nodes.containsKey(source) || !nodes.containsKey(destination)) { // If this is a node that has not been added yet
            return false;
        }

        nodes.get(source).addEdge(destination, weight);
        nodes.get(destination).addEdge(source, weight);

        return true;
    }

    // determines if a node is in the graph.
    public boolean containsNode(String name) {
        return nodes.containsKey(name);
    }

    // Find the closes node given a location.
    public String nearestNodeName(Location loc) {
        return nearestNode(loc).getNode().getName();
    }

    // Find the node that is closest to the location passed.
    /* // FIXME: 12/4/2019?
    * Does nearestNode take floor numbers into account? I run into a problem that when i move
    * towards the y axis in floor 1 and the nearestNode returns a node in floor 4.
    *
    * We can use the first number of id to identify each floor. Idk where to change tho.
    *
    * Or we can just move to the next node everytime we press a button as long as we are in
    * the right direction.
    *
    * Or use the distance to calculate the stepLength so we would only have to press the button
    * a few times.
    *
    * */
    public Step nearestNode(Location loc) {
        double lowest = Double.MAX_VALUE;
        MapNode lowestNode = new MapNode("N/A", new CustomLocation(0, 0, 0));

        for (String nodeName : nodes.keySet()) { //go through all the nodes in the graph
            double distance = nodes.get(nodeName).getLocation().distanceTo(loc); // get the distance from where we are to that node
            if (distance < lowest) { // if its lower than the current lowest distance,
                lowest = distance; //save that distance
                lowestNode = nodes.get(nodeName); //set the lowest node to the actual lowest node
            }
        }
        return new Step(lowestNode, loc);
    }

    // Navigate from a source node, to a destination node, and return the route between them.
    public Route navigateFrom(String source, String destination) {
        return new Route(route(source, destination));
    }

    private ArrayList<Step> route(String sourceVertex, String destinationVertex) {
        if (!nodes.containsKey(sourceVertex) || !nodes.containsKey(destinationVertex)) //Cannot find the distances if the source or destination is not in the graph.
            return new ArrayList<Step>();

        HashMap<String, String> prev = new HashMap<String, String>(); // keeps track of the previous node for each node
        HashMap<String, Double> distance = new HashMap<String, Double>(); //distance used to store the distance of vertex from a source
        ArrayList<Step> shortestPath = new ArrayList<>(); // Create the arrayList that we will use to store the shortest path.

        // Creating a priority queue for ordering which node to select next.
        // Size of the number of nodes, compares based on distance
        // AdjacencyPair contains a String for the destination, and an double for the distance to it.
        PriorityQueue<AdjacencyPair> priorityQueue = new PriorityQueue<AdjacencyPair>(nodes.size(), new AdjacencyPairComparator());
//        TreeSet<AdjacencyPair> priorityQueue = new TreeSet<AdjacencyPair>(new AdjacencyPairComparator()); // Using this instead of a priorityQueue because it does not allow duplicates.

        distance.put(sourceVertex, 0.0); // Set starting vertex to have a distance of 0.
        priorityQueue.offer(new AdjacencyPair(sourceVertex, 0.0));

        //Initialize the distance of all other nodes to infinity
        for (String nodeName : nodes.keySet()) {
            if (!nodeName.equals(sourceVertex))
                distance.put(nodeName, Double.MAX_VALUE); // Set the distance to be infinity for every node
            prev.put(nodeName, "N/A"); // Set the previous value to be N/A for every node

            //priorityQueue.offer(new AdjacencyPair(nodeName, distance.get(nodeName))); // Add the node to the priority queue. SourceVertex will be at the top since it has the smallest.
        }

        // While there are nodes that are still reachable.
        while (priorityQueue.isEmpty() == false) {

            // Find the node with the shortest distance
            String closest = priorityQueue.poll().getVertex(); // Take the top node off the queue.

            if (!closest.equals(destinationVertex)) { // If we have not reached the destination vertex, continue traversing neighbors of this node to find the shortest path.
                HashMap<String, Double> neighbors = nodes.get(closest).getAdjacency(); // Get the neighbors of the closest node.

                for (String neighbor : neighbors.keySet()) {
                    Double pathLength;
                    pathLength = distance.get(closest) + neighbors.get(neighbor); // Store the path length, which is the current distance, plus the distance of to the neighbor.

                    if (pathLength < distance.get(neighbor)) {// If the pathLength we just found is shorter than the one we already have stored in the distance HashMap
                        distance.put(neighbor, pathLength); // Update the length of the new shortest path to that node.
                        prev.put(neighbor, closest); // Set the node previous to the neighbor for the shortest distance path, to the closest node.

                        priorityQueue.offer(new AdjacencyPair(neighbor, pathLength));
                    }
                }
            } else { // We have found the shortest path from the source to the destination!
                // Now we need to build the steps that need to be taken to navigate from the source to the destination.
                String currentNode = closest; //renaming the variable to make this section more clear

                // walk backwards from the destination to the source, and add each node along the way.
                if (!prev.get(currentNode).equals("N/A") || currentNode.equals(sourceVertex)) { // Can only run this if prev is defined, or this is the source (I think thats the case if the destination was not found)
                    while (!prev.get(currentNode).equals("N/A")) { // While there is a node behind the currentNode (excludes the last node).
                        Step nextStep = new Step(nodes.get(currentNode), nodes.get(prev.get(currentNode)).getLocation()); // add the previous node and the distance between them to the step.

                        shortestPath.add(0, nextStep); // Add that node to the top of the list
                        currentNode = prev.get(currentNode); // set the currentNode to the one previous to it along the path.
                    }
                }
            }
        }
        return shortestPath;
    }

    // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
    // This function actually traverses the tree to find the shortest path between the source, and destination vertices.
    // Assumes that the graph is connected!!!
    private List<MapNode> dijkstra(String sourceVertex, String destinationVertex) {
        if (!nodes.containsKey(sourceVertex) || !nodes.containsKey(destinationVertex)) //Cannot find the distances if the source or destination is not in the graph.
            return new ArrayList<MapNode>();

        HashMap<String, String> prev = new HashMap<String, String>(); // keeps track of the previous node for each node
        HashMap<String, Double> distance = new HashMap<String, Double>(); //distance used to store the distance of vertex from a source
        ArrayList<MapNode> shortestPath = new ArrayList<>(); // Create the arrayList that we will use to store the shortest path.

        // Creating a priority queue for ordering which node to select next.
        // Size of the number of nodes, compares based on distance
        // AdjacencyPair contains a String for the destination, and an double for the distance to it.
        PriorityQueue<AdjacencyPair> priorityQueue = new PriorityQueue<AdjacencyPair>(nodes.size(), new AdjacencyPairComparator());
//        TreeSet<AdjacencyPair> priorityQueue = new TreeSet<AdjacencyPair>(new AdjacencyPairComparator()); // Using this instead of a priorityQueue because it does not allow duplicates.

        distance.put(sourceVertex, 0.0); // Set starting vertex to have a distance of 0.
        priorityQueue.offer(new AdjacencyPair(sourceVertex, 0.0));

        //Initialize the distance of all other nodes to infinity
        for (String nodeName : nodes.keySet()) {
            if (!nodeName.equals(sourceVertex))
                distance.put(nodeName, Double.MAX_VALUE); // Set the distance to be infinity for every node
            prev.put(nodeName, "N/A"); // Set the previous value to be N/A for every node

            //priorityQueue.offer(new AdjacencyPair(nodeName, distance.get(nodeName))); // Add the node to the priority queue. SourceVertex will be at the top since it has the smallest.
        }

        // While there are nodes that are still reachable.
        while (priorityQueue.isEmpty() == false) {

            // Find the node with the shortest distance
            String closest = priorityQueue.poll().getVertex(); // Take the top node off the queue.

            if (!closest.equals(destinationVertex)) { // If we have not reached the destination vertex, continue traversing neighbors of this node to find the shortest path.
                HashMap<String, Double> neighbors = nodes.get(closest).getAdjacency(); // Get the neighbors of the closest node.

                for (String neighbor : neighbors.keySet()) {
                    Double pathLength;
                    pathLength = distance.get(closest) + neighbors.get(neighbor); // Store the path length, which is the current distance, plus the distance of to the neighbor.

                    if (pathLength < distance.get(neighbor)) {// If the pathLength we just found is shorter than the one we already have stored in the distance HashMap
                        distance.put(neighbor, pathLength); // Update the length of the new shortest path to that node.
                        prev.put(neighbor, closest); // Set the node previous to the neighbor for the shortest distance path, to the closest node.

                        priorityQueue.offer(new AdjacencyPair(neighbor, pathLength));
                    }
                }
            } else { // We have found the shortest path from the source to the destination!
                // Now we need to build the steps that need to be taken to navigate from the source to the destination.
                String currentNode = closest; //renaming the variable to make this section more clear

                // walk backwards from the destination to the source, and add each node along the way.
                if (!prev.get(currentNode).equals("N/A") || currentNode.equals(sourceVertex)) { // Can only run this if prev is defined, or this is the source (I think thats the case if the destination was not found)
                    while (!currentNode.equals("N/A")) { // While there is a node behind the currentNode
                        shortestPath.add(0, nodes.get(currentNode)); // Add that node to the top of the list
                        currentNode = prev.get(currentNode); // set the currentNode to the one previous to it along the path.
                    }
                }
            }
        }
        return shortestPath;
    }
}
// This code is contributed by Aakash Hasija