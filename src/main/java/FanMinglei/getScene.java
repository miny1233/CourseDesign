package FanMinglei;

import LrmTasks.FirstSet;
import LrmTasks.TypeConversion;
import Model.CharacterBase;
import Model.NonTerminators;
import Model.Terminators;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import miny1233.Standardizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录页面
 */
public class getScene {
    private TypeConversion typeConversion = new TypeConversion();
    FirstSet firstSet = new FirstSet(typeConversion);
    FollowSet followSet = new FollowSet(typeConversion);

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

        vBox.getChildren().addAll(label1, input, submit);

        Scene scene = new Scene(vBox, 500, 240);

        submit.setOnAction(event -> {
            String text = input.getText();
            if(!text.isEmpty()){
                text = Standardizer.standardize(text);
                typeConversion.ConverseGrammar(text);
                typeConversion.saveGrammar(text);
                System.out.println("输入的文本内容是：" + text);
                Stage secondStage = new Stage();
                Scene secondScene = getSecondScene();
                secondStage.setTitle("预测分析器");
                secondStage.setScene(secondScene);
                secondStage.show();
            }else {
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setContentText("文法不能为空");
                alertMessage.show();
            }

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
            firstSet.computeFirstSets();
            Stage thirdStage = new Stage();
            Scene FirstCollectionScene = getFirstCollectionScene();
            thirdStage.setTitle("预测分析器");
            thirdStage.setScene(FirstCollectionScene);
            thirdStage.show();
        });
        //点击获取Follow集后的工作
        getFollow.setOnAction(actionEvent -> {
            followSet.getFollowSet();
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

        TableView<FirstTableItem> tableView = new TableView<>();
        tableView.setPrefHeight(200); // 设置TableView的高度

        Map<String, Terminators> terminatorsMap = typeConversion.getTerminatorsMap();
        typeConversion.removeEmptyTerminators();
        Set<String> keySet = terminatorsMap.keySet();

        List<Terminators> terminators = new ArrayList<>();
        TableColumn<FirstTableItem, String> firstColumn = new TableColumn<>(" ");
        firstColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("left"));
        tableView.getColumns().add(firstColumn);

        //设置表头
        for (String str : keySet) {
            Terminators terminator = terminatorsMap.get(str);
            TableColumn<FirstTableItem, String> column = new TableColumn<>(terminator.getVal());
            column.setCellValueFactory(cellData -> cellData.getValue().getProperty(terminator.getVal()));
            column.setMinWidth(80);
            tableView.getColumns().add(column);
            terminators.add(terminator);
        }
        TableColumn<FirstTableItem, String> lastColumn = new TableColumn<>("ε");
        lastColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("empty"));
        tableView.getColumns().add(lastColumn);
        Map<String, NonTerminators> nonTerminatorsMap = typeConversion.getNonTerminatorsMap();
        Set<String> keySet1 = nonTerminatorsMap.keySet();
        ObservableList<FirstTableItem> data = FXCollections.observableArrayList();

        for (String str : keySet1) {
            NonTerminators nonTerminators = nonTerminatorsMap.get(str); // 获取到当前非终结符
            Set<CharacterBase> first = nonTerminators.getFirst(); // 获取其First集
            System.out.println("first的大小：" + first.size());
            for(CharacterBase t:first){
                System.out.println("first = " + t.getVal());
            }
            FirstTableItem row = new FirstTableItem();
            row.setProperty("left", "First(" + str + ")"); // 最左边那一列
            for (Terminators temp : terminators) { // 遍历所有终结符列
                String result = "0";
                for (CharacterBase inner : first) { // 如果当前First集中有，就设置为1
                    if (inner.equals(temp)) {
                        result = "1";
                    }
                }
                row.setProperty(temp.getVal(), result);
            }
            // row.setProperty("left", "First(" + str + ")"); // 最左边那一列
            if (first.containsAll(typeConversion.getEmptyCharacterMap().values())) {
                row.setProperty("empty","1");
            } else {
                row.setProperty("empty","0");
            }

            data.add(row);
        }

        tableView.setItems(data);

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

        TableView<FollowTableItem> tableView = new TableView<>();
        tableView.setPrefHeight(200); // 设置TableView的高度

        Map<String, Terminators> terminatorsMap = typeConversion.getTerminatorsMap();
        typeConversion.removeEmptyTerminators();
        Set<String> keySet = terminatorsMap.keySet();

        List<Terminators> terminators = new ArrayList<>();
        TableColumn<FollowTableItem, String> firstColumn = new TableColumn<>(" ");
        firstColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("left"));
        tableView.getColumns().add(firstColumn);

        //设置表头
        for (String str : keySet) {
            Terminators terminator = terminatorsMap.get(str);
            TableColumn<FollowTableItem, String> column = new TableColumn<>(terminator.getVal());
            column.setCellValueFactory(cellData -> cellData.getValue().getProperty(terminator.getVal()));
            column.setMinWidth(80);
            tableView.getColumns().add(column);
            terminators.add(terminator);
        }

        Map<String, NonTerminators> nonTerminatorsMap = typeConversion.getNonTerminatorsMap();
        Set<String> keySet1 = nonTerminatorsMap.keySet();
        ObservableList<FollowTableItem> data = FXCollections.observableArrayList();

        for (String str : keySet1) {
            NonTerminators nonTerminators = nonTerminatorsMap.get(str); // 获取到当前非终结符
            Set<CharacterBase> follow = nonTerminators.getFollow(); // 获取其First集
            System.out.println("follow的大小：" + follow.size());
            for(CharacterBase t:follow){
                System.out.println("follow = " + t.getVal());
            }
           FollowTableItem row = new FollowTableItem();
            row.setProperty("left", "Follow(" + str + ")"); // 最左边那一列
            for (Terminators temp : terminators) { // 遍历所有终结符列
                String result = "0";
                for (CharacterBase inner : follow) { // 如果当前Follow集中有，就设置为1
                    if (inner.getVal() == temp.getVal()) {
                        result = "1";
                    }
                }
                row.setProperty(temp.getVal(), result);
            }
            data.add(row);
        }

        tableView.setItems(data);

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
        inputSentence.setPromptText("在此输入句子");

        Button submitButton = new Button("提交");
        submitButton.setStyle("-fx-font-size: 14px;"); // 设置按钮的样式

        Label outputLabel = new Label("预测分析过程：");
        outputLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;"); // 设置输出标签的样式

        Button step = new Button("单步");
        step.setStyle("-fx-font-size: 14px;");
        step.setLayoutX(500);
        step.setLayoutY(500);

        TextArea processOutput = new TextArea();
        processOutput.setPrefHeight(200); // 设置TextArea的高度
        processOutput.setWrapText(true); // 自动换行
        processOutput.setEditable(false); // 设置为只读

        //提交后的事情
        submitButton.setOnAction(event -> {
            String sentence = inputSentence.getText();
            if(!sentence.isEmpty()){
                //隐藏button
                submitButton.setVisible(false);
                String analysisProcess = "i进栈，用产生式F->i归约"; // 调用方法执行预测分析过程
                processOutput.setText(analysisProcess); // 显示预测分析过程
            }else {
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setContentText("句子不能为空");
                alertMessage.show();
            }
        });

        // 将控件添加到VBox
        vBox.getChildren().addAll(titleLabel, inputSentence, submitButton, outputLabel,step, processOutput);

        Scene scene = new Scene(vBox, 500, 400); // 调整场景大小以适应内容
        return scene;
    }

}
