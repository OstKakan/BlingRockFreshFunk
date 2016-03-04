import java.util.Comparator;

public class CompDijkstraPath<E extends Edge> implements Comparator<Path<E>> {

    @Override
    public int compare(Path<E> path1, Path<E> path2) {
        Double pathLength1 = 0.0;
        for (E edge : path1.getPath()) {
            pathLength1 += edge.getWeight();
        }

        Double pathLength2 = 0.0;
        for (E edge : path2.getPath()) {
            pathLength2 += edge.getWeight();
        }

        return pathLength1.compareTo(pathLength2);
    }
}