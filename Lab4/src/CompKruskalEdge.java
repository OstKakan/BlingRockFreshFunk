import java.util.Comparator;
import java.util.DoubleSummaryStatistics;

public class CompKruskalEdge<E extends Edge> implements Comparator<E> {

    @Override
    public int compare(E o1, E o2) {
        return ((Double)o1.getWeight()).compareTo(o2.getWeight());
    }
}
