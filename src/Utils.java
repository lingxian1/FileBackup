import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * Created by LX on 2017/2/22.
 * void xlh(File file, List<?> objects);序列化工具
 * void fxlh(File file);反序列化工具 结果放入OLD_FILE_LIST
 * String getFileMD5(File file);文件MD5获取
 * void parseToFileBean(File dir);递归遍历所有文件（夹）并组装成FileBean 放入NEW_FILE_LIST
 * String getConfigFilePath(String path);获取配置文件路径
 * String filePathTool(String filepath);获取相对路径
 * void save(String configfilepath);保存序列化文件
 * void ReBulid(String configfilepath);重新设置备份文件夹配置
 * void showConfigMessage(String configfilepath);显示配置文件信息
 * void checkConfigFile(String configfilepath);检查配置文件是否存在
 */
public class Utils {


    public static void xlh(File file, List<?> objects) {
        try {
            FileOutputStream fo = new FileOutputStream(file, false);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            //进行序列化
            for (Object object : objects) {
                oo.writeObject(object);
            }
            oo.flush();
            oo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fxlh(File file) {
        // String regex="get.*";
        if (file.exists()) {
            ObjectInputStream ois;
            try {
                FileInputStream fn = new FileInputStream(file);
                ois = new ObjectInputStream(fn);
                //fn.available() > 0 代表文件还有内容
                while (fn.available() > 0) {
                    //从流中读取对象
                    FileBean o = (FileBean) ois.readObject();
                    //设置状态
                    o.setState("old");
//                   Method[] methods=o.getClass().getMethods();
//                    for (Method method : methods) {
//                        if (method.getName().matches(regex) && !method.getName().equals("getClass")) {
//                            System.out.println(method.getName());
//                            System.out.println(method.invoke(o));
//                        }
//                    }
                    Constant.OLD_FILE_LIST.add(o);
                }
                ois.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return "not file";
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

    public static void parseToFileBean(File dir) throws Exception {
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            FileBean fileBean = new FileBean();
            fileBean.setName(fs[i].getName());
            fileBean.setPath(fs[i].getAbsolutePath());
            String temp = filePathTool(fs[i].getAbsolutePath());
            fileBean.setRelativePath(temp);
            fileBean.setMD5(getFileMD5(fs[i]));
            fileBean.setState("new");
            Constant.NEW_FILE_LIST.add(fileBean);
            if (fs[i].isDirectory()) {
                try {
                    parseToFileBean(fs[i]);
                } catch (Exception e) {
                }
            }
        }
    }

    public static String getConfigFilePath(String path) {
        String regex = ".*.lx"; //假设配置文件以lx结尾
        File f = new File(path);
        if (!f.exists()) {
            return "error path";
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.getName().matches(regex)) {
                return path + File.separator + fs.getName();
            }
        }
        return "no config file";
    }

    public static String filePathTool(String filepath) {
        String target = null;
        //将filepath截去newfile然后用configfilepath替换得到相对路径
        target = filepath.replace(Constant.NEW_FILE_PATH, Constant.CONFIG_FILE_PATH);
        return target;
    }

    public static void save(String configfilepath) {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        String path = configfilepath + File.separator + time + ".lx";
        File file = new File(path);
        xlh(file, Constant.NEW_FILE_LIST);
        System.out.println("创建配置文件：" + path);
    }

    public static void ReBulid(String configfilepath) {
        try {
            //如果存在
            String temp = getConfigFilePath(configfilepath);
            if (temp != null) {
                File file = new File(temp);
                if (file.isFile() && file.exists()) {
                    file.delete();
                    System.out.println("删除原配置文件成功");
                }
            }
            parseToFileBean(new File(configfilepath));
            save(configfilepath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showConfigMessage(String configfilepath) {
        System.out.println("配置文件路径：" + getConfigFilePath(configfilepath));
        fxlh(new File(getConfigFilePath(configfilepath)));
        for (FileBean temp : Constant.OLD_FILE_LIST) {
            System.out.println(temp.toString());
        }
    }

    public static int checkConfigFile(String configfilepath){
        String label=getConfigFilePath(configfilepath);
        if(label.equals("error path")){
            System.out.println("路径不存在");
            return -1;
        }
        if(label.equals("no config file")){
            System.out.println("找不到配置文件");
            return -1;
        }else{
            System.out.println("normal");
            return 0;
        }
    }
}
