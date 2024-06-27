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
import miny1233.Analyzer;
import miny1233.Standardizer;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 获取Scene
 */
public class getScene {
    private TypeConversion typeConversion = new TypeConversion();
    FirstSet firstSet = new FirstSet(typeConversion);
    FollowSet followSet = new FollowSet(typeConversion);

    AnalyticsTable analyticsTable = new AnalyticsTable(typeConversion);

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
                firstSet.computeFirstSets();
                followSet.getFollowSet();
                analyticsTable.getAnalyticsTable();

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
        //点击获取预测分析表后的动作
        getTable.setOnAction(actionEvent -> {

            Stage thirdStage = new Stage();
            Scene tableScene = getTableScene();
            thirdStage.setTitle("预测分析器");
            thirdStage.setScene(tableScene);
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
                for(var inner : first){
                    if(temp.getVal().equals(inner.getVal())){
                        result = "1";  //First集中存在
                    }
                }
                row.setProperty(temp.getVal(), result);
            }
            if (first.contains(typeConversion.getEmptyCharacterMap().values())) {
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
            Set<CharacterBase> follow = nonTerminators.getFollow(); // 获取Follow集
            System.out.println("follow的大小：" + follow.size());
            for(CharacterBase t:follow){
                System.out.println("follow = " + t.getVal());
            }
           FollowTableItem row = new FollowTableItem();
            row.setProperty("left", "Follow(" + str + ")"); // 最左边那一列
            for (Terminators temp : terminators) { // 遍历所有终结符列
                String result = follow.contains(temp) ? "1" : "0";// 如果当前Follow集中有，就设置为1
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

    public Scene getTableScene(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setSpacing(10); // 增加VBox子项之间的间距
        vBox.setAlignment(Pos.TOP_CENTER); // 居中对齐

        Label titleLabel = new Label("预测分析表");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // 设置标题的样式

        TableView<AnalyticsTableItem> tableView = new TableView<>();
        tableView.setPrefHeight(200); // 设置TableView的高度

        Map<String, Terminators> terminatorsMap = typeConversion.getTerminatorsMap();
        typeConversion.removeEmptyTerminators();
        Set<String> keySet = terminatorsMap.keySet();

        List<Terminators> terminators = new ArrayList<>();
        TableColumn<AnalyticsTableItem, String> firstColumn = new TableColumn<>(" ");
        firstColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("left"));
        tableView.getColumns().add(firstColumn);

        //设置表头
        for (String str : keySet) {
            Terminators terminator = terminatorsMap.get(str);
            TableColumn<AnalyticsTableItem, String> column = new TableColumn<>(terminator.getVal());
            column.setCellValueFactory(cellData -> cellData.getValue().getProperty(terminator.getVal()));
            column.setMinWidth(80);
            tableView.getColumns().add(column);
            terminators.add(terminator);
        }

        Map<String, NonTerminators> nonTerminatorsMap = typeConversion.getNonTerminatorsMap();
        ObservableList<AnalyticsTableItem> data = FXCollections.observableArrayList();
        for(var key : nonTerminatorsMap.keySet()){
            NonTerminators nonTerminator = nonTerminatorsMap.get(key); //获取当前非终结符
            Map<Character, List<CharacterBase>> mapping = nonTerminator.getMapping();  //获取mapping
            AnalyticsTableItem row = new AnalyticsTableItem();
            row.setProperty("left", nonTerminator.getVal()); // 最左边那一列
            for(var terminator:terminators){
                //遍历所有终结符，找到mapping中key与其一样的，设置该单元格的值为List中所有元素转字符串后拼接
                String contentOfCell = "";
                String right = "";
                for(var keyOfMapping : mapping.keySet()){

                    if(terminator.getVal().charAt(0) == keyOfMapping){
                        List<CharacterBase> list = mapping.get(keyOfMapping);
                        contentOfCell = nonTerminator.getVal() + "->";
                        for(var contentOfList : list){
                            right+=contentOfList.getVal();
                        }
                    }
                }
                row.setProperty(terminator.getVal(),contentOfCell+=right);
            }
            data.add(row);
        }
        tableView.setItems(data);

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

        AtomicReference<StringBuilder> OutputBuffer = new AtomicReference<>(new StringBuilder());
        AtomicReference<Analyzer> analyzer = new AtomicReference<>(new Analyzer(typeConversion.getNonTerminatorsMap().get("E")));
        //提交后的事情
        submitButton.setOnAction(event -> {
            String sentence = inputSentence.getText();
            if(!sentence.isEmpty()){
                // 刷新显示和分析器
                OutputBuffer.set(new StringBuilder());
                analyzer.set(new Analyzer(typeConversion.getNonTerminatorsMap().get("E")));
                //隐藏button
                //submitButton.setVisible(false);
                if(!step.isVisible()){
                    step.setVisible(true);  //针对于分析完毕后，单步按钮消失，此时点击提交后进行新一轮分析，单步按钮不显示的情况
                }
                analyzer.get().setSentence(sentence); // 调用方法执行预测分析过程
                OutputBuffer.get().append(analyzer.get().getMachine().toString()).append('\n');
                processOutput.setText(OutputBuffer.toString()); // 显示预测分析过程
            }else {
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setContentText("句子不能为空");
                alertMessage.show();
            }
        });

        step.setOnAction(event -> {
            try {
                if(analyzer.get().next())
                    OutputBuffer.get().append(analyzer.get().getMachine().toString());
                else
                    OutputBuffer.get().append("分析停止 状态:").append(analyzer.get().getMachine().toString());
            }catch (Exception e)
            {
                OutputBuffer.get().append("发生错误 当前状态:").append(analyzer.get().getMachine().toString());
            }
            OutputBuffer.get().append('\n');
            processOutput.setText(OutputBuffer.toString());
            processOutput.setScrollTop(Double.MAX_VALUE);
            if(processOutput.getText().contains("停止")){
                step.setVisible(false); //文本框中包含停止，隐藏单步按钮
            }
        });

        // 将控件添加到VBox
        vBox.getChildren().addAll(titleLabel, inputSentence, submitButton, outputLabel,step, processOutput);


        Scene scene = new Scene(vBox, 500, 400); // 调整场景大小以适应内容
        return scene;
    }

}
