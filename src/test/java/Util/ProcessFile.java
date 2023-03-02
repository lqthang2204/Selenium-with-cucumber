package Util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.Map;

public class ProcessFile {
    public void createFileCSV(String fileName, String[] headerName) throws IOException {
        File fileCSV = new File(System.getProperty("user.dir") + "/src/test/resources/FileCSV/"+fileName+".csv");
        FileWriter fileWriter = null;
        StringBuilder line = null;
        if(fileCSV.exists()){
           throw new IOException("File name existed, please change file name");
        }else{
            fileWriter = new FileWriter(fileCSV);
            line = new StringBuilder();
            for(int i=0;i<headerName.length;i++){
                line.append(headerName[i]);
                if(i!=headerName.length-1){
                    line.append(";");
                }
            }
        }
        fileWriter.write(line.toString());
        fileWriter.close();
    }
    public void WriteDataCSV(String data, String fileName, Map<String, String> map) throws IOException {
        FileWriter  fileCSV = new FileWriter(System.getProperty("user.dir") + "/src/test/resources/FileCSV/"+fileName.replace("\"",""), true);
        BufferedWriter out = new BufferedWriter(fileCSV);
        out.write("\n");
        if(map.containsKey(data)){
            data = map.get(data);
        }
        out.write(data.replace("\"",""));
        out.close();
    }

    public void WriteFileExcel(String fileName, String sheetName, String header){
        Workbook wb = new HSSFWorkbook();
        wb.createSheet(sheetName);

    }
}
