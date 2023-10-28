import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSortTakeTwo {

    int counter;

        // A utility function to swap two elements
        static void swap(int[] arr, int i, int j)
        {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        int partition(int[] arr, int low, int high) {
            counter  += (high-low);
            //array length of high-low+1
            int med;
            if((high-low+1) %2 == 0){
                med = (high-low)/2 + low;
            } else {
                med = (high-low+1)/2 + low;
            }
            int m = medianOfThree(arr[high], arr[low], arr[med]);
            int median = 0;
            for(int x = low; x <= high; x++){
                if(m == arr[x]) median = x;
            }

            //int median = ThreadLocalRandom.current().nextInt(low, high+1);
            swap(arr, low, median);

            int pivot = arr[low];
            int i = low+1;
            for (int j = low +1; j <= high; j++) {
                if (arr[j] < pivot) {
                    swap(arr, i, j);
                    i++;
                }
            }
            if(i>0) swap(arr, i-1, low);
            return i-1;
        }

         void quickSort(int[] arr, int low, int high)
        {
            if (low < high) {
                int pi = partition(arr, low, high);
                quickSort(arr, low, pi-1);
                quickSort(arr, pi+1, high);
            }
        }

    int medianOfThree(int a, int b, int c){
        if ((a > b) ^ (a > c))
            return a;
        else if ((b < a) ^ (b < c))
            return b;
        else
            return c;
    }

        int getCounter(){
            return counter;
        }

        // Driver Code
        public static void main(String[] args) {
            int[] array = new int [10000];
            int i = 0;
            try(BufferedReader br = new BufferedReader(new FileReader("src/quickSortArray.txt"))) {
                for(String numb; (numb = br.readLine()) != null; ) {
                    array[i] = Integer.parseInt(numb);
                    i++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            QuickSortTakeTwo quickSort = new QuickSortTakeTwo();
            quickSort.quickSort(array, 0, array.length - 1);
            System.out.println("First: " + array[0]+ " Last: " + array[array.length-1]);
            System.out.println("counter:" + quickSort.getCounter());
        }

}
