package virtualFileSystem;
import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class VFileSystem implements VFS {

    private String root;

    public VFileSystem(String root) {
        this.root = root;
    }

    private class FileIterator implements Iterator<String>{

        private Queue<File> files = new LinkedList<>();

        public FileIterator(String path){
            files.add(new File(root + path));
        }
        public boolean hasNext() {
            return !files.isEmpty();
        }
        public String next() {
            File file = files.peek();
            if(file.isDirectory()) {
                File[] buffer = file.listFiles();
                try {
                    if (buffer != null)
                        Collections.addAll(files, buffer);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            return files.poll().getPath();
        }
        public void remove() {
        }
    }

    @Override
    public boolean isExist(String path) {
        return new File(root + path).exists();
    }
    @Override
    public boolean isDirectory(String path) {
        return new File(root + path).isDirectory();
    }
    @Override
    public String getAbsolutePath(String file) {
        return new File(root + file).getAbsolutePath();
    }
    @Override
    public byte[] getBytes(String file) throws IOException {
        FileInputStream fileStream = new FileInputStream(file);
        BufferedInputStream bs = new BufferedInputStream(fileStream);
        DataInputStream dataStream = new DataInputStream(bs);
        byte[] data = new byte[fileStream.available()];
        dataStream.readFully(data);
        dataStream.close();
        return data;
    }
    @Override
    public String getUFT8Text(String file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine;
        while((currentLine = br.readLine()) != null) {
            stringBuilder.append(currentLine);
        }
        br.close();
        return stringBuilder.toString();
    }
    @Override
    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }
}
