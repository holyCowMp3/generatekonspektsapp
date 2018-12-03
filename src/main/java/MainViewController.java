import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

public class MainViewController {

    @FXML
    private TextField batalioneText;

    @FXML
    private TextField signingDateText;

    @FXML
    private TextField comanderDivisioneText;

    @FXML
    private TextField mainSergant;

    @FXML
    private TextField datePochatku;

    @FXML
    private TextField comanderBattalioneText;

    @FXML
    private TextField signingMonthText;

    @FXML
    private TextField comander0Text;

    @FXML
    private TextField gangalText;

    @FXML
    private TextField signingYearText;

    @FXML
    private TextField comander1Text;

    @FXML
    private TextField rotaText;

    @FXML
    void generate(ActionEvent event) {
        Docx docx = new Docx("/home/user/Рабочий стол/"+"упд2.docx");
        docx.setVariablePattern(new VariablePattern("{{", "}}"));
        Variables var = new Variables();
        var.addTextVariable(new TextVariable("{{bat}}",batalioneText.getText()));
        var.addTextVariable(new TextVariable("{{rota}}",rotaText.getText()));
        var.addTextVariable(new TextVariable("{{nach}}",comanderBattalioneText.getText()));
        var.addTextVariable(new TextVariable("{{dateza}}",signingDateText.getText()));
        var.addTextVariable(new TextVariable("{{month}}",signingMonthText.getText()));
        var.addTextVariable(new TextVariable("{{year}}",signingYearText.getText()));
        for (int i=0;i<6;i++){
            var.addTextVariable(new TextVariable("{{date"+i+"}}", (Integer.parseInt(datePochatku.getText())+i)+""));
        }
        var.addTextVariable(new TextVariable("{{6}}","6:40-7:10"));
        var.addTextVariable(new TextVariable("{{mpz}}",gangalText.getText()));
        var.addTextVariable(new TextVariable("{{kom}}",comanderDivisioneText.getText()));
        var.addTextVariable(new TextVariable("{{komvz0}}",comander0Text.getText()));
        var.addTextVariable(new TextVariable("{{komvz1}}",comander1Text.getText()));
        var.addTextVariable(new TextVariable("{{gsgt}}",mainSergant.getText()));
        var.addTextVariable(new TextVariable("{{8}}","8:15-8:45"));
        var.addTextVariable(new TextVariable("{{9-14}}","9:00-14:50"));
        var.addTextVariable(new TextVariable("{{9-10}}","9:00-10:45"));
        var.addTextVariable(new TextVariable("{{10}}","10:50-14:50"));
        var.addTextVariable(new TextVariable("{{10-2}}","10:50-12:35"));
        var.addTextVariable(new TextVariable("{{15}}","15:50-17:50"));
        var.addTextVariable(new TextVariable("{{18}}","18:00-18:10"));
        var.addTextVariable(new TextVariable("{{19}}","19:00-19:50"));
        var.addTextVariable(new TextVariable("{{12}}","12:40-14:50"));
        var.addTextVariable(new TextVariable("{{9-12}}","9:00-12:45"));
        var.addTextVariable(new TextVariable("{{12-3}}","12:40-13:30"));

        var.addTextVariable(new TextVariable("{{rfz}}","Ранкова фізична зарядка\nТренування у виконанні вправи з підтягування на перекладині; комплексно-силової вправи”.\n" +
                "\n"));
        var.addTextVariable(new TextVariable("{{mpz}}",gangalText.getText()));



    }

}
