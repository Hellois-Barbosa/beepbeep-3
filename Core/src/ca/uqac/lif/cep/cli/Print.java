/*
    BeepBeep, an event stream processor
    Copyright (C) 2008-2016 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cep.cli;

import java.util.Queue;
import java.util.ArrayDeque;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.tmf.Sink;
import ca.uqac.lif.cep.util.AnsiPrinter;

/**
 * Sends its input to an ANSI printer (such as the standard output)
 * 
 * @author Sylvain Hallé
 */
public class Print extends Sink
{
	/**
	 * The stream to print to
	 */
	protected AnsiPrinter m_out;

	/**
	 * Creates a new printer
	 */
	@SuppressWarnings("squid:S106")
	public Print()
	{
		this(1, new AnsiPrinter(System.out));
	}

	/**
	 * Creates a new ANSI printer of given input arity
	 * @param in_arity The input arity
	 * @param out The ANSI printer to use
	 */
	public Print(int in_arity, AnsiPrinter out)
	{
		super(in_arity);
		m_out = out;
	}

	@Override
	@SuppressWarnings("squid:S1168")
	protected boolean compute(Object[] inputs, Queue<Object[]> outputs)
	{
		if (inputs == null || allNull(inputs))
		{
			return false;
		}
		Object o = inputs[0];
		if (o != null)
		{
			m_out.setForegroundColor(AnsiPrinter.Color.LIGHT_GRAY);
			prettyPrint(o);
			m_out.setForegroundColor(AnsiPrinter.Color.RED);
			m_out.print(",");
		}
		return true;
	}

	@SuppressWarnings("squid:S106")
	public static void build(ArrayDeque<Object> stack) throws ConnectorException
	{
		Processor p = (Processor) stack.pop();
		stack.pop(); // PRINT
		Print out = new Print(1, new AnsiPrinter(System.out));
		Connector.connect(p, out);
		stack.push(out);
	}

	/**
	 * Prints an object in an eye-pleasing way
	 * @param n The object
	 */
	protected void prettyPrint(Object o)
	{
		if (o instanceof Number)
		{
			prettyPrint((Number) o);
		}
		else
		{
			m_out.print(o);
		}
	}

	/**
	 * Prints a number in an eye-pleasing way. In this case, the
	 * printer trims the decimals from a number if it is an integer
	 * @param n The number
	 */
	protected void prettyPrint(Number n)
	{
		if (n.floatValue() == Math.round(n.floatValue()))
		{
			m_out.print(n.intValue());
		}
		else
		{
			m_out.print(n);
		}
	}

	@Override
	public Print clone()
	{
		return new Print(getInputArity(), m_out);
	}
}