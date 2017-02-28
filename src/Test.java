import java.io.File;

/**
 * Created by LX on 2017/2/21.
 */
public class Test {
    public static void main(String[] args) {
        File newfile = new File("D:\\IDE\\JAVA");
        File configfile=new File("D:\\test.xl");
        FileBean fileBean=new FileBean();
        fileBean.setMD5("afda492eae923a8f299c38c1fef9c553");
        fileBean.setPath("D:\\IDE\\JAVA\\ASCII.jpg");
        fileBean.setName("ASCIIx.jpg");
        //Utils.xlh(configfile,fileBean);
        Compare compare=new Compare(newfile,configfile);
        compare.getResult();



//        Utils.xlh(file,fileBean2);
//        Utils.fxlh(file);
//        System.out.println(fileBean.equals(fileBean3));
//
//        System.out.println(Utils.getFileMD5(file));

//        NEW_FILE_LIST.removeAll(list2);
//        System.out.println(NEW_FILE_LIST.size());
//        for(FileBean temp :NEW_FILE_LIST){
//            temp.toString();
//        }
    }




}
