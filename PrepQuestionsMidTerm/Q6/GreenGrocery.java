import java.util.ArrayList;
import java.util.List;

public class GreenGrocery implements Grocery<Fruit> {

	private final List<Fruit> fruits = new ArrayList<>();

	public GreenGrocery() {
	}

	public List<Fruit> getFruits() {
		return fruits;
	}

	@Override
	public void restock(List<Fruit> fruits) {

		//START YOUR CODE
		for(Fruit fruit:fruits){
			this.fruits.add(fruit);
		}



		//END YOUR CODE
	}

	@Override
	public Iterator<Fruit> getIterator() {
		return new FruitIterator(this);
	}

	public class FruitIterator implements Iterator<Fruit> {

		private final GreenGrocery grocery;

		public FruitIterator(GreenGrocery grocery) {
			this.grocery = grocery;
		}

		int index;

		@Override
		public boolean hasNext() {

			// START YOUR CODE
			if(index<=grocery.fruits.size()-1)
				return true;



			//You are allowed to change this return value.
			return false;

			// END YOUR CODE
		}

		@Override
		public Fruit next() {

			// START YOUR CODE
			Fruit fruit = grocery.fruits.get(index);
			index++;



			//You are allowed to change this return value.
			return fruit;

			// END YOUR CODE
		}
	}
}
