import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    int counter = 0;

    int partition(int arr[], int low, int high) {
        int med = (low + high)/2 + low;
        int median = medianOfThree(low,med,high);
        int swap = arr[low];
        arr[low] = arr[high];
        arr[high] = swap;
        int pivot = arr[low];
        counter += arr.length-1;
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return i+1;
    }

    int part(int arr[], int low, int high){
        int pivot = arr[low];
        int i = low+1;
        for(int j = low+1; j<high; j++){
            if(arr[j]<pivot){
                swap(arr, i, j);
                i++;
            }
        }
        if(i>0){
            swap(arr, arr[i-1],arr[low]);
        }
        return i-1;
    }

    void sort(int arr[], int low, int high) {
        if (low < high) {
            int pi = part(arr, low, high);
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
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

    void swap(int[]arr, int i, int j){
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }


    void printCounter(){
        System.out.println(counter);
    }

    public static void main(String[]args){
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
        QuickSort q = new QuickSort();
        q.sort(array,0,array.length-1);
        //q.quickSort(array);
        System.out.println("First " + array[0]+ " Last " + array[array.length-1]);
        q.printCounter();

    }
}
