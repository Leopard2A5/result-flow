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

import java.util.Optional;

import org.junit.Test;

public class BaseFunctionsTest {

	private final Result<String, Object> ok = Result.ok("foo");
	private final Result<Object, String> err = Result.err("bar");
	
	@Test
	public void okShouldReturnAnOk() {
		assertThat(ok).isExactlyInstanceOf(Result.Ok.class);
	}
	
	@Test
	public void okShouldIdentifyAsOk() {
		assertThat(ok.isOk()).isTrue();
	}
	
	@Test
	public void okShouldNotIdentifyAsErr() {
		assertThat(ok.isErr()).isFalse();
	}
	
	@Test
	public void okShouldReturnItsValue() {
		assertThat(ok.getValue().get()).isEqualTo("foo");
	}
	
	@Test
	public void okShouldReturnEmptyOnGetError() {
		assertThat(ok.getError()).isEqualTo(Optional.empty());
	}
	
	@Test
	public void errShouldReturnAnOk() {
		assertThat(err).isExactlyInstanceOf(Result.Err.class);
	}
	
	@Test
	public void errShouldIdentifyAsErr() {
		assertThat(err.isErr()).isTrue();
	}
	
	@Test
	public void errShouldNotIdentifyAsOk() {
		assertThat(err.isOk()).isFalse();
	}
	
	@Test
	public void errShouldReturnEmptyOnGetValue() {
		assertThat(err.getValue()).isEqualTo(Optional.empty());
	}
	
	@Test
	public void errShouldReturnItsError() {
		assertThat(err.getError().get()).isEqualTo("bar");
	}
}
