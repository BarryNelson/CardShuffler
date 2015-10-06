/**
 * 
 * @author Barry Nelson
 * 
 */
public class Card {

	static private int sequence;

	private final static String[] suitName = { "Spades", "Hearts", "Diamonds", "Clubs" };
	private final static String[] shortSuitName = { "s", "h", "d", "c" };
	private final static String[] faceName = { "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace" };
	private final static String[] shortFaceName = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

	private int suitValue;
	private int faceValue;
	private int seqNum;

	public Card(int s, int f) {
		suitValue = s;
		faceValue = f;
		seqNum = Card.sequence++;
	}
	
	public String getSuitName(){
		return suitName[suitValue];
	}

	public String getFaceName(){
		return faceName[faceValue];
	}

	public String getCardName(){
		return faceName[faceValue]+" of "+suitName[suitValue];
	}

	public String getShortSuitName(){
		return shortSuitName[suitValue];
	}

	public String getShortFaceName(){
		return shortFaceName[faceValue];
	}

	public String getShortCardName(){
		return shortFaceName[faceValue]+" of "+shortSuitName[suitValue];
	}

	public int getSuitValue() {
		return suitValue;
	}

	public int getFaceValue() {
		return faceValue;
	}

	public int getSeqNum() {
		return seqNum;
	}

	@Override
	public String toString() {
		return Integer.toString(seqNum);
	}
	
}
