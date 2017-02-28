import java.io.File;

/**
 * Created by LX on 2017/2/21.
 */
public class Test {
    public static void main(String[] args) {
        File newfilepath = new File("D:\\IDE\\JAVA");
        File configfilepath = new File("D:\\test.lx");
        FileBean fileBean = new FileBean();
        fileBean.setMD5("afda492eae923a8f299c38c1fef9c553");
        fileBean.setPath("D:\\IDE\\JAVA\\ASCII.jpg");
        fileBean.setName("ASCIIx.jpg");
        //Utils.xlh(configfile,fileBean);
        Compare compare = new Compare(newfilepath, configfilepath);
        compare.getResult();

        System.out.println(Utils.getConfigFilePath("D:\\"));
    }
}
