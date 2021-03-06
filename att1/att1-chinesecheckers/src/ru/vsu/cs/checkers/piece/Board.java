package ru.vsu.cs.checkers.piece;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.service.GraphBuilder;
import ru.vsu.cs.checkers.structures.graph.*;
import ru.vsu.cs.checkers.utils.GameUtils;

public class Board {

    private static final Logger log = LoggerFactory.getLogger(Board.class);

    private Graph<Checker> graph;

    public Board() {
        graph = new Graph<>();
        GraphBuilder gb = new GraphBuilder(graph);
        gb.build();
        log.info("Board done.");
}

    public void put(int place, Checker checker) {
        graph.getVertex(place).setData(checker);
    }

    public void clear(int place) {
        graph.removeData(place);
    }

    public Checker getChecker(int place) {
        return graph.getVertex(place).getData();
    }

    public boolean isConnected(int place1, int place2) {
        return graph.isAdj(place1, place2) && graph.getVertex(place2).getData() == null;
    }

    public boolean isConnectedAcrossOne(int place1, int place2) {
        try {
            GraphVertex<Checker> checker1 = graph.getVertex(place1);
            GraphVertex<Checker> checker2 = graph.getVertex(place2);
            for (int i = 0; i < 6; i++) {
                GraphVertex<Checker> connected1 = checker1.connectedOn(i);
                GraphVertex<Checker> connected2 = checker2.connectedOn(GameUtils.getOppositeDirection(i));
                if (connected1 != null && connected2 != null && connected1.getData() != null && connected1.equals(connected2)) {
                    return true;
                }
            }
        } catch (GraphException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
