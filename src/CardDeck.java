import java.util.Random;

/**
 * CardDeck represents a deck of 52 playing cards cards have the values 0 (ace) through 12
 * (king)
 * 
 * @author Barry Nelson
 *
 */
public class CardDeck {

	Random random = new Random(1234L);

	private final static int SUITS = 4;
	private final static int FULLSUIT = 13;
	private final int deckSize = SUITS * FULLSUIT;

	private int shuffleCount;

	private CNode cards;
	private CNode lastCardNode;

	/**
	 * CNode a simple linked list to hold cards
	 * 
	 * @author Barry Nelson
	 *
	 */
	class CNode {

		private Card card;
		private CNode nextCard;

		//
		public CNode(Card card) {
			this.card = card;
		}

		public CNode(CNode deck2, CNode nextCard) {
			this.card = deck2.card;
			this.nextCard = nextCard;
		}

		public void addCardNode(CNode nCard) {
			nextCard = nCard;
		}

		/**
		 * cuts CNode into two parts
		 * 
		 * @param cut
		 * @return remaining CNode after the split
		 */
		public CNode splitNodeAt(int cut) {
			CNode lNode = this;
			CNode cNode = this;
			for (int i = 0; i < cut; i++) {
				lNode = cNode;
				cNode = cNode.nextCard;
			}

			lNode.nextCard = null;

			return cNode;
		}

		public boolean hasNext() {
			return nextCard != null ? true : false;
		}

		/**
		 * set a card into the nextCard replaces whatever may have been there
		 * 
		 * @param nextNode
		 */
		public void setNext(CNode nextNode) {
			nextCard = nextNode;
		}

		/**
		 * returns the next card
		 * 
		 * @return nextCard
		 */
		public CNode getNext() {
			return nextCard;
		}

		/**
		 * traverse list by count or until the last node is located
		 * 
		 * @param count
		 * @return
		 */
		public CNode skip(int count) {
			CNode temp = this;
			while (temp.nextCard != null && count-- > 0) {
				temp = temp.nextCard;
			}
			return temp;
		}

		/**
		 * locate and return the last node
		 * 
		 * @return
		 */
		public CNode getLast() {
			CNode temp = this;
			while (temp.nextCard != null) {
				temp = temp.nextCard;
			}
			return temp;
		}

		/**
		 * attach a CNode list of cards to the last node
		 * 
		 * @param newTail
		 */
		public void append(CNode newTail) {
			CNode currentEnd = getLast();
			currentEnd.setNext(newTail);
		}

		/**
		 * count nodes to the end of list
		 * 
		 * @return
		 */
		public int count() {

			int c = 1;
			CNode temp = this;

			while (temp.hasNext()) {
				c++;
				temp = temp.getNext();
			}

			return c;
		}

		@Override
		public String toString() {
			String str = card.toString();
			if (nextCard != null) {
				str += ", " + nextCard.toString();
			}
			return str;
		}

	};

	/**
	 * Initialize CardDeck to the four suits in order, much like from the factory
	 */
	public CardDeck() {

		shuffleCount = 0;
		for (int suit = 0; suit < SUITS; suit++) {
			for (int face = 0; face < FULLSUIT; face++) {
				CNode nCard = new CNode(new Card(suit, face));
				if (cards == null) {
					cards = nCard;
				} else {
					lastCardNode.addCardNode(nCard);
				}
				lastCardNode = nCard;
			}
		}
	}

	/**
	 * debug test - verify deck1 and deck2 have the same number of cards
	 * 
	 * @param deck1
	 * @param deck2
	 */
	private void checkTotals(CNode deck1, CNode deck2) {

		int x = deck1.count();
		int y = 0;
		if (deck2 != null) {
			y = deck2.count();
		}
		if (x + y != deckSize) {
			System.out.println("fail");
			System.exit(0);
		}
	}

	/**
	 * shuffle the deck
	 * 
	 * @param reps
	 */
	public void shuffle(int reps) {

		// shuffle_striping(random.nextInt(deckSize/5)+(deckSize/6));
		// shuffle_striping(random.nextInt(deckSize/3)+(deckSize/6));

		riffleShuffle(reps);

	}

	/**
	 * stripeShuffle is often used in conjunction to a riffleShuffle when shuffling cards
	 * method: a group of cards is separated from the top of the deck and placed at the
	 * end of the next "stripe" of cards removed from the new top of the deck this
	 * procedure repeats until the original deck is exhausted
	 * 
	 * @param stripe
	 */
	private void stripeShuffle(int stripe) {
		CNode bDeck = null;
		CNode result = null;
		for (int i = stripe; i < deckSize;) {

			bDeck = cards.splitNodeAt(stripe);

			if (result == null) {
				result = cards;
			} else {
				cards.append(result);
				result = cards;
			}

			cards = bDeck;

			i += stripe;
		}
		cards.append(result);
	}

	/**
	 * riffleShuffle is the most common form of shuffling cards method: deck is
	 * stripeShuffled then deck is cut into two decks randomly those two decks are merged
	 * via shuffleMerge
	 * 
	 * @param i
	 */
	private void riffleShuffle(int i) {
		int cutSize;
		for (; i > 0; i--) {

			shuffleCount++;

			do {
				cutSize = random.nextInt(deckSize);
			} while (cutSize == 0);

			float interleaveFactor = (float) cutSize / (float) (deckSize - cutSize);

			stripeShuffle(random.nextInt(deckSize / 5) + (deckSize / 6));

			CNode bDeck = cards.splitNodeAt(cutSize);
			shuffleMerge(cards, bDeck, interleaveFactor);

			System.out.println(cards);

		}
	}

	/**
	 * merges second CNode deck into first method: a random number of cards are "cut" from
	 * the top of both decks one group goes below the other repeat through the remainder
	 * of both decks
	 * 
	 * @param deck1
	 * @param deck2
	 * @param interleave
	 */
	private void shuffleMerge(CNode deck1, CNode deck2, float interleaveFactor) {

		CNode tempDeck;

		// if(deck1.count() < deck2.count()){
		if (random.nextBoolean() || (deck1.count() < deck2.count())) {
			tempDeck = deck1;
			deck1 = deck2;
			deck2 = tempDeck;
			tempDeck = null;
			interleaveFactor = 1 / interleaveFactor;
		}

		int preMergeCount = deck1.count() + deck2.count();

		float interleave = 0.0f;
		float priorInterleave = 0.0f;

		tempDeck = deck1;
		do {
			interleave += interleaveFactor;

			tempDeck = tempDeck.skip((int) (interleave - priorInterleave));
			priorInterleave = interleave;

			tempDeck.setNext(new CNode(deck2, tempDeck.getNext()));
			tempDeck = tempDeck.skip(2);
			deck2 = deck2.getNext();

			checkTotals(deck1, deck2);

		} while (tempDeck != null && deck2 != null && tempDeck.hasNext() && deck2.hasNext());

		while (deck2 != null) {
			tempDeck = tempDeck == null ? deck1.getLast() : tempDeck.getLast();
			tempDeck.setNext(new CNode(deck2, tempDeck.getNext()));
			tempDeck = tempDeck.skip(2);
			deck2 = deck2.getNext();
		}

		int postMergeCount = deck1.count();
		if (postMergeCount != deckSize) {
			System.out.println("failure: count = " + postMergeCount);
		}

		// store into proper place
		cards = deck1;

	}

	@Override
	public String toString() {
		return "shuffled " + shuffleCount + " :" + cards;
	}

}
