package virtualFileSystem;

import java.io.IOException;
import java.util.Iterator;

public interface VFS {
    public boolean isExist(String path);
    public boolean isDirectory(String path);
    public String getAbsolutePath(String file);
    public byte[] getBytes(String file) throws IOException;
    public String getUFT8Text(String file) throws IOException;
    public Iterator<String> getIterator(String startDir);
}
