package com.delidishes.validate.handler.pojo;

public class Rule<T> {
	private RuleOperation operation;
	private T currentValue;
	private T compareValue;

	public RuleOperation getOperation() {
		return operation;
	}

	public void setOperation(RuleOperation operation) {
		this.operation = operation;
	}

	public T getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(T currentValue) {
		this.currentValue = currentValue;
	}

	public T getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(T compareValue) {
		this.compareValue = compareValue;
	}
}
