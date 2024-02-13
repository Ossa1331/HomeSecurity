package com.example.homesecurity;

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

public class EventLogController {

    @FXML
    private TableView<Event> eventsTableView;

    @FXML
    private TableColumn<Event, String> eventDeviceNameTableColumn;
    @FXML
    private TableColumn<Event, String> eventDeviceTypeTableColumn;
    @FXML
    private TableColumn<Event, String> eventTimeAddedTableColumn;
    @FXML
    private TableColumn<Event, String> eventMessageTableColumn;

    List<Event> allEvents= DatabaseUtil.getAllEvents();

    ObservableList<Event> allEventsObservableList= FXCollections.observableList(allEvents);

    public void initialize(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");



        eventDeviceNameTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceName()));

        eventDeviceTypeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceType()));

        eventTimeAddedTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getCurrentEventTriggered().format(formatter)));

        eventMessageTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getMessage()));

        eventsTableView.setItems(allEventsObservableList);
    }

    public void dismissAll(){
        allEventsObservableList.removeAll();
        DatabaseUtil.dismissEvents();
        eventsTableView.refresh();
        initialize();
    }






}
