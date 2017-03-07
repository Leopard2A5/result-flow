/*
 * Copyright 2017 René Perschon <rperschon85@gmail.com>
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

/**
 * This class represents the Ok side of @{link Result}.
 *
 * @param <V> The value type
 * @param <E> The error type
 */
public class Ok<V, E> implements Result<V, E> {
	private final V value;

	/**
	 * Constructor.
	 * @param value the value
	 */
	Ok(final V value) {
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
	public V unwrap() {
		return value;
	}

	@Override
	public void expect(final String message) throws ResultException {
		// do nothing
	}

	@Override
	public String toString() {
		return String.format("Ok(%s)", value);
	}
}
