package ru.job4j;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */

public class CalculateTest {
	/**
	 * Test echo
	 */
	@Test
	public void whenTakeNameThenThreeEchoPlusName() {
		String input = "Vadim Bolokhov";
		String expect = "Echo, echo, echo : Vadim Bolokhov";
		Calculate calc = new Calculate();
		String result = calc.echo(input);
		assertThat(result, is(expect));
	}
}