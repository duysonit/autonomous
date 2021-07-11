package autonomous.automation.web.util;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVHelper {

    public static Object[][] getDataFromCsv (String filePath) {


        List<List<String>> data = new ArrayList();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filePath));
            String[] line;

            while ((line = reader.readNext()) != null) {

                List<String> dataRow = new ArrayList<String>();
                for (int i = 0; i < line.length; i++) {
                    dataRow.add(line[i]);
                }

                data.add(dataRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    return convertListToArray(data);
    }

    public static Object[][] convertListToArray(List<List<String>> data) {
        Object[][] retObjArr = new Object[data.size()][data.get(0).size()];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                retObjArr[i][j] = data.get(i).get(j);
            }
        }
        return retObjArr;
    }

}
