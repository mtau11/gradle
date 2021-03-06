/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.performance.fixture

import spock.lang.Specification

class WaitingReaderTest extends Specification {

    def "can read lines"() {
        def input = new ExpandingReader()
        input.append("1\n2")
        def source = new BufferedReader(input)
        def reader = new WaitingReader(source, 1, 1)
        expect:
        reader.readLine() == "1"
        reader.retriedCount == 0

        reader.readLine() == "2"
        reader.retriedCount == 0

        reader.readLine() == null
        reader.retriedCount > 0
    }

    def "can receive content after end of stream reached"() {
        def input = new ExpandingReader()
        input.append("1\n2\n")
        def source = new BufferedReader(input)
        def reader = new WaitingReader(source, 1, 1)

        expect:
        reader.readLine() == "1"
        reader.readLine() == "2"
        reader.readLine() == null
        input.append("3\n4")
        reader.readLine() == "3"
        reader.readLine() == "4"
        reader.readLine() == null
    }

    static class ExpandingReader extends Reader {
        final buffer = new StringBuilder()

        void append(String content) {
            buffer.append(content)
        }

        @Override
        void close() throws IOException {
        }

        @Override
        int read(char[] dest, int offset, int len) throws IOException {
            if (buffer.length() == 0) {
                return -1
            }
            int count = Math.min(len, buffer.length())
            for (int i = 0; i < count; i++) {
                dest[i + offset] = buffer.charAt(i)
            }
            buffer.delete(0, count)
            return count
        }
    }
}
