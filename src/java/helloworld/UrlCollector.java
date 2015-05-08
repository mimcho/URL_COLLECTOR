/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helloworld;
/**
 *
 * @author Mima Marinova
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("input")
public class UrlCollector {
   
    private final String input;
 
    public UrlCollector() {
//Creating simple interface of TextBox, ComboBox, Submit Button, and Link Button.
//On pressing "Submit" button the url and category go to URL.txt file.
//On pressing "Favorite List" button the content from the URL.txt file 
//displays on the screen into MediaType.TEXT_HTML
        
    this.input = "<H1><p> This is a program, that has two REST services: </p>" +
                "<p> first service using path parameter, and second - query parameter, </p>" +
                "<p> collecting your favorite websites' url, and  category. </p>" +
                "<p> The captured data has to be stored in a file, </p>" +
                "<p> that displays the content upon request. </p></H1>" +
                "<p> Please, input your favorite website's url and category~. </p>" +
                "<form action=\"input/query\" method=\"get\">" +
                    "Url: <input type=\"text\" name=\"FavoriteUrl\" />Category: " +
                    "<select name=\"FavoriteCategory\">" +
                        "<option value=\"estore\">E-Store</option>" +
                        "<option value=\"email\">E-Mail</option>" +
                        "<option value=\"search\" selected=\"selected\">Search</option>" +
                        "<option value=\"health\">Health</option>" +
                        "<option value=\"fasion\">Fasion</option>" +
                        "<option value=\"books\">Book pdf</option>" +
                        "<option value=\"java\">JAVA</option>" +
                        "<option value=\"crafts_kids\">Crafts for Kids</option>" +
                    "</select><input type=\"submit\" value=\"Submit\">" +
                "</form>" +
                "<button type=\"button\">" +
                    "<a href=\"/URL_COLLECTOR/resources/input/list\">Favorite List</a>" +
                "</button></BODY></HTML>";
    }
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String Input() {
        
        return input;
    }
    
    @GET
    @Path("/{FavoriteUrl}/{FavoriteCategory}")
    @Produces(MediaType.APPLICATION_JSON)
    public String PathUrl(@PathParam("FavoriteUrl") String furl,
        @PathParam("FavoriteCategory") String fcat) throws IOException {
            String copy = Copier(furl, fcat);
            
            return copy;
    }
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public String QueryCategory(@QueryParam("FavoriteUrl") String furl,
        @QueryParam("FavoriteCategory") String fcat) throws IOException {
            String copy = Copier(furl, fcat);
 
            return copy;
    }
    
    //FileWriter for writing in the txt file
    private String Copier(String furl, String fcat) throws IOException {
        FileWriter fr = new FileWriter("C:/input/URL.txt",true);
        fr.write(furl + " " + fcat + "\n");
        fr.flush();
        fr.close();
   
        return "Your favorite site: " + furl + "\n" + "in Category: " + fcat + 
               "\n" + "has been recorded recorded in C:/input/URL.txt";
        
        
                
    }
    
    //Method for creating url address
    public String CreateURL(String url, String cat) {
        String url1 = "<a href=\"";
        String url2 = "\">";
        String url3 = "</a>";
        String urlString;
        urlString = "<p>" + cat + ": " + url1 + "http://"+ url + url2 + url + url3 + "\n";
        
        return urlString;
    }
    
    //Method for loading like list url and categories from URL.txt file
    @Path("/list")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public  String loadList() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader("C:/input/URL.txt");
        BufferedReader br = new BufferedReader(fr);
        String ln;     
        String htmlHeader;
        htmlHeader = "<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML 2.0//EN\">" +
            "<HTML><BODY>Your favorite websites are: ";
        String htmlEnd = "</BODY></HTML>";
        String urlList = "\n";
        //Tokenizer
        while((ln = br.readLine()) != null){
            StringTokenizer st = new StringTokenizer(ln," ");
            urlList = urlList + CreateURL(st.nextToken(),st.nextToken()) + "\n";           
        }
        
        br.close();
        fr.close();
        
        return htmlHeader + urlList + htmlEnd;
    }
}

