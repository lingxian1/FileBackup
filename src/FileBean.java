import java.io.Serializable;

/**
 * Created by LX on 2017/2/21.
 */
public class FileBean implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    String path;
    String MD5;
    String state;
    String relativePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state =state;
    }

    public boolean equals(Object o) {
        FileBean fileBean = (FileBean) o;
        if (!name.equals(fileBean.name)) return false;
        if(!MD5.equals(fileBean.MD5)) return false;
        if(!relativePath.equals(fileBean.relativePath)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + MD5.hashCode();
        result = 31 * result + relativePath.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", MD5='" + MD5 + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
