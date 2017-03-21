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

package de.perschon.resultflow.examples;

import de.perschon.resultflow.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Numbers {
	public static void main(final String[] args) {
		final Result<Integer, String> result = readLine()
			.andThen(Numbers::parseInt)
			.map(Numbers::doubleUp);
		System.out.println(result);
	}

	private static Integer doubleUp(final Integer value) {
		return value * 2;
	}

	private static Result<Integer, String> parseInt(final String input) {
		try {
			return Result.ok(Integer.parseInt(input));
		} catch (final NumberFormatException e) {
			return Result.err(e.getMessage());
		}
	}

	private static Result<String, String> readLine() {
		try {
			final InputStreamReader in = new InputStreamReader(System.in);
			final BufferedReader buf = new BufferedReader(in);
			return Result.ok(buf.readLine());
		} catch (final IOException e) {
			return Result.err(e.getMessage());
		}
	}
}
