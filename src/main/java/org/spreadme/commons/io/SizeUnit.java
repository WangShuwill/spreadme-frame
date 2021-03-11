/*
 * Copyright [4/8/20 2:34 PM] [shuwei.wang (c) wswill@foxmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.spreadme.commons.io;

/**
 * size unit
 * @author shuwei.wang
 * @since 1.0.0
 */
public enum SizeUnit {

	B,
	KB,
	MB,
	GB,
	TB;

	private static final int UNIT_SIZE = 1024;

	public static String convert(long length) {
		for (int i = SizeUnit.values().length - 1; i > 0; i--) {
			double step = Math.pow(UNIT_SIZE, i);
			if (length > step) {
				return String.format("%3.1f%s", length / step, SizeUnit.values()[i]);
			}
		}
		return Long.toString(length);
	}
}