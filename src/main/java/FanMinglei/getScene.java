package FanMinglei;

import LrmTasks.TypeConversion;
import Model.NonTerminators;
import Model.Terminators;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Set;

/**
 * 登录页面
 */
public class getScene {
    private TypeConversion typeConversion = new TypeConversion();

    public Scene getFirstScene() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(10); // 增加VBox子项之间的间距
        vBox.setAlignment(Pos.CENTER); // 居中对齐

        Label label1 = new Label("请输入文法：");
        label1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // 设置标签的样式

        TextArea input = new TextArea();
        input.setPrefHeight(100); // 设置TextArea的高度
        input.setWrapText(true); // 自动换行

        Button submit = new Button("提交");
        submit.setStyle("-fx-font-size: 14px;"); // 设置按钮的样式

        // 将控件添加到VBox
        vBox.getChildren().addAll(label1, input, submit);

        Scene scene = new Scene(vBox, 500, 240);

        submit.setOnAction(event -> {
            String text = input.getText();
            typeConversion.ConverseGrammar(text);
            System.out.println("输入的文本内容是：" + text);

            Stage secondStage = new Stage();
            Scene secondScene = getSecondScene();
            secondStage.setTitle("预测分析器");
            secondStage.setScene(secondScene);
            secondStage.show();
        });

