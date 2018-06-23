/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bytesworks.solutions;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.stream.Stream;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ifiok Okonna
 * 
 * The program below is a Crawler that uses IP addresses to crawl 
 * websites for information. The crawler is a bit erroneous as it 
 * requires time to make it perfect
 * 
 * The Idea behind this crawler is to take an IP range and loop through it to get host names,
 * Data returned in XML format will be processed to extract the required information
 * then it will be sent to a PDF File processor for display
 * 
 */
public class Crawler {

    int numRecords;
    String startIP = "41.206.19.34";
    String endIP = "41.206.19.100";//197.210.205.27
    String lastIP = startIP;
    public Crawler(int numRecords) {
        this.numRecords = numRecords;
    }
    public void fireCrawler(){
        for(int i=0; i<numRecords; i++){
            try{
                //Get Hostname from IP
                String hostname = getHost(lastIP);
                //Get XML
                StringBuffer xml = getWebsite(hostname);
                
                System.out.println(lastIP+" - "+hostname);
                System.out.println(xml);
                //Start Processing website as XML Data
                Document data = getXML(xml);

                data.normalizeDocument();

                Element root = data.getDocumentElement();

                NodeList atag = root.getElementsByTagName("a");
                NodeList ptag = root.getElementsByTagName("p");
                NodeList sptag = root.getElementsByTagName("span");

                //More Processing here
                
                
            }catch(Exception e){
                
            }

            
            
            if(lastIP.equals(endIP))
                break;
            
            String[] ip = getIPRaw(lastIP);
            int a = Integer.valueOf(ip[0]),
                    b = Integer.valueOf(ip[1]),
                    c = Integer.valueOf(ip[2]),
                    d = Integer.valueOf(ip[3]);

            if((b + 1) == 255 && b<=255)
                a++;
            if((c + 1) == 255 && c<=255)
                b++;
            if((d + 1) == 255 && c<=255)
                c++;
            if((d + 1) == 256)
                d=1;
            else
                d++;                

            ip[0] = String.valueOf(a);
            ip[1] = String.valueOf(b);            
            ip[2] = String.valueOf(c);
            ip[3] = String.valueOf(d);
            String nIp = getIPString(ip);
            lastIP = nIp;
            
        }

    }
    public String[] getIPRaw(String input){
        return input.split("\\.");
    }
    public String getIPString(String[] ip){
        return ip[0]+"."+ip[1]+"."+ip[2]+"."+ip[3];
    }
    private StringBuffer getWebsite(String address){
        HttpURLConnection con = null;
        try{
            int connectTimeout = 3000;
            int readTimeout = 3000;
            
            URL url = new URL("http://"+address);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");          
            
            con.setConnectTimeout(connectTimeout);
            con.setReadTimeout(readTimeout);
            
            con.setInstanceFollowRedirects(true);
            
            int status = con.getResponseCode();
            
            System.out.println("Website Status: "+status);
            
            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM) {
                      String location = con.getHeaderField("Location");
                      URL newUrl = new URL(location);
                      con = (HttpURLConnection) newUrl.openConnection();
            }
            
            BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = input.readLine()) != null) {
                content.append(inputLine);
            }
            
            input.close(); 
            	
            return content;
            
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
            return new StringBuffer("");
            
        }finally{
            if(	con != null)
                con.disconnect();
        }
        
    }
    private String getHost(String ip){
        try{
            InetAddress addr = InetAddress.getByName(ip);
            String hostname = addr.getHostName();

            return hostname;
        }catch(UnknownHostException ex){
            return null;
        }
    }
    private Document getXML(StringBuffer data){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                            data.toString().getBytes("UTF-8"));

            return builder.parse(input);
        }catch(Exception e){
            return null;
        }
        
    }
    private void getPDF(){
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("iTextTable.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            addRows(table);
            document.add(table);

            document.close();        
        } catch (FileNotFoundException ex) {
            
        } catch (DocumentException ex) {
            
        }
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("column header 1", "column header 2", "column header 3")
          .forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }
    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }
   
}
