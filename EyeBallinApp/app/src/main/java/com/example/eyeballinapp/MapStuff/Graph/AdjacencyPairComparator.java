package com.example.eyeballinapp.MapStuff.Graph;

import java.util.Comparator;

public class AdjacencyPairComparator implements Comparator<AdjacencyPair> {
        @Override
        public int compare(AdjacencyPair p1, AdjacencyPair p2) {
            //sort using distance values
            double key1 = p1.getDistance();
            double key2 = p2.getDistance();
            if (key2 > key1)
                return -1;
            else if (key1 < key1)
                return 1;
            else return 0;
        }
}
