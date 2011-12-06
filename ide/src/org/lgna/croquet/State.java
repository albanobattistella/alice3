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
package org.lgna.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class State<T> extends CompletionModel {
	public static interface ValueObserver<T> {
		public void changing( State< T > state, T prevValue, T nextValue, boolean isAdjusting );
		public void changed( State< T > state, T prevValue, T nextValue, boolean isAdjusting );
	};
	private final java.util.List< ValueObserver<T> > valueObservers = edu.cmu.cs.dennisc.java.util.concurrent.Collections.newCopyOnWriteArrayList();
	private T prevValueForSkipCheck;
	public State( Group group, java.util.UUID id, T initialValue ) {
		super(group, id);
		this.prevValueForSkipCheck = initialValue;
	}
	public void addValueObserver( ValueObserver<T> valueObserver ) {
		assert this.valueObservers.contains( valueObserver ) == false : valueObserver;
		this.valueObservers.add( valueObserver );
	}
	@Deprecated
	public void addAndInvokeValueObserver( ValueObserver<T> valueObserver ) {
		this.addValueObserver( valueObserver );
		valueObserver.changed( this, this.getValue(), this.getValue(), false );
	}
	public void removeValueObserver( ValueObserver<T> valueObserver ) {
		if( this.valueObservers.contains( valueObserver ) ) {
			//pass
		} else {
			edu.cmu.cs.dennisc.java.util.logging.Logger.severe( "listener not contained ", valueObserver );
		}
		this.valueObservers.remove( valueObserver );
	}
	protected void fireChanging( T prevValue, T nextValue, boolean isAdjusting ) {
		this.isInTheMidstOfChanging = true;
		this.prevValueForChanging = prevValue;
		try {
			for( ValueObserver<T> valueObserver : this.valueObservers ) {
				valueObserver.changing( this, prevValue, nextValue, isAdjusting );
			}
		} finally {
			this.prevValueForChanging = null;
			this.isInTheMidstOfChanging = false;
		}
	}
	protected void fireChanged( T prevValue, T nextValue, boolean isAdjusting ) {
		for( ValueObserver<T> valueObserver : this.valueObservers ) {
			valueObserver.changed( this, prevValue, nextValue, isAdjusting );
		}
	}
	
	@Override
	public org.lgna.croquet.history.Step<?> fire(org.lgna.croquet.triggers.Trigger trigger) {
		throw new UnsupportedOperationException();
	}
	
	protected abstract void commitStateEdit( T prevValue, T nextValue, boolean isAdjusting, org.lgna.croquet.triggers.Trigger trigger );
	protected abstract void updateSwingModel( T nextValue );
	private void changeValue( T nextValue, boolean isAdjusting, org.lgna.croquet.triggers.Trigger trigger, boolean isFromSwing ) {
		if( edu.cmu.cs.dennisc.equivalence.EquivalenceUtilities.areEquivalent( this.prevValueForSkipCheck, nextValue ) ) {
			//pass
		} else {
			T prevValue = this.prevValueForSkipCheck;
			this.prevValueForSkipCheck = nextValue;
			this.fireChanging( prevValue, nextValue, isAdjusting );
			if( isFromSwing ) {
				//pass
			} else {
				this.updateSwingModel( nextValue );
			}
			if( this.isAppropriateToComplete() ) {
				this.commitStateEdit( prevValue, nextValue, isAdjusting, trigger );
			}
			//todo?
//			for( org.lgna.croquet.components.JComponent< ? > component : this.getComponents() ) {
//				component.revalidateAndRepaint();
//			}
			this.fireChanged( prevValue, nextValue, isAdjusting );
		}
		
	}
	protected final void changeValue( T nextValue, boolean isAdjusting, org.lgna.croquet.triggers.Trigger trigger ) {
		this.changeValue( nextValue, isAdjusting, trigger, false );
	}
	protected final void changeValueFromSwing( T nextValue, boolean isAdjusting, org.lgna.croquet.triggers.Trigger trigger ) {
		this.changeValue( nextValue, isAdjusting, trigger, true );
	}
	
	private T prevValueForChanging;
	private boolean isInTheMidstOfChanging = false;
	protected abstract T getActualValue();
	public final T getValue() {
		if( this.isInTheMidstOfChanging ) {
			return this.prevValueForChanging;
		} else {
			return this.getActualValue();
		}
	}
	public final void setValue( T value ) {
		this.changeValue( value, false, null );
	}

	private void changeValueTransactionlessly( T value, boolean isAdjusting ) {
		this.pushIgnore();
		try {
			this.changeValue( value, isAdjusting, null );
		} finally {
			this.popIgnore();
		}
	}

	public final void setValueTransactionlessly( T value ) {
		this.changeValueTransactionlessly( value, false );
	}
	public final void adjustValueTransactionlessly( T value ) {
		this.changeValueTransactionlessly( value, true );
	}
	@Override
	public boolean isAlreadyInState( org.lgna.croquet.edits.Edit< ? > edit ) {
		if( edit instanceof org.lgna.croquet.edits.StateEdit ) {
			org.lgna.croquet.edits.StateEdit< ?, T > stateEdit = (org.lgna.croquet.edits.StateEdit< ?, T >)edit;
			T a = this.getValue();
			T b = stateEdit.getNextValue();
			return edu.cmu.cs.dennisc.equivalence.EquivalenceUtilities.areEquivalent( a, b );
		} else {
			return false;
		}
	}
}
