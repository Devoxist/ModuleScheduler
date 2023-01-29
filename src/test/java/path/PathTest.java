/*
 * Copyright (c) 2023 Devoxist, Dev-Bjorn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package path;

import nl.devoxist.modulescheduler.path.Path;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@TestOnly
public class PathTest {

    @Test
    public void hasPreviousPathTest() {
        Path path = new Path();
        Path path2 = new Path();
        path.setNextPath(path2);

        Assertions.assertTrue(path2.hasPreviousPath());
    }

    @Test
    public void equalsPreviousPathTest() {
        Path path = new Path();
        Path path2 = new Path();
        path.setNextPath(path2);

        Assertions.assertEquals(path2.getPreviousPath(), path);
    }

    @Test
    public void equalsNextPathTest() {
        Path path = new Path();
        Path path2 = new Path();
        path.setNextPath(path2);

        Assertions.assertEquals(path.getNextPath(), path2);
    }

    @Test
    public void completeNextPreviousPathTest() {
        Path path = new Path();
        Path path2 = new Path();
        path.setNextPath(path2);

        Assertions.assertTrue(path2.hasPreviousPath());
        Assertions.assertEquals(path2.getPreviousPath(), path);

        Assertions.assertEquals(path.getNextPath(), path2);
    }

    @Test
    public void setClsPathTest() {
        Path path = new Path();
        path.setCls(PathTest.class);

        Assertions.assertEquals(path.getCls(), PathTest.class);
    }

    @Test
    public void previousClsPathTest() {
        Path path = new Path();
        path.setCls(PathTest.class);
        Path path2 = new Path();
        path.setNextPath(path2);

        Assertions.assertEquals(path2.getPreviousPath().getCls(), PathTest.class);
    }
}
