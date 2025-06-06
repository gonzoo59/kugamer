package org.opencv.ml;

import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
/* loaded from: classes2.dex */
public class LogisticRegression extends StatModel {
    public static final int BATCH = 0;
    public static final int MINI_BATCH = 1;
    public static final int REG_DISABLE = -1;
    public static final int REG_L1 = 0;
    public static final int REG_L2 = 1;

    private static native long create_0();

    private static native void delete(long j);

    private static native int getIterations_0(long j);

    private static native double getLearningRate_0(long j);

    private static native int getMiniBatchSize_0(long j);

    private static native int getRegularization_0(long j);

    private static native double[] getTermCriteria_0(long j);

    private static native int getTrainMethod_0(long j);

    private static native long get_learnt_thetas_0(long j);

    private static native long load_0(String str, String str2);

    private static native long load_1(String str);

    private static native float predict_0(long j, long j2, long j3, int i);

    private static native float predict_1(long j, long j2, long j3);

    private static native float predict_2(long j, long j2);

    private static native void setIterations_0(long j, int i);

    private static native void setLearningRate_0(long j, double d);

    private static native void setMiniBatchSize_0(long j, int i);

    private static native void setRegularization_0(long j, int i);

    private static native void setTermCriteria_0(long j, int i, int i2, double d);

    private static native void setTrainMethod_0(long j, int i);

    protected LogisticRegression(long j) {
        super(j);
    }

    public static LogisticRegression __fromPtr__(long j) {
        return new LogisticRegression(j);
    }

    public Mat get_learnt_thetas() {
        return new Mat(get_learnt_thetas_0(this.nativeObj));
    }

    public static LogisticRegression create() {
        return __fromPtr__(create_0());
    }

    public static LogisticRegression load(String str, String str2) {
        return __fromPtr__(load_0(str, str2));
    }

    public static LogisticRegression load(String str) {
        return __fromPtr__(load_1(str));
    }

    public TermCriteria getTermCriteria() {
        return new TermCriteria(getTermCriteria_0(this.nativeObj));
    }

    public double getLearningRate() {
        return getLearningRate_0(this.nativeObj);
    }

    @Override // org.opencv.ml.StatModel
    public float predict(Mat mat, Mat mat2, int i) {
        return predict_0(this.nativeObj, mat.nativeObj, mat2.nativeObj, i);
    }

    @Override // org.opencv.ml.StatModel
    public float predict(Mat mat, Mat mat2) {
        return predict_1(this.nativeObj, mat.nativeObj, mat2.nativeObj);
    }

    @Override // org.opencv.ml.StatModel
    public float predict(Mat mat) {
        return predict_2(this.nativeObj, mat.nativeObj);
    }

    public int getIterations() {
        return getIterations_0(this.nativeObj);
    }

    public int getMiniBatchSize() {
        return getMiniBatchSize_0(this.nativeObj);
    }

    public int getRegularization() {
        return getRegularization_0(this.nativeObj);
    }

    public int getTrainMethod() {
        return getTrainMethod_0(this.nativeObj);
    }

    public void setIterations(int i) {
        setIterations_0(this.nativeObj, i);
    }

    public void setLearningRate(double d) {
        setLearningRate_0(this.nativeObj, d);
    }

    public void setMiniBatchSize(int i) {
        setMiniBatchSize_0(this.nativeObj, i);
    }

    public void setRegularization(int i) {
        setRegularization_0(this.nativeObj, i);
    }

    public void setTermCriteria(TermCriteria termCriteria) {
        setTermCriteria_0(this.nativeObj, termCriteria.type, termCriteria.maxCount, termCriteria.epsilon);
    }

    public void setTrainMethod(int i) {
        setTrainMethod_0(this.nativeObj, i);
    }

    @Override // org.opencv.ml.StatModel, org.opencv.core.Algorithm
    protected void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
