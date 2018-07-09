package com.java8.examples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Comparator.comparing;

import java.util.ArrayList;

class MethodReferences {

	private static class Apple {

		private String colour = "";
		private Integer weight = 0;

		public Apple(int weight, String colour) {
			this.weight = weight;
			this.colour = colour;
		}

		public Apple(int weight) {
			this.weight = weight;
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

	private static class AppleComparator implements Comparator<Apple> {
		public int compare(Apple a1, Apple a2) {
			return a1.getWeight().compareTo(a2.getWeight());
		}
	}

	private static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
		List<Apple> result = new ArrayList<>();
		for (Integer e : list) {
			result.add(f.apply(e));
		}
		return result;
	}

	public static void main(String args[]) {

		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
		System.out.println(inventory);
		inventory.sort(comparing(Apple::getWeight));
		System.out.println(inventory);
		inventory.sort(comparing(Apple::getWeight).reversed());
		System.out.println(inventory);
		List<String> str = Arrays.asList("a", "b", "A", "B");
		System.out.println(str);
		str.sort(String::compareToIgnoreCase);
		System.out.println(str);

		// constructor references
		Function<Integer, Apple> c2 = Apple::new;
		Apple a2 = c2.apply(110);
		System.out.println(a2);
		BiFunction<Integer, String, Apple> c3 = Apple::new;
		Apple a3 = c3.apply(200, "yellow");
		System.out.println(a3);
		List<Integer> weights = Arrays.asList(1, 2, 3, 4);
		List<Apple> appleWeights = map(weights, Apple::new);
		System.out.println(appleWeights);
	}
}
