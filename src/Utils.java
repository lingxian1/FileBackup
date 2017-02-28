import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LX on 2017/2/22.
 * void xlh(File file, Object o);序列化工具
 * void fxlh(File file);反序列化工具 结果放入OLD_FILE_LIST
 * String getFileMD5(File file);文件MD5获取
 * void showAllFiles(File dir);递归遍历所有文件（夹）并组装成FileBean 放入NEW_FILE_LIST
 * String getConfigFilePath(String path);获取配置文件路径
 */
public class Utils {
    static List<FileBean> NEW_FILE_LIST = new ArrayList<>();
    static List<FileBean> OLD_FILE_LIST = new ArrayList<>();

    public static void xlh(File file, Object o) {
        try {
            if (file.exists()) {
                boolean isexist = true;
                FileOutputStream fo = new FileOutputStream(file, true);
                ObjectOutputStream oo = new ObjectOutputStream(fo);
                long pos = 0;
                if (isexist) {
                    //nio截取
                    pos = fo.getChannel().position() - 4;
                    fo.getChannel().truncate(pos);
                }
                //进行序列化
                oo.writeObject(o);
            } else {
                //文件不存在
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fo);
                //进行序列化
                oos.writeObject(o);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fxlh(File file) {
        //  String regex="get.*";
        if (file.exists()) {
            ObjectInputStream ois;
            try {
                FileInputStream fn = new FileInputStream(file);
                ois = new ObjectInputStream(fn);
                //fn.available() > 0 代表文件还有内容
                while (fn.available() > 0) {
                    //从流中读取对象
                    FileBean o = (FileBean) ois.readObject();
//                   Method[] methods=o.getClass().getMethods();
//                    for (Method method : methods) {
//                        if (method.getName().matches(regex) && !method.getName().equals("getClass")) {
//                            System.out.println(method.getName());
//                            System.out.println(method.invoke(o));
//                        }
//                    }
                    OLD_FILE_LIST.add(o);
                }
                ois.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        BufferedInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new BufferedInputStream(new FileInputStream(file));
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();     //must close !!
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showAllFiles(File dir) throws Exception {
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            FileBean fileBean = new FileBean();
            fileBean.setName(fs[i].getName());
            fileBean.setPath(fs[i].getAbsolutePath());
            fileBean.setMD5(getFileMD5(fs[i]));
            fileBean.setState("new");
            NEW_FILE_LIST.add(fileBean);
            if (fs[i].isDirectory()) {
                try {
                    showAllFiles(fs[i]);
                } catch (Exception e) {
                }
            }
        }
    }

    public static String getConfigFilePath(String path) {
        String  regex=".*.lx";
        File f = new File(path);
        if (!f.exists()) {
            System.out.println("路径不存在");
            return null;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if(fs.getName().matches(regex)){
                return fs.getName();
            }
        }
        System.out.println("找不到文件");
        return null;
    }
}
