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

class TreeNodeUtilities {
	private TreeNodeUtilities() {
		throw new AssertionError();
	}

	public static <T> java.util.List<org.lgna.croquet.CascadeBlankChild> updateBlankChildren( java.util.List<org.lgna.croquet.CascadeBlankChild> rv, org.lgna.croquet.cascade.BlankNode<T> blankNode, TreeSelectionState<T> model, T node ) {
		for( T childNode : model.getChildren( node ) ) {
			CascadeBlankChild<T> child = model.getBlankChildFor( childNode );
			if( child != null ) {
				rv.add( child );
			}
		}
		return rv;
	}

}

class TreeNodeFillIn<T> extends CascadeFillIn<T, Void> {
	private static edu.cmu.cs.dennisc.map.MapToMap<TreeSelectionState, Object, TreeNodeFillIn> mapToMap = edu.cmu.cs.dennisc.map.MapToMap.newInstance();

	public static synchronized <T> TreeNodeFillIn<T> getInstance( TreeSelectionState<T> model, T node ) {
		return mapToMap.getInitializingIfAbsent( model, node, new edu.cmu.cs.dennisc.map.MapToMap.Initializer<TreeSelectionState, Object, TreeNodeFillIn>() {
			public TreeNodeFillIn<T> initialize( TreeSelectionState model, Object node ) {
				return new TreeNodeFillIn<T>( model, (T)node );
			}
		} );
	}

	private final TreeSelectionState<T> model;
	private final T node;

	private TreeNodeFillIn( TreeSelectionState<T> model, T node ) {
		super( java.util.UUID.fromString( "db052fcb-b0e3-482a-aad9-13b9a2efc370" ) );
		this.model = model;
		this.node = node;
	}

	@Override
	public T getTransientValue( org.lgna.croquet.cascade.ItemNode<? super T, Void> node ) {
		return this.node;
	}

	@Override
	public T createValue( org.lgna.croquet.cascade.ItemNode<? super T, Void> node, org.lgna.croquet.history.TransactionHistory transactionHistory ) {
		return this.node;
	}

	@Override
	public String getMenuItemText( org.lgna.croquet.cascade.ItemNode<? super T, Void> node ) {
		return this.model.getTextForNode( this.node );
	}

	@Override
	public javax.swing.Icon getMenuItemIcon( org.lgna.croquet.cascade.ItemNode<? super T, Void> node ) {
		return this.model.getIconForNode( this.node );
	}

	@Override
	protected javax.swing.JComponent createMenuItemIconProxy( org.lgna.croquet.cascade.ItemNode<? super T, Void> node ) {
		throw new AssertionError();
	}

	@Override
	protected String getTutorialItemText() {
		return this.node.toString();
	}
}

class TreeNodeMenu<T> extends CascadeMenuModel<T> {
	private static edu.cmu.cs.dennisc.map.MapToMap<TreeSelectionState, Object, TreeNodeMenu> mapToMap = edu.cmu.cs.dennisc.map.MapToMap.newInstance();

	public static synchronized <T> TreeNodeMenu<T> getInstance( TreeSelectionState<T> model, T node ) {
		return mapToMap.getInitializingIfAbsent( model, node, new edu.cmu.cs.dennisc.map.MapToMap.Initializer<TreeSelectionState, Object, TreeNodeMenu>() {
			public TreeNodeMenu<T> initialize( TreeSelectionState model, Object node ) {
				return new TreeNodeMenu<T>( model, (T)node );
			}
		} );
	}

	private final TreeSelectionState<T> model;
	private final T node;

	private TreeNodeMenu( TreeSelectionState<T> model, T node ) {
		super( java.util.UUID.fromString( "3836e893-73c9-4490-9a2a-1cb8a50311e0" ) );
		this.model = model;
		this.node = node;
	}

	@Override
	protected String findDefaultLocalizedText() {
		return null;
	}

	@Override
	protected java.util.List<org.lgna.croquet.CascadeBlankChild> updateBlankChildren( java.util.List<org.lgna.croquet.CascadeBlankChild> rv, org.lgna.croquet.cascade.BlankNode<T> blankNode ) {
		return TreeNodeUtilities.updateBlankChildren( rv, blankNode, this.model, this.node );
	}

}

class TreeBlank<T> extends CascadeBlank<T> {
	private final TreeSelectionState<T> model;
	private final T node;

	public TreeBlank( TreeSelectionState<T> model, T node ) {
		super( java.util.UUID.fromString( "7c277038-5aa8-4429-9642-8dc3890aee5b" ) );
		this.model = model;
		this.node = node;
	}

	@Override
	protected java.util.List<org.lgna.croquet.CascadeBlankChild> updateChildren( java.util.List<org.lgna.croquet.CascadeBlankChild> rv, org.lgna.croquet.cascade.BlankNode<T> blankNode ) {
		return TreeNodeUtilities.updateBlankChildren( rv, blankNode, this.model, this.node );
	}
}

class TreeNodeCascade<T> extends Cascade<T> {
	private static edu.cmu.cs.dennisc.map.MapToMap<TreeSelectionState, Object, TreeNodeCascade> mapToMap = edu.cmu.cs.dennisc.map.MapToMap.newInstance();

	public static <T> TreeNodeCascade<T> getInstance( TreeSelectionState<T> model, T node ) {
		return mapToMap.getInitializingIfAbsent( model, node, new edu.cmu.cs.dennisc.map.MapToMap.Initializer<TreeSelectionState, Object, TreeNodeCascade>() {
			public TreeNodeCascade<T> initialize( TreeSelectionState model, Object node ) {
				return new TreeNodeCascade<T>( model, (T)node );
			}
		} );
	}

