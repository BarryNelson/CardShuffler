
public class Card {

	static private int sequence;
	
	String[] suitName = {
			 "Spades"
			,"Hearts"
			,"Diamonds"
			,"Clubs"
		};
	String[] shortSuitName = {
			 "s"
			,"h"
			,"d"
			,"c"
		};
	
	String[] faceName = {
			  "Two"
			, "Three"
			, "Four"
			, "Five"
			, "Six"
			, "Seven"
			, "Eight"
			, "Nine"
			, "Ten"
			, "Jack"
			, "Queen"
			, "King"
			, "Ace"
		};
	
	String[] shortFaceName = {
			  "2"
			, "3"
			, "4"
			, "5"
			, "6"
			, "7"
			, "8"
			, "9"
			, "10"
			, "J"
			, "Q"
			, "K"
			, "A"
		};
	
	private int suitValue;
	private int faceValue;
	private String cardName;

	private int seqNum;
	
	public Card(int s, int f) {
		suitValue = s;
		faceValue = f;
		seqNum = Card.sequence++;
	}

	@Override
	public String toString() {
		// return "The "+faceName[faceValue] + " of " + suitName[suitValue];
		// return shortFaceName[faceValue] + shortSuitName[suitValue];
		return ""+seqNum;
	}
}
