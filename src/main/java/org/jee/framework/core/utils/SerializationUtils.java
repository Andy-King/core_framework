package org.jee.framework.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.jee.framework.core.utils.io.IOUtils;

/**
 * 序列化工具
 * 
 * @author AK
 * 
 */
public abstract class SerializationUtils {
    /**
     * 序列化一个对象为字节数组
     * 
     * @param obj
     * @return
     * @throws Exception
     */
    public final static byte[] serialize(Serializable obj){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = null;
        try{
        	serialize(obj, baos);
			b = baos.toByteArray();
        }catch (Exception e) {
        	throw ExceptionUtils.unchecked(e);
		}finally{
			IOUtils.close(baos);
		}
        return b;
    }

    /**
     * 将对象序列化到一个指定文件中。
     * 
     * @param obj
     * @param filePath
     */
    public final static void serialize(Serializable obj, String filePath) throws IOException {
        serialize(obj, new FileOutputStream(filePath));
    }

    /**
     * 将对象序列化到一个指定输出流中。
     * 
     * @param obj
     * @param output
     * @throws Exception
     */
    public final static void serialize(Serializable obj, OutputStream output) throws IOException {
    	ObjectOutputStream oos = null;
    	try{
			oos = new ObjectOutputStream(output);
        	oos.writeObject(obj);
    	}finally{
    		IOUtils.close(oos);
    	}
    }

    /**
	 * 先将对象序列化为字节数组，再用BASE64编码为字符串； 
	 * 反序列化时，先用BASE64解码为字节数组； 
	 * 序列化过程中还可以做压缩，以缩短最终的窜长度。
     * @param <T>
     * @param obj
     * @param compressed
     * @return
     * @throws IOException
     */
    public static <T extends Serializable> byte[] serialize(
    		T obj, boolean compressed) throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			if (compressed)
				oos = new ObjectOutputStream(new GZIPOutputStream(buf));
			else
				oos = new ObjectOutputStream(buf);
			oos.writeObject(obj);
			oos.flush();
		} finally {
			IOUtils.close(oos);
		}
		return buf.toByteArray();
	}
	
	/**
	 * 先将对象序列化为字节数组，再用BASE64编码为字符串； 
	 * 反序列化时，先用BASE64解码为字节数组； 
	 * 序列化过程中还可以做压缩，以缩短最终的窜长度。
	 * @param <T>
	 * @param data
	 * @param compressed
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialize(byte[] data,
			boolean compressed) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream ois = null;
		try {
			if (compressed)
				ois = new ObjectInputStream(new GZIPInputStream(in));
			else
				ois = new ObjectInputStream(in);
			return (T) ois.readObject();
		} finally {
			IOUtils.close(ois);
		}
	}
    
    /**
     * 将指定字节数组反序列化为一个对象。
     * 
     * @param b
     * @return
     * @throws Exception
     */
    public final static Serializable deserialize(byte[] b){
        ByteArrayInputStream bais = null;
        Serializable obj;
		try{
			bais = new ByteArrayInputStream(b);
			obj = deserialize(bais);
		}finally{
			IOUtils.close(bais);
		}
        return obj;
    }

    /**
     * 将指定序列化文件反序列化为一个对象。
     * 
     * @param filePath
     * @return
     * @throws FileNotFoundException 
     * @throws Exception
     */
    public final static Serializable deserialize(String filePath) throws FileNotFoundException {
        return deserialize(new FileInputStream(filePath));
    }

    /**
     * 将指定序列化输入流反序列化为一个对象。
     * 
     * @param input
     * @return
     * @throws ClassNotFoundException 
     * @throws Exception
     */
    public final static Serializable deserialize(InputStream input){
        ObjectInputStream ois = null;
        Serializable obj = null;
        try{
        	ois = new ObjectInputStream(input);
			obj = (Serializable) ois.readObject();
        }catch (Exception e) {
        	throw ExceptionUtils.unchecked(e);
		}finally{
			IOUtils.close(ois);
		}
        return obj;
    }

    /**
     * 将一个Map中的key和value全部序列化，并生成一个新的Map。被序列化的Map中的key和value必须都是Serializable的。
     * 
     * @param m
     * @return
     * @throws Exception
     */
    public final static Map<byte[], byte[]> serialize(Map<?, ?> m) throws IOException {
        if (m == null || m.isEmpty()) {
            throw new NullPointerException("map is null or empty.");
        }
        Map<byte[], byte[]> newMap = new HashMap<byte[], byte[]>();
        for (Object obj : m.keySet()) {
            byte[] key = serialize((Serializable) obj);
            byte[] value = serialize((Serializable) m.get(obj));
            newMap.put(key, value);
        }
        return newMap;
    }

    /**
     * 将已序列化过内部元素的Map反序列化。
     * 
     * @param m
     * @return
     * @throws Exception
     */
    public final static Map<?, ?> deserialize(Map<byte[], byte[]> m) throws IOException {
        if (m == null || m.isEmpty()) {
            throw new NullPointerException("map is null or empty.");
        }
        Map<Object, Object> newMap = new HashMap<Object, Object>();
        for (byte[] obj : m.keySet()) {
            Object key = deserialize(obj);
            Object value = deserialize(m.get(obj));
            newMap.put(key, value);
        }
        return newMap;
    }

    /**
     * 将指定List中所有元素序列化，并生成新的List，被序列化的List中元素必须是Serializable的。
     * 
     * @param l
     * @return
     * @throws Exception
     */
    public final static List<byte[]> serialize(List<?> l) throws IOException {
        if (l == null || l.size() == 0) {
            throw new NullPointerException("list is null or empty.");
        }
        List<byte[]> newList = new ArrayList<byte[]>();
        for (Object obj : l) {
            byte[] e = serialize((Serializable) obj);
            newList.add(e);
        }
        return newList;
    }

    /**
     * 将内部元素已经序列化的List反序列化。
     * 
     * @param l
     * @return
     * @throws Exception
     */
    public final static List<?> deserialize(List<byte[]> l) throws IOException {
        if (l == null || l.size() == 0) {
            throw new NullPointerException("list is null or empty.");
        }
        List<Object> newList = new ArrayList<Object>();
        for (byte[] obj : l) {
            Object e = deserialize(obj);
            newList.add(e);
        }
        return newList;
    }

    /**
     * 将指定Set的所有元素序列化，并生成新的Set，被序列化的Set的元素必须是Serializable的。
     * 
     * @param s
     * @return
     * @throws Exception
     */
    public final static Set<byte[]> serialize(Set<?> s) throws IOException {
        if (s == null || s.size() == 0) {
            throw new NullPointerException("set is null or empty.");
        }
        Set<byte[]> newSet = new HashSet<byte[]>();
        for (Object obj : s) {
            byte[] e = serialize((Serializable) obj);
            newSet.add(e);
        }
        return newSet;
    }

    /**
     * 将内部元素已经序列化的Set反序列化。
     * 
     * @param s
     * @return
     * @throws Exception
     */
    public final static Set<?> deserialize(Set<byte[]> s) throws IOException {
        if (s == null || s.size() == 0) {
            throw new NullPointerException("set is null or empty.");
        }
        Set<Object> newSet = new HashSet<Object>();
        for (byte[] obj : s) {
            Object e = deserialize(obj);
            newSet.add(e);
        }
        return newSet;
    }
    
}