        return scene;
    }

    public Scene getSecondScene() {
        VBox mainVBox = new VBox();
        mainVBox.setPadding(new Insets(20, 20, 20, 20));
        mainVBox.setSpacing(20); // 增加VBox子项之间的间距

        Label label1 = new Label("请选择操作：");
        label1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // 设置标签的样式

        HBox buttonHBox = new HBox();
        buttonHBox.setSpacing(10); // 增加HBox子项之间的间距
        buttonHBox.setAlignment(Pos.CENTER); // 居中对齐

        Button getFirst = new Button("获取First集");
        Button getFollow = new Button("获取Follow集");
        Button getTable = new Button("获取预测分析表");
        Button getProcess = new Button("预测分析过程");

        // 将按钮添加到HBox
        buttonHBox.getChildren().addAll(getFirst, getFollow, getTable, getProcess);

        // 将标签和HBox添加到VBox
        mainVBox.getChildren().addAll(label1, buttonHBox);

        Scene scene = new Scene(mainVBox, 600, 240);

        //点击获取First集后的工作
        getFirst.setOnAction(actionEvent -> {
            Stage thirdStage = new Stage();
            Scene FirstCollectionScene = getFirstCollectionScene();
            thirdStage.setTitle("预测分析器");
            thirdStage.setScene(FirstCollectionScene);
            thirdStage.show();
        });
        //点击获取Follow集后的工作
        getFollow.setOnAction(actionEvent -> {
            Stage thirdStage = new Stage();
            Scene FollowCollectionScene = getFollowCollectionScene();
            thirdStage.setTitle("预测分析器");
            thirdStage.setScene(FollowCollectionScene);
            thirdStage.show();
        });


        //点击预测分析过程后的工作
        getProcess.setOnAction(actionEvent -> {
            Stage thirdStage = new Stage();
            Scene ProcessScene = getProcessScene();
            thirdStage.setTitle("预测分析器");
            thirdStage.setScene(ProcessScene);
            thirdStage.show();
        });

        return scene;
    }

    public Scene getFirstCollectionScene() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(10); // 增加VBox子项之间的间距
        vBox.setAlignment(Pos.TOP_CENTER); // 居中对齐

        Label titleLabel = new Label("First集");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // 设置标题的样式

        TableView<Terminators> tableView = new TableView<>();
        tableView.setPrefHeight(200); // 设置TableView的高度
        Map<String, Terminators> terminatorsMap = typeConversion.getTerminatorsMap();
        Set<String> keySet = terminatorsMap.keySet();
        for(String str: terminatorsMap.keySet()){
            System.out.println("str = " + str);
        }
        for(String str : keySet){
            TableColumn column = new TableColumn(terminatorsMap.get(str).getVal());
            column.setMinWidth(80);
            tableView.getColumns().add(column);
        }

        // 创建表格列
       /* TableColumn<NonTerminators, String> empty = new TableColumn<>(" ");
        TableColumn<NonTerminators, String> first = new TableColumn<>("+");
        TableColumn<NonTerminators, String> second = new TableColumn<>("*");
        TableColumn<NonTerminators, String> third = new TableColumn<>("(");
        TableColumn<NonTerminators, String> fourth = new TableColumn<>(")");
        TableColumn<NonTerminators, String> fifth = new TableColumn<>("i");*/

        // 设置列宽度
       /* empty.setMinWidth(80);
        first.setMinWidth(80);
        second.setMinWidth(80);
        third.setMinWidth(80);
        fourth.setMinWidth(80);
        fifth.setMinWidth(80);*/

        // 将列添加到TableView
        //tableView.getColumns().addAll(empty, first, second, third, fourth, fifth);

        // 将标题和TableView添加到VBox
        vBox.getChildren().addAll(titleLabel, tableView);

        Scene scene = new Scene(vBox, 500, 300);
        return scene;
    }

    public Scene getFollowCollectionScene(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(10); // 增加VBox子项之间的间距
        vBox.setAlignment(Pos.TOP_CENTER); // 居中对齐

        Label titleLabel = new Label("Follow集");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // 设置标题的样式

        TableView<NonTerminators> tableView = new TableView<>();
        tableView.setPrefHeight(200); // 设置TableView的高度

        // 创建表格列
        TableColumn<NonTerminators, String> empty = new TableColumn<>(" ");
        TableColumn<NonTerminators, String> first = new TableColumn<>("+");
        TableColumn<NonTerminators, String> second = new TableColumn<>("*");
        TableColumn<NonTerminators, String> third = new TableColumn<>("(");
        TableColumn<NonTerminators, String> fourth = new TableColumn<>(")");
        TableColumn<NonTerminators, String> fifth = new TableColumn<>("i");

        // 设置列宽度
        empty.setMinWidth(80);
        first.setMinWidth(80);
        second.setMinWidth(80);
        third.setMinWidth(80);
        fourth.setMinWidth(80);
        fifth.setMinWidth(80);

        // 将列添加到TableView
        tableView.getColumns().addAll(empty, first, second, third, fourth, fifth);

        // 将标题和TableView添加到VBox
        vBox.getChildren().addAll(titleLabel, tableView);

        Scene scene = new Scene(vBox, 500, 300);
        return scene;
    }

    public Scene getProcessScene() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(10); // 增加VBox子项之间的间距
        vBox.setAlignment(Pos.TOP_CENTER); // 居中对齐

        Label titleLabel = new Label("请输入句子：");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // 设置标签的样式

        TextField inputSentence = new TextField();
        inputSentence.setPromptText("在此输入句子"); // 设置提示文本

        Button submitButton = new Button("提交");
        submitButton.setStyle("-fx-font-size: 14px;"); // 设置按钮的样式

        Label outputLabel = new Label("预测分析过程：");
        outputLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;"); // 设置输出标签的样式

        TextArea processOutput = new TextArea();
        processOutput.setPrefHeight(200); // 设置TextArea的高度
        processOutput.setWrapText(true); // 自动换行
        processOutput.setEditable(false); // 设置为只读

        // 设置按钮的点击事件
        submitButton.setOnAction(event -> {
            String sentence = inputSentence.getText();
            String analysisProcess = "i进栈，用产生式F->i归约"; // 调用方法执行预测分析过程
            processOutput.setText(analysisProcess); // 显示预测分析过程
        });

        // 将控件添加到VBox
        vBox.getChildren().addAll(titleLabel, inputSentence, submitButton, outputLabel, processOutput);

        Scene scene = new Scene(vBox, 500, 400); // 调整场景大小以适应内容
        return scene;
    }

}
