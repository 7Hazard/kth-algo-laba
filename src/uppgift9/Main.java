package uppgift9;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws Exception {
        var rand = ThreadLocalRandom.current();
        var numbers = new int[1_000_000];
        for(int i = 0; i < 1_000_000; i++)
        {
            numbers[i] = rand.nextInt(1_000_000)+1;
        }

        // sort
        var max = 0;
        for (int number : numbers) {
            if(max < number) max = number;
        }
        System.out.println("Max: "+max);

        var start = System.currentTimeMillis();
        var n = numbers.length;
        // radix sort
        for (int exp = 1; (max / exp) > 0; exp *= 10)
        {
            // count sort

            var output = new int[n];
            int[] count = new int[10];

            for (int i = 0; i < n; i++)
                count[(numbers[i] / exp) % 10]++;

            for (int i = 1; i < 10; i++)
                count[i] += count[i - 1];

            for (int i = n - 1; i >= 0; i--) {
                output[count[(numbers[i] / exp) % 10] - 1] = numbers[i];
                count[(numbers[i] / exp) % 10]--;
            }

            for (int i = 0; i < n; i++)
                numbers[i] = output[i];
        }
        var end = System.currentTimeMillis();
        System.out.println("Sorted in: "+(end-start));

        // validate
        var last = 0;
        for (int number : numbers) {
            if(last < number) // ok
                last = number;
            else if(last > number) throw new Exception("SHIT");
        }
    }
}
