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
public abstract class CustomItemState<T> extends ItemState<T> {
	public static class InternalRootResolver<T> extends IndirectResolver<InternalRoot<T>, CustomItemState<T>> {
		private InternalRootResolver( CustomItemState<T> indirect ) {
			super( indirect );
		}

		public InternalRootResolver( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
			super( binaryDecoder );
		}

		@Override
		protected InternalRoot<T> getDirect( CustomItemState<T> indirect ) {
			return indirect.getCascadeRoot();
		}
	}

	public static class InternalRoot<T> extends org.lgna.croquet.CascadeRoot<T, CustomItemState<T>> {
		private final CustomItemState<T> state;

		private InternalRoot( CustomItemState<T> state ) {
			super( java.util.UUID.fromString( "8a973789-9896-443f-b701-4a819fc61d46" ) );
			this.state = state;
		}

		@Override
		public java.util.List<? extends CascadeBlank<T>> getBlanks() {
			return this.state.getBlanks();
		}

		@Override
		protected InternalRootResolver<T> createResolver() {
			return new InternalRootResolver<T>( this.state );
		}

		@Override
		public org.lgna.croquet.history.CompletionStep<CustomItemState<T>> createCompletionStep( org.lgna.croquet.history.Transaction transaction, org.lgna.croquet.triggers.Trigger trigger ) {
			return org.lgna.croquet.history.CompletionStep.createAndAddToTransaction( transaction, this.state, trigger, null );
		}

		@Override
		public Class<T> getComponentType() {
			return this.state.getItemCodec().getValueClass();
		}

		@Override
		public CustomItemState<T> getCompletionModel() {
			return this.state;
		}

		@Override
		protected void prologue( org.lgna.croquet.triggers.Trigger trigger ) {
			super.prologue( trigger );
			this.state.prologue( trigger );
		}

		@Override
		protected void epilogue() {
			this.state.epilogue();
			super.epilogue();
		}

		@Override
		public org.lgna.croquet.history.CompletionStep handleCompletion( org.lgna.croquet.history.TransactionHistory transactionHistory, org.lgna.croquet.triggers.Trigger trigger, org.lgna.croquet.cascade.RtRoot<T, org.lgna.croquet.CustomItemState<T>> rtRoot ) {
			try {
				//todo: investigate
				org.lgna.croquet.history.Transaction transaction = transactionHistory.acquireActiveTransaction();
				org.lgna.croquet.history.CompletionStep<CustomItemState<T>> completionStep = this.createCompletionStep( transaction, trigger );
				org.lgna.croquet.history.TransactionHistory subTransactionHistory = completionStep.getTransactionHistory();
				T[] values = rtRoot.createValues( subTransactionHistory, this.getComponentType() );
				return this.state.changeValueFromIndirectModel( values[ 0 ], IsAdjusting.FALSE, trigger );
			} finally {
				this.getPopupPrepModel().handleFinally();
			}
		}
	}

	private final InternalRoot<T> root;

	public CustomItemState( org.lgna.croquet.Group group, java.util.UUID id, T initialValue, org.lgna.croquet.ItemCodec<T> itemCodec ) {
		super( group, id, initialValue, itemCodec );
		this.root = new InternalRoot<T>( this );
	}

	protected abstract java.util.List<? extends CascadeBlank<T>> getBlanks();

	public InternalRoot<T> getCascadeRoot() {
		return this.root;
	}

	@Override
	public Iterable<? extends PrepModel> getPotentialRootPrepModels() {
		return edu.cmu.cs.dennisc.java.util.Collections.newArrayList( this.root.getPopupPrepModel() );
	}

	@Override
	protected void localize() {
	}

	protected void prologue( org.lgna.croquet.triggers.Trigger trigger ) {
	}

	protected void epilogue() {
	}

	@Override
	public org.lgna.croquet.history.Transaction addGeneratedStateChangeTransaction( org.lgna.croquet.history.TransactionHistory history, T prevValue, T nextValue ) throws UnsupportedGenerationException {
		org.lgna.croquet.history.Transaction rv = super.addGeneratedStateChangeTransaction( history, prevValue, nextValue );
		org.lgna.croquet.history.PopupPrepStep.createAndAddToTransaction( rv, this.getCascadeRoot().getPopupPrepModel(), org.lgna.croquet.triggers.ActionEventTrigger.createGeneratorInstance() );
		return rv;
	}

	@Override
	public boolean isEnabled() {
		return this.getCascadeRoot().getPopupPrepModel().isEnabled();
	}

	@Override
	public void setEnabled( boolean isEnabled ) {
		this.getCascadeRoot().getPopupPrepModel().setEnabled( isEnabled );
	}
}
