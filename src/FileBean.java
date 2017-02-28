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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileBean fileBean = (FileBean) o;

        if (!name.equals(fileBean.name)) return false;
        if (!path.equals(fileBean.path)) return false;
        return MD5.equals(fileBean.MD5);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + path.hashCode();
        result = 31 * result + MD5.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", MD5='" + MD5 + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
