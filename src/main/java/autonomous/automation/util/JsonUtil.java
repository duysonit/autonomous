//package autonomous.automation.util;
//
//import com.jayway.jsonpath.JsonPath;
//import net.minidev.json.JSONArray;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//
//public class JsonUtil {
//    public static String getAttribute(String json ,String path, String attribute){
//        JSONArray array = JsonPath.read(json,path);
//        Map<String, String> map = (Map)array.get(0);
//        return map.get(attribute);
//    }
//
//    //Return attribute of the first item in an array
//    public static String getAttribute(JSONArray array, String attribute){
//        Map<String, Object> map = (Map)array.get(0);
//        return map.get(attribute).toString();
//    }
//
//    public static List<String> getAttributes(JSONArray array, String attribute){
//        List<String> listAtt = new ArrayList<String>();
//        for (int i=0; i<array.size();i++){
//            Map<String, Object> map = (Map)array.get(i);
//            listAtt.add(map.get(attribute).toString());
//        }
//
//        return listAtt;
//    }
//}
