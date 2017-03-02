import java.util.ArrayList;
import java.util.List;

/**
 * Created by LX on 2017/3/1.
 */
public class Constant {
    final static String NEW_FILE_PATH="c:\\JAVA";     //现在新的文件夹
    final static String CONFIG_FILE_PATH="D:\\IDE\\JAVA";    //之前的文件夹 有配置文件 没有就rebuild下
    static List<FileBean> NEW_FILE_LIST = new ArrayList<>();
    static List<FileBean> OLD_FILE_LIST = new ArrayList<>();
}
