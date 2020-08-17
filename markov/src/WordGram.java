import java.util.*;

public class WordGram {
	
	private String[] myWords;
    private int myHash;
    private StringBuilder hashMe;
    
	public WordGram(String[] words, int index, int size){
		
		myWords = new String[size];
		
		int count = 0; 
		for (int i = index; i < index + size; i++){
			myWords[count] = words[i];
			count++;
		}
	}
	
	public int hashCode() {

		hashMe = new StringBuilder();
		
		for(int i=0; i<myWords.length; i++) {
			hashMe.append(myWords[i]+i);
		}
		
		myHash = hashMe.toString().hashCode();
		
		return myHash;
		
	}
	
	public int compareTo(WordGram otherGram) {
		
		if (this.equals(otherGram)) return 0;
		
		String[] otherWords = otherGram.myWords;
		
		int shortLen = myWords.length;
		if (myWords.length != otherWords.length) {
			shortLen = Math.min(myWords.length, otherWords.length);
		}

		for (int i=0; i<shortLen; i++) {
			int value = myWords[i].compareTo(otherWords[i]);
			if (value == 0) continue;
			return value;
		}
		
		if (myWords.length > otherWords.length) return 1;
		else if (myWords.length < otherWords.length) return -1;
		else return 0;
		
	}
	
	public String toString() {
		
		String print = "{";

		int len = myWords.length;
		String word;
		
		for (int i=0; i<len; i++) {
			
			word = myWords[i];
			print += word + " ";
		}
		print = print.substring(0, print.length()-1);
		print += "}";
		return print;
		
		
	}
	
	public boolean equals(Object other) {
		
		if (! (other instanceof WordGram)) return false;
		
		WordGram otherGram = (WordGram) other;
		
		if (this.hashCode() == otherGram.hashCode()) {
			return true;
		}
		
		return false;
		
	}
	
	public int length() {
		return myWords.length;
	}
	
	public WordGram shiftAdd(String last) {
		
		int len = this.length();
		
		String[] newWords = new String[len];
		
		for (int i=1; i<len; i++) {
			newWords[i-1] = myWords[i];
		}
		
		newWords[len-1] = last;
		
		myWords = newWords;
		
		return this;
		
	}
	
}
