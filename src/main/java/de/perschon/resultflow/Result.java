/*
 * Copyright 2016 René Perschon <rperschon85@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.perschon.resultflow;

import java.util.function.Consumer;

/**
 * The Result type is an alternative way of chaining together functions in a
 * functional programming style while hiding away error handling structures such as
 * try-catch-blocks and conditionals.<br/>
 * 
 * Instead of adding a throws declaration to a function, the return type of the function
 * is instead set to Result<V, E> where V is the original return type, i.e. the
 * "happy case" and E is the error type, usually the Exception type or a String
 * if an error explanation is sufficient.<br/><br/>
 * 
 * Example:
 * <pre>
 * public Result<Float, String> divide(int a, int b) {
 *     if (b == 0) {
 *         return Result.err("Can't divide by zero!");
 *     } else {
 *         return Result.ok(a / b);
 *     }
 * }
 * </pre>
 *   
 * @param <V> The value type of the Result.
 * @param <E> The error type of the Result.
 */
public interface Result<V, E> {
	
	public abstract V getValue();
	public abstract E getError();
	public abstract boolean isOk();
	public abstract boolean isErr();
	
	public static <V, E> Result<V, E> ok(final V value) {
		return new Ok<V, E>(value);
	}
	
	public static <V, E> Result<V, E> err(final E error) {
		return new Err<V, E>(error);
	}
	
	/**
	 * Calls the given {@link Consumer} with the contained value, if this is an Ok. If
	 * this is an Err, the lambda is not called. In both cases andThen returns this.
	 * 
	 * @param lambda the {@link Consumer} to call.
	 * @return this
	 */
	public default Result<V, E> andThen(final Consumer<V> lambda) {
		if (isOk()) {
			lambda.accept(getValue());
		}
		return this;
	}
	
	
	public static class Ok<V, E> implements Result<V, E> {
		private final V value;
		
		private Ok(final V value) {
			super();
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public E getError() {
			throw new RuntimeException("Can't call getError() on Ok instances!");
		}
		
		@Override
		public boolean isOk() {
			return true;
		}

		@Override
		public boolean isErr() {
			return false;
		}
		
		@Override
		public String toString() {
			return String.format("Ok(%s)", value);
		}
	}
	
	public static class Err<V, E> implements Result<V, E> {
		private final E error;
		
		private Err(final E error) {
			super();
			this.error = error;
		}
		
		public V getValue() {
			throw new RuntimeException("Can't call getValue() on Err instances!");
		}

		public E getError() {
			return error;
		}
		
		@Override
		public boolean isOk() {
			return false;
		}

		@Override
		public boolean isErr() {
			return true;
		}
		
		@Override
		public String toString() {
			return String.format("Err(%s)", error);
		}
	}
	
}
