package com.example.homesecurity;

import classes.Change;
import classes.Event;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import utils.DatabaseUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChangeLogController {
    @FXML
    private TableView<Change> changeTableView;
    @FXML
    private TableColumn<Change, String> deviceNameChangeTableColumn;
    @FXML
    private TableColumn<Change, String> deviceTypeChangeTableColumn;
    @FXML
    private TableColumn<Change, String> messageChangeTableColumn;
    @FXML
    private TableColumn<Change, String> changeTimeAddedTableColumn;

     List<Change>allChanges=DatabaseUtil.getAllChanges();
     ObservableList<Change> allChangesObservableList= FXCollections.observableArrayList(allChanges);

    public void initialize(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        deviceNameChangeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceName()));

        deviceTypeChangeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceType()));

        changeTimeAddedTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getTime_added().format(formatter)));

        messageChangeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getMessage()));

        changeTableView.setItems(allChangesObservableList);

    }

    public void dismissAll(){
        allChangesObservableList.removeAll();
        changeTableView.refresh();
        DatabaseUtil.dismissChanges();
        initialize();
    }
}
