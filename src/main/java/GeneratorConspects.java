import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class GeneratorConspects {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
   private static Document document;

    static {
        try {
            document = Jsoup.parse(Unirest.get("http://tyzhden.ua/History/").asString().getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private static Element element =  document.getElementsByAttributeValue("class","ap3").get(0);
   private static Elements elements = element.getElementsByTag("a");
   private static List<String> links = elements.eachAttr("href");

    public GeneratorConspects() throws UnirestException {
    }

    public static List<CursantConspectModel> getModels(File file) throws IOException, ParseException, InvalidFormatException {
       OPCPackage pkg = OPCPackage.open(file);
       XSSFWorkbook workbook = new XSSFWorkbook(pkg);
       List<CursantConspectModel> list = new ArrayList<CursantConspectModel>();
       System.out.println();
       Iterator<Row> iterator = workbook.getSheetAt(0).iterator();
       for (int i=0; i<workbook.getSheetAt(0).getLastRowNum()+1; i++){
           Row row = workbook.getSheetAt(0).getRow(i);
           String theme = row.getCell(4).getStringCellValue().trim();
                   String rank = row.getCell(0).getStringCellValue().trim();
                   String pib =  row.getCell(2).getStringCellValue().trim();
                   String date = dateFormat.format(row.getCell(3).getDateCellValue());
           CursantConspectModel model =new CursantConspectModel(theme,rank,pib,date);
           System.out.println(model);
           list.add(model);
       }

       return list;
   }


   public static void fillFile(CursantConspectModel model) throws UnirestException, IOException, ParseException, ExecutionException, InterruptedException {

    List<String> paths = getAllFiles("/home/user/Apps/Plans");
       String docxPath = "";
       String text ="";

    switch (model.getTheme()){
        case "Тренування зі стройової підготовки":{
            int rand = new Random().nextInt(3);
            docxPath = paths.stream().filter(path -> path.contains(model.getTheme()+rand)).findFirst().get();

        } break;
        case "Тренування по захисту від ЗМУ":{
            docxPath = paths.stream().filter(path -> path.contains(model.getTheme())).findFirst().get();
        } break;
        case "Тренування захист від ЗМУ":{
            docxPath = paths.stream().filter(path -> path.contains(model.getTheme())).findFirst().get();
        } break;
        case "Спортивно-масова робота":{
            docxPath = paths.stream().filter(path -> path.contains(model.getTheme())).findFirst().get();
        } break;
        case "Ранкова фізична зарядка":{
            docxPath = paths.stream().filter(path -> path.contains(model.getTheme())).findFirst().get();
        } break;
        case "Ранковий огляд, вечірня прогулянка, перевірка":{
            docxPath = paths.stream().filter(path -> path.contains(model.getTheme())).findFirst().get();
        } break;
        case "Година культурно-освітньої роботи":{
            String body = Unirest.get(links.get(new Random().nextInt(links.size()))).asString().getBody();
            Element elementes = Jsoup.parse(body).getElementById("cheat-orphan");

            Thread.sleep(4000);
            try {
                text = elementes.text();
            } catch (Exception ex){
                ex.printStackTrace();
                Thread.sleep(4000);
                body = Unirest.get(links.get(new Random().nextInt(links.size()))).asString().getBody();
                elementes = Jsoup.parse(body).getElementById("cheat-orphan");
                try {
                    text = elementes.text();
                } catch (Exception exs){
                    exs.printStackTrace();
                }
            }

           docxPath = paths.stream().filter(path -> path.contains(model.getTheme())).findFirst().get();
            fillDocx(docxPath,model,text);

            return;
        }
            }
       fillDocx(docxPath,model);

   }

    public static List<String> getAllFiles(String simplePath) {
        List<String> pathses = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(simplePath))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> pathses.add(path.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathses;
    }

    public  static void fillDocx(String path, CursantConspectModel model, String...text) throws IOException, ParseException {
        System.out.println(path +" PATH!");
        System.out.println(model);
        Docx docx = new Docx(path);
        docx.setVariablePattern(new VariablePattern("#{", "}"));
        Variables var = new Variables();
        var.addTextVariable(new TextVariable("#{rank}",model.getRank()));
        var.addTextVariable(new TextVariable("#{day}",(Integer.parseInt(model.getDate().split("\\.")[0]))!=1?(Integer.parseInt(model.getDate().split("\\.")[0])-1)+"":1+""));
        var.addTextVariable(new TextVariable("#{month}",(dateFormat.parse(model.getDate()).getMonth()+1)+""));
        var.addTextVariable(new TextVariable("#{pd}","   " /*(Integer.parseInt(model.getDate().split("\\.")[0]))!=1?(Integer.parseInt(model.getDate().split("\\.")[0])-1)+"":1+"")*/));
        var.addTextVariable(new TextVariable("#{pib}",model.getPib()));

       if (text.length>0){
           var.addTextVariable(new TextVariable("#{text}",text[0]));
       }
       if ((dateFormat.parse(model.getDate()).getMonth()+1)<=9) {
           var.addTextVariable(new TextVariable("#{nach}", "старший лейтенант"));
       }else {
           var.addTextVariable(new TextVariable("#{nach}", "капітан"));
       }
       docx.fillTemplate(var);

       Files.createDirectories(Paths.get("/home/user/Apps/GeneratedFiles/"+model.getPib()+"/"));

       docx.save(new FileOutputStream(new File("/home/user/Apps/GeneratedFiles/"+model.getPib()+"/"+model.getTheme()+" "+model.getDate()+".docx")));
    }

    public static void main(String[] args) throws IOException, ParseException, InvalidFormatException {
        getModels(new File("/home/user/Apps/Plans/plans.xlsx")).stream().forEach(model -> {
            try {
                fillFile(model);
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
