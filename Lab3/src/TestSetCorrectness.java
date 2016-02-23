import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class TestSetCorrectness {
    public static void main(String[] args){

        int n1 = Integer.parseInt(args[0]);
        int n2 = Integer.parseInt(args[1]);
        int n3 = Integer.parseInt(args[2]);
        int n4 = Integer.parseInt(args[3]);

        SimpleSet<Integer> set = null;
        Set<Integer> javaSet;

        int interval = n4;
        Random random = new Random();

        restartsLoop:
        for(int restarts = n2 ; restarts > 0 ; restarts--){
            if(n1 == 1){
                set = new SortedLinkedListSet<Integer>();
            }else if(n1 == 2){
                set = new SplayTreeSet<Integer>();
            }
            javaSet = new TreeSet<Integer>();

            boolean testSuccessful;
            String failedTest;
            for(int operationsPerTest = n3 ; operationsPerTest > 0 ; operationsPerTest--){

                int methodChooser = random.nextInt(4);
                int randomInt = random.nextInt(interval);

                if(methodChooser == 0){
                    testSuccessful = set.add(randomInt) == javaSet.add(randomInt);
                    failedTest = "add";
                }else if(methodChooser == 1){
                    testSuccessful = set.contains(randomInt) == javaSet.contains(randomInt);
                    failedTest = "contains";
                }else if(methodChooser == 2){
                    testSuccessful = set.remove(randomInt) == javaSet.remove(randomInt);
                    failedTest = "remove";
                }else{
                    testSuccessful = set.size() == javaSet.size();
                    failedTest = "size";
                }

                if (!testSuccessful) {
                    System.out.println("Test " + failedTest + "failed! There was a bug in the code. :(");
                    break restartsLoop;
                }
            }
        }
    }
}
