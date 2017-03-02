import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LX on 2017/2/28.
 */
public class Compare {
    File newfile = null;
    File configfile = null;

    public Compare(File newfile, File configfile) {
        this.newfile = newfile;
        this.configfile = configfile;
    }

    public List<FileBean> getResult() {
        List<FileBean> result = new ArrayList<>();
        //获取两个list
        if (getList()) {
            //保存序列化信息
            Utils.save();
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
        }
        return result;
    }

    //用于获取两个列表
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
}
