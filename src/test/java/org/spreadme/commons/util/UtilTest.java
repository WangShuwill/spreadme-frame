/*
 *    Copyright [2019] [shuwei.wang (c) wswill@foxmail.com]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.spreadme.commons.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.spreadme.commons.id.IdentifierGenerator;
import org.spreadme.commons.id.support.PrefixedLeftNumericGenerator;
import org.spreadme.commons.id.support.SnowflakeLongGenerator;
import org.spreadme.commons.id.support.TimeBasedIdentifierGenerator;
import org.spreadme.commons.lang.Dates;
import org.spreadme.commons.lang.Randoms;
import org.spreadme.commons.system.SystemMonitor;

/**
 * @author shuwei.wang
 */
public class UtilTest {

	private static final String FORMATTER = "HH:mm:ss dd.MM.yyyy";

	private static final SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER);

	private static final int THREAD_POOL_SIZE = 100;

	@Test
	public void testStringUtil() {
		System.out.println(Arrays.toString(Randoms.nextBytes(3)));
		for (int i = 0; i < 100; i++) {
			System.out.println(StringUtil.randomString(6));
			System.out.println(StringUtil.randomString("-+*", 1));
		}
	}

	@Test
	public void testId() {
		IdentifierGenerator<String> timeBasedGenerator = new TimeBasedIdentifierGenerator();
		IdentifierGenerator<String> leftNumericGenerator = new PrefixedLeftNumericGenerator(StringUtil.randomString(1), true, 3);
		IdentifierGenerator<Long> longIdentifierGenerator = new SnowflakeLongGenerator(1, 1);
		for (int i = 0; i < 101; i++) {
			System.out.println(timeBasedGenerator.nextIdentifier());
			System.out.println(leftNumericGenerator.nextIdentifier());
			System.out.println(longIdentifierGenerator.nextIdentifier());
		}
	}

	@Test
	public void testDates() {
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		while (true) {
			executor.submit(() -> {
				try {
					String dateCreate = "19:30:55 03.05.2015";
					//sdf.parse(dateCreate);
					Dates.parse(dateCreate, FORMATTER);
				}
				catch (Throwable th) {
					th.printStackTrace();
					return;
				}
				System.out.print("OK ");

			});
		}
	}

	@Test
	public void testSystemInfo() {
		SystemMonitor monitor = new SystemMonitor();
		System.out.println(monitor.getSystemInfo());
	}
}
