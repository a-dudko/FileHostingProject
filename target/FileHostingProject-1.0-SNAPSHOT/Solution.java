import java.io.*;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Stack;

public class Solution {
    private static int[][] a;

    private static ArrayList<Integer> [] edgesList;

    private static Stack<Integer> stack;

    public static void main (String[] args) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader("input.txt"));
        PrintWriter outputStream = new PrintWriter(new FileOutputStream("output.txt"));

        String s = inputStream.readLine();
        s = s.trim();

        int n = Integer.parseInt(s);

        a = new int[2*n+2][2*n+2];

        edgesList = new ArrayList[2*n+2];
        for (int i = 0; i<n+1; i++) {
            a[i][0] = 1;
            edgesList[i] = new ArrayList<>();
            edgesList[n+i] = new ArrayList<>();

            a[i][n+i] = 1;
            edgesList[i].add(n+i);
            edgesList[n+i].add(i);
        }

        a[2*n+1][0] = 1;
        edgesList[2*n+1] = new ArrayList<>();

        s = inputStream.readLine();
        s = s.trim();

        int m = Integer.parseInt(s);

        for (int i = 0; i < m; i++) {
            s = inputStream.readLine();
            String[] numbers = s.split(" ");
            int j = 0;
            while ("".equals(numbers[j])) {
                j++;
            }
            int firstTown = Integer.parseInt(numbers[j]);
            j++;
            while ("".equals(numbers[j])) {
                j++;
            }

            int secondTown = Integer.parseInt(numbers[j]);

            a[firstTown + n][secondTown] = 1;
            a[secondTown + n][firstTown] = 1;
            edgesList[firstTown + n].add(secondTown);
            edgesList[firstTown].add(secondTown + n);
            edgesList[secondTown].add(firstTown + n);
            edgesList[secondTown + n].add(firstTown);
        }

        s = inputStream.readLine();
        String[] numbers = s.split(" ");
        int j = 0;
        while ("".equals(numbers[j])) {
            j++;
        }
        int firstTown = Integer.parseInt(numbers[j]);
        j++;
        while ("".equals(numbers[j])) {
            j++;
        }

        int secondTown = Integer.parseInt(numbers[j]);
        j++;
        while ("".equals(numbers[j])) {
            j++;
        }
        int thirdTown = Integer.parseInt(numbers[j]);

        a[2*n+1][firstTown] = 1;
        a[2*n+1][secondTown] = 1;

        edgesList[2*n+1].add(firstTown);
        edgesList[2*n+1].add(secondTown);

        boolean b = dfs(2*n+1,thirdTown,n);
        b = dfs(2*n+1,thirdTown,n);
        int count = 0;
        if (a[2*n+1][firstTown] == 2 && a[2*n+1][secondTown] == 2) {
                count = 2;
        }

        if (count == 2) {
            outputStream.println("Yes");
        }
        else {
            outputStream.println("No");
        }
        outputStream.flush();
        outputStream.close();
    }



    private static boolean dfs(int s, int t, int n) {
        stack = new Stack<>();
        int met[] = new int[2*n+2];
        int currentPoint = s;
        met[currentPoint] = 1;
        stack.push(currentPoint);

        while (currentPoint != t) {
            boolean b = true;

            for (Integer i : edgesList[currentPoint]) {
                if ((a[i][currentPoint] == 2 && met[i] != 1) || (a[currentPoint][i] == 1 && met[i] != 1)) {
                    a[currentPoint][0] = i + 1;
                    stack.push(i);
                    met[i] = 1;
                    currentPoint = i;
                    b = false;
                    break;
                }
            }

            if (b) {
                a[currentPoint][0] = 1;
                stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                currentPoint = stack.peek();
            }
        }

        if (currentPoint != t) {
            return false;
        }

        stack.pop();

        while (!stack.isEmpty()) {
            int previousPoint = stack.pop();
            if (a[previousPoint][currentPoint] == 0) {
                a[currentPoint][previousPoint] = 1;

            }
            else {
                a[previousPoint][currentPoint] = 2;

            }
            currentPoint = previousPoint;
            a[currentPoint][0] = 1;
        }

        return true;
    }
}

