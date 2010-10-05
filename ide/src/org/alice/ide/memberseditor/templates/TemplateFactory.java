/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may 
 *    "Alice" appear in their name, without prior written permission of 
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software 
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is 
 *    contributed by Electronic Arts Inc. and may be used for personal, 
 *    non-commercial, and academic use only. Redistributions of any program 
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in 
 *    The Alice 3.0 Art Gallery License.
 * 
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A 
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO 
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.alice.ide.memberseditor.templates;

/**
 * @author Dennis Cosgrove
 */
public class TemplateFactory {
	private TemplateFactory() {
		throw new AssertionError();
	}
	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.AbstractMethod, edu.cmu.cs.dennisc.croquet.DragComponent > mapMethodToProcedureInvocationTemplate = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.AbstractMethod, edu.cmu.cs.dennisc.croquet.DragComponent > mapMethodToFunctionInvocationTemplate = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();

	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.AbstractField, edu.cmu.cs.dennisc.croquet.DragComponent > mapMethodToAccessorTemplate = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.AbstractField, edu.cmu.cs.dennisc.croquet.DragComponent > mapMethodToAccessArrayAtIndexTemplate = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.AbstractField, edu.cmu.cs.dennisc.croquet.DragComponent > mapMethodToArrayLengthTemplate = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	
	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.AbstractField, edu.cmu.cs.dennisc.croquet.DragComponent > mapMethodToMutatorTemplate = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private static java.util.Map< edu.cmu.cs.dennisc.alice.ast.AbstractField, edu.cmu.cs.dennisc.croquet.DragComponent > mapMethodToMutateArrayAtIndexTemplate = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	
	public static edu.cmu.cs.dennisc.croquet.DragComponent getProcedureInvocationTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractMethod method ) {
		edu.cmu.cs.dennisc.croquet.DragComponent rv = mapMethodToProcedureInvocationTemplate.get( method );
		if( rv != null ) {
			//pass
		} else {
			rv = new org.alice.ide.memberseditor.templates.ProcedureInvocationTemplate( method );
			mapMethodToProcedureInvocationTemplate.put( method, rv );
		}
		return rv;
	}
	public static edu.cmu.cs.dennisc.croquet.DragComponent getFunctionInvocationTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractMethod method ) {
		edu.cmu.cs.dennisc.croquet.DragComponent rv = mapMethodToFunctionInvocationTemplate.get( method );
		if( rv != null ) {
			
		} else {
			rv = new org.alice.ide.memberseditor.templates.FunctionInvocationTemplate( method );
			mapMethodToFunctionInvocationTemplate.put( method, rv );
		}
		return rv;
	}
	public static edu.cmu.cs.dennisc.croquet.DragComponent getAccessorTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractField field ) {
		edu.cmu.cs.dennisc.croquet.DragComponent rv = mapMethodToAccessorTemplate.get( field );
		if( rv != null ) {
			
		} else {
			rv = new org.alice.ide.memberseditor.templates.GetterTemplate( field );
			mapMethodToAccessorTemplate.put( field, rv );
		}
		return rv;
	}
	public static edu.cmu.cs.dennisc.croquet.DragComponent getAccessArrayAtIndexTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractField field ) {
		edu.cmu.cs.dennisc.croquet.DragComponent rv = mapMethodToAccessArrayAtIndexTemplate.get( field );
		if( rv != null ) {
			
		} else {
			rv = new org.alice.ide.memberseditor.templates.AccessArrayAtIndexTemplate( field );
			mapMethodToAccessArrayAtIndexTemplate.put( field, rv );
		}
		return rv;
	}
	public static edu.cmu.cs.dennisc.croquet.DragComponent getArrayLengthTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractField field ) {
		edu.cmu.cs.dennisc.croquet.DragComponent rv = mapMethodToArrayLengthTemplate.get( field );
		if( rv != null ) {
			
		} else {
			rv = new org.alice.ide.memberseditor.templates.ArrayLengthTemplate( field );
			mapMethodToArrayLengthTemplate.put( field, rv );
		}
		return rv;
	}
	
	public static edu.cmu.cs.dennisc.croquet.DragComponent getMutatorTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractField field ) {
		edu.cmu.cs.dennisc.croquet.DragComponent rv = mapMethodToMutatorTemplate.get( field );
		if( rv != null ) {
			
		} else {
			rv = new org.alice.ide.memberseditor.templates.SetterTemplate( field );
			mapMethodToMutatorTemplate.put( field, rv );
		}
		return rv;
	}
	public static edu.cmu.cs.dennisc.croquet.DragComponent getMutateArrayAtIndexTemplate( edu.cmu.cs.dennisc.alice.ast.AbstractField field ) {
		edu.cmu.cs.dennisc.croquet.DragComponent rv = mapMethodToMutateArrayAtIndexTemplate.get( field );
		if( rv != null ) {
			
		} else {
			rv = new org.alice.ide.memberseditor.templates.SetArrayAtIndexTemplate( field );
			mapMethodToMutateArrayAtIndexTemplate.put( field, rv );
		}
		return rv;
	}
	
}
