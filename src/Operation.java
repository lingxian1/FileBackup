import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by LX on 2017/2/28.
 */
public class Operation {

    public void opera() {
        List<FileBean> operationlist = new ArrayList<>();
        File newfilepath = new File(Constant.NEW_FILE_PATH);
        File configfile = new File(Utils.getConfigFilePath(Constant.CONFIG_FILE_PATH));
        Compare compare = new Compare(newfilepath, configfile);
        operationlist = compare.getResult();
 //       开始备份
        System.out.println("待操作数："+operationlist.size());

        for (FileBean temp : operationlist) {
            if (!temp.getState().equals("new") && (temp.getMD5() == "not file")) {
                deleteDir(new File(temp.getPath()));

            }
            if (temp.getState().equals("new") && (temp.getMD5() == "not file")) {
                createDir(filePathTool(temp.getPath(), Constant.NEW_FILE_PATH, Constant.CONFIG_FILE_PATH));
            }
        }
        for (FileBean temp : operationlist) {
            if (!temp.getState().equals("new") && (temp.getMD5() != "not file")) {
                deleteFile(new File(temp.getPath()));
            }
            if (temp.getState().equals("new") && (temp.getMD5() != "not file")) {
                copyFile(new File(temp.getPath()),
                        filePathTool(temp.getPath(), Constant.NEW_FILE_PATH, Constant.CONFIG_FILE_PATH));
                System.out.println("复制文件成功："+temp.getPath());
            }
        }
        //删除配置文件
        deleteFile(configfile);
        System.out.println("删除原配置文件成功！");
    }

    //复制文件时获得目标路径 配置文件必须在目标文件夹下！
    public static File filePathTool(String filepath, String newfile, String configfilepath) {
        String target = null;
        //将filepath截去newfile然后拼接configfilepath
        target = filepath.replace(newfile, configfilepath);
        System.out.println(target);
        File target2 = new File(target);
        return target2;
    }

    //创建文件夹
    public boolean createDir(File dir) {
        String dirpath=dir.getPath();
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
    public boolean deleteFile(File file) {
        String filePath=file.getPath();
        Boolean flag = false;
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
            System.out.println("删除文件成功："+filePath);
        }
        return flag;
    }

    //删除文件夹
    public void deleteDir(File path) {
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
        System.out.println("删除文件夹成功："+path.getPath());
    }

    //文件复制
    public void copyFile(File source, File target) {
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
