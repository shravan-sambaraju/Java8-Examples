package com.java8.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class BehaviorParameterization {

	private static class Apple {

		private String colour = "";
		private Integer weight = 0;

		public Apple(int weight, String colour) {
			this.weight = weight;
			this.colour = colour;
		}

		private String getColour() {
			return colour;
		}

		private void setColour(String colour) {
			this.colour = colour;
		}

		private Integer getWeight() {
			return weight;
		}

		private void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String toString() {
			return "Apple{" + "colour='" + colour + '\'' + ", weight=" + weight + '}';
		}

	}

	interface ApplePredicate {
		public boolean test(Apple a);
	}

	private static class AppleWeightPredicate implements ApplePredicate {

		@Override
		public boolean test(Apple a) {
			// TODO Auto-generated method stub
			return a.getWeight() > 150;
		}
	}

	private static class AppleColourPredicate implements ApplePredicate {

		@Override
		public boolean test(Apple a) {
			// TODO Auto-generated method stub
			return "green".equalsIgnoreCase(a.getColour());
		}
	}

	private static class AppleRedAndHeavyPredicate implements ApplePredicate {
		@Override
		public boolean test(Apple a) {
			// TODO Auto-generated method stub
			return "red".equalsIgnoreCase(a.getColour()) && a.getWeight() > 150;
		}
	}

	private static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static void main(String args[]) {

		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

		List<Apple> greenApples = filter(inventory, new AppleColourPredicate());
		System.out.println(greenApples);

		List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
		System.out.println(heavyApples);

		List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
		System.out.println(redAndHeavyApples);

		List<Apple> redApples = filter(inventory, new ApplePredicate() {

			@Override
			public boolean test(Apple a) {
				return "red".equalsIgnoreCase(a.getColour());
			}

		});
		System.out.println(redApples);
		List<Apple> greenApples2 = filter(inventory, (Apple a) -> "green".equalsIgnoreCase(a.getColour()));
		System.out.println(greenApples2);
		System.out.println(inventory);

		// anonymous class

		/*
		 * inventory.sort(new Comparator<Apple>() { public int compare(Apple a1,
		 * Apple a2){ return a1.getWeight().compareTo(a2.getWeight());
		 * 
		 * } });
		 */

		// without type inference
		// inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
		// with type inference
		inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
		System.out.println(inventory);
	}
}