	private final TreeSelectionState<T> model;
	private final T node;

	public TreeNodeCascade( TreeSelectionState<T> model, T node ) {
		super( model.getGroup(), java.util.UUID.fromString( "dcbf42f7-cd25-4061-a8f6-abcb1b47fe41" ), model.getItemCodec().getValueClass(), new TreeBlank<T>( model, node ) );
		this.model = model;
		this.node = node;
	}

	@Override
	protected org.lgna.croquet.edits.Edit createEdit( org.lgna.croquet.history.CompletionStep<org.lgna.croquet.Cascade<T>> completionStep, T[] values ) {
		assert values.length == 1;
		this.model.changeValueFromIndirectModel( values[ 0 ], org.lgna.croquet.State.IsAdjusting.FALSE, org.lgna.croquet.triggers.NullTrigger.createUserInstance() );
		return null;
		//return new org.lgna.croquet.edits.TreeSelectionStateEdit< T >( completionStep, this.model, this.model.getSelectedNode(), values[ 0 ] );
	}
}

/**
 * @author Dennis Cosgrove
 */
public abstract class TreeSelectionState<T> extends ItemState<T> {
	private class SingleTreeSelectionModel extends javax.swing.tree.DefaultTreeSelectionModel {
	}

	public class SwingModel {
		private final SingleTreeSelectionModel treeSelectionModel = new SingleTreeSelectionModel();

		private SwingModel() {
		}

		public javax.swing.tree.TreeSelectionModel getTreeSelectionModel() {
			return this.treeSelectionModel;
		}
	}

	private final SwingModel swingModel = new SwingModel();
	private final javax.swing.event.TreeSelectionListener treeSelectionListener = new javax.swing.event.TreeSelectionListener() {
		public void valueChanged( javax.swing.event.TreeSelectionEvent e ) {
			T nextValue = getSelectedNode();
			TreeSelectionState.this.changeValueFromSwing( nextValue, IsAdjusting.FALSE, org.lgna.croquet.triggers.TreeSelectionEventTrigger.createUserInstance( e ) );
			T prevValue = getValue();
			fireChanged( prevValue, nextValue, IsAdjusting.FALSE );
		}
	};

	public TreeSelectionState( Group group, java.util.UUID id, T initialValue, ItemCodec<T> itemCodec ) {
		super( group, id, initialValue, itemCodec );
		this.swingModel.treeSelectionModel.addTreeSelectionListener( this.treeSelectionListener );
	}

	@Override
	public Iterable<? extends PrepModel> getPotentialRootPrepModels() {
		return java.util.Collections.emptyList();
	}

	public SwingModel getSwingModel() {
		return this.swingModel;
	}

	@Override
	protected void localize() {
	}

	protected abstract String getTextForNode( T node );

	protected abstract javax.swing.Icon getIconForNode( T node );

	public abstract edu.cmu.cs.dennisc.javax.swing.models.TreeModel<T> getTreeModel();

	public abstract void refresh( T node );

	public final void refreshAll() {
		this.refresh( this.getTreeModel().getRoot() );
	}

	public T getSelectedNode() {
		javax.swing.tree.TreePath path = this.swingModel.treeSelectionModel.getSelectionPath();
		if( path != null ) {
			return (T)path.getLastPathComponent();
		} else {
			return null;
		}
	}

	private void setSelectedNode( T e ) {
		this.swingModel.treeSelectionModel.setSelectionPath( this.getTreeModel().getTreePath( e ) );
	}

	@Override
	protected T getSwingValue() {
		return this.getSelectedNode();
	}

	@Override
	protected void setSwingValue( T nextValue ) {
		this.setSelectedNode( nextValue );
	}

	public java.util.List<T> getChildren( T node ) {
		edu.cmu.cs.dennisc.javax.swing.models.TreeModel<T> treeModel = this.getTreeModel();
		final int N = treeModel.getChildCount( node );
		java.util.List<T> rv = edu.cmu.cs.dennisc.java.util.Collections.newArrayListWithInitialCapacity( N );
		for( int i = 0; i < N; i++ ) {
			rv.add( treeModel.getChild( node, i ) );
		}
		return rv;
	}

	public boolean isLeaf( T node ) {
		edu.cmu.cs.dennisc.javax.swing.models.TreeModel<T> treeModel = this.getTreeModel();
		return treeModel.isLeaf( node );
	}

	public T getParent( T node ) {
		edu.cmu.cs.dennisc.javax.swing.models.TreeModel<T> treeModel = this.getTreeModel();
		javax.swing.tree.TreePath treePath = treeModel.getTreePath( node );
		return (T)treePath.getParentPath().getLastPathComponent();
	}

	protected boolean isComboDesired( T childNode ) {
		return this.isLeaf( childNode ) == false;
	}

	protected CascadeBlankChild<T> getBlankChildFor( T childNode ) {
		CascadeBlankChild<T> blankChild;
		TreeNodeFillIn<T> fillIn = TreeNodeFillIn.getInstance( this, childNode );
		if( this.isComboDesired( childNode ) ) {
			blankChild = new CascadeItemMenuCombo<T>( fillIn, TreeNodeMenu.getInstance( this, childNode ) );
		} else {
			blankChild = fillIn;
		}
		return blankChild;
	}

	public Cascade<T> getCascadeFor( T node ) {
		return TreeNodeCascade.getInstance( this, node );
	}

	public org.lgna.croquet.components.Tree<T> createTree() {
		return new org.lgna.croquet.components.Tree<T>( this );
	}
}
