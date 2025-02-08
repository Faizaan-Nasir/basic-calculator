import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.lang.Math;
// import javafx.scene.input.KeyCode;

public class Calculator extends Application{
    @Override
    public void start(Stage primaryStage) {
        VBox root=new VBox(0);
        root.setMaxWidth(Double.MAX_VALUE);
        Label label=new Label("0");
        root.getChildren().add(label);
        root.setAlignment(Pos.CENTER_RIGHT);
        

        GridPane grid=new GridPane();
        Button buttons[][];
        buttons = new Button[5][4];
        String [][] arr={
        {"AC","del",".","^"},
        {"1","2","3","+"},
        {"4","5","6","-"},
        {"7","8","9","/"},
        {"0","%","*","="}};
        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++){
                final int row = i;
                final int col = j;
                buttons[i][j]=new Button(arr[i][j]);
                buttons[i][j].setMinWidth(55);
                buttons[i][j].setMinHeight(55);
                if (arr[i][j].equals("=")){
                    buttons[i][j].setOnAction(e->{
                        label.setText(Double.toString(compute(label.getText())));
                    });
                }
                else{
                    buttons[i][j].setOnAction(e->{
                        if(checkOperator(label.getText().charAt(label.getText().length()-1))&&checkOperator(arr[row][col].charAt(0))){
                            label.setText(label.getText().substring(0,label.getText().length()-1)+arr[row][col]);
                        }
                        else if(arr[row][col].equals("AC")){
                            label.setText("0");
                        }
                        else if(arr[row][col].equals("del") && label.getText().length()>1){
                            label.setText(label.getText().substring(0,label.getText().length()-1));
                        }
                        else if(arr[row][col].equals("del")){
                            label.setText("0");
                        }
                        else if (label.getText().equals("0")){
                            label.setText(arr[row][col]);
                        }
                        else{
                            label.setText(label.getText()+arr[row][col]);
                        }
                    });
                }
                grid.add(buttons[i][j],j,i);
            }
        }
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 280, 450);
        // scene.setOnKeyPressed(event->{
        //     if(event.getCode()==KeyCode.ENTER){
        //         event.consume();
        //         buttons[4][3].fire();
        //     }
        // });
        scene.setOnKeyTyped(event -> {
            // System.out.println(event.getCharacter());
            for(int i=0;i<5;i++){
                // if(event.getCharacter().isBlank()||event.getCharacter().equals("\r")){
                //     System.out.println("hello");
                //     break;
                // }
                for(int j=0;j<4;j++){
                    if(event.getCharacter().equals(arr[i][j])){
                        buttons[i][j].fire();
                    }
                }
            }
        });
        scene.getStylesheets().add(getClass().getResource("./src/styles.css").toExternalForm());
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(280);
        primaryStage.setMaxHeight(450);
        primaryStage.setMinWidth(280);
        primaryStage.setMinHeight(450);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public static boolean checkOperator(char ch){
        if (ch=='+'||ch=='-'||ch=='/'||ch=='%'||ch=='*'||ch=='^'){
            return true;
        }
        return false;
    }

    public static double compute(String line){
        double result=0;
        String num="";
        int i=0;
        if (line.charAt(i)=='-'){
            num+=line.charAt(i);
            i+=1;
        }
        for(;i<line.length()&&checkOperator(line.charAt(i))==false;i++){
            num+=line.charAt(i);
        }
        char op='+';
        for(int j=i;j<line.length();j++){
            if (checkOperator(line.charAt(j))){
                result=modifyResult(result,num,op);
                op=line.charAt(j);
                num="";
            }
            else{
                num+=line.charAt(j);
            }            
        }
        result=modifyResult(result,num,op);
        
        return result;
    }
    public static double modifyResult(double result,String num,char op){
        if (op=='+'){
            result+=Double.parseDouble(num);
        }
        else if(op=='-'){
            result-=Double.parseDouble(num);
        }
        else if(op=='/'){
            result/=Double.parseDouble(num);
        }
        else if(op=='%'){
            result%=Double.parseDouble(num);
        }
        else if(op=='*'){
            result*=Double.parseDouble(num);
        }
        else if(op=='^'){
            result=Math.pow(result,Double.parseDouble(num));
        }
        return result;
    }
}