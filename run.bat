set FX_LIB=lib\javafx-sdk-14.0.1\lib
java -cp ";bin;lib\j3d-org-texture_1.1.0.jar;" --module-path %FX_LIB% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web -Xmx4G Main
