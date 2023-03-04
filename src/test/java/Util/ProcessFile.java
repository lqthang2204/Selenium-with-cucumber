package Util;

import io.cucumber.java.sl.In;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.*;
import java.util.Map;

public class ProcessFile {
    public void createFileCSV(String fileName, String[] headerName) throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/resources";
        path =  createFolderIfNotExist(path,"FileCSV");
        File fileCSV = new File(path+"/"+fileName+".csv");
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
    public void createFileExcel(String fileName, String[] headerName, String fileType) throws IOException {
        String path = System.getProperty("user.dir") + "/src/test/resources";
        path =  createFolderIfNotExist(path,"FileExcel");
       Workbook wb = getWorkbook(fileType);
       Sheet sheet =  wb.createSheet("automation");
       Row row = sheet.createRow(0);
       for(int i=0;i<headerName.length;i++){
           row.createCell(i).setCellValue(headerName[i].replace("\"",""));
       }
        OutputStream fileOut = new FileOutputStream(path+"/"+fileName+"."+fileType);
        System.out.println("Excel File has been created successfully.");
        wb.write(fileOut);
    }
    private Workbook getWorkbook(String excelFilePath)
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
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

        public void WriteDataExcel(String data, String fileName, Map<String, String> map) throws IOException, InvalidFormatException {
        String path = System.getProperty("user.dir")+"/src/test/resources/FileExcel/";
        String file = path+fileName;
        data = data.replace("\"","");
        String[] arrData = getArrayDataToScript(data, map);
        if(checkFileExist(file))
        {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sheet = wb.getSheetAt(0);
            int rowNumber = sheet.getLastRowNum();
            Row row = sheet.createRow(rowNumber+1);
            for(int i=0;i<arrData.length;i++){
                row.createCell(i).setCellValue(arrData[i]);
            }
            fis.close();
            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
        }else{
            throw new FileNotFoundException("Not found file excel "+ fileName);
        }

    }
    public String createFolderIfNotExist(String path, String folderName){
        File f = new File(path+"/"+folderName);
        if(!f.exists()){
            f.mkdir();
        }
        return  f.toString();
    }
    public int checkNumer(String number){
        try {
            int num = Integer.parseInt(number);
            return num-1;
        }
        catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException(number +" not a number, please input a number");
        }


    }
    public String[] getArrayDataToScript(String data, Map<String, String> map){
        String[] arrDataTemp =  data.split(",");
        String[] arrData= new String[arrDataTemp.length];
        for (int i=0;i<arrDataTemp.length;i++){
            if(map.containsKey(arrDataTemp[i])){
                arrData[i] = map.get(arrDataTemp[i]);
            }else{
                arrData[i] = arrDataTemp[i];
            }
        }
        return  arrData;

    }
    public boolean checkFileExist(String file){
       File f = new File(file);
        if(f.exists()){
            return true;
        }
        return false;

    }


}
