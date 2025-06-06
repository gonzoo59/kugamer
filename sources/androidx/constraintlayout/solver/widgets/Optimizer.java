package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: assets/adb/sincoserver.dex */
public class Optimizer {
    static final int FLAG_CHAIN_DANGLING = 1;
    static final int FLAG_RECOMPUTE_BOUNDS = 2;
    static final int FLAG_USE_OPTIMIZE = 0;
    public static final int OPTIMIZATION_BARRIER = 2;
    public static final int OPTIMIZATION_CHAIN = 4;
    public static final int OPTIMIZATION_DIMENSIONS = 8;
    public static final int OPTIMIZATION_DIRECT = 1;
    public static final int OPTIMIZATION_NONE = 0;
    public static final int OPTIMIZATION_RATIO = 16;
    public static final int OPTIMIZATION_STANDARD = 3;
    static boolean[] flags = new boolean[3];

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkMatchParent(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ConstraintWidget constraintWidget) {
        if (constraintWidgetContainer.mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int i = constraintWidget.mLeft.mMargin;
            int width = constraintWidgetContainer.getWidth() - constraintWidget.mRight.mMargin;
            constraintWidget.mLeft.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mLeft);
            constraintWidget.mRight.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mRight);
            linearSystem.addEquality(constraintWidget.mLeft.mSolverVariable, i);
            linearSystem.addEquality(constraintWidget.mRight.mSolverVariable, width);
            constraintWidget.mHorizontalResolution = 2;
            constraintWidget.setHorizontalDimension(i, width);
        }
        if (constraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || constraintWidget.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            return;
        }
        int i2 = constraintWidget.mTop.mMargin;
        int height = constraintWidgetContainer.getHeight() - constraintWidget.mBottom.mMargin;
        constraintWidget.mTop.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mTop);
        constraintWidget.mBottom.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBottom);
        linearSystem.addEquality(constraintWidget.mTop.mSolverVariable, i2);
        linearSystem.addEquality(constraintWidget.mBottom.mSolverVariable, height);
        if (constraintWidget.mBaselineDistance > 0 || constraintWidget.getVisibility() == 8) {
            constraintWidget.mBaseline.mSolverVariable = linearSystem.createObjectVariable(constraintWidget.mBaseline);
            linearSystem.addEquality(constraintWidget.mBaseline.mSolverVariable, constraintWidget.mBaselineDistance + i2);
        }
        constraintWidget.mVerticalResolution = 2;
        constraintWidget.setVerticalDimension(i2, height);
    }

    private static boolean optimizableMatchConstraint(ConstraintWidget constraintWidget, int i) {
        if (constraintWidget.mListDimensionBehaviors[i] != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            return false;
        }
        if (constraintWidget.mDimensionRatio != 0.0f) {
            if (constraintWidget.mListDimensionBehaviors[i != 0 ? (char) 0 : (char) 1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            }
            return false;
        }
        if (i == 0) {
            if (constraintWidget.mMatchConstraintDefaultWidth != 0 || constraintWidget.mMatchConstraintMinWidth != 0 || constraintWidget.mMatchConstraintMaxWidth != 0) {
                return false;
            }
        } else if (constraintWidget.mMatchConstraintDefaultHeight != 0 || constraintWidget.mMatchConstraintMinHeight != 0 || constraintWidget.mMatchConstraintMaxHeight != 0) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void analyze(int i, ConstraintWidget constraintWidget) {
        constraintWidget.updateResolutionNodes();
        ResolutionAnchor resolutionNode = constraintWidget.mLeft.getResolutionNode();
        ResolutionAnchor resolutionNode2 = constraintWidget.mTop.getResolutionNode();
        ResolutionAnchor resolutionNode3 = constraintWidget.mRight.getResolutionNode();
        ResolutionAnchor resolutionNode4 = constraintWidget.mBottom.getResolutionNode();
        boolean z = (i & 8) == 8;
        boolean z2 = constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && optimizableMatchConstraint(constraintWidget, 0);
        if (resolutionNode.type != 4 && resolutionNode3.type != 4) {
            if (constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED || (z2 && constraintWidget.getVisibility() == 8)) {
                if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget == null) {
                    resolutionNode.setType(1);
                    resolutionNode3.setType(1);
                    if (z) {
                        resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode3.dependsOn(resolutionNode, constraintWidget.getWidth());
                    }
                } else if (constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget == null) {
                    resolutionNode.setType(1);
                    resolutionNode3.setType(1);
                    if (z) {
                        resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode3.dependsOn(resolutionNode, constraintWidget.getWidth());
                    }
                } else if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget != null) {
                    resolutionNode.setType(1);
                    resolutionNode3.setType(1);
                    resolutionNode.dependsOn(resolutionNode3, -constraintWidget.getWidth());
                    if (z) {
                        resolutionNode.dependsOn(resolutionNode3, -1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode.dependsOn(resolutionNode3, -constraintWidget.getWidth());
                    }
                } else if (constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget != null) {
                    resolutionNode.setType(2);
                    resolutionNode3.setType(2);
                    if (z) {
                        constraintWidget.getResolutionWidth().addDependent(resolutionNode);
                        constraintWidget.getResolutionWidth().addDependent(resolutionNode3);
                        resolutionNode.setOpposite(resolutionNode3, -1, constraintWidget.getResolutionWidth());
                        resolutionNode3.setOpposite(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode.setOpposite(resolutionNode3, -constraintWidget.getWidth());
                        resolutionNode3.setOpposite(resolutionNode, constraintWidget.getWidth());
                    }
                }
            } else if (z2) {
                int width = constraintWidget.getWidth();
                resolutionNode.setType(1);
                resolutionNode3.setType(1);
                if (constraintWidget.mLeft.mTarget == null && constraintWidget.mRight.mTarget == null) {
                    if (z) {
                        resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode3.dependsOn(resolutionNode, width);
                    }
                } else if (constraintWidget.mLeft.mTarget == null || constraintWidget.mRight.mTarget != null) {
                    if (constraintWidget.mLeft.mTarget != null || constraintWidget.mRight.mTarget == null) {
                        if (constraintWidget.mLeft.mTarget != null && constraintWidget.mRight.mTarget != null) {
                            if (z) {
                                constraintWidget.getResolutionWidth().addDependent(resolutionNode);
                                constraintWidget.getResolutionWidth().addDependent(resolutionNode3);
                            }
                            if (constraintWidget.mDimensionRatio == 0.0f) {
                                resolutionNode.setType(3);
                                resolutionNode3.setType(3);
                                resolutionNode.setOpposite(resolutionNode3, 0.0f);
                                resolutionNode3.setOpposite(resolutionNode, 0.0f);
                            } else {
                                resolutionNode.setType(2);
                                resolutionNode3.setType(2);
                                resolutionNode.setOpposite(resolutionNode3, -width);
                                resolutionNode3.setOpposite(resolutionNode, width);
                                constraintWidget.setWidth(width);
                            }
                        }
                    } else if (z) {
                        resolutionNode.dependsOn(resolutionNode3, -1, constraintWidget.getResolutionWidth());
                    } else {
                        resolutionNode.dependsOn(resolutionNode3, -width);
                    }
                } else if (z) {
                    resolutionNode3.dependsOn(resolutionNode, 1, constraintWidget.getResolutionWidth());
                } else {
                    resolutionNode3.dependsOn(resolutionNode, width);
                }
            }
        }
        boolean z3 = constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && optimizableMatchConstraint(constraintWidget, 1);
        if (resolutionNode2.type == 4 || resolutionNode4.type == 4) {
            return;
        }
        if (constraintWidget.mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.FIXED && (!z3 || constraintWidget.getVisibility() != 8)) {
            if (z3) {
                int height = constraintWidget.getHeight();
                resolutionNode2.setType(1);
                resolutionNode4.setType(1);
                if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget == null) {
                    if (z) {
                        resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode4.dependsOn(resolutionNode2, height);
                    }
                } else if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget == null) {
                    if (z) {
                        resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode4.dependsOn(resolutionNode2, height);
                    }
                } else if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget != null) {
                    if (z) {
                        resolutionNode2.dependsOn(resolutionNode4, -1, constraintWidget.getResolutionHeight());
                    } else {
                        resolutionNode2.dependsOn(resolutionNode4, -height);
                    }
                } else if (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null) {
                } else {
                    if (z) {
                        constraintWidget.getResolutionHeight().addDependent(resolutionNode2);
                        constraintWidget.getResolutionWidth().addDependent(resolutionNode4);
                    }
                    if (constraintWidget.mDimensionRatio == 0.0f) {
                        resolutionNode2.setType(3);
                        resolutionNode4.setType(3);
                        resolutionNode2.setOpposite(resolutionNode4, 0.0f);
                        resolutionNode4.setOpposite(resolutionNode2, 0.0f);
                        return;
                    }
                    resolutionNode2.setType(2);
                    resolutionNode4.setType(2);
                    resolutionNode2.setOpposite(resolutionNode4, -height);
                    resolutionNode4.setOpposite(resolutionNode2, height);
                    constraintWidget.setHeight(height);
                    if (constraintWidget.mBaselineDistance > 0) {
                        constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
                    }
                }
            }
        } else if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget == null) {
            resolutionNode2.setType(1);
            resolutionNode4.setType(1);
            if (z) {
                resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
            } else {
                resolutionNode4.dependsOn(resolutionNode2, constraintWidget.getHeight());
            }
            if (constraintWidget.mBaseline.mTarget != null) {
                constraintWidget.mBaseline.getResolutionNode().setType(1);
                resolutionNode2.dependsOn(1, constraintWidget.mBaseline.getResolutionNode(), -constraintWidget.mBaselineDistance);
            }
        } else if (constraintWidget.mTop.mTarget != null && constraintWidget.mBottom.mTarget == null) {
            resolutionNode2.setType(1);
            resolutionNode4.setType(1);
            if (z) {
                resolutionNode4.dependsOn(resolutionNode2, 1, constraintWidget.getResolutionHeight());
            } else {
                resolutionNode4.dependsOn(resolutionNode2, constraintWidget.getHeight());
            }
            if (constraintWidget.mBaselineDistance > 0) {
                constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
            }
        } else if (constraintWidget.mTop.mTarget == null && constraintWidget.mBottom.mTarget != null) {
            resolutionNode2.setType(1);
            resolutionNode4.setType(1);
            if (z) {
                resolutionNode2.dependsOn(resolutionNode4, -1, constraintWidget.getResolutionHeight());
            } else {
                resolutionNode2.dependsOn(resolutionNode4, -constraintWidget.getHeight());
            }
            if (constraintWidget.mBaselineDistance > 0) {
                constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
            }
        } else if (constraintWidget.mTop.mTarget == null || constraintWidget.mBottom.mTarget == null) {
        } else {
            resolutionNode2.setType(2);
            resolutionNode4.setType(2);
            if (z) {
                resolutionNode2.setOpposite(resolutionNode4, -1, constraintWidget.getResolutionHeight());
                resolutionNode4.setOpposite(resolutionNode2, 1, constraintWidget.getResolutionHeight());
                constraintWidget.getResolutionHeight().addDependent(resolutionNode2);
                constraintWidget.getResolutionWidth().addDependent(resolutionNode4);
            } else {
                resolutionNode2.setOpposite(resolutionNode4, -constraintWidget.getHeight());
                resolutionNode4.setOpposite(resolutionNode2, constraintWidget.getHeight());
            }
            if (constraintWidget.mBaselineDistance > 0) {
                constraintWidget.mBaseline.getResolutionNode().dependsOn(1, resolutionNode2, constraintWidget.mBaselineDistance);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0030, code lost:
        if (r6.mHorizontalChainStyle == 2) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0034, code lost:
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0046, code lost:
        if (r6.mVerticalChainStyle == 2) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean applyChainOptimized(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r20, androidx.constraintlayout.solver.LinearSystem r21, int r22, int r23, androidx.constraintlayout.solver.widgets.ChainHead r24) {
        /*
            Method dump skipped, instructions count: 841
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Optimizer.applyChainOptimized(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):boolean");
    }
}
