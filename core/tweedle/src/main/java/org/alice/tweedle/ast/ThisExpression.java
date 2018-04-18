package org.alice.tweedle.ast;

import org.alice.tweedle.TweedleType;
import org.alice.tweedle.TweedleValue;
import org.alice.tweedle.run.Frame;

public class ThisExpression extends TweedleExpression {

	public ThisExpression( TweedleType resultType ) {
		super( resultType );
	}

	@Override public TweedleValue evaluate( Frame frame ) {
		return null;

		// TODO track on execution frame and read from there
		// return frame.getThis();
	}
}
