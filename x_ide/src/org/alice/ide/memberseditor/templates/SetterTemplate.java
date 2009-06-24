/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package org.alice.ide.memberseditor.templates;

/**
 * @author Dennis Cosgrove
 */
public class SetterTemplate extends ExpressionStatementTemplate {
	private edu.cmu.cs.dennisc.alice.ast.AbstractField field;
	public SetterTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractField field ) {
		this.field = field;
		if( this.field instanceof edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice ) {
			edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice fieldInAlice = (edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice)this.field;
			this.setPopupOperation( new FieldPopupOperation( fieldInAlice ) );
		}
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.Expression createIncompleteExpression() {
		return org.alice.ide.ast.NodeUtilities.createIncompleteAssignmentExpression( this.field );
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.AbstractType[] getBlankExpressionTypes() {
		return new edu.cmu.cs.dennisc.alice.ast.AbstractType[] { this.field.getDesiredValueType() };
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.Expression createExpression( edu.cmu.cs.dennisc.alice.ast.Expression... expressions ) {
		edu.cmu.cs.dennisc.alice.ast.AssignmentExpression rv = new edu.cmu.cs.dennisc.alice.ast.AssignmentExpression(
			this.field.getValueType(), 
			new edu.cmu.cs.dennisc.alice.ast.FieldAccess( getIDE().createInstanceExpression(), this.field ),
			edu.cmu.cs.dennisc.alice.ast.AssignmentExpression.Operator.ASSIGN,
			expressions[ 0 ] 
		);
		return rv;
	}
}
