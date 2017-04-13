import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by LX on 2017/2/28.
 */
public class Operation {
    private String newfilepath = null;
    private String configfilepath = null;
    private File newfile = null;
    private File configfile = null;

    public Operation(String newfilepath, String configfilepath) {
        this.newfilepath = newfilepath;
        this.configfilepath = configfilepath;
    }

    /**
     * 文件操作
     */
    public void opera() {
        List<FileBean> operationlist = new ArrayList<>();
        newfile = new File(newfilepath);
        configfile = new File(Utils.getConfigFilePath(configfilepath));
        //获取操作表
        operationlist = getResult();
        if(operationlist!=null) {
            //开始备份
            System.out.println("待操作数：" + operationlist.size());
            //先删除文件
            for (FileBean temp : operationlist) {
                if (!temp.getState().equals("new") && (temp.getMD5() != "not file")) {
                    deleteFile(new File(temp.getRelativePath()));
                }
            }
            //操作文件夹
            for (FileBean temp : operationlist) {
                if (!temp.getState().equals("new") && (temp.getMD5().equals("not file"))) {
                    deleteDir(new File(temp.getRelativePath()));
                }
                if (temp.getState().equals("new") && (temp.getMD5().equals("not file"))) {
                    createDir(filePathTool(temp.getPath(), newfilepath, configfilepath));
                }
            }
            //复制文件
            for (FileBean temp : operationlist) {
                if (temp.getState().equals("new") && (temp.getMD5() != "not file")) {
                    copyFile(new File(temp.getPath()),
                            filePathTool(temp.getPath(),newfilepath, configfilepath));
                    System.out.println("复制文件成功：" + temp.getPath());
                }
            }
            //删除配置文件
            deleteFile(configfile);
            System.out.println("删除原配置文件成功！");
        }
        else{
            System.out.println("空列表");
        }
    }

    /**
     * 获取操作表
     * @return
     */
    public List<FileBean> getResult() {
        List<FileBean> result = new ArrayList<>();
        //获取两个list
        if (getList()) {
            //保存序列化信息
            Utils.save(configfilepath);
            System.out.println("save:"+configfilepath);
            //归并去重 可优化
            List<FileBean> tempNew = Constant.NEW_FILE_LIST;
            List<FileBean> tempOld = Constant.OLD_FILE_LIST;
            List<FileBean> temp = new ArrayList<>(tempNew);
            tempNew.removeAll(tempOld);
            tempOld.removeAll(temp);
            result.addAll(tempNew);
            result.addAll(tempOld);

            for (FileBean s : result) {
                System.out.println(s);
            }
            return result;
        }
        return null;
    }

    /**
     * 用于获取两个列表
     * @return
     */
    public Boolean getList() {
        try {
            Utils.fxlh(configfile);
            Utils.parseToFileBean(newfile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 复制文件时获得目标路径 配置文件必须在目标文件夹下！
     * @param filepath
     * @param newfile
     * @param configfilepath
     * @return
     */
    public File filePathTool(String filepath, String newfile, String configfilepath) {
        String target = null;
        //将filepath截去newfile然后拼接configfilepath
        target = filepath.replace(newfile, configfilepath);
        File target2 = new File(target);
        return target2;
    }

    /**
     * 创建文件夹
     * @param dir
     */

    public void createDir(File dir) {
        String dirpath = dir.getPath();
        if (dir.exists()) {// 判断目录是否存在
            System.out.println("创建目录失败，目标目录已存在！");
        }
        if (!dirpath.endsWith(File.separator)) {// 结尾是否以"/"结束
            dirpath = dirpath + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + dirpath);
        } else {
            System.out.println("创建目录失败！");
        }
    }

    /**
     * 删除文件
     * @param file
     */
    public void deleteFile(File file) {
        String filePath = file.getPath();
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除文件成功：" + filePath);
        }
    }

    /**
     * 删除文件夹
     * @param path
     */
    public void deleteDir(File path) {
        if (!path.exists()) {
            System.out.println("路径不存在");
            return;
        }
        path.delete();
        System.out.println("删除文件夹成功：" + path.getPath());
    }

    /**
     * 文件复制
     * @param source
     * @param target
     */
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
