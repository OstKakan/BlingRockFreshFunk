import java.util.Comparator;

public class CompDijkstraPath<E extends Edge> implements Comparator<Path<E>> {

    @Override
    public int compare(Path<E> path1, Path<E> path2) {
        return ((Double)path1.getDistance()).compareTo(path2.getDistance());
    }
}