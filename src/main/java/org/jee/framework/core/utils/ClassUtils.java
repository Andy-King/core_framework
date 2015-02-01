package org.jee.framework.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class ClassUtils {
	
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
	 * 扫描包下所有类，暂不支持子包
	 * @param packg
	 * @return
	 * @throws IOException
	 */
	public static List<Class<?>> scanPackage(Package packageName) throws IOException {
		String path = packageName.getName().replace('.', '/') + "/";
		return scanPackage(path);
	}

	/**
	 * 扫描包下所有类，暂不支持子包
	 * @param packg
	 * @return
	 * @throws IOException
	 */
	public static List<Class<?>> scanPackage(String packagePath) throws IOException {
		List<Class<?>> cls = new ArrayList<Class<?>>(0);
		if(!StringUtils.hasLength(packagePath)){
			return cls;
		}
		packagePath = packagePath.replace('.', '/') + "/";
		ClassLoader cl = CloneUtils.class.getClassLoader();
		Enumeration<URL> urls = cl.getResources(packagePath);
		while(urls.hasMoreElements()){
			URL url = urls.nextElement();
			if("file".equalsIgnoreCase(url.getProtocol())){
				File file = new File(url.getPath());
				if(file.exists() && file.isDirectory()){
					File[] classFiles = file.listFiles();
					if(classFiles != null && classFiles.length > 0){
						for(File classFile : classFiles){
							String className = (packagePath.replace('/', '.') + classFile.getName().replace(".class", "")).replace("..", ".");
							try {
								cls.add(cl.loadClass(className));
							} catch (ClassNotFoundException e) {
								System.out.println("警告：没有发现类->"+className);;
							}
						}
					}
				}
			}
			else if("jar".equalsIgnoreCase(url.getProtocol())){
				JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
				Enumeration<JarEntry> jarEntities = jar.entries();
				while(jarEntities.hasMoreElements()){
					JarEntry je = jarEntities.nextElement();
					String className = je.getName();
					if(className.startsWith(packagePath) && className.endsWith(".class")){
						className = je.getName().replace(".class", "").replace('/', '.');
						try {
							cls.add(cl.loadClass(className));
						} catch (ClassNotFoundException e) {
							System.out.println("警告：jar中没有发现类->"+className);;
						}
					}
				}
			}
		}
		return cls;
	}
}
