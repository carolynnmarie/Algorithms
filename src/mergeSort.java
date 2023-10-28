import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class mergeSort {

    static int counter = 0;
        // Merges two subarrays of arr[].
        // First subarray is arr[l..m]
        // Second subarray is arr[m+1..r]
        void merge(int arr[], int l, int m, int r)
        {
            // Find sizes of two subarrays to be merged
            int n1 = m - l + 1;
            int n2 = r - m;

            // Create temp arrays
            int L[] = new int[n1];
            int R[] = new int[n2];

            // Copy data to temp arrays
            for (int i = 0; i < n1; ++i)
                L[i] = arr[l + i];
            for (int j = 0; j < n2; ++j)
                R[j] = arr[m + 1 + j];

            int i = 0;
            int j = 0;

            // Initial index of merged subarray array
            int k = l;
            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                }
                else {
                    arr[k] = R[j];
                    j++;
                    counter++;
                }
                k++;
            }

            // Copy remaining elements of L[] if any
            while (i < n1) {
                arr[k] = L[i];
                i++;
                k++;

            }

            // Copy remaining elements of R[] if any
            while (j < n2) {
                arr[k] = R[j];
                j++;
                k++;
                counter++;
            }
        }

        // Main function that sorts arr[l..r] using
        // merge()
        void sort(int arr[], int l, int r) {
            if (l < r) {
                int m = (l + r) / 2;
                sort(arr, l, m);
                sort(arr, m + 1, r);
                merge(arr, l, m, r);
            }
        }

        static void printArray(int arr[]) {
            int n = arr.length;
            for (int i = 0; i < n; ++i)
                System.out.print(arr[i] + " ");
            System.out.println();
        }

        static long countInversions(int arr[]){
            int n = arr.length;
            long inv_count = 0;
            for (int x = 0; x < n - 1; x++){
                for (int y = x + 1; y < n; y++){
                    if (arr[x] > arr[y]) {
                        inv_count++;
                    }
                }
            }
            return inv_count;
        }

        // Driver method
        public static void main(String args[])
        {
            int[] arr = new int [100000];
            int i = 0;
            try(BufferedReader br = new BufferedReader(new FileReader("src/integerarray.txt"))) {
                for(String numb; (numb = br.readLine()) != null; ) {
                    arr[i] = Integer.parseInt(numb);
                    i++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            int [] arr = {4,6,8,9,3,5,2};
            long invs = countInversions(arr);
            System.out.println("Inversions: " + invs);
//            System.out.println("Given Array");
//            printArray(arr);
//
//            // Calling of Merge Sort
//            mergeSort ob = new mergeSort();
//            ob.sort(arr, 0, arr.length - 1);
//
//            System.out.println("\nSorted array");
//            printArray(arr);
//            System.out.println("\nInversions: " + counter);


        }
}
