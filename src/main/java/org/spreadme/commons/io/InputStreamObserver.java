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

package org.spreadme.commons.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream Obsercer
 * @author shuwei.wang
 * @since 1.0.0
 */
public interface InputStreamObserver {

	/**
	 * Called to indicate, that {@link InputStream#read()} has been invoked
	 */
	void data(final int pByte) throws IOException;

	/**
	 * Called to indicate, that {@link InputStream#read(byte[])}, or
	 * {@link InputStream#read(byte[], int, int)} have been called, and are about to
	 * invoke data.
	 */
	void data(final byte[] pBuffer, final int pOffset, final int pLength) throws IOException;

	/**
	 * Called to indicate, that EOF has been seen on the underlying stream.
	 */
	void finished() throws IOException;

	/**
	 * Called to indicate, that an error occurred on the underlying stream.
	 */
	void error(final IOException pException) throws IOException;
}
