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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpectTest {

	@Test
	public void expectShouldDoNothingOnOk() {
		Result.ok(5).expect("foo");
	}

	@Test
	public void expectShouldThrowExceptionOnErr() {
		try {
			Result.err(7).expect("This should be ok");
		} catch (final ResultException e) {
			assertThat(e)
				.isExactlyInstanceOf(ResultException.class)
				.hasMessage("This should be ok");
		}
	}
}
