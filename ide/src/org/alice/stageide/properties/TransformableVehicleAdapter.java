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

package org.alice.stageide.properties;

import java.util.Locale;

import org.alice.apis.moveandturn.AsSeenBy;
import org.alice.apis.moveandturn.Composite;
import org.alice.apis.moveandturn.Transformable;
import org.alice.ide.IDE;
import org.alice.ide.properties.adapter.AbstractDoublePropertyAdapter;
import org.alice.ide.properties.adapter.AbstractPropertyAdapter;
import org.alice.ide.swing.icons.ColorIcon;
import org.alice.stageide.sceneeditor.MoveAndTurnSceneEditor;

import edu.cmu.cs.dennisc.alice.ast.FieldDeclaredInAlice;
import edu.cmu.cs.dennisc.color.Color4f;
import edu.cmu.cs.dennisc.croquet.Model;
import edu.cmu.cs.dennisc.croquet.Operation;
import edu.cmu.cs.dennisc.math.AffineMatrix4x4;
import edu.cmu.cs.dennisc.scenegraph.event.HierarchyEvent;
import edu.cmu.cs.dennisc.scenegraph.event.HierarchyListener;

public class TransformableVehicleAdapter extends AbstractPropertyAdapter<Composite, Transformable> {

	private HierarchyListener hierarchyListener;
	private edu.cmu.cs.dennisc.croquet.PopupMenuOperation popupMenuOperation;
	
	protected class SetVehicleOperation extends SetValueOperation
	{
		public SetVehicleOperation( Composite value, String name) {
			super( value, name, java.util.UUID.fromString( "981768b7-f40b-4363-b64f-34264be73651" ) );
			edu.cmu.cs.dennisc.alice.ast.AbstractField field = ((MoveAndTurnSceneEditor)IDE.getSingleton().getSceneEditor()).getFieldForInstanceInJava(value);
			if (field != null)
			{
				edu.cmu.cs.dennisc.alice.ast.AbstractType<?,?,?> valueType = field.getValueType();
				this.setSmallIcon( org.alice.stageide.gallerybrowser.ResourceManager.getSmallIconForType( valueType ) );
			}
		}
	}
	
	public TransformableVehicleAdapter(Transformable instance) 
	{
		super("Vehicle", instance);
	}
	

	private void initializeListenersIfNecessary()
	{
		if (this.hierarchyListener == null)
		{
			this.hierarchyListener = new HierarchyListener()
			{

				public void hierarchyChanged(HierarchyEvent hierarchyEvent) 
				{
					TransformableVehicleAdapter.this.handleHeirarchyChanged();
				}
			};
		}
	}
	
	protected void handleHeirarchyChanged()
	{
		this.notifyValueObservers(this.getValue());
	}
	
	@Override
	public Operation getEditOperation() 
	{
		if (this.popupMenuOperation == null)
		{
			this.popupMenuOperation = new edu.cmu.cs.dennisc.croquet.MenuModel( java.util.UUID.fromString( "2ae18028-e18a-47ad-8dda-ba6c186142a4" ) ) {
				@Override
				protected void handlePopupMenuPrologue(edu.cmu.cs.dennisc.croquet.PopupMenu popupMenu, edu.cmu.cs.dennisc.croquet.PopupMenuOperationContext context ) 
				{
					edu.cmu.cs.dennisc.croquet.ListSelectionState< edu.cmu.cs.dennisc.alice.ast.Accessible > possibleFields = org.alice.ide.IDE.getSingleton().getAccessibleListState();
					java.util.List<Model> setVehicleOperations = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
					
					Composite currentVehicle = TransformableVehicleAdapter.this.getValue();
					if (currentVehicle != null)
					{
						setVehicleOperations.add(new SetVehicleOperation(currentVehicle, TransformableVehicleAdapter.getNameForVehicle(currentVehicle)+TransformableVehicleAdapter.this.getCurrentValueLabelString()));
						setVehicleOperations.add(edu.cmu.cs.dennisc.croquet.MenuModel.SEPARATOR);
					}
					
					for (edu.cmu.cs.dennisc.alice.ast.Accessible field : possibleFields)
					{
						if (field instanceof FieldDeclaredInAlice)
						{
							Composite objectInJava = ((MoveAndTurnSceneEditor)IDE.getSingleton().getSceneEditor()).getInstanceInJavaForField((FieldDeclaredInAlice)field, Composite.class);
							if (objectInJava != null && objectInJava != TransformableVehicleAdapter.this.instance)
							{
								setVehicleOperations.add(new SetVehicleOperation(objectInJava, TransformableVehicleAdapter.getNameForVehicle(objectInJava)));
							}
						}
					}
					edu.cmu.cs.dennisc.croquet.MenuItemContainerUtilities.addMenuElements( popupMenu, setVehicleOperations );
				}
			}.getPopupMenuOperation();
		}
		
		// TODO Auto-generated method stub
		return this.popupMenuOperation;
	}

	public static String getNameForVehicle(Composite vehicle)
	{
		if (vehicle != null)
		{
			edu.cmu.cs.dennisc.alice.ast.AbstractField field = ((MoveAndTurnSceneEditor)IDE.getSingleton().getSceneEditor()).getFieldForInstanceInJava(vehicle);
			if (field != null)
			{
				edu.cmu.cs.dennisc.alice.ast.AbstractType<?,?,?> valueType = field.getValueType();
				return field.getName();
			}
			else
			{
				return vehicle.getName()+", "+vehicle.getClass().getSimpleName();
			}
		}
		else
		{
			return "No Vehicle";
		}
	}
	
	public static javax.swing.Icon getIconForVehicle(Composite vehicle)
	{
		if (vehicle != null)
		{
			edu.cmu.cs.dennisc.alice.ast.AbstractField field = ((MoveAndTurnSceneEditor)IDE.getSingleton().getSceneEditor()).getFieldForInstanceInJava(vehicle);
			if (field != null)
			{
				edu.cmu.cs.dennisc.alice.ast.AbstractType<?,?,?> valueType = field.getValueType();
				return org.alice.stageide.gallerybrowser.ResourceManager.getSmallIconForType(valueType);
			}
		}
		return null;
	}
	
	@Override
	protected String getUndoRedoDescription(Locale locale) 
	{
		return "Set Vehicle";
	}

	@Override
	public void setValue(Composite value) 
	{
		if (this.instance != null)
		{
			AffineMatrix4x4 currentPosition = this.instance.getTransformation(AsSeenBy.SCENE);
			this.instance.setVehicle(value);
			org.alice.apis.moveandturn.Scene scene = this.instance.getScene();
			assert scene != null;
			this.instance.moveAndOrientTo(scene.createOffsetStandIn(currentPosition), 0);
		}
	}

	public Class<Composite> getPropertyType() 
	{
		return Composite.class;
	}

	public Composite getValue() 
	{
		if (this.instance != null)
		{
			return this.instance.getVehicle();
		}
		return null;
	}

	@Override
	protected void startListening() 
	{
		if (this.instance != null && this.instance.getSGTransformable() != null)
		{
			this.initializeListenersIfNecessary();
			this.instance.getSGTransformable().addHierarchyListener(this.hierarchyListener);
		}
	}

	@Override
	protected void stopListening() 
	{
		if (this.instance != null && this.instance.getSGTransformable() != null)
		{
			this.instance.getSGTransformable().removeHierarchyListener(this.hierarchyListener);
		}
	}


}
