package com.java8.examples;

import java.util.*;
import java.util.stream.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

class StreamsGroupingExample {

	static class Dish {

		private final String name;
		private final boolean vegetarian;
		private final int calories;
		private final Type type;

		public Dish(String name, boolean vegetarian, int calories, Type type) {
			this.name = name;
			this.vegetarian = vegetarian;
			this.calories = calories;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public boolean isVegetarian() {
			return vegetarian;
		}

		public int getCalories() {
			return calories;
		}

		public Type getType() {
			return type;
		}

		public enum Type {
			MEAT, FISH, OTHER
		}

		public enum CaloricLevel {
			DIET, NORMAL, FAT
		};

		@Override
		public String toString() {
			return name;
		}

		public static final List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 400, Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));

		public static final Map<String, List<String>> dishTags = new HashMap<>();
		static {
			dishTags.put("pork", Arrays.asList("greasy", "salty"));
			dishTags.put("beef", Arrays.asList("salty", "roasted"));
			dishTags.put("chicken", Arrays.asList("fried", "crisp"));
			dishTags.put("french fries", Arrays.asList("greasy", "fried"));
			dishTags.put("rice", Arrays.asList("light", "natural"));
			dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
			dishTags.put("pizza", Arrays.asList("tasty", "salty"));
			dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
			dishTags.put("salmon", Arrays.asList("delicious", "fresh"));
		}
	}

	// In Java 7

	public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes) {
		List<Dish> lowCaloricDishes = new ArrayList<>();
		for (Dish d : dishes) {
			if (d.getCalories() < 400) {
				lowCaloricDishes.add(d);
			}
		}
		List<String> lowCaloricDishesName = new ArrayList<>();
		Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
			public int compare(Dish d1, Dish d2) {
				return Integer.compare(d1.getCalories(), d2.getCalories());
			}
		});
		for (Dish d : lowCaloricDishes) {
			lowCaloricDishesName.add(d.getName());
		}
		return lowCaloricDishesName;
	}

	// In Java 8
	public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes) {
		return dishes.stream().filter(d -> d.getCalories() < 400).sorted(comparing(Dish::getCalories))
				.map(Dish::getName).collect(toList());
	}

	public static List<String> getThreeHighCaloricDishNames(List<Dish> dishes) {
		return dishes.stream().filter(d -> d.getCalories() > 500).map(Dish::getName).limit(3).collect(toList());

	}

	public static List<Dish> getVegetarianDishes(List<Dish> dishes) {
		return dishes.stream().filter(Dish::isVegetarian).collect(toList());

	}

	public static List<Dish> getFirstThreeHighCaloricDishNames(List<Dish> dishes) {
		return dishes.stream().filter(d -> d.getCalories() > 500).limit(3).collect(toList());

	}

	public static List<Dish> skipFirstMeatDishNames(List<Dish> dishes) {
		return dishes.stream().filter(d -> d.getType() == Dish.Type.MEAT).skip(2).collect(toList());

	}

	// Finding

	public static boolean isVegetarianFriendlyMenu(List<Dish> dishes) {
		return dishes.stream().anyMatch(Dish::isVegetarian);
	}

	public static boolean isHealthyMenu(List<Dish> dishes) {
		return dishes.stream().allMatch(d -> d.getCalories() < 1000);
	}

	public static boolean isHealthyMenu2(List<Dish> dishes) {
		return dishes.stream().noneMatch(d -> d.getCalories() >= 1000);
	}

	public static Optional<Dish> findVegetarianDish(List<Dish> dishes) {
		return dishes.stream().filter(Dish::isVegetarian).findAny();
	}

	// Grouping

	public static Map<Dish.Type, List<Dish>> groupDishesByType(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType));
	}

	public static Map<Dish.Type, List<String>> groupDishNamesByType(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
	}

	/*
	 * private static Map<Dish.Type, Set<String>> groupDishTagsByType() { return
	 * Dish.menu.stream().collect(groupingBy(Dish::getType, flatMapping(dish ->
	 * dishTags.get( dish.getName() ).stream(), toSet()))); }
	 * 
	 * private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
	 * //return Dish.menu.stream().filter(dish -> dish.getCalories() >
	 * 500).collect(groupingBy(Dish::getType)); return
	 * Dish.menu.stream().collect(groupingBy(Dish::getType, filtering(dish ->
	 * dish.getCalories() > 500, toList()))); }
	 */

	public static Map<Dish.CaloricLevel, List<Dish>> groupDishesByCaloricLevel(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(dish -> {
			if (dish.getCalories() <= 400)
				return Dish.CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return Dish.CaloricLevel.NORMAL;
			else
				return Dish.CaloricLevel.FAT;
		}));
	}

	public static Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel(
			List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType, groupingBy((Dish dish) -> {
			if (dish.getCalories() <= 400)
				return Dish.CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return Dish.CaloricLevel.NORMAL;
			else
				return Dish.CaloricLevel.FAT;
		})));
	}

	public static Map<Dish.Type, Long> countDishesInGroups(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType, counting()));
	}

	public static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType,
				reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
	}

	public static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType,
				collectingAndThen(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2), Optional::get)));
	}

	public static Map<Dish.Type, Integer> sumCaloriesByType(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
	}

	public static Map<Dish.Type, Set<Dish.CaloricLevel>> caloricLevelsByType(List<Dish> dishes) {
		return Dish.menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
			if (dish.getCalories() <= 400)
				return Dish.CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return Dish.CaloricLevel.NORMAL;
			else
				return Dish.CaloricLevel.FAT;
		}, toSet())));
	}

	// Reducing and Summarizing

	static Comparator<Dish> dishCaroliesComparator = Comparator.comparingInt(Dish::getCalories);

	public static Optional<Dish> mostCalorieDish(List<Dish> dishes) {
		return dishes.stream().collect(maxBy(dishCaroliesComparator));

	}

	public static int totalCalories(List<Dish> dishes) {
		return dishes.stream().collect(summingInt(Dish::getCalories));

	}

	// Partitioning

	public static Map<Boolean, List<Dish>> partitionedMenu(List<Dish> dishes) {
		return dishes.stream().collect(partitioningBy(Dish::isVegetarian));

	}

	public static Map<Boolean, Map<Dish.Type, List<Dish>>> partitionedGroupedMenu(List<Dish> dishes) {
		return dishes.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));

	}

	public static void main(String[] args) {

		getLowCaloricDishesNamesInJava7(Dish.menu).forEach(System.out::println);

		System.out.println("---");

		getLowCaloricDishesNamesInJava8(Dish.menu).forEach(System.out::println);

		System.out.println("---");

		getThreeHighCaloricDishNames(Dish.menu).forEach(System.out::println);

		System.out.println("---");

		getVegetarianDishes(Dish.menu).forEach(System.out::println);

		System.out.println("---");

		getFirstThreeHighCaloricDishNames(Dish.menu).forEach(System.out::println);

		System.out.println("---");

		skipFirstMeatDishNames(Dish.menu).forEach(System.out::println);

		if (isVegetarianFriendlyMenu(Dish.menu)) {
			System.out.println("Vegetarian friendly");
		}

		System.out.println(isHealthyMenu((Dish.menu)) + " isHealthyMenu");
		System.out.println(isHealthyMenu2((Dish.menu)) + " isHealthyMenu2");

		Optional<Dish> dish = findVegetarianDish(Dish.menu);
		dish.ifPresent(d -> System.out.println(d.getName()));
		
		System.out.println(groupDishesByType(Dish.menu));
		System.out.println(groupDishNamesByType(Dish.menu));
		System.out.println(groupDishesByCaloricLevel(Dish.menu));
		System.out.println(groupDishedByTypeAndCaloricLevel(Dish.menu));
		System.out.println(countDishesInGroups(Dish.menu));
		System.out.println(mostCaloricDishesByType(Dish.menu));
		System.out.println(mostCaloricDishesByTypeWithoutOptionals(Dish.menu));
		System.out.println(sumCaloriesByType(Dish.menu));
		System.out.println(caloricLevelsByType(Dish.menu));
		System.out.println(mostCalorieDish(Dish.menu));
		System.out.println(totalCalories(Dish.menu));
		System.out.println(partitionedMenu(Dish.menu));
		System.out.println(partitionedGroupedMenu(Dish.menu));

	}

}
