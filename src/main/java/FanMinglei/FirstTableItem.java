package FanMinglei;

import LrmTasks.TypeConversion;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Set;

public class FirstTableItem {
    private final ObservableMap<String, StringProperty> properties = FXCollections.observableHashMap();

    public StringProperty getProperty(String key) {
        return properties.computeIfAbsent(key, k -> new SimpleStringProperty());
    }

    public void setProperty(String key, String value) {
        getProperty(key).set(value);
    }
}
