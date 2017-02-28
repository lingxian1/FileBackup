import java.io.*;

/**
 * Created by LX on 2017/2/28.
 */
public class Operation {
    public static String filePathTool(String filepath,String newfile,String configfilepath) {
        String target = null;
        target=configfilepath.concat(filepath.replace(newfile,""));
        return target;
    }

    //创建文件夹
    public static boolean createDir(String dirpath) {
        File dir = new File(dirpath);
        if (dir.exists()) {// 判断目录是否存在
            System.out.println("创建目录失败，目标目录已存在！");
            return false;
        }
        if (!dirpath.endsWith(File.separator)) {// 结尾是否以"/"结束
            dirpath = dirpath + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + dirpath);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }

    //删除文件
    public static boolean deleteFile(String filePath) {
        Boolean flag = false;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    //删除文件夹
    public static void deleteDir(File path) {
        if (!path.exists()) {
            System.out.println("路径不存在");
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteDir(files[i]);
        }
        path.delete();
    }

    //文件复制
    public static void copyFile(File source, File target) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(source));
            fos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
