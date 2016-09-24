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

public class AndThenTest {

	private final Result<String, String> five = Result.ok("5");
	private final Result<String, String> err = Result.err("error");
	private int target;
	
	@Test
	public void andThenShouldCallLambda() {
		assertThat(five.getValue()).isEqualTo("5");
		assertThat(target).isNotEqualTo(5);
		
		five.andThen(v -> target = Integer.valueOf(v));
		assertThat(target).isEqualTo(5);
		assertThat(five.getValue()).isEqualTo("5");
	}
	
	@Test
	public void andThenShouldNotCallLambdaWhenItsAnErr() {
		err.andThen(v -> {
			throw new RuntimeException("should not be called!");
		});
	}
	
	@Test
	public void andThenShouldReturnThis() {
		Object ret = null;
		ret = five.andThen(v -> {});
		assertThat(ret).isSameAs(five);
	}

}
