package com.delidishes.validate.handler;

public interface IValidateHandler<T> {

	boolean verify(T value);
}
