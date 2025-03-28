//The approach here is to first store all sentences and their scores in a map for efficient query
//To search for each character entered by user constant time, we make a trie out of all the sentences we have and at each trienode, we maintain only the top3 hottest sentences so that when we know a character is present at a trienode, we can just return the top 3 hottest list
// if we don't have such character in our tree, we return an empty list
// Time complexity: O(1) for inserting and searching
//Space complexity: O(Nl) for hashmap and trie where N is number of sentences in given list and l is the average length of a sentence 
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
class AutocompleteSystem {
    class TrieNode{
        HashMap<Character, TrieNode> children; //Instead of an array, we have a trienode implemented in hashmap where key is the character and it's value is a trienode(children)
        List<String> li; //here list contains only top 3 sentences which are hottest 
        public TrieNode(){
            this.children = new HashMap<>(); //because we can have # and ' ' also
            this.li = new ArrayList<>();
        }
    }
    
    private void insert(String sentence){
        TrieNode curr = root;
        for(int i = 0; i<sentence.length(); i++){
            char ch = sentence.charAt(i);
            if(!curr.children.containsKey(ch)){
                curr.children.put(ch, new TrieNode());
            }
            curr.children.get(ch);//going to it's baby trienode
            if(!curr.li.contains(sentence)){ //here we are only adding to list if that sentence doesn't already present in list
                curr.li.add(sentence);
            }

            List<String> top3 = curr.li;
            Collections.sort(top3, (a,b) -> {
                int cnta = map.get(a);
                int cntb =  map.get(b);
                if(cnta == cntb){
                    return a.compareTo(b);
                }
                return cntb-cnta;            
            });
            if(top3.size() > 3){
                top3.remove(top3.size() - 1);
            }

        }
    }
    private List<String> search(String input){
        TrieNode curr = root;
        for(int i = 0; i<input.length(); i++){
            char ch = input.charAt(i);
            // if(curr.children[ch-' '] == null){
            //     return new ArrayList<>(); //no prefix exists in trie that means no sentences with that input exists, so return empty list
            // }
            if(!curr.children.containsKey(ch)){
                return new ArrayList<>();
            }
            curr.children.get(ch);
        }
        return curr.li; //if we find a prefix, we send list associated with that trienode back to put in min-heap and give top 3 hottest ones
    }
    private HashMap<String, Integer> map;
    private String inp;
    private TrieNode root;
    public AutocompleteSystem(String[] sentences, int[] times){
        this.root = new TrieNode();
        this.map = new HashMap<>();
        this.inp = "";
        for(int i = 0; i<sentences.length; i++){
            int cnt = times[i];
            String str = sentences[i];
            map.put(str, map.getOrDefault(str, 0) + cnt);
            insert(str); //This line is critical!! we should put in map first, then insert //here we are inserting every time, because it takes care of the ordering of sentences among top 3
        }
    
    }
    public List<String> input(char c){
        if(c == '#'){
            map.put(inp, map.getOrDefault(inp, 0) +1); //putting the string before entering # into the cache(hashmap)
            insert(inp);//this line is critical!!!! this should come after putting in map because if we put before, the insert function will sort in which custom comparator references map again. so first put in map, then insert
            this.inp = ""; //resetting the current input string to empty string
            return new ArrayList<>();        
        }
        this.inp = this.inp + 'c';
        //search if any of strings in map start with input string
        return search(inp); //because each list at trienode only has top 3 hottest
    
    }
}

