import java.util.*;

public class EfficientMarkov implements MarkovInterface<String> {
	
	private String myText;
	private int myOrder;
	private Random myRandom;
	private Map<String, ArrayList<String>> myWords;
	
	private static String PSEUDO_EOS = "";
	private static long RANDOM_SEED = 1234;

	public EfficientMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myWords = new HashMap();
		myOrder = order;
	}
	
	public void setTraining(String text) {
		myText = text;
		readText();
	}
	
	/*
	 * Updates the map with the k grams as keys and what follows as a list of chars.
	 */
	public void readText() {
		
		String nextChar;
		ArrayList<String> follows; // chars following the k gram
		String word;
		
		int start = 0;
		int end = 0;
		
		while (start < myText.length() - myOrder) {
			
			end = start + myOrder;
			
			word = myText.substring(start, end);
			nextChar = myText.substring(end, end+1);
			
			if (! myWords.containsKey(word)) {
				follows = new ArrayList();
				follows.add(nextChar);
				myWords.put(word, follows);
			}
			
			else {
				follows = myWords.get(word);
				follows.add(nextChar);
				myWords.put(word, follows);
			}
			start++;
		}
		
		word = myText.substring(start, start+1);
		
		if (! myWords.containsKey(word)) {
			follows = new ArrayList();
			follows.add(PSEUDO_EOS);
			myWords.put(word, follows);
		}
		
		else {
			follows = myWords.get(word);
			follows.add(PSEUDO_EOS);
			myWords.put(word, follows);
		}
		
	}
	
	public ArrayList<String> getFollows(String word) {
		return myWords.get(word);
	}

	public String getRandomText(int length) {
		
		StringBuilder sb = new StringBuilder();
		
		int index = myRandom.nextInt(myText.length() - myOrder);

		String current = myText.substring(index, index + myOrder);
		
		//System.out.printf("first random %d for '%s'\n",index,current);
		
		sb.append(current);
		
		for(int k=0; k < length-myOrder; k++){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
//				System.out.println("PSEUDO");
				break;
			}
			sb.append(nextItem);
			current = current.substring(1) + nextItem;
		}
		return sb.toString();
	}

	@Override
	public int getOrder() {
		return myOrder;
	}

	@Override
	public void setSeed(long seed) {
		RANDOM_SEED = seed;
		myRandom = new Random(RANDOM_SEED);	
		
	}
	
}