import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Kristoffer on 2016-02-12.
 */
public class TestSetCorrectness {

    private static SimpleSet<Integer> set;
    private static Set<Integer> javaSet;
    private static int restarts, operationsPerTest, interval, n1, n2, n3, n4;
    private static int removeFail, removeSuccess, addFail, addSuccess, containsFail, containSuccess, sizeFail, sizeSuccess;

    public static void main(String[] args){

        n1 = Integer.parseInt(args[0]);
        n2 = Integer.parseInt(args[1]);
        n3 = Integer.parseInt(args[2]);
        n4 = Integer.parseInt(args[3]);


        interval = n4;
        Random random = new Random();

        restartsLoop:
        for(restarts = n2 ; restarts > 0 ; restarts--){
            if(n1 == 1){
                set = new SortedLinkedListSet<Integer>();
            }else if(n1 == 2){
                set = new SplayTreeSet<Integer>();
            }
            javaSet = new TreeSet<Integer>();

            boolean testSuccessful;
            String failedTest;
            for(operationsPerTest = n3 ; operationsPerTest > 0 ; operationsPerTest--){

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

                //set.add(random.nextInt(interval-1));
            }
            //System.out.println("set:\t\t" + set.toString());
            //System.out.println("javaSet:\t" + javaSet.toString());
        }
        System.out.println("Adds successfull: " + addSuccess);
        System.out.println("Adds failure: " + addFail);
        System.out.println("Removes successfull: " + removeSuccess);
        System.out.println("Remove fails: " + removeFail);
        System.out.println("Contains successfull: " + containSuccess);
        System.out.println("Contain fails: " + containsFail);
        System.out.println("Size successfull: " + sizeSuccess);
        System.out.println("Size fails: " + sizeFail);
        /*System.out.println("set:\t\t" + set.toString());
        System.out.println("javaSet:\t" + javaSet.toString());*/
    }
}
