/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
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
 *******************************************************************************/
package org.alice.stageide.sceneeditor.interact.croquet;

import org.alice.ide.instancefactory.ThisFieldAccessFactory;
import org.alice.stageide.ast.sort.OneShotSorter;
import org.alice.stageide.sceneeditor.StorytellingSceneEditor;
import org.alice.stageide.sceneeditor.interact.croquet.edits.GetAGoodLookAtEdit;
import org.lgna.croquet.ActionOperation;
import org.lgna.croquet.Group;
import org.lgna.croquet.history.UserActivity;
import org.lgna.project.ast.Expression;
import org.lgna.project.ast.FieldAccess;
import org.lgna.project.ast.UserField;
import org.lgna.story.SCamera;
import org.lgna.story.SThing;

import java.util.UUID;

/**
 * @author dculyba
 *
 */
public class GetAGoodLookAtActionOperation extends ActionOperation {

  private final SCamera camera;
  private final SThing toLookAt;

  public GetAGoodLookAtActionOperation(Group group, SCamera camera, SThing toLookAt) {
    super(group, UUID.fromString("566dedf3-e612-4eed-8025-a49763feeeb4"));
    this.camera = camera;
    this.toLookAt = toLookAt;
  }

  public static boolean IsValidOperation(SCamera camera, SThing toLookAt) {
    UserField cameraField = StorytellingSceneEditor.getInstance().getFieldForInstanceInJavaVM(camera);
    UserField toLookAtField = StorytellingSceneEditor.getInstance().getFieldForInstanceInJavaVM(toLookAt);
    if ((cameraField == null) || (toLookAtField == null) || (cameraField == toLookAtField)) {
      return false;
    }
    return true;
  }

  @Override
  protected void perform(UserActivity activity) {
    UserField cameraField = StorytellingSceneEditor.getInstance().getFieldForInstanceInJavaVM(this.camera);
    UserField toLookAtField = StorytellingSceneEditor.getInstance().getFieldForInstanceInJavaVM(this.toLookAt);
    ThisFieldAccessFactory cameraInstanceFactory = ThisFieldAccessFactory.getInstance(cameraField);
    Expression[] toLookAtExpressions = {new FieldAccess(toLookAtField)};
    GetAGoodLookAtEdit edit = new GetAGoodLookAtEdit(activity, cameraInstanceFactory, OneShotSorter.MOVE_AND_ORIENT_TO_A_GOOD_VANTAGE_POINT_METHOD, toLookAtExpressions, camera, toLookAt);
    activity.commitAndInvokeDo(edit);
  }
}
