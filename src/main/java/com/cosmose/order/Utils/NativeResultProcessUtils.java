package com.cosmose.order.Utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import javax.persistence.Tuple;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * @author xujian
 * @create 2019-09-07
 */
public class NativeResultProcessUtils {
    public static <T> T processResult(Tuple source, Class<T> targetClass) {
        Object instantiate = BeanUtils.instantiate(targetClass);
        convertTupleToBean(source,instantiate,null);
        return (T) instantiate;
    }

    public static <T> T processResult(Tuple source,Class<T> targetClass,String... ignoreProperties) {
        Object instantiate = BeanUtils.instantiate(targetClass);
        convertTupleToBean(source,instantiate,ignoreProperties);
        return (T) instantiate;
    }

    public static void convertTupleToBean(Tuple source,Object target){
        convertTupleToBean(source,target,null);
    }

    public static void convertTupleToBean(Tuple source,Object target, String... ignoreProperties){
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                String propertyName = targetPd.getName();
                Object value = source.get(propertyName);
                if(value!=null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], value.getClass())) {
                    try {
                        if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }
                        writeMethod.invoke(target, value);
                    }
                    catch (Throwable ex) {
                        throw new FatalBeanException(
                                "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                    }
                }
            }
        }
    }
}
