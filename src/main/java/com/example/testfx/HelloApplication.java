package com.example.testfx;

import entity.GrammarNode;
import entity.LexT;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lexical.analysis.Tests1;
import yufa.Test2;
import yuyi.Test3;

import java.io.File;
import java.nio.file.Files;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("文件");
        Menu analysisMenu = new Menu("分析");
        MenuItem openFileItem = new MenuItem("打开");
        MenuItem saveFileItem = new MenuItem("保存");
        MenuItem lexAnalysisItem = new MenuItem("词法分析");
        MenuItem synAnalysisItem = new MenuItem("语法分析");
        Menu semAnalysisItem = new Menu("语义分析");
        Menu interCodeGenItem = new Menu("中间代码生成");
        Menu codeOptimizeItem = new Menu("代码优化");
        Menu targetCodeGenItem = new Menu("目标代码生成");

        TextArea sourceCodeTextArea = new TextArea();
        TextArea outputTextArea = new TextArea();
        TextArea errorAnalysisTextArea = new TextArea();
        sourceCodeTextArea.setPrefHeight(800);
        sourceCodeTextArea.setPrefWidth(580);
        outputTextArea.setPrefHeight(595);
        outputTextArea.setPrefWidth(580);
        errorAnalysisTextArea.setPrefHeight(195);
        errorAnalysisTextArea.setPrefWidth(580);
        openFileItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择文件");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("c语言文件", "*.cpp")
            );
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    String content = new String(Files.readAllBytes(selectedFile.toPath()));
                    sourceCodeTextArea.setText(content);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        synAnalysisItem.setOnAction(e -> {
            LexT contents = Tests1.lineAy(sourceCodeTextArea.getText());
            StringBuffer str = new StringBuffer();
            StringBuffer str1 = new StringBuffer();


        });
        lexAnalysisItem.setOnAction(e -> {
            LexT contents = Tests1.lineAy(sourceCodeTextArea.getText());
            StringBuffer str = new StringBuffer();
            StringBuffer str1 = new StringBuffer();
            int i = 0;
            while (i < contents.words.size()){
                str.append(contents.words.get(i).toString() + "\n");
                i++;
            }
            i = 0;
            while (i < contents.erros.size()){
                str1.append(contents.erros.get(i).toString() + "\n");
                i++;
            }
            outputTextArea.setText(str.toString());
            errorAnalysisTextArea.setText(str1.toString());

        });

        synAnalysisItem.setOnAction(e->{
            LexT contents = Tests1.lineAy(sourceCodeTextArea.getText());
            GrammarNode node = Test2.getYu(contents);
            outputTextArea.setText(node.toString());
        });
        semAnalysisItem.setOnAction(e->{
            LexT contents = Tests1.lineAy(sourceCodeTextArea.getText());
            GrammarNode node = Test3.getYi(contents);
            outputTextArea.setText(Test3.toAString());
        });


        fileMenu.getItems().addAll(openFileItem, saveFileItem,lexAnalysisItem, synAnalysisItem,
                semAnalysisItem);
        menuBar.getMenus().addAll(
                fileMenu,
                interCodeGenItem,
                codeOptimizeItem,
                targetCodeGenItem
        );
        root.setTop(menuBar);

        VBox leftSide = new VBox(10);
        leftSide.getChildren().add(new Label("源代码"));
        leftSide.getChildren().add(sourceCodeTextArea);

        VBox displayWindows = new VBox(10);
        displayWindows.getChildren().addAll(
                new Label("实现窗口"),
                outputTextArea,
                new Label("错误分析窗口"),
                errorAnalysisTextArea
        );
        HBox box = new HBox(10);
        box.getChildren().addAll(leftSide,displayWindows);
        double sceneWidth = 1200;
        double sceneHeight = 900;

        root.setCenter(box);

        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        primaryStage.setTitle("C编译器");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
