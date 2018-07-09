package com.java8.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

class AppleLambdasExample {

	private static class Apple {

		private String colour = "";
		private int weight = 0;

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

		private int getWeight() {
			return weight;
		}

		private void setWeight(int weight) {
			this.weight = weight;
		}

		public String toString() {
			return "Apple{" + "colour='" + colour + '\'' + ", weight=" + weight + '}';
		}

	}

	private static boolean isGreenApple(Apple apple) {
		return "green".equalsIgnoreCase(apple.getColour());
	}

	private static boolean isHeavyApple(Apple apple) {
		return apple.getWeight() > 150;
	}

	private static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	public static void main(String args[]) {

		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"),
				new Apple(75, "brown"));

		List<Apple> greenApples = filterApples(inventory, AppleLambdasExample::isGreenApple);
		System.out.println(greenApples);

		List<Apple> heavyApples = filterApples(inventory, AppleLambdasExample::isHeavyApple);
		System.out.println(heavyApples);

		List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equalsIgnoreCase(a.getColour()));
		System.out.println(greenApples2);

		List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
		System.out.println(heavyApples2);

		List<Apple> weirdApples = filterApples(inventory,
				(Apple a) -> a.getWeight() < 80 || "brown".equalsIgnoreCase(a.getColour()));
		System.out.println(weirdApples);
	}
}