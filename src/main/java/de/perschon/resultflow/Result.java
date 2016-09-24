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

import java.util.function.Function;

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
	
	/**
	 * Returns the value of this Ok instance. If it's an Err instance, this method throws an Exception.
	 * @return see above.
	 */
	public abstract V getValue();
	
	/**
	 * Returns the error value of this Err instance. If it's an Ok instance, this method throws an Exception.
	 * @return see above.
	 */
	public abstract E getError();
	
	/**
	 * Returns <code>true</code> if this instance represents an Ok value, false otherwise.
	 * @return see above.
	 */
	public abstract boolean isOk();
	
	/**
	 * Returns <code>true</code> if this instance represents an Err value, false otherwise.
	 * @return see above
	 */
	public abstract boolean isErr();
	
	/**
	 * Returns a new Ok instance containing the given value.
	 * 
	 * @param value the value
	 * @param <V> The type of the value
	 * @param <E> The type of the error
	 * @return see above
	 */
	public static <V, E> Result<V, E> ok(final V value) {
		return new Ok<V, E>(value);
	}
	
	/**
	 * Returns a new Err instance containing the given error.
	 * 
	 * @param error the error
	 * @param <V> The type of the value
	 * @param <E> The type of the error
	 * @return see above
	 */
	public static <V, E> Result<V, E> err(final E error) {
		return new Err<V, E>(error);
	}
	
	/**
	 * If this is an Ok value, andThen() returns the result of the given {@link Function}.
	 * Otherwise returns this.
	 * 
	 * @param lambda The {@link Function} to be called with the value of this.
	 * @param <U> The new value type.
	 * @return see above.
	 */
	public default <U> Result<U, E> andThen(final Function<V, Result<U, E>> lambda) {
		if (isOk()) {
			return lambda.apply(getValue());
		}
		
		@SuppressWarnings("unchecked")
		final Result<U, E> ret = (Result<U, E>) this;
		return ret;
	}
	
	/**
	 * This class represents the Ok side of @{link Result}.
	 * 
	 * @param <V> The value type
	 * @param <E> The error type
	 */
	public static class Ok<V, E> implements Result<V, E> {
		private final V value;
		
		/**
		 * Constructor.
		 * @param value the value
		 */
		private Ok(final V value) {
			super();
			this.value = value;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
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
	
	/**
	 * This class represents theErr side of @{link Result}.
	 * 
	 * @param <V> The value type
	 * @param <E> The error type
	 */
	public static class Err<V, E> implements Result<V, E> {
		private final E error;
		
		/**
		 * Constructor.
		 * @param error the error
		 */
		private Err(final E error) {
			super();
			this.error = error;
		}
		
		@Override
		public V getValue() {
			throw new RuntimeException("Can't call getValue() on Err instances!");
		}

		@Override
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
