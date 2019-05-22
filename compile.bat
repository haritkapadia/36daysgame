set FX_LIB=javafx-sdk-11.0.2\lib

javac --module-path %FX_LIB% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web -d bin src\*.java

