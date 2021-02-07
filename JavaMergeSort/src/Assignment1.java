import java.util.Random;

public class Assignment1 {
    static private final int ARRAY_LENGTH = 100;
    static private final int MAX_VALUE   = 10000;

    public static void main(String[] args) throws InterruptedException{
        Random rand = new Random();
        Integer[] original = new Integer[ARRAY_LENGTH];

        for (int i = 0; i < original.length; i++)
            original[i] = rand.nextInt(MAX_VALUE);

        System.out.println("Before sort\n");

        for (int i = 0; i < original.length; i++)
            System.out.println(i + 1 + " " + original[i]);

        Integer[] subArray1 = new Integer[original.length / 2];
        Integer[] subArray2 = new Integer[original.length - original.length / 2];

        System.arraycopy(original, 0, subArray1, 0, original.length / 2);
        System.arraycopy(original, original.length/2, subArray2, 0, original.length - original.length / 2);

        @SuppressWarnings("unchecked")
        MergeSort thread1 = new MergeSort(subArray1);

        @SuppressWarnings("unchecked")
        MergeSort thread2 = new MergeSort(subArray2);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        //noinspection unchecked
        combineAndPrintResult(thread1.getInternal(), thread2.getInternal());
    }

    private static<AnyType extends Comparable<? super AnyType>> void combineAndPrintResult(AnyType[] a, AnyType[] b) {
        @SuppressWarnings("unchecked")
        AnyType[] result = (AnyType[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), a.length + b.length);

        int i = 0;
        int j = 0;
        int r = 0;

        while (i < a.length && j < b.length) {
            if ( a[i].compareTo(b[j]) < 0 || a[i].compareTo(b[j]) == 0) {
                result[r] = a[i];
                i++;
                r++;
            } else {
                result[r]=b[j];
                j++;
                r++;
            }

            if (i == a.length) {
                while (j < b.length) {
                    result[r] = b[j];
                    r++;
                    j++;
                }
            }

            if (j == b.length) {
                while (i < a.length) {
                    result[r] = a[i];
                    r++;
                    i++;
                }
            }
        }

        System.out.println("\nAfter sort\n");

        for (i = 0; i < result.length; i++)
            System.out.println(i + 1 + " " + result[i]);
    }
}
