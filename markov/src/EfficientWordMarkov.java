import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EfficientWordMarkov implements MarkovInterface<WordGram> {
	
	private String myText;
	private String[] words;
	private int myOrder;
	private Random myRandom;
	private Map<WordGram, ArrayList<String>> wordMap;
	
	private static String PSEUDO_EOS = "";
	private static long RANDOM_SEED = 1234;

	public EfficientWordMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		wordMap = new HashMap();
		myOrder = order;
	}

	@Override
	public void setTraining(String text) {
		myText = text;
		words = myText.split("\\s+");
		readText(); // updates the map
	}
	
	public void readText() {
		
//		WordGram kGram = new WordGram(words, 0, myOrder);
		ArrayList<String> follows;
		String follow;
		WordGram kGram = (null);
		
		int index = 0;
		while (index < words.length-myOrder) {
			
			kGram = new WordGram(words, index, myOrder);
			follow = words[index+myOrder];
			
			if (!wordMap.containsKey(kGram)) {
				follows = new ArrayList();
				follows.add(follow);
				wordMap.put(kGram, follows);
			}
			
			else {
				follows = wordMap.get(kGram);
				follows.add(follow);
				wordMap.put(kGram, follows);
			}
			
			index++;
			
		}
		
		kGram = new WordGram(words, index, myOrder);
		
		if (! wordMap.containsKey(kGram)) {
			follows = new ArrayList();
			follows.add(PSEUDO_EOS);
			wordMap.put(kGram, follows);
		}
		
		else {
			follows = wordMap.get(kGram);
			follows.add(PSEUDO_EOS);
			wordMap.put(kGram, follows);
		}
		
	}

	@Override
	public String getRandomText(int length) {
		
		StringBuilder sb = new StringBuilder();
		
		int index = myRandom.nextInt(words.length - myOrder);

//		String current = myText.substring(index, index + myOrder);
		WordGram current = new WordGram(words, index, myOrder);
		
		//System.out.printf("first random %d for '%s'\n",index,current);
		
		sb.append(current + " ");
		
		for(int k=0; k < length-myOrder; k++){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
//			System.out.println(nextItem);
			if (nextItem.equals(PSEUDO_EOS)) {
//				System.out.println("PSEUDO");
				break;
			}
			sb.append(nextItem + " ");
			current = current.shiftAdd(nextItem);
		}
		return sb.toString();
	}

	@Override
	public ArrayList<String> getFollows(WordGram key) {
		return wordMap.get(key);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return myOrder;
	}

	@Override
	public void setSeed(long seed) {
		RANDOM_SEED = seed;
		myRandom = new Random(RANDOM_SEED);	
		
	}

}
