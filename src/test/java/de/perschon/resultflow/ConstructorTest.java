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
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;

public class ConstructorTest {

	@Test
	public void okShouldReturnAnOk() {
		final Result<String, Object> ok = Result.ok("foo");
		
		assertThat(ok).isExactlyInstanceOf(Result.Ok.class);
		
		assertThat(ok.isOk()).isTrue();
		assertThat(ok.isErr()).isFalse();
		
		assertThat(ok.getValue()).isEqualTo("foo");
		assertThatExceptionOfType(RuntimeException.class)
			.isThrownBy(() -> ok.getError());
	}
	
	@Test
	public void errShouldReturnAnOk() {
		final Result<Object, String> err = Result.err("bar");
		
		assertThat(err).isExactlyInstanceOf(Result.Err.class);
		
		assertThat(err.isOk()).isFalse();
		assertThat(err.isErr()).isTrue();
		
		assertThat(err.getError()).isEqualTo("bar");
		assertThatExceptionOfType(RuntimeException.class)
			.isThrownBy(() -> err.getValue());
	}

}
