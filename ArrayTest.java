import java.util.Arrays;

public class ArrayTest {


    String[] arr = {"ABC", "DEF", "XYZ"};

    public static void changeArray(String[] alocalRef, ArrayTest aObject) {
       
        System.out.println("before: ");
        System.out.println("\t" + Arrays.toString(alocalRef));
        System.out.println("\t" + System.identityHashCode(alocalRef));
        alocalRef[2] = "GHI";
        System.out.println("after: ");
        System.out.println("\t" + Arrays.toString(alocalRef));
        System.out.println("\t" + System.identityHashCode(alocalRef));
       
       
        System.out.println("before: ");
        System.out.println("\t" + Arrays.toString(aObject.arr));
        System.out.println("\t" + System.identityHashCode(aObject.arr));
        alocalRef[2] = "GHI";
        System.out.println("after: ");
        System.out.println("\t" + Arrays.toString(aObject.arr));
        System.out.println("\t" + System.identityHashCode(aObject.arr));



    }
    public static void main(String[] args) {


        ArrayTest test1 = new ArrayTest();
        System.out.println("Before  code  = " + System.identityHashCode(test1));
        System.out.println("Before code = " + System.identityHashCode(test1.arr));

        
        test1.changeArray(test1.arr, test1);

        System.out.println("After arr = " + System.identityHashCode(test1));
        System.out.println("After arr = " + System.identityHashCode(test1.arr));





        

    }

    
}
