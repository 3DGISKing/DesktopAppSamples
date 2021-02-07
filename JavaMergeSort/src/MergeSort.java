class MergeSort<AnyType extends Comparable<? super AnyType>> extends Thread {
    private AnyType[] internal;

    MergeSort(AnyType[] arr) {
        internal = arr;
    }

    AnyType[] getInternal() {
        return internal;
    }

    private AnyType[] leftHalf(AnyType[] array) {
        int size1 = array.length / 2;

        @SuppressWarnings("unchecked")
        AnyType[] left = (AnyType[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size1);

        System.arraycopy(array, 0, left, 0, size1);

        return left;
    }

    private AnyType[] rightHalf(AnyType[] array) {
        int size1 = array.length / 2;
        int size2 = array.length - size1;

        @SuppressWarnings("unchecked")
        AnyType[] right = (AnyType[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size2);

        System.arraycopy(array, size1, right, 0, size2);

        return right;
    }

    private void merge(AnyType[] result, AnyType[] left, AnyType[] right) {
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && left[i1].compareTo(right[i2]) < 0)) {
                result[i] = left[i1];
                i1++;
            } else {
                result[i] = right[i2];
                i2++;
            }
        }
    }

    private void sort(AnyType[] array) {
        if (array.length > 1) {
            AnyType[] left = leftHalf(array);
            AnyType[] right = rightHalf(array);

            sort(left);
            sort(right);

            merge(array, left, right);
        }
    }

    public void run() {
        sort(internal);
    }
}
