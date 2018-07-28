import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class SquareWords {
	
	
	static HashMap <Integer, ArrayList<String> > countToWords;
	static HashMap <String, ArrayList<String> > prefixToWords;
	
	/**
	 * Fill countToWords dictionary using a text file
	 */
	public static void fillDictionary() {
		countToWords = new HashMap<>();
		FileReader in = null;
		try {
			in = new FileReader("words.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner input = new Scanner(in);
		while((input.hasNext())) {
			String word = input.next();
			System.out.println(word);
			if ( countToWords.containsKey(word.length())) {
				ArrayList<String> temp = countToWords.get(word.length());
				temp.add(word);
				countToWords.put(word.length(), temp);
			} else {
				ArrayList<String> temp = new ArrayList<>();
				temp.add(word);
				countToWords.put(word.length(), temp);
			}
        }
	}
	
	/**
	 * Fill prefixToWords dictionary using a list
	 */
	public static HashMap<String, ArrayList<String>> fillPrefix(ArrayList<String> list) {
		prefixToWords = new HashMap<>();
		
		for ( int i = 0; i < list.get(0).length(); i++) {
			for ( String s : list) {
				String minStr = s.substring(0, i + 1);
				if ( prefixToWords.containsKey(minStr)) {
					ArrayList<String> temp = prefixToWords.get(minStr);
					temp.add(s);
					prefixToWords.put(minStr, temp);
				} else {
					ArrayList<String> temp = new ArrayList<>();
					temp.add(s);
					prefixToWords.put(minStr, temp);
				}
			}
		}
		return prefixToWords;
	}
	
	
	public static boolean isWordSquare( String[] words) {
		for ( int i = 0 ; i < words.length; i++) {
			StringBuilder str = new StringBuilder();
			for ( int j = 0; j < words.length; j++) {
				str.append(words[j].charAt(i));
			}
			if ( !str.toString().equals(words[i])) return false;
		}
		return true;
	}
	
	/**
	 * A BFS Search algorithm that used a queue and arraylist
	 */
	public static ArrayList<ArrayList<String>> findLists(ArrayList<String> list) {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		Queue < ArrayList<String> >  queue = new LinkedList <ArrayList<String>> (); 
		
		for ( String s : list) {
			ArrayList<String> tmpList = new ArrayList<>();
			tmpList.add(s);
			queue.add(tmpList);
		}
		
		while ( !queue.isEmpty() ) {
			if ( result.size() >= 20) 
				return result;
			ArrayList<String> queueList = queue.poll();
			System.out.println(queueList.toString());
			int currSize = queueList.size();
			
			String match = "";
			for ( int i = 0; i < currSize; i++)  
				match += queueList.get(i).charAt(currSize);
			
			for (String s : list) {
					if ( s.substring(0, currSize).equals(match)) {
						queueList.add(s);
					
						if ( queueList.size() == list.get(0).length())  
							result.add( new ArrayList<>(queueList));
						
						 else 
							queue.add( new ArrayList<>(queueList));	
						
						queueList.remove(queueList.size() - 1);
					}
			}
			
		}
		return result;
	}
	
	/**
	 * A faster BFS Search algorithm that uses an additional dictionary
	 */
	public static ArrayList<ArrayList<String>> findListsTwo(ArrayList<String> list) {
		HashMap<String, ArrayList<String>> prefixes = fillPrefix(list);
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		Queue < ArrayList<String> >  queue = new LinkedList <ArrayList<String>> (); 
		HashSet<String> visited = new HashSet<>();
		
		for ( String s : list) {
			ArrayList<String> tmpList = new ArrayList<>();
			tmpList.add(s);
			queue.add(tmpList);
		}
		
		while ( !queue.isEmpty() ) {
			if ( result.size() >= 20) 
				return result;
			
			ArrayList<String> queueList = queue.poll();
			System.out.println(queueList.toString());
			int currSize = queueList.size();
			
			String match = "";
			for ( int i = 0; i < currSize; i++)  
				match += queueList.get(i).charAt(currSize);
			
			if ( prefixes.containsKey(match)) {
				for ( String s : prefixes.get(match)) {
					if ( visited.contains(s)) continue;
					queueList.add(s);
					if ( queueList.size() == list.get(0).length())  
						result.add( new ArrayList<>(queueList));
					
					 else 
						queue.add( new ArrayList<>(queueList));	
					
					queueList.remove(queueList.size() - 1);
					
				}
			}
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		fillDictionary();
		ArrayList<String> list = new ArrayList<>();
		ArrayList<ArrayList<String>> result = findListsTwo(countToWords.get(5));
		
		System.out.println(result.toString());
	
	}

}
