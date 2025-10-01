import java.util.*;
import java.io.*;

class Query implements Comparable<Query> {
    int L, R, idx;
    public Query(int L, int R, int idx) {
        this.L = L;
        this.R = R;
        this.idx = idx;
    }
    
    // Mo's ordering
    public int compareTo(Query other) {
        int block1 = this.L / BLOCK_SIZE;
        int block2 = other.L / BLOCK_SIZE;
        if (block1 != block2)
            return block1 - block2;
        return this.R - other.R;
    }
    
    static int BLOCK_SIZE;
}

public class MosAlgorithm {
    static int[] arr;
    static int[] answer;
    static int[] freq;
    static int distinctCount = 0;
    
    static void add(int x) {
        freq[x]++;
        if (freq[x] == 1) distinctCount++;
    }
    
    static void remove(int x) {
        freq[x]--;
        if (freq[x] == 0) distinctCount--;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        int n = Integer.parseInt(parts[0]);
        int q = Integer.parseInt(parts[1]);
        
        arr = new int[n];
        parts = br.readLine().split(" ");
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(parts[i]);
        
        Query.BLOCK_SIZE = (int)Math.sqrt(n);
        Query[] queries = new Query[q];
        answer = new int[q];
        
        for (int i = 0; i < q; i++) {
            parts = br.readLine().split(" ");
            int L = Integer.parseInt(parts[0]) - 1; // 0-based index
            int R = Integer.parseInt(parts[1]) - 1;
            queries[i] = new Query(L, R, i);
        }
        
        Arrays.sort(queries);
        freq = new int[1000001]; // adjust max value as needed
        
        int currL = 0, currR = -1;
        for (Query query : queries) {
            int L = query.L, R = query.R;
            
            while (currL > L) add(arr[--currL]);
            while (currR < R) add(arr[++currR]);
            while (currL < L) remove(arr[currL++]);
            while (currR > R) remove(arr[currR--]);
            
            answer[query.idx] = distinctCount;
        }
        
        for (int ans : answer) System.out.println(ans);
    }
}
