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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MapTest {

	private final Result<Integer, String> five = Result.ok(5);
	private final Result<Integer, String> err = Result.err("error");
	
	@Test
	public void mapShouldCallLambda() {
		final Result<String, String> result = five.map(this::intToString);
		assertThat(result.getValue()).isEqualTo("5");
	}
	
	@Test
	public void mapShouldNotCallLambdaWhenItsAnErr() {
		err.map(v -> {
			throw new RuntimeException("should not have been called!");
		});
	}
	
	@Test
	public void mapShouldReturnThisIfItsAnErr() {
		final Result<String, String> result = err.map(v -> "foo");
		assertThat(result).isSameAs(err);
	}
	
	private String intToString(final int input) {
		return Integer.toString(input);
	}

}
