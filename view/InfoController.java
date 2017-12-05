package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class InfoController implements Initializable {

	@FXML
	private Text infoText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void init(String info) {
		infoText.setText(info);
	}

}
