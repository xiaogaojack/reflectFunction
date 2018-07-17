package test.sdk.mugu.reflectfunction;

import android.text.TextUtils;
import android.view.TextureView;

import junit.framework.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by xiang on 2018/6/23.
 */

public class ReflectUtils {
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        assert (obj != null);
        assert (!TextUtils.isEmpty(fieldName));

        Field field = getFieldByClass(obj.getClass(), fieldName);

        if (field == null){
            throw new NoSuchFieldException("no such field:" + fieldName);
        }

        field.setAccessible(true);

        Object objValue = field.get(obj);

        return objValue;
    }

    public static Object getDeclaredFieldValue(Object obj, String fieldName) throws Exception {
        assert (obj != null);
        assert (!TextUtils.isEmpty(fieldName));

        Field field = getDeclaredFieldByClass(obj.getClass(), fieldName);

        if (field == null){
            throw new NoSuchFieldException("no such field:" + fieldName);
        }

        field.setAccessible(true);

        Object objValue = field.get(obj);

        return objValue;
    }

    public static Object getStaticField(Class clazz, String fieldName) throws Exception {
        assert (clazz != null);
        assert (!TextUtils.isEmpty(fieldName));

        Field field = getDeclaredFieldByClass(clazz, fieldName);

        if (field == null){
            throw new NoSuchFieldException("no such field:" + fieldName);
        }

        field.setAccessible(true);

        Object objValue = field.get(null);

        return objValue;
    }

    /**
     * Field是包含public相关域
     * DeclaredFiedl包含所有域
     */

    public static void setFieldValue(Object obj, String fieldName, Object objValue) throws Exception {
        assert (obj != null);
        assert (!TextUtils.isEmpty(fieldName));

        Field field = getFieldByClass(obj.getClass(), fieldName);

        if (field == null){
            throw new NoSuchFieldException("no such field:" + fieldName);
        }
        field.setAccessible(true);
        field.set(obj, objValue);
    }

    public static void setDeclaredFieldValue(Object obj, String fieldName, Object objValue) throws Exception {
        assert (obj != null);
        assert (!TextUtils.isEmpty(fieldName));

        Field field = getDeclaredFieldByClass(obj.getClass(), fieldName);

        if (field == null){
            throw new NoSuchFieldException("no such field:" + fieldName);
        }

        field.setAccessible(true);
        field.set(obj, objValue);
    }

    public static void setStaticFieldValue(Class clazz, String fieldName, Object value) throws Exception {
        assert (clazz != null);
        assert (!TextUtils.isEmpty(fieldName));

        Field field = getDeclaredFieldByClass(clazz, fieldName);

        if (field == null){
            throw new NoSuchFieldException("no such field:" + fieldName);
        }

        field.setAccessible(true);
        field.set(null, value);
    }


    public static Object invokeStaticMethod(Class clazz, String methodName, Object[] objParams, Class<?>... paramType) throws Exception {
        assert (clazz != null);
        assert (!TextUtils.isEmpty(methodName));

        Method method = clazz.getMethod(methodName, paramType);
        method.setAccessible(true);
        Object objReturn = method.invoke(null, objParams);
        return objReturn;
    }


    public static Object invokeStaticDeclaredMethod(Class clazz, String methodName, Object[] objParams, Class<?>[] paramType) throws Exception {
        assert (clazz != null);
        assert (!TextUtils.isEmpty(methodName));

        Method method = clazz.getDeclaredMethod(methodName, paramType);
        method.setAccessible(true);
        Object objReturn = method.invoke(null, objParams);

        return objReturn;
    }

    public static Object invokeMethod(Object obj, String methodName, Object[] objParams, Class<?>... paramType) throws Exception {
        assert (obj != null);
        assert (!TextUtils.isEmpty(methodName));

        Method method = obj.getClass().getMethod(methodName, paramType);
        method.setAccessible(true);
        Object objReturn =  method.invoke(obj, objParams);

        return objReturn;
    }

    public static Object invokeDeclaredMethod(Object obj, String methodName, Object[] objParams, Class<?>[] paramType) throws Exception {
        assert (obj != null);
        assert (!TextUtils.isEmpty(methodName));

        Method method = obj.getClass().getDeclaredMethod(methodName, paramType);
        method.setAccessible(true);
        Object objReturn = method.invoke(obj, objParams);

        return objReturn;
    }

    private static Field getDeclaredFieldByClass(Class clazz, String fieldname){
        if (clazz == null){
            return null;
        }

        Field field = null;

        try {
            field = clazz.getDeclaredField(fieldname);
        }catch (NoSuchFieldException e){
            return getDeclaredFieldByClass(clazz.getSuperclass(), fieldname);
        }

        return field;
    }

    private static Field getFieldByClass(Class clazz, String fieldname){
        if (clazz == null){
            return null;
        }

        Field field = null;

        try {
            field = clazz.getField(fieldname);
        }catch (NoSuchFieldException e){
            return getFieldByClass(clazz.getSuperclass(), fieldname);
        }

        return field;
    }
}
