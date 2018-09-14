//Problem 895 in Leetcode.com

/*
Implement FreqStack, a class which simulates the operation of a stack-like data structure.

FreqStack has two functions:
    push(int x), which pushes an integer x onto the stack.
    pop(), which removes and returns the most frequent element in the stack.
        If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.
Examples:
  Input: 
    ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
    [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
    Output: [null,null,null,null,null,null,null,5,7,5,4]
    Explanation:
    After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:

    pop() -> returns 5, as 5 is the most frequent.
    The stack becomes [5,7,5,7,4].

    pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
    The stack becomes [5,7,5,4].

    pop() -> returns 5.
    The stack becomes [5,7,4].

    pop() -> returns 4.
    The stack becomes [5,7].

Notes: 
    Calls to FreqStack.push(int x) will be such that 0 <= x <= 10^9.
    It is guaranteed that FreqStack.pop() won't be called if the stack has zero elements.
    The total number of FreqStack.push calls will not exceed 10000 in a single test case.
    The total number of FreqStack.pop calls will not exceed 10000 in a single test case.
    The total number of FreqStack.push and FreqStack.pop calls will not exceed 150000 across all test cases.
*/

/**
* Solved on : 14/09/2018
* Author: Zeyad Khaled
*/

class FreqStack {
    ArrayList<Stack<Integer>> freqBuckets; //Buckets that contains number based on its frequency
    HashMap<Integer, Integer> map; //Number to its frequency
    
    public FreqStack() {
        freqBuckets = new ArrayList<>();
        freqBuckets.add(null); // Add a zero frequency to start at 1
        map = new HashMap<>();
    }
    
    public void push(int x) {
            map.put( x, map.getOrDefault(x , 0) + 1);
            int bucket = map.get(x);
            
            //bucket doesn't exist
            if ( bucket >= freqBuckets.size()) {
                freqBuckets.add(new Stack<Integer>());
                freqBuckets.get(bucket).push(x); 
            } else 
                freqBuckets.get(bucket).add(x);
    }
    
    public int pop() {
        Stack<Integer> tmp = freqBuckets.get(freqBuckets.size() - 1);
        int item  = tmp.pop();
        if ( tmp.isEmpty()) 
            freqBuckets.remove(freqBuckets.size() - 1);
        
        map.put( item , map.get(item) - 1);
        return item;
    }
}


