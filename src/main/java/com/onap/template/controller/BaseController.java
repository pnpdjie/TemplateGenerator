package com.onap.template.controller;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * 控制器基类.
 * 
 * @author ywx474563 2017年6月13日
 */
public abstract class BaseController implements Initializable {

  protected Stage mainStage;

  public Stage getMainStage() {
    return mainStage;
  }

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }
  
}