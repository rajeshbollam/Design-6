//The idea here is to use hashset and all numbers for constant time for check and release
//We also additionally maintain a queue which contains all available numbers for constant time get() operation
//Time Complexity: O(1) for get(), check(), release() operations
//Space complexity: O(n) for hashset and queue
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class PhoneDirectory{
    private HashSet<Integer> set; //add all numbers first for constant check and release
    private Queue<Integer> q; //for maintaining available numbers

    public PhoneDirectory(int maxNumbers){
        this.set = new HashSet<>();
        this.q = new LinkedList<>();
        for(int i = 0; i< maxNumbers; i++){
            set.add(i);
            q.add(i);
        }
        
    }

    public int get(){
        if(q.isEmpty()) return -1;
        int popped = q.poll();
        set.remove(popped);
        return popped;
    }

    public boolean check(int number){
        return set.contains(number);
    
    }

    public void release(int number){
        if(set.contains(number)) return; //if number
        set.add(number);
        q.add(number);
    }
}