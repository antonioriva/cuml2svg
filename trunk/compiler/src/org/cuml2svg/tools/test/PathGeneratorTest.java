package org.cuml2svg.tools.test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.cuml2svg.tools.FirstStategy;
import org.cuml2svg.tools.PathGenerator;
import org.junit.Test;

public class PathGeneratorTest {

	@Test
	public void testGetAbsoluteDirectionToStopPoint() {
		assertEquals(PathGenerator.TOP, PathGenerator.getAbsoluteDirection( new Point(176,58),new Point(167,14)));
		assertEquals(PathGenerator.RIGHT, PathGenerator.getAbsoluteDirection( new Point(17,7),new Point(19,5)));
		assertEquals(PathGenerator.BOTTOM, PathGenerator.getAbsoluteDirection( new Point(16,4),new Point(18,10)));
		assertEquals(PathGenerator.LEFT, PathGenerator.getAbsoluteDirection( new Point(16,4),new Point(10,6)));
	}
}
