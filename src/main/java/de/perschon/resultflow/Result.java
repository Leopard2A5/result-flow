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

import java.util.Optional;
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
	 * Returns a new Ok instance containing the given value.
	 *
	 * @param value the value
	 * @param <V> The type of the value
	 * @param <E> The type of the error
	 * @return see above
	 */
	static <V, E> Result<V, E> ok(final V value) {
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
	static <V, E> Result<V, E> err(final E error) {
		return new Err<V, E>(error);
	}

	/**
	 * Returns the value of this instance as an {@link Optional}. Returns Optional.empty()
	 * if this is an Err instance.
	 * @return see above.
	 */
	Optional<V> getValue();
	
	/**
	 * Returns the error of this instance as an {@link Optional}. Returns Optional.empty()
	 * if this is an Ok instance.
	 * @return see above.
	 */
	Optional<E> getError();
	
	/**
	 * Returns <code>true</code> if this instance represents an Ok value, false otherwise.
	 * @return see above.
	 */
	boolean isOk();
	
	/**
	 * Returns <code>true</code> if this instance represents an Err value, false otherwise.
	 * @return see above
	 */
	boolean isErr();
	
	/**
	 * If this is an Ok value, andThen() returns the result of the given {@link Function}.
	 * Otherwise returns this.
	 * 
	 * @param lambda The {@link Function} to be called with the value of this.
	 * @param <U> The new value type.
	 * @return see above.
	 */
	default <U> Result<U, E> andThen(final Function<V, Result<U, E>> lambda) {
		return getValue()
			.map(lambda::apply)
			.orElseGet(() -> {
				@SuppressWarnings("unchecked")
				final Result<U, E> ret = (Result<U, E>) this;
				return ret;
			});
	}
	
	/**
	 * If this is an Ok value, map() returns the result of the given {@link Function}, wrapped
	 * in a new Ok Result instance. Otherwise returns this.
	 * 
	 * @param lambda The {@link Function} to call with the value of this.
	 * @param <U> The new value type.
	 * @return see above.
	 */
	default <U> Result<U, E> map(final Function<V, U> lambda) {
		return getValue()
			.map(v -> Result.<U, E>ok(lambda.apply(v)))
			.orElseGet(() -> {
				@SuppressWarnings("unchecked")
				final Result<U, E> ret = (Result<U, E>) this;
				return ret;
			});
	}

	/**
	 * If this is an Err value, mapErr() returns the result of the given @{link Function}, wrapped
	 * in a new Err Result instance. Otherwise returns this.
	 *
	 * @param lambda The {@link Function} to call with the error of this.
	 * @param <F>  The new error type.
     * @return see above
     */
	default <F> Result<V, F> mapErr(final Function<E, F> lambda) {
		return getError()
			.map(e -> Result.<V, F>err(lambda.apply(e)))
			.orElseGet(() -> {
				@SuppressWarnings("unchecked")
				final Result<V, F> ret = (Result<V, F>) this;
				return ret;
			});
	}
	
	/**
	 * This class represents the Ok side of @{link Result}.
	 * 
	 * @param <V> The value type
	 * @param <E> The error type
	 */
	class Ok<V, E> implements Result<V, E> {
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
		public Optional<V> getValue() {
			return Optional.of(value);
		}

		@Override
		public Optional<E> getError() {
			return Optional.empty();
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
	class Err<V, E> implements Result<V, E> {
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
		public Optional<V> getValue() {
			return Optional.empty();
		}

		@Override
		public Optional<E> getError() {
			return Optional.of(error);
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
