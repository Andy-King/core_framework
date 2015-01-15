package org.jee.framework.core.utils.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.springframework.util.StringUtils;

public abstract class Files {

	public static final long B = 1;//单位
	public static final long K = B * 1024;
	public static final long M = K * 1000;
	public static final long G = M * 1000;
	public static final long T = G * 1000;
	
	
	/**
	 * 删除文件，递归方式
	 * @param file
	 */
	public static void delete(File file) {
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f : files){
				delete(f);
			}
		}
		file.delete();
	}
	
	/**
	 * 得到所有盘符
	 * @return
	 */
	public static File[] getThisComputerDisk(){
		File[] roots = File.listRoots();
		
		return roots;
	}
	
	/**
	 * 总空间
	 * @param file
	 * @param unit
	 * @return
	 */
	public static long getTotalSpace(File file, long unit){
		////unit=Files.M ...
		return file.getTotalSpace()/unit;
	}
	
	/**
	 * 剩余空间
	 * @param file
	 * @param unit
	 * @return
	 */
	public static long getFreeSpace(File file, long unit){
		return file.getFreeSpace()/unit;
	}
	/**
	 * 已使用空间
	 * @param file
	 * @param unit
	 * @return
	 */
	public static long getUsableSpace(File file, long unit){
		return file.getUsableSpace()/unit;
	}
	
	/**
	 * 盘符
	 * @param str
	 * @return
	 */
	public static String getWindowsDriveName(String str){
		String driveName = null;
		if(StringUtils.hasLength(str)){
			driveName = String.valueOf(str.charAt(0)).toUpperCase();
			if("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(driveName) > -1){
				driveName = driveName + ":/";
			}else{
				driveName = null;
			}
		}
		if(driveName == null)
			throw new RuntimeException("不合格的盘符");
		return driveName;
	}
	
	public static File getRoot(File file){
		return file.getParentFile() == null ? file : getRoot(file.getParentFile());
	}
	public static File getRoot(){
		return getRoot(new File("/").getAbsoluteFile());
	}
	
	public static File getRootByUserDir(){
		return getRoot(new File(System.getProperty("user.dir")));
	}
	
	
	
    public static void removeFile(File file) {
        int maxTry = 3;
        while(maxTry > 0) {
            maxTry--;
            if(file.isFile()) {
                if(file.delete())
                    return;
                else
                    continue;
            }
            else {
                return;
            }
        }
    }

    public static void readFile(File file, OutputStream output) throws IOException {
        FileInputStream input = null;
        FileChannel fc = null;
        try {
            input = new FileInputStream(file);
            fc = input.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            for(;;) {
                buffer.clear();
                int n = fc.read(buffer);
                if(n==(-1))
                    break;
                output.write(buffer.array(), 0, buffer.position());
            }
            output.flush();
        }
        finally {
        	IOUtils.close(fc, input);
        }
    }

    public static void writeFile(File file, byte[] data) throws IOException {
        final int MAX_BUFFER_SIZE = 4096;
        FileOutputStream output = null;
        FileChannel fc = null;
        try {
            output = new FileOutputStream(file);
            fc = output.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE);
            int offset = 0;
            while(offset<data.length) {
                buffer.clear();
                int len = data.length - offset;
                if(len>MAX_BUFFER_SIZE)
                    len = MAX_BUFFER_SIZE;
                buffer.put(data, offset, len);
                offset += len;
                buffer.flip();
                fc.write(buffer);
            }
        }
        finally {
        	IOUtils.close(fc, output);
        	
        }
    }
    
	/**
	 * 使用NIO快速复制Java文件
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void fileCopy(File in, File out) throws IOException {
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			// inChannel.transferTo(0, inChannel.size(), outChannel);
			// original -- apparently has trouble copying large files on Windows
			// magic number for Windows, 64Mb - 32Kb)

			int maxCount = (64 * 1024 * 1024) - (32 * 1024);
			long size = inChannel.size();
			long position = 0;
			while (position < size) {
				position += inChannel.transferTo(position, maxCount, outChannel);
			}

		} finally {
			IOUtils.close(inChannel, outChannel);

		}
	}
    /**
     * 返回文件名
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName){
    	String ext = null;
    	if(StringUtils.hasText(fileName) && fileName.lastIndexOf('.') > 0 ){
    		ext = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
    	}
    	return ext;
    }
    
    public static void main(String[] args) {
		System.out.println(getRoot());
		System.out.println(getRootByUserDir());
	}
}
