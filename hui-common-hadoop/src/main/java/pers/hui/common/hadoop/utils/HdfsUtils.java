package pers.hui.common.hadoop.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * <code>HdfsUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 0:46.
 *
 * @author Gary.Hu
 */
public class HdfsUtils {

    public static FileSystem getFs(Configuration conf) throws IOException {
        return FileSystem.get(conf);
    }

    public static void copyToLocal(FileSystem fs, String hdfsPath, String localPath) throws IOException {
        Path src = new Path(hdfsPath);
        Path dest = new Path(localPath);


        File destFile = new File(localPath);
        if (!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdirs();
        }

        if (fs.exists(src) && fs.getFileStatus(src).isFile()){
            fs.copyToLocalFile(src, dest);
        }
    }


    public static void mkdir(){

    }

}
