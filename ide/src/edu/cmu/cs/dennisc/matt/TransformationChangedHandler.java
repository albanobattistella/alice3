﻿package edu.cmu.cs.dennisc.matt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.lgna.story.Entity;
import org.lgna.story.ImplementationAccessor;
import org.lgna.story.MovableTurnable;
import org.lgna.story.MultipleEventPolicy;
import org.lgna.story.Visual;
import org.lgna.story.event.AbstractEvent;
import org.lgna.story.implementation.AbstractTransformableImp;
import org.lgna.story.implementation.EntityImp;

import edu.cmu.cs.dennisc.java.util.Collections;
import edu.cmu.cs.dennisc.scenegraph.event.AbsoluteTransformationEvent;
import edu.cmu.cs.dennisc.scenegraph.event.AbsoluteTransformationListener;

public abstract class TransformationChangedHandler<L, E extends AbstractEvent> extends AbstractEventHandler<L,E> implements AbsoluteTransformationListener {

	HashMap<Visual,LinkedList<Object>> eventMap = new HashMap<Visual,LinkedList<Object>>();
	List<L> listenerList = Collections.newLinkedList();
	List<Entity> modelList = Collections.newLinkedList();
	private Map<Object,Class[]> checkNewMap = Collections.newHashMap();

	public final void fireAllTargeted( Entity changedEntity ) {
		if( shouldFire ) {
			check( changedEntity );
		}
	}

	public void instanceCreated( AbstractTransformableImp created ) {
		for( Object key : checkNewMap.keySet() ) {
			for( int i = 0; i != checkNewMap.get( key ).length; ++i ) {
				Class cls = checkNewMap.get( key )[ i ];
				if( cls != null ) {
					Entity abstraction = created.getAbstraction();
					if( checkNewMap.get( key )[ 1 - i ] == null || cls.isAssignableFrom( abstraction.getClass() ) && !checkNewMap.get( key )[ 1 - i ].isAssignableFrom( cls ) ) {
						ammend( key, i, abstraction );
					}
				}
			}
		}
	}

	protected abstract void ammend( Object key, int i, Entity newObject );

	protected abstract void check( Entity changedEntity );

	protected <A extends MovableTurnable, B extends MovableTurnable> ArrayList[] addPairedListener( Object listener, ArrayList<A> groupOne, Class<A> a, ArrayList<B> groupTwo, Class<B> b, MultipleEventPolicy policy ) {

		registerIsFiringMap( listener );
		registerPolicyMap( (L)listener, policy );
		Class<?>[] clsArr = { a, b };
		ArrayList<ArrayList<? extends Object>> list;
		ArrayList<?> firstList;
		ArrayList<?> secondList;
		Class[] checkClassArr = { null, null };
		if( groupOne == null && groupTwo == null ) {
			firstList = scene.findAll( a );
			secondList = scene.findAll( b );
			checkClassArr[ 0 ] = a;
			checkClassArr[ 1 ] = b;
			if( a.equals( b ) ) {
				System.out.println( a + " equals " + b );
				System.out.println( "WHAT SHOULD WE DO IF AN OBJECT EXISTS IN BOTH LISTS?" );
			} else if( a.isAssignableFrom( b ) ) {
				firstList.removeAll( secondList );
			} else if( b.isAssignableFrom( a ) ) {
				secondList.removeAll( firstList );
			}
		} else if( groupOne == null && groupTwo != null ) {
			firstList = scene.findAll( a );
			secondList = groupTwo;
			firstList.removeAll( secondList );
			checkClassArr[ 0 ] = a;
		} else if( groupOne != null && groupTwo == null ) {
			firstList = groupOne;
			secondList = scene.findAll( b );
			checkClassArr[ 1 ] = b;
			secondList.removeAll( firstList );
		} else {// if( arrayList != null && arrayList2 != null ) {
			firstList = groupOne;
			secondList = groupTwo;
			for( Object o : secondList ) {
				if( firstList.contains( o ) ) {
					System.out.println( "WHAT SHOULD WE DO IF AN OBJECT EXISTS IN BOTH LISTS?" );
				}
			}
		}
		if( checkClassArr[ 0 ] != null || checkClassArr[ 1 ] != null ) {
			checkNewMap.put( listener, checkClassArr );
		}
		list = Collections.newArrayList( firstList, secondList );
		EventBuilder.register( listener, clsArr, list );
		List<Entity> allObserving = (List<Entity>)Collections.newArrayList( (Collection<? extends Entity>)firstList );
		allObserving.addAll( (Collection<? extends Entity>)secondList );
		for( Entity m : allObserving ) {
			if( !modelList.contains( m ) ) {
				modelList.add( m );
				ImplementationAccessor.getImplementation( m ).getSgComposite().addAbsoluteTransformationListener( this );
			}
		}
		ArrayList[] rv = { firstList, secondList };
		return rv;
	}

	public final void absoluteTransformationChanged( AbsoluteTransformationEvent absoluteTransformationEvent ) {
		Entity source = EntityImp.getAbstractionFromSgElement( absoluteTransformationEvent.getTypedSource() );
		fireAllTargeted( source );
		//		if( source instanceof Turnable ) {
		//			fireAllTargeted( (Turnable)source );
		//		} else {
		//			Logger.severe( source );
		//		}
	}
}
