package org.jee.framework.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author AK
 *
 */
public abstract class CloneUtils {
	
	/**
	 * clone object method
	 * @param <T>
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T obj){
		T cloneObj=null;
		
		ByteArrayOutputStream baos;
		ByteArrayInputStream bais;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			bais = new ByteArrayInputStream(baos.toByteArray());
			ois = new ObjectInputStream(bais);
			cloneObj=(T)ois.readObject();
		}catch (IOException e) {
			throw ExceptionUtils.unchecked(e);
		} catch (ClassNotFoundException e) {
			throw ExceptionUtils.unchecked(e);
		}finally{
			if(null != oos)	try {oos.close();}catch(IOException e){/*ignore exception*/}
			if(null != ois)	try {ois.close();}catch(IOException e){/*ignore exception*/}
		}
		
		return cloneObj;
	}
	
	public static int size(Object object){
		int result = -1;
		if(object != null && object instanceof Serializable){
			ByteArrayOutputStream baos = null;
			ObjectOutputStream    oos  = null;
			try {
				baos = new ByteArrayOutputStream();
				oos  = new ObjectOutputStream(baos);
				oos.writeObject(object);
				oos.flush();
				result = baos.toByteArray().length;
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(oos != null){
					try {oos.close();} catch (Exception e2) {}
				}
				if(baos != null){
					try {baos.close();} catch (Exception e2) {}
				}
			}
		}
		return result;
	}

	/**
	 * 得到一个Pojo所有的方法
	 * @param methods
	 * @param clazz
	 */
	public static void getDeclaredMethods(Map<String, Method> methods, Class<?> clazz) {
		Method[] ms = clazz.getDeclaredMethods();
		for(Method m : ms){
			if(!methods.containsKey(m.getName())){
				methods.put(m.getName(), m);
			}
		}
		if(!clazz.equals(Object.class)){
			clazz = clazz.getSuperclass();
			getDeclaredMethods(methods,clazz);
		}
	}

	/**
	 * 得到所有但不包括final修饰的字段
	 * @param fields
	 * @param clazz
	 */
	public static void getDeclaredFields(Map<String,Field> fields, Class<?> clazz){
		Field[] fs = clazz.getDeclaredFields();
		for(Field f : fs){
			if(f.getModifiers() != (Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL)){
				if(!fields.containsKey(f.getName())){
					fields.put(f.getName(), f);
				}
			}
		}
		if(!clazz.equals(Object.class)){
			clazz = clazz.getSuperclass();
			getDeclaredFields(fields, clazz);
		}
	}




	public static void getDeclaredFields(List<Field> fields, Class<?> clazz) {
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			if ((f.getModifiers() == 26) ||  (fields.contains(f))) {
				continue;
			}
			fields.add(f);
		}

		if (!clazz.equals(Object.class)) {
			clazz = clazz.getSuperclass();
			getDeclaredFields(fields, clazz);
		}
	}

	public static Field getField(Class<?> clazz, Class<?> annotationClass) {
		List<Field> fields = new ArrayList<Field>();
		getDeclaredFields(fields, clazz);
		for (Field f : fields) {
			Annotation[] ans = f.getAnnotations();
			for (Annotation a : ans) {
				if (a.annotationType().equals(annotationClass))
					return f;
			}
		}
		return null;
	}

	/**
	 * 数组相加
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static String[] addArrayOfString(String[] first, String[] second){
		String[] dst = new String[first.length + second.length];
		System.arraycopy(first,0,dst,0,first.length);
		System.arraycopy(second, 0, dst, first.length, second.length);
		return dst;
	}
	/**
	 * 数组相加：注意，返回类型不能强转
	 * @param first
	 * @param second
	 * @return
	 */
	public static Object[] addArray(Object[] first, Object[] second){
		Object[] dst = new String[first.length + second.length];
		System.arraycopy(first,0,dst,0,first.length);
		System.arraycopy(second, 0, dst, first.length, second.length);
		return dst;
	}

}
