import java.io.File;

public class JsonUtils {
    
    public static String getJson(String strPath) {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        File file = new File(strPath);
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                File mFile = new File(fileList[i].getPath());
                File[] mFileList = mFile.listFiles();
                for (int j = 0; j < mFileList.length; j++) {
                    String name = "";
                    String price = "";
                    String path = "";
                    String category = "";
                    String str = mFileList[j].getName();
                    String[] strList = str.split("\\$");
                    name = strList[0];
                    strList = strList[1].split("\\.");
                    price = strList[0];
                    path = mFileList[j].getPath();
                    strList=path.split("\\\\");
                    path="";
                    for(String mstr:strList){
                        path=path+mstr+"\\\\";
                    }
                    path=path.substring(0, path.length()-2);
                    category = mFileList[j].getParent().substring(
                            mFileList[j].getParent().lastIndexOf("\\") + 1);
                    builder.append("{name:\"").append(name).append("\",");
                    builder.append("price:").append(price).append(',');
                    builder.append("path:\"").append(path).append("\",");
                    builder.append("category:\"").append(category)
                            .append("\"},");
                }
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(']');
        return builder.toString();

    }
}