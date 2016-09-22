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

public interface Result<V, E> {
	
	public abstract V getValue();
	public abstract E getError();
	
	public static <V, E> Result<V, E> ok(final V value) {
		return new Ok<V, E>(value);
	}
	
	public static <V, E> Result<V, E> err(final E error) {
		return new Err<V, E>(error);
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
		public String toString() {
			return String.format("Ok(%s)", value);
		}
	}
	
	public static class Err<V, E> implements Result<V, E> {
		private final E error;
		
		public Err(final E error) {
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
		public String toString() {
			return String.format("Err(%s)", error);
		}
	}
	
}
