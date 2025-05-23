package com.jieli.component.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/* loaded from: classes2.dex */
public class ReflectHelper {
    public static void setFieldValueByName(Object obj, String str, Object obj2) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        if (declaredField.isAccessible()) {
            declaredField.set(obj, obj2);
            return;
        }
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
        declaredField.setAccessible(false);
    }

    public static Object getFieldValueByName(Object obj, String str) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        if (declaredField.isAccessible()) {
            return declaredField.get(obj);
        }
        declaredField.setAccessible(true);
        Object obj2 = declaredField.get(obj);
        declaredField.setAccessible(false);
        return obj2;
    }

    public static void invokeMethodByName(Object obj, String str) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = obj.getClass().getDeclaredMethod(str, new Class[0]);
        if (declaredMethod.isAccessible()) {
            declaredMethod.invoke(obj, new Object[0]);
            return;
        }
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(obj, new Object[0]);
        declaredMethod.setAccessible(false);
    }
}
