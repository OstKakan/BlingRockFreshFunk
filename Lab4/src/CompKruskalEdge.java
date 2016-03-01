import java.util.Comparator;

public class CompKruskalEdge<E extends Edge> implements Comparator<E> {


    @Override
    public int compare(E o1, E o2) {
        if(o1.getWeight() -  o2.getWeight() > 0){
            return 1;
        }else if(o1.getWeight() -  o2.getWeight() < 0){
            return -1;
        }else return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
