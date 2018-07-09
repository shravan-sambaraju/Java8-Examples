package com.java8.examples;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;

class GroupingByExample {

	private static class Transaction {
		private final Currency currency;
		private final double value;

		public Transaction(Currency currency, double value) {
			this.currency = currency;
			this.value = value;
		}

		private Currency getCurrency() {
			return currency;
		}

		private double getValue() {
			return value;
		}

		@Override
		public String toString() {
			return currency + " " + value;
		}
	}

	public enum Currency {
		EUR, USD, JPY, GBP, CHF
	}

	private static List<Transaction> transactions = Arrays.asList(new Transaction(Currency.EUR, 1500.0),
			new Transaction(Currency.USD, 2300.0), new Transaction(Currency.GBP, 9900.0),
			new Transaction(Currency.EUR, 1100.0), new Transaction(Currency.JPY, 7800.0),
			new Transaction(Currency.CHF, 6700.0), new Transaction(Currency.EUR, 5600.0),
			new Transaction(Currency.USD, 4500.0), new Transaction(Currency.CHF, 3400.0),
			new Transaction(Currency.GBP, 3200.0), new Transaction(Currency.USD, 4600.0),
			new Transaction(Currency.JPY, 5700.0), new Transaction(Currency.EUR, 6800.0));

	private static Map<Currency, List<Transaction>> transactionsByCurrencies(List<Transaction> transactions) {
		return transactions.stream().collect(groupingBy(Transaction::getCurrency));

	}

	public static void main(String[] args) {
		System.out.println(transactionsByCurrencies(transactions));
	}
}
