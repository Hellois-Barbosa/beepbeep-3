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
package ca.uqac.lif.cep.tmf;


import ca.uqac.lif.cep.UniformProcessor;

/**
 * Converts <i>n</i> input traces into a single output trace, whose events are
 * arrays of <i>n</i> elements
 * @author Sylvain Hallé
 */
public class NaryToArray extends UniformProcessor
{
	public NaryToArray(int in_arity)
	{
		super(in_arity, 1);
	}

	@Override
	protected boolean compute(Object[] inputs, Object[] outputs)
	{
		outputs[0] = wrapObject(inputs);
		return true;
	}

	@Override
	public NaryToArray clone()
	{
		return new NaryToArray(getInputArity());
	}
}