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
        DataInputStream dataStream = new DataInputStream(fileStream);
        byte[] data = new byte[fileStream.available()];
        dataStream.readFully(data);
        dataStream.close();
        return data;
    }
    @Override
    public String getUFT8Text(String file) throws IOException {
        FileInputStream fs = new FileInputStream(file);
        DataInputStream ds = new DataInputStream(fs);
        InputStreamReader sr = new InputStreamReader(ds, "UTF-8");
        BufferedReader br = new BufferedReader(sr);
        StringBuilder lines = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)   {
            lines.append(line);
        }
        br.close();
        return lines.toString();
    }
    @Override
    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }
}
