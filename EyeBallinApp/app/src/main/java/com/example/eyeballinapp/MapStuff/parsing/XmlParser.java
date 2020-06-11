package com.example.eyeballinapp.MapStuff.parsing;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.content.Context;

import java.util.HashMap;

import android.content.res.Resources;

import com.example.eyeballinapp.MapStuff.Location.CustomLocation;
import com.example.eyeballinapp.MapStuff.Graph.MapGraph;
import com.example.eyeballinapp.MapStuff.Graph.MapNode;


public class XmlParser {

    public MapGraph graph;
    private Context context;

    public XmlParser(Context current) {
        this.graph = new MapGraph();
        this.context = current;
    }

    public XmlParser(MapGraph existingGraph) {
        this.graph = existingGraph;
    }

    public MapGraph tempParse() throws Resources.NotFoundException {

        // When I parse the nodes I also add their id/ name to this hashmap for
        // easier access to add edges.
        HashMap<Integer, String> idToName = new HashMap<>();
        InputStream ins;

        try {
            ins = context.getResources().openRawResource(
                    context.getResources().getIdentifier("halfschool", "raw", context.getPackageName()));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ins);
            doc.getDocumentElement().normalize();

            Element element = doc.getDocumentElement();
            NodeList nodes = element.getChildNodes();

            Element node = (Element) nodes.item(1);
            NodeList graphs = node.getChildNodes();

            Node nodeVals = graphs.item(1);
            Node edgeVals = graphs.item(3);

            // only the odd children have value because they're wrapped in
            // openening and closing newline characters probably because of the
            // formatting/ built in parse function
            for (int i = 1; i < nodeVals.getChildNodes().getLength(); i += 2) {

                int id = Integer.parseInt(nodeVals.getChildNodes().item(i).getAttributes().item(2).getNodeValue());
                String name = nodeVals.getChildNodes().item(i).getAttributes().item(3).getNodeValue();

                // Insert into map
                idToName.put(id, name);

                // Create a new CustomLocation object to be inserted into graph
                CustomLocation newLocation = new CustomLocation(
                        Double.valueOf(nodeVals.getChildNodes().item(i).getAttributes().item(0).getNodeValue()),
                        Double.valueOf(nodeVals.getChildNodes().item(i).getAttributes().item(1).getNodeValue()),
                        getFloorNum(id)
                );

                // Create a new MapNode with the values
                MapNode newNode = new MapNode(name,
                        new HashMap<String, Double>(),
                        newLocation,
                        id
                );

                // Insert into the graph to be returned
                this.graph.addNode(newNode);
            }

            // Adding edges to the graph
            for (int i = 1; i < edgeVals.getChildNodes().getLength(); i += 2) {

                // use the hashmap to get the node names
                String source = idToName.get(Integer.parseInt(
                        edgeVals.getChildNodes().item(i).getAttributes().item(0).getNodeValue()));
                String dest = idToName.get(Integer.parseInt(
                        edgeVals.getChildNodes().item(i).getAttributes().item(1).getNodeValue()));

                graph.addEdge(source, dest,
                        Double.parseDouble(edgeVals.getChildNodes().item(i).getAttributes().item(3).getNodeValue())/*,
                        getFloorNum(Integer.parseInt(edgeVals.getChildNodes().item(i).getAttributes().item(5).getNodeValue()))*/); // I removed the floor from the criteria to add an edge. Assuming that you must add all nodes involved before you can add an edge.
            }

            //ins.read();

            // Don't think any of used methods can throw exceptions
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return graph;
    }

    // Method to get the proper floor number from the item's id
    int getFloorNum(int nodeId) {

        while (nodeId >= 10) {
            nodeId /= 10;
        }
        return nodeId;
    }
}
